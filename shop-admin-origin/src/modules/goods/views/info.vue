<template>
	<cl-crud ref="Crud">
		<cl-row>
			<!-- 刷新按钮 -->
			<cl-refresh-btn />
			<!-- 新增按钮 -->
			<cl-add-btn />
			<!-- 删除按钮 -->
			<cl-multi-delete-btn />

			<cl-filter label="分类">
				<cl-select tree :options="options.type" prop="typeId" all-levels-id :width="140" />
			</cl-filter>

			<cl-filter label="状态">
				<cl-select :options="options.status" prop="status" :width="140" />
			</cl-filter>

			<cl-flex1 />
			<!-- 关键字搜索 -->
			<cl-search-key placeholder="搜索标题" />
		</cl-row>

		<cl-row>
			<!-- 数据表格 -->
			<cl-table ref="Table" />
		</cl-row>

		<cl-row>
			<cl-flex1 />
			<!-- 分页控件 -->
			<cl-pagination />
		</cl-row>

		<!-- 新增、编辑 -->
		<cl-upsert ref="Upsert" />

		<!-- 商品规格 -->
		<goods-spec :ref="setRefs('goodsSpec')" />
	</cl-crud>
</template>

<script lang="ts" name="goods-info" setup>
import { useCrud, useTable, useUpsert } from "@cool-vue/crud";
import { router, useCool } from "/@/cool";
import { computed, onActivated, reactive } from "vue";
import { deepTree } from "/@/cool/utils";
import type { Dict } from "/$/dict/types";
import GoodsSpec from "../components/spec.vue";

const { service, refs, setRefs } = useCool();

const options = reactive({
	type: [] as Dict.Item[],
	status: [
		{
			label: "已上架",
			value: 1
		},
		{
			label: "已下架",
			value: 0
		}
	]
});

// cl-upsert
const Upsert = useUpsert({
	dialog: {
		width: "1000px"
	},
	items: [
		{
			type: "tabs",
			props: {
				labels: [
					{
						label: "商品信息",
						value: "base"
					},
					{
						label: "商品介绍",
						value: "detail"
					}
				]
			}
		},
		{
			group: "base",
			label: "分类",
			prop: "typeId",
			component: {
				name: "cl-select",
				props: {
					options: computed(() => options.type),
					tree: true
				}
			},
			required: true
		},
		{
			group: "base",
			label: "标题",
			prop: "title",
			component: { name: "el-input", props: { clearable: true } },
			required: true
		},
		{
			group: "base",
			label: "副标题",
			prop: "subTitle",
			component: { name: "el-input", props: { clearable: true, type: "textarea", rows: 3 } }
		},
		{
			group: "base",
			label: "主图",
			prop: "mainPic",
			component: { name: "cl-upload" },
			required: true
		},
		{
			group: "base",
			label: "示例图",
			prop: "pics",
			component: { name: "cl-upload", props: { multiple: true, draggable: true } }
		},
		{
			group: "base",
			label: "价格",
			prop: "price",
			hook: "number",
			span: 12,
			component: {
				name: "el-input-number",
				props: {
					min: 0.01,
					max: 1000000
				}
			},
			required: true
		},
		{
			group: "base",
			label: "已售",
			prop: "sold",
			hook: "number",
			span: 12,
			component: {
				name: "el-input-number",
				props: {
					min: 0,
					max: 1000000
				}
			},
			required: true
		},
		{
			group: "base",
			label: "排序",
			prop: "sortNum",
			hook: "number",
			component: { name: "el-input-number" }
		},
		{
			group: "base",
			label: "状态",
			prop: "status",
			flex: false,
			value: 1,
			component: { name: "cl-switch" },
			required: true
		},
		{
			group: "detail",
			prop: "content",
			component: {
				name: "cl-editor-wang"
			}
		}
	],
	async onSubmit(data, { next }) {
		const info = await next(data);

		// 新增后弹出规格添加
		if (Upsert.value?.mode == "add") {
			refs.goodsSpec.openById(info.id);
		}
	}
});

// cl-table
const Table = useTable({
	columns: [
		{ type: "selection" },
		{ label: "分类", prop: "typeId", minWidth: 160, dict: computed(() => options.type) },
		{ label: "标题", prop: "title", minWidth: 240 },
		{
			label: "主图",
			prop: "mainPic",
			minWidth: 100,
			component: { name: "cl-image", props: { size: 50 } }
		},
		{
			label: "示例图",
			prop: "pics",
			minWidth: 100,
			component: { name: "cl-image", props: { size: 50 } }
		},
		{ label: "价格", prop: "price", minWidth: 100, sortable: "custom" },
		{ label: "已售", prop: "sold", minWidth: 100, sortable: "custom" },
		{ label: "状态", prop: "status", minWidth: 100, component: { name: "cl-switch" } },
		{ label: "排序", prop: "sortNum", minWidth: 100, sortable: "desc" },
		{ label: "创建时间", prop: "createTime", minWidth: 160, sortable: "custom" },
		{
			label: "更新时间",
			prop: "updateTime",
			minWidth: 160,
			sortable: "custom"
		},
		{
			type: "op",
			width: 300,
			buttons({ scope }) {
				return [
					"edit",
					"delete",
					{
						label: "规格",
						onClick() {
							refs.goodsSpec.open(scope.row);
						}
					},
					{
						label: "评论",
						onClick() {
							router.push({
								path: "/goods/comment",
								query: {
									id: scope.row.id,
									title: scope.row.title
								}
							});
						}
					}
				];
			}
		}
	]
});

// cl-crud
const Crud = useCrud(
	{
		service: service.goods.info
	},
	(app) => {
		app.refresh();
	}
);

// 获取分类
function getTypes() {
	service.goods.type.list().then((res) => {
		res.forEach((e) => {
			e.label = e.name;
			e.value = e.id;
		});

		options.type = deepTree(res);
	});
}

onActivated(() => {
	getTypes();
});
</script>
