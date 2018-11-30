<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
   	 $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            
            if(confirm("确认作废吗！")){
	 	       var id = $("input[name='id']:checked").val();
	 	       $.post(_path+"/serviceManager/loss_delete.html","query.id="+id,function(data){
	                 var callback = function(){
	 					$("#search").submit();
	 				};
	 				processCall(data, callback);
	           },"json");
            }
     });
   	 $("#btn_sh").click(function(){//功能条删除按钮
       	if($("input[name='id']:checked").length==0){
               openAlert("请先选中操作行！", "e");
                return false;
            }
        if($("input[name='id']:checked").length>1){
               openAlert("不能选中多行！", "e");
                return false;
            }
	    var id = $("input[name='id']:checked").val();
	    var url = _path+"/serviceManager/loss_shenhe.html?query.id=" + id;
        $("#myModal_toji").find(".modal-content").width(550);
        $("#myModal_toji").find(".modal-content").height(360);
        $("#myFrame").height(360);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");   
 	 });
   	$("#btn_zl").click(function(){//功能条删除按钮
       	if($("input[name='id']:checked").length==0){
               openAlert("请先选中操作行！", "e");
                return false;
            }
        if($("input[name='id']:checked").length>1){
               openAlert("不能选中多行！", "e");
                return false;
            }
	    var id = $("input[name='id']:checked").val();
	    var url = _path+"/serviceManager/loss_zlrsh.html?query.id=" + id;
        $("#myModal_toji").find(".modal-content").width(550);
        $("#myModal_toji").find(".modal-content").height(200);
        $("#myFrame").height(200);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");   
 	 });
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
	 
	 $("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
		var ispass = $(this).find("input[name='ispass']").val();
		var isover = $(this).find("input[name='isover']").val();
			if(check){
			   $("#btn_sh").show();
			   $("#btn_zl").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   if (ispass=="0") {
				   $("#btn_sh").show();
				   $("#btn_zl").hide();
			   } else {
				   if (isover=="0" && ispass=="2") {
					   $("#btn_sh").hide();
					   $("#btn_zl").show();
				   } else {
					   $("#btn_sh").hide();
					   $("#btn_zl").hide();
				   }
			   }
			   
			}
	 });
	
	 $("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			}
	 });
     //fillRows("20", "", "", false);//填充空行

    });
    
	
	
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd"><%--
              <button type="button" class="btn btn-default" id="btn_sh">审核</button>
              --%><button type="button" class="btn btn-default" id="btn_zl">结果登记</button>
              <button type="button" class="btn btn-default" id="btn_sc">作废</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath()%>/serviceManager/loss_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">主题</label>
                        <div class="col-xs-7">
                            <input id="xtbm" maxlength="15" placeholder="最多15个字符" class="form-control" name="query.title" value="${query.title }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">发布者</label>
                        <div class="col-xs-7">
                            <input id="xtmc" maxlength="10" placeholder="最多10个字符" name="query.name" class="form-control" value="${query.name }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">丢失地点</label>
                        <div class="col-xs-7">
                            <input id="xtmc" maxlength="10" placeholder="最多10个字符" name="query.place" class="form-control" value="${query.place }"/>
                        </div>
                    </div>
                    <%--<div class="form-group">
                        <label for="" class="col-xs-5 control-label">审核状态</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.ispass" class="form-control" value="${query.ispass }" >
                            	<option value="" ></option>
                                <option value="0" <c:if test = "${query.ispass eq '0'}"> selected="selected"</c:if>>未审核</option>
                                <option value="1" <c:if test = "${query.ispass eq '1'}"> selected="selected"</c:if>>未通过</option>
                                <option value="2" <c:if test = "${query.ispass eq '2'}"> selected="selected"</c:if>>已通过</option>
                                <option value="3" <c:if test = "${query.ispass eq '3'}"> selected="selected"</c:if>>作废</option>
                            </select>
                        </div>
                   </div> --%>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否寻回|招领</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.isover" class="form-control" value="${query.isover }" >
                            	<option value="" ></option>
                                <option value="0" <c:if test = "${query.isover eq '0'}"> selected="selected"</c:if>>否</option>
                                <option value="1" <c:if test = "${query.isover eq '1'}"> selected="selected"</c:if>>是</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">丢失|寻回 者</label>
                        <div class="col-xs-7">
                            <input id="xtmc" name="query.lossuser" class="form-control" value="${query.lossuser }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">招领类型</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.flag" class="form-control" value="${query.flag }" >
                            	<option value="" ></option>
                                <option value="0" <c:if test = "${query.flag eq '0'}"> selected="selected"</c:if>>丢失</option>
                                <option value="1" <c:if test = "${query.flag eq '1'}"> selected="selected"</c:if>>招领</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="row sl_aff_btn">
              <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
              </div>
          </div>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th style="width:7%;">发布者</th>
                    <th style="width:10%;">发布时间</th>
                    <th style="width:12%;">主题</th>
                    <th style="width:16%;">内容</th>
                    <th style="width:8%;">位置</th><%--
                    <th style="width:8%;">审核状态</th>
                    <th style="width:8%;">审核备注</th>
                    --%><th style="width:8%;">是否寻回|招领</th>
                    <th style="width:10%;">丢失|寻回 者</th>
                    <th style="width:10%;">寻回时间</th>
                    <th style="width:8%;">招领类型</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.id }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.name }</td>
                    <td class="autocut" ><fmt:formatDate value="${menu.timecreate }" pattern="yyyy-MM-dd"/></td>
                    <td class="autocut" >${menu.title}</td>
                    <td class="autocut" >
                    ${menu.content}
                    </td>
                    <td class="autocut" >${menu.place}</td><%--
                    <td class="autocut">
                    	<input type="hidden"" name="ispass" value="${menu.ispass }"/>
						<c:if test="${menu.ispass eq '0' }">未审核</c:if>
	                    <c:if test="${menu.ispass eq '1' }"><font color="red">未通过</font></c:if>
	                    <c:if test="${menu.ispass eq '2' }"><font color="green">已通过</font></c:if>
	                    <c:if test="${menu.ispass eq '3' }"><font color="#DAA520">作废</font></c:if>
					</td>
                    <td class="autocut" >${menu.rejectreason}</td>
                    --%><td class="autocut" >
                    	<input type="hidden"" name="isover" value="${menu.isover }"/>
                    	<c:if test="${menu.flag eq '0' }">
		                    <c:if test="${menu.isover eq '0' }"><font color="red">未寻回</font></c:if>
		                    <c:if test="${menu.isover eq '1' }"><font color="green">已寻回</font></c:if>
                    	</c:if>
                    	<c:if test="${menu.flag eq '1' }">
		                    <c:if test="${menu.isover eq '0' }"><font color="red">未招领</font></c:if>
		                    <c:if test="${menu.isover eq '1' }"><font color="green">已招领</font></c:if>
                    	</c:if>
                    </td>
                    <td class="autocut" >${menu.lossuser }</td>
                    <td class="autocut" ><fmt:formatDate value="${menu.overtime }" pattern="yyyy-MM-dd"/></td>
                    <td class="autocut" >
                    	<input type="hidden"" name="flag" value="${menu.flag }"/>
	                    <c:if test="${menu.flag eq '0' }">丢失</c:if>
	                    <c:if test="${menu.flag eq '1' }">招领</c:if>
                    </td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>