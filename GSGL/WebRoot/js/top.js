/**
 * 
 */
//预先加载所需要的模块
var layer;//弹层
var form;//表单
var table;//表格
var layedit;//富文本编辑器
var laydate;//日期
var element;//元素操作,//导航的hover效果、二级菜单等功能，需要依赖element模块
var flow;//加载流
$(function(){
	initLayuiModules(initModulesCallback);
});

//初始化layui模块
function initLayuiModules(callback){
	layui.use(['layer','form','flow','element'], function(){
	  layer = layui.layer;
	  form = layui.form;
	  element = layui.element;
//	  table = layui.table;
//	  layedit = layui.layedit;
//	  laydate = layui.laydate;
	  flow = layui.flow;
	  callback();
	});
}

function initModulesCallback(){
	//initTree();
}