<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!doctype html>
<html>

<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/base_wj.css" type="text/css" media="all" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
<script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/skin/WdatePicker.css"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
<%@ include file="/commons/hrm/head_v5.ini" %>


    <script type="text/javascript">
    $(function(){
    	//var qzList = ${ExamDyJsList};
    	//var qzmcList = "";
    	//var qzidList = "";
    	//for(var i = 0; i < qzList.length; i++){
    	//	qzmcList += qzList[i].qzmc;
    	//	qzidList += qzList[i].qzid;
    	//	if(i != qzList.length-1){
    	//		qzmcList += ",";
    	//		qzidList += ",";
    	//	}
    	//}
    	//$("#qzmc").val(qzmcList);
    	//$("#qzid").val(qzidList);
    	//$("#endtime").hide();
    	new Sortable(document.getElementById("list_body"),{group:"items",animation:150});
    	//$("#fz_btn").click(function(){
    	//	var qzidList = $("#qzid").val();
    	//	var url = _path + "/pushMsg/pushMsg_examGroupList.html?qzidList="+qzidList;
        //	$("#myModal_toji").find(".modal-content").width(700);
        //    $("#myModal_toji").find(".modal-content").height(520);
        //    $("#myFrame").attr("src", url);
        //    $("#myModal_toji").modal("show");
    	//});
        $("#save_btn").click(function(){
        	//var starttime = $("input[name='model.starttimeStr']").val();
        	//var endtime   = $("input[name='model.endtimeStr']").val();
        	//if(starttime != ""){
        	//	if(endtime == ""){
        	//		errorAlert("请设置结束时间！");
        	//		return false;        			
        	//	}
        	//}
       		//if(endtime != ""){
        	//	if(starttime == ""){
        	//		errorAlert("请设置结束时间！");
        	//		return false;
	        //	}
       		//}
       		//if((starttime != "" || endtime != "") && $("input[id='id']").length==0){
            //    errorAlert("请添加题型！");
            //     return false;
            // }
       		var papermainname = $("#papermainname").val();
	       	 if(papermainname==null || papermainname==''){
	             errorAlert("问卷名称不能为空");
	             return false;
	         }
       		if($("input[id='id']").length==0){
                errorAlert("请添加题型！");
                 return false;
             }
       		var url = "<%=request.getContextPath()%>/serviceManager/question_saveAll.html";
        	$.post(url,$("#form_edit").serialize() ,function(data){
                if(data.success){
                	window.location.href="<%=request.getContextPath()%>/serviceManager/question_list.html";
                }else{
                    errorAlert(data.text);
                }
                        
        	},"json");
        });
		$("#btn_zj").click(function(){
			var papermainid = $("input[name='model.papermainid']").val();
			var url = "<%=request.getContextPath()%>/serviceManager/question_addType.html?model.papermainid="+papermainid;
	    	 $("#myModal_toji").find(".modal-content").width(750);
	         $("#myModal_toji").find(".modal-content").height(521);
	         $("#myFrame").height(521);
	         $("#myFrame").attr("src", url);
	         //$("#myModal_toji").find(".modal-content").css("margin-top","50px");
	         $("#myModal_toji").modal("show");
        });
		$("#list_body button").click(function(){
            //$(this).closest("tr").remove();
			var questionid = $(this).closest(".item").find("input[name='id']").val();
			$.post(_path+"/serviceManager/question_removeQuestion.html","questionid="+questionid,function(data){
                var callback = function(){
                	$("#"+questionid+"").remove();
                	var checkLength = $("input[name='id']").length;
                	if(checkLength == '0'){
                		$("input[name='model.starttimeStr']").val("");
                		$("input[name='model.endtimeStr']").val("");
                	}
				};
				processCall(data, callback);
            },"json");
		});
        //$("#btn_sz").click(function(){
        //	$("#endtime").show();
        //});
        
        
        
        $("#cancle").click(function(){
        	//window.location.href="<%=request.getContextPath()%>/serviceManager/question_list.html";
        	history.go(-1);
        });
    });
    
    function showResult(result){
        if( result == null ) return;
        qzmcList = "";
        qzidList = "";
        for(var i=0;i<result.length;i++){
        	qzmcList += result[i][1];
        	qzidList += result[i][0];
        	if(i != result.length-1){
        		qzmcList += ",";
        		qzidList += ",";
    		}
        }
        var papermainid = $("input[name='model.papermainid']").val();
        $.post("<%=request.getContextPath()%>/serviceManager/question_addQz.html","qzid="+qzidList+"&papermainid="+papermainid ,function(data){
            if(data.success){
		        $("#qzmc").val(qzmcList);
		        $("#qzid").val(qzidList);
		        $("#myModal_toji").modal("hide");
            }else{
                errorAlert(data.text);
            }
                    
    	},"json");
    }
    
     </script>
</head>
<body>

        <div class="container_ydht sl_all_bg">

<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
    <form action="<%=request.getContextPath()%>/serviceManager/question_save.html" name="search" id="form_edit" method="post">
        <!-- top -->
        

	<div >

		<div class="panel-body index3_con">

			<div class="index31" style="width:100%;height: 100%;" id="index">
			<%--<div class="row panel-body " >

					<div class="col-sm-12">

						<label class="padding-10 col-sm-12"><span>问卷活动时间设置</span></label>
					</div>



					<div class="col-sm-3">

						<input size="16" style="width: 90%;" type="text" placeholder="点击设置有效开始时间"  name="model.starttimeStr" value="${model.starttimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly class="form_datetime form-control Wdate">

					</div>
					<div class="col-sm-1" style="text-align:left;padding-top: 5px;padding-left: 0px;">
至
					</div>
					

					<div class="col-sm-3">
							
						<input size="16" style="width: 90%;" type="text" placeholder="点击设置有效截止时间"  name="model.endtimeStr" value="${model.endtimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})"  readonly class="form_datetime form-control Wdate">

					</div>

				</div>
				
				<div class=" row panel-body ">

					<div class="col-sm-12">

						<button type="button" class="btn btn-primary btn-md margin_b15" id="fz_btn">编辑分组</button>
					</div>
					</div>
					<div class=" row panel-body ">
					<div class="col-sm-12">
<textarea id="qzmc" rows="3" cols="" readonly="readonly" placeholder="点击左边按钮添加分组" style="width:100%;" class="form-control"></textarea>
                        <input type="hidden" id="qzid" />
					
					</div>

				</div>
				--%>
						<input type="hidden" name="model.papermainid"  value="${model.papermainid }" />
						<input type="hidden" name="op" type="text" value="${op }" />
				
				<c:if test="${op eq 'modify' }">
					
				
				<div class="panel-heading ">

					<div class="col-sm-12 title_bg">

						<label class="padding-10"><span>问卷基本信息</span></label>

					</div>

					<div class="col-lg-12 margin-t20">
						<textarea id="papermainname" maxlength="50" placeholder="问卷名称(不要超过50个字符哦^_^,不可为空)" name="model.papermainname"  value="${model.papermainname }" class="form-control" rows="3">${model.papermainname }</textarea>

					</div>

					<div class="col-lg-12 margin-t20">

						<textarea maxlength="250" class="form-control" rows="3" placeholder="问卷介绍(不要超过250个字符哦^_^)" id="instruction" name="model.instruction" value="${model.instruction}">${model.instruction}</textarea>

					</div>

					<div class="col-lg-12 margin-t20 margin-b20">

						<textarea id="remark" maxlength="100" placeholder="问卷备注(不要超过100个字符哦^_^)" name="model.remark" value="${model.remark }" class="form-control" rows="3">${model.remark }</textarea>

					</div>
					<div class="col-lg-12 margin-t20 margin-b20">仅发起人可查看结果
						<select name="model.qn_owner" class="form-control">
	                              <option value="1" <c:if test="${model.qn_owner == '1' }"> selected="selected"</c:if>>是</option>
	                              <option value="0" <c:if test="${model.qn_owner == '0' }"> selected="selected"</c:if>>否</option>
                        </select>

					</div>

				</div>
				</c:if>
				<c:if test="${op != 'modify' }">
					<input id="papermainname" type="hidden" name="model.papermainname"  value="${model.papermainname }" />
					<input  type="hidden" name="model.remark"  value="${model.remark }" />
					<input  type="hidden" name="model.instruction"  value="${model.instruction }" />
				</c:if>
				
				
				<div class=" row panel-body ">

					<div class="col-sm-3 margin-t20">

						<button type="button" class="btn btn-primary btn-md margin_b15" id="btn_zj">增加题型</button>

					</div>

				</div>
				<div class="panel-heading ">

					<div class="col-sm-12 title_bg">

						<label class="padding-10"><span>题型信息（可上下拖拽进行排序）</span></label>

					</div>

				</div>
				<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
				
				<div id="list_body">
					<c:forEach var="questionitem" items="${questionList}" varStatus="st">
				<div class=" panel-body col-sm-12 padding-30 padding-t0 item" id="${questionitem.questionid }">
				<div>
					<div style="width: 80%;float: left;">
					<label class="padding-10 col-sm-12">
					<span>${questionitem.title }</span>
					</label>
					</div>
					<div style="width: 20%;float: left;">
					<button type="button"  style="float:right;" class="btn btn-default glyphicon glyphicon-trash"></button>
					</div>
				</div>
					
					
					<div class="col-sm-12 table-bordered padding-10">
					<input type="hidden" id="id" name="id" value="${questionitem.questionid }"/>
                    <input type="hidden" value="${questionitem.sort }"/>
					<c:if test = "${questionitem.type eq '0'}"> 
                         		<c:forEach var="itemvalue" items="${questionitem.item}" varStatus="st">
                         		
                         		<div class="radio padding-l50">
									<label>
										<input type="radio" value="${st.index}" name="#st.index+1" >${itemvalue }
									</label>
								</div>
                         		
	                            
                            </c:forEach>
					   </c:if> 
					   <c:if test = "${questionitem.type eq '1'}"> 
                         		<c:forEach var="itemvalue" items="${questionitem.item}" varStatus="st">
                         		
                         		<div class="checkbox padding-l50">
									<label>
										<input type="checkbox" value="${st.index}" name="${itemvalue }">${itemvalue }
									</label>
								</div>
                         		
                            </c:forEach>
					   </c:if> 
					   <c:if test = "${questionitem.type eq '2'}"> 
					   <div class="col-sm-12 padding-0">

							<textarea class="form-control" rows="3"></textarea>
	
						</div>
                         		
					   </c:if> 
					   <c:if test = "${questionitem.type eq '3'}"> 
				   		  
                            <div class="radio padding-l50">

							<label>

								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>1分

							</label>&nbsp;&nbsp;
							<label>

								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>2分

							</label>&nbsp;&nbsp;
							<label>

								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>3分

							</label>&nbsp;&nbsp;
							<label>

								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>4分

							</label>&nbsp;&nbsp;
							<label>

								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>5分

							</label>
						</div>
                          
					   </c:if> 
					
				</div>
				</div>
					</c:forEach>
					</div>
				
				<div class="col-sm-6">

					<button type="button" class="btn btn-primary btn-md margin_b15 pull-right" id="save_btn">保存提交</button>

				</div>

				<div class="col-sm-3 margin-b20">

					<button type="button" class="btn btn-primary btn-md margin_b15" id="cancle">返回</button>

				</div>

			</div>

		</div>

	</div>

    </form>
</div>

<!-- top-end -->










</body>
</html>