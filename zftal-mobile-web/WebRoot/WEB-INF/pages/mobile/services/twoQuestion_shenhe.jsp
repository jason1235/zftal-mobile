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
    	var qzList = ${ExamDyJsList};
    	var qzmcList = "";
    	var qzidList = "";
    	for(var i = 0; i < qzList.length; i++){
    		qzmcList += qzList[i].qzmc;
    		qzidList += qzList[i].qzid;
    		if(i != qzList.length-1){
    			qzmcList += ",";
    			qzidList += ",";
    		}
    	}
    	$("#qzmc").val(qzmcList);
    	$("#qzid").val(qzidList);
    	$("#fz_btn").click(function(){
    		//var qzidList = $("#qzid").val();
    		var papermainid = $("input[name='model.papermainid']").val();
    		var url = _path + "/pushMsg/pushMsg_examGroupList.html?papermainid="+papermainid;
        	$("#myModal_toji").find(".modal-content").width(800);
            $("#myModal_toji").find(".modal-content").height(570);
            $("#myFrame").height(570);
            $("#myFrame").attr("src", url);
            $("#myModal_toji").modal("show");
    	});
        $("#save_btn").click(function(){
        	var starttime = $("input[name='model.starttimeStr']").val();
        	var endtime   = $("input[name='model.endtimeStr']").val();
        	if(starttime != ""){
        		if(endtime == ""){
        			errorAlert("请设置结束时间！");
        			return false;        			
        		}
        	}
       		if(endtime != ""){
        		if(starttime == ""){
        			errorAlert("请设置结束时间！");
        			return false;
	        	}
       		}
       		if(starttime == "" || endtime == ""){
                errorAlert("选择开始时间和结束时间！");
                 return false;
             }
       		var url = "<%=request.getContextPath()%>/serviceManager/twoQuestion_saveAll.html";
        	$.post(url,$("#form_edit").serialize() ,function(data){
                if(data.success){
                	window.location.href="<%=request.getContextPath()%>/serviceManager/twoQuestion_list.html";
                }else{
                    errorAlert(data.text);
                }
                        
        	},"json");
        });
		
        //$("#btn_sz").click(function(){
        //	$("#endtime").show();
        //});
        
        
        
        $("#cancle").click(function(){
        	//window.location.href="<%=request.getContextPath()%>/serviceManager/twoQuestion_list.html";
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
        $.post("<%=request.getContextPath()%>/serviceManager/twoQuestion_addQz.html","qzid="+qzidList+"&papermainid="+papermainid ,function(data){
            if(data.success){
           		$("#qzmc").val(data.qzmc);
		        //$("#qzid").val(qzidList);
		        //$("#myModal_toji").modal("hide");
            }else{
                errorAlert(data.text);
            }
                    
    	},"json");
    }
    
    function deleteResult(result){
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
        $.post("<%=request.getContextPath()%>/serviceManager/twoQuestion_deleteQz.html","qzid="+qzidList+"&papermainid="+papermainid ,function(data){
            if(data.success){
           		$("#qzmc").val(data.qzmc);
		        //$("#qzid").val(qzidList);
		        //$("#myModal_toji").modal("hide");
            }else{
                errorAlert(data.text);
            }
                    
    	},"json");
    }
    
     </script>
</head>
<body>
<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
        <div class="container_ydht sl_all_bg">

<jsp:include page="../../globalweb/alert.jsp" flush="true"></jsp:include>
    <form action="<%=request.getContextPath()%>/serviceManager/twoQuestion_saveAll.html" name="search" id="form_edit" method="post">
        <!-- top -->
        <input type="hidden" name="model.papermainid"  value="${model.papermainid }" />
		<input type="hidden" name="model.papermainname"  value="${model.papermainname }" />
		<input type="hidden" name="model.instruction"  value="${model.instruction }" />
		<input type="hidden" name="model.remark"  value="${model.remark }" />
	<div >

		<div class="panel-body index3_con">

			<div class="index31" style="width:100%;height: 100%;" id="index">
			<div class="row panel-body " >

					<div class="col-sm-12">

						<label class="padding-10 col-sm-12"><span>问卷调查时间设置</span></label>
					</div>



					<div class="col-sm-3">

						<input size="16" style="width: 90%;" type="text" placeholder="点击设置问卷调查开始时间"  name="model.starttimeStr" value="${model.starttimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})" readonly class="form_datetime form-control Wdate">

					</div>
					<div class="col-sm-1" style="text-align:left;padding-top: 5px;padding-left: 0px;">
至
					</div>
					

					<div class="col-sm-3">
							
						<input size="16" style="width: 90%;" type="text" placeholder="点击设置问卷调查结束时间"  name="model.endtimeStr" value="${model.endtimeStr }" onfocus="WdatePicker({dateFmt:'yyyy/MM/dd HH:mm:ss'})"  readonly class="form_datetime form-control Wdate">

					</div>

				</div>
				
				<div class=" row panel-body ">

					<div class="col-sm-12">

						<button type="button" class="btn btn-primary btn-md margin_b15" id="fz_btn">编辑分组</button>
					</div>
					</div>
					<div class=" row panel-body ">
					<div class="col-sm-12">
<textarea id="qzmc" rows="3" cols="" readonly="readonly" placeholder="点击上方编辑分组按钮添加分组" style="width:100%;" class="form-control"></textarea>
                        <input type="hidden" id="qzid" />
					
					</div>

				</div>
				
				
				
				<jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
				
				
				
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