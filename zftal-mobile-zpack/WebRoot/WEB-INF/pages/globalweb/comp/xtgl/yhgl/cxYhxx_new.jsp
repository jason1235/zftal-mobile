<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
    <%--<style type="text/css">
      .autocut {
          overflow:hidden;
          white-space:normal;
          text-overflow:ellipsis;
          -o-text-overflow:ellipsis;
          -icab-text-overflow:ellipsis;
          -khtml-text-overflow:ellipsis;
          -moz-text-overflow:ellipsis;
          -webkit-text-overflow:ellipsis;
      }

      .autocut:hover {
          overflow:visible;
          white-space:normal;
          word-wrap:break-word;
      }
    </style>--%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
		$(function(){
			$("#btn_fpjs").click(function(){
				if($("input[name='id']:checked").length==0){
	                   openAlert("请先选中操作行！", "e");
	                    return false;
	                }
	            if($("input[name='id']:checked").length>1){
	                   openAlert("不能选中多行！", "e");
	                    return false;
	                }
	            var id = $("input[name='id']:checked").val();
	            var jsdm = $("input[name='id']:checked").closest("tr").find("input[name='jsdm']").val();
	            var url = "<%=request.getContextPath()%>/xtgl/yhgl_fpjs.html?yhglModelQuery.zgh="+id+"&yhglModelQuery.jsdm="+jsdm;
	            $("#myModal_toji").find(".modal-content").width(750);
	            $("#myModal_toji").find(".modal-content").height(500);
	            $("#myFrame").height(500);
	            $("#myFrame").attr("src", url);
	            $("#myModal_toji").modal("show");
			});
						
			$("#btn_qiyong").click(function(){
				if($("input[name='id']:checked").length==0){
	                   openAlert("请先选中操作行！", "e");
	                    return false;
	                }
	            if($("input[name='id']:checked").length>1){
	                   openAlert("不能选中多行！", "e");
	                    return false;
	                }
	            var id = $("input[name='id']:checked").val();
	            //openAlert(id, "e");
	            var url = "<%=request.getContextPath()%>/xtgl/yhgl_switchYhQy.html?model.zgh="+id+"&model.sfqy="+1;
	            $.ajax({
					url:url,
					type:"post",
					dataType:"json",
					data:{},
					success:function(data){
						if(data!=null){
						   if(data.status=='success'){
							  window.location.href="<%=request.getContextPath()%>/xtgl/yhgl_cxYhxx.html";
						   }else{
						      openAlert(data.msg, "e");
						   }
						}
				    }
				});
			});
			
			$("#btn_tingyong").click(function(){
				if($("input[name='id']:checked").length==0){
	                   openAlert("请先选中操作行！", "e");
	                    return false;
	                }
	            if($("input[name='id']:checked").length>1){
	                   openAlert("不能选中多行！", "e");
	                    return false;
	                }
	            var id = $("input[name='id']:checked").val();
	            //openAlert(id, "e");
	            var url = "<%=request.getContextPath()%>/xtgl/yhgl_switchYhQy.html?model.zgh="+id+"&model.sfqy="+0;
	            $.ajax({
					url:url,
					type:"post",
					dataType:"json",
					data:{},
					success:function(data){
						if(data!=null){
						   if(data.status=='success'){
							  window.location.href="<%=request.getContextPath()%>/xtgl/yhgl_cxYhxx.html";
						   }else{
						      openAlert(data.msg, "e");
						   }
						}
				    }
				});
			});
			
			//绑定设备号
			$("#btn_bd").click(function(){
				if($("input[name='id']:checked").length==0){
	                   openAlert("请先选中操作行！", "e");
	                    return false;
	                }
	            if($("input[name='id']:checked").length>1){
	                   openAlert("不能选中多行！", "e");
	                    return false;
	                }
	            var id = $("input[name='id']:checked").val();
	            var url = "<%=request.getContextPath()%>/xtgl/yhgl_bdsbh.html?model.zgh="+id;
	            $("#myModal_toji").find(".modal-content").width(750);
	            $("#myModal_toji").find(".modal-content").height(200);
	            $("#myFrame").height(200);
	            $("#myFrame").attr("src", url);
	            $("#myModal_toji").modal("show");
			});
			//解绑设备号
			$("#btn_jb").click(function(){
				if($("input[name='id']:checked").length==0){
	                   openAlert("请先选中操作行！", "e");
	                    return false;
	                }
	            if($("input[name='id']:checked").length>1){
	                   openAlert("不能选中多行！", "e");
	                    return false;
	                }
	            var id = $("input[name='id']:checked").val();
	            //openAlert(id, "e");
	            var url = "<%=request.getContextPath()%>/xtgl/yhgl_jbsbh.html?model.zgh="+id;
	            $.ajax({
					url:url,
					type:"post",
					dataType:"json",
					data:{},
					success:function(data){
						if(data!=null){
							openAlert(data.msg, "e");
						}
				    }
				});
			});
			
			
			$("#list_body tr").bind("click",function(e){
			 	var check = $(this).find("input[name='id']").prop("checked");
			 	//alert(check);
			 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
				if(!check){
				   $(this).find("input[name='id']").prop("checked",true);
				   
				   //判断是否启用,显示隐藏启用、停用按钮
				   var sfqyTd = $(this).find("#sfqyTd").text().trim();
				   if(sfqyTd=='启用'){
				     $("#btn_tingyong").show();
				     $("#btn_qiyong").hide();
				   }else{
				     $("#btn_qiyong").show();
				     $("#btn_tingyong").hide();
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
		})
    </script>
    </head>
  <body>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_fpjs">分配角色</button>
              <button type="button" class="btn btn-default" id="btn_qiyong">启用</button>
              <button type="button" class="btn btn-default" id="btn_tingyong">停用</button>
              
              <button type="button" class="btn btn-default" id="btn_bd">绑定设备号</button>
              <button type="button" class="btn btn-default" id="btn_jb">解绑设备号</button>
          </div>
      </div>
      <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="xtgl/yhgl_cxYhxx.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户名</label>
                        <div class="col-xs-7">
                            <input id="xtbm" maxlength="20" placeholder="最多20个字符" class="form-control" name="yhglModelQuery.zgh" value="${yhglModelQuery.zgh }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">姓名</label>
                        <div class="col-xs-7">
                            <input id="xtmc" maxlength="20" placeholder="最多20个字符" name="yhglModelQuery.xm" class="form-control" value="${yhglModelQuery.xm }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">联系电话</label>
                        <div class="col-xs-7">
                            <input id="xtmc" maxlength="20" placeholder="最多20个字符" name="yhglModelQuery.lxdh" class="form-control" value="${yhglModelQuery.lxdh }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否启用</label>
                        <div class="col-xs-7">
                            <select id="status" name="yhglModelQuery.sfqy" class="form-control" value="${yhglModelQuery.sfqy }" >
                                <option value="" ></option>
                                <option value="1" <c:if test = "${yhglModelQuery.sfqy eq '1'}"> selected="selected"</c:if>>是</option>
                                <option value="0" <c:if test = "${yhglModelQuery.sfqy eq '0'}"> selected="selected"</c:if>>否</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户角色</label>
                        <div class="col-xs-7">
                            <select id="status" name="yhglModelQuery.jsdm" class="form-control" value="${yhglModelQuery.jsdm }" >
                                <option value="" ></option>
                                <c:forEach var="menu" items="${jsList}" varStatus="st">
                                    <option value="${menu.jsdm}" <c:if test = "${yhglModelQuery.jsdm eq menu.jsdm}"> selected="selected"</c:if>>${menu.jsmc}</option>
                                 </c:forEach>
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
          <input name="minIndex" id="minIndex" type="hidden" value=""/>
        <input name="maxIndex" id="maxIndex" type="hidden" value=""/>
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th style="width:20%;">用户名</th>
                    <th style="width:20%;">姓名</th>
                    <th style="width:20%;">所属角色</th>
                    <th style="width:20%;">联系电话</th>
                    <th style="width:20%;">是否启用</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="yhglModelList" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.zgh }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.zgh }</td>
                    <td class="autocut" >${menu.xm}</td>
                    <td class="autocut" >
                    	${menu.jsmc}
                    	<input type="hidden" value="${menu.jsdm }" name="jsdm"/>
                    </td>
                    <td class="autocut" >${menu.lxdh}</td>
                    <td class="autocut" id="sfqyTd">
	                    <c:if test="${menu.sfqy eq '1'}">
		                    		<font color="green">启用</font>
	                    </c:if>
	                    <c:if test="${menu.sfqy eq '0'}">
		                    		<font color="red">停用</font>
	                    </c:if>
                    </td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${yhglModelList }" query="${yhglModelQuery }" queryName="yhglModelQuery"/>
      </form>
  </div>
	</body>
</html>