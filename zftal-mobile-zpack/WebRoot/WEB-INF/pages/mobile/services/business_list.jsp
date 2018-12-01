<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
    <%--<style type="text/css">
      .autocut {
          overflow:hidden;
          white-space:normal;
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
    </style>--%>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
   	 new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
     $('#minIndex').val($('.pxtr:first').find('[name=pxm]').val());
   	 $('#maxIndex').val($('.pxtr:last').find('[name=pxm]').val());
     $("#btn_ck").click(function(){//功能条编辑按钮
          //alert($("input[name='id']:checked").length);
     		if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }	
                showbusiness();
            });
    
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
            });
            
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").closest("tr").find("input[name='procode']").val() == "999"){
            		//alert("不能删除移动门户！");
            		openAlert("不能删除移动门户！", "e");
                    return false;
            	};
            	removeData();
     });
     $("#btn_qy").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            	qiyong();
     });
     $("#btn_ty").click(function(){//功能条删除按钮
           	if($("input[name='id']:checked").length==0){
                   //alert("请先选中操作行！");
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   //alert("不能选中多行！");
                   openAlert("不能选中多行！", "e");
                    return false;
                }
            	tingyong();
     });
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
     
	 
	$("#list_body tr").bind("click",function(e){
	 	var check = $(this).find("input[name='id']").prop("checked");
	 	//alert(check);
	 	$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
		//$(this).closest("tbody").find("tr").removeClass("current");
		var procodeMax = $(this).find("input[name='procode']").val();
		var status = $(this).find("#status").val();
			//alert(check);
			if(check){
			   $("#btn_qy").show();
			   $("#btn_ty").show();
			   $("#btn_sc").show();
			}else{
			   $(this).find("input[name='id']").prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
			   if (status=="1") {
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
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			//$(this).closest("tbody").find("tr").removeClass("current");
			if(check){
			   
			}else{
			   $(this).prop("checked",true);
			   //$(this).closest("tr").attr("class","current");
			}
	 });
				
	 
            
      //fillRows("20", "", "", false);//填充空行

    });
    
    function removeData(){
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/serviceManager/business_remove.html","query.classId="+id,function(data){
                var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
            },"json");
	}
	
	function tingyong(){
	       var status = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 0){
		       //alert("该记录已停用！");
		       openAlert("该记录已停用！", "e");
		   	   return false;
	       }
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/serviceManager/business_tingyong.html","query.classId="+id,function(data){
                var callback = function(){
					$("#search").submit();
				};
							
				processCall(data, callback);
                
                //if(data.success){
                //	$("#search").submit();
                //}else{
                //    alert(data.text);
                //}
            },"json");
	}
	function qiyong(){
		   var status = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 1){
		       //alert("该记录已启用！");
		       openAlert("该记录已启用！", "e");
		   	   return false;
	       }
	       var id = $("input[name='id']:checked").val();
	       $.post(_path+"/serviceManager/business_qiyong.html","query.classId="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	       
	       
	}
	
	function showbusiness(){
        var id = $("input[name='id']:checked").val();
        var url = _path+"/serviceManager/business_toModify.html?query.classId=" + id;
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(500);
        $("#myFrame").height(500);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
        //showWindowV2("编辑",_path+"/serviceManager/business_toModify.html?query.classId="+id, 520,320);
    }
    
    function add(){
        var url = _path+"/serviceManager/business_toAdd.html";
        $("#myModal_toji").find(".modal-content").width(750);
        $("#myModal_toji").find(".modal-content").height(500);
        $("#myFrame").height(500);
        $("#myFrame").attr("src", url);
        $("#myModal_toji").modal("show");
        //showWindowV2("增加",_path+"/serviceManager/business_toAdd.html", 520,320);
    }
    
    function doUpdateIndex() {
		
		var action = $('#search').attr('action');
		$('#search').attr('action','<%=request.getContextPath()%>/serviceManager/business_updateIndex.html');
		$('#search').submit();
		$('#search').attr('action',action);
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
              <button type="button" class="btn btn-default" id="btn_save_index" onclick="doUpdateIndex()">保存顺序</button>
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="serviceManager/business_list.html" name="search" id="search" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">系统编码</label>
                        <div class="col-xs-7">
                            <input id="xtbm" maxlength="30" placeholder="最多30个字符" class="form-control" name="query.classXtbm" value="${query.classXtbm }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">系统名称</label>
                        <div class="col-xs-7">
                            <input id="xtmc" maxlength="100" placeholder="最多100个字符" name="query.classXtmc" class="form-control" value="${query.classXtmc }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">使用状态</label>
                        <div class="col-xs-7">
                            <select id="status" name="query.classSyzt" class="form-control" value="${query.classSyzt }" >
                                <option value="" ></option>
                                <option value="1" <c:if test = "${query.classSyzt eq '1'}"> selected="selected"</c:if>>启用</option>
                                <option value="0" <c:if test = "${query.classSyzt eq '0'}"> selected="selected"</c:if>>停用</option>
                            </select>
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
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:64px;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th style="width:10%;">系统编码</th>
                    <th style="width:10%;">系统名称</th>
                    <th style="width:12%;">PROCODE 编码</th>
                    <th style="width:15%;">系统描述</th>
                    <th style="width:7%;">服务数量</th>
                    <th style="width:7%;">使用状态</th>
                    <th style="width:20%;">系统地址</th>
                    <th style="width:12%;">是否第三方系统</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data pxtr">
                    <td>
                    	<input type="checkbox" id="id" name="id" value="${menu.classId }"/>
                    	<input name="ids" type="hidden" value="${menu.classId }"/>
                    	<input name="pxm" type="hidden" value="${menu.pxm }"/>
                    </td>
                    <td class="autocut" ><s:property value='#st.index+1'/> </td>
                    <td class="autocut" >${menu.classXtbm }</td>
                    <td class="autocut" >${menu.classXtmc}</td>
                    <td class="autocut" >
                    ${menu.procode}<input type="hidden" name="procode" value="${menu.procode }" id="procode"/>
                    </td>
                    <td class="autocut discription">${menu.classXtms}</td>
                    <td class="autocut" >${menu.classFwsl}</td>
                    
                    <td class="autocut" >
                    <c:if test="${menu.classSyzt eq '1' }"><span id="tdstatus" style="color:green">启用</span></c:if>
                    <c:if test="${menu.classSyzt eq '0' }"><span id="tdstatus" style="color:red">停用</span></c:if>
                    <input type="hidden" name="status" value="${menu.classSyzt }" id="status"/>
                    </td>
                    <td class="autocut" >${menu.classXtdz }</td>
                    <td class="autocut" >
                    <c:if test="${menu.otherFlag eq '0' }"><span id="tdstatus" style="color:green">否</span></c:if>
                    <c:if test="${menu.otherFlag eq '1' }"><span id="tdstatus" style="color:red">是</span></c:if>
                    </td>
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
                            <a id="btn_qy" class="btn_qd">启用</a>
                        </li>
                        <li>
                            <a id="btn_ty" class="btn_zant">停用</a>
                        </li>
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
  </div>
<form action="serviceManager/business_list.html" name="search" id="search" method="post">        
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th >系统编码</th>
          <td >
            <input id="xtbm" name="query.classXtbm" value="${query.classXtbm }"/>
          </td>
          <th >系统名称</th>
          <td >
            <input id="xtmc" name="query.classXtmc" value="${query.classXtmc }"/>
          </td>
          <th >是否启用</th>
          <td >
           <select id="status" name="query.classSyzt"  value="${query.classSyzt }" style="width:154px;">
                                <option value="" ></option>
                                <option value="1" <c:if test = "${query.classSyzt eq '1'}"> selected="selected"</c:if>>启用</option>
                                <option value="0" <c:if test = "${query.classSyzt eq '0'}"> selected="selected"</c:if>>停用</option>
           </select>
          </td>
         </tr>
      </tbody>
    </table>
    <table width="100%" border="0">
    	<tfoot>
         <tr>
            <td colspan="8">
              <div class="btn">
	              <button class="btn_cx" name="search" type="submit" id="search_go">查 询</button>
	          </div>
           </td>
          </tr>
       </tfoot>
    </table>
  </div>       
  
        <div class="formbox">
  <div class="con_overlfow">
        <table width="100%" class="dateline tablenowrap" style="table-layout:fixed;" id="tiptab">
                <thead id="list_head">
                    <tr>
                        <td width="5%"></td>
                        <td width="5%">序号</td>
                        <td width="10%">系统编码</td>
                        <td width="15%">系统名称</td>
                        <td width="15%">PROCODE 编码</td>
                        <td width="15%">系统描述</td>
                        <td width="10%">服务数量</td>
                        <td width="10%">使用状态</td>
                        <td width="15%">系统地址</td>
                        <!-- <td width="10%">状态</td> -->
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="list" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" name="id" value="${menu.classId }"/></td>
                        <td class="autocut" width="5%"><s:property value='#st.index+1'/> </td>
                        <td class="autocut" width="10%">${menu.classXtbm }</td>
                        <td class="autocut" width="15%">${menu.classXtmc}</td>
                        <td class="autocut" width="15%">
                        ${menu.procode}<input type="hidden" name="procode" value="${menu.procode }" id="procode"/>
                        </td>
                        <td class="autocut" width="15%">${menu.classXtms}</td>
                        <td class="autocut" width="10%">${menu.classFwsl}</td>
                        
                        <td class="autocut" width="10%">
                        <c:if test="${menu.classSyzt eq '1' }">启用</c:if>
                        <c:if test="${menu.classSyzt eq '0' }">停用</c:if>
                        <input type="hidden" name="status" value="${menu.classSyzt }" id="status"/>
                        </td>
                        <td class="autocut" width="15%">${menu.classXtdz }</td>
                        <!--${p.statusText }</td>-->
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
        <div>
			 <ct:page pageList="${list }" />				
		</div>
    </div>
   </div>
   </form> --%>
	</body>
</html>