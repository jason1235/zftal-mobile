<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %> 
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/hrm/head_v5.ini" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/amazeui.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/app.css">

<style type="text/css">
      .am-form-file {
		    position: relative;
		    overflow: hidden;
		}
		.am-form-group {
		    margin-bottom: 1.5rem;
		}
		
    </style>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
<title></title>

</head>
<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>

<div class="container_ydht sl_all_bg">

<form id="uploadForm" class="am-form am-margin" onsubmit="return doSubmit();" action="<%=request.getContextPath() %>/txl/txl_imp.html" target="resultFrame" method="post" enctype="multipart/form-data">
<div class="am-form-group am-form-file">
	<a alt="模板下载" href="<%=request.getContextPath() %>/upload/contacts.xls">黄页数据表格模板下载，请点击此处下载</a>
	
</div>
<div id="file-list">
   </div>
   <div class="toolbox">
          <div class="btn-group" id="but_ancd">
          <div style="width: 150px;;float: left;margin-left: 1px;">
          	<div class="am-form-group am-form-file" style="padding-left: 1px;">
			  <button type="button" class="btn btn-default" style="border-radius: 0;margin-left: -1px;">
			    <i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>
			  <input id="doc-form-file" name="file" type="file">
			</div>
          </div>
			   <button class="btn btn-default" type="button" value="上传" id="submit"/>上传</button>
				<input type="hidden" id="filename" name="filename"/>
				<button class="btn btn-default" type="button" value="预览" onclick="window.open('<%=request.getContextPath() %>/txl/txl_index.html');"/>预览</button>
			
			
          </div>
  </div>
  
  

<script>

function doSubmit() {
	var filename = $('.am-badge').html();
	$("#filename").val(filename);
	if(filename == null || filename == "" || filename==undefined){
		openAlert("请上传文件再点击上传！", "e");
		return false;
	}else if (/.*\.xls(x)?/.test(filename)) {
		return true;
	} else {
		openAlert("上传文件格式不对，您导入的不是excel文件，请导入excel文件！", "e");
		return false;
	}
}

  $(function() {
   $("#submit").click(function(){
	   var filename = $('.am-badge').html();
	   $("#filename").val(filename);
	   if(filename == null || filename == "" || filename==undefined){
		   openAlert("请上传文件再点击上传！", "e");
			return false;
		}else if (/.*\.xls(x)?/.test(filename)) {
			//return true;
		} else {
			openAlert("上传文件格式不对，您导入的不是excel文件，请导入excel文件！", "e");
			//alert("上传文件格式不对！");
			return false;
		}
      $.ajax({
    	    url: '<%=request.getContextPath() %>/txl/txl_imp.html',
    	    type: 'POST',
    	    cache: false,
    	    data: new FormData($('#uploadForm')[0]),
    	    processData: false,
    	    contentType: false
	    	}).done(function(data) {
	    		    var callback = function(){
						//reflashPage();
						location.reload();
					};
					if(data.success){
						openAlert(data.text, "s", "auto", callback);
					}else{
						openAlert(data.text, "e");
					}
	    	}).fail(function(data) {
	    		errorAlert("上传失败!");
	    	});
   		});
	  
    $('#doc-form-file').on('change', function() {
      var fileNames = '';
      var fname = $(this).val();
      var lindex = fname.lastIndexOf("/");
      var windex = fname.lastIndexOf("\\");
      var index = lindex > windex ? lindex : windex;
      fname = fname.substring(index+1);
        //$.each(this.files, function() {
        fileNames += '<span class="am-badge" style="color:#000000;background-color:white;">' + fname + '</span> ';
      //});
      $('#file-list').html(fileNames);
    });
  });
  
 
</script>



</form>
</div>
<div class="container_ydht sl_all_bg">

	<form action="<%=request.getContextPath() %>/txl/txl_upload.html" name="search" id="search" method="post">
		 <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">${commentsList[1].theVal }</label>
                        <div class="col-xs-7">
                            <input id="xtbm" maxlength="100" placeholder="最多100个字符" class="form-control" name="query.bm" value="${query.bm }"/>
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">${commentsList[2].theVal }</label>
                        <div class="col-xs-7">
                            <input id="xtbm" maxlength="100" placeholder="最多100个字符" class="form-control" name="query.ks" value="${query.ks }"/>
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-4">
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
                    </div>
                </div>
                
                
            </div>
          </div>
		
	<%--<div class="row sl_aff_btn">
              <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
              </div>
          </div>
          
          --%><table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                	<th style="width: 64px;">序号</th>
                	<s:iterator value="commentsList" var="commentMenu" status="st">
                		<th>${commentMenu.theVal }</th>
                	</s:iterator>
                    <%--<th>序号</th>
                    <th>部门</th>
                    <th>部门别称</th>
                    <th>科室</th>
                    <th>电话</th>
                    <th>传真</th>
                    <th>办公地点1</th>
                    <th>办公地点2</th>
                    <th>主要业务</th>
                --%></tr>
                
            </thead>
            <tbody  id="list_body">
                <s:iterator value="pageList" var="menu" status="st">
                <tr name="tr" class="data">
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut ">${menu.dh}</td>
                    <td class="autocut" >${menu.bm }</td>
                    <td class="autocut" >${menu.ks}</td>
                    <td class="autocut ">${menu.zyyw}</td>
                    <td class="autocut ">${menu.bgdd1}</td>
                    <td class="autocut" >${menu.bgdd2}</td>
                    <td class="autocut" >${menu.bmbc}</td>
                    <td class="autocut" >${menu.chuanzhen}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${pageList }" query="${query }" />
	</form>

</div>
<iframe name="resultFrame"></iframe>

<script type="text/javascript"> 
	function isMobile() {
        var agent = navigator.userAgent.toLowerCase();
        var patterns = ["android","iphone","windows phone","windows ce","ipad","ipod","rv:1.2.3.4","ucweb"];
        for (var i = 0; i < patterns.length; i++) {
            if (agent.indexOf(patterns[i]) > -1) {
                return patterns[i];
            }
        }
        return false;
    }
    
    function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false)
		}
	}
    
    
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title = '${title}';
    data.header.content.left = [{link:"javascript:goBack()",icon:"chevron-left",className:"back-button"}];
    //var html = template(data);
    //$tpl.before(html);
    var mobileDevice = isMobile();
    
    if (mobileDevice) {
    	
    }
    
 	function goBack() {
 		if (mobileDevice) {
 			if (mobileDevice === 'android') {
 				MobileJavaApi.Quit();
 			}
 		}
 		history.go(-1);
 	}
    
    connectWebViewJavascriptBridge(function(bridge) {
		var callbackButton = document.getElementsByClassName('back-button')[0]
		callbackButton.onclick = function(e) {
			e.preventDefault()
			bridge.callHandler('testObjcCallback', function(response) {
                               
			})
		}
	})
    
</script>	
</body>
</html>