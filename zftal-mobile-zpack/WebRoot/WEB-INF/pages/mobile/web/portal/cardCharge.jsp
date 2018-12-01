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
          		<a onclick="GoBack()" class="back">
          	</c:if>
          	
          	<c:if test="${type == '2' }" >
          		<a id="buttons" class="back">
          	</c:if>
--%>          	
          	<c:if test="${type != '1' && type != '2'}" >
          		<header class="header">
              <a href="<%=request.getContextPath() %>/portal/portal_cardlist.html" class="back">

                     	返回
              </a>
    <div class="header_title">一卡通</div>
    
      </header>
          	</c:if>

</div>		
<c:if test="${type != '1' && type != '2'}" > 
<br><br>
 </c:if>
  <c:if test="${type == '1' || type == '2'}" >

</c:if>
<input type="hidden" value="${type}" id="type" name="type"/>
		<div class="am-list-news-default" >
        <ul class="am-list am-list-static">
            <li>
                <table border="0" width="100%">
                    <tr>
                        <td width="40px"></td>
                        <td>

							<c:forEach items="${bankCards }" var="bankCard">
	                        	<c:if test="${bankCard.classIscheck eq '1'}" >
                            			<div ><img src="<%=request.getContextPath()%>/assets/i/${bankCard.classSsyh}.jpg"/></div>

                            		<div style="font-size:1.3rem;color:#757575; padding-left:50px;">
										尾号<fmt:formatNumber type="number" pattern="##00" maxIntegerDigits="4" value="${bankCard.classYhkh}" />
									</div>
									
								</c:if>
                   			</c:forEach>
							
                        </td>
                        <td width="40px" align="center" rowspan="2">
                            <a id="choosecard" class="am-header-icon am-icon-chevron-right"></a>
                        </td>
                    </tr>
                </table>
            </li>
            <li>
                <div class="am-input-group">
                    <span class="am-input-group-label">金额(元)</span>
                    <input type="text" class="am-form-field">
                </div>
            </li>
        </ul>
    </div>
    <table border="0" width="100%">
        <tr><td align="center">
            <button type="button" class="am-btn am-btn-default" style="width:200px;">充值</button>
        </td></tr>
    </table>



	<div class="am-popup" id="my-popup">
        <div class="am-popup-inner">
            <div class="am-popup-hd">
                <h4 class="am-popup-title">选择支付</h4>
                <span data-am-modal-close class="am-close">&times;</span>
            </div>
            <div class="am-popup-bd">
                <div class="am-list-news-default" >
                    <ul class="am-list am-list-static">

							
						<c:forEach items="${bankCards }" var="bankCard">
							<li>
                            <table border="0" width="100%">
                                <tr>
                                    <td>

                            			<div style="font-size:1.3rem;color:#757575;"><img src="<%=request.getContextPath()%>/assets/i/${bankCard.classSsyh}.jpg"/>
											尾号<fmt:formatNumber type="number" pattern="##00" maxIntegerDigits="4" value="${bankCard.classYhkh}" />
										</div>
										
									</td>

									<td width="40px" align="center">
										<c:if test="${bankCard.classIscheck eq '1'} ">
                                        	<i class="am-icon-check" style="color:#0e90d2;"/>
										</c:if>
                                    </td>
								</tr>
							</table>
							</li>		
                   			</c:forEach>
							
                        
                        <li>
                            <table border="0" width="100%">
                                <tr>
                                    <td>
                                        <div style="font-size:1.3rem;color:#757575;"><img src="<%=request.getContextPath()%>/assets/i/Alipay.png"/></div>
                                    </td>
                                    <td width="40px" align="center">
                                        <!--<i class="am-icon-check" style="color:#0e90d2;"/>-->
                                    </td>
                                </tr>
                            </table>
                        </li>
                        <li>
                            <table border="0" width="100%">
                                <tr>
                                    <td width="40px"></td>
                                    <td>
                                        <div style="font-size:2.0rem;color:#757575; padding-left:50px;">添加银行卡</div>
                                    </td>
                                    <td width="40px" align="center">
                                        <a class="am-header-icon am-icon-chevron-right"></a>
                                    </td>
                                </tr>
                            </table>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
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
	$(function(){
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
    function cardCharge(){
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_cardCharge.html";
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

    var param = "${param}";
    var src = "";
    if(param == "{param=paymyself}")
    	src = "<%=request.getContextPath() %>/portal/portal_paymyself.html";
   	else
    	src = "<%=request.getContextPath() %>/portal/portal_cardlist.html";
    var $tpl = $('#amz-tpl');
    var source = $tpl.text();
    var template = Handlebars.compile(source);
    data.header.content.title="一卡通充值";
    data.header.content.left=[
                    {
                        "link" : src,
                        "icon" : "chevron-left"
                    }
                ];
    var html = template(data);
    $tpl.before(html);
     $(function () {
        $("#choosecard").attr("data-am-modal", "{target: '#my-popup'}");

        $('.am-offcanvas-bar').on('click',function(){
            $(this).addClass('am-offcanvas-flip').removeClass('am-offcanvas-bar-overlay');
        });

        console.log($('.am-offcanvas-bar').html());
    })
</script>	
</body>
</html>