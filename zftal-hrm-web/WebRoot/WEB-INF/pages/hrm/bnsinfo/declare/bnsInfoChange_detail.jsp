<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/bill/billview.js"></script>
    <script type="text/javascript">
    $(function(){
        $("#back").click(function(){//功能条增加按钮
        	location.href = _path+"/bnsinfo/infochange_list.html?classId=${classId}&initTagId=declarePage";
        });
        $("a[name='btn_up']").click(function(){
            var saves=$("#content").find(".btn_xxxx_bc");
            if(saves.length>0){
                showWarning("请先保存");
                return false;
            }
            showConfirm("确定要提交此次修改吗？");
            
            $("#why_cancel").click(function(){
                divClose();
            });
        
            $("#why_sure").click(function(){
                var id = $("input[name='businessInfoChange.id']").val();
                $.post(_path+'/bnsinfo/infochange_commit.html?query.classId=${classId}','businessInfoChange.id='+id,function(data){
                    var callback = function(){
                        location.href = _path+"/bnsinfo/infochange_list.html?classId=${classId}&initTagId=declarePage";
                    };
                    processDataCall(data,callback);
                },"json");
            });
        });
            
        load($("#content"),'${billConfigId }',
                '${businessInfoChange.billInstanceId }','${privilegeExpression }',false);
        
    });

    

    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
                        <li>
                            <a id="btn_up" name='btn_up' class="btn_up">提 交</a>
                        </li>
                    </ul>
                    <a id="back" class="btn_fh_rs" style="cursor: pointer" onclick="">返 回</a>
                </div>
          <p class="toolbox_fot">
                <em></em>
            </p>
        </div>
<div class="formbox">
    <input type="hidden" name="businessInfoChange.id" value="${businessInfoChange.id }"/>
    <input type="hidden" name="spBillInstance.id" value="${businessInfoChange.billInstanceId }"/>
    <input type="hidden" name="spBillConfig.id" value="${billConfigId }"/>
    <input type="hidden" name="privilegeExpression" value="${privilegeExpression }"/>
<!--标题start-->
    <h3 class="datetitle_01">
        <span>信息修改<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
    <div id="content" style="padding-top:20px;">
    </div>
    <div class="demo_add_03" id="bottom_btn">
        <a href="#" name='btn_up' class="btn">提 交</a>
    </div>
  </body>
</html>