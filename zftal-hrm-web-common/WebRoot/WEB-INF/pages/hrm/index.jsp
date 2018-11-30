<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript"
			src="<%=systemPath %>/js/globalweb/comm/operation.js"></script>
	<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/message.js"></script>
    <script type="text/javascript">
    	function syncronizedMenu(subs){
   			$(".location").remove();
   			var level1 = "<a href=\"#\">" + $(".current > a").text() + "</a>";
   			subs = level1 + subs;
   			$(".tab_cur").append("<p class=\"location\"><span>您的当前位置：</span>" + subs + "</p>");
    	}
    	
    	$(function(){
    		$(".btn_hide_on>button").click(function(){
    			$(".btn_hide_off").show();
    			$(".btn_hide_on").hide();
    			$("#left").hide();
    			$("#right").removeClass("typeright").addClass("typeright_hidden");
    		});
    		$(".btn_hide_off>button").click(function(){
    			$(".btn_hide_off").hide();
    			$(".btn_hide_on").show();
    			$("#left").show();
    			$("#right").removeClass("typeright_hidden").addClass("typeright");
    		});
    	});
    </script>
  </head>
  <body>
		<%@ include file="/WEB-INF/pages/hrm/top.jsp"%>
		<div class="type_mainframe">
  		<div class="typeleft floatleft" id="left">
			<iframe name="leftFrame" style="width: 100%;" id="leftFrame"
				marginwidth="0" marginheight="0" framespacing="0" frameborder="0"
				scrolling="no" src="index_leftMenu.html?gnmkdm=${gnmkdm }"
				onload="this.height=0;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>500?fdh:500)"></iframe>
		</div>
  		<div class="btn_hide_on"><button></button></div>
  		<div class="btn_hide_off" style="display:none;"><button></button></div>
  		
  		<div class="typeright floatright" id="right"><!--左边隐藏后class名切换成typeright_hidden-->
			<!--选项卡-->
			<div class="tab_cur">
				<p class="location"><span>您的当前位置：</span></p>
			</div>
			<!--选项卡-->

			<!--内容区主体-->
      		<div class="typecon" >
      			<iframe name="framecon" id="frame_content" allowTransparency="true" src="about:blank" width="100%" frameborder="0" marginwidth="0" marginheight="0" onload="this.height=600"  scrolling="no"></iframe>
      			
      			<script type="text/javascript">
					function reinitIframe(){
						var iframe = document.getElementById("frame_content");
						try{
							var bHeight = iframe.contentWindow.document.body.scrollHeight;
							var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
							var height = Math.max(bHeight, dHeight);
							iframe.height =  height;
						}catch (ex){}
					}
					
					window.setInterval("reinitIframe()", 200);
				</script>
			</div>
		</div>
		
		<!-- 返回顶部 -->
		<div id="gotopbtn" style="width:22px; height:71px;position:fixed;_position:absolute; background:red;bottom:100px;right:10px;display:none;cursor:pointer;">
			<img src="<%=stylePath %>/images/blue/ico_uptop.gif" />
		</div>
		
		<script type="text/javascript">
			backTop=function (btnId){
				var btn=document.getElementById(btnId);
				var d=document.documentElement;
				window.onscroll=set;
				
				btn.onclick=function (){
					btn.style.display="none";
					window.onscroll=null;
					this.timer=setInterval(function(){
						d.scrollTop-=Math.ceil(d.scrollTop*0.1);
						if(d.scrollTop==0) clearInterval(btn.timer,window.onscroll=set);
					},10);
				};
				
				function set(){
					btn.style.display=d.scrollTop?'block':"none"
				}
			};
			
			backTop('gotopbtn');
		</script>
	</div>
	<!-- 版权信息 -->
	<%@include file="/WEB-INF/pages/globalweb/bottom.jsp" %>
  </body>
</html>
