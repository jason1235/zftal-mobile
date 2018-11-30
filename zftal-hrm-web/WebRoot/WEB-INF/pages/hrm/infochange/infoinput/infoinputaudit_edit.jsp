<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript">
    $(function(){
            var params={idInput:$("input[name=infoChange\\.id]"),
                        container1:$("#content1"),
                        billConfigId:'${spBusiness.billId }',
                        billInstanceId:'${infoChange.billInstanceId }',
                        approveBillClassesPrivilege:'${privilegeExpression}',
                        returnUrl:"/infochange/inputaudit_page.html"};
            $("#back").click(function(){//功能条增加按钮
                location.href = _path+params.returnUrl;
            });
            $("#dyBeanBtn").toggle(
                    function(){
                        loadDybeanList("classId=${infoChange.classId}&gh=${infoChange.userId}&dyQuery.perPageSize=5");
                    },
                    function(){
                        $("#dyBeanList").html("");
                    }
                );
            
            $("a[name = 'btn_up']").click(function(){
                var saves=$("#content1").find(".btn_xxxx_bc");
                if(saves.length>0){
                    showWarning("请先保存");
                    return false;
                }
                var id = $("input[name='infoChange.id']").val();
                $.post(_path+'/infochange/inputaudit_modify.html?query.classId=${query.classId}','infoChange.id='+id,function(data){
                    var callback = function(){
                        location.href = _path+"/infochange/inputaudit_page.html";
                    };
                    processDataCall(data,callback);
                },"json");
            });
            loadContent(params);
       });

    function loadContent(params){
        function successCall(data){
            try{
                $.parseJSON(data);
                alert(data.text);
            }catch(e){
                $(params.container1).html(data);
            }
        }
        var inputs ="spBillConfig.id="+params.billConfigId+"&spBillInstance.id="+params.billInstanceId
                +"&privilegeExpression="+params.approveBillClassesPrivilege+"&localEdit=true&saveLog=true";;
        $.ajax({
            url:_path+"/bill/instance_list.html",
            type:"post",
            data:inputs,
            cache:false,
            dataType:"html",
            success:successCall
        });
    }
    function loadDybeanList(inputs){
        function successCall(data){
            try{
                $.parseJSON(data);
                alert(data.text);
            }catch(e){
                $("#dyBeanList").html(data);
            }
        }
        $.ajax({
            url:_path+"/infochange/infoinput_beanlist.html",
            type:"post",
            data:inputs,
            cache:false,
            dataType:"html",
            success:successCall
        });
    }
    
    </script>
  </head>
  <body>
  <div class="toolbox">
        <!-- 按钮 -->
                <div class="buttonbox">
                    <ul>
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
    <div id="content1" style="padding-top:20px;">
    </div>
    <div id="content2" >
    <div class="por-rz-tool"><a id="dyBeanBtn" href="#">查看历史数据</a></div>
            <div id="dyBeanList" class="por-rz-con"></div>
    </div>
    <div class="bz">说明：<span class="red">保存并提交后信息才会改变相应的信息类</span></div>
    <div class="demo_add_03" id="bottom_btn">
        <a href="#" name='btn_up' class="btn">提 交</a>
    </div>
  </body>
</html>
