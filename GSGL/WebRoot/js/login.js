//预先加载所需要的模块
var layer;//弹层
var form;//表单
$(function(){
	initLayuiModules(initModulesCallback);
});

//初始化layui模块
function initLayuiModules(callback){
	layui.use(['layer','form'], function(){
	  layer = layui.layer;
	  form = layui.form;
	  callback();
	});
}

function initModulesCallback(){
	//监听提交
  form.on('submit(formSubmit)', function(data){
	  var value = data.field;
	  var param = value;
	  param.sqlapi="login";
	  $.ajax({
		   type: "POST",
		   dataType: "json",
		   url: "login",
		   data: param,
		   success: function(data){
			   if(data.code=="0"){
				   window.location.href="index.html";
			   }else{
				   layer.msg(data.msg);
			   }
		   }
	   });
	  return false;
  });
}