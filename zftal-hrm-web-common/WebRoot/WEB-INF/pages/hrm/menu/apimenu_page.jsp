<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
            $("#btn_ck").click(function(){//功能条增加按钮
                view();
            });
            $("#btn_zj").click(function(){//功能条增加按钮
                add();
            });
            $("#btn_sc").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		openAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post(_path+"/menu/apiMenu_remove.html","model.gnmkdm="+id,function(data){
                    if(data.success){
                    	refulsh();
                    }else{
                    	openAlert(data.text);
                    }
                },"json");
            });
            $("#btn_ty").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		openAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post(_path+"/menu/apiMenu_updateSfqy.html","model.gnmkdm="+id+"&model.sfqy=0",function(data){
                    if(data.success){
                    	refulsh();
                    }
                },"json");
            });
            $("#btn_qy").bind("click",function(e){
            	if($("input[name='id']:checked").length==0){
            		openAlert("请先选中操作行");
                    return false;
                }
                var id = $("input[name='id']:checked").val();
                $.post(_path+"/menu/apiMenu_updateSfqy.html","model.gnmkdm="+id+"&model.sfqy=1",function(data){
                    if(data.success){
                    	refulsh();
                    }
                },"json");
            });
            
            $("#list_body tr").bind("click",function(e){
        	 	var check = $(this).find("input[name='id']").prop("checked");
        	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
        		var sfqy = $(this).find("input[name='menu.sfqy']").val();
       			if(check){
       			   $("#btn_qy").show();
       			   $("#btn_ty").show();
       			   $("#btn_sc").show();
       			}else{
       			   $(this).find("input[name='id']").prop("checked",true);
       			   if (sfqy=="1") {
       					$("#btn_qy").hide();
       					$("#btn_ty").show();
       					$("#btn_sc").hide();
       			   } else {
       					$("#btn_qy").show();
       					$("#btn_ty").hide();
       					$("#btn_sc").show();
       						
       			   }
       			   
       			}
        	});
        	
        	$("#list_body tr").find("input[name='id']").bind("click",function(e){
        			var check = $(this).prop("checked");
        			if(check){
        				$(this).removeAttr("checked");
        			}else{
        			   $(this).prop("checked",true);
        			}
        	 });

            
            
            
            $('#list_body tr').dblclick(function(){//行数据双击事件
            	 var id = $(this).find("input[name='id']").val();
            	if(id.length<5){
            		  goUrl(_path+"/menu/apiMenu_page.html?query.fjgndm="+id);
            	}else{
            		var url = _path+"/menu/apiMenu_toEdit.html?model.gnmkdm="+id;
        	        window.location.href=url;
            	}
            });
            
            
            

            $("button[name='search']").click(function(e){//搜索按钮
            	refulsh();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
            $("select[name='model.gnmkdm']").val('${model.gnmkdm}');
            //fillRows("20", "", "", false);//填充空行

            $("tbody > tr:not(.data)").dblclick(function(){//行数据双击事件
                add();
            });
        });

        function add(){
        	var url = _path+"/menu/apiMenu_toEdit.html?model.fjgndm="+$("#xtbm").val();
            window.location.href=url;
        }


        
	    function view(){
	        if($("input[name='id']:checked").length==0){
	        	openAlert("请先选中操作行");
	            return false;
	        }
	        var id = $("input[name='id']:checked").val();
	        var url = _path+"/menu/apiMenu_toEdit.html?model.gnmkdm="+id;
	        window.location.href=url;
	    }

	    function refulsh(){
	    	$("#search").attr("action","<%=request.getContextPath()%>/menu/apiMenu_page.html");
            $("#search").attr("method","post");
            $("#search").submit();
		}
    </script>
  </head>
  <body>
  <div class="container_ydht sl_all_bg">
   <div class="toolbox">
          <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_ck">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc">删除</button>
              <button type="button" class="btn btn-default" id="btn_qy">启用</button>
              <button type="button" class="btn btn-default" id="btn_ty">停用</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
 <form action="<%=request.getContextPath()%>/menu/apiMenu_page.html" name="search" id="search" method="post">
 <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">父节点代码</label>
                        <div class="col-xs-7">
                            <input id="xtbm" class="form-control" name="query.fjgndm" value="${query.fjgndm }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">系统名称</label>
                        <div class="col-xs-7">
                            <input id="xtmc" name="query.gnmkmc" class="form-control" value="${query.gnmkmc }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否启用</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.sfqy" class="form-control" value="${query.sfqy }" >
                                <option value="" ></option>
                                <option value="1" <c:if test = "${query.sfqy eq '1'}"> selected="selected"</c:if>>启用</option>
                                <option value="0" <c:if test = "${query.sfqy eq '0'}"> selected="selected"</c:if>>停用</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
          </div>
          <div class="row sl_aff_btn">
              <div class="col-xs-12">
                  <button type="submit" class="btn btn-primary btn-sm" id="search_go" name="search">查询</button>
              </div>
          </div>
 
        <div class="formbox">

        <table width="100%" class="table table-striped sl_all_bg table-bordered text-left" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td style="width:64px;"><input type="checkbox" disabled/></td>
                        <td >功能代码</td>
                        <td >菜单名称</td>
                        <td >是否启用</td>
                        <td >显示顺序</td>
                        <%-- <td width="10%">状态</td> --%>
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="menus" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td class="autocut"><input type="checkbox" name="id" value="${menu.gnmkdm }"/></td>
                        <td class="autocut">
                        ${menu.gnmkdm }
                        </td>
                        <td class="autocut">${menu.gnmkmc}</td>
                        <td class="autocut"><input type="hidden" value="${menu.sfqy }" name="menu.sfqy"/>
                        <c:if test="${menu.sfqy eq '1'}">启用</c:if>
                        <c:if test="${menu.sfqy eq '0'}">取消</c:if>
                        </td>
                         <td class="autocut">${menu.xssx }</td>
                        <%--${p.statusText }</td>--%>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
      </form>
      </div>
  </body>
</html>