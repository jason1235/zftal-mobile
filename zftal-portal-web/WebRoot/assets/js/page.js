function initPageInput(obj,callback){
	//$(obj).unbind();
	$(obj).removeAttr("onmouseover");
	$(obj).keypress(function(e){
		if(e.keyCode==13){
			var pageNumber = $(obj).closest("form").find("#toPage").val();
			doPage(pageNumber,obj,callback);
			e.preventDefault();
		}
	});
}

function initPageAnchor(obj,callback){
	//$(obj).unbind();
	$(obj).removeAttr("onmouseover");
	$(obj).click(function(e){
		var pageNumber = $(obj).attr("alt");
		doPage(pageNumber,obj,callback);
	});
}

function doPage(pageNumber,obj,callback){
	var result = validatePageNumber(pageNumber,obj);
	if(result){
		if(callback !=null){
			callback();
		}else{
			$(obj).closest("form").submit();
		}
	}
}

function validatePageNumber(pageNumber,obj){
	var form = $(obj).closest("form");
	if(form.length==0){
		alert("请把翻页器置入表单内");
		return false;
	}
	if (!isNumber(pageNumber)) {
    	pageNumber = 1;
    }
	var totalPage = $(form).find("#totalPage").text();
	pageNumber = parseInt(pageNumber,10);
	totalPage = parseInt(totalPage,10);
    if (pageNumber > totalPage) {
        //alert("对不起，您不能够转到该页！");
        pageNumber = totalPage;
    } else if (pageNumber < 1) {
        pageNumber = 1;
    }
    if (parseInt(pageNumber,10) != pageNumber) {
        pageNumber = 1;
    }
    var pageSize = $(form).find("#pageSize").val();
    
    if(pageSize == undefined || !isNumber(pageSize)){
    	pageSize = $(form).find("#defaultPageSize").val();
    	$(form).find("#pageSize").val(pageSize);
    }
    $(form).find("#toPage").val(pageNumber);
    $(form).find("#toPage2").val(pageNumber);
    return true;
}

function isNumber( aaaa ) {
    var re=/^\d*$/;
    if (aaaa.match(re) == null)
	    return false;
    else
	    return true;
}