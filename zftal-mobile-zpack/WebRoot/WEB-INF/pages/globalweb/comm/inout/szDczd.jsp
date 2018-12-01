<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/validate.js"></script>
</head>
		<script type="text/javascript">
			
			function loadXz(){
				var array = $("input[name='dczdcbv']");
				var mrzd = document.getElementById("mrzd").value;
				var xzzd = document.getElementById("xzzd").value;
				var data = xzzd.split(",");
					$.each(array,function(j,n) {
						for(var i=0;i<data.length;i++){
							if(data[i]==$(array[j]).attr("value")){
								array[j].checked = true;
							}
						}
					});
			}
			
		function checAll(){
				var checs = document.getElementsByName('dczdcbv');
				var ch = document.getElementById('che');
				if(ch.checked){
					for(var i=0; i<checs.length; i++){
						if(!checs[i].checked){
							checs[i].checked = "checked"
						}
					}
					$('rev').checked="";
				}else {
					for(var i=0; i<checs.length; i++){
						if(checs[i].checked){
							checs[i].checked = ""
						}
					}
				}
		 	 }

 	 function revAll(){
 		var checs = document.getElementsByName('dczdcbv');
 		$('che').checked="";
		for(var i=0; i<checs.length; i++){
			checs[i].checked = (checs[i].checked ? "" : "checked");
		}
 	 }

		</script>

<s:form action="" method="post" theme="simple">
	<body onload="loadXz();">
     <input type="hidden" id="mrzd" name="mrzd" value="${mrzdmodel.zd}"/>  
     <input type="hidden" id="xzzd" name="xzzd" value="${xzzdmodel.zd}"/> 
	<input type="hidden" id="dcclbh" name="dcclbh" value="${model.dcclbh}"/> 
	 <div class="tab">
	  <table width="100%"  border="0" class=" formlist" cellpadding="0" cellspacing="0">
	    <thead>
	    	<tr>
	        	<th colspan="4"><span>数据字段</span></th>
	        </tr>
	    </thead>
	    <tfoot>
						<tr>
							<td colspan="4" class="noMove" id="footId">
								<div class="choose">
									<input name="全选" id="che" type="checkbox" value="全选" onclick="checAll();"/>
									全选
									<input name="反选" id="rev" type="checkbox" value="反选" onclick="revAll();" />
									反选
								</div>
								<div align="right">
								<button name="保存" class="" onclick="subForm('dc_bcZdsz.html');return false;">
									保 存
								</button>
								<button name="取消" class="" onclick="iFClose();return false;">
									关 闭
								</button>
								</div>
							</td>
						</tr>
					</tfoot>
	    <tbody>
	       <tr>
						<td width="100%">
						<s:iterator value="dczdList" id="s" status="substa">
									<div style="width:120px;float: left;">
										<input type="checkbox"  class="cbvclass" style="cursor: pointer;" id="dczdcbv" name="dczdcbv" value="${s.zd}" />
										${s.zdmc}
									</div>
									<s:if test="#substa.index != 0 && (#substa.index+1)%5==0">
										</td>
										</tr>
										<tr>
										<td colspan="3">
									</s:if>
								</s:iterator>
	       </td>
	        
	      </tr>
	    </tbody>
	  </table>
  </div>
  <s:if test="result != null && result != ''">
  	<script>
  		alert('${result}','',{'clkFun':function(){refershParent()}});
  	</script>
  </s:if>
</body>
</s:form>
</html>