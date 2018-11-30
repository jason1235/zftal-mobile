<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
    <%--<style type="text/css">
    table{
         table-layout:fixed;word-wrap:break-word;
	}
      .autocut {
          overflow:hidden;
          white-space:nowrap;
          text-overflow:ellipsis;
          -o-text-overflow:ellipsis;
          -icab-text-overflow:ellipsis;
          
          -khtml-text-overflow:ellipsis;
          -moz-text-overflow:ellipsis;
          -webkit-text-overflow:ellipsis;
      }

      .autocut:hover {
          overflow:visible;
          white-space:normal;
          word-wrap:break-word;
      }
    </style> --%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
    $('#minIndex').val($('.pxtr:first').find('[name=rdpx]').val());
	$('#maxIndex').val($('.pxtr:last').find('[name=rdpx]').val());
     $("#btn_ck").click(function(){//功能条编辑按钮
     		if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
                view();
            });
     
     $("#btn_up").click(function(){//功能条编辑按钮
     		if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
                up();
            });
     
     $("#btn_down").click(function(){//功能条编辑按钮
     		if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
                down();
            });
            
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
            });
            
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            var checked = $("input[id='id']:checked");
	        var len=String(checked.length);
	        for(i=0;i<len;i++){
	           var status = $(checked[i]).closest("tr").find("input[name='status']").val();
	           if(status == 1){
			       //alert("存在已发布状态记录,不能删除！");
			       openAlert("存在已发布状态记录,不能删除！", "e");
			   	   return false;
	           }
	        }
            	removeData();
     });
     
     $("#btn_tj").click(function(){//功能条删除按钮
        	if($("input[id='id']:checked").length==0){
                //alert("请先选中操作行！");
                openAlert("请先选中操作行！", "e");
                 return false;
             }
         var checked = $("input[id='id']:checked");
	        var len=String(checked.length);
	        for(i=0;i<len;i++){
	           var recommendFlag = $(checked[i]).closest("tr").find("input[name='recommendFlag']").val();
	           if(recommendFlag == 1){
			       //alert("存在已发布状态记录,不能删除！");
			       openAlert("存在已推荐状态记录,不能操作！", "e");
			   	   return false;
	           }
	        }
         	tjData();
  	});
     
     $("#btn_qxtj").click(function(){//功能条删除按钮
     	if($("input[id='id']:checked").length==0){
             //alert("请先选中操作行！");
             openAlert("请先选中操作行！", "e");
              return false;
          }
      var checked = $("input[id='id']:checked");
	        var len=String(checked.length);
	        for(i=0;i<len;i++){
	           var recommendFlag = $(checked[i]).closest("tr").find("input[name='recommendFlag']").val();
	           if(recommendFlag == 0){
			       //alert("存在已发布状态记录,不能删除！");
			       openAlert("存在未推荐状态记录,不能操作！", "e");
			   	   return false;
	           }
	        }
      	qxtjData();
	});
     
     
     
     
     $("#btn_qy").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            	fabu();
     });
     $("#btn_ty").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            	quxiao();
     });
     
     $('#list_body tr').find('td').find('input[name="mobile"]').bind('click',function(e){
           	var androidStatus = $(this).closest("td").find("#android").prop("checked");
           	var iosStatus     = $(this).closest("td").find("#ios").prop("checked");
           	var weixinStatus     = $(this).closest("td").find("#weixin").prop("checked");
           	var showway = "";
           	if(androidStatus) showway+=",1";
           	if(iosStatus)  showway+=",2";
           	if(weixinStatus)  showway+=",3";
           	showway = showway.substr(1);
           	//alert(showway);
           	
           	//if(androidStatus && iosStatus && weixinStatus) showway = "1,2,3";
           	//else if(androidStatus && iosStatus) showway = "1,2";
           	//else if(androidStatus && weixinStatus) showway = "1,3";
           	//else if(iosStatus && weixinStatus) showway = "2,3";
           	//else if(androidStatus) showway = "1";
           	//else if(iosStatus) showway = "2";
           	//else if(weixinStatus) showway = "3";
           	//else showway = "";
           	var classId = $(this).closest("tr").find("input[id='id']").val();
           	
           	//alert(androidStatus+"   "+iosStatus+"   "+weixinStatus);
           	$.post(_path+"/serviceManager/serviceManager_changeShow.html","showway="+showway+"&classId="+classId,function(data){
                if(data.success){
                	//location.reload();
                }else{
                   alert(data.text);
                }
            },"json");
     });
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#toPage").val(1);
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
     $('#list_body tr').find('input').bind('click',function(e){
					e.stopPropagation();
					$(this).parent().parent().toggleClass('current');
	 });      
     $('#list_body tr').bind('click',function(e){
		 var status = $(this).find('input[name="id"]').prop('checked');
		 if (status) {
			$(this).find('input[name="id"]').removeAttr('checked');
			$(this).removeClass("current");
		 } else {
		    $(this).attr("class", "current");
			$(this).find('input[name="id"]').prop('checked',true);
		 }
		 //$(this).find('input').attr('checked','checked');
		 	//$(this).siblings().find('input[id="id"]').removeAttr('checked');
			//$(this).toggleClass('current').siblings().removeClass('current');
	});
	
     //$('#list_body tr').bind('click',function(e){ //监听单击行
                    //var checkedLength = $("input[id='id']:checked").length;
                    //var check = $(this).find("input:checkbox").attr("checked");
                    //$("input:checkbox").removeAttr("checked");
                    //$("tbody > tr[name^='tr']").removeClass("current");
                    //if(check == "checked"){
                    	//$(this).removeClass("current");
                    	//$(this).find("input:checkbox").removeAttr("checked");
                    //}else{
                    	//$(this).attr("class", "current");
                    	//$(this).find("input:checkbox").attr("checked","checked");
                    //}
                //}
            //);
     
    //fillRows("20", "", "", false);//填充空行
    });
    
    function removeData(){
	        var checked = $("input[id='id']:checked");
	        $.post(_path+"/serviceManager/serviceManager_remove.html",checked.serialize(),function(data){
                if(data.success){
                	var callback = function(){
						//reflashPage();
						location.reload();
					};
                	openAlert("删除成功", "s","auto", callback);
                }else{
                    alert(data.text);
                }
            },"json");
	}
    
    function tjData(){
    	var checked = $("input[id='id']:checked");
        $.post(_path+"/serviceManager/serviceManager_recommend.html",checked.serialize(),function(data){
            if(data.success){
            	var callback = function(){
					//reflashPage();
					location.reload();
				};
            	openAlert("推荐成功", "s","auto", callback);
            }else{
                alert(data.text);
            }
        },"json");
    }
    
    function qxtjData(){
    	var checked = $("input[id='id']:checked");
        $.post(_path+"/serviceManager/serviceManager_unrecommend.html",checked.serialize(),function(data){
            if(data.success){
            	var callback = function(){
					//reflashPage();
					location.reload();
				};
            	openAlert("取消成功", "s","auto", callback);
            }else{
                alert(data.text);
            }
        },"json");
    }
	
	function quxiao(){
	       var checked = $("input[id='id']:checked");
	       var len=String(checked.length);
	       for(i=0;i<len;i++){
	           var status = $(checked[i]).closest("tr").find("input[name='status']").val();
	           if(status == 0){
			       //alert("存在未发布状态记录！");
			       openAlert("存在未发布状态记录！", "e");
			   	   return false;
	           }
	       }
	       $.post(_path+"/serviceManager/serviceManager_quxiao.html",checked.serialize(),function(data){
                if(data.success){
                	var callback = function(){
						//reflashPage();
						location.reload();
					};
                	openAlert("取消发布成功", "s","auto", callback);
                }else{
                    alert(data.text);
                }
            },"json");
	}
	function fabu(){
		   var checked = $("input[id='id']:checked");
	       var len=String(checked.length);
	       for(i=0;i<len;i++){
	           var status = $(checked[i]).closest("tr").find("input[name='status']").val();
	           if(status == 1){
			       //alert("存在已发布状态记录！");
			       openAlert("存在已发布状态记录！", "e");
			   	   return false;
	           }
	       }
	       var id = $("input[id='id']:checked").val();
	       $.post(_path+"/serviceManager/serviceManager_fabu.html",checked.serialize(),function(data){
                if(data.success){
                	var callback = function(){
						//reflashPage();
						location.reload();
					};
                	openAlert("发布成功", "s","auto", callback);
                }else{
                    alert(data.text);
                }
            },"json");
	}
	function view(){
        var id = $("input[id='id']:checked").val();
        var url = _path+"/serviceManager/serviceManager_toModify.html?query.classId=" + id;
        //$("#myModal_toji").find(".modal-content").width(750);
        //$("#myModal_toji").find(".modal-content").height(1100);
        //$("#myFrame").attr("src", url);
        //$("#myModal_toji").modal("show");
        window.location.href=url;
        //showWindowV1("编辑",_path+"/serviceManager/serviceManager_toModify.html?query.classId="+id, 750,700);
    }
    
    function add(){
        var url = _path+"/serviceManager/serviceManager_toAdd.html";
        //$("#myModal_toji").find(".modal-content").width(750);
        //$("#myModal_toji").find(".modal-content").height(1100);
        //$("#myFrame").attr("src", url);
        //$("#myModal_toji").modal("show");
        window.location.href=url;
        //showWindowV1("增加",_path+"/serviceManager/serviceManager_toAdd.html", 750,700);
    }
    
    function up(jsdm,jsmc){
       var id = $("input[id='id']:checked").val();
       $.post(_path+"/serviceManager/serviceManager_up.html","model.classId="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	function down(jsdm,jsmc){
	   var id = $("input[id='id']:checked").val();
       $.post(_path+"/serviceManager/serviceManager_down.html","model.classId="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	
	function doUpdateIndex() {
					
					var action = $('#search').attr('action');
					$('#search').attr('action','<%=request.getContextPath()%>/serviceManager/serviceManager_updateIndex.html');
					$('#search').submit();
					$('#search').attr('action',action);
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
    </script>
    </head>
  <body>
  <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
  <div class="container_ydht sl_all_bg">
      <div class="toolbox">
          <div class="btn-group" id="but_ancd">
            <button type="button" class="btn btn-default" id="btn_zj">增加</button>
            <button type="button" class="btn btn-default" id="btn_ck">修改</button>
            <button type="button" class="btn btn-default" id="btn_sc">删除</button>
            <button type="button" class="btn btn-default" id="btn_qy">发布</button>
            <button type="button" class="btn btn-default" id="btn_ty">取消发布</button>
            <button type="button" class="btn btn-default" id="btn_tj">推荐</button>
            <button type="button" class="btn btn-default" id="btn_qxtj">取消推荐</button>
            <!--<button type="button" class="btn btn-default" id="btn_up">上移</button>
            <button type="button" class="btn btn-default" id="btn_down">下移</button>
            --><button type="button" class="btn btn-default" id="btn_save_index" onclick="doUpdateIndex()">保存顺序</button>
          </div>
      </div>
      
      <form action="serviceManager/serviceManager_list.html" name="search" id="search" method="post">
        <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务编码</label>
                        <div class="col-xs-7">
                            <input id="fwbm" maxlength="10" placeholder="最多10个字符" class="form-control" name="query.classFwbm" value="${query.classFwbm }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务名称</label>
                        <div class="col-xs-7">
                            <input id="fwmc" maxlength="10" placeholder="最多10个字符" name="query.classFwmc" class="form-control" value="${query.classFwmc }"/>
                        </div>
                    </div>
                </div>
                <%--
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">所属产品</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classSscp" class="form-control" value="${query.classSscp }">
                                    <option value="" ></option>
                                 <c:forEach var="menu" items="${ProductList}" varStatus="st">
                                    <option value="${menu.key}" <c:if test = "${query.classSscp eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div> --%>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">业务系统</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classSsywxt" class="form-control" value="${query.classSsywxt }" >
                                    <option value="" ></option>
                                 <c:forEach var="menu" items="${YewuList}" varStatus="st">
                                    <option value="${menu.classId}" <c:if test = "${query.classSsywxt eq menu.classId}"> selected="selected"</c:if>>${menu.classXtmc}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务类型</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classFwlx" class="form-control" value="${query.classFwlx }" >
                                    <option value="" ></option>
                                 <c:forEach var="menu" items="${ServiceTypeList}" varStatus="st">
                                    <option value="${menu.key}" <c:if test = "${query.classFwlx eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="row">
            <%--
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务类型</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classFwlx" class="form-control" value="${query.classFwlx }" >
                                    <option value="" ></option>
                                 <c:forEach var="menu" items="${ServiceTypeList}" varStatus="st">
                                    <option value="${menu.key}" <c:if test = "${query.classFwlx eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div> --%>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">发布状态</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classFbzt" class="form-control" value="${query.classFbzt }">
                                    <option value="" ></option>
                                    <option value="1" <c:if test = "${query.classFbzt eq '1'}"> selected="selected"</c:if>>发布</option>
                                    <option value="0" <c:if test = "${query.classFbzt eq '0'}"> selected="selected"</c:if>>取消</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">服务来源</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classFwly" class="form-control" value="${query.classFwly }" >
                                    <option value="" ></option>
                                 <c:forEach var="menu" items="${ServiceSourceList}" varStatus="st">
                                    <option value="${menu.key}" <c:if test = "${query.classFwly eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">是否公众服务</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.iscommon" class="form-control" value="${query.iscommon }">
                                    <option value="" ></option>
                                    <option value="0" <c:if test = "${query.iscommon eq '0'}"> selected="selected"</c:if>>否</option>
                                    <option value="1" <c:if test = "${query.iscommon eq '1'}"> selected="selected"</c:if>>是</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-xs-3">
                    <div class="form-group">
                    </div>
                </div>
            </div>
        </div>
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <button type="submit" class="btn btn-primary btn-sm" id="search_go" name="search">查询</button>
            </div>
        </div>
        <input name="minIndex" id="minIndex" type="hidden" value=""/>
        <input name="maxIndex" id="maxIndex" type="hidden" value=""/>
		            	<font color="red">(^_^)提示：</font>	拖动条目并保存顺序，可变更移动app端应用中心各服务的显示次序
        <table class="table table-striped sl_all_bg table-bordered text-left table-condensed">
            <thead id="list_head">
                <tr>
                    <th style="width:64px;"><input type="checkbox" onclick="selectAllOrCancel(this,'id');"/></th>
                    <%--<th>热度排序</th> --%>
                    <th>服务编码</th>
                    <th>服务名称</th>
                    <th>CHOICE 编码</th>
                    <%--<th>展示方式</th> --%>
                    <th>服务描述</th>
                    <%--<th>所属产品</th>--%>
                    <th>业务系统</th>
                    <th>服务类型</th>
                    <th>发布状态</th>
                    <th>服务来源</th>
                    <th>是否公众服务</th>
                    <th>是否单点</th>
                    <th>是否推荐</th>
                    <%--<th>服务地址\模块ID\应用服务</th>
                    <th>服务图标地址</th> --%>
                </tr>
            </thead>
            <tbody id="list_body" >
            <s:iterator value="list" var="menu" status="st">
                        <tr name="tr" class="data pxtr">
                        <td>
		                        <input type="checkbox" id="id" name="id" value="${menu.classId }"/>
		                        <input name="ids" type="hidden" value="${menu.classId }"/>
	                            <input name="rdpx" type="hidden" value="${menu.classRdpx }"/>
                        </td>
                        <%--<td class="autocut" width="14%">${menu.classRdpx }</td> --%>
                        <td class="autocut" width="14%">${menu.classFwbm }</td>
                        <td class="autocut" width="14%">${menu.classFwmc }</td>
                        <td class="autocut">${menu.choice }</td>
                        <%--<td class="" width="14%">
                                        <input type="checkbox" id="android" name="mobile" value="1"  
                            <c:forEach items="${menu.classShowList}" var="show"> 
                                        <c:if test="${show eq '1' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />android 
                                        
                                        <input type="checkbox" id="ios" name="mobile" value="2"  
                            <c:forEach items="${menu.classShowList}" var="show"> 
                                        <c:if test="${show eq '2' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />ios 
                                        
                                        <input type="checkbox" id="weixin" name="mobile" value="3"  
                            <c:forEach items="${menu.classShowList}" var="show"> 
                                        <c:if test="${show eq '3' }"> checked="checked"</c:if> 
                            </c:forEach>
                                        />微信 
                        </td> --%>
                        <td nowrap style="overflow:hidden;text-overflow:ellipsis;" width="14%" class="discription">${menu.classFwms }</td>
                        <%--<td class="autocut" width="14%">
                            <c:forEach items="${menu.classSscpList}" var="cplx"> 
                                <c:forEach items="${ProductList}" var="li"> 
                                        <c:if test="${cplx eq li.key }">${li.text}</c:if>
                                </c:forEach> 
                            </c:forEach> 
                        </td>--%>
                        <td class="autocut" width="14%">
                                <c:forEach items="${YewuList}" var="li"> 
                                        <c:if test="${menu.classSsywxt eq li.classId }">${li.classXtmc}</c:if>
                                </c:forEach> 
                                <c:if test = "${menu.classSsywxt eq 'application'}">第三方应用</c:if>
                        </td>
                        <td class="autocut" width="10%">
                            
                                <c:forEach items="${ServiceTypeList}" var="li"> 
                                        <c:if test="${menu.classFwlx eq li.key }">${li.text}</c:if>
                                </c:forEach> 
                            
                        </td>
                        <td class="autocut" width="10%">
                            <c:if test="${menu.classFbzt eq '1' }"><span id="tdstatus" style="color:green">发布</span></c:if>
                            <c:if test="${menu.classFbzt eq '0' }"><span id="tdstatus" style="color:red">取消</span></c:if>
                            <input type="hidden" name="status" value="${menu.classFbzt }"/>
                        </td>
                        <td class="autocut" width="10%">
                            <c:forEach items="${ServiceSourceList}" var="li"> 
                                    <c:if test="${menu.classFwly eq li.key }">${li.text}</c:if>
                            </c:forEach> 
                        </td>
                        <td class="autocut" width="14%">
                            <c:if test="${menu.iscommon eq '1' }"><span id="tdstatus" style="color:red">是 </span></c:if>
                            <c:if test="${menu.iscommon eq '0' }"><span id="tdstatus" style="color:green">否</span></c:if>
                            <input type="hidden" name="status" value="${menu.classFbzt }"/>
                        </td>
                        <td class="autocut" width="14%">
                            <c:if test="${menu.isSignal eq '1' }"><span id="tdstatus" style="color:red">是 </span></c:if>
                            <c:if test="${menu.isSignal eq '0' }"><span id="tdstatus" style="color:green">否</span></c:if>
                            <input type="hidden" name="status" value="${menu.isSignal }"/>
                        </td>
                        <td class="autocut" width="14%">
                            <c:if test="${menu.recommendFlag eq '1' }"><span id="tdstatus" style="color:red">是 </span></c:if>
                            <c:if test="${menu.recommendFlag eq '0' }"><span id="tdstatus" style="color:green">否</span></c:if>
                            <input type="hidden" name="recommendFlag" value="${menu.recommendFlag }"/>
                        </td>
                        <%--<td class="autocut" width="14%">
                            ${menu.classFwlj }
                            <c:if test="${menu.classAppyydz != null }">,${menu.classAppyydz }</c:if>
                        </td>
                        <td class="autocut" width="14%">
                            ${menu.classFwtbdz }
                        </td> --%>
                    </tr>
                    </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
  <%--
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_zj" class="btn_zj">增加</a>
                        </li>
                        <li>
                            <a id="btn_ck" class="btn_ck">修改</a>
                        </li>
                        <li>
                            <a id="btn_sc" class="btn_sc">删除</a>
                        </li>
                        <li>
                            <a id="btn_qy" class="btn_qd">发布</a>
                        </li>
                        <li>
                            <a id="btn_ty" class="btn_zant">取消</a>
                        </li>
                        <li>
                            <a id="btn_up" class="btn_sy">上移</a>
                        </li>
                        <li>
                            <a id="btn_down" class="btn_xy">下移</a>
                        </li>
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
  </div>
<form action="serviceManager/serviceManager_list.html" name="search" id="search" method="post">        
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <td >服务编码</td>
          <td >
            <input id="fwbm" name="query.classFwbm" value="${query.classFwbm }" style="width:124px;"/>
          </td>
          <td >服务名称</td>
          <td >
            <input id="fwmc" name="query.classFwmc" value="${query.classFwmc }" style="width:124px;"/>
          </td>
          <td >所属产品</td>
          <td >
           <select id="status" name="query.classSscp"  value="${query.classSscp }" style="width:126px;">
                                    <option value="" ></option>
                       			 <c:forEach var="menu" items="${ProductList}" varStatus="st">
                         		    <option value="${menu.key}" <c:if test = "${query.classSscp eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
           </select>
          </td>
         </tr>
         
         <tr>
          <td >所属业务系统</td>
          <td >
           <select id="status" name="query.classSsywxt"  value="${query.classSsywxt }" style="width:126px;">
                                    <option value="" ></option>
                       			 <c:forEach var="menu" items="${YewuList}" varStatus="st">
                         		    <option value="${menu.classId}" <c:if test = "${query.classSsywxt eq menu.classId}"> selected="selected"</c:if>>${menu.classXtmc}</option>
                                 </c:forEach>
           </select>
          </td>
         <td >服务类型</td>
          <td >
           <select id="status" name="query.classFwlx"  value="${query.classFwlx }" style="width:126px;">
                                    <option value="" ></option>
                       			 <c:forEach var="menu" items="${ServiceTypeList}" varStatus="st">
                         		    <option value="${menu.key}" <c:if test = "${query.classFwlx eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
           </select>
          </td>
          <td >发布状态</td>
          <td >
           <select id="status" name="query.classFbzt"  value="${query.classFbzt }" style="width:126px;">
                                    <option value="" ></option>
                         		    <option value="1" <c:if test = "${query.classFbzt eq '1'}"> selected="selected"</c:if>>发布</option>
                         		    <option value="0" <c:if test = "${query.classFbzt eq '0'}"> selected="selected"</c:if>>取消</option>
           </select>
          </td>
        </tr>
        
        <tr>
          <td >服务来源</td>
          <td >
           <select id="status" name="query.classFwly"  value="${query.classFwly }" style="width:126px;">
                                    <option value="" ></option>
                       			 <c:forEach var="menu" items="${ServiceSourceList}" varStatus="st">
                         		    <option value="${menu.key}" <c:if test = "${query.classFwly eq menu.key}"> selected="selected"</c:if>>${menu.text}</option>
                                 </c:forEach>
           </select>
          </td>
          <td colspan="4"></td>
         </tr>
      </tbody>
    </table>
    
    <table width="100%" border="0">
      <tfoot>
         <tr>
            <td colspan="8">
              <div class="btn">
	              <button class="btn_cx" name="search" type="button" id="search_go">查 询</button>
	          </div>
           </td>
          </tr>
       </tfoot>
    </table>
         
  </div>       
  
        <div class="formbox">
  <div class="con_overlfow">
        <table width="180%" class="dateline tablenowrap" style="table-layout:fixed;" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="3%" ><input type="checkbox" onclick="selectAllOrCancel(this,'id');"/></td>
                        <!--<td width="3%">序号</td>
                        --><td width="7%">热度排序</td>
                        <td width="7%">服务编码</td>
                        <td width="7%">服务名称</td>
                        <td width="10%">CHOICE 编码</td>
                        <td width="15%">展示方式</td>
                        <td width="7%">服务描述</td>
                        <td width="7%">所属产品</td>
                        <td width="7%">所属业务系统</td>
                        <td width="7%">服务类型</td>
                        <td width="7%">服务来源</td>
                        <td width="7%">是否发布</td>
                        <td width="7%" class="autocut">服务地址\模块ID\应用服务</td>
                        <td width="7%">服务图标地址</td>
                        <!-- <td width="10%">状态</td> -->
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="list" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" name="id" value="${menu.classId }"/></td>
                        <!--	
                        --><td class="autocut" width="14%">${menu.classRdpx }</td>
                        <td class="autocut" width="14%">${menu.classFwbm }</td>
                        <td class="autocut" width="14%">${menu.classFwmc }</td>
                        <td class="autocut">${menu.choice }</td>
                        <td class="autocut" width="15%">
										<input type="checkbox" id="android" name="mobile" value="1"  
	                        <c:forEach items="${menu.classShowList}" var="show"> 
										<c:if test="${show eq '1' }"> checked="checked"</c:if> 
							</c:forEach>
										/>android 
										
										<input type="checkbox" id="ios" name="mobile" value="2"  
						    <c:forEach items="${menu.classShowList}" var="show"> 
										<c:if test="${show eq '2' }"> checked="checked"</c:if> 
							</c:forEach>
										/>ios 
										
										<input type="checkbox" id="weixin" name="mobile" value="3"  
							<c:forEach items="${menu.classShowList}" var="show"> 
										<c:if test="${show eq '3' }"> checked="checked"</c:if> 
							</c:forEach>
										/>微信 
                        </td>
                        <td nowrap style="overflow:hidden;text-overflow:ellipsis;" width="15%">${menu.classFwms }</td>
                        <td class="autocut" width="14%">
                        	<c:forEach items="${menu.classSscpList}" var="cplx"> 
		                        <c:forEach items="${ProductList}" var="li"> 
										<c:if test="${cplx eq li.key }">${li.text}</c:if>
								</c:forEach> 
							</c:forEach> 
                        </td>
                        <td class="autocut" width="14%">
                        	
		                        <c:forEach items="${YewuList}" var="li"> 
										<c:if test="${menu.classSsywxt eq li.classId }">${li.classXtmc}</c:if>
								</c:forEach> 
								<c:if test = "${menu.classSsywxt eq 'application'}">第三方应用</c:if>
							
                        </td>
                        <td class="autocut" width="14%">
                        	
		                        <c:forEach items="${ServiceTypeList}" var="li"> 
										<c:if test="${menu.classFwlx eq li.key }">${li.text}</c:if>
								</c:forEach> 
							
                        </td>
                        <td class="autocut" width="14%">
	                        <c:forEach items="${ServiceSourceList}" var="li"> 
									<c:if test="${menu.classFwly eq li.key }">${li.text}</c:if>
							</c:forEach> 
                        </td>
                        <td class="autocut" width="14%">
	                        <c:if test="${menu.classFbzt eq '1' }">发布</c:if>
	                        <c:if test="${menu.classFbzt eq '0' }">取消</c:if>
	                        <input type="hidden" name="status" value="${menu.classFbzt }"/>
                        </td>
                        <td class="autocut" width="14%">
	                        ${menu.classFwlj }
	                        <c:if test="${menu.classAppyydz != null }">,${menu.classAppyydz }</c:if>
                        </td>
                        <td class="autocut" width="14%">
	                        ${menu.classFwtbdz }
                        </td>
                        <!--${p.statusText }</td>-->
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
    </div>
        <div>
			 <ct:page pageList="${list }" />				
		</div>
   </div>
   </form> --%>
	</body>
</html>