import dayjs from "dayjs";

// 日期格式化
export function dateFormatter(date?: Date) {
	const t = dayjs(date);

	// 在今天之前
	if (t.isBefore(dayjs().hour(0).minute(0).second(0))) {
		return t.format("YYYY-MM-DD HH:mm");
	} else {
		return t.format("HH:mm");
	}
}
