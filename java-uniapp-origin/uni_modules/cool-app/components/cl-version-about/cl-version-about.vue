<template>
	<!-- #ifdef APP -->
	<cl-list>
		<cl-list-item label="当前版本" :arrow-icon="false">
			<cl-text color="info">{{ version.num }}</cl-text>
		</cl-list-item>

		<cl-list-item label="新版更新" :arrow-icon="false" :border="false" @tap="version.update()">
			<cl-loading :size="36" v-if="version.loading" />

			<template v-else>
				<cl-tag type="error" v-if="version.isUpgrade">
					{{ `新版本 ${version.updateInfo?.version}` }}
				</cl-tag>

				<cl-text color="info" value="无新版" v-else />
			</template>
		</cl-list-item>
	</cl-list>
	<!-- #endif -->
</template>

<script setup lang="ts">
import { useVersion } from "../../hooks";
import { onMounted } from "vue";

const version = useVersion();

onMounted(() => {
	version.check();
});
</script>
