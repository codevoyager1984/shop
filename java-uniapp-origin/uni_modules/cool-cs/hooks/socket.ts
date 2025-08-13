import { defineStore } from "pinia";
// @ts-ignore
import io, { type Socket } from "@hyoga/uni-socket.io";
import type { Cs } from "../types";
import { useStore, module } from "/@/cool";
import { useSession } from "./session";
import { useMessage } from "./message";

export const useSocket = defineStore("cs.socket", () => {
	const config = module.config("cool-cs");
	const { user } = useStore();
	const session = useSession();
	const message = useMessage();

	let client = undefined as Socket | undefined;

	// 连接
	function connect() {
		if (!user.token) {
			return false;
		}

		if (client) {
			disconnect();
		}

		if (!client) {
			client = io(config.socketUrl, {
				transports: ["websocket", "polling"],
				auth: {
					isAdmin: false,
					token: user.token,
				},
			});

			client.on("connect", () => {
				console.log("[cs] connect");
			});

			client.on("disconnect", () => {
				console.log("[cs] disconnect");
			});

			client.on("msg", (data: Cs.Msg) => {
				if (data.type == 1) {
					if (data.sessionId == session.info?.id) {
						// 追加消息
						message.append({
							...data.user,
							...data,
							isAnimation: true,
						});

						// 读消息
						message.read(data.id!);
					}
				}
			});
		}
	}
	// 断开连接
	function disconnect() {
		client?.disconnect();
		client = undefined;
	}

	// 发送消息
	function send(content: Cs.Content) {
		if (client) {
			// 发送事件
			client.emit("send", {
				sessionId: session.info?.id,
				content,
			});
			// 追加消息
			message.append({ content });
		} else {
			console.log("[cs] client error");
		}
	}

	// 监听退出
	uni.$on("user.logout", () => {
		session.clear();
		message.clear();
		disconnect();
	});
	return {
		connect,
		client,
		send,
	};
});
