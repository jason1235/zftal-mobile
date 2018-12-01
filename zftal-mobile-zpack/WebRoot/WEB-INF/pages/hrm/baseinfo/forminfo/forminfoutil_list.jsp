<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
	<%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/normal/resume/resume.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
	<script type="text/javascript">
		var num=6;
	    var displayNum = 2;
	    var dept="";
		function updateEntity(param,title,index){
			var guid = title.attr("id");
			guid=guid.split("_");
			
			$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_modifyDynaBean.html?formInfoTypeId=${formInfoTypeId}'
					+'&infoClassId='+guid[1]+'&index='+index, param, function(data){
				
				if( data.message.success ) {
					$("#newData").remove();
					$("#newDataTable").remove();
					var content = display(data.dynaBean);
					if($("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).length==0){
						$(content).appendTo($("#container_"+data.dynaBean.clazz.guid));
					}else{
						$("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[0]);
						$("#table_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[1]);
					}
					removeButtion();
				}else{
					showWarning(data.message.text);
					$("#war_sure").click(function(){
						divClose();
					});
				}
			}, "json");
			return false;
		}
		function getEntityToEdit(title,index){
			var guid = title.attr("id");
			guid=guid.split("_");
			
			$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_getDynaBeanByInfoClassId.html?formInfoTypeId=${formInfoTypeId}'
					+'&infoClassId='+guid[1]+'&index='+index,null, function(data){
				
				if( data.message.success ) {
					var content = addInputForm(data.dynaBean,false);
					if($("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).length==0){
						$(content).appendTo($("#container_"+data.dynaBean.clazz.guid));
					}else{
						$("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[0]);
						$("#table_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[1]);
					}
				}
			}, "json");
			return false;
		}
		function getEntityToView(title,index){
			var guid = title.attr("id");
			guid=guid.split("_");
			$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_getDynaBeanByInfoClassId.html?formInfoTypeId=${formInfoTypeId}'
					+'&infoClassId='+guid[1]+'&index='+index,null, function(data){
				if( data.message.success ) {
					$("#newData").remove();
					$("#newDataTable").remove();
					var content = display(data.dynaBean);
					if($("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).length==0){
						$(content).appendTo($("#container_"+data.dynaBean.clazz.guid));
					}else{
						$("#ul_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[0]);
						$("#table_"+data.dynaBean.clazz.guid+"_"+data.dynaBean.index).replaceWith(content[1]);
					}
					removeButtion();
				}
			}, "json");
			return false;
		}
		function message(){
			return "请先完成【"+$("#newData").closest(".demo_college").find(".title_name").html()+"】的编辑并点击【保存】按钮";
		}
		function display(data){
			var html = "<ul id=\"ul_"+data.clazz.guid+"_"+data.index+"\" class=\"btn_xxxx\"><li class=\"btn_xxxx_sc\"><a href=\"#\">删除</a></li><li class=\"btn_xxxx_bj\"><a id=\"bj_"+data.index+"\" href=\"#\">编辑</a></li><input type=\"hidden\" value=\""
			+data.values["globalid"]+"\"/><li class=\"btn_xxxx_bc\" style=\"display:none;\"><a id=\"bc_"+data.index+"\" href=\"#\">保存</a></li></ul>"
	    	+"<table id=\"table_"+data.clazz.guid+"_"+data.index+"\" width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
	    	+	"<tbody></tbody></table>";
		    var content = $(html);
		    $(content).find(".btn_xxxx_sc a").click(function(){
		    	if($(".demo_college").find("#newData").length>0){
					showWarning(message());
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}else{
					$.post("<%=request.getContextPath()%>/baseinfo/forminfoutil_removeDynaBean.html?formInfoTypeId=${formInfoTypeId }&infoClassId="+
		                    data.clazz.guid+"&index="+data.index);
					$("#ul_"+data.clazz.guid+"_"+data.index).remove();
                    $("#table_"+data.clazz.guid+"_"+data.index).remove();
                    $("#item_"+data.clazz.guid).find(".demo_add_02").show();
				}
		    });
		    $(content).find(".btn_xxxx_bj a").click(function(){
		    	if($("#newData").length>0){
					showWarning(message());
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
		    	var title = $(this).closest("div[class='con after']");
				var index = $(this)[0].id.split("_")[1];
		    	getEntityToEdit(title,index);
			    });
		    $(content).find(".btn_xxxx_bc a").click(function(){
				$(content).last().wrap("<form id=\"form\"></form>");
				var param = $("#form").serialize();
				$(content).last().unwrap();
				$(content).find(".btn_xxxx_bj").show();
				$(this).parent().hide();
				var title = $(this).closest("#container_"+data.clazz.guid);
				var index = $(this)[0].id.split("_")[1];
				updateEntity(param,title,index);
				$(content).find("[name='view']").show();
				
			    });
			    
		    var pos = $(content).find("tbody");
		    var zpView='';
			var c_=0;
			var zp_=0;
			var a=0;
			for(var cnt=0;cnt<$(data.viewables).length;cnt++){
				var prop = data.viewables[cnt];
				var pNameValue = data.viewHtml[prop.fieldName]
		        if(pNameValue==undefined){
		            pNameValue = "";
		        }
		        if(prop.fieldName=='zp'){
		            zp_=1;
		            //$(pos).append("<td width='35%' name='view' style=\"vertical-align:top;\">"+pNameValue+"</td>");
		            zpView = "<th width='15%' rowspan='"+num+"' ><span name='p'>"+prop.name+"</span></th><td rowspan='"+num+"' width='25%' name='view'>"+pNameValue+"</td>";
		            //$(pos).append(row);
		            if (displayNum==1) {
		                var row = $("<tr></tr>").append(zpView);
		                $(pos).append(row);
		            }
		            break;
		        }
		    }
		    for(var cnt=0;cnt<$(data.viewables).length;cnt++){
			    var prop = data.viewables[cnt];
			    var pNameValue = data.viewHtml[prop.fieldName]
		        if(pNameValue==undefined){
		            pNameValue = "";
		        }
		        
		        if(prop.fieldName=='zp'){
		            continue;
		        }
			    var size = $(pos).find("tr").length;
                /**
                 * 当前存在行数小于 照片占用行数 则计入影响行
                 */
                if (size<(num+1)*zp_) {
                    c_+=size-a;
                    a=size;
                }
                
                if(c_%displayNum==0){
                	var row = $("<tr></tr>").append("<th width='14%'>"+prop.name+"</th><td width='30%' name='view'>"+data.viewHtml[prop.fieldName]+"</td>");
                    if(cnt==0){
                        $(row).append("<input name=\"globalid\" type=\"hidden\" value=\""+data.values["globalid"]+"\"/>");
                    }
                    $(pos).append(row);
                }else{
                    $(pos).find("tr:last").append("<th width='14%'>"+prop.name+"</th><td width='30%' name='view'>"+data.viewHtml[prop.fieldName]+"</td>");
                }
                
                c_++;
			}

		    /**拼接照片*/
            if(displayNum>1&&zpView!='')
                $(pos).find("tr:first").append(zpView);

            /**填入空TD*/
            for ( ;c_%displayNum!=0; c_++) {
                $(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
            }
		    return content;
		}

		function addInputForm(data,isNew){
			var html = "<ul class=\"btn_xxxx\" id=\"newData\"><li class=\"btn_xxxx_cx\"><a id=\"cx_"+data.index+"\" href=\"#\">撤销</a></li><li class=\"btn_xxxx_bc\"><a id=\"bc_"+data.index+"\" href=\"#\">保存</a></ul>"
		    	+"<table id=\"newDataTable\" width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">"
		    	+	"<tbody></tbody></table>";
	    	var content = $(html);
	    	$(content).find(".btn_xxxx_bc a").click(function(){
	        	if($(content).find("td[name='edit']").length==0){
	        		showWarning("请检查信息类配置及可编辑的属性");
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
	        	}
				$(content).last().wrap("<form id=\"form\"></form>");
				var param = $("#form").serialize();
				$(content).last().unwrap();
				var title = $(this).closest("#container_"+data.clazz.guid);
				var index = $(this)[0].id.split("_")[1];
				updateEntity(param,title,index);
			});
	    	$(content).find(".btn_xxxx_cx a").click(function(){
	    		if(isNew){
	    			$("#newData").remove();
                    $("#newDataTable").remove();
                    $("#item_"+data.clazz.guid).find(".demo_add_02").show();
	    		}else{
		        	if($(content).find("td[name='edit']").length==0){
		        		showWarning("请检查信息类配置及可编辑的属性");
						$("#war_sure").click(function(){
							divClose();
						});
						return false;
		        	}
					var title = $(this).closest("#container_"+data.clazz.guid);
					var index = $(this)[0].id.split("_")[1];
					getEntityToView(title,index);
	    		}
			});
	    	
	    	var pos = $(content).find("tbody");
	    	
	    	var c_=0;
			var zp_=0;
			var zpView='';
			var a=0;
			if (displayNum==1) {
		        num=1;
		    }
			for(var cnt=0;cnt<$(data.editables).length;cnt++){
				 var prop = data.editables[cnt];
			     if(prop.fieldName=='zp'){
			    	 var pNameValue = data.editHtml[prop.fieldName];
	                 if(pNameValue==undefined){
	                     pNameValue = "";
	                 }
			         zp_=1;
			         zpView = "<th width='15%' rowspan='"+num+"' ><span name='p'>"+prop.name+"</span></th><td rowspan='"+num+"' width='25%' name='view'>"+pNameValue+"</td>";
	                 //$(pos).append(row);
			         if (displayNum==1) {
			        	var row = $("<tr></tr>").append(zpView);
			            $(pos).append(row);
			         }
			         break;
			     }
			}
		    for(var cnt=0;cnt<$(data.editables).length;cnt++){
		    	var need="";
			    var prop = data.editables[cnt];
			    if(prop.need==true){
			    	need="*";
			    }
			    if(prop.fieldName=='zp'){
			    	//$(pos).append(row);
			    	//zp_++;
			    	continue;
			    }
			    var size = $(pos).find("tr").length;
		        /**
		         * 当前存在行数小于 照片占用行数 则计入影响行
		         */
		        if (size<(num+1)*zp_) {
		            c_+=size-a;
		            a=size;
		        }
		        
			    if(c_%displayNum==0){
			    	var row = $("<tr></tr>").append("<th width='15%'><span class=\"red\">"+need+"</span>"+prop.name+"</th>");
                    $(row).append("<td width='35%' name='edit'>"+data.editHtml[prop.fieldName]+"</td>");
                    if(cnt==0){
                        $(row).append("<input name=\"staffid\" type=\"hidden\" value=\"${staffid}\"/>");
                    }
                    $(pos).append(row);
	            }else{
	            	$(pos).find("tr:last").append("<th width='15%'><span class=\"red\">"+need+"</span>"+prop.name+"</th>");
                    $(pos).find("tr:last").append("<td width='35%' name='edit'>"+data.editHtml[prop.fieldName]+"</td>");
	            }
	            
			    c_++;
			}
		    /**拼接照片*/
		    if(displayNum>1&&zpView!='')
		        $(pos).find("tr:first").append(zpView);

		    /**填入空TD*/
		    for ( ;c_%displayNum!=0; c_++) {
		        $(pos).find("tr:last").append("<th width='15%'></th><td width='35%'></td>");
		    }
	        
		    return content;
		}
		
		$(function(){
			$btn=$(".college_title a");
			$btn.toggle(function(){
				conId = $(this).attr("id").split("_");
				conId = conId[1];
				$("#item_"+conId).hide();
				$(this).attr("class","down");
				$(this).text("展开");
			return false;
			},function(){
				conId = $(this).attr("id").split("_");
				conId = conId[1];
				$("#item_"+conId).show();
				$(this).attr("class","up");
				$(this).text("收起");
			});
			
			$("#saveBtn").click(function(){
				if($("#newData").length>0){
					showWarning(message());
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				
				$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_findUser.html?formInfoTypeId=${formInfoTypeId}',null, function(data){
					if(data.success){
						save();
					}else{
						showConfirm("检测到已存在同名教职工 是否继续？");
				        $("#why_cancel").click(function(){
					        divClose();
					    });
				        
				        $("#why_sure").click(function(){
				        	save();
						});
					}
				});
				return false;
			});
			
			$("#cancelBtn").click(function(){
				showConfirm("确定取消录入登记表数据吗？");

				$("#why_cancel").click(function(){
					divClose();
				});
				
				$("#why_sure").click(function(){
					//divClose();
					$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_delete.html?formInfoTypeId=${formInfoTypeId}',null, function(data){
						var callback = function(){
							location.href="<%=request.getContextPath()%>/baseinfo/forminfoutil_list.html?formInfoTypeId=${formInfoTypeId}&newSession=true";
						};
						processDataCall(data, callback);
					}, "json");
					return false;
				});
			});
			
			$(".demo_add_02 a").click(function(){
				if($("#newData").length>0){
					showWarning(message());
					$("#war_sure").click(function(){
						divClose();
					});
					return false;
				}
				var id=$(this)[0].id.split("_");
				var infoClassId=id[1];
				var is_more=id[0];
				if(is_more=="false"){
					$(this).closest("div").hide();
				}else{
					$(this).closest("div").show();
				}
				
				$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_addDynaBean.html?formInfoTypeId=${formInfoTypeId}&infoClassId='+infoClassId,"dept="+dept, function(data){
					if( !data.success ) {
						tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
						$("#window-sure").click(function() {
							divClose();
						});
					}else{
						if($(data.result).length>0){
							var content = addInputForm(data.result,true);
							$(content).appendTo($("#container_"+infoClassId));
							removeButtion();
						}else{
							alert("无数据的请求");
						}
					}
				}, "json");
			});
			return false;
		});

		function save(){
			$.post('<%=request.getContextPath() %>/baseinfo/forminfoutil_save.html?formInfoTypeId=${formInfoTypeId}',null, function(data){
				var callback = function(){
					location.href="<%=request.getContextPath()%>/baseinfo/forminfoutil_list.html?formInfoTypeId=${formInfoTypeId}&newSession=true";
				};
				processDataCall(data.message,callback);
			},"json");
		}
		
		window.onload = function(){
			<% if("dept".equals(SubSystemHolder.getPropertiesValue("gh_auto_rule"))){ %>
			var deptInput="<div><input type='hidden' id='deptInput' name='deptInput' value=''/><input type='text'  class='text_nor text_sel' value='' style='width: 100px;'  readonly='readonly' onclick='codePicker(this, \"DM_DEF_ORG\");'/></div>";
            alert("请先选择部门编号便于生成职工号"+deptInput);
            $("#war_sure").click(function(){
            	dept=$("#deptInput").val();
                alertDivClose();
                $("a[class='add']")[0].click();
                }
            )
            <%}else{%>
            $("a[class='add']")[0].click();
            <%}%>
			
		}
		//删除第一个文本框的删除、撤销按钮
		function removeButtion(){
		    var a = $(".demo_college")[0];
		    $(a).find(".btn_xxxx_sc").remove();
		    $(a).find(".btn_xxxx_cx").remove();
		}
	</script>
</head>

<body>
	<input type="hidden" name="formInfoTypeId" value="${formInfoTypeId}"/>
	<c:forEach items="${infoClazzes}" var="infoClass">
		<div class="demo_college">
			<h3 class="college_title">
				<span class="title_name">${infoClass.name }</span>
				<a class="up" href="#" id="con_${infoClass.guid}">收起</a>
			</h3>
			<div class="con after" id="item_${infoClass.guid}" >
				<div id="container_${infoClass.guid}"></div>
		    	<div class="demo_add_02"><a id="${infoClass.moreThanOne}_${infoClass.guid}" class="add" href="#">添 加</a>
			</div>
			</div>
			
		</div>
	</c:forEach>
	<div class="demo_add_03"><a id="saveBtn" class="btn" href="#">提交</a><a id="cancelBtn" class="btn" href="#">取消</a></div>
</body>

</html>