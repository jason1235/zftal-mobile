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
            $.post(_path+"/infochange/infoinput_check.html?query.classId=${classId}","query.id=${infoChange.id}",function(data){
                if(data.success){
                    if(data.modified){
                        location.href = _path+"/infochange/infoinput_list.html?classId=${classId}";
                    }else{
                        $.post(_path+"/infochange/infoinput_cancel.html?classId=${classId}","infoChange.id=${infoChange.id}",function(data1){
                            if(data1.success){
                                location.href = _path+"/infochange/infoinput_list.html?classId=${classId}";
                            }else{
                                alert(data1.message);
                            }
                        },"json");
                    }
                    
                }else{
                    alert(data.message);
                }
            },"json");
            return false;
        });
        $("a[name = 'btn_up']").click(function(){
            var saves=$("#content").find(".btn_xxxx_bc");
            if(saves.length>0){
                showWarning("请先保存");
                return false;
            }
            var id = $("input[name='infoChange.id']").val();
            $.post(_path+'/infochange/infoinput_commit.html?query.classId=${classId}','infoChange.id='+id,function(data){
                var callback = function(){
                    location.href = _path+"/infochange/infoinput_list.html?classId=${classId}";
                };
                processDataCall(data,callback);
            },"json");
        });
        $("#content").ajaxStart(function(){
        	$(".page_loading").css({display:"block"});
        });
        $("#content").ajaxComplete(function(){
        	$(".page_loading").css({display:"none"});
        });
        load($("#content"),'${spBusiness.billId }',
                '${infoChange.billInstanceId }','${spBusiness.billClassesPrivilegeString }',true);
        
    });
    
    </script>
  </head>
  <body>
	<!-- -页面加载提示  -->
	<div class="page_loading" style="display: none;">
		<div class="load_con">
	  		<div class="pic"></div>
	    </div>
	  <p style="color: red;">信息正在加载，请稍候。。。</p>
	</div>
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
    <input type="hidden" name="infoChange.id" value="${infoChange.id }"/>
<!--标题start-->
    <h3 class="datetitle_01">
        <span>信息修改<font color="#0457A7" style="font-weight:normal;" id="tip"></font></span>
    </h3>
    <div id="content" style="padding-top:20px;">
    </div>
    <div class="bz">说明：<span class="red">您录入的信息提交后才能被业务部门审核，信息提交后本人不可再进行编辑。</span></div>
    <div class="demo_add_03" id="bottom_btn">
        <a href="#" name='btn_up' class="btn">提 交</a>
    </div>
  </body>
</html>
