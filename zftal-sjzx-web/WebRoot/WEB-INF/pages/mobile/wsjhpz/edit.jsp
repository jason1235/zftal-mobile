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
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/skin/WdatePicker.css"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.datepicker-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <!-- <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" /> -->
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
   $(function(){
   	$("#option").hide();
    //执行设置标签下，启动按钮和删除按钮显示控制
		var dsqxh;
		$("input[type='hidden'][id^='dsqzt']").each(function(i){
			dsqxh = $(this).attr("id").substring(5);
			if($("#dsqzt"+dsqxh).val()==0){
				$("#start"+dsqxh).hide();
			}else{
				$("#stop"+dsqxh).hide();
			}
	});
	
	$("#2").click(function(){				
			if($('#paramsflag').val()==1){
				return false;
			}
			var mdbs = $('select[id="mds_select_id"]').val();//数据库
 	        var mdb = $('#pmdb').val();//传参表 	       
	        var params = "mdbs="+mdbs+"&mdb="+mdb+"&params="+$('select[id="params_id"]').val()+"&wsjhmc="+encodeURI($("#wsjhConfig_wsjhmc").val());
 	      
	        $.ajax({
			       url:'<%=request.getContextPath() %>/webservice/wsjhconfig_getAllTableFieldsAndParams.html',
			       type:'POST',         //数据发送方式
			       dataType:'text',     //接受数据格式
			       data:params,
			       cache:false,
			       success:function(data){
					var array = data.split("!!");				
					var temp;
					var retu="";	
					var types="";
					if(array[1]!=""){
						$.each(array[1].split(","),function(i,n){
							temp = n.split(":");		
							retu+="<option value='"+temp[0]+"'>"+temp[0]+"</option>";							
						});
					}		       		
					var table_tbody = $("#paramsTable tbody");		       		
					table_tbody.empty();
					if(array[2]!=""&&array[2].split("@").length>0){
						$("#params_save").removeAttr("disabled");
					}else{
						return false;
					}
					if(array[3]!=""){
						$.each(array[3].split(","),function(i,n){
							temp = n.split(":");		
							types+="<option value='"+temp[0]+"'>"+temp[0]+"</option>";							
						});
					}
					$.each(array[2].split("@"),function(i,n){
						var retu_html ="";
						//alert(n);
						var value=n.split(":")[1];
						if(value==1){
							retu_html="<input type='text' name='PAGE' id='PAGE' value='"+n.split(":")[2]+"'/>";
						}else if(value==2){
							retu_html="<input type='text' name='PAGESIZE' id='PAGESIZE' value='"+n.split(":")[2]+"'/>";
						}else if(value==3){
							retu_html="<input type='text' disabled name='SIGN' id='SIGN' value='"+n.split(":")[2]+"'/>";
						}else{
							retu_html = "<select  class='params_mdbz' id='params_mdbz_"+i+"' >"+retu+"</select>";
						}
						var types_html="<select class='params_types'>"+types+"</select>";
						table_tbody.append("<tr  style='cursor:hand'>"		       				   
						+"<td class='params_fields' title="+n.split(":")[0]+">"+n.split(":")[0]+"</td>"		  
						+"<td class='params_types_td' >"+types_html+"</td>"
						+"<td class='params_mdbz_td' >"+retu_html+"</td>"	);
					     $('#params_mdbz_'+i).children("option[value="+n.split(":")[2]+"]").attr("selected",true);
					});		
			       }
			    });
 	        
		});	
		
		//字段对照 更新按钮
		$("#params_save").click(function(){			
			var pfields = "";			//方法参数属性
			var csz = "";
			var types="";	
			$("#paramsTbody").children("tr").each(function(i){			
				var fields = $.trim($(this).find(".params_fields").attr("title"));			
				if('PAGE'==fields){
					csz+=$('#PAGE').val()+",";
				}else if('PAGESIZE'==fields){
					csz+=$('#PAGESIZE').val()+",";
				}else if('SIGN'==fields){
					csz+=$('#SIGN').val()+",";
				}else{
					csz+=$(this).find('select[class="params_mdbz"]').val()+",";
				}
				types+=$(this).find('select[class="params_types"]').val()+",";
				pfields+=fields+",";
			});    
		    $.ajax({
		       url:'<%=request.getContextPath() %>/webservice/wsjhconfig_updateWsjhParamsConfig.html',
		       type:'POST',         //数据发送方式
		       dataType:'text',     //接受数据格式
		       data:{"wsjhmc":encodeURI($("#wsjhConfig_wsjhmc").val()),"pfields":pfields,"csz":csz,"types":types},
		       cache:false,
		       success:function(data){
		       		//alert("方法参数更新成功");
		       		$('#paramsflag').val(1);
		       }
	     	});
		});
		
		//字段对照标签的点击事件
		$("#3").click(function(){	
			
			if($('#zddzflag').val()==1){
				return false;
			}
	    	var mdbs = $('select[id="mds_select_id"]').val();//数据库
	    	var mdb = $('#mdb').val();//表
	    	var variable=$('#wsjhConfig_fhbl').val();
	    	var wsjhmc = $('#wsjhConfig_wsjhmc').val();
			var params = "mdbs="+mdbs+"&mdb="+mdb+"&variable="+variable+"&wsjhmc="+wsjhmc+"&params="+$('select[id="params_id"]').val();
			$.ajax({
		       url:'<%=request.getContextPath() %>/webservice/wsjhconfig_getAllTableFieldsAndTypes.html',
		       type:'POST',         //数据发送方式
		       dataType:'text',     //接受数据格式
		       data:params,
		       cache:false,
		       success:function(data){
		       		var array = data.split("!!");
		       		//返回字段
		       		var yzd = $("#yzd")
		       		yzd.empty();
		       		var temp;
		       		var retu="";
		       		if(array[0]!=""){
			       		$.each(array[0].split(","),function(i,n){		
			       			retu+="<option value='"+n+"'>"+n+"</option>";
			       			yzd.append("<option value='"+n+"'>"+n+"</option>");
			       		});
		       		}
		       		//目的字段
		       		var mdzd = $("#mdzd");
		       		mdzd.empty();
		       		if(array[1]!=""){
			       		$.each(array[1].split(","),function(i,n){
			       			temp = n.split(":");
			       			if(i==0){
			       				$("#mdzdlx").val(temp[1]);
			       				$('#mdzdlx_index').val(temp[2]);
			       			}
			       			mdzd.append("<option value='"+temp[0]+":"+temp[2]+":"+temp[1]+":"+temp[3]+"'>"+temp[0]+"</option>");
			       		});
		       		}		       		
		       		var table_tbody = $("#zddzTable tbody");
		       		var vl;
		       		table_tbody.empty();
		       		if(array[2]!=""&&array[2].split("@").length>0){
		       			$("#zddzSave").removeAttr("disabled");
		       		}else{
		       			return false;
		       		}
		       		$.each(array[2].split("@"),function(i,n){ 		       		
		       				var data = n.split(":");	
			       			if(data!=""&&data.length>0){
			       				var sfzj="";
				       			if(data[3]==1)sfzj="checked";
				       			table_tbody.append("<tr onclick=\"setRowId('dztr"+i+"')\" id='dztr"+i +"' style='cursor:hand'>"		       				   
				       			+"<td class='mbzd' title="+data[0]+">"+data[0]+"</td>"
				       			+"<td class='mbzdsjlx' title='"+data[1].split("-")[0]+"'>"+data[1].split("-")[1]+"</td>"	
				       			+"<td class='variable' title='"+data[2]+"'>"+data[2]+"</td>"		       			
				       			+"<td><input type='checkbox' class='sfzj' "+sfzj+" ></td>"
				       			+"<td><input type='checkbox' value='1' checked class='sfgx'></td>"
				       			+"<td><input type='hidden' id='jhId"+i+"' class='jhId'/><a style='color: blue;text-decoration:underline' href='javaScript:removeRow("+i+")'> 删除 </a></td></tr>");
			       			}		       			
		       		});		   
		       		  		
		       }
		    });
		});
		
		//执行设置标签页点击事件
		$("#4").click(function(){
			$("#jgzx1").hide();
			$("#jgzx2").hide();
			$("#jdxzx1").hide();
			$("#jdxzx2").hide();
		});
		
		//字段对照 增加按钮
		$("#zddzAdd").click(function(){			
			var table_tbody = $("#zddzTbody");
			var i = $("#zddzTable tbody tr").length;
			var mdzd = $.trim($("#mdzd").val().split(":")[0]);			
			var flag = false;
			$("#zddzTbody").children("tr").each(function(){
				if(mdzd==$(this).find(".mbzd").text()){
					flag = true;
					return false;
				}
			});
			if(flag){
				errorAlert("目标字段已经对照,不能重复对照！");
				return false;
			}	
		
			 var _mdzd=$("#mdzd").val().split(":")[0];
			 if(_mdzd.length>8){
			    _mdzd=_mdzd.substring(0,6)+"...";
			 }
			
			var mdzdlx = $('#mdzdlx').val();		
			var mdzdlx_index=$('#mdzdlx_index').val();
			table_tbody.append("<tr onclick=\"setRowId('dztr"+i+"')\" id='dztr"+i+"'    style='cursor:hand'>"+
			"<td class='mbzd' title='"+$("#mdzd").val().split(":")[0]+"'>"+_mdzd+"</td><td class='mbzdsjlx' title='"+mdzdlx_index+"' >"+mdzdlx+"</td><td class='variable' title='"+$("select[id='yzd']").val()+"'>"+$("select[id='yzd']").val()+"</td>"
			+"<td><input type='checkbox' value='1' class='sfzj'></td><td><input type='checkbox' checked value='1' class='sfgx'></td>"
			+"<td><input type='hidden' id='jhId"+i+"' class='jhId'/><a style='color: blue;text-decoration:underline' href='javaScript:removeRow("+i+");'> 删除 </a></td></tr>");
			$("#zddzSave").removeAttr("disabled");
		
		});
		
		//字段对照 更新按钮
		$("#zddzSave").click(function(){
			var i = $("#zddzTable tbody tr").length;	
			if(i!=0&&$("input:checkbox[class=sfzj]:checked").length==0){
				errorAlert("结果对照中至少有一个字段为主键！");
				return false;
			}
			var mbzd = "";			
			var mbzdsjlx = "";
			var sfzj ="";
			var sfgx = "";		
			var jhId="";
			var variable="";
			var lenargs=new Array();			
			$("#zddzTbody").children("tr").each(function(i){
			   var lens=$(this).val();
			   if(lens!=""){
			      var lenss=lens.split(":");
			      if(lenss[0]>lenss[1]){
			         lenargs.push($(this).find(".yzd").attr("title"));
			       }
			   }
				mbzd += $(this).find(".mbzd").attr("title")+",";
				var mjs = $(this).find(".mbzdsjlx").val();
				if(mjs==null||mjs.length==0||mjs==undefined){
					mjs=$(this).find(".mbzdsjlx").attr("title");
				}
				mbzdsjlx += mjs+",";
				var vt = $(this).find(".variable").attr("title");
				if(vt==null||vt.length==0||vt==undefined){
					vt = $(this).find('.variable').find('select').val();
				}
				variable+=vt+",";
				jhId+=$(this).find(".jhId").val()+",";
				if($(this).find(".sfzj").prop("checked")){
					sfzj += "1,"
				}else{
					sfzj += "0,";
				}
				if($(this).find(".sfgx").prop("checked")){
					sfgx += "1,"
				}else{
					sfgx += "0,";
				}
			});
            if(lenargs.length>0){
     	      if(!window.confirm("有"+lenargs+"对应字段的源字段长度大于目标字段，是否允许增加")){
					return false;
				}
           }
			var dspzt;
			if(i==0){
				dsqzt="0";	
			}else{
				dsqzt="1";
			}
			
		    $.ajax({
		       url:'<%=request.getContextPath() %>/webservice/wsjhconfig_saveWsjhdzConfig.html', //后台处理程序
		       type:'POST',         //数据发送方式
		       dataType:'text',     //接受数据格式
		       data:"wsjhmc="+encodeURI($("#wsjhConfig_wsjhmc").val())+"&variables="+variable+"&mbzd="+mbzd+"&mbzdsjlx="
		       +mbzdsjlx+"&sfzj="+sfzj+"&sfgx="+sfgx+"&dsqzt="+dsqzt+"&jhId="+jhId,
		       cache:false,
		       success:function(data){
		       		errorAlert("结果对照更新成功");
		       		$('#zddzflag').val(1);
					$("#a[id^=start]").show();
					$("#a[id^=stop]").hide();
		       }
	     	});
		});
		
		//点击关闭按钮
		$(".divclose").click(function(){
			window.location.href="<%=request.getContextPath() %>/webservice/wsjhconfig_main.html"
		});
		$(".close").click(function(){
			window.location.href="<%=request.getContextPath() %>/webservice/wsjhconfig_main.html"
		});
		
		
		$("#jgTime").blur(function(){
			if($(this).val()!=""){
				if(!isInteger($(this).val())){
					errorAlert("请输入整数！");
					$(this).val("");
					$(this).focus();
				}
				if($(this).val()<=0){
					errorAlert("请输入大于0的整数！");
					$(this).val("");
					$(this).focus();
				}
			}
		});
		
		//执行设置中增加按钮点击事件
		$("#dsqAdd").click(function(){			
			var i = $("#zxszTable tbody tr").length;
			var tp = $("#dsqlx").val();
			var qsyear = $("#qsyear").val();
			var qstime = $("#qstime").val();
			var jsyear = $("#jsyear").val();
			var jstime = $("#jstime").val();

			if($.trim($("#dsqmc").val())==""){
				errorAlert("定时器名称不能为空，请输入！");
				return false;
			}
			if(tp==1){
				if($("#dsTime").val()==""){
					errorAlert("请选择执行时间");
					return false;
				}
			}else{
				if($("#jgTime").val()==""){
					errorAlert("请输入间隔时间");
					return false;
				}
			}
			
			if(qsyear!=""||jsyear!=""){
				if(qstime==""||jstime==""||qsyear==""||jsyear==""){
					errorAlert("请选择相关时间节点");
					return false;
				}
			}
			if(qstime==""&&jstime!=""){
				errorAlert("请选择相关时间节点");
				return false;
			}
			if(qstime!=""&&jstime==""){
				errorAlert("请选择相关时间节点");
				return false;
			}			
			var qsview = "";
			var jsview = "";			
			var param="wdc.dsqmc="+$.trim($("#dsqmc").val())+"&wdc.dsqzt=0&wdc.czdxbh=1&wdc.dsqlx="+tp;
			if(qstime!=""&&qsyear!=""){
				qsview = qsyear+"-"+qstime;
				param += "&wdc.jdqssj="+qsyear+"-"+qstime;
			}else if(qstime!=""){
				qsview = qstime;
				param += "&wdc.jdqssj=0000-"+qstime
			}
			
			if(jstime!=""&&jsyear!=""){
				jsview = jsyear+"-"+jstime;
				param += "&wdc.jdjssj="+jsyear+"-"+jstime
			}else if(jstime!=""){
				jsview = jstime;
				param += "&wdc.jdjssj=0000-"+jstime;
			}

			if(tp==1){
				param += "&wdc.ddsjd="+$("#dsTime").val();
			}else{
				param += "&wdc.jgsj="+$("#jgTime").val()+"&wdc.sjdwbh="+$("#sjdw").val()
			}
			var wsjhmc = $("#wsjhConfig_wsjhmc").val();
			param+="&wdc.jhpzmc="+wsjhmc;
			
			$.ajax({
		       url:'<%=request.getContextPath() %>/webservice/wsjhconfig_saveRunSet.html', //后台处理程序
		       type:'POST',         //数据发送方式
		       dataType:'text',     //接受数据格式
		       data:param,
		       cache:false,
		       success:function(data){			    	
		       		var html;
		       		data=$.trim(data);
					if(tp==1){
						html ="<tr onclick=\"setDsqRowId('dsq"+data+"')\" id='dsq"+data+"'><input type='hidden' value='"+data+"' id='dsqxh"+data+"' />"+
						"<td>"+$("#dsqmc").val()+"</td>"+
						"<td>"+$("#dsqlx option:selected").text()+"</td>"+
						"<td>"+$("#dsTime").val()+"</td>"+
						"<td></td>"+
						"<td>"+qsview+"</td>"+
						"<td>"+jsview+"</td>"+
						"<td><a id='start"+data+"' style='color: blue;text-decoration:underline' href='javaScript:startRunset("+data+",0)'>启动</a>"+
						"<a id='stop"+data+"' style='color: blue;text-decoration:underline' href='javaScript:startRunset("+data+",1)'>停止</a>"+
						"&nbsp;&nbsp;&nbsp;<a href='javaScript:deleteRunset("+data+");' style='color: blue;text-decoration:underline'>删除</a>"+
						"</td></tr>";
					}else{
						html ="<tr onclick=\"setDsqRowId('dsq"+data+"')\" id='dsq"+data+"'><input type='hidden' value='"+data+"' id='dsqxh"+i+"' />"+
						"<td id='z1"+data+"'>"+$("#dsqmc").val()+"</td>"+
						"<td>"+$("#dsqlx option:selected").text()+"</td>"+
						"<td></td>"+
						"<td>"+$("#jgTime").val()+$("#sjdw option:selected").text()+"</td>"+
						"<td>"+qsview+"</td>"+
						"<td>"+jsview+"</td>"+
						"<td><a id='start"+data+"' style='color: blue;text-decoration:underline' href='javaScript:startRunset("+data+",0)'>启动</a>"+
						"<a id='stop"+data+"'  style='color: blue;text-decoration:underline' href='javaScript:startRunset("+data+",1)'>停止</a>"+
						"&nbsp;&nbsp;&nbsp;<a href='javaScript:deleteRunset("+data+");' style='color: blue;text-decoration:underline'>删除</a>"+
						"</td></tr>";
					}
					$("#zxszTable tbody").append(html);
					$("#stop"+data).hide();
		       }
		    });
		});
		
		//执行设置标签下定时器类型下拉框
		$("#dsqlx").change(function(){
			var vl = $(this).val();
			var qstime = $("#qstime");
			var jstime = $("#jstime");
			qstime.val("");
			jstime.val("");
			qstime.unbind("focus");
			jstime.unbind("focus");
			if(vl==1){
				$("#ddzx1").show();
				$("#ddzx2").show();				
				$("#jgzx1").hide();
				$("#jgzx2").hide();

				qstime.bind("focus",function(){
					WdatePicker({dateFmt:'MM-dd',maxDate:'#F{$dp.$D(\'jstime\')}'});
				});
				jstime.bind("focus",function(){
					WdatePicker({dateFmt:'MM-dd',minDate:'#F{$dp.$D(\'qstime\')}'});
				});
			}if(vl==2){
				$("#ddzx1").hide();
				$("#ddzx2").hide();				
				$("#jgzx1").show();
				$("#jgzx2").show();
				qstime.bind("focus",function(){
					WdatePicker({dateFmt:'MM-dd HH:mm',maxDate:'#F{$dp.$D(\'jstime\')}'});
				});
				jstime.bind("focus",function(){
					WdatePicker({dateFmt:'MM-dd HH:mm',minDate:'#F{$dp.$D(\'qstime\')}'});
				});
			}
		});
		
		//绑定事件
		$("#qstime").bind("focus",function(){
			WdatePicker({dateFmt:'MM-dd',maxDate:'#F{$dp.$D(\'jstime\')}'});
		});
		$("#jstime").bind("focus",function(){
			WdatePicker({dateFmt:'MM-dd',minDate:'#F{$dp.$D(\'qstime\')}'});
		});
		
		$("#ysjk").change(function(){
			$("#ysql").val("");
			$("#yb").val("");
		});
		
		$("#mbsjk").change(function(){
			$("#mdb").val("");
		});
		
		//隐藏实时交换
		if(!$(".sfssjh").prop("checked")){
			$("#ssjh").hide();
			
		}
		//是否实时交换改变事件
		$(".sfssjh").click(function(){
			if($(".sfssjh").prop("checked")){
				$("#ssjh").show();
			}else{
				$("#ssjhlx").val("");
				$("#bzwzd").val("");
				$("#ssjh").hide();
			}
		});
		
		$("#ssjhlx").change(function(){
			if($(this).val()=='sjc'){
				$("#ssjh th span").html("时间戳");
			}else if($(this).val()=='bzw'){
				$("#ssjh th span").html("标志位");		
			}else{
			}
		});
		
		//时间戳/标志位 onblur 
		$("#bzwzd").blur(function(){
			var ysqlstr =$("#ysql").val();
			var bzwzdstr =$(this).val();
			if(!ysqlstr==''&&!bzwzdstr==''){
			if(ysqlstr.indexOf(bzwzdstr)>=0)
			{
				//alert("blur"+$(this).val());	
			}else{
				errorAlert("请确认源表已经存在:"+$(this).val()+"字段");	
			}
			}
		});
		
		$("#mds_select_id").children("option[value="+$("#mds_id").val()+"]").attr("selected",true);
		$('#wsjh_load_id').click(function(){
		    var url =  $('#wsjhConfig_url').val();
		    if(url==null||url.length==0){
		    	errorAlert("请输入WebService URL地址，格式：http://localhost:8080/zfsjzx/services/test?wsdl");
		    	return false;
		    }else if(url.length>0&&url.lastIndexOf('?wsdl') == -1){
		    	errorAlert("请输入正确的WebService URL地址!格式：http://localhost:8080/zfsjzx/services/test?wsdl");
		    	return false;
		    }else{
		    	$.post("webservice/wsjhconfig_load.html",{"url":url},function(data){	    		
		    		var namespace = data.namespace;
		    		$('#wsjhConfig_namespace').val(namespace);
		    		var records = data.records;
		    		var wsjhConfig_operate_html = "<select name='query.operate' id='wsjhConfig_operate' style='width:408px;'>";
		    		var wsjhConfig_params_html="<select name='query.params' disabled id='params_id' style='width:408px;'>";
		    		for(var i=0;i<records.length;i++){    			
		    				wsjhConfig_operate_html+="<option value='"+records[i].methods+"' >"+records[i].methods+"</option>";	  
		    				wsjhConfig_params_html+="<option value='"+records[i].params+"' >"+records[i].params+"</option>";
		    		}
		    		wsjhConfig_operate_html+="<option value='2' >2</option>";	  
					wsjhConfig_params_html+="<option value='2' >2</option>";
		    		
		    		
		    		wsjhConfig_operate_html+="</select>";
		    		wsjhConfig_params_html+="</select>";
		    		$('#wsjhConfig_operate_td').html(wsjhConfig_operate_html);
		    		$('#wsjhConfig_params_td').html(wsjhConfig_params_html);
		    	},"json");
		    }
		});	
		
		validate();
		$('#wsjh_save_id').click(function(){
	    	var operate = $("select[name='query.operate']").val();
	    	if(operate==null||operate.length==0){
	    		errorAlert("请输入WebService  操作方法!");
	    		return false;
	    	}
	    			
	    	var mdb = $('select[id="mds_select_id"]').val();
	    	var fhbl=$('#wsjhConfig_fhbl').val();
	    	var params = $('select[id="params_id"]').val();
	    	var wsjhmc = $('#wsjhConfig_wsjhmc').val();
	    	var sjcz = $('#wsjhConfig_sjcz').val();
	    	var isSjc = $('input[name="query.isSjc"]').prop("checked");    	
	    	var isType=$('input[name="query.isType"]').prop("checked");    
	    	var gys = $('#wsjhConfig_gys').val();
	    	var zys = $('#wsjhConfig_zys').val();
	    	var oflag=$('input[name="query.oflag"]').prop("checked");
	    	
	    	$.post("webservice/wsjhconfig_update.html",{"wsjhmc":encodeURI(wsjhmc),"mdb":mdb,"wsjhzcb":$('#pmdb').val(),"wsjhb":$('#mdb').val(),"wsjhxh":$('#wsjhConfig_wsjhxh').val(),"sjcz":sjcz,"isSjc":isSjc
	    		,"url":$('#wsjhConfig_url').val(),"namespace":$('#wsjhConfig_namespace').val(),"operate":operate,"params":params,"fhbl":fhbl,"isType":isType,"gys":gys,"zys":zys,"oflag":oflag},function(data){
	    		if(data.result=='true'){
	    			//alert("更新成功！");
	    			$('#wsjhmc').val(wsjhmc);
	    			return true;
	    		}else{
	    			errorAlert("更新失败！");
	    			return false;
	    		}
	    	},"json");    	
	    });
	    
	    $('#wsjh_fhbl_id').click(function(){
	    	var params = $('select[id="params_id"]').val();
	    	if(params==null||params.length==0){
	    		errorAlert("请选择方法参数内容！");
	    		return false;
	    	}
	    	var url = $('#wsjhConfig_url').val();
	    	var namespace=$('#wsjhConfig_namespace').val();
	    	var operate = $("select[name='query.operate']").val();  
	    	$("#myModal_toji").find(".modal-content").width(400);
	        $("#myModal_toji").find(".modal-content").height(500);
	        $("#myFrame").attr("src", "<%=request.getContextPath() %>/webservice/wsjhconfig_getParamsPage.html?params="+params+"&namespace="+namespace+"&operate="+operate+"&url="+url);
	        $("#myModal_toji").modal("show");   	
	    });
	    
	    $("#mdbView").click(function(){
			var datasource = $('select[id="mds_select_id"]').val();
			if(datasource==null||datasource.length==0||datasource==''){				
				errorAlert("请选择目数据库!");
				return false;
			}
			$("#myModal_toji").find(".modal-content").width(600);
	        $("#myModal_toji").find(".modal-content").height(800);
	        $("#myFrame").attr("src", "<%=request.getContextPath() %>/webservice/wsjhconfig_getAllTable.html?datasource="+datasource+"&type=2");
	        $("#myModal_toji").modal("show"); 
		});
		
		$("#pmdbView").click(function(){
			var datasource = $('select[id="mds_select_id"]').val();
			if(datasource==null||datasource.length==0||datasource==''){				
				errorAlert("请选择目数据库!");
				return false;
			}
			$("#myModal_toji").find(".modal-content").width(600);
	        $("#myModal_toji").find(".modal-content").height(800);
	        $("#myFrame").attr("src", "<%=request.getContextPath() %>/webservice/wsjhconfig_getAllTableAndView.html?datasource="+datasource+"&type=1");
	        $("#myModal_toji").modal("show"); 
		});
		
		$("body").on("change","select[id='wsjhConfig_operate']", function () { 
			var operate= document.getElementById("wsjhConfig_operate");
			var index = operate.selectedIndex;
			var params = document.getElementById("params_id");		
			$('#params_id').val(params.options[index].value);
		});
		
		$('#wsjhConfig_isSjc_1').click(function(){		
			$('#wsjhConfig_jsSjc_div').show();
		});
		$('#wsjhConfig_isSjc_2').click(function(){		
			$('#wsjhConfig_jsSjc_div').hide();
		});
		
		var isSjc=$('input[name="query.isSjc"]').prop("checked");
		if(isSjc){
			$('#wsjhConfig_jsSjc_div').show();
		}
		$('#wsjhConfig_isType_1').click(function(){		
			$('#wsjhConfig_jsType_div').show();
		});
		$('#wsjhConfig_isType_2').click(function(){		
			$('#wsjhConfig_jsType_div').hide();
		});
		var isSjc=$('input[name="query.isType"]').prop("checked");
		if(isSjc){
			$('#wsjhConfig_jsType_div').show();
		}
		var utffzc =$('input[name="query.utffzc"]').prop("checked");
		if(utffzc){
			$('#wsjhConfig_utffzcv_div').show();
		}
		
		$('#wsjhConfig_utffzc_1').click(function(){
			$('#wsjhConfig_utffzcv_div').show();
		});
		$('#wsjhConfig_utffzc_0').click(function(){
			$('#wsjhConfig_utffzcv_div').hide();
		});
});

	function validate(){
		$("body").on("blur","#wsjhConfig_wsjhmc", function () { 
			var wsjhmc = $(this).val();
			if(wsjhmc==null||wsjhmc.length==0){
				errorAlert("请输入WebService 交换配置名称!");
				$('#wsjhConfig_wsjhmc').focus();
				return false;
			}		
		});
		$("body").on("blur","#wsjhConfig_url", function () { 
			var url = $(this).val();
			if(url==null||url.length==0){
				errorAlert("请输入WebService URL地址，格式：http://localhost:8080/zfsjzx/services/test?wsdl");
				$('#wsjhConfig_url').focus();
		    	return false;
			}		
		});
		$("body").on("blur","#wsjhConfig_namespace", function () { 
			var namespace = $(this).val();
			if(namespace==null||namespace.length==0){
				errorAlert("请输入WebService 命名空间!");
				$('#wsjhConfig_namespace').focus();
				return false;
			}
		});
		$("body").on("blur","#ydb", function () { 
			var ydb = $(this).val();
			if(ydb==null||ydb.length==0){
				errorAlert("请输入源数据表!");
				return false;
			}
		});
		$("body").on("blur","#mdb", function () { 
			var ydb = $(this).val();
			if(ydb==null||ydb.length==0){
				errorAlert("请输入目数据表!");
				return false;
			}
		});
		
		return true;	
	}

	function inputVl(vl){	
		$("#mdzdlx").val(vl.split(":")[2]); 	
		$('#mdzdlx_index').val(vl.split(":")[1]);
	}
	
	function deleteRunset(dsqxh){
		//if(!confirm("确定删除该定时器？")){
		//	return false;
		//}	
		$.ajax({
		      url:'<%=request.getContextPath() %>/webservice/wsjhconfig_deleteRunset.html', //后台处理程序
		      type:'POST',         //数据发送方式
		      dataType:'json',     //接受数据格式	      
		      data:"wdc.dsqxh="+dsqxh,
		      cache:false,
		      success:function(data){
		         if(data['success']=="true"){	        	 
		         	$("#dsq"+dsqxh).remove();
		         }else{
		         	errorAlert(data['msg']);
		         }
		      }
	   });	
	}
    
    function startRunset(dsqxh,status){
	var table_tbody = $("#zddzTable tbody");	
	var lt = table_tbody.find('tr').length;
	if(status==1&&lt==0){
		errorAlert("结果对照为空或未更新，定时器无法启动！");
		return false;
	}
	$.ajax({
	      url:'<%=request.getContextPath() %>/webservice/wsjhconfig_setRunStatus.html', //后台处理程序
	      type:'POST',         //数据发送方式
	      dataType:'json',     //接受数据格式
	      data:"wdc.dsqxh="+dsqxh+"&wdc.dsqzt="+status+"&wdc.jhpzmc="+$('#wsjhConfig_wsjhmc').val(),
	      cache:false,
	      success:function(data){
	         if(data['success']=="true"){
	         	//alert(data['msg']);
	         	if(status==0){
	         		$("#stop"+dsqxh).show();
	         		$("#start"+dsqxh).hide();
	         	}else{
	         		$("#stop"+dsqxh).hide();
	         		$("#start"+dsqxh).show();
	         	}
	         }else{
	         	errorAlert(data['msg']);
	         }
	      }
	   });	
	}
	
	function delRow(rowId){
		$("#"+rowId).remove();
		var front = rowId.substring(0,3);
		if(front=="dsq"){
			$("#zxszSave").removeAttr("disabled");
		}else{
			$("#zddzSave").removeAttr("disabled");
		}
	}
	
	function numberOnly() {
		if (!(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13) || (window.event.keyCode == 46))) {
			window.event.keyCode = 0;
			errorAlert("请输入数字!");
		}
	}
	
	function setDsqRowId(jhxh){
		$(".current").removeClass("current");
		$("#"+jhxh).addClass("current");
	}
	
	function setRowId(jhxh){
		$(".current").removeClass("current");
		$("#"+jhxh).addClass("current");
		$("#onClickRowId").val(jhxh.split('dztr')[1]);
	}
	
	function removeRow(i){	
		$("#dztr"+i).remove();
	}
	
	
	
    </script>
    </head>
  <body>
   
   <jsp:include page="../../globalweb/modal.jsp" flush="true"></jsp:include>
   
   <div class="sl_mod modal-content">
   <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">webservice编辑</h4>
            </div>
   	
   	<div class="row">
     <div id="divul" class="col-sm-12">
	<ul class="nav nav-tabs" role="tablist" id="myTab">
	  <li id='li1' role="presentation" class="active"><a id="1" href="#tab_lis1" role="tab" data-toggle="tab">webservice交换配置</a></li>
	  <li id='li2' role="presentation"><a id="2"href="#tab_lis2" role="tab" data-toggle="tab">方法传参</a></li>
	  <li id='li3' role="presentation"><a id="3" href="#tab_lis3" role="tab" data-toggle="tab">结果对照</a></li>
	  <li id='li4' role="presentation"><a id="4" href="#tab_lis4" role="tab" data-toggle="tab">执行设置</a></li>
	</ul>
   
		<div class="tab-content">
		
		<div role="tabpanel" class="tab-pane active" id="tab_lis1">
	<div class="tab" style="clear: both;overflow: auto;">
		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<tbody>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>配置名称:
                 </th>
                  <td>
                     <input id="wsjhConfig_wsjhmc" disabled maxlength="30" placeholder="不要超过30个字符哦(^_^)" name="query.wsjhmc" value="<s:property value="query.wsjhmc"/>" type="text" class="form-control"/>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>URL地址:
                 </th>
                  <td>
                  	<span>
                     <input id="wsjhConfig_url" disabled maxlength="100" placeholder="不要超过100个字符哦(^_^)" name="query.url" value="<s:property value="query.url"/>" type="text" style="width: 80%;"/>
                 	 <!--<button type="button" class="btn btn-primary" id="wsjh_load_id">加 载</button>
                  	--></span>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>命名空间:
                 </th>
                  <td>
                     <input id="wsjhConfig_namespace" disabled value="<s:property value="query.namespace"/>" maxlength="60" placeholder="不要超过60个字符哦(^_^)" name="query.namespace" type="text" class="form-control"/>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>操作方法:
                 </th>
                  <td id="wsjhConfig_operate_td">
                   <select id="wsjhConfig_operate" name="query.operate" disabled  class="form-control">
                   	<option value="<s:property value='query.operate'/>"><s:property value="query.operate"/></option>
                    
                   </select>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>方法参数:
                 </th>
                  <td id="wsjhConfig_params_td">
                   <select id="params_id" disabled name="query.params"  class="form-control">
                     <option value="<s:property value="query.params"/>"><s:property value="query.params"/></option>
                   </select>
                 </td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>返回类型:
                 </th>
                  <td >
                   <span>
	                      	<input type="radio" name="query.isType" value="1" id="wsjhConfig_isType_1" disabled <s:if test="query.isType!=null&&query.isType ==1">checked</s:if> >
	                      	XML
	                      	</input> &nbsp;&nbsp;&nbsp;&nbsp;
	                      	<input type="radio" id="wsjhConfig_isType_2" name="query.isType"  value="0" disabled <s:if test="query.isType!=null&&query.isType==0">checked</s:if> >
	                      	JSON
	                      	</input>
                   </span>
                   <div id="wsjhConfig_jsType_div" style="display:none;">
                   <span>
                   		
                   		根元素：<input id="wsjhConfig_gys" maxlength="15" placeholder="不要超过15个字符哦(^_^)" name="query.gys" value="<s:property value="query.gys"/>" disabled type="text" style="width: 30%;"/>
                   		子元素：<input id="wsjhConfig_zys" maxlength="15" placeholder="不要超过15个字符哦(^_^)" name="query.zys" disabled value="<s:property value="query.zys"/>" type="text" style="width: 30%;"/>
                   </span>
                   </div>
                 </td>
              </tr>	
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>返回变量:
                 </th>
                  <td>
                     <textarea rows="1" cols="20" name="query.fhbl" value="<s:property value="query.fhbl"/>" id="wsjhConfig_fhbl" readonly="true" style="width: 80%;height: 50px;">
                     <s:property value="query.fhbl"/>
                     </textarea>
                     <!--<button type="button" class="btn btn-primary" id="wsjh_fhbl_id">加 载</button>
                 --></td>
              </tr>
              <tr>
                 <th width="30%" >
                     <span class="red">*</span>启用时间戳:
                 </th>
                  <td>
                  <input type="radio" name="query.isSjc" value="1" id="wsjhConfig_isSjc_1" <s:if test="query.isSjc!=null&&query.isSjc ==1">checked</s:if> >
                  		是
             	  </input> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
             	  <input type="radio" id="wsjhConfig_isSjc_2" name="query.isSjc" <s:if test="query.isSjc!=null&&query.isSjc ==0">checked</s:if>    value="0" checked>
             	  		否
             	  </input>
			         <div id="wsjhConfig_jsSjc_div" style="display:none;float:right">
			         	<input type="text" id="wsjhConfig_sjcz" name="query.sjcz" value="<s:property value="query.sjcz"/>"   style="width:100px;"/>
			         </div>
			     </td>
			     </tr>
			     <tr>
				     <th width="30%" >
	                     <span class="red">*</span>目的数据库:
	                 </th>
	                 <td>
	                 <select id="mds_select_id" name="query.wsjhds" class="form-control">
			            <s:iterator value="#DB" var="o">
			    			<option value='<s:property value='#o.pzxh' />'><s:property value='#o.pzljmc' /></option>
			    		</s:iterator>
				     </select>
				     <input type="hidden" id="mds_id" value="<s:property value="query.wsjhds"/>"/>
				     </td>
           		 </tr>
           		  <tr>
				     <th width="30%" >
	                     <span class="red">*</span>传参数据表:
	                 </th>
	                 <td>
	                 <span>
	                 	<input type="text" id="pmdb" name="query.wsjhzcb" value="<s:property value="query.wsjhzcb"/>" readonly="true" style="width: 80%;"/>
	     				<a href="#" id="pmdbView">
	     				✚
	     				</a>
	                 
	                 </span>
                 </tr>
                 <tr>
				     <th width="30%" >
	                     <span class="red">*</span>目的数据表:
	                 </th>
	                 <td>
	                 	<input type="text" id="mdb" name="query.wsjhb" value="<s:property value="query.wsjhb"/>" readonly="true" style="width: 80%;"/>
	     				<a href="#" id="mdbView">
	     				✚
	     				</a>
                 </tr>
                 <tr>
                 <th width="30%" >
                     <span class="red">*</span>插入操作标志
                 </th>
                  <td>
                  <input type="radio" <s:if test="query.oflag!=null&&query.oflag ==1">checked</s:if> name="query.oflag" value="1">
                 		 是
                  </input> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="radio" <s:if test="query.oflag!=null&&query.oflag ==0">checked</s:if>  name="query.oflag" checked value="0">
                 		 否
                  </input>
			     </td>
			     </tr>
             </tbody>
		</table>
	</div>
		<div class="modal-footer">
            <button type="button" class="btn btn-primary" id="wsjh_save_id">保 存</button>
            <button type="button" class="btn btn-primary divclose" id="close_div_1">取 消</button>
        </div>
  </div>
  
  <!-- 方法传参 -->
  <div role="tabpanel" class="tab-pane" id="tab_lis2">
		<input type="hidden" id="params_wsjhmc" value="-1"/>
		<input type="hidden" id="paramsflag" value="0"/>	
		<input type="hidden" id="wsjhConfig_sign" />  	
		<table id="paramsTable" width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<thead id="list_head">
                <tr>
                    <th>参数</th>
                    <th>参数类型</th>
                    <th>目标值</th>
                </tr>
            </thead>
            <tbody id="paramsTbody" >
            	
            </tbody>
		</table>
		<div class="modal-footer">
            <button type="button" class="btn btn-primary" id="params_save">保 存</button>
            <button type="button" class="btn btn-primary divclose" id="close_div_2" name="关闭">关 闭</button>
        </div>
  </div>
  
  
  <!-- 字段对照 -->
  <div role="tabpanel" class="tab-pane" id="tab_lis3">
  		<input type="hidden" id="wsjhmc" name="query.wsjhmc" value="-1"/>
		<input type="hidden" id="zddzflag" value="0"/>
		<table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<tbody>
              <tr>
                 <th width="20%" >
                     	目的字段:
                 </th>
                  <td width="30%">
                     <select id="mdzd" onchange="inputVl(this.value)" class="form-control"></select>
                 </td>
                 <th width="20%" >
                     	返回字段:
                 </th>
                  <td width="30%">
                     <select id="yzd" class="form-control"></select>
                 </td>
              </tr>
              <tr>
			   <th width="20%" >数据类型</th>
			    <td width="30%"> 
				    <input type="text" id="mdzdlx" readonly="true" class="form-control" />  
				    <input type="hidden" id="mdzdlx_index" />
			    </td>
			    <th width="20%" >数据类型</th>
			    <td width="30%">			  
			    	<input type="text" id="yzdlx" value="String" readonly="true" class="form-control" /> 
			    </td>			   
			  </tr>
            </tbody>
		</table>
		<div class="modal-footer">
            	<button type="button" class="btn btn-primary" id="zddzAdd">增 加</button>
        </div>
        <table id='zddzTable'  width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<thead>
		      <tr>		     
		        <td>目标字段</td>
		        <td>目标字段类型</td>
		        <td>对应返回字段</td>
		        <td>是否主键</td>
		        <td>是否更新</td>
		        <td>操作</td>		
		      </tr>
		    </thead>		   
		    <tbody id="zddzTbody">
		    </tbody>
		</table>
  		<div class="modal-footer">
            <button type="button" class="btn btn-primary" id="zddzSave">保 存</button>
            <button type="button" class="btn btn-primary divclose" id="close_div_3" name="关闭">关 闭</button>
        </div>
  </div>
  
  
  <!-- 执行设置 -->
  <div role="tabpanel" class="tab-pane" id="tab_lis4">
  	<table id="table4" width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
  		<tbody>
	  <tr>	 
	    <th><span class="red">*</span>定时器名称</th>
	    <td colspan="3"><input type="text" id="dsqmc" class="form-control" /></td>
	   <!--  <th>是否监控</th>
	    <td><input type="checkbox"  name="isjk" id="isjk" value="1"  class="text_nor"/></td> -->
	  </tr>
	  <tr>
	    <th><span class="red">*</span>定时器类型</th>
	    <td>
	    	<select id="dsqlx"  style="width:153px">
	    		<s:iterator value="#dsqlx" var="o"> 
	    			<option value='<s:property value='#o.dsqlx' />'><s:property value='#o.dsqlxmc' /></option>
	    		</s:iterator>
	    	</select>
	    </td>
	    <th id='ddzx1'><span class="red">*</span>执行时间</th>
	    <td id='ddzx2'><input type="text" id="dsTime" class="Wdate" onfocus="WdatePicker({dateFmt:'HH:mm'})" /></td>
  		<th id='jgzx1'><span class="red">*</span>间隔时间</th>
	  	<td id='jgzx2'>
	  		<input type="text" id="jgTime" style="ime-mode:disabled;text-align:right;" onkeypress="numberOnly();"  maxlength="3" class="text_nor" />
	  		<select id='sjdw'>
	    		<s:iterator value="#sjdw" var="o"> 
	    			<option value='<s:property value='#o.sjdwbh' />'><s:property value='#o.sjdwmc' /></option>
	    		</s:iterator>
	  		</select>
	  	</td>
	  </tr>
	  <tr>
	  	<th>阶段起始年</th>
	  		<td><input type="text" id="qsyear" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy',maxDate:'#F{$dp.$D(\'jsyear\')}'})" /></td>
	  	<th>阶段起始日期</th>
	  		<td><input type="text" id="qstime" class="Wdate" /></td>
	  </tr>
	  <tr>
	  	<th>阶段结束年</th>
	  		<td><input type="text" id="jsyear" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy',minDate:'#F{$dp.$D(\'qsyear\')}'})" /></td>	  	
	  	<th>阶段结束日期</th>
	  		<td><input type="text" id="jstime" class="Wdate" /></td>	  	
	  </tr>
  </tbody>
  	</table>
  	<div class="modal-footer">
            <button type="button" class="btn btn-primary" id="dsqAdd">增 加</button>
    </div>
        <table id='zxszTable'  width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
			<thead>
		      <tr>		     
		        <td>定时器名称</td>
		        <td>定时器类型</td>
		        <td>定点时间</td>
		        <td>间隔时间</td>
		        <td>阶段起始时间</td>
		        <td>阶段结束时间</td>		        
		        <td>操作</td>		
		      </tr>
		    </thead>		   
		    <tbody>
		    
		    	<s:iterator value="#dsqpzb" status="zt">
		    	<input type='hidden' value='<s:property value='dsqxh'/>' id='dsqxh<s:property value='#zt.index'/>' />
		    		 <tr id="dsq<s:property value='dsqxh'/>" onclick="setRowId('dsq<s:property value='dsqxh'/>');">
		    		 	<td id="td_dsqmc_<s:property value='dsqxh'/>"><input type="hidden" id="dsqmc<s:property value='dsqxh'/>" value="<s:property value='dsqmc'/>" /><s:property value='dsqmc'/></td>
		    		 	<td id="td_dsqlx_<s:property value='dsqxh'/>"><input type="hidden" id="dsqlx<s:property value='dsqxh'/>" value="<s:property value='dsqlx'/>" /><s:property value='dsqlxmc'/></td>
		    		 	<td id="td_ddsjd_<s:property value='dsqxh'/>"><input type="hidden" id="ddsjd<s:property value='dsqxh'/>" value="<s:property value='ddsjd'/>" /><s:property value='ddsjd'/></td>
		    		 	<td id="td_jgsj_<s:property value='dsqxh'/>">
		    		 	<input type="hidden" id="jgsj<s:property value='dsqxh'/>" value="<s:property value='jgsj'/>" />
		    		 	<input type="hidden" id="sjdwbh<s:property value='dsqxh'/>" value="<s:property value='sjdwbh'/>" />
		    		 	<s:property value='jgsj'/><s:property value='sjdwmc'/>
		    		 	
		    		 	</td>
		    		 	<td><input type="hidden" id="jdqssj<s:property value='dsqxh'/>" value="<s:property value='jdqssj'/>" /><s:property value='jdqssj'/></td>
		    		 	<td><input type="hidden" id="jdjssj<s:property value='dsqxh'/>" value="<s:property value='jdjssj'/>" /><s:property value='jdjssj'/></td>
		    		 	<td>
		    		 	    <a id='start<s:property value='dsqxh'/>' style="color: blue;text-decoration:underline" href='javaScript:startRunset(<s:property value='dsqxh'/>,0)'>启动</a>
		    		 	   <a id='stop<s:property value='dsqxh'/>' style="color: blue;text-decoration:underline" href='javaScript:startRunset(<s:property value='dsqxh'/>,1)'>停止</a>
		    		 	   
			    		   <a href='javaScript:deleteRunset(<s:property value='dsqxh'/>);' style="color: blue;text-decoration:underline">删除</a>
		    		 		<input type="hidden" value="<s:property value='dsqzt'/>" id="dsqzt<s:property value='dsqxh'/>">
		    		 	</td>
		    		 </tr>
		    	</s:iterator>
		    
		    </tbody>
		</table>
  		<div class="modal-footer">
            <button type="button" class="btn btn-primary divclose" id="close_div_4" name="关闭">关 闭</button>
        </div>
        
  </div>
  
  
		
   		</div>
      </div>
    </div>
   </div>
   <div id='option' style="display:none">
		<s:iterator value="#sjlscmsx" var="o"> 
	    	<option id="<s:property value='#o.sjlxmc' />" value='<s:property value='#o.sjlx' />'><s:property value='#o.sjlxmc' /></option>
	    </s:iterator>
	</div>
   
	</body>
</html>