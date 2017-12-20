
//创建柱状图
function creatChart_bar(source_dl_name,source_dl_count){
	var myChart = echarts.init(document.getElementById("chart_bar"),macarons);
	option = {
	    tooltip: {
	        trigger: 'axis'
	        ,axisPointer: {
	            type: 'none'
	        }
	    },
	    grid:{
	    	borderWidth:1,
	    	borderColor:"#e0e0e0"
	    },
	    xAxis: [
	        {
	            type: 'category',
	            splitArea:{show:false},
	            splitLine:{show:false},
	            axisLine:{show:false},
	            axisTick:{show:false},
	            axisLabel:{
	            	textStyle:{
	            		color:'#464646'
	            	}
	            },
	            data: source_dl_name
	        }
	       
	    ],
	    yAxis: [
	        {
	            type: 'value',
	            name: '',
	            splitArea:{show:false},
	            splitLine:{show:false},
	            axisLine:{show:false},
	            axisLabel:{
	            	textStyle:{
	            		color:'#464646'
	            	}
	            },
	            axisLabel: {
	                formatter: '{value}'
	            }
	        }
	    ],
	    series: [
	        {
	            name:'模板个数',
	            type:'bar',
	            barCategoryGap:'50%',
	            itemStyle: {
	                normal: {
	                	color:'#47c3fd',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: '{c}'
	                    }
	                }
	            },
	            data:source_dl_count
	        }
	    ]
	};
	myChart.setOption(option);
	myChart.on('click',function(param){
		var name = param.name;
		$("#sourceType").html(name);
		creatChart_rose(source_xl_arr[name]);
	});
}

function creatChart_rose(datalist){
	var myChart = echarts.init(document.getElementById("chart_rose"),macarons);
	option = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{b} <br/>{a} : {c}"
	    },
	    calculable : false,
	    series : [
	        {
	            name:'模板个数',
	            type:'pie',
	            radius : [20, 110],
	            center : ['50%', 200],
	            roseType : 'radius',
	            width: '40%',       // for funnel
	            max: 40,            // for funnel
	            itemStyle : {
	                normal : {
	                    label : {
	                    	formatter : "{b} : {c}"
	                    },
	                }
	            },
	            data:datalist
	        }
	    ]
	};
	myChart.setOption(option);
}