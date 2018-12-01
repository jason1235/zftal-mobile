<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <style>
        .ui-autocomplete{
            z-index:12001;
            width: 500px
        }
    </style>
    <script type="text/javascript">
    $(function(){
        var caches = {};
        $("#userInput" ).autocomplete({
                     source: function(request,response){
             var key=$.trim(request.term);
             if(key!=""){
                    if(key in caches){
                        response(caches[key]);
                        }
                    $.ajax({
                        type:'post',
                        url:'<%=request.getContextPath() %>/normal/overallInfo_userListScopeThink.html',
                        dataType:'json',
                        data:'deptType=self&term='+key,
                        cache:true,
                        success:function(data){
                            caches[key]=data;
                            response(data);
                            }
                        });
                 }
              },
                     minLength: 1, //触发条件，达到两个字符时，才进行联想
                     select: function( event, ui ) {
                         $("#xmLabel").html(ui.item.userName);
                         $("#xmInput").val(ui.item.userName);
                         $("#ghLabel").html(ui.item.userId);
                         $("#ghInput").val(ui.item.userId);
                         $("#dwmcLabel").html(ui.item.departmentName);
                         $("#dwmInput").val(ui.item.departmentId);
                         return false;
                     }
                }).data("ui-autocomplete")._renderItem = function( ul, item ) {
                      return $("<li>")
                        .append( "<a>" + item.userName+"("+item.userId+") "+item.departmentName+"("
                                +item.departmentId+") "+item.statusName+"("+item.status+") " + "</a>" )
                        .appendTo( ul );
                };
                
        $("#save_btn").click(function(){
            
            if(!check()){
                 return false;
            }
            var param=$("#form_edit").serialize();
            $.post("<%=request.getContextPath()%>/dagl/archives_saveArchives.html",
                $("#form_edit").serialize(),function(data){
                    var callback = function(){
                        reload();
                    };
                    if(data.success){
                        processDataCall(data, callback);
                    }else{
                        showWarning(data.text);
                    }
                            
            },"json");
            return false;
        });
        
        $("#cancel").click(function(){
            divClose();
        });
    });

    function check()
    {
        var name=$("input[name='archives.gh']").val();
        if(name==null || name==''){
            showWarning("人员不能为空");
            return false;
        }
        var type=$("input[name='archives.bh']").val();
        if(type==null || type==''){
            showWarning("档案编号不能为空");
            return false;
        }
        var type=$("input[name='archives.savePoint']").val();
        if(type==null || type==''){
            showWarning("存放位置不能为空");
            return false;
        }
        var type=$("input[name='archives.changeStatusTime']").val();
        if(type==null || type==''){
            showWarning("调入时间不能为空");
            return false;
        }
        var type=$("textarea[name='dataMessage']").val();
        if(type==null || type==''){
            showWarning("调入说明不能为空");
            return false;
        }
        var checkData=false;
        $("#dataPage tr").each(function(i){
            if(checkData) return;
            var name = $(this).find(".dataName");
            if(name.val()==null || name.val()==''){
                showWarning("档案材料第"+(i+1)+"行：材料名称不能为空");
                checkData=true;return;
            }
            name.attr("name","dataList["+i+"].name");
            $(this).find(".desc").attr("name","dataList["+i+"].desc");
            $(this).find(".bh").attr("name","dataList["+i+"].bh");
            $(this).find(".lh").attr("name","dataList["+i+"].lh");
            $(this).find(".xh").attr("name","dataList["+i+"].xh");
            $(this).find(".fs").attr("name","dataList["+i+"].fs");
            $(this).find(".ym").attr("name","dataList["+i+"].ym");
            
            var t = $(this).find(".Wdate");
            if(t.val()==null || t.val()==''){
                showWarning("档案材料第"+(i+1)+"行：入档时间不能为空");
                checkData=true;return;
            }
            t.attr("name","dataList["+i+"].createTime");
        });
        if(checkData)return false;
        return true;
    }

    function addArchivesData(){
        var trHtml="<tr><td ><input class='bh' style='width: 68px'></td><td ><input class='dataName' style='width: 68px'></td>"
            +"<td ><input onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\" class='Wdate' style='width: 68px'/></td><td ><input class='desc' style='width: 68px'></td>"
            +"<td ><input class='lh' style='width: 68px'></td><td ><input class='xh' style='width: 68px'></td><td ><input class='fs' style='width: 68px'></td><td ><input class='ym' style='width: 68px'></td>"
            +"<td ><a style='text-decoration: underline;color: blue;' onclick='removeArchivesData(this);return false;' href='javascript:void(0);' style='width: 68px'>删除</a></td></tr>";
        $("#dataPage").append(trHtml);
    }
    function removeArchivesData(obj){
        $(obj).closest("tr").remove();
    }
    </script>
</head>
<body>
    <form id="form_edit">
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span><font color="#0f5dc2" style="font-weight:normal;">增加档案信息</font></span>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>
                            <span class="red"></span>人员查询
                        </th>
                        <td colspan="3">
                            <input type="text" id="userInput" >
                            <span class="red">输入职工号、姓名、部门代码、部门名称和状态查找</span>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>职工号
                        </th>
                        <td>
                            <label id="ghLabel"></label>
                            <input id="ghInput" type="hidden" name="archives.gh"/>
                        </td>
                        <th>
                            <span class="red"></span>姓名
                        </th>
                        <td>
                            <label id="xmLabel"></label>
                            <input id="xmInput" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red">*</span>档案编号
                        </th>
                        <td>
                            <input name="archives.bh" />
                        </td>
                        <th>
                            <span class="red"></span>档案描述
                        </th>
                        <td>
                            <input name="archives.detail" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>入档时间<br/>  </th>
                        <td >
                        <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="archives.rdsj"
                            value="<s:date name='nowTime' format='yyyy-MM-dd'/>"/>
                        </td>
                        <th>
                            <span class="red">*</span>调入时间
                        </th>
                        <td>
                            <input onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="archives.changeStatusTime"
                            value="<s:date name='nowTime' format='yyyy-MM-dd'/>"/>
                        </td>
                    </tr>
                    
                    <tr>
                        
                        <th>
                            <span class="red">*</span>存放位置
                        </th>
                        <td colspan="3">
                            <input name="archives.savePoint" />
                        </td>
                    </tr>
                    <tr>
                        <th><span class="red">*</span>调入说明<br/>(从何处因何调入本校等)  </th>
                        <td colspan="3">
                            <textarea name="dataMessage" cols="50"></textarea>
                        </td>
                    </tr>
                </tbody>
                </table>
               <div style="clear: both;max-height: 200px;overflow: auto;">
                <table class="formlist" width="100%">
                    <tr>
                     <th colspan="9" style="text-align: center">档案材料
                     <button onclick="addArchivesData();" type="button" >增加材料</button>
                     </th>  
                    
                     </tr>
                    <tr>
                     <th width="10%">材料编号</th>
                     <th width="10%"><span class="red">*</span>材料名称</th>
                     <th width="10%"><span class="red">*</span>制成时间</th>
                     <th width="10%">材料说明</th>
                     <th width="10%">类号</th>
                     <th width="10%">序号</th>
                     <th width="10%">份数</th>
                     <th width="10%">页码</th>
                     <th width="10%">操作</th>
                     
                    </tr>
                </tbody>
                <tbody id="dataPage">
                </tbody>
                </table>
             </div>
            <table class="formlist" >
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save_btn" type="button">保 存</button>
                                <button id="cancel" type="button">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </div>
    </form>
</body>
</html>