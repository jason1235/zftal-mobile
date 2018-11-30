var caches ={};
function thinkField(fieldClass,tableNameObj){
    $("."+fieldClass).each(function(i){
	        $(this).autocomplete({
	        source: function(request,response){
	            var key=$.trim(request.term);
	            var tableName=$(tableNameObj).val();
	            if(key!=""&&tableName!=""){
	               if(key+"_"+tableName in caches){
	                   response(caches[key+"_"+tableName]);
	                   }
	               $.ajax({
	                   type:'post',
	                   url:_path+'/bill/config_getFieldList.html',
	                   dataType:'json',
	                   data:'tableName='+tableName+"&fieldName="+key,
	                   cache:true,
	                   success:function(data){
	                       caches[key+"_"+tableName]=data;
	                       response(data);
	                       }
	                   });
	            }
	        },
	        minLength: 1, //触发条件，达到两个字符时，才进行联想
	        select: function( event, ui ) {
	            $(this).val(ui.item.fieldName);
	            return false;
	        }
	   }).data("ui-autocomplete")._renderItem = function( ul, item ) {
	         return $("<li>")
	           .append( "<a>" + item.fieldName + "," +item.fieldType +"("+item.fieldLen +")</a>" )
	           .appendTo( ul );
	   };
	});
}