<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <script type="text/javascript">
    	$(function(){
    		$("#list_body1 input:checkbox").click(function(){
        		if($(this).is(":checked")){
        			var id = $(this).attr("id");
        			$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").attr("checked","true");
        			setColor(id);
        			if(isAll()){
        				$("input:checkbox[id='checkAll']:not(:disabled)").attr("checked","true");
            		}
        		}else{
        			var id = $(this).attr("id");
        			$("#list_body1 input:checkbox[id^='"+id+"']:not(:disabled)").removeAttr("checked");
        			$("input:checkbox[id='checkAll']:not(:disabled)").removeAttr("checked");
        			removeColor(id);
        		}
        	});
    		$("#checkAll").click(function(){
    			checkAll();
    		});
    		$("#save").click(function(){
    			save();
    		});
    		$("span[name='list_ico']").click(function(){
				var id=$(this).closest("td").find("input[name='selectId']").attr("id");
				var tid='tr_'+id;
				if($(this).attr("opened") == 'false'){
					$("#list_body1 tr[pid='"+id+"']").css("display","");
					$(this).attr("opened","true");
				}
				else{
					$("#list_body1 tr[id^='"+tid+"'][id!='"+tid+"']").css("display","none");
					$(this).attr("opened","false")
				}
        	})

        	$("span").css("cursor","pointer");
    	});

    	function isAll(){
    		var size = $("#list_body1 input:checkbox:not(:disabled)[checked!='true']").size();
    		if(size == 0){
				return true;
        	}
    		return false;
    	}

    	function initColor(){
			var cks = $("#list_body1 input:checkbox");
			if(cks == null || cks.size() == 0){
				return;
			}
			for(var i = 0; i < cks.size(); i++){
				if($(cks[i]).is(":checked")){
					$(cks[i]).closest("td").find("font").attr("color","#0457A7");
					setColorForParent($(cks[i]).attr("id"));
				}
			}
        }

        function setColor(id){
        	$("#list_body1 tr[id^='tr_"+id+"']").find("font").attr("color","#0457A7");
			setColorForParent(id);
        }

        function removeColor(id){
        	$("#list_body1 tr[id^='tr_"+id+"']").find("font").removeAttr("color");
			removeColorForParent(id);
        }

        function setColorForParent(id){
            var pid = $("#tr_"+id).attr("pid");
            if(pid == null || pid == ''){
				return;
            }
        	if($("#tr_"+pid).find("font").attr("color") == null || $("#tr_"+pid).find("font").attr("color") == '' || $("#tr_"+pid).find("font").attr("color").toUpperCase() != '#0457A7'){
				$("#tr_"+pid).find("font").attr("color","#0457A7");
				setColorForParent(pid);
			}
        }

        function removeColorForParent(id){
        	var pid = $("#tr_"+id).attr("pid");
            if(pid == null || pid == ''){
				return;
            }
            var rpid = $("#tr_"+pid).find("input[name='selectId']").attr("id");
            if($("#list_body1 tr[pid='"+rpid+"']").find("font[color^='#04']").size() == 0){
            	$("#tr_"+pid).find("font").removeAttr("color");
            	removeColorForParent(pid);
            }
        }

        function checkAll(){
        	if($("#checkAll").is(":checked")){
        		$("#list_body1 input:checkbox:not(:disabled)").attr("checked","true");
        		$("font").attr("color","#0457A7");
        	}else{
        		$("#list_body1 input:checkbox:not(:disabled)").removeAttr("checked");
        		$("font").removeAttr("color");
        	}
        }

        function save(){
        	$.post(_path+'/manoeuvre/AuditConfigOrgInfo_save.html?query.aid=${query.aid}',$("#configs input:checkbox").serialize(),function(data){
        		var callback = function(){
        			$("#searchForm").submit();
        		};
        		processDataCall(data, callback);
        	},"json");
        }

        initColor();
    </script>
  </head>
  <body>
  	<input type="hidden" id="propertyName" name="propertyName"  value="dwm"/>
	<div class="formbox">
		<table width="100%" class="formlist" id="configs">
			<thead id="list_head1">
				<tr>
					<td width="60%">全选<input type="checkbox" id="checkAll"/>    部门名称 </td>
				    <td width="30%">部门代码</td>
				</tr>
			</thead>
			<tbody id="list_body1" style="text-align:left;">
				${orghtml }
			</tbody>
			<tfoot>
		      <tr>
		        <td colspan="2">
		          	<div class="btn">
		            	<button id="save" name="save" >保 存</button>
		            	<button name="cancel" onclick='divClose();'>取 消</button>
		          	</div>
		        </td>
		      </tr>
			</tfoot>
  		</table>
	</div>
</body>
</html>
