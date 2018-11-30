<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 1.0 Transitional//EN">
<html>
  <head>
    <title></title>
	<jsp:include page="/common/ini/head.ini"/>
  </head>
  <body>
    <script type="text/javascript" src="<%=request.getContextPath() %>/common/js/My97DatePicker/WdatePicker.js"></script>
  <script language="javascript">
      $(function(){
         $("#start").val($("#startid").val());
         $("#end").val($("#endid").val());
         $("input[type='radio']").each(function(){
             if($(this).val()==$("#errorsid").val()){
             $(this).attr("checked","checked");
             }
         })
       })
function viewDetail(id_batch){
	tipsWindown("详细日志","url:POST?<%=request.getContextPath() %>/webservice/wsjhconfig!viewDetailLog?wsjhrzb.id="+id_batch,"670","360","true","","true");
}
function setRowId(jhxh){
	$(".current").removeClass("current");
	$("#"+jhxh).addClass("current");
}
        function query(){
         $("#startid").val($("#start").val());
         $("#endid").val($("#end").val());
         $("#errorsid").val($("input[@name='err']:checked").val());
	     $('#wsjhRzb_form_id').submit();
}
</script> 
    <!-- 多功能操作区一 -->
	<!-- From内容 start-->
	<!---------------------表格--有复选框的数据表单------------------->
	  <div class="searchtab">
	    <table  width="100%" border="0">
	    	<tbody>

					<tr>
						<th>
							时间从
						</th>
						<td>
							<input type="text" name="start" id="start" class="Wdate"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'end\')||\'%y-%m-%d\'}'})" />
						</td>
						<th>
							到
						</th>
						<td>
							<input type="text" name="end" id="end" class="Wdate"
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'start\')}',maxDate:'%y-%m-%d'})" />
						</td>
							<th>
							只显示错误记录
						</th>
						<td colspan="3">
							<input name="err" type="radio" value="1" />
							是
							<input name="err" type="radio" value="0" />
							否
						</td>
						<td>
                  <div class="btn">
                    <button class="button" name="查询" onclick='query()' >查 询</button>
                  </div></td>
					</tr>
				</tbody>
	    </table>
	  </div>
	<div class="formbox">
	<s:form action="webservice/wsjhconfig!viewLog" id='wsjhRzb_form_id' name="wsjhRzbAction"  method="post">
    	<s:hidden name="wsjhrzb.toPage" id="toPage" value="%{#resourceDO.toPage}" />
    	<s:hidden name="wsjhrzb.perPageSize" id="perPageSize" value="%{#resourceDO.perPageSize}"/>
    	<s:hidden name="wsjhrzb.wsjhxh" id="wsjhxh" value="%{#resourceDO.wsjhxh}"/>
    	  <s:hidden name="wsjhrzb.startDate" id="startid" value="%{#resourceDO.startDate}"/>
		  <s:hidden name="wsjhrzb.endDate" id="endid" value="%{#resourceDO.endDate}"/>
		  <s:hidden name="wsjhrzb.errors" id="errorsid" value="%{#resourceDO.errors}"/>
				<table summary="" class="datelist" align="center" width="100%">
					<thead>
						<tr>
					        <td>开始时间</td>
					        <td>结束时间</td>
					        <td>消耗时间(s)</td>
					        <td>读取数据</td>					        
					        <td>更新数据</td>
					        <td>错误数据</td>
					        <td>操作</td>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="4"></td>
						</tr>
					</tfoot>
					<tbody>
				    	<s:iterator value="#list" status="zt">				    		
				    		<tr onclick="setRowId('<s:property value="id"/>')" id='<s:property value="id"/>'>
				    			<td><s:property value="startDate"/></td>
						        <td><s:property value="endDate"/></td>
						        <td><s:property value="runTime"/></td>
						        <td><s:property value="readLines"/></td>
						        <td><s:property value="updateLines"/></td>
						        <td><s:property value="errors"/></td>
						        <td><s:if test="errors>0"><a style="color: blue;text-decoration:underline" href="javaScript:viewDetail('<s:property value="id"/>')" >查看</a></s:if></td>
				    		</tr>
				    	</s:iterator>
					</tbody>
				</table>
	  
	  <!--分页显示-->
	  <div class="footbox">
	    <div class="footbox_con">
	      <div class="pagination">
	        <div class="pageleft">
	          <!-- 全选反选位置 -->
	          <div class="choose">
	            
	           </div>

	        </div>
	        <div class="pageright">
	          <!-- 分页位置 -->
	          <div id="pagediv" class="paging"> 
	          	<!-- 分页器 -->
				<jsp:include flush="true" page="/common/page/pageNav.jsp">
					<jsp:param name="theFome" value="wsjhRzbAction"/>
				</jsp:include>
	          </div>
	        </div>
	      </div>
	    </div>
	  </div>
	  </s:form>
	</div>
  </body>
</html>