<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html class="no-js">
<head>
  <%@ include file="/commons/head.ini" %>
  <script type="text/javascript">
		$(function(){
    	
    	var fjgndm = '${model.fjgndm }';
        if(fjgndm == 'N'){
        	$("#dyym").hide();
        }
        if(fjgndm.length>2 ){
        	$("#dyym").show();
        }
        
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            $.post("<%=request.getContextPath()%>/guest/menu_save.html",
                $("#form_edit").serialize(),function(data){
                    if(data.success){
                        $('#search_go',window.parent.document).click();
                    }else{
                        errorAlert(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        $("#fjgn").bind('input propertychange', function() {
        	var fjgndm = $(this).val();
        	if(fjgndm.length>2 ){
        		$("#dyym").show();
        	}else{
        		$("#dyym").hide();
        	}
        });
        
        $("#cancle_btn").bind('click',function(e){
				$('#search_go',window.parent.document).click();
			});
        
    });

    function check()
    {
        var name=$("input[name='model.gnmkdm']").val();
        if(name==null || name==''){
            showWarning("功能代码不能为空");
            return false;
        }
        return true;
        var xssx=$("input[name='model.xssx']").val();
        if(xssx==null || xssx==''){
            showWarning("显示顺序不能为空");
            return false;
        }
        return true;
    }

    
		
  </script>
</head>

<body>


 <div class="admin-content" >
 <jsp:include page="/commons/alert.jsp" flush="true"></jsp:include>
    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">菜单信息</strong> / <small>维护</small></div>
    </div>

    <hr/>



      <div class="am-u-sm-12 am-u-md-8 am-u-md-pull-12">
        <form class="am-form am-form-horizontal" action="<%=request.getContextPath() %>/guest/menu_save.html" name="search" id="form_edit" method="post">
          <div class="am-form-group">
            <label for="user-name" class="am-u-sm-4 am-form-label">功能代码</label>
            <div class="am-u-sm-8 am-form-label" style="text-align: left;">
            
            				<c:if test="${op=='add'}">
                                <input name="model.gnmkdm" type="text" placeholder="功能代码"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.gnmkdm }
                                <input value="${model.gnmkdm }" name="model.gnmkdm" type="hidden" />
                            </c:if>
                            <input name="op" type="hidden" value="${op }"/>
                            
                            
              
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-email" class="am-u-sm-4 am-form-label">父级功能代码 </label>
            <div class="am-u-sm-8  am-form-label" style="text-align: left;">
          					<c:if test="${op=='add'}">
                                <input id="fjgn" value="${model.fjgndm }" name="model.fjgndm" type="text" placeholder="父级功能代码"/>
                            </c:if>
                            <c:if test="${op!='add'}">
                                ${model.fjgndm }
                                <input id="fjgn" value="${model.fjgndm }" name="model.fjgndm" type="hidden"/>
                            </c:if>
            
              
            </div>
          </div>
          
          <div class="am-form-group"  id="dyym">
            <label for="user-QQ" class="am-u-sm-4 am-form-label">页面链接</label>
            <div class="am-u-sm-8">
            	<input type="text" name="model.dyym" value="${model.dyym }" placeholder="页面链接"/>
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-phone" class="am-u-sm-4 am-form-label">菜单名称</label>
            <div class="am-u-sm-8">
            	<input name="model.gnmkmc" type="text" value="${model.gnmkmc }" placeholder="菜单名称"/>
            </div>
          </div>

          <div class="am-form-group">
            <label for="user-QQ" class="am-u-sm-4 am-form-label">显示顺序</label>
            <div class="am-u-sm-8">
            	<input type="text" name="model.xssx" value="${model.xssx }" placeholder="显示顺序"/>
            </div>
          </div>


          <div class="am-form-group">
            <div class="am-u-sm-9 am-u-sm-push-3">
              <button type="button" class="am-btn am-btn-primary" id="save_btn">保存修改</button>
              <button type="button" class="am-btn am-btn-primary" id="cancle_btn">关闭页面</button>
            </div>
          </div>
        </form>
    </div>
  </div>

</body>
</html>