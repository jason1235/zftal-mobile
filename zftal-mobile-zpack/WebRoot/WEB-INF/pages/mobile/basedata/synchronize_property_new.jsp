<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <style type="text/css">
      .sl_yx_con li a .modClass img{opacity:0;}
      .sl_yx_con li a .delClass img{opacity:0;}
      .sl_yx_con .infolcasCatalogMod{opacity:0;}
      .sl_yx_con .infolcasCatalogDel{opacity:0;}
      .sl_yx_con li a {padding: 3px 10px 3px 5px;border: none;background:#FFFFFF;text-decoration: none;}
      .sl_yx_con li .addInfoClass{padding: 3px 10px 3px 5px;border: 1px solid #ddd;background: #fff;text-decoration: none;}
      .red {color: red;}
    </style>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	$("#footTable").css('width','${footWidth}');
    	$("#tableHead").css('width','${footWidth}');
    	
    	
    	//监听增加
        $("#btn_zj").click( function(){
        	var infoClassID = $("input[name='infoClassID']").val();
        	var url = "<%=request.getContextPath() %>/basedata/infoproperty_editNew.html?classId="+infoClassID;
            $("#myModal_toji").find(".modal-content").width(750);
            $("#myModal_toji").find(".modal-content").height(430);
            $("#myFrame").height(430);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        });
    	
        $("#btn_xg").click( function(){
        	if($("input[name='id']:checked").length==0){
                openAlert("请先选中操作行！", "e");
                 return false;
            }
        	var guid = $("input[name='id']:checked").val();
        	var url = "<%=request.getContextPath() %>/basedata/infoproperty_editNew.html?guid="+guid;
            $("#myModal_toji").find(".modal-content").width(750);
            $("#myModal_toji").find(".modal-content").height(430);
            $("#myFrame").height(430);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
        	
        });
        
        $("#btn_sc").click( function(){
        	if($("input[name='id']:checked").length==0){
                openAlert("请先选中操作行！", "e");
                 return false;
            }
        	var guid = $("input[name='id']:checked").val();
        	openAlert("确定要删除此信息类属性吗？", "w");
        	$("#why_sure").click(function(){
        		$.post('<%=request.getContextPath() %>/basedata/infoproperty_remove.html', 'guid='+guid, function(data){
        			var callback = function(){
						//reflashPage();
						location.reload();
					};
                   	openAlert("成功删除信息类属性", "s", "auto", callback);
                }, "json");
        		
				
			});
        	
        });
        
        var current = null;
        //监听单击行
   
        //上移
          $("#btn_sy").click( function(){
        	  if($("input[name='id']:checked").length==0){
                  openAlert("请选需要移动的信息类属性记录！", "e");
                   return false;
              }
        	  current = $("input[name='id']:checked").closest("tr");
              var prev = current.prev();
              var prevIndex = prev.find("td:first").html();
              if( prevIndex == "" )
              {
            	  openAlert("已经在第一行，无法上移！", "e");
                  return;
              }
              var guid = current.attr("id")
                      + ","
                      + prev.attr("id");
              $.post('<%=request.getContextPath() %>/basedata/infoproperty_swap.html', 'guid='+guid, function(data){
                  if( data.success ) {
                      prev.find("td:first").html( current.find("td:first").html() );
                      current.find("td:first").html(prevIndex)
                      current.after(prev);
                      current.find("input[name='id']").prop("checked",true);
                  }
              }, "json")
              
          });

          //下移
          $("#btn_xy").click( function(){
        	  if($("input[name='id']:checked").length==0){
                  openAlert("请选需要移动的信息类属性记录！", "e");
                   return false;
              }
        	  current = $("input[name='id']:checked").closest("tr");
              
              var next = current.next();
              var nextIndex = next.find("td:first").html();
              //var nextIndex = next.html();

              if( $.trim(nextIndex) == "" )
              {
            	  openAlert("已经在最后一行，无法下移！", "e");
                  return;
              }
              var guid = current.attr("id")
                      + ","
                      + next.attr("id");
              $.post('<%=request.getContextPath() %>/basedata/infoproperty_swap.html', 'guid='+guid, function(data){
                  if( data.success ) {
                      next.find("td:first").html( current.find("td:first").html() );
                      current.find("td:first").html(nextIndex);
                      current.before(next);
                      current.find("input[name='id']").prop("checked",true);
                  }
              }, "json")

          });
          
        $("#btn_fbfw").click( function(){ 
        	
        	$.post("<%=request.getContextPath() %>/basedata/basedata_isPublishPicture.html","classId="+$("#classId").val(),function(data){
                if(data.success){
               		openAlert("该信息类已发布为服务，请去服务管理中进行修改及查看！", "e");
                }else{
	        		window.location.href = "<%=request.getContextPath()%>/basedata/basedata_publishService.html?classId=" + $("#classId").val();
                }
            },"json");
        });
        
        $("#btn_szda").click( function(){  
        	window.location.href = "<%=request.getContextPath()%>/basedata/basedata_publishPicture.html?classId=" + $("#classId").val();
        });
        
        $("#btn_zjcd").click( function(){  
        	window.location.href = "<%=request.getContextPath() %>/basedata/basedata_addMenu.html?classId=${infoClass.guid}";
        });
        
        $("#btn_yl").click(function(){
       		var infoClassID = $("input[name='infoClassID']").val();
       		window.open("<%=request.getContextPath() %>/basedata/basedata_clientBaseData.html?classId=${infoClass.guid}");
         });
    	
        $("#list_body tr").bind("click",function(e){
    	 	var check = $(this).find("input[name='id']").prop("checked");
    	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
    			if(!check){
    			   $(this).find("input[name='id']").prop("checked",true);
    			}
    	});
    	
    	$("#list_body tr").find("input[name='id']").bind("click",function(e){
    			var check = $(this).prop("checked");
         		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
    			if(!check){
    			   $(this).prop("checked",true);
    			}
    	 });
    	
    	$("input[type='radio']").click(function(){
        	var lviewProperty =  $(this).val();
        	var propertyID  = $(this).closest("tr").find("input[name='propertyID']").val();
        	var infoClassID  = $("input[name='infoClassID']").val();
        	
        	$.post("<%=request.getContextPath() %>/basedata/basedata_changeShow.html",
        	"infoClassID="+infoClassID+"&propertyID="+propertyID+"&lviewProperty="+lviewProperty,function(data){
                if(data.success){
                	//var callback = function(){
						//location.reload();
					//};
                	//openAlert("操作成功", "s","auto", callback);
                }else{
                	openAlert(data.text, "e");
                }
            },"json");
        });
        
        $("input[name='conditionStatus']").click(function(){
        	var Status =  $(this).prop("checked");
        	var conditionStatus = "n";
        	if(Status) 
        		conditionStatus="y";
        	var propertyID  = $(this).closest("tr").find("input[name='propertyID']").val();
        	var infoClassID  = $("input[name='infoClassID']").val();
        	
        	$.post("<%=request.getContextPath() %>/basedata/basedata_changeShow.html",
        	"infoClassID="+infoClassID+"&propertyID="+propertyID+"&conditionStatus="+conditionStatus,function(data){
                if(data.success){
                	//var callback = function(){
						//location.reload();
					//};
                	//openAlert("操作成功", "s","auto", callback);
                }else{
                	openAlert(data.text, "e");
                }
            },"json");
        });
        
        $("input[name='dviewProperty']").click(function(){
        	var propertyID  = $(this).closest("tr").find("input[name='propertyID']").val();
        	var infoClassID  = $("input[name='infoClassID']").val();
        	var dviewProperty = "";
        	
        	$(this).closest("tr").find("input[name='dviewProperty']").each(function(i){
        			if($(this).prop("checked"))
			   			dviewProperty += "," + $(this).val();
			 });
			 
			dviewProperty = dviewProperty.substr(1);
			
        	$.post("<%=request.getContextPath() %>/basedata/basedata_changeShow.html",
        	"infoClassID="+infoClassID+"&propertyID="+propertyID+"&dviewProperty="+dviewProperty,function(data){
                if(data.success){
                	//var callback = function(){
						//location.reload();
					//};
                	//openAlert("操作成功", "s","auto", callback);
                }else{
                	openAlert(data.text, "e");
                }
            },"json");
        });   

    });
    
    </script>
    </head>
  <body>
  <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
  	<div class="container sl_all_bg" style="width: 100%;">
  		<div class="toolbox">
        <div class="btn-group" id="but_ancd">
            <button type="button" class="btn btn-default" id="btn_zj">增加</button>
            <button type="button" class="btn btn-default" id="btn_xg">修改</button>
            <button type="button" class="btn btn-default" id="btn_sc">删除</button>
            <button type="button" class="btn btn-default" id="btn_sy">上移</button>
            <button type="button" class="btn btn-default" id="btn_xy">下移</button>
            <button type="button" class="btn btn-default" id="btn_fbfw">发布服务</button>
            <button type="button" class="btn btn-default" id="btn_zjcd">增加菜单</button>
            <button type="button" class="btn btn-default" id="btn_szda">个人档案图标</button>
            <button type="button" class="btn btn-default" id="btn_yl">预览信息类</button>
        </div>
    	</div>
  	<table id="tableHead" class="table table-striped sl_all_bg table-bordered text-left" style="margin-bottom: 0px;">
        <thead>
        <tr >
            <th  style="background-color: #f5f5f5;width: 100%;">
            <input  type="hidden" name="infoClassID" value="${infoClass.guid }" />
                <span id="infoClassname">${infoClass.name }</span>属性信息
            </th>
        </tr>
        </thead>
    </table>
    <div style="overflow-x: auto; width: 100%;">

    <table id="footTable" class="table table-striped sl_all_bg table-bordered text-left"  style="width: 1500px;margin-bottom: 0px;">

        <thead id="list_head">
            <tr>
                <th style="width:40px;">选择</th>
                <th style="width:40px;;">序号</th>
                <th style="width:90px;">属性名称</th>
                <th style="width:90px;">字段名称</th>
                <th style="width:60px;">可显示</th>
                <th style="width:60px;">可编辑</th>
                <th style="width:40px;">必填</th>
                <th style="width:130px;">列表显示方式</th>
                <th style="width:90px;">是否查询条件</th>
                <%--
                	<th style="width:90px;">操作</th>
                --%>
                <c:forEach items="${menuList }" var="menu" >
	           	<th class="sort_title" style="width: ${menu.menuLength}px;">${menu.menuName }</th>
	          </c:forEach>

            </tr>
        </thead>
        <tbody id="list_body" >
        	<c:forEach items="${infoClass.properties }" var="bean" varStatus="i">
        		<tr id="${bean.guid }">
        			<td ><input  type="checkbox" name="id" value="${bean.guid }" /></td>
        			<td >${i.index+1 }<input  type="hidden" name="propertyID" value="${bean.guid }" /></td>
        			<td >${bean.name }</td>
            <td >${bean.fieldName }</td>
            <td ><c:if test="${bean.viewable eq true}">是</c:if><c:if test="${!bean.viewable eq true}"><font class="red">否</font></c:if></td>
            <td ><c:if test="${bean.editable eq true}">是</c:if><c:if test="${!bean.editable eq true}"><font class="red">否</font></c:if></td>
            <td ><c:if test="${bean.need eq true}">是</c:if><c:if test="${!bean.need eq true}"><font class="red">否</font></c:if></td>
            <td >
              	<c:if test="${bean.viewable eq true}">
	              	<input type="radio" name="${i.index }+'title'" value="b" <c:if test="${vpro[bean.guid].lviewProperty eq 'b'}">checked="checked"</c:if> />标题 
					<input type="radio" name="${i.index }+'title'" value="c" <c:if test="${vpro[bean.guid].lviewProperty eq 'c'}">checked="checked"</c:if> />常规 
					<input type="radio" name="${i.index }+'title'" value="n" <c:if test="${vpro[bean.guid].lviewProperty eq 'n'}">checked="checked"</c:if> />不显示 
				</c:if>
            </td>
            <td >
              	<c:if test="${bean.viewable eq true and bean.fieldType ne 'DATE' and bean.fieldType ne 'MONTH' and bean.fieldType ne 'YEAR' }">
              		<input type="checkbox" name="conditionStatus" value="y" <c:if test="${vpro[bean.guid].conditionStatus eq 'y'}">checked="checked"</c:if> /> 是
              	</c:if>
            </td>
            <%--<td >
                <div>               
                   <a name="modify" href="#" class="link">修改</a>       
                   <c:if test="${bean.sourceInit eq false}">
                   <a name="del" href="#" style="padding-left: 1px;" class="link">删除</a>
                   </c:if>
                </div>
            </td>
            --%>
            <c:forEach items="${menuList }" var="menu" varStatus="i">
            <td >
            	<c:if test="${bean.viewable eq true}">
	            	<input type="checkbox" name="dviewProperty" value="${menu.classCdbm }"
	            			<c:forEach items="${vpro[bean.guid].dviewPropertyList }" var="menuItem" varStatus="i">
			           	 		<c:if test="${menuItem eq menu.classCdbm}">checked="checked"</c:if> 
			          		</c:forEach>
			          />勾选
		          </c:if>
            </td>
          	</c:forEach>
            
        		</tr>
        	</c:forEach>
        
        
        </tbody>

    </table>
    </div>
    
  </div>
  
    
    
	</body>
</html>