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
		   var allServices= ${allServicesList};
		   var userServices  = ${userServicesList};
		   
		  //alert(typeof(userServices.AppItemType));
		  var appendstr = "<ul class='dragsort' id='list2'>  ";
		  //alert(userServices.AppItemType.length);
		  if(allServices.AppItemType.length > 0){
			  		for ( var j = 0; j < allServices.AppItemType.length; j++) {
	                        var classId = allServices.AppItemType[j].id;  
	                        var tpdz    = allServices.AppItemType[j].icon;  
	                        var fwmc    = allServices.AppItemType[j].name;  
	                        var type    = allServices.AppItemType[j].type;  
	                        var url     = allServices.AppItemType[j].url; 
	                        var fwbm     = allServices.AppItemType[j].fwbm; 
	                        //var rdpx    = userServicesList[i+j].classRdpx; 
	                        appendstr += " <li style='width:20%'> <div> "; 
	                        //appendstr += " <span class='ico'></span> ";
	                        appendstr += " <input type='hidden' name='id' value='" + classId + "' /> ";  
	                        appendstr += " <input type='hidden' name='type' value='" + type + "' /> "; 
	                        appendstr += " <input type='hidden' name='fwmc' value='" + fwmc + "' /> "; 
	                        appendstr += " <input type='hidden' name='fwbm' value='" + fwbm + "' /> "; 
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr += " <font>" + fwmc + "</font> ";
	                        appendstr += " </div>  </li> ";  
		           } 		
		  }
		  if(typeof(allServices.AppItemType.module)!="undefined"){
		   				    var classId = allServices.AppItemType.module.id;  
	                        var tpdz    = allServices.AppItemType.module.icon;  
	                        var fwmc    = allServices.AppItemType.module.name;  
	                        var type    = allServices.AppItemType.module.type;  
	                        var url     = allServices.AppItemType.module.url; 
	                        var fwbm     = allServices.AppItemType.module.fwbm; 
	                        //var rdpx    = userServicesList[i+j].classRdpx; 
	                        appendstr += " <li style='width:20%'> <div> ";  
	                        appendstr += " <input type='hidden' name='id' value='" + classId + "' /> ";  
	                        appendstr += " <input type='hidden' name='type' value='" + type + "' /> "; 
	                        appendstr += " <input type='hidden' name='fwmc' value='" + fwmc + "' /> "; 
	                        appendstr += " <input type='hidden' name='fwbm' value='" + fwbm + "' /> "; 
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr += " <font>" + fwmc + "</font> ";
	                        appendstr += " </div>  </li> ";  
		  }
          	
		   appendstr += " </ul> ";
           $("#list_body2").append(appendstr);  
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
	                        //appendstr += " <a href='javascript:onfwbm(" + fwbm + ");' >";
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr1 += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr1 += " <font>" + fwmc + "</font> ";
	                        //appendstr += " </a>";
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
	                        //appendstr += " <a href='javascript:onfwbm(" + fwbm + ");' >";
	                        //appendstr += " <input type='text' name='rdpx' value='" + rdpx + "' /> "; 
	                        appendstr1 += " <img src='" + tpdz + "' height='50' width='50' />";
	                        appendstr1 += " <font>" + fwmc + "</font> ";
	                        //appendstr += " </a>";
	                        appendstr1 += " </div>  </li> ";  
		  }
          appendstr1 += " </ul> ";  
          $("#list_body1").append(appendstr1);  
          
			
			
		
		  jQuery("#list1").dragsort({ dragSelector: "li", dragSelectorExclude:".ico", dragBetween: true, dragEnd: saveOrder, placeHolderTemplate: "<li class='placeHolder'><div></div></li>" });
		  $("#list_body1 .ico").live("click",function(e){
		     var fwbm = $(this).closest("li").find("input[name='fwbm']").val();
				 $.post(_path+"/serviceManager/WXApplicationCenter_deletefuwu.html","fwbm="+fwbm,function(data){
	                if(data.success){
	                    $("input[value='" + fwbm + "']").closest("li").remove();
	                	//location.reload();
	                	//alert("删除成功！");
	                }else{
	                    alert(data.text);
	                }
	            },"json");
			});
			
			$("#list2 li").click(function(e){
				 var fwbm = $(this).find("input[name='fwbm']").val();
				 $.post(_path+"/serviceManager/WXApplicationCenter_insertfuwu.html","fwbm="+fwbm,function(data){
	                if(data.success){
	                	location.reload();
	                }else{
	                    alert(data.text);
	                }
	            },"json");
			});
			
			$("#btn_ck").click(function(){
				 var len = $("#list1 li").length;
				 $("#list1 li").each(function(i){
				    $(this).append("<span class='ico' onclick=>删除</span>");
				 });
				 $("#list_body2").empty();
				 $("#btn_ck").hide();
				 $("#btn_wc").show();
				 $("#btn_list").hide();
			});
			
			$("#btn_wc").click(function(){
	                	location.reload();
			});
			$("#btn_wc").hide();
			
			$("#btn_list").click(function(){
			    window.location = _path+"/serviceManager/WXApplicationCenter_list.html"; 
			    //$.post(_path+"/serviceManager/WXApplicationCenter_edit.html","",function(data){
	            //    if(data.success){
	            //    }else{
	            //        alert(data.text);
	            //    }
	            //},"json");
			});
		});
		
		function onfwbm(fwbm){
		   $.post(_path+"/serviceManager/WXApplicationCenter_insertfuwu.html","fwbm="+fwbm,function(data){
	                if(data.success){
	                	location.reload();
	                }else{
	                    alert(data.text);
	                }
	            },"json");
		}
		
		     
		function saveOrder() {
		   var fwbms = $("#list1 input[name='fwbm']");
           $.post(_path+"/serviceManager/WXApplicationCenter_rdpx.html",fwbms.serialize(),function(data){
                if(data.success){
                	location.reload();
                }else{
                    alert(data.text);
                }
            },"json");
            
	   }
	
  
            
		</script>
	</head>
	<body>
	
      <div class="functionapply" style="height:auto"> 
	      	<div class="demo_list" style="width:100%" id="list_body1">
	      	     <span><font>已选应用</font></span>	
	      	     <span>长按拖动排序/  点击删除</span> 
	      	     <button class="btn_cx" name="search" type="button" id="btn_ck">编 辑</button>
	      	     <button class="btn_cx" name="wancheng" type="button" id="btn_wc">完 成</button>
	        </div>
        </div>
        
		 <div class="functionapply" style="height:auto" > 
	      	<div class="demo_list" style="width:100%" id="list_body2">	
	      	<span><font>点击添加更多应用</font></span>	 
	        </div>
        </div>
        <div class="functionapply" style="height:auto" > 
	      	<button class="btn_cx" name="applist" type="button" id="btn_list" >▲ </button>
        </div>

	</body>
</html>
	
