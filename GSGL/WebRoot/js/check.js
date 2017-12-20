/*check*/
/*******************通用方法******************/
//判断变量是否为空
function isNull(inVariable){
    if(inVariable=='undefined'||typeof inVariable=='undefined'||inVariable==undefined||inVariable == "" || inVariable == "null" ||inVariable==null || inVariable==0){
        return true;
    }else{
        return false;
    }
}
//验证是否输入数字
function checkNumber(num){
    var num = parseFloat(num);
    var express = /^[-+]?\d+(\.\d+)?$/;
    if(!express.test(num)){
        return false;
    }else{
        return true;
    }
}
//将json对象的null转为''
function parseNullForObj(t){
    for(var p in t){
        if(typeof t[p]=='undefined'||t[p]==null||t[p]=='null' || t[p]==undefined){
            t[p]='';
        }
    }
    return t;
}

/*
* 保留几位小数
* num:要处理的数
* n:保留小数的位数
* */
function parseFloatTo(num,n){//alert("num"+num+",n="+n);
    var r = '';
    if(!isNull(num)){
        var i = parseInt(n);
        r = parseFloat(num).toFixed(i);
    }
    if(num==0||num=="0"){
        r=0;
    }
    return r;
}

//显示弹出窗，在屏幕中间显示
function showCenter(id,width,height){
    var h= $(window).height();
    var w= $(window).width();
    var t = (h-height)/2;
    var l = (w-width)/2;
    $("#"+id).css({"top":t+"px","left":l+"px"}).show();
}

//复制对象
function cloneObj(obj) {
    var newObj = {}
    for(var prop in obj) {
        newObj[prop] = obj[prop];
    }
    return newObj
}

//按照数字大小正序排列：小的在前，大的在后
function numberOrder(a,b){
    return a-b;
}
//按照数字大小d倒序排列：大的在前，小的在后
function reverseOrder(a,b){
    return b-a;
}

//显示loading
function showLoading(text){
    var h = $(window).height();
    var w = $(window).width();
    var top = (h-120)/2;
    var left = (w-300)/2;
    if(text!=null||text!=''){
        $("#loading p").text(text);
    }
    $("#loading").css("display","block");
    $("#loading .loadingText").css({"top":top+"px","left":left+"px"});
}
//关闭loading
function hideLoading(){
    $("#loading").css("display","none");
    $("#loading p").text('');
}