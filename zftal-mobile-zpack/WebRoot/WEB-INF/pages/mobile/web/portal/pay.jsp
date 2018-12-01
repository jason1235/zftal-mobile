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
<div data-am-sticky>
	{{>header header}}
</div>    
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
                    <span class="am-input-group-label"><div style="width:100px;">单价(元/度)</div></span>
                    <input id="price" type="text" class="am-form-field" value="2.00" disabled="true"/>
                </div>
            </li>
            <li>
                <div class="am-input-group">
                    <span class="am-input-group-label"><div style="width:100px;">度数</div></span>
                    <input id="number" type="text" class="am-form-field" id="ds"/>
                </div>
            </li>
            <li>
                <div class="am-input-group">
                    <span class="am-input-group-label"><div style="width:100px;">金额(元)</div></span>
                    <input id="tp" type="text" class="am-form-field" />
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
    {{>navbar navbar}}
</script>

<%@ include file="/WEB-INF/pages/mobile/web/new/js.jsp" %>
<script>
	$(function(){
		$("#number").change(function(){
			var price = $("#price").val();
			var number = $("#number").val();
			
			var total = accMul(price,number);
			$("#tp").val(total);
		})
    });
    
    function accMul(arg1,arg2)
	{
		var m=0,s1=arg1.toString(),s2=arg2.toString();
		try{m+=s1.split(".")[1].length}catch(e){}
		try{m+=s2.split(".")[1].length}catch(e){}
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
	}
 
    function cardCharge(){
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_cardCharge.html";
    }
    function electricCharge(){
    	window.location.href = "<%=request.getContextPath()%>/portal/portal_pay.html";
    }
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
    data.header.content.title="电费充值";
    data.header.content.left= [
                    {
                        "link" : "<%=request.getContextPath() %>/portal/portal_paymyself.html",
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