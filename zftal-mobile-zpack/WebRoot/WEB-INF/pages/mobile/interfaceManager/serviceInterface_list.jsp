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
                view();
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
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
            
     $('#list_body tr').find('input').bind('click',function(e){
     		$(this).closest("tr").find('input[id="id"]').removeAttr('checked');
			$(this).closest("tr").removeClass('current');
			var status = $(this).prop('checked');
			if (status) {
				$('#btn_qy').closest("li").hide();
				$('#btn_ty').closest("li").show();
				$('#btn_sc').closest("li").hide();
			 } else {
				$('#btn_qy').closest("li").show();
				$('#btn_ty').closest("li").hide();
				$('#btn_sc').closest("li").show();
			 }
	 });
				
	 $('#list_body tr').bind('click',function(e){
	 var status = $(this).find('input').prop('checked');
	 if (status) {
		$(this).find('input').removeAttr('checked');
	 } else {
		$(this).find('input').attr('checked',true);
	 }
	 	$(this).siblings().find('input[id="id"]').removeAttr('checked');
		$(this).toggleClass('current').siblings().removeClass('current');
		var status = $(this).find('#status').val();
		status = (status==null?'':trim(status));
		if (status == '1') {
			$('#btn_qy').closest("li").hide();
			$('#btn_ty').closest("li").show();
			$('#btn_sc').closest("li").hide();
		} else {
		    $('#btn_qy').closest("li").show();
			$('#btn_ty').closest("li").hide();
			$('#btn_sc').closest("li").show();
		}
	});
            
      fillRows("20", "", "", false);//填充空行

    });
    
    function removeData(){
	       var id = $("input[id='id']:checked").val();
	       $.post(_path+"/interfaceManager/serviceInterface_remove.html","query.classFwjkid="+id,function(data){
                if(data.success){
                	$("#search").submit();
                }else{
                    alert(data.text);
                }
            },"json");
	}
	
	
	function view(){
        var id = $("input[id='id']:checked").val();
        showWindowV1("编辑",_path+"/interfaceManager/serviceInterface_toModify.html?query.classFwjkid="+id, 800,550);
    }
    
    function add(){
        showWindowV1("增加",_path+"/interfaceManager/serviceInterface_toAdd.html", 800,550);
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
                    </ul>
            
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
  </div>
<form action="interfaceManager/serviceInterface_list.html" name="search" id="search" method="post">        
 <div class="searchtab">
    <table width="100%" border="0">
      <tbody>
        <tr>
          <th >服务名称</th>
          <td >
            <input id="fwmc" name="query.classFwmc" value="${query.classFwmc }"/>
          </td>
          <th >接口类名</th>
          <td >
           <input id="jklm" name="query.classJklm" value="${query.classJklm }"/>
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
                        <td width="15%">服务编码</td>
                        <td width="15%">服务名称</td>
                        <td width="15%">接口方法描述</td>
                        <td width="15%">接口类名</td>
                        <td width="15%">接口方法名</td>
                        <%-- <td width="10%">状态</td> --%>
                    </tr>
                </thead>
                <tbody id="list_body" >
                   <s:iterator value="list" var="menu" status="st">
                        <tr name="tr" class="data">
                        <td><input type="checkbox" id="id" value="${menu.classFwjkid}"/></td>
                        <td class="autocut" width="5%"><s:property value='#st.index+1'/> </td>
                        <td class="autocut" width="15%">${menu.classFwbm}</td>
                        <td class="autocut" width="15%">${menu.classFwmc}</td>
                        <td class="autocut" width="15%">${menu.classJkffms}</td>
                        <td class="autocut" width="15%">${menu.classJklm}</td>
                        <td class="autocut" width="15%">${menu.classJkffm}</td>
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