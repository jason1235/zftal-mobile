<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  
    <%@ include file="/commons/hrm/head_v5.ini" %>
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
      }--%>
    </style>
        <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){
    	
  	 $("#list_body tr .endtr").each(function(){
  		 var dateBetween = $(this).find("#endtime").val();
		 $(this).empty();
  		 if(dateBetween < 0){
  			$(this).html("<font color='green'>已结束 </font><input name='status' type='hidden' value='1' />");
  		 }
  		 else if(dateBetween == 0){
  			$(this).html("<font color='red'>未审核</font><input name='status' type='hidden' value='0' />");
  		 }
  		 else{
  			lazyGo(dateBetween,this); 
  		 }
  	 });
     $("#btn_ck").click(function(){//功能条编辑按钮
     		if($("input[name='id']:checked").length==0){
                   openAlert("请先选中操作行！", "e");
                    return false;
                }
            if($("input[name='id']:checked").length>1){
                   openAlert("不能选中多行！", "e");
                    return false;
                }	
            var papermainid = $("input[name='id']:checked").val();
            var status  = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
            if(status == '2'){
            	openAlert("问卷进行中，如需修改，请先停用此问卷！", "e");
                return false;
            }
            window.location.href = _path+"/serviceManager/twoQuestion_toModifyQuestion.html?query.papermainid="+papermainid;
     });
     
     
     
     $("#btn_sh").click(function(){//功能条编辑按钮
  		if($("input[name='id']:checked").length==0){
                openAlert("请先选中操作行！", "e");
                 return false;
             }
         if($("input[name='id']:checked").length>1){
                openAlert("不能选中多行！", "e");
                 return false;
             }	
         var papermainid = $("input[name='id']:checked").val();
         var status  = $("input[name='id']:checked").closest("tr").find("input[name='status']").val();
         if(status == '2'){
         	openAlert("问卷进行中，如需审核，请先停用此问卷！", "e");
             return false;
         }
         
         $.post("<%=request.getContextPath() %>/serviceManager/twoQuestion_checkHasQue.html", 
    			 "query.papermainid="+papermainid, function(data){
				if(data.success){
			         window.location.href = _path+"/serviceManager/twoQuestion_toShenHe.html?query.papermainid="+papermainid;
				}else{
					 openAlert(data.text, "e");
				}
			}, "json");
         
    });
    
     $("#btn_zj").click(function(){//功能条增加按钮
    	 var url = _path+"/serviceManager/twoQuestion_toadd.html";
    	 $("#myModal_toji").find(".modal-content").width(750);
         $("#myModal_toji").find(".modal-content").height(450);
         $("#myFrame").height(450);
         $("#myFrame").attr("src", url);
         $("#myModal_toji").modal("show");
     });
     $("#btn_jg").click(function(){//功能条增加按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
    	 var papermainid = $("input[name='id']:checked").val();
    	 window.open(_path+"/serviceManager/twoQuestion_getExamResult.html?papermainid="+papermainid);  
     });
            
     $("#btn_sc").click(function(){//功能条删除按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
      	if($("input[name='id']:checked").length>1){
             openAlert("不能选中多行！", "e");
              return false;
          }	
    	 var papermainid = $("input[name='id']:checked").val();
    	 $.post("<%=request.getContextPath() %>/serviceManager/twoQuestion_remove.html", $("#form_edit").serialize(), function(data){
				var callback = function(){
					location.reload();
				};
				processCall(data, callback);
			}, "json");
     });
     
     
     $("#btn_anwser").click(function(){//功能条删除按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
      	if($("input[name='id']:checked").length>1){
             openAlert("不能选中多行！", "e");
              return false;
          }	
    	 var papermainid = $("input[name='id']:checked").val();
    	 window.open("<%=request.getContextPath() %>/serviceManager/twoQuestion_userList.html?twoResultQuery.paperMainId="+papermainid);
     });
     
     
     $("#btn_ty").click(function(){//功能条删除按钮
    	 if($("input[name='id']:checked").length==0){
             openAlert("请先选中操作行！", "e");
              return false;
          }
      	if($("input[name='id']:checked").length>1){
             openAlert("不能选中多行！", "e");
              return false;
          }	
    	 var papermainid = $("input[name='id']:checked").val();
    	 $.post("<%=request.getContextPath() %>/serviceManager/twoQuestion_control.html", 
    			 "query.papermainid="+papermainid+"&query.isOver=0", function(data){
				var callback = function(){
					location.reload();
				};
				processCall(data, callback);
			}, "json");
     });
     
            
     $("button[name='search']").click(function(e){//搜索按钮
            	//refulsh();
            	$("#search").submit();
                e.preventDefault();//阻止默认的按钮事件，防止多次请求
            });
     
	 
     $('#list_body tr').find('input').bind('click',function(e){
			e.stopPropagation();
	 });      
	 $('#list_body tr').bind('click',function(e){
			 var status = $(this).find('input[name="id"]').prop('checked');
			 $(this).closest("tbody").find("input[name='id']").removeAttr("checked");
			 var controlStatus = $(this).find("input[name='status']").val();
			 //$(this).closest('#list_body').find('input[name="id"]').removeAttr('checked');
			 if (controlStatus == "2") {
				$("#btn_ty").show();
				$("#btn_sc").hide();
				$("#btn_ck").hide();
				$("#btn_jg").show();
				$("#btn_anwser").show();
			 } else {
				 $("#btn_ty").hide();
				 if(controlStatus == "1"){
					 $("#btn_ck").hide();
					 $("#btn_jg").show();
					 $("#btn_anwser").show();
					 $("#btn_sc").show();
					 $("#btn_sh").hide();
				 }else{
					 $("#btn_sc").show();
					 $("#btn_ck").show();
					 $("#btn_sh").show();
					 $("#btn_jg").hide();
					 $("#btn_anwser").hide();
				 }
				 if (status){
					 $("#btn_ty").show();
				 }
			 }
			 if (status) {
				$("#btn_ty").show();
				$("#btn_jg").show();
				$("#btn_anwser").show();
				$("#btn_sc").show();
				$("#btn_ck").show();
				$("#btn_sh").show();
				$(this).find('input[name="id"]').removeAttr('checked');
			 } else {
				$(this).find('input[name="id"]').prop('checked',true);
			 }
	 });
      //fillRows("20", "", "", false);//填充空行

    });
    function lazyGo(dateBetween,obj) {
    	
    	dateBetween = dateBetween - 1000;
    	var days=Math.floor(dateBetween/(24*3600*1000));
    	//计算出小时数
    	var leave1=dateBetween%(24*3600*1000);    //计算天数后剩余的毫秒数
    	var hours=Math.floor(leave1/(3600*1000));
    	//计算相差分钟数
    	var leave2=leave1%(3600*1000);      //计算小时数后剩余的毫秒数
    	var minutes=Math.floor(leave2/(60*1000));
    	//计算相差秒数
    	var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
    	var seconds=Math.round(leave3/1000);
		if (dateBetween > 0){
			obj.innerHTML = "问卷进行中，还剩：<font color='red'>"+days+"</font>天<font color='red'>"+hours+
			"</font>时<font color='red'>"+minutes+"</font>分<font color='red'>"+seconds+"</font>秒"+
			"<input name='status' type='hidden' value='2' />";
			setTimeout(function(){lazyGo(dateBetween,obj)}, 1000);
		}else{
			obj.innerHTML = "<font color='green'>已结束 </font><input name='status' type='hidden' value='1' />";
		}
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
              <button type="button" class="btn btn-default" id="btn_sh">审核</button>
              <button type="button" class="btn btn-default" id="btn_ty">停用</button>
              <button type="button" class="btn btn-default" id="btn_anwser">查看用户答案</button>
              <!-- 
             	 <button type="button" class="btn btn-default" id="btn_jg">答题统计结果查看</button>
               -->
          </div>
      </div>
      <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
      
      <form action="<%=request.getContextPath() %>/serviceManager/twoQuestion_list.html" name="search" id="form_edit" method="post">
          <div class="form-horizontal sl_all_form">
            <div class="row">
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">问卷名称</label>
                        <div class="col-xs-7">
                            <input id="Papermainname" maxlength="50" placeholder="最多50个字符" class="form-control" name="query.papermainname" value="${query.papermainname }"/>
                        </div>
                    </div>
                </div>
                
                <div class="col-xs-4">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">问卷状态</label>
                        <div class="col-xs-7">
                            <select  name="query.isOver" class="form-control" value="${query.isOver }" >
                                <option value="" ></option>
                                <option value="2" <c:if test = "${query.isOver eq '2'}"> selected="selected"</c:if>>进行中</option>
                                <option value="1" <c:if test = "${query.isOver eq '1'}"> selected="selected"</c:if>>已结束</option>
                                <option value="0" <c:if test = "${query.isOver eq '0'}"> selected="selected"</c:if>>未审核</option>
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
          <table class="table table-striped sl_all_bg table-bordered text-left" >
            <thead id="list_head">
                <tr>
                    <th style="width:5%;">选择</th>
                    <th style="width:5%;">序号</th>
                    <th >问卷名称</th>
                    <th>问卷介绍</th>
                    <th>仅发起人可查看结果</th>
                    <th>问卷状态</th>
                    <th>总分值</th>
                    <th style="width:8%;">创建者</th>
                    <th>问卷备注</th>
                    <th style="width:15%;">创建时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
                <s:iterator value="list" var="menu" status="st">
                <tr name="tr" class="data">
                    <td><input type="checkbox" id="id" name="id" value="${menu.papermainid }"/></td>
                    <td class="autocut" ><s:property value='#st.index+1'/></td>
                    <td class="autocut" >${menu.papermainname }</td>
                    <td class="autocut" >${menu.instruction}</td>
                    <td class="autocut" >
						<c:if test="${menu.qn_owner eq '0' }">
							<font color="red">否</font>
						</c:if>
						<c:if test="${menu.qn_owner eq '1' }">
							<font color="green">是</font>
						</c:if>
					</td>
                    <td class="autocut endtr" >
                    	<input value="${timeBetween }" id="endtime" type="text"/>
                    </td>
					<td class="autocut" >${menu.totalsocre}</td>
                    <td class="autocut" >${menu.creater} </td>
                    <td class="autocut" >${menu.remark}</td>
                    <td class="autocut" ><s:date name="#menu.createtime" format="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                </s:iterator>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${query }" />
      </form>
  </div>
	</body>
</html>