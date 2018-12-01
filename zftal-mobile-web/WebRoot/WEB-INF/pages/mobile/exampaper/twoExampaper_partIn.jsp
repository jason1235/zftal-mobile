<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html class="no-js">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1">
  <title>参与问卷调查</title>

  <!-- Set render engine for 360 browser -->
  <meta name="renderer" content="webkit">

  <!-- No Baidu Siteapp-->
  <meta http-equiv="Cache-Control" content="no-siteapp"/>

  <link rel="icon" type="image/png" href="<%=request.getContextPath() %>/assets/i/favicon.png">

  <!-- Add to homescreen for Chrome on Android -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="icon" sizes="192x192" href="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">

  <!-- Add to homescreen for Safari on iOS -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black">
  <meta name="apple-mobile-web-app-title" content="Amaze UI"/>
  <link rel="apple-touch-icon-precomposed" href="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">

  <!-- Tile icon for Win8 (144x144 + tile color) -->
  <meta name="msapplication-TileImage" content="<%=request.getContextPath() %>/assets/i/app-icon72x72@2x.png">
  <meta name="msapplication-TileColor" content="#0e90d2">

  <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/amazeui.min.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/css/app.css">
  <style type="text/css">  
  	#partInPaperMainId{
  		display: none;
  	}
  	
  	#question{
  		padding:10px;
  	}
  
    #qestitle{
  		font-size: 16px;
  		font-weight: bold;
  	}
  	
  	label{
  		font-weight: normal;
  	}
  </style>
</head>
<body>
<!--在这里编写你的代码start-->
    <!--PaperMainId,用于ajax加载问卷的所有题目-->
	<span id="partInPaperMainId" >${twoExamPaper.paperMainId }</span>
	<!--PaperMainId-->
	
	<div class="am-container" style="margin-top:30px;">
	   	<div class="am-g">
		  <div class="am-u-sm-10 am-u-sm-centered">
		  	<h4>欢迎参加本次调查,问卷名称：${twoExamPaper.paperMainName },满分为${twoExamPaper.totalSocre }</h4>
		  	<span id="welcome" style="diplay:block; float:right;"></span>
		  	<hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
		  	<div id="question"></div>
		  	<div id="nextOperation" style="text-align: center;"></div>
		  </div>
		</div>
	</div>
	
	<!--多选超过最多项提示模态框start-->
	<div class="am-modal am-modal-no-btn" tabindex="-1" id="your-modal">
	  <div class="am-modal-dialog">
	    <div class="am-modal-hd">提示
	      <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
	    </div>
	    <div class="am-modal-bd">
	      
	    </div>
	  </div>
	</div>
	<!--多选超过最多项提示模态框end-->
	
	
	<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd">
      			该问卷已经结束，无法提交！
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
	
<!--在这里编写你的代码end-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=request.getContextPath() %>/assets/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="<%=request.getContextPath() %>/assets/js/amazeui.min.js"></script>
<script type="text/javascript">
    var questionArrays = [];//所有题目数组
    var questionArraysLength = 0;//所有题目数组长度
    var question = {};//当前题目
    var results = []; //结果数组，保存填完问卷的结果
	var checkedCheckboxItemCounts = 0; //已选中的多选框数量
    
    
	//获取所有题目请求函数
	function getAllQes(){
		var partInPaperMainId = $("span#partInPaperMainId").text();
		$.ajax({
		   type: "POST",
		   url: "<%=request.getContextPath() %>/twoExampaper/twoExampaper_getAllQes.html",
		   data: "query.paperMainId="+partInPaperMainId,
		   dataType: "json",
		   scriptCharset: "utf-8",
		   success: function(msg){
		     questionArrays = msg;
		     questionArraysLength = questionArrays.length;
		     if(questionArraysLength>0){
		    	 //排序，先按type排，再按sort排
			     questionArrays.sort(function(a,b){
			     	if(a.type==b.type){
			     		return a.sort-b.sort;
			     	}else{
			     		return a.type-b.type;
			     	}
			     });
			     questionArrays.forEach(function(item,index){
			    	 item.arraySort=index;
			     });
			     console.log(questionArrays);
			     question = questionArrays[0];
			     $("#question").html("");
			     getQuestionHtml(question);
		     }
		   }
		});
	}
	
	//根据question生成html
	function getQuestionHtml(question){
		if(!!question){
		   	var questionHtml = "";
		    if(question.type==0){//单选题
		   		 questionHtml+=("&nbsp;&nbsp;"+question.title+"(单选,分值为"+question.score+")<br/>");
		   		 if(!!question.items){
			    	itemsArray = question.items.split("\r\n");
		   		 }
		   		 questionHtml+="<table class='am-table am-table-bordered'>";
		   		 itemsArray.forEach(function(item,index){
		   			questionHtml+=("&nbsp;&nbsp;<tr><td><label><input name='"+(question.questionId)+"' type='radio' value='"+(index+1)+"'/>&nbsp;"+item+"</label></td></tr>");
		   		 });
		   		 questionHtml+="</table>";
	   	    }else if(question.type==1){//多选题
	   	    	//checkedCheckboxItemCounts = 0;//将多选项数count器重置为0
	   	    	questionHtml+=("&nbsp;&nbsp;"+question.title+"(多选,最多"+question.maxItem+"项,分值为"+question.score+")<br/>");
	   	    	if(!!question.items){
		    		 itemsArray = question.items.split("\r\n");
		  		}
	   	    	questionHtml+="<table class='am-table am-table-bordered'>";
		  		itemsArray.forEach(function(item,index){
		  			 questionHtml+=("&nbsp;&nbsp;<tr><td><label><input name='"+(question.questionId)+"' type='checkbox' value='"+(index+1)+"'/>&nbsp;"+item+"</label></td></tr>");
		  		});
		  		questionHtml+="</table>";
	   	    }else if(question.type==2){//简答题
	   	    	questionHtml+=("&nbsp;&nbsp;"+question.title+"(简答)<br/>");
	   	    	questionHtml+="<form class='am-form'>";
	   	    	questionHtml+=("&nbsp;&nbsp;<textarea name='"+(question.questionId)+"' rows='4'></textarea><br/><br/>");
	   	    	questionHtml+="</form>";
	   	    }else if(question.type==3){//打分题
	   	    	questionHtml+=("&nbsp;&nbsp;"+question.title+"(打分)<br/>");
	   	    	for(var i=0;i<5;i++){
	   	    		questionHtml+=("&nbsp;&nbsp;<label><input name='"+(question.questionId)+"' type='radio' value='"+(i+1)+"'/>"+(i+1)+"分</label>&nbsp;");
	   	    	}
	   	    }else{
	   	    	questionHtml+="<strong>该问卷还没有具体的题目</strong></br>";
	   	    }
		    if(question.arraySort<questionArraysLength-1){
		    	//$("#nextOperation").html("<a id='nextQes' style='cursor:pointer;'>下一题</a>");
		    	$("#nextOperation").html("<button id='nextQes' class='am-btn am-btn-primary am-btn-sm'>下一题</button>");
		    }else{
		    	//$("#nextOperation").html("<a id='commit' style='cursor:pointer;'>提交</a>");
		    	$("#nextOperation").html("<button id='commit' class='am-btn am-btn-primary am-btn-sm'>提交</button>");
		    }
	   	    $("#question").html(questionHtml);
	   	    
	   	    //更新题目计数器
	   	    $("#welcome").html((question.arraySort+1)+"/"+questionArraysLength);
		}
	}
	
	//获取当前问题的答案
	function getCurrentQuestionVal(question){
		//获取当前题目的回答值
		var questionId = question.questionId;
		if(question.type==0){//单选题
			var danxuanVal = $("[name="+questionId+"]:checked").val();
			if(!!danxuanVal==false){
				//alert("您还未选中任何选项");
				return false;
			}
			results.push({"yhid":"","questionId":questionId,"itemValue":danxuanVal});
		}else if(question.type==1){//多选题
			var valArray=new Array();  
			$("[name="+questionId+"]:checked").each(function(){  
				valArray.push($(this).val());//向数组中添加元素  
			});  
			if(valArray.length==0){
				//alert("您还未选中任何选项");
				return false;
			}
			var valStr=valArray.join(',');//将数组元素连接起来以构建一个字符串  
			results.push({"yhid":"","questionId":questionId,"itemValue":valStr}); 
		}else if(question.type==2){//简答题
			var jiandaVal = $("[name="+questionId+"]").val();
			if(!!jiandaVal==false){
				//alert("您还未填写内容");
				return false;
			}
			results.push({"yhid":"","questionId":questionId,"itemValue":jiandaVal});
		}else if(question.type==3){//打分题
			var dafenVal = $("[name="+questionId+"]:checked").val();
			if(!!dafenVal==false){
				//alert("您还未选中任何选项");
				return false;
			}
			results.push({"yhid":"","questionId":questionId,"itemValue":dafenVal});
		}
		console.log(results);
	}

	$(function(){
		//从后台加载所有题目
		getAllQes();
		
		//点击下一题，先将该题的填写结果保存，再加载下一题的内容
		$("#nextOperation").on("click","button#nextQes", function() {
			var retVal = getCurrentQuestionVal(question);
			if(retVal==false){
				var $modal = $('#your-modal');
		        if(question.type==2){
					$('.am-modal-bd').html("您还未填写内容！");
		        }else{
		        	$('.am-modal-bd').html("您还未选中任何选项！");
		        }
				$modal.modal('open');
				return;
			}
			
			//加载下一题的内容
			if(question.arraySort<questionArraysLength){
				question = questionArrays[question.arraySort+1];
			}
			getQuestionHtml(question);
		});
		
		
		//答完最后一题，将最后一题的答案保存到结果数组中，然后将保存所有题的结果一并提交到后台进行保存
		$("#nextOperation").on("click","button#commit", function() {
			var retVal = getCurrentQuestionVal(question);
			if(retVal==false){
				var $modal = $('#your-modal');
		        if(question.type==2){
					$('.am-modal-bd').html("您还未填写内容！");
		        }else{
		        	$('.am-modal-bd').html("您还未选中任何选项！");
		        }
				$modal.modal('open');
				return;
			}
			
			//检查用户是否参与过该投票
			var partInPaperMainIdForCheck = $("span#partInPaperMainId").text();
			$.ajax({
				   type: "POST",
				   url: "<%=request.getContextPath() %>/twoExampaper/twoExampaper_checkUserPartIn.html",
				   data: "query.paperMainId="+partInPaperMainIdForCheck,
				   dataType: "json",
				   scriptCharset: "utf-8",
				   success: function(msg){
					 console.log(msg);
				     if(msg.shiFouCanyu=='yes'){
				    	 //alert("您已参与过该投票！");
				    	 /*
				    	 var $modal = $('#your-modal');
				    	 $('.am-modal-bd').html("您已参与过该投票!");
						 $modal.modal('open');
						 */
						 if(confirm("您已参与过该投票！")){
				    	    window.location.href="<%=request.getContextPath() %>/twoExampaper/twoExampaper_list.html";							 
						 }
				     }else{
				    	var partInPaperMainId = $("span#partInPaperMainId").text();
				    	$.ajax({
							   type: "POST",
							   url: "<%=request.getContextPath() %>/serviceManager/twoQuestion_checkUserHasPartIn.html?query.papermainid="+partInPaperMainId,
							   data: "query.jsonStr="+JSON.stringify(results),
							   dataType: "json",
							   scriptCharset: "utf-8",
							   success: function(msg){
								   if(msg.success){
									 //提交保存问卷结果请求
										$.ajax({
											   type: "POST",
											   url: "<%=request.getContextPath() %>/twoExampaper/twoExampaper_insertResults.html?query.paperMainId="+partInPaperMainId,
											   data: "query.jsonStr="+JSON.stringify(results),
											   dataType: "json",
											   scriptCharset: "utf-8",
											   success: function(msg){
												   if(msg.data=='success'){
												     console.log(msg);
											   	     $("#welcome").html("");//更新题目计数器置空
													 $("#question").html("<h2>谢谢您的参与!<h2>");
													 $("#nextOperation").html("<button id='backToList' class='am-btn am-btn-primary am-btn-sm'>返回</button>");
												   }
											   }
										});
								     }else{
								    	 $('#my-confirm').modal({
										        relatedTarget: this,
										        onConfirm: function(options) {
										        	window.location.href="<%=request.getContextPath() %>/twoExampaper/twoExampaper_list.html";	
										        }
										   });
								   }
							   }
						});
				    	
						
				     }
				  }
			});
			
		});
		
		//限制多选的最多选几项
		/*
		$("#question").on("click","input:checkbox",function () { 
			checkedCheckboxItemCounts++;  
			var maxItem = question.maxItem;
			if(checkedCheckboxItemCounts>maxItem){
				alert("该题最多选"+maxItem+"项");
				return false;
			}
		});
		*/
		
		//点击返回
		$("#nextOperation").on("click","button#backToList", function() {
			window.location.href="<%=request.getContextPath() %>/twoExampaper/twoExampaper_list.html";
		});
		
		//弹出限制最多选几项的模态框
	    //$('#question').on('click',"input:checkbox", function() {
		//	var $modal = $('#your-modal');
	        
	        //获取所有已选中checkbox的长度
	    //    var checkedLength = $('input:checkbox:checked').length;
		//	var maxItem = question.maxItem;
		//	if(checkedLength>maxItem){
		//		$('.am-modal-bd').html("该题最多选"+maxItem+"项");
		//		$modal.modal('open');
		//		return false;
		//	}
	    //});
	});
</script>
</body>
</html>