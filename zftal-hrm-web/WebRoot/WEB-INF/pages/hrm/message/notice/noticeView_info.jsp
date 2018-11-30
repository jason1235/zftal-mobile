<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript">
		function downloadFile(guId){
			var url = "<%=request.getContextPath() %>/file/attachement_download.html?guId=" + guId;
			window.open( url, "400", "300", true);
		}
		</script>
	</head>
	<body>
	  <div class="newspage" style="width:700px;min-height:200px">
		<div class="newspagebg" style="width:700px">
			<div class="title">
				<h3>
					${notice.title }
				</h3>
				<h4>
					<span>发布人：<ct:PersonParse code="${notice.author }" />
					</span>
					<span>发布日期：<s:date name="notice.createTime"
							format="yyyy-MM-dd HH:mm:ss" />
					</span>
				</h4>
			</div>
			<div id="tzggb.fjtr" class="download">
			</div>
			<div>
				<div class="newcont" style="word-break:break-all;min-height:10px;">
					<p align="left">&nbsp;&nbsp;&nbsp;&nbsp;${notice.content }</p>
				</div>	
				<c:if test="${notice.attachements != null && fn:length(notice.attachements) != 0 }">
					<div align="left" style="margin-left:20px;font-size: small" >
						<span>附件 : </span>
						<c:forEach items="${notice.attachements }" var="attachMent" varStatus="sta">
							<p id="${attachMent.guId }">
								&nbsp;&nbsp;&nbsp;&nbsp;${sta.index + 1 }、 <a style="color: blue;" href="javascript:downloadFile('${attachMent.guId }');" >${attachMent.name }<span class="close-icon"></span></a>
							</p>
						</c:forEach>
					</div>
				</c:if>
				<div class="newcont" style="word-break:break-all;min-height:10px;">
					<p align="right"><s:date name="notice.createTime"
								format="yyyy年MM月dd日" /></p>
				</div>
			</div>
		</div>
	  </div>
	</body>
</html>
