<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib  
    prefix="fmt"  
    uri="http://java.sun.com/jsp/jstl/fmt"  
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
<script type="text/javascript">
$(function(){
		var caches = {};
        $("#userInput" ).autocomplete({
                     source: function(request,response){
             var key=$.trim(request.term);
             if(key!=""){
                    if(key in caches){
                        response(caches[key]);
                        }
                    $.ajax({
                        type:'post',
                        url:'<%=request.getContextPath() %>/normal/overallInfo_userListScopeThink.html',
                        dataType:'json',
                        data:'deptType=self&term='+key,
                        cache:true,
                        success:function(data){
                            caches[key]=data;
                            response(data);
                            }
                        });
                 }
              },
                     minLength: 1, //触发条件，达到两个字符时，才进行联想
                     select: function( event, ui ) {
                         $("#xmLabel").html(ui.item.userName);
                         $("#xmInput").val(ui.item.userName);
                         $("#ghLabel").html(ui.item.userId);
                         $("#ghInput").val(ui.item.userId);
                         $("#dwmcLabel").html(ui.item.departmentName);
                         $("#dwmInput").val(ui.item.departmentId);
                         return false;
                     }
                }).data("ui-autocomplete")._renderItem = function( ul, item ) {
                      return $("<li>")
                        .append( "<a>" + item.userName+"("+item.userId+") "+item.departmentName+"("
                                +item.departmentId+") "+item.statusName+"("+item.status+") " + "</a>" )
                        .appendTo( ul );
                };
                
          $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                    var check = $(this).find("input:checkbox").attr("checked");
                   
                    var clmc = $(this).find("th[name='name']").text();
                    $("#clmcLabel").html(clmc);
                    $("#clmcInput").val(clmc);
                    var bh = $(this).find("th[name='bh']").text();
                    $("#clbhLabel").html(bh);
                    $("#clbhInput").val(bh);
                    var clid = $(this).find("input:checkbox").val();
                    $("#clidInput").val(clid);
                    
                    $("input:checkbox").removeAttr("checked");
                    $("tbody > tr[name^='tr']").removeClass("current");
                    if(check == "checked"){
                    	$(this).removeClass("current");
                    	$(this).find("input:checkbox").removeAttr("checked");
                        $("#clmcLabel").html("");
                        $("#clmcInput").val("");
                        $("#clbhLabel").html("");
                        $("#clbhInput").val("");
                        $("#clidInput").val("");
                    }else{
                    	$(this).attr("class", "current");
                    	$(this).find("input:checkbox").attr("checked","checked");
                    }
                }
            );
            
            $("button[name='search']").click(function(e){//搜索按钮
            reflashPage();
            e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
            
            function reflashPage(){
                //var lh = $("#querylh").val();
                //var xh = $("#queryxh").val();
                //var mc = $("#querymc").val();
                //var ghInput = $("#ghInput").val();
                //var xmInput = $("#xmInput").val();
                //var jysjInput = $("#jysjInput").val();
                //var ghsjInput = $("#ghsjInput").val();
                //var userInput = $("#userInput").val();
		        //$("#search").attr("action","<%=request.getContextPath()%>/dagl/materials_toAdd.html");
		        //$("#search").attr("method","post");
		        //$("#search").submit();
		        //$(".ymPrompt_close").click();
		        $("#form_edit").submit();
		        //$(".ymPrompt_close",window.parent.document).click();
		        //showWindow("增加",_path+"/dagl/materials_toAdd.html?query.classLh="+lh+"&query.classXh="+xh+"&query.classClmc="+mc
		        //+"&materials.classGh="+ghInput+"&materials.classXm="+xmInput+"&materials.classJysj="+jysjInput+"&materials.classGhsj="
		        //+ghsjInput+"&userInput="+userInput
		        //, 750,500);
    		};
    		
    		$("#save_btn").click(function(){
    		
            var op = $("#op").val();
            if(!check()){
                 return false;
            }
            var param=$("#form_edit").serialize();
            if(op == "add"){
            $.post("<%=request.getContextPath()%>/dagl/materials_saveMaterials.html",
                $("#form_edit").serialize(),function(data){
                    if(data.success){
                        $(".ymPrompt_close",window.parent.document).click();
                        $("#search",window.parent.document).submit();
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
            }
            
            if(op == "modify"){
            $.post("<%=request.getContextPath()%>/dagl/materials_modifyData.html",
                $("#form_edit").serialize(),function(data){
                    if(data.success){
                        $(".ymPrompt_close",window.parent.document).click();
                        $("#search",window.parent.document).submit();
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
            }
        });
        
        $("#cancel").click(function(){
            divClose();
        });
      });
      
      function check()
    {
        var name=$("input[name='materials.classGh']").val();
        if(name==null || name==''){
            showWarning("人员不能为空");
            return false;
        }
        var type=$("input[name='materials.classXm']").val();
        if(type==null || type==''){
            showWarning("人员不能为空");
            return false;
        }
        var type=$("input[name='materials.classJysj']").val();
        if(type==null || type==''){
            showWarning("请输入借阅时间");
            return false;
        }
        var type=$("input[name='materials.classClid']").val();
        if(type==null || type==''){
            showWarning("请选择借阅材料");
            return false;
        }
        
        return true;
    }
</script>
</head>
<body>

<form  action="<%=request.getContextPath()%>/dagl/materials_toAdd.html" name="search" id="form_edit" method="post">
<input type="hidden" id="op" value="${op }">
<input type="hidden" id="materials.classId" name="materials.classId" value="${materials.classId }">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <tbody>
               		<tr>
                        <th>
                            <span class="red"></span>人员查询(对借阅人查询)
                        </th>
                        <td colspan="3">
                            <input type="text" id="userInput" value="${userInput }">
                            <span class="red">输入职工号、姓名、部门代码、部门名称和状态查找</span>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>借阅人职工号
                        </th>
                        <td>
                            <label id="ghLabel">${materials.classGh }</label>
                            <input id="ghInput" type="hidden" name="materials.classGh" value="${materials.classGh }"/>
                        </td>
                        <th>
                            <span class="red">*</span>借阅人姓名
                        </th>
                        <td>
                            <label id="xmLabel">${materials.classXm }</label>
                            <input id="xmInput" type="hidden" name="materials.classXm" value="${materials.classXm }"/>
                        </td>
                    </tr>
                    <tr>
                       <th>
                            <span class="red">*</span>借阅时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="materials.classJysj"
                            value="<fmt:formatDate value="${materials.classJysj }" />" id="jysjInput"/>
                            
                        </td>
                        <th>
                            <span class="red"></span>归还时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="materials.classGhsj"
                            value="<fmt:formatDate value="${materials.classGhsj }" />" id="ghsjInput"/>
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>借阅材料名称<br/>  </th>
                        <td >
                            <label id="clmcLabel">
                            ${materials.classClmc }
                            </label>
                            <input id="clmcInput" type="hidden" name="materials.classClmc" value="${materials.classClmc }"/>
                        </td>
                        <th><span class="red">*</span>借阅材料编号<br/>  </th>
                        <td >
                            <label id="clbhLabel">
                            ${materials.classDescribe }
                            </label>
                            <input id="clbhInput" type="hidden" name="materials.classDescribe" value="${materials.classDescribe }"/>
                        </td>
                            <input id="clidInput" type="hidden" name="materials.classClid"   value="${materials.classClid }" />
                    </tr>
                </tbody>
                </table>
        
        <table class="formlist" >
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">提交借阅记录</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
       
        <div class="searchtab">
    <table width="100%" border="0" class="formlist" cellpadding="0" cellspacing="0">
      <thead>
                    <tr>
                        <th colspan="6">
                            <span><font color="#0f5dc2" style="font-weight:normal;">材料查询</font></span>
                        </th>
                    </tr>
      </thead>
      <tbody>
        <tr>
          <th width="10%">所属档案职工号</th>
          <td width="20%">
                <input type="text" id="querylh" style="width:128px" class="text_nor" name="query.classGh" value="${query.classGh }"/>
          </td>
          <th width="10%">所属档案姓名</th>
          <td width="20%">
            <input type="text" id="queryxh" style="width:128px" class="text_nor" name="query.classXm" value="${query.classXm }"/>
          </td>
          <th width="10%">材料名称</th>
          <td width="20%">
            <input type="text" id="querymc" style="width:128px" class="text_nor" name="query.classClmc" value="${query.classClmc }"/>
          </td>
        </tr>
        <tr> 
      </tbody>
      <tfoot>
        <tr>
          <td colspan="6">
            <div class="btn">
              <button class="btn_cx" name="search" >查 询</button>
            </div>
          </td>
        </tr>
      </tfoot>
    </table>
  </div>
                
                
               <div class="formbox">

				    <div class="con_overlfow">
				        <table width="100%" class="dateline tablenowrap" id="tiptab">
				                <thead id="list_head">
				                    <tr>
				                        <td width="5%"></td>
				                        <td class="sort_titlem_m" id="t.bh"  width="10%">材料编号</td>
				                        <td class="sort_titlem_m" id="t.mc"  width="10%">材料名称</td>
				                        <td class="sort_titlem_m" id="t.rdsj"  width="10%">入档时间</td>
				                        <td class="sort_titlem_m" id="t.clsm"  width="10%">材料说明</td>
				                        <td class="sort_titlem_m" id="t.gh"  width="10%">所属档案职工号</td>
				                        <td class="sort_titlem_m" id="t.xm"  width="10%">所属档案姓名</td>
				                        <td class="sort_titlem_m" id="t.fs"  width="10%">份数</td>
				                        <td class="sort_titlem_m" id="t.ym"  width="10%">页码</td>
				                    </tr>
				                </thead>
				                <tbody id="list_body" >
				                    <s:iterator value="archiveItemList" var="item" status="st">
				                        <tr name="tr">
				                        <td><input type="checkbox" id="id" value="${item.itemId }"/></td>
				                         <th name="bh">${item.bh }</th>
		                                 <th name="name">${item.name }</th>
		                                 <th name="createTime"><s:date name="createTime" format="yyyy-MM-dd"/></th>
		                                 <th name="desc">${item.desc }</th>
		                                 <th name="gh">${item.gh }</th>
		                                 <th name="xm">${item.xm }</th>
		                                 <th name="fs">${item.fs }</th>
		                                 <th name="ym">${item.ym }</th>
				                        <%-- <td>${p.statusText }</td> --%>
				                    </tr>
				                    </s:iterator>
				                </tbody>
				    </table>
				    </div>
    					<ct:page pageList="${archiveItemList }" />
    			</div>
            
            
        </div>
    </form>

</body>
</html>