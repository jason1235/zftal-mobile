$(function(){
	$(".sort_title_current_down").each(function(){
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
        $(this).append("<a class='Sort_down' id='_"+$(this).attr("id")
        		+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
	});
	$(".sort_title_current_up").each(function(){
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
        $(this).append("<a class='Sort_up' id='_"+$(this).attr("id")
        		+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
	});
	$(".sort_title,.sort_title_current_down,.sort_title_current_up").click(function(){
		var up="up";
		
		if($(this).find(".Sort_up").length>0){
			up="down";
		}
		if($(this).find(".Sort_down").length>0){
			up="up";
		}
		removeSortATag();
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
		$(this).append("<a class='Sort_"+up+"' id='_"+$(this).attr("id")+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
		callBackForSort($(this).attr("id"),up);
	});
});
function removeSortATag(){
	$(".Sort_down,.Sort_up").each(function(){
		var td=$(this).closest("td");
		var text = $(this).text();
		$(this).remove();
		var subTag = $(td).children();
        $(td).text(text).append(subTag);
	});
}

function sort(field,asc){
	if(field!=""){
		$("[id='"+field+"']").removeClass("sort_titlem_m");
	    $("[id='"+field+"']").attr("class", "sort_title_m_current_"+asc);
	}
	$(".sort_title_m_current_down").each(function(){
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
        $(this).append("<a class='Sort_down' id='_"+$(this).attr("id")
        		+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
	});
	$(".sort_title_m_current_up").each(function(){
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
        $(this).append("<a class='Sort_up' id='_"+$(this).attr("id")
        		+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
	});
	$(".sort_titlem_m,.sort_title_m_current_down,.sort_title_m_current_up").click(function(){
		var up="up";
		if($(this).find(".Sort_up").length>0){
			up="down";
		}
		if($(this).find(".Sort_down").length>0){
			up="up";
		}
		removeSortATag();
		var text=$(this).text();
		var subTag = $(this).children();
        $(this).empty().append(subTag);
		$(this).append("<a class='Sort_"+up+"' id='_"+$(this).attr("id")+"' title='三角向上的是升序，向下的是降序' href='#'>"+text+"</a>");
		callBackForSort($(this).attr("id"),up);
		return false;
	});
}
			