$(function(){
	$(".YEAR").datepicker({
		changeMonth : false,
		changeYear : true,
		showOtherMonths : true,
		selectOtherMonths : true,
		dateFormat:"yy",
		buttonImageOnly: true
	});
	
	$(".YEAR_MONTH").datepicker({
		changeMonth : true,
		changeYear : true,
		showOtherMonths : true,
		selectOtherMonths : true,
		dateFormat:"yy-mm",
		buttonImageOnly: true
	});
	
	$(".DATE").datepicker({
		changeMonth : true,
		changeYear : true,
		showOtherMonths : true,
		selectOtherMonths : true,
		buttonImageOnly: true
	});
});
