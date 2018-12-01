<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
    <script type="text/javascript">
    $(function(){
        //sort('${sortFieldName}','${asc }');
         
         $(".finalGradeChoose").change(function(){
             changeGrade(this);
         });
         //bindContentEvent($("#content2"));
         $("#logButton").toggle(
			function(){
				$("#logContent").slideDown();
			},
			function(){
				$("#logContent").slideUp();
			}
		);
         //loadView();
         fillRows("10", "thead", "tbody", false);
         FixTableHead("tiptab", 300);
    });

    function back(){//功能条增加按钮
        location.href = _path+"/dagl/archives_page.html";
    };

    function addItem(){//功能条增加按钮
    	showWindow("材料添加",_path+"/dagl/archives_toAddItem.html?archives.id=${archives.id}", 650,300);
    };

    function removeItem(id){
    	 showConfirm("你确定要删除该记录？");
         $("#why_cancel").click(function(){
             $("#windown-close").click();
           });
         $("#why_sure").click(function(){
           $.post(_path+'/dagl/archives_removeArchiveItem.html','archiveItem.itemId='+id,function(data){
             var callback = function(){
            	 reload();
             };
             processDataCall(data,callback);
           },"json");
         });
    }

    function reload(){
    	location.href = _path+"/dagl/archives_detail.html?archives.id=${archives.id}";
    }


    </script>
    <style>
        .sumNum_num{
            color:red;
        }
    </style>
  </head>
  <body>

  <div class="toolbox">
        <!-- 按钮 -->
               <div class="toolbox">
            <!-- 按钮 -->
            <div class="buttonbox">
            
                <ul>
                <c:if test="${archives.status=='SAVE'}">
                     <li>
                        <a name="btn_zj" class="btn_zj" onclick="addItem();">材料添加</a>
                    </li>
                    </c:if>
                </ul>
                <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="back();">返 回</a>
                <input type="hidden" name="declare.id" value="${declare.id }"/>
                <input type="hidden" name="query.auditType" value="${declare.auditType}" />
            </div>
            <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <form enctype="application/x-www-form-urlencoded" name="search" id="search" method="post">
     <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
     <input type="hidden" id="asc" name="asc" value="${asc}"/>
     <input type="hidden" id="asc" name="asc" value="${asc}"/>
     <div class="message">
         <table width="90%" class="table_kb">
             <caption>
                 <span>档案编号：${archives.bh } </span>
                 <span>姓名：${archives.xm }(${archives.gh }) </span>
             </caption>
             <caption>
                <span>档案描述：${archives.detail } </span>
             <caption>   
                           
                 <span>当前状态：${archives.statusText}
                    (<s:date name="archives.changeStatusTime" format="yyyy-MM-dd"/>)
                 </span>
                 <span>存放位置：${archives.savePoint}
                 </span>
             </caption>
         </table>
     </div>
     
     <div class="searchtab" style="500px">
        
      </div>
        <div class="formbox">
<!--标题start-->
            <h3 class="datetitle_01">
                <span>材料列表<font color="#0457A7" style="font-weight:normal;"> </font></span>
            </h3>
        <!--标题end-->
            <div class="con_overlfow" style="height: 300px;overflow-y:auto;" >
                <table width="100%" class="dateline tablenowrap" id="tiptab">
                        <thead id="thead">
                             <tr>
                                 <th>材料编号</th>
			                     <th>材料名称</th>
			                     <th>入档时间</th>
			                     <th>材料说明</th>
			                     <th>类号</th>
			                     <th>序号</th>
			                     <th>份数</th>
			                     <th>页码</th>
			                     <c:if test="${archives.status=='SAVE'}">
			                     <th>操作</th>
			                     </c:if>
                             </tr>
                         </thead>
                         <tbody id="tbody">
                         <s:iterator value="dataList" var="item" status="st">
                             <tr>
                                 <th>${item.bh }</th>
                                 <th>${item.name }</th>
                                 <th><s:date name="createTime" format="yyyy-MM-dd"/></th>
                                 <th>${item.desc }</th>
                                 <th>${item.lh }</th>
                                 <th>${item.xh }</th>
                                 <th>${item.fs }</th>
                                 <th>${item.ym }</th>
                                  <c:if test="${archives.status=='SAVE'}">
                                 <th><a style="color: blue;" onclick="removeItem('${item.itemId}')"  title="">删除</a></th>
                                 </c:if>
                             </tr>
                         </s:iterator>
                         <c:forEach items="${config.viewField}" var="v">
                            <c:if test ="${v[2]==null }">
                                <script type="text/javascript">
                                   $(".${v[0]}").remove();
                                </script>
                            </c:if>
                         </c:forEach>
                         </tbody>
                </table>
            </div>
      </form>
      
       <div id="content2" >
        <input type="hidden" name="reAudit" value="${reAudit }"/>
        <br/>
        <div class="por-rz">
            <div class="por-rz-tool"><a id="logButton" href="#">查看变动日志</a></div>
            <div id="logContent" style="display:none;" class="por-rz-con">
                <table class="por-rz-tab">
                    <tbody>
                <s:if test="logList.size()>0">
                <s:iterator value="logList" var="log">
                    <tr><th style="width: 150px;"><s:date name="operatorTime" format="yyyy-MM-dd" /></th>
                    <td>${log.logMessage }</td>
                    <th style="width: 200px;">${logOperator}(<s:date name="logTime" format="yyyy-MM-dd HH:mm" />)</th>
                    </tr>
                </s:iterator>
                </s:if>
                <s:else>
                    <tr><th>-</th><td>暂无</td></tr>
                </s:else>
                    </tbody>
                </table>
            </div>
        </div>
        <br/></div></div>
  </body>
</html>
