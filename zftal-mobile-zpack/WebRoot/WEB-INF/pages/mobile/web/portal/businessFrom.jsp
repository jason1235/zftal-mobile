<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta chaset="UTF-8">
<%@ include file="/WEB-INF/pages/mobile/web/new/meta.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/global.css">
<title></title>
        <style type="text/css">
        .am-navbar-default .am-navbar-nav {
            /*background-color: #3c3c3c;*/
        }
        .am-offcanvas-bar {
            left: auto;
            right: 0;
            -webkit-transform: translateX(100%);
            -ms-transform: translateX(100%);
            transform: translateX(100%);
        }
        .am-offcanvas-bar:after {
            right: auto;
            left: 0;
        }
        .am-menu-nav {
            width: 33.33333333%;
        }

    </style>
</head>
<body>
	<script type="text/x-handlebars-template" id="amz-tpl">
	<div data-am-sticky="" class="" style="margin: 0px;">
    <%--
			
			<c:if test="${type == '1' }" >
				<a href="<%=request.getContextPath() %>/portal/portal_cardlist.html?classId=cardlist&type=1" class="back">
          	</c:if>
          	
          	<c:if test="${type == '2' }" >
				<a href="<%=request.getContextPath() %>/portal/portal_cardlist.html?classId=cardlist&type=2" class="back">
          	</c:if>
    --%>      	
          	<c:if test="${type != '1' && type != '2'}" >
          		<header class="header">
              <a href="<%=request.getContextPath() %>/portal/portal_cardlist.html" class="back">
    返回
              </a>
    <div class="header_title">流水及账单</div>
    
      </header>
          	</c:if>
    
                
	</div>  
<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
<input type="hidden" value="${type}" id="type" name="type"/>
		 <div class="am-list-news am-list-news-default" >
        <ul class="am-list am-list-static">
            <li>



                <div>
                    <input id="mydate" type="text" class="am-form-field" data-am-datepicker="{format: 'yyyy-mm', viewMode: 'months', minViewMode: 'months'}" placeholder="请选择时间" data-am-datepicker readonly/>
                </div>


            </li>
            <li>
                <table border="0" width="100%">
                    <tr valign="center">
                        <td align="right" style="font-size:1.6rem;font-weight:bold;color:#CD2626;">支出(元)</td>
                        <td width="15px"></td>
                        <td align="left" style="font-size:2.0rem;font-weight:bold;color:#CD2626;">
							<fmt:formatNumber value="${totalOut}" pattern="0.00"/>
							
 						</td>
                        <td align="right" style="font-size:1.6rem;font-weight:bold;color:#556B2F;">存入(元)</td>
                        <td width="15px"></td>
                        <td align="left" style="font-size:2.0rem;font-weight:bold;color:#556B2F;">
							<fmt:formatNumber value="${totalIn}" pattern="0.00"/>
							
						</td>
                    </tr>
                </table>
            </li>
        </ul>
    </div>

	<div class="am-list-news am-list-news-default" >
        <ul class="am-list am-list-static">

			<c:forEach items="${businessList }" var="business">
					<li class="am-g am-list-item-desced">
                		<div style="float:left;">
							<c:if test="${business.classOutIn eq 'in'}" >
                    			<h3 class="am-list-item-hd" style="font-size:1.6rem;font-weight:bold;color:#556B2F;">
										${business.classSh}-
											存入
								</h3>
							<div class="am-list-item-text" style="font-size:1.3rem;font-weight:bold;color:#556B2F;">
								<fmt:formatDate value="${business.classJysj}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</div>
							</c:if>
							<c:if test="${business.classOutIn eq 'out'}" >
								<h3 class="am-list-item-hd" style="font-size:1.6rem;font-weight:bold;color:#CD2626;">
										${business.classSh}-
											支出
								</h3>
							<div class="am-list-item-text" style="font-size:1.3rem;font-weight:bold;color:#CD2626;">
								<fmt:formatDate value="${business.classJysj}" pattern="yyyy-MM-dd HH:mm:ss"/>
							</div>
							</c:if>
                    		
                		</div>
						
               			 <div style="float:right; " align="right">
                    	<div class="am-list-item-hd"></div>
						<c:if test="${business.classOutIn eq 'in'}" >
                    		<div class="am-list-item-hd" style="font-size:1.6rem;font-weight:bold;color:#556B2F;">
								金额:<fmt:formatNumber value="${business.classJye}" pattern="0.00"/>
							</div>
						</c:if>
						<c:if test="${business.classOutIn eq 'out'}" >
							<div class="am-list-item-hd" style="font-size:1.6rem;font-weight:bold;color:#CD2626;">
								金额:<fmt:formatNumber value="${business.classJye}" pattern="0.00"/>
							</div>
						</c:if>
                		</div>
           			 </li>
			</c:forEach>

            


        </ul>
    </div>
<c:if test="${ type != '1' && type != '2'}" >
    {{>navbar navbar}}
</c:if>
--</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script>
/**
网页中的JS调用IOS客户端函数，需要在网页的JS代码中添加如下函数：
*/
function connectWebViewJavascriptBridge(callback) {
		if (window.WebViewJavascriptBridge) {
			callback(WebViewJavascriptBridge)
		} else {
			document.addEventListener('WebViewJavascriptBridgeReady', function() {
				callback(WebViewJavascriptBridge)
			}, false)
		}
	};
	$(function() {
		var type = '${type}';
		if(type == '2'){
		   			connectWebViewJavascriptBridge(function(bridge) {
					var callbackButton = document.getElementById('buttons');
					callbackButton.onclick = function(e) {
						e.preventDefault();
						bridge.callHandler('testObjcCallback', function(response) {
			                               
					});
				}
			});
	   		
	   	}
		$('#mydate').val('${dateTime}');	
        $('#mydate').datepicker().
                on('changeDate.datepicker.amui', function(event) {

                    $(this).datepicker('close');
                    var dateTime = event.date;
                    //alert(dateTime);
                    var  type   = $("#type").val();
                    window.location.href = "<%=request.getContextPath()%>/portal/portal_businessFrom.html?date="+dateTime+"&type="+type;

                });


    });
	/**
	网页中的JS调用android客户端函数，只需在网页的JS代码中直接调用客户端提供的调用函数即可，网页例子如下所示
	*/
	function ShowAlert()
	{
	 MobileJavaApi.ShowAlertQuit("弹出消息提示");
	};
	function Quit()
	{
		MobileJavaApi.Quit();
	};
	
	function GoBack()
	{
		MobileJavaApi.GoBack();
	};
    //注册一个比较大小的Helper,判断v1是否大于v2
    Handlebars.registerHelper('compare',function(v1,v2,options){
        if (v1 > v2) {
            return options.fn(this);
        }  else {
            return options.inverse(this);
        }
    });

    //注册一个比较的Helper,判断v1是否等于v2
    Handlebars.registerHelper('eq',function(v1,v2,options){
        if (v1 == v2) {
            return options.fn(this);
        }  else {
            return options.inverse(this);
        }
    });

    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title="流水及账单";
    data.header.content.left=[
                    {
                        "link" : "<%=request.getContextPath() %>/portal/portal_cardlist.html",
                        "icon" : "chevron-left"
                    }
                ];
    var html = template(data);
    $tpl.before(html);
</script>	
</body>
</html>