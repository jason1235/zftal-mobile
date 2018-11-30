<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
			$("#btn_zj").click(function(){//功能条增加按钮
				input();
			});
			$("#btn_qx").click(function(){//
				cancelDeclare();
			});
			$("#btn_sc").click(function(){//
				if($("input[id='id']:checked").length==0){
	    			alert("请先选中操作行");
	    			return false;
	    		}
				deleteDeclare();
			});
			$("#btn_ck").click(function(){//
				viewDeclare();
			});
			$("#btn_sb").click(function(){//
                view()
            });
			$("#btn_ck2").click(function(){//
				var id = $("input[id='id']:checked").val();
				showWindow("查看流程跟踪",_path+"/sp/spworkflow_detail.html?workId="+id,720,400);
			});

			var current = null;

			$("input:checkbox").click(function(e){
				e.stopPropagation();
				if($(this).is(":checked")){
					$(this).closest("tr").click();
				}else{
					$(this).closest("tr").removeClass("current");
				}
			});
			$("tbody > tr[name^='tr']").click(
				function(){	//监听单击行
					$("input:checkbox").removeAttr("checked");
					$("tbody > tr[name^='tr']").removeClass("current");
					$(this).attr("class", "current");
					$(this).find("input:checkbox").attr("checked","checked");
					current = $(this);
					refreshButton();
				}
			);
			
			$("tbody > tr[name^='tr']").dblclick(function(){//行数据双击事件
				var status = $(this).find("#status").val();
                if(status == "INITAIL"){
                	view();
                }else{
                	viewDeclare();
                }
				
			});

			$("button[name='search']").click(function(e){//搜索按钮
				$("#search").attr("action","<%=request.getContextPath()%>/expertmanage/declare_page.html");
				$("#search").attr("method","post");
				$("#search").submit();
				e.preventDefault();//阻止默认的按钮事件，防止多次请求
			});
			
			function refreshButton(){
	    		var status = $(current).find("#status").val();
	    		$("div.buttonbox li").hide();
	    		if(status == "INITAIL"){
	    			$("#btn_zj").parent().show();
	    			$("#btn_sb").parent().show();
	    			$("#btn_sc").parent().show();
	    		}else{
		    		$("#btn_ck").parent().show();
		    		$("#btn_ck2").parent().show();
	    		}
	    		if(status == "WAIT_AUDITING" || status == ""){
	    			$("#btn_qx").parent().show();
	    		}
	    		if(status == "NO_PASS_AUDITING"){
	    			$("#btn_sx").parent().show();
	    		}
	    	}
			$("select[name='expertDeclare.status']").val('${expertDeclare.status}');
			fillRows("20", "", "", false);//填充空行
		});

	    function input(){
	        showWindow("新增",_path+"/expertmanage/declare_input.html", 320,180);
	    }
    	
    	function cancelDeclare(){
    		if($("input[id='id']:checked").length==0){
    			alert("请先选中操作行");
    			return false;
    		}
    		var id = $("input[id='id']:checked").val();
    		$.post(_path+"/expertmanage/declare_cancel.html","expertDeclare.id="+id,function(data){
    			if(data.success){
    				window.location.reload();
    			}else{
    				alert(data.text);
    			}
    		},"json");
    	}
    	
    	function deleteDeclare(){
    		showConfirm("确定要删除吗?");
    		$("#why_cancel").click(function(){
    			divClose();
    		});
    		$("#why_sure").click(function(){
    			var id = $("input[id='id']:checked").val();
	    		$.post(_path+"/expertmanage/declare_delete.html","expertDeclare.id="+id,function(data){
					var callback = function(){
						$("#search").submit();
					};
					processDataCall(data,callback);
		    	},"json");
    		});
    	}
    	
    	function view(){
    	            if($("input[id='id']:checked").length==0){
    	                alert("请先选中操作行");
    	                return false;
    	            }
    	            var id = $("input[id='id']:checked").val();
    	            goUrl(_path+"/expertmanage/declare_detail.html?expertDeclare.id="+id);
       }
    	
    	function viewDeclare(){  //查看
    	            if($("input[id='id']:checked").length==0){
    	                alert("请先选中操作行");
    	                return false;
    	            }
    	            var id = $("input[id='id']:checked").val();
    	            goUrl(_path+"/expertmanage/declare_view.html?expertDeclare.id="+id);
    	}
    	
    	/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#search").submit();
		}  
	
		function tipsRefresh(msg,suc){
				if(suc == 'false'){
					tipsWindown("提示信息","text:"+msg,"340","120","true","","true","id");
				}else{
					add();
				}
				
				$("#window-sure").click(function() {
					divClose();
				});
		}
		
		function add(){
			var spBillConfigId = $("input[name='spBillConfig.id']").val();
			var content = '<form id="form" method="post" action="<%=request.getContextPath()%>/sectiongrading/sectionGrading_add.html">';
			content +=' 	<input type="hidden" name="spBillConfigId" value="' + spBillConfigId + '" />';
			content +='    </form>';
			$('body').append(content);
			$('#form').submit();
			$('#form').remove();
		}
    </script>
  </head>
  <body>

  <div class="toolbox">
		<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">新 增</a>
						</li>
						<li>
                            <a id="btn_sb" class="btn_ck">上 报</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">查 看</a>
                        </li>
						<li>
							<a id="btn_qx" class="btn_qx">取 消</a>
						</li>
						<li>
							<a id="btn_sc" class="btn_sc">删 除</a>
						</li>
						<li>
							<a id="btn_ck2" class="btn_ck">流程跟踪</a>
						</li>
					</ul>			
				</div>
		  <p class="toolbox_fot">
				<em></em>
			</p>
		</div>
		<input type="hidden" name="spBillConfig.id" value="${expertDeclare.config_id }"/>
 <form action="<%=request.getContextPath()%>/expertmanage/declare_page.html" name="search" id="search" method="post">
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 <input type="hidden" id="asc" name="asc" value="${asc}"/>
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
		  <th width="10%">工号</th>
		  <td><input name="expertDeclare.tjrgh" value="${expertDeclare.tjrgh }" type="text"/></td>
		  <th width="10%">姓名</th>
		  <td><input name="expertDeclare.tjrxm" value="${expertDeclare.tjrxm }" type="text"/></td>  
          <th width="10%">专业</th>
		  <td width="20%">
		  	  <ct:codePicker name="expertDeclare.type" catalog="<%=ICodeConstants.DM_GB_B_YJXKDM %>" code="${expertDeclare.type }" />
		  </td>
		</tr>
		<tr>  
		  <th width="10%">审批状态</th>
		  <td width="20%">
          	<select name="expertDeclare.status" style="width:186px">
          		<option value="" selected>全部</option>
          		<c:forEach items="${statusArray }" var="d">
          		<option value="${d.key }">${d.text }</option>
          		</c:forEach>
          	</select>
		  </td>
		  <td colspan="6">
		  	<div class="btn">
              <button class="btn_cx" name="search" type="button">查 询</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
		<div class="formbox">
<!--标题start-->
    <h3 class="datetitle_01">
    	<span>专家推荐上报<font color="#0457A7" style="font-weight:normal;">  ((双击：未上报状态申报操作，其他状态查看操作)</font></span>
    </h3>
<!--标题end-->
	<div class="con_overlfow">
		<table width="100%" class="dateline tablenowrap" id="tiptab">
				<thead id="list_head">
					<tr>
						<td width="5%"><input type="checkbox" disabled/></td>
						<td width="5%">序号</td>
						<td id="attenceTime" width="10%">姓名</td>
						<td id="type" width="20%">专家类型</td>
						<td id="declareTime" width="20%">上报时间</td>
						<td id="auditTime" width="20%">审核时间</td>
						<td id="status" width="20%">状态</td>
					</tr>
				</thead>
				<tbody id="list_body" >
					<s:iterator value="pageList" var="p" status="st">
						<tr name="tr">
						<td><input type="checkbox" id="id" value="${p.id }"/></td>
						<td>${st.index + beginIndex }
							<input type="hidden" id="status" value="${p.status }"/>
						</td>
						<td><ct:PersonParse code="${p.tjrgh }"/></td>
						<td><ct:codeParse code="${p.type }" catalog="DM_DEF_ZJLX"/></td>
						<td><s:date name="dedate" format="yyyy-MM-dd"/></td>
						<td><s:date name="adudate" format="yyyy-MM-dd"/></td>
						<td>${p.statusText }</td>
					</tr>
					</s:iterator>
				</tbody>
  	</table>
  	</div>
  	<ct:page pageList="${pageList }" query="${expertDeclare }" queryName="expertDeclare"/>
	</div>
  	  </form>
  </body>
</html>
