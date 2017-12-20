/**
 * index
 */
$(function(){
	initQuery();
	//element.init();//更新渲染动态效果
});
$(window).resize(function() {
});

$('.left_item').off('click').on('click',function(){
	$('.layui-this').removeClass('layui-this');
	$(this).addClass('layui-this');
});


