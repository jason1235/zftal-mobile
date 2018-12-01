<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!doctype html>
<html>
<head>
  <%@ include file="/commons/head.ini" %>
  <script type="text/javascript">
		$(function(){
			$(".menu").click(function(){
				var dyym = $(this).find("input[name='dyym']").val();
				$("#myifram").attr("src",dyym); 
			});
			$("#firstPage").click(function(){
				$("#myifram").attr("src","http://portal.zfsoft.com:9090/zftal-mobile/menu/apiMenu_list.html"); 
			});
		
		});
		function deleteSession(){
			$.post("<%=request.getContextPath()%>/guest/user_deleteSession.html",
	                "",function(data){
	                    if(data.success){
							location.href="<%=request.getContextPath()%>";
						}else{
							errorAlert(data.text);
						}  
           	});
		}
  </script>
</head>

<body id="blog-article-sidebar">


<header class="am-topbar am-topbar-inverse">
<h1 class="am-topbar-brand">
    <a href="#">正方软件</a>
  </h1>

  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#doc-topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>

  <div class="am-collapse am-topbar-collapse" id="doc-topbar-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav">
      <li id="firstPage"><a href="#">首页</a></li>
    
   	  <c:forEach items="${menuList}" var="item"> 
                
       <c:if test="${item.fjgndm eq 'N' }">
          <li class="am-dropdown" data-am-dropdown>
	        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          		${item.gnmkmc } <span class="am-icon-caret-down"></span>
	        </a>
	        <ul class="am-dropdown-content">
	        	<c:forEach items="${reMenuList}" var="reitem"> 
	        		<c:if test="${reitem.fjgndm eq item.gnmkdm }">
		          		<li class="menu">
		          		<a href="#">
		          		${reitem.gnmkmc }
		          		</a>
		          		<input type="hidden" value="<%=request.getContextPath()%>${reitem.dyym }" name="dyym">
		          		</li>
	        		</c:if>
	        		
	        	</c:forEach>
	         <!--
	          <li class="am-active"><a href="#">2. 去火星</a></li>
	          -->
	        </ul>
	      </li>
       	
       </c:if>         
                
      </c:forEach> 
    
      
      
      
    </ul>
	<!--
    <form class="am-topbar-form am-topbar-left am-form-inline" role="search">
      <div class="am-form-group">
        <input type="text" class="am-form-field am-input-sm" placeholder="搜索">
      </div>
    </form>
	-->
    <div class="am-topbar-right">
      <div class="am-dropdown" data-am-dropdown="{boundary: '.am-topbar'}">
        <button class="am-btn am-btn-secondary am-topbar-btn am-btn-sm am-dropdown-toggle" data-am-dropdown-toggle>其他 <span class="am-icon-caret-down"></span></button>
        <ul class="am-dropdown-content">
          <li><a href="#" onclick="deleteSession();">注销</a></li>
        </ul>
      </div>
    </div>

    <div class="am-topbar-right am-topbar-brand">
    <%=session.getAttribute("name") %>，你好
    </div>
  </div>
</header>
<!-- header end -->
</div>

<%--
<hr data-am-widget="divider" style="" class="am-divider am-divider-dotted" />
--%>

<iframe id="myifram" src="http://portal.zfsoft.com:9090/zftal-mobile/menu/apiMenu_list.html" marginwidth="0" marginheight="0" framespacing="0" frameborder="0"
        scrolling="no" style="width: 100%;border-top: 0px;height: 4000px;" onload="this.height=0;var fdh=(this.Document?this.Document.body.scrollHeight:this.contentDocument.body.offsetHeight);this.height=(fdh>4000?fdh:4000)"></iframe>

<!-- content srart -->

<!-- content end -->


<footer class="blog-footer"><%--
    <div class="am-g am-g-fixed blog-fixed am-u-sm-centered blog-footer-padding">
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
            <h3>模板简介</h3>
            <p class="am-text-sm">这是一个使用amazeUI做的简单的前端模板。<br> 博客/ 资讯类 前端模板 <br> 支持响应式，多种布局，包括主页、文章页、媒体页、分类页等<br>嗯嗯嗯，不知道说啥了。外面的世界真精彩<br><br>
                Amaze UI 使用 MIT 许可证发布，用户可以自由使用、复制、修改、合并、出版发行、散布、再授权及贩售 Amaze UI 及其副本。</p>
        </div>
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
            <h3>社交账号</h3>
            <p>
                <a href=""><span class="am-icon-qq am-icon-fw am-primary blog-icon blog-icon"></span></a>
                <a href=""><span class="am-icon-github am-icon-fw blog-icon blog-icon"></span></a>
                <a href=""><span class="am-icon-weibo am-icon-fw blog-icon blog-icon"></span></a>
                <a href=""><span class="am-icon-reddit am-icon-fw blog-icon blog-icon"></span></a>
                <a href=""><span class="am-icon-weixin am-icon-fw blog-icon blog-icon"></span></a>
            </p>
            <h3>Credits</h3>
            <p>我们追求卓越，然时间、经验、能力有限。Amaze UI 有很多不足的地方，希望大家包容、不吝赐教，给我们提意见、建议。感谢你们！</p>
        </div>
        <div class="am-u-sm-12 am-u-md-4- am-u-lg-4">
            <h1>我们站在巨人的肩膀上</h1>
            <h3>Heroes</h3>
            <p>
            <ul>
                <li>jQuery</li>
                <li>Zepto.js</li>
                <li>Seajs</li>
                <li>LESS</li>
                <li>...</li>
            </ul>
            </p>
        </div>
    </div>
    --%><div class="blog-text-center">©1999-2016 正方软件股份有限公司 版权所有 Ver 4.8.7</div>
</footer>


</body>
</html>