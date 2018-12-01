<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){ 
        $("#back").click(function(){//功能条增加按钮
            location.href = _path+"/expertvote/experttask_page.html";
        });

        $("#btn_fqps").click(function(){
        	if($("input[id='id']:checked").length==0){
                alert("请先选中操作行");
                return false;
            }
        	 var subject=$("input[id='id']:checked").val();
        	 showWindowV2("选择评审组",_path+"/expertvote/experttask_chooseGroup.html?query.level=${model.level}&detail.businessClassId="+subject,360,250);
        });
        $("#btn_cxfq").click(function(){
        	if($("input[id='id']:checked").length==0){
                alert("请先选中操作行");
                return false;
            }
        	showConfirm("重新发起仅对待评审人员生效，评审重新发起后专家组成员需要重新进行评审，确定要重新发起评审么？");
            $("#why_cancel").click(function(){divClose();});
            $("#why_sure").click(function(){
                $('#windown-close').click();
            	var subject=$("input[id='id']:checked").val();
                showWindowV2("选择评审组",_path+"/expertvote/experttask_chooseGroupReSend.html?detail.id=${model.id}&query.level=${model.level}&detail.businessClassId="+subject,360,250);
            });
        });
        
        $("tbody > tr[name^='tr']").click(
                function(){ //监听单击行
                    $("input:checkbox").removeAttr("checked");
                    $("tbody > tr[name^='tr']").removeClass("current");
                    $(this).attr("class", "current");
                    $(this).find("input:checkbox").attr("checked","checked");
                    current = $(this);
                }
            );
            
        initTag();
    });

    function zj_send(param){
    	$.post(_path+"/expertvote/experttask_send.html?detail.id=${model.id}",param,function(data){
            if(data.success){
                goUrl(_path+"/expertvote/experttask_detail.html?model.id=${model.id}&detail.send=${detail.send}");
            }else{
                alert(data.text);
            }
        },"json");
    }

    function initTag(){
        $("#${detail.send}").closest("li").addClass("ha");
        $("#comp_title a").click(function(){
            var id=$(this).attr("id");
            location.href = _path+"/expertvote/experttask_detail.html?model.id=${model.id}&detail.send="+id;
        });
    }

    function declarelist(taskStatus,subject){
    	showWindow("申报人员列表",_path+"/expertvote/experttask_declareList.html?instanceQuery.taskId=${model.id}"
    	    	+"&instanceQuery.taskStatus="+taskStatus+"&instanceQuery.businessClassId="+subject,600,400);
    }

    </script>
  </head>
  
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
 <div class="tab" >
<!--标题start-->
    <h3 class="datetitle_01">
        <span>任务信息<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
    
        <table align="center" class="formlist" width="100%">
            <tr>
                <th style="width:120px">任务名称</th>
                <td style="width:200px">
                    ${model.name }
                </td>
                <th style="width:120px"><span class="red">*</span>审核级别</th>
                <td> 
                    <ct:codeParse catalog="DM_SYS_ZJPSJB" code="${model.level}"/>
                 </td>
            </tr>
            <tr>
                  <th style="width:120px"><span class="red">*</span>任务监控</th>
                <td colspan="3" >
			                    尚未到达该任务流程：${detail.beforeNumber }<br/>
			                    已经到达该任务流程：${detail.waitNumber }<br/>
			                    已经通过该任务流程：${detail.passNumber }<br/>         
                 </td>
            </tr>
        </table>
        </form>
        <br />
        <form action="">
        <div class="compTab">
            <div class="comp_title" id="comp_title">
              <ul style="width:90%">
                <li><a href="#" id="NotSend"><span>待发起</span></a></li>
                <li><a href="#" id="Send"><span>已发起</span></a></li>
              </ul>
            </div>
            <div id="report_view" ></div>
        </div>
                <div class="toolbox">
                    <!-- 按钮 -->
                    <div class="buttonbox">
                        <ul>
                            <c:if test="${detail.send == 'NotSend'}">
                            <li>
                                <a onclick="return false;" class="btn_zj" id="btn_fqps" href="#">
                                  发起评审
                                </a>
                            </li>
                            </c:if>
                            <c:if test="${detail.send == 'Send'}">
                            <li>
                                <a onclick="return false;" class="btn_sx" id="btn_cxfq" href="#">
                                  重新发起
                                </a>
                            </li>
                            </c:if>
                        </ul>
                    </div>  
                </div>
                <div class="formbox">
                    <!--标题start-->
                    <h3 class="datetitle_01">
                        <span>当前任务监控信息</span>
                    </h3>
                    <!--标题end-->
                    <table width="100%" class="dateline nowrap" id="tiptab" >
                        <thead id="list_head">
                            <tr>
                                <td width="3%">
                                    <input type="checkbox" onclick="selectAllOrCancel(this,'ids');" id="allCheckBoxDel"/>
                                </td>
                                <td width="5%">序号</td>
                                <td>业务信息类</td>
                                <td>未到达评审人数</td>
                                <td>待评审人数</td>
                                <td>已经评审人数</td>
                            </tr>
                        </thead>
                        <tbody id="list_body">
                            <input type="hidden" name="postRelease.id" value="${model.id }">
                            <c:forEach items="${subjectList}" var="s" varStatus="st">
                              <tr name="tr">
                              <td><input type="checkbox" id="id" value="${s.businessClassId }"/></td>
                                <td>${st.index+1 }</td>
                                <td>${s.businessClassName }</td>
                                <td>
	                                <c:if test="${s.beforeNumber =='0' }">
	                                    <span style="color: gray;">${s.beforeNumber }</span>
		                            </c:if>
		                            <c:if test="${s.beforeNumber !='0' }">
		                                <a href="#" onclick="declarelist('BEFORE','${s.businessClassId}')"><span style="color: blue;text-decoration: underline;">${s.beforeNumber }</span></a>                              
		                            </c:if>
                                </td>
                                <td>
                                    <c:if test="${s.waitNumber =='0' }">
                                        <span style="color: gray;">${s.waitNumber }</span>
                                    </c:if>
                                    <c:if test="${s.waitNumber !='0' }">
                                        <a href="#" onclick="declarelist('WAIT','${s.businessClassId}')"><span style="color: blue;text-decoration: underline;">${s.waitNumber }</span></a>                              
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${s.passNumber =='0' }">
                                        <span style="color: gray;">${s.passNumber }</span>
                                    </c:if>
                                    <c:if test="${s.passNumber !='0' }">
                                        <a href="#" onclick="declarelist('PASS','${s.businessClassId}')"><span style="color: blue;text-decoration: underline;">${s.passNumber }</span></a>                              
                                    </c:if>
                                </td>
                              </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <script type="text/javascript">
                $(function(){
                    fillRows("16", "", "", false);//填充空行
                });
                </script>
                </form>
    </body>
</html>
