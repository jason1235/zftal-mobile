/* 此JS提供日期(年,月,日),时间(时,分,秒)相关格式化操作的工具库.*/

// JS方法注释格式须包含以下几点说明：
// 1.方法的使用说明
// 2.参数说明(可选)
// 3.方法返回类型说明 (可选)
// 4.作者与时间
// 例如：

function showCalendar(id, format) {    
	if(format.toLowerCase()==="yyyy-mm-dd" || format.toLowerCase()==="y-mm-dd") {    
		WdatePicker({el:id,skin:'whyGreen',lang:'zh-cn'});   
	} else {   
		WdatePicker({el:id,skin:'whyGreen',lang:'zh-cn',startDate:'08:00:00',dateFmt:format});    
	}
}
//
function superQueryOnDateScope(itemCode){
	return showCalendar(itemCode,'yyyy-MM-dd');
}
//
function superQueryOnTimeScope(itemCode){
	return showCalendar(itemCode,'yyyy-MM-dd HH:mm:ss'); 
}