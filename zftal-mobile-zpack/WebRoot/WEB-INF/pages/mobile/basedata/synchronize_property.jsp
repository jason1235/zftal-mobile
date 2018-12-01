<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <script type="text/javascript">
      $(function(){
        //fillRows(15, '', '', false);
        
        $("input[name='chooseView']").click(function() {
          var check = "";
          if ($(this).is(':checked')) {
            check = "y";
          } else {
            check = "n";
          }
          var param = "vpModel.classId=" + $("#classId").val()
           + "&vpModel.propertyId=" + $(this).closest("tr").attr("id")
           + "&vpModel.viewStatus=" + check;

          modifyItem(param);
        });
        
        $("input[name='chooseCondition']").click(function() {
          var check = "";
          if ($(this).is(':checked')) {
            check = "y";
          } else {
            check = "n";
          }
          var param = "vpModel.classId=" + $("#classId").val()
           + "&vpModel.propertyId=" + $(this).closest("tr").attr("id")
           + "&vpModel.conditionStatus=" + check;
          
          modifyItem(param);
        });
        
        $("input[type='radio']").click(function(){
        	var lviewProperty =  $(this).val();
        	var propertyID  = $(this).closest("tr").find("input[name='propertyID']").val();
        	var infoClassID  = $("input[name='infoClassID']").val();
        	
        	$.post("<%=request.getContextPath() %>/basedata/basedata_changeShow.html",
        	"infoClassID="+infoClassID+"&propertyID="+propertyID+"&lviewProperty="+lviewProperty,function(data){
                if(data.success){
                	//location.reload();
                }else{
                   alert(data.text);
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
                	//location.reload();
                }else{
                   alert(data.text);
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
                	//location.reload();
                }else{
                   alert(data.text);
                }
            },"json");
            
        });
        
        //增加菜单
        $("#btn_zjcd").click(function() {
        	window.location.href = "<%=request.getContextPath() %>/basedata/basedata_addMenu.html?classId=${infoClass.guid}";
        });
        
        // 发布服务
        $("#btn_fbfw").click(function() {
          showWindow("发布服务", "<%=request.getContextPath()%>/basedata/basedata_publishService.html?classId=" + $("#classId").val(), 500, 400);
        });
        
        // 数字档案图标上传
        $("#btn_tb").click(function() {
          showWindow("个人档案图标上传", "<%=request.getContextPath()%>/basedata/basedata_publishPicture.html?classId=" + $("#classId").val(), 550, 400);
        });

      //监听增加
        $("#btn_zj").click( function(){
            addItem();
        });
       
        $(".btn_yl").click(function(){
       		var infoClassID = $("input[name='infoClassID']").val();
       		//alert(infoClassID);
       		window.open("<%=request.getContextPath() %>/basedata/basedata_clientBaseData.html?classId=${infoClass.guid}");
       		//showWindow("预览","<%=request.getContextPath() %>/basedata/basedata_clientBaseData.html?classId=${infoClass.guid}", 360, 500);
         });
         
       $("input[name='xxlmhcx']").click( function(){
       		var xxlmhcx = "n";
       		var infoClassID = $("input[name='infoClassID']").val();
       		if($(this).prop("checked"))
       			 xxlmhcx  = "y";
       		
       		$.post("<%=request.getContextPath() %>/basedata/basedata_changeShow.html",
        	"infoClassID="+infoClassID+"&xxlmhcx="+xxlmhcx,function(data){
                if(data.success){
                	//location.reload();
                }else{
                   alert(data.text);
                }
            },"json");
            
       });
       
       $("input[name='xxlall']").click( function(){
      		var xxladd = "n";
      		var infoClassID = $("input[name='infoClassID']").val();
      		if($(this).prop("checked"))
      			 xxladd  = "y";
      		
      		$.post("<%=request.getContextPath() %>/basedata/basedata_changeAll.html",
       	"infoClassID="+infoClassID+"&xxlall="+xxladd,function(data){
               if(data.success){
               	//location.reload();
               }else{
                  alert(data.text);
               }
           },"json");
           
      });
        var current = null;
      //监听单击行
        $("tbody > tr").click(function(){
            var guid = $(this).attr("id");
            
            if( guid == null )
            {
                return;
            }
            
            if(current != null) {
                current.removeClass("current");
            }

            $(this).attr("class", "current");

            current = $(this);
        });
 
      //上移
        $(".btn_sy").click( function(){
            if( current == null )
            {
                showWarning("请选需要移动记录！");
                return;
            }
            
            var prev = current.prev();
            var prevIndex = prev.find("td:first").text();

            if( prevIndex == "" )
            {
                showWarning("已经在第一行，无法上移！");
                return;
            }

            var guid = current.attr("id")
                    + ","
                    + prev.attr("id");

            $.post('<%=request.getContextPath() %>/basedata/infoproperty_swap.html', 'guid='+guid, function(data){
                if( data.success ) {
                    prev.find("td:first").text( current.find("td:first").text() );
                    current.find("td:first").text(prevIndex)
                    
                    current.after(prev);
                }
            }, "json")
            
        });

        //下移
        $(".btn_xy").click( function(){
            if( current == null )
            {
                showWarning("请选需要移动记录！");
                return;
            }
            
            var next = current.next();
            var nextIndex = next.find("td:first").text();

            if( $.trim(nextIndex) == "" )
            {
                showWarning("已经在最后一行，无法下移！");
                return;
            }

            var guid = current.attr("id")
                    + ","
                    + next.attr("id");

            $.post('<%=request.getContextPath() %>/basedata/infoproperty_swap.html', 'guid='+guid, function(data){
                if( data.success ) {
                    next.find("td:first").text( current.find("td:first").text() );
                    current.find("td:first").text(nextIndex)
                    
                    current.before(next);
                }
            }, "json")

        });

        $("a[name='modify']").click(function(){//行数据修改链接
            var guid = $(this).closest("tr").attr("id");
            modifyItem(guid);
        });
        $("a[name='del']").click(function(){//行数据删除链接
            var guid = $(this).closest("tr").attr("id");
            removeItem(guid);
        });

      })
      
      // 修改属性
      function modifyItem(param) {
        $.post('<%=request.getContextPath() %>/basedata/basedata_modifyViewPro.html', param, function(data){
          var callback = function(){
            $("form:first").submit();
          };
          
          processDataCall(data, callback);
        }, "json");
      }

    //增加信息类属性
      function addItem()
      {
          showWindow("增加","<%=request.getContextPath() %>/basedata/infoproperty_edit.html?classId=${infoClass.guid}", 720, 360);
      }
    //修改信息类属性
      function modifyItem(guid)
      {
          showWindow("修改","<%=request.getContextPath() %>/basedata/infoproperty_edit.html?guid="+guid, 720, 360);
      }

      //删除信息类属性
      function removeItem(guid)
      {
          showConfirm("确定要删除吗？");

          $("#why_cancel").click(function(){
              divClose();
          })

          $("#why_sure").click(function(){
              $.post('<%=request.getContextPath() %>/basedata/infoproperty_remove.html', 'guid='+guid, function(data){
                  var callback = function(){
                      $("form:first").submit();
                  };
                  
                  processDataCall(data, callback);
              }, "json");
          })
      }

    </script>
    <style type="text/css">
    .link{
    color: #1c69b6 !important;
    text-decoration: underline;
    }
    </style>
  </head>

  <body>
    <input  type="hidden" name="infoClassID" value="${infoClass.guid }" />
    <div class="formbox">
      <div class="toolbox">
        <div class="buttonbox">
          <ul>
              <li><a id="btn_zj" href="#" class="btn_zj">增加</a></li>
              <li><a id="btn_sy" href="#" class="btn_sy">上移</a></li>
              <li><a id="btn_xy" href="#" class="btn_xy">下移</a></li>
              <li><a id="btn_fbfw" class="btn_up">发布服务</a> </li>
              <li><a id="btn_zjcd" href="#" class="btn_zj">增加菜单</a></li>
              <c:if test="${isOrNot eq '1'}">
              <li><a id="btn_tb" href="#" class="btn_up">个人档案图标</a></li>
              </c:if>
              <li><a id="btn_ylxxl" href="#" class="btn_yl">预览</a></li>
              <li><input type="checkbox" name="xxlmhcx" value="y" <c:if test="${isOrNotLike eq 'y'}">checked="checked"</c:if> />模糊查询</li>
              <li><input type="checkbox" name="xxlall" value="y" <c:if test="${xxlall eq 'y'}">checked="checked"</c:if> />全部查看</li>
          </ul>
        </div>
      </div>
      <h3 class="datetitle_01">
        <span>${infoClass.name }属性信息</span>
      </h3>
      
      <div class="con_overlfow">
      <table summary="" class="dateline tablenowrap" align="" width="100%"  id="MyTable">
        <thead id="list_head">
          <tr>
            <td class="sort_title">序号</td>
            <td class="sort_title">属性名称</td>
            <td class="sort_title">字段名称</td>
            <td class="sort_title">可显示</td>
            <td class="sort_title">可编辑</td>
            <td class="sort_title">必填</td>
            <td class="sort_title">列表显示方式</td>
            <td class="sort_title">是否查询条件</td>
            
          <c:forEach items="${menuList }" var="menu" >
           	<td class="sort_title">${menu.menuName }</td>
          </c:forEach>
            
            <td class="sort_title">操作</td>
          </tr>
        </thead>
        <tbody id="list_body">
          <c:forEach items="${infoClass.properties }" var="bean" varStatus="i">
          <tr id="${bean.guid }">
	          
	          
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
              	<c:if test="${bean.viewable eq true}">
              		<input type="checkbox" name="conditionStatus" value="y" <c:if test="${vpro[bean.guid].conditionStatus eq 'y'}">checked="checked"</c:if> /> 是
              	</c:if>
            </td>
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
            <td >
                <div>               
                   <a name="modify" href="#" class="link">修改</a>       
                   <c:if test="${bean.sourceInit eq false}">
                   <a name="del" href="#" style="padding-left: 1px;" class="link">删除</a>
                   </c:if>
                </div>
            </td>
          </tr>
          </c:forEach>
        </tbody>
      </table>
      </div>
    </div>
  </body>

</html>