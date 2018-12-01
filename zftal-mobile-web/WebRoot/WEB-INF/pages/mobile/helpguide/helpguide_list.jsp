<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/commons/hrm/head_v5.ini" %>
		<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
		 <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
	</head>
	<body>
	<div class="container_ydht sl_all_bg">
	    <div class="toolbox">
            <div class="btn-group" id="but_ancd">
              <button type="button" class="btn btn-default" id="btn_zj">增加</button>
              <button type="button" class="btn btn-default" id="btn_xg">修改</button>
              <button type="button" class="btn btn-default" id="btn_sc" >删除</button>
              <button type="button" class="btn btn-default" id="btn_qy" onclick="doControl('query.isPlayed=1')">启用</button>
              <button type="button" class="btn btn-default" id="btn_ty" onclick="doControl('query.isPlayed=0')">停用</button>
              <button type="button" class="btn btn-default" id="btn_save_index" onclick="doUpdateIndex()">排序保存</button>
              <button type="button" class="btn btn-default" id="btn_recmd" onclick="doControl('btn_recmd')">热门推荐</button>
              <button type="button" class="btn btn-default" id="btn_crecmd" onclick="doControl('btn_crecmd')">取消热门</button>
              <button type="button" class="btn btn-default" id="btn_yl">预览页面</button>
            </div>
        </div>
        <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
	    
	    <form action="<%=request.getContextPath()%>/helpguide/helpguide_list.html" name="search" id="search"  method="post">
	    <div class="form-horizontal sl_all_form">
            <input type="hidden" id="sortFieldName" name="sortFieldName" value=""/>
            <input type="hidden" id="asc" name="asc" value=""/>
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">标题</label>
                        <div class="col-xs-7">
                            <s:textfield maxlength="50" placeholder="最多50个字符" name="query.title" cssClass="form-control" theme="simple"></s:textfield>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">使用状态</label>
                        <div class="col-xs-7">
                            <s:select id="isPlayed" name="query.isPlayed" list="#{'1':'启用','0':'停用' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否热门</label>
                        <div class="col-xs-7">
                            <s:select id="isHot" name="query.isHot" list="#{'1':'是','0':'否' }" cssClass="form-control" listKey="key" listValue="value" headerKey="" headerValue="" theme="simple"></s:select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go">查询</button>
            </div>
        </div>
         <input name="minIndex" id="minIndex" type="hidden" value=""/>
        <input name="maxIndex" id="maxIndex" type="hidden" value=""/>
		<font color="red">(^_^)提示：</font>	拖动条目并保存顺序，可变更移动app端应用中心系统的显示次序
        <table class="table table-striped sl_all_bg table-bordered text-left">
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:20%;">标题</th>
                    <th style="width:11%;">创建时间</th>
                    <th style="width:11%;">是否热门</th>
                    <th style="width:7%;">使用状态</th>
                    <%--<th style="width:7%;">排序码</th>
                --%></tr>
            </thead>
            <tbody id="list_body" >
            	<s:iterator value="pageList" var="p" status="st">
                    <tr class="pxtr">
	                    <td width="5%">
	                       <input name="idckb" type="checkbox" id="id" value="${p.id }"/>
	                    	<input name="ids" type="hidden" value="${p.id}"/>
	                    	<input name="pxm" type="hidden" value="${p.orderNumber}"/>
	                    </td>
                        <td width="20%">${p.title}</td>
                        <td width="11%"><s:date name="#p.createTime" format="yyyy-MM-dd" /></td>
                        <td id="tdisHot" width="11%">
                        	<s:if test="%{#p.isHot==1 }"><span style="color:green">是</span></s:if>
                        	<s:if test="%{#p.isHot==0 }"><span style="color:red">否</span></s:if>
                        </td>
                        <td id="tdisPlayed" width="7%">
                        	<s:if test="%{#p.isPlayed==1}"><span style="color:green">启用</span></s:if>
                        	<s:if test="%{#p.isPlayed==0}"><span style="color:red">停用</span></s:if>
                        </td>
                        <%--<td width="7%" name="orderNumber">${p.orderNumber}</td>
                   --%></tr>
               </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${pageList }" query="${query }" />    
	    </form>
	</div>
<script type="text/javascript">
var heightOffset= 200;
 var idarr=new Array();
			$(function(){
				new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
			     $("#minIndex").val($(".pxtr:first").find("[name=pxm]").val());
			   	 $("#maxIndex").val($(".pxtr:last").find("[name=pxm]").val());
				$('#list_body tr').find('input').bind('click',function(e){
					e.stopPropagation();
					var $that = $(this);
					showMenu($(this).parent().parent());
					if (!$(this).prop("checked")) {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','');
					}
					$("#list_body").find('input[type="checkbox"]').each(function(){
						if ($that.val() != $(this).val()) {
							$(this).removeAttr('checked');
							$(this).parent().parent().removeClass('current');
						}
					});
					$(this).parent().parent().toggleClass('current');
				});
				$('#list_body tr').bind('click',function(e){
				showMenu($(this));
				if ($(this).find("input[type='checkbox']").length < 1) {
					    $('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','');
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','');
				}
				var status = $(this).find("input[type='checkbox']").prop('checked');
				if (status) {
					$(this).find("input[type='checkbox']").prop('checked',false);
					$('#btn_qy').css('display','');
					$('#btn_sc').css('display','');
					$('#btn_ty').css('display','');
					$('#btn_recmd').css('display','');
					$('#btn_crecmd').css('display','');
				} else {
					$(this).find("input[type='checkbox']").prop('checked',true);
				}
					$(this).siblings().find('input[id="id"]').prop('checked',false);
				});
				
				function showMenu(obj) {
					var tdstatus = obj.find('#tdisPlayed').html();
					tdstatus = (tdstatus==null?'':trim(tdstatus));
					
					var tdrecommend = obj.find('#tdisHot').html();
					tdrecommend = (tdrecommend==null?'':trim(tdrecommend));
					
					if (tdstatus == '启用') {
						$('#btn_qy').css('display','none');
						$('#btn_sc').css('display','none');
						$('#btn_ty').css('display','');
					} else {
						$('#btn_qy').css('display','');
						$('#btn_sc').css('display','');
						$('#btn_ty').css('display','none');
					}
					
					if (tdrecommend == '是') {
						$('#btn_recmd').css('display','none');
						$('#btn_crecmd').css('display','');
					} else {
						$('#btn_recmd').css('display','');
						$('#btn_crecmd').css('display','none');
					}
				}
				
				$('#list_body tr').bind('dblclick',function(){
                    var id = $(this).find("input[id='id']").val();
                    location.href="<%=request.getContextPath()%>/helpguide/helpguide_toModify.html?query.id=" + id;
				});
			});
     $("#btn_xg").click(function(){//功能条编辑按钮
     		if($("input[name='idckb']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='idckb']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }	
                toModify();
      });
    
     $("#btn_zj").click(function(){//功能条增加按钮
		location.href="<%=request.getContextPath()%>/helpguide/helpguide_toAdd.html";
     });
     
      /**
       *删除处理，只能删除停用状态的数据，单选或多选
       onclick="doControl('delete')"
       */   
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[name='idckb']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
             }else if($("input[name='idckb']:checked").length>0){
                var objChks = $("input[name='idckb']:checked");
                var idstr;
                idstr=$(objChks).val();
               /* for(var k=0;k<objChks.length;k++){
                    var opinion = $("#"+objChks[k].id).val();
                    idarr.push(opinion);
                    idstr=idarr.join(",");
                }*/
           		 removeData(idstr);
             }
     });
     $("#btn_qy").click(function(){//功能条启用按钮
           	if($("input[name='idckb']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
            }
            if($("input[name='idckb']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
           }
        //qiyong();
     });
     $("#btn_ty").click(function(){//功能条停用按钮
           	if($("input[name='idckb']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='idckb']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
            }
        	//tingyong();
     });
     
     $("#btn_yl").click(function(){
       		window.open("<%=request.getContextPath() %>/helpguide/helpguide_toView.html");
         });
            
     $("button[name='search']").click(function(e){//搜索按钮
      	$("#search").submit();
          e.preventDefault();//阻止默认的按钮事件，防止多次请求
      });
              
    function toModify(){
     	var id = $("input[id='id']:checked").val();
        var url = "<%=request.getContextPath()%>/helpguide/helpguide_toModify.html?query.id=" + id;
        window.location.href=url;
    
    }

    function doControl(arg) {
		var checked=$("input[id='id']:checked");
         if(checked.length==0){
             openAlert("请选择行", "e");
             return false;
         }else if(checked.length>1){
             openAlert("只能对单行进行操作", "e");
             return false;
         }
                    
			var id = checked.val();
			
			if (arg == "btn_crecmd") {
			    arg = 'query.isHot=0';
				openAlert("确定要取消热门吗？", "w");
			}else if(arg == "btn_recmd"){
			   arg = 'query.isHot=1';
			   openAlert("确定要设置热门吗？", "w");
			}else {
				$.post("<%=request.getContextPath() %>/helpguide/helpguide_control.html?"+arg+"&query.id="+id, null, function(data){
					if(data.success){
						openAlert(data.text, "s","auto", function(){
							location.reload();
						});
					}else{
					   openAlert(data.text,"e");
					}
				}, "json");
			}
			$("#why_sure").click(function(){
				$.post("<%=request.getContextPath() %>/helpguide/helpguide_control.html?"+arg+"&query.id="+id, null, function(data){
					if(data.success){
						openAlert(data.text, "s","auto", function(){
							location.reload();
						});
					}else{
					   openAlert(data.text,"e");
					}
				}, "json");
			});
					
	}
    
    function removeData(id){
       $.post(_path+"/helpguide/helpguide_remove.html","query.id="+id,function(data){
       		if(data.success){
               	openAlert(data.text, "s","auto", function(){
					location.reload();
				});
               }else{
                   openAlert(data.text,"e");
               }
        },"json");
	}
    
    function doUpdateIndex() {
		var action = $('#search').attr('action');
		$('#search').attr('action','<%=request.getContextPath()%>/helpguide/helpguide_updateIndex.html');
		$('#search').submit();
		$('#search').attr('action',action);
	}
		</script>
	</body>
</html>
