<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
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
	            $.post("<%=request.getContextPath() %>/xtgl/yhgl_unBingUser.html?yhglModelQuery.zgh="+id, null, function(data){
							var callback = function(){
								location.reload();
							};
							processCall(data, callback);
						}, "json");
			})
			
			$("#list_body tr").bind("click",function(e){
			 	var check = $(this).find("input[name='id']").prop("checked");
			 	//alert(check);
			 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
				if(!check){
				   $(this).find("input[name='id']").prop("checked",true);
				   
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
              <button type="button" class="btn btn-default" id="btn_fpjs">解绑设备</button>
          </div>
      </div>
      <jsp:include page="/WEB-INF/pages/globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="xtgl/yhgl_bingUserList.html" name="search" id="search" method="post">
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
                    <!--<th style="width:20%;">所属角色</th>-->
                    <th style="width:20%;">联系电话</th>
                    <th style="width:20%;">绑定设备</th>
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
                    <!--<td class="autocut" >
                    	${menu.jsmc}
                    	<input type="hidden" value="${menu.jsdm }" name="jsdm"/>
                    </td>-->
                    <td class="autocut" >${menu.lxdh}</td>
                    <td class="autocut" >${menu.deviceId}</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${yhglModelList }" query="${yhglModelQuery }" queryName="yhglModelQuery"/>
      </form>
  </div>
	</body>
</html>