<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<%@include file="/commons/hrm/head.ini" %>
	<script type="text/javascript">
		
		$(function(){
			
			$(".college_title .up").live('click',function(){
				conId = "con_"+$(this)[0].id.split('_')[1];
				$(".demo_college #"+conId).hide();
				$(this).attr("class","down");
				$(this).text("展开");
				return false;
			})
			
			$(".college_title .down").live('click',function(){
				conId = "con_"+$(this)[0].id.split('_')[1];
				$(".demo_college #"+conId).show();
				$(this).attr("class","up");
				$(this).text("收起");
			})

			$(".demo_college li .college_li").live('mouseenter',function(){
				//alert( $(this).closest("ul").find("li").index($(this).closest("li")) );
				var idx = $(this).closest("ul").find("li").index($(this).closest("li"))*1;
				if(idx % 5 < 3){
					$(this).next().removeClass().addClass("explain_left01");
				}
				else{
					$(this).next().removeClass().addClass("explain_right01");
				}
				$(this).next().show();
				$(this).parent().css("position","relative");
			})
			
			$(".demo_college li .college_li").live('mouseleave',function(){
				$(this).next().hide();
				$(this).parent().css("position","");
			})

			$(".college_title .title_name").live('mouseenter',function(){
				$(this).next().show();
				$(this).parent().css("position","relative");
			})
			
			$(".college_title .title_name").live('mouseleave',function(){
				$(this).next().hide();
				$(this).parent().css("position","");
			})
			
			$(".explain_left01,.explain_right01").live('mouseenter',function(){
				$(this).show();
				$(this).parent().css("position","relative");
			})
			
			$(".explain_left01,.explain_right01").live('mouseleave',function(){
				$(this).hide();
				$(this).parent().css("position","");
			})
			
		});


	</script>
  </head>
  <body>
  	<div class="prompt">
	  <p>当前（在职）教工总人数：<font color="#f06600"><b>${peopleCount }</b></font>人</p>
	</div>

	<div id="conDiv" class="demo_college">
		<c:if test="${fn:length(infoList) == 0}">
			<div class="page_prompt">
				<div class="page_promptcon">
				  <h3>温馨提示：</h3>
				  <p><font color="#0f5dc2">没有找到符合条件的组织机构信息</font></p>
				</div>
				<p>&nbsp;</p>
			</div>
		</c:if>
		<c:if test="${fn:length(infoList) > 0}">
			<c:forEach items="${infoList}" var="bean" varStatus="sta">
				<c:if test="${bean.viewable}">
					<div>	
						<h3 class="college_title" id="college_title_${bean.orgInfo.oid }">
							<span id="college_span_${bean.orgInfo.oid}" name="college_span" class="title_name">
								<a style="text-decoration:none;float:left;padding-left:15px;font-weight:bold;color:#333333;">${bean.orgInfo.name }</a>
								<span style="font-weight:normal;color:#0f5dc2;">
									<a style="text-decoration:none;float:left;padding-left:5px;padding-right:0px;">(</a>
									<a <c:if test="${bean.result.exactCount == 0 || !bean.clickable }">  </c:if><c:if test="${bean.result.exactCount != 0 && bean.clickable }"> href="<%=request.getContextPath() %>/org/orgSearch_detailList.html?oid=${bean.orgInfo.oid}" </c:if> style="text-decoration:none;float:left;padding-left:3px;padding-right:3px;">直属：${bean.result.exactCount } 人</a>
									<a <c:if test="${bean.totalCount == 0 || !bean.clickable }">  </c:if><c:if test="${bean.totalCount != 0 && bean.clickable }"> href="<%=request.getContextPath() %>/org/orgSearch_detailList.html?oid=${bean.orgInfo.oid}&searchByParent=1" </c:if> style="text-decoration:none;float:left;padding-left:0px;padding-right:3px;">, 总计：${bean.totalCount } 人 </a>
									<a style="text-decoration:none;float:left;padding-left:0px;padding-right:5px;">)</a>
								</span>
							</span>
							
							<div class="explain_left01" style="display:none;top:24px;">
								<div class="explain_con">
									<table width="100%" border="0" class="explain_tab" >
										<tbody>
											<tr>
												<td><span class="title">部门代码：</span>${bean.orgInfo.oid }</td>
												<td><span class="title">部门名称：</span>${bean.orgInfo.name }</td>
											</tr>
											<tr>
												<td><span class="title">部门类别：</span>${ bean.orgInfo.orgTypeInfo.text }</td>
												<td><span class="title">顺序码：</span>${bean.orgInfo.orderCode }</td>
											</tr>
											<tr>
												<td><span class="title">主管：</span>${bean.orgInfo.managerInfo.name }</td>
												<td><span class="title">负责人：</span>${bean.orgInfo.prinInfo.name }</td>
											</tr>
											<tr>
												<td><span class="title">创建时间：</span>${bean.orgInfo.createTimeText }</td>
												<td>&nbsp;</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<a class="up" id="up_${bean.orgInfo.oid }" href="#">收起</a>
						</h3>
						<div id="con_${bean.orgInfo.oid }" name="con_name_${bean.orgInfo.name }" class="con after">
							<ul id="ul_${bean.orgInfo.oid }">
								<c:forEach items="${bean.childrenSearch}" var="child" varStatus="sta_c">
									<c:if test="${bean.viewable && child.viewable}">
										<li id="li_${child.orgInfo.oid }">
											<a class="college_li" <c:if test="${child.result.exactCount == 0 || !child.clickable }">  </c:if><c:if test="${child.result.exactCount != 0 && child.clickable }"> href="<%=request.getContextPath() %>/org/orgSearch_detailList.html?oid=${child.orgInfo.oid}" </c:if> id="college_li_${child.orgInfo.oid }">${child.orgInfo.name }<span>( 任职：${child.result.exactCount }人 )</span></a>
											<div class="explain_left01" style="display:none;">
												<div class="explain_con">
													<table width="100%" border="0" class="explain_tab">
														<tbody>
															<tr>
																<td><span class="title">部门代码：</span>${child.orgInfo.oid }</td>
																<td><span class="title">部门名称：</span>${child.orgInfo.name }</td>
															</tr>
															<tr>
																<td><span class="title">上级部门：</span>${child.orgInfo.parent.name }</td>
																<td><span class="title">部门类别：</span>${child.orgInfo.orgTypeInfo.text }</td>
															</tr>
															<tr>
																<td><span class="title">主管：</span>${child.orgInfo.managerInfo.name }</td>
																<td><span class="title">负责人：</span>${child.orgInfo.prinInfo.name }</td>
																
															</tr>
															<tr>
																<td><span class="title">顺序码：</span>${child.orgInfo.orderCode }</td>
																<td><span class="title">创建时间：</span>${child.orgInfo.createTimeText }</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</li>
									</c:if>
								</c:forEach>
							</ul> 
							
							<ul>
							</ul>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>
			
	<div class="demo_add">
	</div>
	<div style="height: 300px"></div>	
  </body>
</html>
