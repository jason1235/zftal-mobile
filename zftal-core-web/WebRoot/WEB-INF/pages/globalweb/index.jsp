<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
		function getElementByClassName(o,tagName,className){
				var elements = o.getElementsByTagName(tagName);
				
				for (var i = 0 ; i < elements.length ; i++){
					if (elements[i].className == className){
						return elements[i];
					}
				}
				return null;
			}
		
			/**
			 * 隐藏左边菜单条的JS
			 * @return
			 */
		function changeWin() {
			var o = document.frames[1].document;
			var div = getElementByClassName(o,"div","con_overlfow");
		
			if (document.getElementById("left").className != "hide") {
				document.getElementById("left").className = "hide";
				document.getElementById("right").style.width = "100%";
				document.getElementById("leftBotton").style.display = "none";
				document.getElementById("rightBotton").style.display = "";
				
				if (div){
					div.style.width="100%";
				}
				scrollDivWidth = "100%";
			} else {
				document.getElementById("left").className = "typeleft floatleft";
				document.getElementById("right").style.width = "803px";
				document.getElementById("leftBotton").style.display = "";
				document.getElementById("rightBotton").style.display = "none";
				
				if (div){
					div.style.width="785px";
				}
				scrollDivWidth = "785px";
			}
		}
		</script>
	</head>
	<body>
		<div class="mainbody type_mainbody">
			<!-- TOP菜单的加载 -->
			<div class="topframe" id="topframe">
				<jsp:include page="top.jsp" flush="true"></jsp:include>
			</div>

			<div class="type_mainframe" style="z-index:0">
				<div class="typeleft floatleft" id="left">
					<iframe name="xg_leftFrame" style="width: 100%;" id="xg_leftFrame"
						marginwidth="0" marginheight="0" framespacing="0" frameborder="0"
						scrolling="no" src="index_leftMenu.html?gnmkdm=${gnmkdm }"
						onload="this.height=0;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>500?fdh:500)"></iframe>
				</div>

				<div class="btn_hide_on" id="leftBotton">
					<button onclick="changeWin();" id="changeid"></button>
				</div>
				<div class="btn_hide_off" style="display: none;" id="rightBotton">
					<button onclick="changeWin();"></button>
				</div>

				<div class="typeright floatright" id="right">
					<!--选项卡-->
					<!---->
					<!--内容区主体-->
					<div class="typecon">
						<iframe id="xg_rightFrame" name="xg_rightFrame" class="framecon"
							allowTransparency="true" src="index_content.html?fjgndm=${gnmkdm }" width="100%"
							frameborder="0" marginwidth="0" marginheight="0"
							onload="this.height=300;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>600?fdh:610)"
							scrolling="no">
						</iframe>
					</div>
				</div>
			</div>

			<!-- 底部页面加载 -->
			<div class="botframe" id="botframe">
				<jsp:include page="bottom.jsp" flush="true"></jsp:include>
			</div>
		</div>
	</body>
</html>
