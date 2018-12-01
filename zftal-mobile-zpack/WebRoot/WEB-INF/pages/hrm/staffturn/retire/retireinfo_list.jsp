<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page import="com.zfsoft.hrm.staffturn.config.IStatusUpdateConfig"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="ct" uri="/custom-code"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<%@include file="/commons/hrm/head.ini"%>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/inputPrompt.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/print/print.js"></script>
		<script language="javascript" src="<%=request.getContextPath() %>/js/hrm/LodopFuncs.js"></script>
        <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
               <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
        </object> 
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />

		<script type="text/javascript">
		$(function(){
			var current = null;
			
			$("#btn_zj").click(function(){
				showWindow("增加","<%=request.getContextPath()%>/retire/retire_add.html", 480, 180);
			});
			
			$("#btn_sc").click(function(){
					var checked=$("input[name='ids']:checked");
                    if(checked.length==0){
                        alert("请选择行");
                        return false;
                    }
                    var ids = [];
                    checked.each(function(){
                    	ids.push('\'' + $(this).val() + '\'');
                    });
					var id = ids.join(',');
                    showConfirm("确定要删除吗？");

					$("#why_cancel").click(function(){
						alertDivClose();
					});
					

					$("#why_sure").click(function(){
						$.post("<%=request.getContextPath() %>/retire/retire_delete.html?model.userId="+id, null, function(data){
							var callback = function(){
								//reflashPage();
								location.reload();
							};
							
							processDataCall(data, callback);
						}, "json");
					});
				});

			$("#btn_dy").click(function(){
				var checkObj=$("input[name='ids']:checked");
                if(checkObj.length == 0){
                    alert("请先点击选择要打印的信息");
                    return;
                }
                if(checkObj.length > 1){
                    alert("只能选择一条要打印的信息");
                    return;
                }
                var userId=$(checkObj).val();
                //showWindow("退休证打印","<%=request.getContextPath()%>/retire/retire_certPrint.html?query.userId=" + userId,
                //        500,480);
                $.ajax({
                    url:_path + "/retire/retire_certPrint.html",
                    type:"post",
                    data:"query.userId=" + userId,
                    cache:false,
                    dataType:"json",
                    global:false,
                    success:function(data) {
                        if ( data.success ) {
                            createPrintPage(data, print_type_setup);
                            //CreatePageTXZ(data, "1");
                            //LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
                            //LODOP.PRINT_SETUP();
                            //CreatePageTXZ(data, "2");
                            //LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
                            //LODOP.PRINT_SETUP();
                        } else {
                            tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
                            $("#window-sure").click(function() {
                                divClose();
                            });
                        }
                    }
                });
            });
            
            $("#btn_dc").click(function(){
            	exportInfo();
            });
            
			$("#search_btn").click(function(){
				$("#form").attr("action","<%=request.getContextPath()%>/retire/retire_list.html");
				$("#form").attr("method","post");
				$("#form").submit();
			});

			$("tbody > tr[name^='list_tr']").click(function(){	//监听单击行
				var current = $(this).attr("class");
				if(current != null && current != '') {
					$(this).removeClass("current");
					$(this).find("input[name='ids']").removeAttr("checked");
				}else{
					$(this).attr("class", "current");
					$(this).find("input[name='ids']").attr("checked","checked");
				}
			});
			
			$("#list_body tr").dblclick(function(){//行数据修改链接
				var userId = $(this).attr("id");
				modify( userId );
			});
			
			$("#btn_modify_batch").click(function(){
	        	var checkObj=$("input[name='ids']:checked");
	            if(checkObj.length==0){
	           		alert("请选择要操作的记录！");
	           		return false;
	            }
	            showWindow("批量修改退休文号","<%=request.getContextPath()%>/retire/preretire_plxgtxwhInit.html?op=modify",350,200);
	        });
	        
	        $("#btn_download").click(function(){
                var checkObj = $("input[name='ids']:checked");
                if (checkObj.length == 0) {
                    alert("请选择要导出退休审批表的人员！");
                    return false;
                } else if(checkObj.length > 1) {
                    alert("该操作只能选择1条记录！");
                    return false;
                }
                
                window.open(_path+"/retire/preretire_exportInfo.html?userId="+checkObj.val());
            });
			
			fillRows("20", "", "", false);//填充空行
			
			operationList();
			
		});
		<%--
		function CreatePageTXZ(dataMap, page) {
            LODOP = getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
            LODOP.PRINT_INITA(0,0,705,493,"打印退休证书 ");
            if (page == "1") {
                LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='../img/retire/cert_1.jpg'>");
                // 姓名
                LODOP.ADD_PRINT_TEXT(72,455,105,25, dataMap.info.overall.viewHtml.xm);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 性别
                LODOP.ADD_PRINT_TEXT(72,630,45,25, dataMap.info.overall.viewHtml.xbm);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 籍贯
                LODOP.ADD_PRINT_TEXT(123,455,105,25, dataMap.info.overall.viewHtml.jg);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 名族
                LODOP.ADD_PRINT_TEXT(123,630,45,25, dataMap.info.overall.viewHtml.mzm);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 太原理工大学
                LODOP.ADD_PRINT_TEXT(172,475,200,25, "太原理工大学");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 事业单位
                LODOP.ADD_PRINT_TEXT(222,475,200,25, "事业单位");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 职称
                LODOP.ADD_PRINT_TEXT(284,475,200,25, dataMap.info.overall.viewHtml.zc);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);

                var gw = dataMap.info.overall.values.gwlb;
                var gwmc = "";
                if (gw == "01") {
                    gwmc = "管理人员";
                } else if (gw == "02") {
                    gwmc = "专业技术人员";
                } else if (gw == "03") {
                    gwmc = "工勤技能人员";
                }
                LODOP.ADD_PRINT_TEXT(349,475,200,25, gwmc);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 居住地址
                LODOP.ADD_PRINT_TEXT(404,475,200,25, "e");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);

            } else {
                LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='../img/retire/cert_2.jpg'>");
                // 出生日期
                LODOP.ADD_PRINT_TEXT(73,115,70,25, dataMap.info.overall.viewHtml.csrq);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 退休时间
                LODOP.ADD_PRINT_TEXT(73,255,55,25, dataMap.info.retireTimeString);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 参加工作时间
                LODOP.ADD_PRINT_TEXT(124,115,70,25, dataMap.info.overall.viewHtml.rzrq);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 工作年限
                LODOP.ADD_PRINT_TEXT(124,255,55,25, "a");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 社会保障号
                LODOP.ADD_PRINT_TEXT(175,115,70,25, "b");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 缴费年限
                LODOP.ADD_PRINT_TEXT(175,255,55,25, "c");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 身份证
                LODOP.ADD_PRINT_TEXT(228,115,200,25, dataMap.info.overall.viewHtml.sfzh);
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 批准退休机关
                LODOP.ADD_PRINT_TEXT(290,115,200,25, "d");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
                // 备注
                LODOP.ADD_PRINT_TEXT(382,115,200,25, "e");
                LODOP.SET_PRINT_STYLEA(0,"FontName","楷体_GB2312");
                LODOP.SET_PRINT_STYLEA(0,"FontSize",12);
                LODOP.SET_PRINT_STYLEA(0,"Bold",1);
                LODOP.SET_PRINT_STYLEA(0,"ReadOnly",0);
            }
        };--%>
	  		
		function selectAllOrCancel(obj,name){//全选选择框操作
			var checks = document.getElementsByName(name);
			var body = document.getElementById("list_body");
			var tr = body.getElementsByTagName("tr");
			if(obj.checked){
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='current';
					checks[i].checked = true;
				}
			}else{
				for ( var i = 0; i < checks.length; i++) {
					tr[i].className='';
					checks[i].checked = false;
				}
			}
		}
		
		function exportInfo(){
            var str = $("#form").serialize();
            window.open("<%=request.getContextPath()%>/retire/retire_export.html?classId=${classId}&"+str);
	    }
	        
		function operationList(){
			$("a[name='detail']").click(function(){//行数据修改链接
				var globalid=$(this).closest("li").attr("name");
				//location.href="<%=request.getContextPath()%>/normal/staffResume_list.html?globalid="+globalid+"&type=teacher";
				goUrl(_path+"/normal/staffResume_list.html?globalid="+globalid+"&type=teacher");
			});
			
			$("a[name='modify']").click(function(){//行数据修改链接
				var userId=$(this).closest("td").attr("name");
				modify( userId );
			});

			$(".select_tools a").css("cursor", "pointer");
			operationHover();
		}

		function modify( userId ) {
			showWindow("信息维护","<%=request.getContextPath()%>/retire/retire_modify.html?query.userId=" + userId, 650, 400);
		}

		/*
		*排序回调函数
		*/
		function callBackForSort(sortFieldName,asc){
			$("#sortFieldName").val(sortFieldName);
			$("#asc").val(asc);
			$("#selectForm").submit();
		}
</script>
	</head>

	<body>
		<div class="formbox">
			<div class="toolbox">
				<div class="buttonbox">
					<ul>
						<li>
							<a id="btn_zj" class="btn_zj">增 加</a>
						</li>
						<li>
							<a id="btn_sc" class="btn_sc">删 除</a>
						</li>
						<li>
                            <a id="btn_dy" class="btn_dc">打印退休证</a>
                        </li>
                        <li>
                            <a id="btn_dc" class="btn_dc">导出</a>
                        </li>
                        <li>
	                        <a id="btn_modify_batch" class="btn_xg">批量修改退休文号</a>
                        </li>
                        <li>
                            <a id="btn_download" class="btn_dc">下载退休审批表</a>
                        </li>
					</ul>
				</div>
			</div>
			<form id="form" enctype="application/x-www-form-urlencoded" method="post">
				<input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
 				<input type="hidden" id="asc" name="asc" value="${asc}"/>
				<div class="searchtab">
					<table width="100%" border="0">
						<tbody>
							<tr>
								<th>职工号</th>
								<td>
									<input type="text" name="query.userId" class="text_nor" style="width: 130px;" value="${query.userId }" />
								</td>
								<th>
									姓 　名
								</th>
								<td>
									<input type="text" name="query.userName" class="text_nor" style="width: 130px;" value="${query.userName }" />
								</td>
								<th>部 　门</th>
								<td>
									<ct:codePicker name="query.deptCode" catalog="<%=ICodeConstants.DM_DEF_ORG %>" code="${query.deptCode }" width="130" />
								</td>
							</tr>
							<tr>
							    <th>退休证编号</th>
                                <td>
                                    <input type="text" name="query.identifier" class="text_nor" style="width: 130px;" value="${query.identifier }" />
                                </td>
								<th>性 　别</th>
								<td>
									<ct:codePicker name="query.sex" catalog="<%=ICodeConstants.SEX %>" code="${query.sex }" width="130" />
								</td>
								<th>退休级别</th>
								<td>
									<ct:codePicker name="query.retirePost" catalog="<%=ICodeConstants.ADMIN_DUTY_LEVEL %>" code="${query.retirePost }" width="130" />
								</td>
							</tr>
							<tr>
								<th>退休时间</th>
								<td>
									<input type="text" name="query.startRetireTime" id="retireDateStr" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 95px;" value="${query.startRetireTimeString }" />
									至
									<input type="text" name="query.endRetireTime" id="retireDateEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 95px;" value="${query.endRetireTimeString }" />
								</td>
								<th>出生年月</th>
								<td>
									<input type="text" name="query.startBothdayTime" id="birthDayStr" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 95px;" value="${query.startBothdayTimeString }" />
									至
									<input type="text" name="query.endBothdayTime" id="birthDayEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" style="width: 95px;" value="${query.endBothdayTimeString }" />
								</td>
								<th>是否返聘</th>
								<td>
									<select name="query.reEngage">
										<option value="" ${query.reEngageStr==""?"selected=\"selected\"":"" }>全部</option>
										<option value="false" ${query.reEngage=="false"?"selected=\"selected\"":"" }>否</option>
										<option value="true" ${query.reEngage=="true"?"selected=\"selected\"":"" }>是</option>
									</select>
									<div class="btn">
										<button class="btn_cx" type="button" id="search_btn">查 询</button>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			
			 <h3 class="datetitle_01">
				 	<span>退休人员信息<font color="#0457A7" style="font-weight:normal;">（提示：单击一行可以选定，双击一行可以维护信息）</font></span>
				 </h3>
			<div class="con_overlfow">				
				<table summary="" class="dateline tablenowrap" align="" width="100%" id="MyTable">
					<thead id="list_head">
						<tr>
							<td width="5%"><input type="checkbox" onclick="selectAllOrCancel(this,'ids')"/></td>
							<td>操作</td>
							<c:if test="${'userId' eq sortFieldName}">
							<td class="sort_title_current_${asc }" id="userId">职工号</td>
							</c:if>
							<c:if test="${'userId' != sortFieldName}">
								<td class="sort_title" id="userId">职工号</td>
							</c:if>
							<c:if test="${'xm' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="xm">姓名</td>
							</c:if>
							<c:if test="${'xm' != sortFieldName}">
							<td class="sort_title" id="xm">姓名</td>
							</c:if>
							<c:if test="${'xbm' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="xbm">性别</td>
							</c:if>
							<c:if test="${'xbm' != sortFieldName}">
							<td class="sort_title" id="xbm">性别</td>
							</c:if>
							<c:if test="${'csrq' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="csrq">出生年月</td>
							</c:if>
							<c:if test="${'csrq' != sortFieldName}">
							<td class="sort_title" id="csrq">出生年月</td>
							</c:if>
							<c:if test="${'zc' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="zc">职称</td>
							</c:if>
							<c:if test="${'zc' != sortFieldName}">
							<td class="sort_title" id="zc">职称</td>
							</c:if>
							<c:if test="${'zwjbdm' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="zwjbdm">行政职务级别</td>
							</c:if>
							<c:if test="${'zwjbdm' != sortFieldName}">
							<td class="sort_title" id="zwjbdm">行政职务级别</td>
							</c:if>
							<c:if test="${'zyjszj' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="zyjszj">专技职务级别</td>
							</c:if>
							<c:if test="${'zyjszj' != sortFieldName}">
							<td class="sort_title" id="zyjszj">专技职务级别</td>
							</c:if>
							<c:if test="${'grdj' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="grdj">工人等级</td>
							</c:if>
							<c:if test="${'grdj' != sortFieldName}">
							<td class="sort_title" id="grdj">工人等级</td>
							</c:if>
							<c:if test="${'xrzw' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="xrzw">现任职务</td>
							</c:if>
							<c:if test="${'xrzw' != sortFieldName}">
							<td class="sort_title" id="xrzw">现任职务</td>
							</c:if>
							<c:if test="${'retireTime' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="retireTime">退休时间</td>
							</c:if>
							<c:if test="${'retireTime' != sortFieldName}">
							<td class="sort_title" id="retireTime">退休时间</td>
							</c:if>
							<td>退休工龄</td>
							<c:if test="${'identifier' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="identifier">退休证编号</td>
							</c:if>
							<c:if test="${'identifier' != sortFieldName}">
							<td class="sort_title" id="identifier">退休证编号</td>
							</c:if>
							<c:if test="${'num' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="num">退休文号</td>
							</c:if>
							<c:if test="${'num' != sortFieldName}">
							<td class="sort_title" id="num">退休文号</td>
							</c:if>
							<c:if test="${'reEngage' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="reEngage">是否返聘</td>
							</c:if>
							<c:if test="${'reEngage' != sortFieldName}">
							<td class="sort_title" id="reEngage">是否返聘</td>
							</c:if>
							<c:if test="${'dwm' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="dwm">部门</td>
							</c:if>
							<c:if test="${'dwm' != sortFieldName}">
							<td class="sort_title" id="dwm">部门</td>
							</c:if>
							<c:if test="${'rzrq' eq sortFieldName}">
								<td class="sort_title_current_${asc }" id="rzrq">参加工作时间</td>
							</c:if>
							<c:if test="${'rzrq' != sortFieldName}">
							<td class="sort_title" id="rzrq">参加工作时间</td>
							</c:if>	
							<td class="sort_title" id="rzrq">离退后享受级别</td>						
							<td class="sort_title" id="rzrq">离退休费支付单位</td>						
							<td class="sort_title" id="rzrq">离退后管理单位</td>						
							<td class="sort_title" id="rzrq">异地安置地点</td>
						</tr>
					</thead>
					<tbody id="list_body">
						<c:forEach items="${retireInfoList}" var="bean">
							<tr name="list_tr" id="${bean.userId}">
								<td><input type="checkbox" name="ids" value="${bean.userId }" /></td>
								<td name="${bean.userId}">
                                    <div>
                                        <div class="current_item">
                                            <span class="item_text">维护信息</span>
                                        </div>
                                        <div class="select_tools" id="select_tools1"
                                            style="width: 80px; display: none">
                                            <ul>
                                                <li>
                                                    <a name="modify" class="first1">维护信息</a>
                                                </li>
                                                <li name="${bean.overall.values['globalid']}">
                                                    <a name="detail" class="last1">查看详细</a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </td>
								<td>${bean.userId}</td>
								<td>${bean.overall.viewHtml['xm']}</td>
								<td>${bean.overall.viewHtml['xbm']}</td>
								<td>${bean.overall.viewHtml['csrq']}</td>
								<td>${bean.overall.viewHtml['zc']}</td>
								<td>${bean.overall.viewHtml['zwjbdm']}</td>
								<td>${bean.overall.viewHtml['zyjszj']}</td>
								<td>${bean.overall.viewHtml['grdj']}</td>
								<td>${bean.overall.viewHtml['xrzw']}</td>
								<td>${bean.retireTimeString}</td>
								<td>${empty bean.retireTimeString? "" : bean.overall.viewHtml['gl']}</td>
								<td>${bean.identifier}</td>
								<td>${bean.num}</td>								
								<td>${bean.doResult=='04'?"是":"否"}</td>
								<td>${bean.overall.viewHtml['dwm']}</td>
								<td>${bean.overall.viewHtml['rzrq']}</td>								
								<td><ct:codeParse catalog="DM_GB_A_LTXGBXXSDYLBDM" code="${bean.lthxsjbm }"/></td>						
								<td>${bean.ltxfzfdw}</td>						
								<td>${bean.lthgldw}</td>						
								<td>${bean.ydazdd}</td>						
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<ct:page pageList="${retireInfoList }" />
		</form>
		</div>
		<!-- button class="btn_dhk"  >对话框</button-->
	</body>
</html>
