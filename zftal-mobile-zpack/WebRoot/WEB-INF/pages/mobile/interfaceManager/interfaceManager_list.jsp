<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head.ini" %>
    <style type="text/css">
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
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
     $("#btn_ck").click(function(){//功能条编辑按钮
     		if($("input[id='id']:checked").length==0){
                   alert("请先选中操作行！");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   alert("不能选中多行！");
                    return false;
                }
                showview();
            });
    
     $("#btn_zj").click(function(){//功能条增加按钮
			    add();
            });
            
     $("#btn_sc").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   alert("请先选中操作行！");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   alert("不能选中多行！");
                    return false;
                }
            	removeData();
     });
     $("#btn_qy").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   alert("请先选中操作行！");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   alert("不能选中多行！");
                    return false;
                }
            	qiyong();
     });
     $("#btn_ty").click(function(){//功能条删除按钮
           	if($("input[id='id']:checked").length==0){
                   alert("请先选中操作行！");
                    return false;
                }
            if($("input[id='id']:checked").length>1){
                   alert("不能选中多行！");
                    return false;
                }
            	tingyong();
     });
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
     $('#list_body tr').bind('click',function(e){
	 	var check = $(this).find('input').prop('checked');
	 	$(this).closest("tbody").find("input[id='id']").removeAttr("checked");
		$(this).closest("tbody").find("tr").removeClass("current");
		var status = $(this).find("#status").val();
			//alert(check);
			if(check){
			   $("#btn_qy").closest("li").show();
			   $("#btn_ty").closest("li").show();
			   $("#btn_sc").closest("li").show();
			}else{
			   $(this).find("input").attr("checked","true");
			   $(this).closest("tr").attr("class","current");
			   if (status=="1") {
					$("#btn_qy").closest("li").hide();
					$("#btn_ty").closest("li").show();
					$("#btn_sc").closest("li").hide();
			   } else {
					$("#btn_qy").closest("li").show();
					$("#btn_ty").closest("li").hide();
					$("#btn_sc").closest("li").show();
			   }
			   
			}
	});
	
	$("#list_body tr").find("input").bind("click",function(e){
			var check = $(this).prop("checked");
     		$(this).closest("tbody").find("input[id='id']").removeAttr("checked");
			$(this).closest("tbody").find("tr").removeClass("current");
			if(check){
			   
			}else{
			   $(this).attr("checked","true");
			   $(this).closest("tr").attr("class","current");
			}
	 });
            
      fillRows("20", "", "", false);//填充空行

    });
    
    function removeData(){
	       var id = $("input[id='id']:checked").val();
	       $.post(_path+"/interfaceManager/interfaceManager_remove.html","query.classJkid="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	
	function tingyong(){
	       var status = $("input[id='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 0){
		       alert("该记录已停用！");
		   	   return false;
	       }
	       var id = $("input[id='id']:checked").val();
	       $.post(_path+"/interfaceManager/interfaceManager_tingyong.html","query.classJkid="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	function qiyong(){
		   var status = $("input[id='id']:checked").closest("tr").find("input[name='status']").val();
	       if(status == 1){
		       alert("该记录已启用！");
		   	   return false;
	       }
	       var qbjkdz = $("input[id='id']:checked").closest("tr").find("input[name='jkdz']").val();
	       $.post("<%=request.getContextPath()%>/interfaceManager/interfaceManager_test.html",
                "qbjkdz="+ qbjkdz ,function(data){
                    if(data.success){
                       var id = $("input[id='id']:checked").val();
				       $.post(_path+"/interfaceManager/interfaceManager_qiyong.html","query.classJkid="+id,function(data){
			                if(data.success){
			                	$("#search").submit();
			                }else{
			                    alert(data.text);
			                }
			            },"json");
                    }else{
                        alert(data.text);
                    }
                            
            },"json");
	       
	       
	}
	
	function showview(){
        var id = $("input[id='id']:checked").val();
        showWindowV2("编辑",_path+"/interfaceManager/interfaceManager_toModify.html?query.classJkid="+id, 550,320);
    }
    
    function add(){
        showWindowV2("增加",_path+"/interfaceManager/interfaceManager_toAdd.html", 550,320);
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
<form action="interfaceManager/interfaceManager_list.html" name="search" id="search" method="post">        
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th >接口类名</th>
          <td >
            <input id="jklm" name="query.classJklm" value="${query.classJklm }"/>
          </td>
          <th >接口编码</th>
          <td >
            <input id="jkbm" name="query.classJkbm" value="${query.classJkbm }"/>
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
                        <!--<td width="15%">业务系统编号</td>
                        <td width="15%">业务系统名称</td>
                        -->
                        <td width="15%">接口编码</td>
                        <td width="15%">接口描述</td>
                        <td width="15%">接口类名</td>
                        <td width="15%">使用状态</td>
                        <td width="15%">接口地址</td>
                        <%-- <td width="10%">状态</td> --%>
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="list" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" value="${menu.classJkid }"/></td>
                        <td class="autocut" width="5%"><s:property value='#st.index+1'/> </td>
                        <td class="autocut" width="15%">${menu.classJkbm}</td>
                        <td class="autocut" width="15%">${menu.classJkms}</td>
                        <td class="autocut" width="15%">${menu.classJklm}</td>
                        
                        <td class="autocut" width="15%">
                        <c:if test="${menu.classSyzt eq '1' }">启用</c:if>
                        <c:if test="${menu.classSyzt eq '0' }">停用</c:if>
                        <input type="hidden" name="status" value="${menu.classSyzt }" id="status"/>
                        </td>
                        <td class="autocut" width="15%">
                        	${menu.classJkdz }
                        	<input type="hidden" name="jkdz" value="${menu.classJkdz }" id="jkdz"/>
                        </td>
                        <%--${p.statusText }</td>--%>
                    </tr>
                    </s:iterator>
                </tbody>
    </table>
        <div>
			 <ct:page pageList="${list }" />				
		</div>
    </div>
   </div>
   </form>
	</body>
</html>