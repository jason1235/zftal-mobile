<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
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
            $("#btn_yh").click(function(){//功能条增加按钮
                user();
            });
            $("#btn_sc").click(function(){//功能条增加按钮
                if($("input[id='id']:checked").length==0){
                    //alert("请先选中操作行");
                    openAlert("请先选中操作行", "e");
                    return false;
                }
                //showConfirm("确定要删除群组么？");
                openAlert("确定要删除群组么？", "w");

                //$("#why_cancel").click(function(){
                //    divClose();
                //})

                $("#why_sure").click(function(){
                    removeData();
                })
            });

            $("input:checkbox").click(function(e){
                //e.stopPropagation();
                //if($(this).is(":checked")){
                //    $(this).closest("tr").click();
                //}else{
                //    $(this).closest("tr").removeClass("current");
                //}
            });
            
            $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                //    $("input:checkbox").removeAttr("checked");
                //    $("tbody > tr[name^='tr']").removeClass("current");
                //    $(this).attr("class", "current");
                    $(this).find("input:checkbox").attr("checked","checked");
                }
            );
            
            $("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
                 var id = $("input[id='id']:checked").val();
                 goUrl(_path+"/pushMsg/group_edit.html?model.id="+id);
            });
            

            $("button[name='search']").click(function(e){//搜索按钮
                refulsh();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
            $("select[name='query.status']").val('${query.status}');
            //fillRows("20", "", "", false);//填充空行

        });

        function add(){
            goUrl(_path+"/pushMsg/group_edit.html");
        }
        
        function user(){
        	if($("input[id='id']:checked").length==0){
                //alert("请先选中操作行");
                openAlert("请先选中操作行", "e");
                return false;
            }
            var id = $("input[id='id']:checked").val();
            goUrl(_path+"/pushMsg/group_reuser.html?model.id="+id);
        }


        function removeData(){
            if($("input[id='id']:checked").length==0){
                //alert("请先选中操作行");
                openAlert("请先选中操作行", "e");
                return false;
            }
            var id = $("input[id='id']:checked").val();
            $.post(_path+"/pushMsg/group_remove.html","model.id="+id,function(data){
                if(data.success){
                    refulsh();
                }else{
                    alert(data.text);
                }
            },"json");
        }
        
        function view(){
            if($("input[id='id']:checked").length==0){
                //alert("请先选中操作行");
                openAlert("请先选中操作行", "e");
                return false;
            }
            var id = $("input[id='id']:checked").val();
            goUrl(_path+"/pushMsg/group_edit.html?model.id="+id);
        }

        function refulsh(){
            $("#search").attr("action","<%=request.getContextPath()%>/pushMsg/group_list.html");
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
          <button type="button" class="btn btn-default" id="btn_yh">分配用户</button>
        </div>
    </div>
    <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
    
    <form action="pushMsg/group_list.html" name="search" id="search" method="post">
        <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组名称</label>
                        <div class="col-xs-7">
                            <input maxlength="30" placeholder="最多30个字符" name="query.name" class="form-control" value="${query.name }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">分组描述</label>
                        <div class="col-xs-7">
                            <input maxlength="300" placeholder="最多300个字符" name="query.detail" class="form-control" value="${query.detail }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">群组状态</label>
                        <div class="col-xs-7">
                            <select name="query.status" class="form-control">
				                <option value="">全部</option>
				                <option value="1">启用</option>
				                <option value="0">不启用</option>
				            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-sm-12">
                <button type="button" class="btn btn-primary btn-sm" id="search_go" name="search">查询</button>
            </div>
        </div>
        
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th>分组名称</th>
                    <th>分组描述</th>
                    <th>创建时间</th>
                    <th>是否启用</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="pageList" var="p" status="st">
                    <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" value="${p.id }"/></td>
                    <td>
                    ${p.name }
                    </td>
                    <td>${p.detail}</td>
                    <td><fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                     <td>${p.status=='0'?'不启用':'启用' }</td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${pageList }" query="${query }" />
    </form>
  
  </div>
  <%--<div class="toolbox">
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
 <form action="pushMsg/group_list.html" name="search" id="search" method="post">
<div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th width="10%">分组名称</th>
          <td width="20%">
            <input name="query.name" value="${query.name }"/>
          </td>
          <th width="10%">分组描述</th>
          <td width="20%">
            <input name="query.detail" value="${query.detail }"/>
          </td>
          <th width="10%">群组状态</th>
          <td width="20%">
            <select name="query.status" style='width:120px'>
                <option value="">全部</option>
                <option value="1">启用</option>
                <option value="0">不启用</option>
            </select>
          </td>
          <td >
              <button class="btn_cx" name="search" type="button">查 询</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
        <div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
        <span>用户分组列表<font color="#0457A7" style="font-weight:normal;">（双击进入编辑页面）</font></span>
    </h3>
<!--标题end-->
    <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"><input type="checkbox" disabled/></td>
                        <td width="20%">分组名称</td>
                        <td width="40%">分组描述</td>
                        <td width="20%">创建时间</td>
                        <td width="5%">是否启用</td>
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="pageList" var="p" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" value="${p.id }"/></td>
                        <td>
                        ${p.name }
                        </td>
                        <td>${p.detail}</td>
                        <td><fmt:formatDate value="${p.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
                         <td>${p.status=='0'?'不启用':'启用' }</td>
                        <!--${p.statusText }</td>-->
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
    <ct:page pageList="${pageList}"/>
    </div>
      </form> --%>
  </body>
</html>