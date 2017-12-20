/**
 * admin
 */
$(function(){
	setTabContentHeight();
	initNavClickEvent();
	initTabClickEvent();
	layui.use('element', function(){
		element = layui.element;
	});
});
$(window).resize(function() {
	setTabContentHeight();
});

//设置中间tab内容区的高度
function setTabContentHeight(){
	var winh = $(window).height();
	var h = winh-70-40;
	$(".layui-tab-content").height(h+"px");//alert(h)
}

//左侧导航菜单点击事件
function initNavClickEvent(){
	$(".admin_left a[name=active]").click(function(){
		var isAdd = true;
		var tabname = $(this).text();
		var tabid = $(this).attr("tabid");
		//循环tab页标题的ul，判断是否已经加载过该tab页，如果已经加载过则跳转过去，结束循环；如果没有则添加一个新的tab页
		$(".layui-tab-title li").each(function(){
			var thisLayid = $(this).attr("lay-id");
			if(thisLayid==tabid){
				isAdd = false;
				element.tabChange('adminTab', tabid); 
				return false;//结束循环
			}
		});
		var str = '<iframe name="mainframe" src="test.html"  width="100%" height="100%"  border="0" frameborder="0" scrolling="yes"  style="display:block;"></iframe>';
		if(isAdd){
			element.tabAdd('adminTab', {
			  title: tabname
			  ,content: str 
			  ,id: tabid
			});
			element.tabChange('adminTab', tabid);
			initTabClickEvent();
		}
	});
}

//tab页点击事件
function initTabClickEvent(){
	$(".layui-tab-title li").unbind("click").bind("click",function(){
		$(".admin_left .layui-this").removeClass("layui-this");
		var tabid = $(this).attr("lay-id");
		$(".admin_left a[tabid="+tabid+"]").parent().addClass("layui-this");
	});
}

