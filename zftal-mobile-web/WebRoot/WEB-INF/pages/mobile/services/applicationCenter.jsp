<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<style type="text/css">
			.formlist td {vertical-align: top;} 
			 ul { margin:0px; padding:0px; margin-left:0px; }
			.ul1_class, .ul2_class { width:350px; list-style-type:none; margin:0px; }
			.ul1_class li, .ul2_class li { float:left; padding:5px; width:100px; height:25px; }
			.ul1_class div, .ul2_class div { width:90px; height:12px; border:solid 1px black; background-color:#E0E0E0; text-align:center; padding-top:3px; }
			.ul2_class { float:right; }
			.placeHolder div { background-color:white !important; border:dashed 1px gray !important; }
		</style>
		<script type="text/javascript" src="<%=systemPath %>/js/mobile/jquery.dragsort-0.4.min.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/mobile/jquery.form.js"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/json/json2.js"></script>
		<script type="text/javascript">
		$(function(){
		   var userServices  = ${userServicesList};
           var appendstr1 = "<ul class='dragsort' id='list1'>  ";  
           if(userServices.AppItemType.length > 0){
			  		for ( var j = 0; j < userServices.AppItemType.length; j++) {
	                        var classId = userServices.AppItemType[j].id;  
	                        var tpdz    = userServices.AppItemType[j].icon;  
	                        var fwmc    = userServices.AppItemType[j].name;  
	                        var type    = userServices.AppItemType[j].type;  
	                        var url     = userServices.AppItemType[j].url; 
	                        var fwbm     = userServices.AppItemType[j].fwbm; 
	                        //var rdpx    = userServicesList[i+j].classRdpx; 
	                        appendstr1 += " <li style='width:20%'> <div> "; 
	                        //appendstr1 += " <span class='ico'>删除</span> "; 
	                        appendstr1 += " <input type='hidden' name='id' value='" + classId + "' /> ";  
	                        appendstr1 += " <input type='hidden' name='type' value='" + type + "' /> "; 
	                        appendstr1 += " <input type='hidden' name='fwmc' value='" + fwmc + "' /> "; 
	                        appendstr1 += " <input type='hidden' name='fwbm' value='" + fwbm + "' /> "; 
	                        //appendstr1 += " <input type='text' name='fwbm' value='" + url + "' />";
	                        appendstr1 += " <a href='" + url + "' >";
	                        //appendstr += " <a href='javascript:onfwbm(" + fwbm + ");' >";
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr1 += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr1 += " <font>" + fwmc + "</font> ";
	                        appendstr1 += " </a>";
	                        appendstr1 += " </div>  </li> ";  
					} 
           }
           if(typeof(userServices.AppItemType.module)!="undefined"){
		   				    var classId = userServices.AppItemType.module.id;  
	                        var tpdz    = userServices.AppItemType.module.icon;  
	                        var fwmc    = userServices.AppItemType.module.name;  
	                        var type    = userServices.AppItemType.module.type;  
	                        var url     = userServices.AppItemType.module.url; 
	                        var fwbm     = userServices.AppItemType.module.fwbm; 
	                        //var rdpx    = userServicesList[i+j].classRdpx; 
	                        appendstr1 += " <li style='width:20%'> <div> ";
	                        //appendstr1 += " <span class='ico' onclick=>删除</span> ";   
	                        appendstr1 += " <input type='hidden' name='id' value='" + classId + "' /> ";  
	                        appendstr1 += " <input type='hidden' name='type' value='" + type + "' /> "; 
	                        appendstr1 += " <input type='hidden' name='fwmc' value='" + fwmc + "' /> "; 
	                        appendstr1 += " <input type='hidden' name='fwbm' value='" + fwbm + "' /> "; 
	                        appendstr1 += " <a href='" + url + "' >";
	                        //appendstr += " <a href='javascript:onfwbm(" + fwbm + ");' >";
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr1 += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr1 += " <font>" + fwmc + "</font> ";
	                        appendstr += " </a>";
	                        appendstr1 += " </div>  </li> ";  
		  }
          appendstr1 += " </ul> ";  
          $("#list_body1").append(appendstr1); 
           
			$("#btn_dy").click(function(){
			    window.location = _path+"/serviceManager/WXApplicationCenter_edit.html"; 
			    //$.post(_path+"/serviceManager/WXApplicationCenter_edit.html","",function(data){
	            //    if(data.success){
	            //    }else{
	            //        alert(data.text);
	            //    }
	            //},"json");
			});
		});
		
		
		     
	
  
            
		</script>
	</head>
	<body>
	
        
      <div class="functionapply" style="height:auto"> 
	      	<div class="demo_list" style="width:100%" id="list_body1">
	      	<button class="btn_cx" name="Book" type="button" id="btn_dy">▼</button>
	      	     
	        </div>
        </div>

	</body>
</html>
	
