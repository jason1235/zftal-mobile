<%@ page language="java"
	import="java.util.*,com.zfsoft.hrm.config.ICodeConstants"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/commons/hrm/head.ini"%>
		<script type="text/javascript"
			src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
		<script type="text/javascript">
   
    $(function(){ 
    	$("#back").click(function(){//功能条增加按钮
            location.href = _path+"/expertvote/expertgroup_page.html";
        });
    	$("a[name='privillege']").click(function(){
    		showWindowV2('选择部门',_path+'/dataprivilege/deptFilter_load.html?deptFilter.userId=${model.id}&deptFilter.roleId=expertgroup&viewType=role',500,300);
        });

        $("#bj").click(function(){
        	$("#bj").parent().hide();
            $("a[name='privillege']").hide();
            $("#cx").parent().show();
            $("#bc").parent().show();
            $("input[name ='model.name']").removeAttr("disabled");
            $("input[name ='model.type']").parent().find("input").removeAttr("disabled","disabled");
            $("input[name ='model.level']").parent().find("input").removeAttr("disabled","disabled");
        });
        $("#cx").click(function(){
        	init();
        });
        $("#bc").click(function(){
        	$.post(_path+"/expertvote/expertgroup_save.html",$("#group").serialize(),function(data1){
                if(data1.success){
                    goUrl(_path+"/expertvote/expertgroup_detail.html?model.id=${model.id}");
                }else{
                    alert(data1.text);
                }
            },"json");
        });
        $("#tjzj").click(function(){   //手动添加专家
        	var zid='${model.id}';
        	showWindowIframe("添加专家信息",720,500,"<%=request.getContextPath()%>/expertvote/expertgroup_select.html?groupMember.zjz_id="+zid,refreshList);
        });
        
        $("#yczj").click(function(){   //移除专家
        	var param = $("#list_body > tr input").serialize();
			$.post(_path+"/expertvote/expertgroup_remove.html",param,function(data){
				var callback = function(){					
					refreshList();
				};
				processDataCall(data,callback);
			},"json");
        });
        
        $("#cqzj").click(function(){  //随机抽取专家
        	var zid='${model.id}';
			showWindow("抽取规则",_path+"/expertvote/expertgroup_rule.html?groupMember.zjz_id="+zid,400,180);
        });
        
		$("tbody > tr[name^='tr']").click(function() { //监听单击行
			var current = $(this).attr("class");
			if (current != null && current != '') {
				$(this).removeClass("current");
				$(this).find("input[name='cy_ids']").removeAttr("checked");
			} else {
				$(this).attr("class", "current");
				$(this).find("input[name='cy_ids']").attr("checked", "checked");
			}
		});	
		
    	getSjfw("${model.id}");
    	init();
    	fillRows("16", "", "", false);//填充空行
    });
   	
   	function refreshList(){
   		var zjz_id = $("input[name='zjz_id']").val();
		$("#menber").attr("action","/expertvote/expertgroup_detail.html?model.id="+zjz_id);
		$("#member").attr("method","post");
		$("#member").submit();
	}
    
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
   		/**
	 * 把原有的弹出窗口转成ymPormpt式弹出
	 * @param title
	 * @param width
	 * @param height
	 * @param url
	 */
	function showWindowIframe(title,width,height,url,handler){
		
		ymPrompt.win({message:url,
					  width:width,
					  height:height,
					  title:title,
					  maxBtn:true,
					  minBtn:true,
					  iframe:true,
					  showShadow:false,
					  useSlide:true,
					  maskAlphaColor:"#FFFFFF",
					  maskAlpha:0.3,
					  handler:handler
				}
		);
	}
		
    function init(){
    	//$("#group").reset();
    	$("#reset").click();
    	$("#bj").parent().show();
    	$("a[name='privillege']").show();
    	$("#cx").parent().hide();
    	$("#bc").parent().hide();
    	$("input[name ='model.name']").attr("disabled","disabled");
    	$("input[name ='model.type']").parent().find("input").attr("disabled","disabled");
    	$("input[name ='model.level']").parent().find("input").attr("disabled","disabled");
    }
    
    function getSjfw(groupId){
        $.post( "<%=request.getContextPath()%>/dataprivilege/deptFilter_getOrgsTextForRow.html",
						{
							"deptFilter.userId" : groupId,
							"deptFilter.roleId" : "expertgroup"
						}, function(data) {
							$("#sjfw_expertgroup").html(data.simple);
							$("#sjfw_expertgroup").attr("name", data.whole);
							$("#sjfw_expertgroup").mousemove(function() {
								datatips($(this));
							})
						});
	}
	function datatips(obj) {
		var x = 0; //设置偏移量
		var y = 20;
		var padding_right = 0;
		var t = jQuery(obj);
		var l = 100;
		t
				.mouseover(function(e) {
					var datatip = "<div id=\"datatip\" style=\"width:200px;z-index:9999;display:none;position:absolute;padding:10px;border:1px solid #999; color:#0457A7; background: #F2F2F2;\"></div>"; //创建 div 元素
					var tip = jQuery(datatip);
					var data = jQuery(obj).attr("name");
					jQuery(tip).append(data);
					jQuery("body").append(tip); //把它追加到文档中
					l = jQuery(tip).outerWidth();
					jQuery("#datatip").css({
						"top" : (e.pageY + y) + "px",
						"left" : checkX(e.pageX) + "px"
					}).show("fast"); //设置x坐标和y坐标，并且显示
				});
		t.mouseout(function() {
			jQuery("#datatip").remove(); //移除 
		});
		t.mousemove(function(e) {
			jQuery("#datatip").css({
				"top" : (e.pageY + y) + "px",
				"left" : checkX(e.pageX) + "px"
			});
		});

		function checkX(mouseX) {
			var width = jQuery(document).width();
			var border = width - l - x - padding_right;
			if (mouseX + x < border) {
				return mouseX + x;
			} else {
				return mouseX - l;
			}
		}
	}
</script>
	</head>

	<body>
		<div class="toolbox">
			<!-- 按钮 -->
			<div class="buttonbox">
				<a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返
					回</a>
			</div>
		</div>
		<div class="tab">
			<!--标题start-->
			<h3 class="datetitle_01">
				<span>专家组信息设置<font color="#0457A7"
					style="font-weight: normal;" id="tip"></font> </span>
			</h3>
			<div class="toolbox">
				<!-- 按钮 -->
				<div class="buttonbox">
					<ul>
						<li>
							<a onclick="return false;" id="bc" class="btn_zj" href="#">
								保存信息 </a>
						</li>
						<li>
							<a onclick="return false;" id="cx" class="btn_qxsh" href="#">
								撤销编辑 </a>
						</li>
						<li>
							<a onclick="return false;" id="bj" class="btn_xg" href="#">
								编辑修改 </a>
						</li>
					</ul>
				</div>
			</div>
			<form id="group">
				<table align="center" class="formlist" width="100%">
					<tr>
						<th style="width: 120px">
							组名
						</th>
						<td style="width: 200px">
							<input name="model.name" value='${model.name }' />
							<input name="model.id" value='${model.id }' type="hidden" />
						</td>
						<th style="width: 120px">
							专业领域
							<span class="red">(为空则表示无限制)</span>
						</th>
						<td>
							<ct:codePicker name="model.type" catalog="DM_DEF_ZJLX"
								code="${model.type}" />
						</td>
					</tr>
					<tr>
						<th style="width: 120px">
							<span class="red">*</span>审核级别
						</th>
						<td>
							<ct:codePicker name="model.level" catalog="DM_SYS_ZJPSJB"
								code="${model.level}" />
						</td>
						<th style="width: 120px">
							<span class="red">*</span>审查范围
						</th>
						<td>
							<span id="sjfw_expertgroup"></span><a style="color: blue;"
								name="privillege">(变更)</a>
						</td>
					</tr>
				</table>
				<button id="reset" type="reset" style="display: none;"></button>
			</form>
			<form id="member">
				<c:if test="${model.id != null && model.id != ''||1==1 }">
					<div class="toolbox">
						<!-- 按钮 -->
						<div class="buttonbox">
							<ul>
								<li>
									<a onclick="return false;" id="tjzj" class="btn_zj" href="#">
										添加专家 </a>
								</li>
								<li>
									<a onclick="return false;" id="yczj" class="btn_sc" href="#">
										移除专家 </a>
								</li>
								<li>
									<a onclick="return false;" id="cqzj" class="btn_xg" href="#">
										随机抽取专家 </a>
								</li>
							</ul>
						</div>
					</div>
					<div class="formbox">
						<!--标题start-->
						<h3 class="datetitle_01">
							<span>专家组成员信息</span>
						</h3>
						<!--标题end-->
						<table width="100%" class="dateline nowrap" id="tiptab">
							<thead id="list_head">
								<tr>
									<td width="3%">
										<input type="checkbox"
											onclick="selectAllOrCancel(this,'cy_ids');"
											id="allCheckBoxDel" />
									</td>
									<td width="5%">
										序号
									</td>
									<td>
										职工号
									</td>
									<td>
										姓名
									</td>
									<td>
										部门
									</td>
									<td>
										专业领域
									</td>
								</tr>
							</thead>
							<tbody id="list_body">
								<input type="hidden" name="zjz_id" value="${model.id }">
									<c:forEach items="${memberList}" var="p" varStatus="st">
										<tr name="tr">
											<td>
												<input type="checkbox" name="cy_ids" id="id" value="${p.id}" />
											</td>
											<td>
												${st.index+1 }
											</td>
											<td>
												${p.gh }
											</td>
											<td>
												${p.xm }
											</td>
											<td>
												<ct:codeParse code="${p.dwm }"
													catalog="<%=ICodeConstants.DM_DEF_ORG %>" />
											</td>
											<td>
												<ct:codeParse code="${p.type }"
													catalog="DM_DEF_ZJLX" />
											</td>
										</tr>
									</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<ct:page pageList="${memberList }" query="${groupMemberQuery }"
					queryName="groupMemberQuery" />
			</form>
	</body>
</html>
