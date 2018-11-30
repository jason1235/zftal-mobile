<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@page import="com.zfsoft.hrm.infochange.action.InfoInputAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/date.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
        
        <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <style>
        .progressbar{background:repeat-x scroll #87CEFA;height:20px;}
    </style>
    <script type="text/javascript">
    $(function(){
	    $("button[name='search']").click(function(e){//搜索按钮
	    	reflashPage();
	        e.preventDefault();//阻止默认的按钮事件，防止多次请求
	    });
	    $(".btn_dc").click(function(e){
		    var str = $("#pClassId").serialize()+"&"+$("#pGuid").serialize()+"&"+$("#dqztm").serialize()
	        window.open("<%=request.getContextPath()%>/baseinfo/infoClassCheck_export.html?"+str);
		});
		$(".btn_down").click(function(e){
            var str = $("#pClassId").serialize()+"&"+$("#pGuid").serialize()+"&"+$("#dqztm").serialize()
            showWindow("数据导入", "<%=request.getContextPath()%>/baseinfo/infoClassCheck_upload.html?"+str, 480, 150 );
        });

	    $("#list_body tr").click(function(){
            var curclass = $(this).attr("class");
            if(curclass != null && curclass != '') {
                $(this).removeClass("current");
            }else{
                $(this).attr("class", "current");
            }
        });

	    $("#list_body tr").dblclick(function(){
            var id = $(this).attr("id");
            var classId = $(this).attr("classId");
            var editable=$("#editable").val();
            win(id,classId);
        });

	    var xmtrs=$("tr[name=tr]");
        for ( var i=0; i<xmtrs.length; i++){
            var xmtr = xmtrs[i];
            var tds = $(xmtr).closest("tr").find("td");
            var xmtd = tds[1];
            var id = $(xmtr).closest("tr").attr("id");
            var classId = $(xmtr).closest("tr").attr("classId");
            var html="<a style=\"color:#074695\" href=\"#\" onclick=\"win('"+id+"','"+classId+"')\" >"+$(xmtd).html()+ "</a>";
            $(xmtd).html(html);
        }

        fillRows("20", "", "", false);//填充空行
        $("#pClassId").val("${property.classId}");
    });
    function win(id,classId){
        showWindow("修改","<%=request.getContextPath()%>/normal/staffBatch_modify.html?classId="
                +classId+"&instanceId="+id+"&editable=couldModify", 750, '${230+heightOffset}');
    }
    
    function reflashPage(){
    	$("#search").attr("action","<%=request.getContextPath()%>/baseinfo/infoClassCheck_list.html");
        $("#search").attr("method","post");
        $("#search").submit();
    }

    function changeClazz(){
    	$.post("<%=request.getContextPath() %>/baseinfo/infoClassCheck_findInfoClass.html", $("#pClassId").serialize(), function(data){
        	var htmlStr="";
        	for(var i = 0 ;i<data.clazz.viewables.length;i++){
        		htmlStr+="<option value="+data.clazz.viewables[i].guid +">"+data.clazz.viewables[i].name+"</option>";
            }
    		$("#pGuid").html(htmlStr);
        }, "json");
    }
    
   	/*
	*排序回调函数
	*/
	function callBackForSort(sortFieldName,asc){
		$("#sortFieldName").val(sortFieldName);
		$("#asc").val(asc);
		$("#search").submit();
	}


	function requestProgress(){
        $("#windown-content").unbind("ajaxStart");
		$.ajax({type:"post",
			url:"<%=request.getContextPath()%>/baseinfo/infoClassData_process.html",
			success:function(data){
				if(data.success){
					$("#scroll").append(data.result.description);
					//var li = $("#scroll").find("li:last");
					$("#scroll").parent().scrollTop($("#scroll").height());
					if(data.progress != null){
						$("#progressTitle").text(data.progress.msg);
						$("#progressInfo").text(data.progress.percent);
						$("#progressbar").css("width",data.progress.percent);
					} 
					if(data.result.finish){//操作结束，出现关闭按钮
						$("#viewInfoWindow").fadeOut("normal",function(){
							$("#closeInfoWindow").fadeIn("normal");
						});
					}else{
						setTimeout("requestProgress()",200);//请求间隔200ms
					}
				}else{
					$("#scroll").append("<li><font color='red'>请求失败</font></li>");
				}						
			},
			datatType:"json",
			global:false
		});
    }
    
    function showProgress(){
        $("#scroll").empty();
        $("#progressTitle").empty();
        $("#progressInfo").empty();
        $("#viewInfoWindow").show();
        $("#closeInfoWindow").hide();
        $("#progressbar").css("width","0px");
        var left = ($(document).width()-600)/2;
        $("#tips").css({"left":left+"px","top":40+"px"});
        $("#tips").fadeIn("slow");
    }
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_dc" class="btn_dc">导 出</a>
                        </li>
                        <li>
                            <a id="btn_down" class="btn_down">数据导入</a>
                        </li>
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
     <form name="search" id="search" method="post" action="<%=request.getContextPath()%>/baseinfo/infoClassCheck_list.html">
     	 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
		 <input type="hidden" id="asc" name="asc" value="${asc}"/>
	     <div class="searchtab">
	        <table width="100%" border="0">
	          <tbody>
	            <tr>
	             <th width="10%">信息类</th>
	              <td width="20%">
	                <select id="pClassId" name="property.classId" style="width:160px" onchange="changeClazz()">
	                    <c:forEach items="${clazzList }" var="c">
	                    <option value="${c.guid }" >${c.name }</option>
	                    </c:forEach>
	                </select>
	              </td>
	              <th width="10%">字段</th>
	              <td width="20%">
	                <select id="pGuid" name="property.guid" style="width:160px" multiple="multiple" size="3">
	                    <c:forEach items="${clazz.viewables }" var="p">
	                    <c:if test="${!p.virtual}">
	                       <option value="${p.guid }" ${fn:contains(property.guid,p.guid)?'selected="selected"':'' }>${p.name }</option>
	                    </c:if>
	                    </c:forEach>
	                </select>
	              </td>
	              <th width="10%">当前状态</th>
                  <td width="12%">
                     <ct:codePicker name="dqztm" catalog="<%=ICodeConstants.STATUS %>" code="${dqztm }" width="100" />
                  </td>
	              <%--th width="10%">检查类型</th>
                  <td width="20%">
                  </td--%>
                  <td width="100px">
                    <div class="btn">
                      <button class="btn_cx" name="search" type="button">查 询</button>
                    </div>
                  </td>
                  <td>
                  </td>
	            </tr>
	          </tbody>
	        </table>
	      </div>
        <div class="formbox">
        <!--标题start-->
            <h3 class="datetitle_01">
                <span>${clazz.name }信息_${property.name}字段不全信息列表<font color="#0457A7" style="font-weight:normal;">  (提示：双击或者点击姓名可以对单条信息进行编辑。)</font></span>
                <input type="hidden" id=classId value="${classId }"/>
            </h3>
        <!--标题end-->
            <div class="con_overlfow">
                <table summary="" class="dateline tablenowrap" align="" width="100%">
                    <thead id="list_head">
                        <tr>
                            <td>序号 </td>
                            <c:if test="${'gh' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="gh">工号</td>
							</c:if>
							<c:if test="${'gh' != sortFieldName}">
							     <td class="sort_title" id="gh">工号</td>
							</c:if>
                            <c:if test="${'o.xm' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="o.xm">姓名</td>
							</c:if>
							<c:if test="${'o.xm' != sortFieldName}">
							     <td class="sort_title" id="o.xm">姓名</td>
							</c:if>
                            <c:forEach items="${clazz.viewables}" var="infoProperty">
                            <c:if test="${infoProperty.fieldName != 'gh' && infoProperty.fieldName != 'xm' && infoProperty.fieldType!='PHOTO' && infoProperty.fieldType!='IMAGE'}">
                                <c:if test="${infoProperty.fieldName eq sortFieldName}">
									<td class="sort_title_current_${asc }" id="${infoProperty.fieldName }" ${fn:contains(property.guid,infoProperty.guid)?'style="color:red;"':'' }>${infoProperty.name }
									</td>
								</c:if>
								<c:if test="${infoProperty.fieldName != sortFieldName}">
									<td class="sort_title" id="${infoProperty.fieldName }" ${fn:contains(property.guid,infoProperty.guid)?'style="color:red;"':'' }>${infoProperty.name }
									</td>
								</c:if>
                            </c:if>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody id="list_body">
                        <s:iterator value="list" var="bean" status="st">
                            <tr name="tr" id="${bean.values['globalid']}" classId="${bean.clazz.guid}">
                                <td>${st.index + query.startRow}</td>
                                <td>${bean.values['gh'] }</td>
                                <td>${bean.values['overall_xm'] }</td>
                                <c:forEach items="${clazz.viewables}" var="infoProperty">
                                <c:if test="${infoProperty.fieldName != 'gh' && infoProperty.fieldName != 'xm' && infoProperty.fieldType!='PHOTO' && infoProperty.fieldType!='IMAGE'}">
                                 <td>${bean.viewHtml[infoProperty.fieldName]}</td>
                                </c:if>
                                </c:forEach>
                            </tr>
                        </s:iterator>
                    </tbody>
                </table>
             </div>
             <ct:page pageList="${list }" query="${query }"/>
        </div>
      
      <div id="tips" style="position:absolute;z-index:2000;display:none;background: none repeat scroll 0 0 #ffffff">
    <div class="readme" style="margin:0px;" style="width:640px">
      <h2>操作信息</h2>
      <div style="height:370px;width:640px;overflow-x:hidden;overflow-y:auto;">
      <ul id="scroll">
      </ul>
      </div>
      <table style="width:640px;">
          <tr style="background:#E8F0FB;">
          <td width="70px">
          <div style="float:left;margin:5px">
            <font id="progressTitle"></font>
          </div>
          </td>
          <td width="400px">
          <div style="border: 1px solid #AAAAAA;background:#ffffff;height:20px">
            <div id="progressbar" class="progressbar" style="width:0px"></div>
          </div>
          </td>
          <td width="70px">
          <div style="float:left;margin:5px">
            <font id="progressInfo"></font>
          </div>
          </td>
          <td>
          <div style="float:right;margin:5px">
            <button id="closeInfoWindow" name="close">关     闭</button>
            <button id="viewInfoWindow" >处理中</button>
          </div>
          </td>
          </tr>
      </table>
    </div>
    </div>
    </form>
  </body>
</html>