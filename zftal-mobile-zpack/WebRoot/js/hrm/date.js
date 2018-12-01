function dateInit(obj, format) {
	var fmt = "yy-mm-dd";
	if (format == "yyyy-MM") {
		fmt = "yy-mm";
	}
	if (format == "yyyy") {
		fmt = "yy";
	}
	$(obj).datepicker({
		changeMonth : true,
		changeYear : true,
		showOtherMonths : true,
		selectOtherMonths : true,
		dateFormat : fmt
	});
	$(obj).removeAttr("onmouseover");
	$(obj).unbind("mouseover");
}