<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
        <script language="javascript" src="<%=systemPath %>/js/jquery/jqGrid_new/jquery.jqGrid.min.js"></script>
		<script type="text/javascript">

				var jsid="${jsid}";
			//为select option 增加title属性提示
			jQuery(function(){
				addOptionTitle();
				wfpGrid= new WfpGrid();
				loadJqGrid("#tabGrid","#pager",wfpGrid);

				yfpGrid=new YfpGrid();
				loadJqGrid("#yfpTabGrid",null,yfpGrid);

				jQuery("a[name='privillege']").live('click',function(){
					var id = jQuery(this).attr("id");
					showWindow('选择部门',600,400,'<%=request.getContextPath()%>/dataprivilege/deptFilter_load.html?deptFilter.userId='+id+'&deptFilter.roleId='+jsid+'&viewType=user');
				});

			});
			
			
		</script>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/globalweb/comp/xtwh/jsgl.js"></script>
		<script type="text/javascript">
			var jsdm="${jsdm}";	
		</script>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/mobile/jsgl_fpfw.js"></script>
		<script type="text/javascript" 
			src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
			
		
</head>


	<body >
	<div class="container_ydht sl_all_bg" style="height:100%;">
   
        
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>

    <button type="button" class="btn btn-primary" id="btn_fh" onclick="window.location.href='<%=request.getContextPath()%>/xtgl/jsgl_cxJsxx.html';return false;">返回</button>
    <div style="height: 30px;padding-top: 10px;font-size: 14px;"><font style="font-weight:bold;font-style:italic;color:red" >角色名称:</font>&nbsp;&nbsp;&nbsp;${jsmc }</div>
   
  
    <s:form name="form" method="post" action="" theme="simple">
        <div class="form-horizontal sl_all_form">
        <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }"/>
       
            <div class="row">
            
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务编码</label>
                        <div class="col-xs-7">
							<input type="text" class="form-control"name="fwbm" id="fwbm"/>
                            
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务名称</label>
                        <div class="col-xs-7">
							<input type="text" class="form-control" name="fwmc" id="fwmc"/>
                            
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                <button class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">查 询</button>
                </div>
            </div>
        </div>
        
    </s:form>
    <div style="height: 30px;padding-top: 10px;font-size: 14px;"><font style="font-weight:bold;font-style:italic;color:red" >说明:</font>&nbsp;&nbsp;&nbsp;可双击"未分配服务"中的记录至"已分配服务"</div>
 <div class="form-horizontal sl_all_form" 	>
    <div style="width:950px;height:300px;">
      <div  style="width:450px;float:left;">
                <table id="tabGrid"></table>
				<div id="pager"></div>
            </div>
            <div width="10px;float:left;"></div>
        <div  style="width:450px;float:right;padding-right: 15px;">
                <table id="yfpTabGrid"></table>
        </div>
    </div>
  <input type="hidden" name="result" id="result" value="${result}"/>
  <s:if test="result != null && result != ''">
  	<script>
  		refreshParent($('#result').val());
  	</script>
  </s:if>
  </div>
  </div> 
					
</body>

</html>