<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
		<script type="text/javascript">
			
		</script>
</head>

<s:form action="/xtgl/jsgl_list.html"  theme="simple">
	<body>
		<img src="<%=request.getContextPath() %>/logo/system/ico_type_open.gif" id="img_open" style="display: none;"/>
		<img src="<%=request.getContextPath() %>/logo/system/ico_type_close.gif" id="img_close" style="display: none;"/>
		<!-- 功能操作 -->
		<input type="hidden" name="gnczStr" id="gnczStr" value="${gnczStr }"/>

            <div class="toolbox">
            	<div class="buttonbox">
		        <ul>
		            <li>
			                <a href="#" class="btn_zj" onclick="showWin('jsgl_add.html',490,350);">
			                    	增加
			                </a>
			            </li>
			            <li>
			                <a href="#" class="btn_xg" onclick="operJsxx('update')">
			                    	修改
			                </a>
			            </li>
			            <li>
			                <a href="#" class="btn_sc" onclick="operJsxx('del')">
			                  	  删除
			                </a>
			            </li>
			            <li>
			                <a href="#" class="btn_sh" onclick="saveGnqx()">
			                  	 保存
			                </a>
			            </li>
			            <li>
			                <a href="#" class="btn_shtg" onclick="jsfpyh()">
			                  	  分配用户
			                </a>
			            </li>
			            <li>
			        </ul>
			    </div>
            
            	<div class="searchtab">
            		<table width="100%" border="0">
						<tbody>
							<tr>
								<th>
									岗位级别
								</th>
								<td>
									<s:select list="gwjbList" headerKey="" headerValue="" listKey="gwjbdm" listValue="gwjbmc" name="gwjbdm" cssStyle="width:140px"></s:select>
								</td>
								<th>
									是否已分配用户
								</th>
								<td>
									<s:select list="#{'':'','1':'是','0':'否'}" cssStyle="width:140px" name="sffpyh"></s:select>
								</td>
								<th>
									角色名称
								</th>
								<td>
									<s:textfield id="jsmc" name="jsmc" maxlength="20" cssStyle="width:130px"></s:textfield> 
								</td>
								 <td>
					                <div class="btn">
					                   <button type="button" id="search_go" onclick="subForm('jsgl_list.html')">
											查询
										</button>
					                 </div>
					              </td>
							</tr>
						</tbody>
					</table>
            	</div>
            </div> 
            
            <div class="formbox">
				<h3 class="datetitle_01">
					<span> 查询结果&nbsp;&nbsp; 
						
					</span>
				</h3>
				<table width="100%">
					<tr>
						<td valign="top" style="padding:2px">
						<table width="100%" class="dateline">
						<thead>
							<tr>
								<td>
									角色名称
									</td>
								<td>
									已分配用户数</td>
							</tr>
						</thead>
						<tbody>
							<s:iterator status="sta" value="rs" id="obj">
							<tr onclick="rowMoreClick(this);displayJsgnqx('${obj.jsdm }');"  style="cursor: hand;">
								<td >
									<input type="hidden" name="jsdm" value="${obj.jsdm }"/>
									<input type="hidden" name="sfksc" value="${obj.sfksc }"/>
									<input type="hidden" name="yhnum" value="${obj.yhnum }"/>
									<font color="${obj.color }">
									${obj.jsmc }
									</font>
								</td>
								<td>
								<!--<a href="#" onclick="showWin('yhgl_viewYh.html?jsdm=${jsdm}',580,380,'')" style='cursor: pointer;'>
								<font color="${obj.color }">
								${obj.yhnum}
								</font>
								</a>
								--></td>
							</tr>
							</s:iterator>
						</tbody>
  					</table>
						</td>
						
						<td valign="top" style="padding:2px" width="65%">
						        <div class="tab_box" style="border:none;">
       						<table width="100%" class="dateline">
						<thead>
							<tr>
								<td>
									权限列表
									</td>
								
							</tr>
						</thead></table>
									<div class="titlelist" style="overflow-y: auto;height:420px;border:1px solid #B9B9B9;border-top:none;width:99.7%" id="topDiv">
        							<s:if test="firstList != null && firstList.size() >0">
										<s:iterator value="firstList" id="s">
												&nbsp;&nbsp;&nbsp;<a style="cursor:pointer;" name="gnid" id="${s.gnmkdm }" style="text-decoration:none!important;" >
												<img src="<%=request.getContextPath() %>/logo/system/ico_type_open.gif" 
												alt="点击展现叶子结点" id="${s.gnmkdm }_img" onclick="dispChildMenu(this)"/>
												<input type="checkbox" class="cbvclass" style="cursor: pointer;" id="${s.gnmkdm }_cbv" name="topcbv" value="${s.gnmkdm }" onclick="selectChild(this);"/>${s.gnmkmc }
												</a>
												<br/>
												<div style="display: none;"  id="${s.gnmkdm }_div"></div>
										</s:iterator>
										</s:if>
										<s:else>
												未找到任何功能模块记录！
										</s:else>
              	
            			</div>

					</div>
						</td>
					</tr>
				</table>
			</div>
			<script type="text/javascript">
			//加载一级菜单下面的所有子菜单
			if ($("a[name='gnid']")) {
				var jdArray = $("a[name='gnid']");
				$.ajax({
					url:"jsgl_queryGnmkdmList.html",
					type: "post",
					dataType:"json",
					data:{},
					async:false,
					success:function(data){	
						createChildMenu(jdArray,data,0);	
					}
				});
			}
		</script>
</body>
</s:form>
<script type="text/javascript">
			if('${result}'!=null && '${result}'!=''){
				alert('${result}');
			}
		</script>
</html>