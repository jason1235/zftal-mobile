<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<%@ include file="/commons/hrm/head_v5.ini" %>
<style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    var AppFwList = ${AppFwList};
    $(function(){
    	var initialssywxt = $("#ssywxt").val();
    	var initialfwbm = '${model.classFwbm}';
    	var fwbmhtml = "<select name=\"model.classFwbm\" class=\"form-control\" id=\"fwcode\">";
    	var hasornot = false;
    	for(var i = 0 ; i < AppFwList.length; i++){
    		if(initialfwbm == AppFwList[i].fwbm){
    			hasornot = true;
    			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\" selected=\"selected\">"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
    		}else if(AppFwList[i].ssywxt == initialssywxt.trim()){
    			hasornot = true;
    			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\">"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
    		}
    	}
    	fwbmhtml += "</select>";
    	$("#apptypeselect").empty();
    	if(hasornot){
	    	$("#apptypeselect").append(fwbmhtml);
    	}else{
    		$("#apptypeselect").append("暂无此业务系统对应的app服务");
    	}
        var fwlx = $("#fwlx").find("option:selected").val();
        var zxfwlx = '${model.classFwlx}';
        if(fwlx == "WEB_SERVICE"){
        	  $("#isSignal").show(); 
              $("#bhlj").show(); 
              $("#yyfwdz").hide();
              $("#choice").show();
              $("#procode").show();
              $("#signalXtbm").show();
              $("#fwljmc").empty();
              $("#fwljmc").append("<span class='red'>*</span>服务地址");
              $("#file").hide();
              ssywxtChange($("#ssywxt"));
           }
           if(zxfwlx == "NEWS_APPLICATION"){
        	  $("#isSignal").hide(); 
              $("#bhlj").hide(); 
              $("#yyfwdz").hide();
              $("#choice").hide();
              $("#procode").hide();
              $("#signalXtbm").hide();
              $("#file").hide();
              $("#fwljmc").empty();
              $("#fwljmc").append("<span class='red'>*</span>服务链接");
           }
           if(fwlx == "APP_SERVICE"){
        	  $("#isSignal").hide(); 
              $("#bhlj").hide();
              //$("#bhlj").show();
              $("#choice").hide();
              $("#procode").hide();
              $("#signalXtbm").hide();
              $("#yyfwdz").hide();
              $("#fwljmc").empty();
              $("#file").hide();
              $("#fwljmc").append("<span class='red'>*</span>模块ID");
           }
           if(fwlx == "APP_APPLICATION"){
        	  $("#isSignal").hide();
          	  $("#showway").show();
              $("#bhlj").hide(); 
              $("#yyfwdz").show(); 
              $("#choice").hide();
              $("#procode").hide();
              $("#signalXtbm").hide();
              $("#fwljmc").empty();
              $("#file").show();
              $("#fwljmc").append("<span class='red'>*</span>应用服务名称");
              $("#URLScheme").show();
              $("#URLiTunes").show();
           }
           
        var ylfwlx = '${model.classFwlx}';
		var code = '${model.classFwbm}';
		if(ylfwlx != '' && ylfwlx != 'APP_SERVICE'){
			$("#apptypetr").hide();
			
		}
		
		var op = '${op}';
		var modelfwtbid =  '${model.classFwtbid}';
		var modelfwtbdz =  '${model.classFwtbdz}';
		if(op!='add' && modelfwtbid == '' && modelfwtbdz != ''){
			$("#systemimage").show();
			$("#systemimageword").show();
			$("#myselfimage").hide();
			$("input[name='uploadMethod']").get(1).checked = true;
		}else{
			$("#systemimage").hide();
			$("#systemimageword").hide();
			$("#myselfimage").show();
			$("input[name='uploadMethod']").get(0).checked = true;
		}
		
		$("input[name='uploadMethod']").click(function(){
			uploadmethod = $(this).val();
			if(uploadmethod == "myself"){
				$("#systemimage").hide();
				$("#systemimageword").hide();
				$("#myselfimage").show();
			}else{
				$("#systemimage").show();
				$("#systemimageword").show();
				$("#myselfimage").hide();
			}
		});
		
		$(".iconclass").click(function(){
				$(this).find("input[name='icoPath']").attr("checked","checked");
		});	
			
        $("#fwlx").change(function(){
           var fwlx = $(this).find("option:selected").val();
           if(fwlx == "APP_SERVICE"){
				$("#apptypetr").show();
			}else{
				$("#apptypetr").hide();
			}
			
           if(fwlx == "WEB_SERVICE"){
        	  $("#isSignal").show();
           	  $("#showway").hide();
              $("#bhlj").show(); 
              $("#yyfwdz").hide();
              $("#choice").show();
              $("#procode").show();
              $("#signalXtbm").show();
              $("#fwljmc").empty();
              $("#fwljmc").append("<span class='red'>*</span>服务地址");
              $("#fwlj").val("");
              $("#file").hide();
              //$("#ssywxttd").empty();
              //$("#ssywxttd").append("<select id='ssywxt' name='model.classSsywxt' onchange='ssywxtChange(this);' value='${model.classSsywxt }' " + 
              //                      "style='width:126px;'> <c:forEach var='menu' items='${YewuList}' varStatus='st'>" + 
              //                      " <option value='${menu.classId}'  <c:if test = '${model.classSsywxt eq menu.classId}'>"+
              //                      " selected='selected'</c:if>>${menu.classXtmc}</option> </c:forEach> </select>");
              ssywxtChange($("#ssywxt"));
              $("#choicebm").val("");
              $("#URLScheme").hide();
              $("#URLiTunes").hide();
              //alert($("#ssywxt").val());
           }
           if(fwlx == "APP_SERVICE"){
        	   $("#isSignal").hide();
          	  $("#showway").hide();
              $("#bhlj").hide();
              //$("#bhlj").show();
              $("#yyfwdz").hide();
              $("#choice").hide();
              $("#procode").hide();
              $("#signalXtbm").hide();
              $("#file").hide();
              $("#fwljmc").empty();
              $("#fwljmc").append("<span class='red'>*</span>模块ID");
              $("#fwlj").val("");
              //$("#ssywxttd").empty();
              //$("#ssywxttd").append("<select id='ssywxt' name='model.classSsywxt' onchange='ssywxtChange(this);' value='${model.classSsywxt }' " + 
              //                      "style='width:126px;'> <c:forEach var='menu' items='${YewuList}' varStatus='st'>" + 
              //                      " <option value='${menu.classId}'  <c:if test = '${model.classSsywxt eq menu.classId}'>"+
              //                      " selected='selected'</c:if>>${menu.classXtmc}</option> </c:forEach> </select>");
              //alert($("#ssywxt").val());
              $("#choicebm").val("");
              $("#URLScheme").hide();
              $("#URLiTunes").hide();
           }
           if(fwlx == "APP_APPLICATION"){
        	   $("#isSignal").hide();
           	  $("#showway").show();
              $("#bhlj").hide(); 
              $("#choice").hide();
              $("#procode").hide();
              $("#signalXtbm").hide();
              $("#fwljmc").empty();
              $("#fwljmc").append("<span class='red'>*</span>应用服务名称");
              $("#fwlj").val("");
              $("#appyydz").val("");
              //$("#ssywxttd").empty();
              //$("#ssywxttd").append("第三方应用<input value='application' name='model.classSsywxt' readonly='readonly' type='hidden'/>");
              $("#choicebm").val("");
              $("#ios").prop("checked",true);
      		  $("#android").prop("checked",true);
      		  $(".iosmobile").show();
      		  $(".androidmobile").show();
              //$("#yyfwdz").show(); 
              //$("#file").show();
              //$("#URLScheme").show();
              //$("#URLiTunes").show();
           }
        }); 
        
        var serviceSource = $("input[name='model.classFwly']").val();
        if(serviceSource != undefined && serviceSource != "CUSTOM_SOURCE" && serviceSource != "INFOCLASS_SOURCE"){
           $("#fwlxxx").hide();
           $("#bhlj").hide();
           $("#choice").hide();
           $("#procode").hide();
           $("#signalXtbm").hide();
           $("#yyfwdz").hide();
        }
        if(serviceSource == "INFOCLASS_SOURCE"){
           $("#fwlxxx").show();
           $("#bhlj").show();
           $("#choice").show();
           $("#procode").show();
           $("#signalXtbm").show();
           $("#fwljmc").empty();
           $("#fwljmc").append("<span class='red'>*</span>服务地址");
           $("#procodebm").html("999");
        }
        
        var showList = '${model.classShowList}';
        $(".androidmobile").hide();
        $(".iosmobile").hide();
        if(showList.indexOf("1") > 0 && fwlx == "APP_APPLICATION"){
        	$(".androidmobile").show();
        }
        if(showList.indexOf("2") > 0 && fwlx == "APP_APPLICATION"){
        	$(".iosmobile").show();
        }
        $("input[name='model.classShowList']").click(function(){
        	 var iosval = $("#ios").is(":checked");
     		 var androidval = $("#android").is(":checked");
     		 if(iosval == true){
     		 	$(".iosmobile").show();
     		 }else{
     		 	$(".iosmobile").hide();
     		 }
     		 if(androidval == true){
     		 	$(".androidmobile").show();
     		 }else{
     		 	$(".androidmobile").hide();
     		 }
        });
        
        $("#save_btn").click(function(){
            
			var fwcode = $("#fwcode").val();
	        var fwmc=$("input[name='model.classFwmc']").val();
	        var fwlx = $("#fwlx").val();
	        var fwbm = $("#fwcode").val();
	        var fwlx = $("#fwlx").val();
	        
	        //var chinese = /^[0-9]*$/g;
			//var flag = (chinese.test(fwbm));
	        //if(fwbm==null || fwbm==''){
	        //    errorAlert("服务编码不能为空");
	        //    return false;
	        //}
			//if(!flag){
			//    errorAlert("服务编码只能为数字");
	        //    return false;
			//}
	        if(fwlx == 'APP_SERVICE' && (fwbm==null || fwbm=='')){
	            errorAlert("服务编码不能为空");
	            window.parent.scroll(0,0);
	            return false;
	        }
	        if(fwmc==null || fwmc==''){
	            errorAlert("服务名称不能为空");
	            window.parent.scroll(0,0);
	            return false;
	        }
	        if(fwlx==null || fwlx=='' ){
	            errorAlert("服务类型不能为空");
	            window.parent.scroll(0,0);
	            return false;
	        }
	        if(fwlx == "WEB_SERVICE" || fwlx == "NEWS_APPLICATION"){
	        		var fwlj=$("input[name='model.classFwlj']").val();
	              	if(fwlj==null || fwlj=='' ){
	            		errorAlert("服务地址或模块ID不能为空");
	            		window.parent.scroll(0,0);
	            		return false;
	        		}
	           }
	        if(fwlx == "APP_APPLICATION"){
        		  var iosval = $("#ios").is(":checked");
        		  var androidval = $("#android").is(":checked");
	              var appyydz=$("input[name='model.classAppyydz']").val();
	        		if(androidval == true && (appyydz==null || appyydz=='' )){
	            		errorAlert("您已选择此服务在安卓端显示，应用服务地址不能为空");
	            		window.parent.scroll(0,0);
	            		return false;
	        		}
        		  var apkfile = $("#fileHref").find("a").text();
        		  if(androidval == true && (apkfile == "")){
        		  		errorAlert("您已选择此服务在安卓端显示，请上传安卓apk包");
        		  		window.parent.scroll(0,0);
	            		return false;
        		  }
        		  var URLScheme = $("#iosURLScheme").val();
        		  if(iosval == true && (URLScheme==null || URLScheme=='' )){
	            		errorAlert("您已选择此服务在苹果端显示，URLScheme(ios第三方应用的标识符)不能为空");
	            		window.parent.scroll(0,0);
	            		return false;
	        		}
        		  var URLiTunes = $("#iosURLiTunes").val();
        		  if(iosval == true && (URLiTunes==null || URLiTunes=='' )){
	            		errorAlert("您已选择此服务在苹果端显示，iTunes的链接地址(ios第三方APP的链接地址)不能为空");
	            		window.parent.scroll(0,0);
	            		return false;
	        		}
        		   
	        }
	       
	        var url = "<%=request.getContextPath()%>/serviceManager/serviceManager_save.html";
				if(fwlx != 'APP_SERVICE'){
					if(ylfwlx == 'APP_SERVICE' || ylfwlx == ''){
						url += "?code=0";
					}
					if(ylfwlx != '' && ylfwlx != 'APP_SERVICE'){
						url += "?code="+code;
					}
				}
 			var androidStatus = $("#android").prop("checked");
           	var iosStatus     = $("#ios").prop("checked");
           	var showway = "";
           	if(androidStatus) showway+=",1";
           	if(iosStatus)  showway+=",2";
           	showway = showway.substr(1);
           	if(url.indexOf("?") > -1){
	           	url += "&showway="+showway;
           	}else{
           		url += "?showway="+showway;
           	};
            $.post(url,$("#form_edit").serialize() ,function(data){
                    if(data.success){
                    	window.location.href="<%=request.getContextPath()%>/serviceManager/serviceManager_list.html";
                        //$(".ymPrompt_close",window.parent.document).click();
                        //$("#search",window.parent.document).submit();
                    }else{
                        errorAlert(data.text);
                        window.parent.scroll(0,0);
                    }
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
        	//window.location.href="<%=request.getContextPath()%>/serviceManager/serviceManager_list.html";
            //modalClose();
            //return false;
            history.go(-1);
        });
        
        $(".close").click(function(){
        	//window.location.href="<%=request.getContextPath()%>/serviceManager/serviceManager_list.html";
            //modalClose();
            //return false;
            history.go(-1);
        });
        
        $("input[name='keyWord']").bind('input propertychange', function() {
			var keyWord = $(this).val();
			var imageName = $("input[name='imageName']").val();
			$.post("<%=request.getContextPath()%>/serviceManager/serviceManager_getImages.html",
					"keyWord="+keyWord+"&imageName="+imageName ,function(data){
                if(data.success){
                	console.log(data.imageHtml);
                	$("#imageUL").empty();
                	$("#imageUL").append(data.imageHtml);
                }else{
                    errorAlert(data.text);
                    window.parent.scroll(0,0);
                }
                        
        	},"json");
			return false;
		});
        
        $("#ssywxt").change(function(){
        	var afterssywxt = $("#ssywxt").val();
        	var fwbmhtml = "<select name=\"model.classFwbm\" class=\"form-control\" id=\"fwcode\">";
        	var ishas = false;
        	for(var i = 0 ; i < AppFwList.length; i++){
        		if(AppFwList[i].ssywxt == afterssywxt.trim()){
        			ishas = true;
	       			fwbmhtml += "<option value=\""+ AppFwList[i].fwbm +"\">"+ AppFwList[i].fwbm +"&nbsp;&nbsp;"+ AppFwList[i].fwmc +"</option>";
        		}
        	}
        	fwbmhtml += "</select>";
        	$("#apptypeselect").empty();
        	if(ishas){
    	    	$("#apptypeselect").append(fwbmhtml);
        	}else{
        		$("#apptypeselect").append("暂无此业务系统对应的app服务");
        	}
        });
        
    });
    
     
    
    function ssywxtChange(obj) {
      var fwlxval = $("#fwlx").val();
      if (fwlxval == "WEB_SERVICE") {
        var strHtml = "";
        var param = "model.classSsywxt=" + $(obj).val();
        $.ajax({
        url:"<%=request.getContextPath()%>/serviceManager/serviceManager_getProcode.html",
        type:"post",
        dataType:"json",
        data:param,
        success:function(data){
            if (data != "") {
                $("#procodebm").html(data);
            }
        }
        });
      }
    }
     </script>
</head>
<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
<form action="<%=request.getContextPath()%>/serviceManager/business_save.html" name="search" id="form_edit" method="post">
    <div class="sl_mod modal-content">
        <div class="modal-header">
            <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <h4 class="modal-title text-left" id="myModalLabel">服务编辑</h4>
        </div>
        <div class="modal-body">
        <jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
             <tbody>
             <tr>
                        <th width="30%" >
                            <span class="red">*</span>是否公众服务 
                        </th>
                         
                        
                        <td>
	                        <select name="model.iscommon"  value="${model.iscommon }" class="form-control">
	                           <option value="0" <c:if test = "${model.iscommon eq 0}"> selected="selected"</c:if>>否</option>
                               <option value="1" <c:if test = "${model.iscommon eq 1}"> selected="selected"</c:if>>是</option>
                            </select>
                        </td>
              </tr>
              
              <tr>
                        <th width="30%" >
                            <span class="red">*</span>是否推荐位服务
                        </th>
                         
                        
                        <td>
	                        <select name="model.recommendFlag"  value="${model.recommendFlag }" class="form-control">
	                           <option value="0" <c:if test = "${model.recommendFlag eq 0}"> selected="selected"</c:if>>否</option>
                               <option value="1" <c:if test = "${model.recommendFlag eq 1}"> selected="selected"</c:if>>是</option>
                            </select>
                        </td>
              </tr>
              
             <tr id="fwlxxx">
                        <th width="30%">
                            <span class="red">*</span>服务类型
                        </th>
                         <td>
                         
                                <c:if test="${model.classFwlx =='NEWS_APPLICATION'}">
                                    资讯应用
                                    <input id="fwlx" value="${model.classFwlx }" name="model.classFwlx" value="WEB_SERVICE" type="hidden" />
                                </c:if>
                                <c:if test="${model.classFwlx !='NEWS_APPLICATION'}">
                                
                                
                                <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                                WEB服务
                                    <input id="fwlx" value="${model.classFwlx }" name="model.classFwlx" value="WEB_SERVICE" type="hidden" />
                                </c:if>
                                
                                <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
                                 <select id="fwlx" name="model.classFwlx"  value="${model.classFwlx }" class="form-control">
                                         <c:forEach var="menu" items="${ServiceTypeList}" varStatus="st">
                                            <c:if test="${menu.key !='NEWS_APPLICATION'}">
                                            <option value="${menu.key}" <c:if test = "${model.classFwlx eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                            </c:if>
                                         </c:forEach>
                                </select>
                                </c:if>
                                </c:if>
                        </td>
             </tr>
             <tr id="isSignal">
                        <th width="30%" >
                            <span class="red">*</span>是否单点服务
                        </th>
                         
                        
                        <td>
	                        <select name="model.isSignal"  value="${model.isSignal }" class="form-control">
	                           <option value="0" <c:if test = "${model.isSignal eq 0}"> selected="selected"</c:if>>否</option>
                               <option value="1" <c:if test = "${model.isSignal eq 1}"> selected="selected"</c:if>>是</option>
                            </select>
                        </td>
              </tr>
             <tr>
                        <th>
                            <span class="red">*</span>所属业务系统
                        </th>
                         <td id="ssywxttd">
                         
                            <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                                移动门户
                                    <input id="ssywxt" value="${model.classSsywxt }" name="model.classSsywxt"  type="hidden" />
                                </c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
                             <select id="ssywxt" name="model.classSsywxt"  value="${model.classSsywxt }" onchange="ssywxtChange(this);" class="form-control">
                                     <c:forEach var="menu" items="${YewuList}" varStatus="st">
                                        <option value="${menu.classId} "  <c:if test = "${model.classSsywxt eq menu.classId}"> selected="selected"</c:if>>${menu.classXtmc}</option>
                                     </c:forEach>
                             </select>
                         </c:if>
                        </td>
             </tr>
              <tr id="apptypetr">
                        <th width="30%" >
                            <span class="red">*</span>服务编码
                            <input value="${model.newsid }" name="model.newsid" type="hidden"/>
                        	<c:if test="${op!='add'}">
                            
                                <input value="${model.classId }" name="model.classId" type="hidden"/>
                                <input value="${model.classFwly }" name="model.classFwly" type="hidden"/>
                            </c:if>
                        </th>
                         
                        
                        <td id="apptypeselect">
	                        <%--<select name="model.classFwbm" class="form-control" id="fwcode">
	                        	<c:forEach var="fwbmunit" items="${fwbmList}">
	                        	 <option value="${fwbmunit.key }" <c:if test="${fwbmunit.key == model.classFwbm }"> selected="selected" </c:if>>${fwbmunit.key }&nbsp;&nbsp;${fwbmunit.text }</option>
	                        	</c:forEach>
	                        </select>
                        --%></td>
              </tr>
              <tr>
                        <th width="30%">
                            <span class="red">*</span>服务名称
                        </th>
                         <td>
                            <input id="fwmc" maxlength="10" placeholder="不要超过10个字哦(^_^)" name="model.classFwmc" type="text" value="${model.classFwmc }" class="form-control"/>
                        </td>
             </tr>
             <tr>
                        <th>
                                    服务描述
                        </th>
                         <td>
                         <textarea maxlength="500" placeholder="不要超过500个字符哦(^_^)" rows="4" cols="30" id="dwName" name="model.classFwms" value="${model.classFwms}" class="form-control">${model.classFwms}</textarea>
                        </td>
             </tr>
             
             <!--<tr>
                        <th>
                            <span class="red">*</span>所属产品
                        </th>
                         <td>
                            <c:forEach items="${ProductList}" var="li"> 
                                <input name="model.classSscp" type="checkbox" value="${li.key}" 
                                    <c:forEach items="${model.classSscpList}" var="cplx"> 
                                             <c:if test="${cplx eq li.key }">checked="checked"</c:if> 
                                    </c:forEach> 
                                />
                                ${li.text}
                            </c:forEach> 
                         </td>
             </tr>
              
             -->
             <tr id="showway" style="display: none;">
             			<th>
                            <span class="red">*</span>移动端是否显示
                        </th>
                         <td>
                         	<input type="checkbox" id="android" name="model.classShowList" value="1"  
                            <c:forEach items="${model.classShowList}" var="show"> 
                                        <c:if test="${show eq '1' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />android 
                                        
                                        <input type="checkbox" id="ios" name="model.classShowList" value="2"  
                            <c:forEach items="${model.classShowList}" var="show"> 
                                        <c:if test="${show eq '2' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />ios 
                                        
                                        <!--<input type="checkbox" id="weixin" name="mobile" value="3"  
                            <c:forEach items="${model.classShowList}" var="show"> 
                                        <c:if test="${show eq '3' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />微信 
                         -->
                         </td>
             </tr>
             <tr id="bhlj" style="display:none ">
                        <th id="fwljmc" >
                            
                        </th>
                         <td>
                         
                         
                         <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                                ${model.classFwlj }
                                    <input id="fwlj" maxlength="150" placeholder="不要超过150个字符哦(^_^)" value="${model.classFwlj }" name="model.classFwlj"  type="hidden" />
                                </c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
                             <input id="fwlj" maxlength="150" placeholder="不要超过150个字符哦(^_^)" name="model.classFwlj" type="text" value="${model.classFwlj }" class="form-control"/>
                         </c:if>
                         
                         
                             
                        </td>
             </tr>
             <tr id="yyfwdz" style="display:none " class="androidmobile">
                        <th >
                            <span class="red">*</span>安卓apk包名<br />
                            
                            <span><a href="<%=request.getContextPath() %>/upload/APKTool.rar">(获取包名工具下载链接--右边填写的包名需要通过上传apk到此工具获取)</a></span>
                        </th>
                        <td>
                        
                            <input id="appyydz" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.classAppyydz" type="text" value="${model.classAppyydz }" class="form-control"/>
                        </td>
             </tr>
             <tr id="file" style="display:none " class="androidmobile">
                        <th >
                            <span class="red">*</span>安卓apk文件上传
                        </th>
                         <td >
                         
                         ${fileHtml }
                             
                        </td>
             </tr>
             <tr id="URLScheme" style="display:none " class="iosmobile">
                        <th >
                            <span class="red">*</span>URLScheme<br />(ios第三方应用的标识符)
                        </th>
                         <td >
                         
                             <input id="iosURLScheme" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.iosURLScheme" type="text" value="${model.iosURLScheme }" class="form-control"/>
                        </td>
             </tr>
             <tr id="URLiTunes" style="display:none " class="iosmobile">
                        <th >
                            <span class="red">*</span>iTunes的链接地址<br />(ios第三方APP的链接地址)
                        </th>
                         <td >
                         
                             <input id="iosURLiTunes" maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.iosURLiTunes" type="text" value="${model.iosURLiTunes }" class="form-control"/>
                        </td>
             </tr>
             <tr id="signalXtbm" style="display:none ">
                        <th  >
                            <span class="red"></span>单点系统编码
                        </th>
                        <td >
							<input  maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="model.signalXtbm" type="text" value="${model.signalXtbm }" class="form-control"/>
						</td>
             </tr>
             <tr id="procode" style="display:none ">
                        <th  >
                            <span class="red"></span>PROCODE 编码
                        </th>
                        <td ><span id="procodebm"></span></td>
             </tr>
             <tr id="choice" style="display:none ">
                        <th >
                            <span class="red"></span>CHOICE 编码
    

                        </th>
                         <td >
                         
                         <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                                ${model.choice }
                                    <input id="choicebm" maxlength="32" placeholder="不要超过32个字符哦(^_^)" value="${model.choice }" name="model.choice"  type="hidden" />
                                </c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
                             <input id="choicebm" maxlength="32" placeholder="不要超过32个字符哦(^_^)" name="model.choice" type="text" value="${model.choice }" class="form-control"/>
                         </c:if>
                         
                         
                             
                        </td>
             </tr>
             <tr  style="max-height: 300px;">
                    <th >
                      <span class="red"></span>服务Logo图片(下面选择方式)<br />
                      <span>
                      	<br />
                      	<input type="radio" value="myself" name="uploadMethod"/>自定义上传<br /><br />
                      	<input type="radio" value="fromsomewhere" name="uploadMethod"/>系统中选择
                      </span>
                    </th>
                    <td >
                    <input type="hidden" name="imageName" value="${imageName }"/>
                    <div id="systemimageword">
                    	<input type="text" maxlength="20" placeholder="请输入需要查询的图标名称"  name="keyWord" class="form-control" value="${keyWord }" style="width: 100%;"/>
                    </div>
                    <div class="index_zjsy show-grid-15" style="height:400px; overflow:auto;border-style:solid;border-width:1px; border-color:#CCCCCC;margin-top: 5px;" id="systemimage">
							<ul class="list-unstyled " id="imageUL">
							
							<c:forEach var="icon" items="${iconList}" varStatus="st">
                            
                            <li class="col-md-3 col-sm-3 col-xs-3 iconclass" style="white-space:nowrap;text-overflow:ellipsis;overflow:hidden;">
                            <img style="height: 50px;width: 50px;" src="<%=request.getContextPath() %>/${icon}">
								<h5>
								<input type="radio" value="${icon}" name="icoPath" <c:if test = "${model.classFwtbdz == icon}"> checked="checked"</c:if>/>
								<c:forEach var="fileName" items="${fileNameList}" varStatus="stitem">
									<c:if test="${st.index == stitem.index}">
										${fileName }
									</c:if>
								</c:forEach>
								
								</h5>
							</li>
								
                            </c:forEach>
                                         
								
							</ul>
							</div>
							<div  style="height:300px; " id="myselfimage">
                      ${imageHtml }
                      </div>
                    </td>
             </tr>
             
              </tbody>
            </table>
        </div>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="save_btn">保 存</button>
            <button type="button" class="btn btn-primary" id="cancel">取 消</button>
        </div>
        <%--<div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
            <thead>
                    <tr>
                        <th colspan="2">
                            <span><font color="#0f5dc2" style="font-weight:normal;">服务编辑</font></span>
                        </th>
                    </tr>
            </thead>
                
             <tbody>
             <tr>
                        <c:if test="${op!='add'}">
                                <input value="${model.classId }" name="model.classId" type="hidden"/>
                        </c:if>
                        <c:if test="${op!='add'}">
                                <input value="${model.classFwly }" name="model.classFwly" type="hidden"/>
                        </c:if>
             </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>服务编码
                        </th>
                         <td width="8%" >
                            <c:if test="${op!='add'}">
                            ${model.classFwbm }
                                <input value="${model.classFwbm }" name="model.classFwbm"  type="hidden" />
                            </c:if>
                            <c:if test="${op == 'add'}">
                                <input id="fwbm" name="model.classFwbm" type="text" value="${model.classFwbm }" style="width:255px"/>
                            </c:if>
                        </td>
              </tr>
              <tr>
                        <th width="4%" >
                            <span class="red">*</span>服务名称
                        </th>
                         <td width="8%" >
                            <input id="fwmc" name="model.classFwmc" type="text" value="${model.classFwmc }" style="width:255px"/>
                        </td>
             </tr>
             <tr>
                        <th width="4%">
                            		服务描述
                        </th>
                         <td width="8%" >
                         <textarea rows="4" cols="30" id="dwName" name="model.classFwms" value="${model.classFwms}" style="width:255px">${model.classFwms}</textarea>
                        </td>
             </tr>
             <tr>
                        <th width="4%" >
                            <span class="red">*</span>所属业务系统
                        </th>
                         <td width="8%" id="ssywxttd">
                         
                         	<c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                         		移动门户
                         			<input id="ssywxt" value="${model.classSsywxt }" name="model.classSsywxt"  type="hidden" />
                         		</c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
	                         <select id="ssywxt" name="model.classSsywxt"  value="${model.classSsywxt }" onchange="ssywxtChange(this);" style="width:126px;">
	                       			 <c:forEach var="menu" items="${YewuList}" varStatus="st">
	                         		    <option value="${menu.classId} "  <c:if test = "${model.classSsywxt eq menu.classId}"> selected="selected"</c:if>>${menu.classXtmc}</option>
	                                 </c:forEach>
	          				 </select>
          				 </c:if>
                        </td>
             </tr>
             <tr>
                        <th width="4%">
                            <span class="red">*</span>所属产品
                        </th>
                         <td width="8%" >
                        	<c:forEach items="${ProductList}" var="li"> 
                        		<input name="model.classSscp" type="checkbox" value="${li.key}" 
	                         		<c:forEach items="${model.classSscpList}" var="cplx"> 
											 <c:if test="${cplx eq li.key }">checked="checked"</c:if> 
									</c:forEach> 
								/>
								${li.text}
							</c:forEach> 
                         </td>
             </tr>
              <tr id="fwlxxx">
                        <th width="4%">
                            <span class="red">*</span>服务类型
                        </th>
                         <td width="8%" >
                         
	                         	<c:if test="${model.classFwlx =='NEWS_APPLICATION'}">
	                            	资讯应用
	                            	<input id="fwlx" value="${model.classFwlx }" name="model.classFwlx" value="WEB_SERVICE" type="hidden" />
	                            </c:if>
                         		<c:if test="${model.classFwlx !='NEWS_APPLICATION'}">
                         		
                         		
                         		<c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                         		WEB服务
                         			<input id="fwlx" value="${model.classFwlx }" name="model.classFwlx" value="WEB_SERVICE" type="hidden" />
                         		</c:if>
                         		
                         		<c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
		                         <select id="fwlx" name="model.classFwlx"  value="${model.classFwlx }" style="width:126px;">
		                       			 <c:forEach var="menu" items="${ServiceTypeList}" varStatus="st">
		                       			 	<c:if test="${menu.key !='NEWS_APPLICATION'}">
		                         		    <option value="${menu.key}" <c:if test = "${model.classFwlx eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
		                                 	</c:if>
		                                 </c:forEach>
		           				</select>
		           				</c:if>
	                            </c:if>
                        </td>
             </tr>
             <tr id="bhlj" style="display:none ">
                        <th width="4%" id="fwljmc" >
                            
                        </th>
                         <td width="8%" >
                         
                         
                         <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                         		${model.classFwlj }
                         			<input id="fwlj" value="${model.classFwlj }" name="model.classFwlj"  type="hidden" />
                         		</c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
	                         <input id="fwlj" name="model.classFwlj" type="text" value="${model.classFwlj }" style="width:255px"/>
          				 </c:if>
                         
                         
	                         
                        </td>
             </tr>
             <tr id="yyfwdz" style="display:none ">
                        <th width="4%" >
                            <span class="red">*</span>应用服务地址
                        </th>
                         <td width="8%" >
                         
	                         <input id="appyydz" name="model.classAppyydz" type="text" value="${model.classAppyydz }" style="width:255px"/>
                        </td>
             </tr>
             <tr id="procode" style="display:none ">
                        <th width="4%" >
                            <span class="red"></span>PROCODE 编码
                        </th>
                        <td width="8%" ><span id="procodebm"></span></td>
             </tr>
             <tr id="choice" style="display:none ">
                        <th width="4%" >
                            <span class="red"></span>CHOICE 编码
                        </th>
                         <td width="8%" >
                         
                         <c:if test="${model.classFwly == 'INFOCLASS_SOURCE' }">
                         		${model.choice }
                         			<input id="choicebm" value="${model.choice }" name="model.choice"  type="hidden" />
                         		</c:if>
                         <c:if test="${model.classFwly != 'INFOCLASS_SOURCE' }">
	                         <input id="choicebm" name="model.choice" type="text" value="${model.choice }" style="width:255px"/>
          				 </c:if>
                         
                         
                             
                        </td>
             </tr>
             <tr>
		            <th width="4%">
		              <span class="red"></span>服务Logo图片
		            </th>
		            <td width="8%">
		              ${imageHtml }
		            </td>
          </tr>
             </tbody>
             <tfoot>
                    <tr>
                        <td colspan="2">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button" >取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div> --%>
</form>


</body>
</html>