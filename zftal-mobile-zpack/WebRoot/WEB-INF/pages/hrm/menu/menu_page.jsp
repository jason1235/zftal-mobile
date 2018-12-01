<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
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
            $("#btn_sc").click(function(){//功能条增加按钮
            	if($("input[id='id']:checked").length==0){
                    alert("请先选中操作行");
                    return false;
                }
            	showConfirm("确定要删除菜单么？");

                $("#why_cancel").click(function(){
                    divClose();
                })

                $("#why_sure").click(function(){
                	removeData();
                })
            });

            $("input:checkbox").click(function(e){
                e.stopPropagation();
                if($(this).is(":checked")){
                    $(this).closest("tr").click();
                }else{
                    $(this).closest("tr").removeClass("current");
                }
            });
            
            $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                    $("input:checkbox").removeAttr("checked");
                    $("tbody > tr[name^='tr']").removeClass("current");
                    $(this).attr("class", "current");
                    $(this).find("input:checkbox").attr("checked","checked");
                }
            );
            
            $("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
            	 var id = $("input[id='id']:checked").val();
            	if(id.length<7){
            		  goUrl(_path+"/menu/menu_page.html?model.gnmkdm="+id);
            	}else{
            		   showWindow("菜单信息",_path+"/menu/menu_toEdit.html?model.gnmkdm="+id, 750,400);
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
            showWindow("菜单信息",_path+"/menu/menu_toEdit.html?model.fjgndm="+$("#gnmkdm").val(), 750,400);
        }


        function removeData(){
            if($("input[id='id']:checked").length==0){
                alert("请先选中操作行");
                return false;
            }
            var id = $("input[id='id']:checked").val();
            $.post(_path+"/menu/menu_remove.html","model.gnmkdm="+id,function(data){
                if(data.success){
                	refulsh();
                }else{
                    alert(data.text);
                }
            },"json");
        }
        
	    function view(){
	        if($("input[id='id']:checked").length==0){
	            alert("请先选中操作行");
	            return false;
	        }
	        var id = $("input[id='id']:checked").val();
	        showWindow("菜单信息",_path+"/menu/menu_toEdit.html?model.gnmkdm="+id, 750,400);
	    }

	    function refulsh(){
	    	$("#search").attr("action","<%=request.getContextPath()%>/menu/menu_page.html");
            $("#search").attr("method","post");
            $("#search").submit();
		}
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_zj" class="btn_zj">增加</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">编辑</a>
                        </li>
                        <li>
                            <a id="btn_sc" class="btn_sc">删除</a>
                        </li>
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form action="<%=request.getContextPath()%>/menu/menu_page.html" name="search" id="search" method="post">
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">父节点代码</th>
          <td width="20%">
            <input id="gnmkdm" name="model.gnmkdm" value="${model.gnmkdm }"/>
          </td>
          
          <td >
              <button class="btn_cx" name="search" type="button">查 询</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
<form action="sdp/declare_page.html" name="search" id="search" method="post">
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>系统菜单列表<font color="#0457A7" style="font-weight:normal;">  (提示：双击可以查看选定行)</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="5%">功能代码</td>
                        <td width="20%">菜单名称</td>
                        <td width="20%">菜单链接</td>
                        <td width="20%">显示顺序</td>
                        <%-- <td width="10%">状态</td> --%>
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="menus" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" value="${menu.gnmkdm }"/></td>
                        <td>
                        ${menu.gnmkdm }
                        </td>
                        <td>${menu.gnmkmc}</td>
                        <td>${menu.dyym }</td>
                         <td>${menu.xssx }</td>
                        <%--${p.statusText }</td>--%>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
    </div>
      </form>
  </body>
</html>