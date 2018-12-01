<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    
    <script type="text/javascript">
        $(function(){
            $("#save").click(function(){
            	var create_type = $("#create_type").val();
                var bcode=$("#spBusiness_bcode").val();
                var btype=$("#spBusiness_btype").val();
                var class_guid=$("#business_infoclass").val();
                if('business'==create_type){
                	bcode='SH_BNSCLASS_'+class_guid;
                    btype='BUSINESSCLASS_TYPE';
                }
                else if('teacher'==create_type){
                	bcode='SH_GRXX_'+class_guid;
                    btype='GRXX_TYPE';
                }
                $("input[name='spBusiness.bcode']").val(bcode);
                $("input[name='spBusiness.btype']").val(btype);
                
                $.post('<%=request.getContextPath() %>/sp/spbusiness_insertBusiness.html', $('form:last').serialize(), function(data){
                    tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
                    
                    $("#window-sure").click(function() {
                        divClose();
                        
                        if( data.success ) {
                            window.location.reload();
                        }
                    });
                }, "json");

                return false;
            });

            $("input[name='classIds']").click(function(e){
            	  clickClassId(this);
            });

            $("#spbusiness_pid").change(function(e){
            	searchBillClass($("#spbusiness_pid").val());
            });
            $("#create_type").change(function(e){
            	fillByCreateType();
            });
            
            $("#cancel").click(function(){
                divClose();
                return false;
            });

            fillByCreateType();
        });

        function clickClassId(obj){
           var checked=$(obj).attr("checked");
           if(checked=='checked'){
               var html=
                       "<h2>权限<select name=\"classPrivilege\">"
                       + "<option value=\"SEARCH\">查询</option>"
                       + "<option value=\"SEARCH_EDIT\">查询-编辑</option>"
                       + "<option value=\"SEARCH_ADD_DELETE\">查询-增加-删除</option>"
                       + "<option value=\"SEARCH_ADD_DELETE_EDIT\" selected=\"selected\">查询-增加-删除-编辑</option>"
                       + "</select></h2>";
               $(obj).closest("div").append(html);
           }else{
               $(obj).next().remove();
           }
       }

        function searchBillClass(pid){
            if(pid == null || pid == ''){
            	$("#dybill_classList").html("");
                return;
            }
            if(pid == "${spBusiness.pid}"){
            	var html = $("#def_class_list").html();
            	$("#dybill_classList").html(html);
                return;
            }
            $.post('<%=request.getContextPath()%>/sp/spbusiness_findBillClass.html','pid='+pid,function(data){
                if(data.success){
                	$("#dybill_classList_tr").hide();
                    var html = '';
                    if(data.classList!=null){
	                    for(var i=0;i<data.classList.length;i++){
	                        html+="<div><input type=\"checkbox\" onclick='clickClassId(this)' name=\"classIds\" value=\""+data.classList[i].id+"\" />"+data.classList[i].name+"</div>";
	                    }
                    }
                    if(html != ''){
                        $("#dybill_classList_tr").show();
                    }
                    var billName="无";
                    if(data.billName!=null &&data.billName != '')
                    	billName = data.billName;
                    $("#dybill_name").html(billName);
                    $("#spBusiness_billId").val(data.billId);
                    $("#dybill_classList").html(html);
                }else{
                   alert(data.text);
                }
            },"json");
        }

        function fillByCreateType(){
        	var create_type = $("#create_type").val();
        	
            if(''==create_type){
            	$("#class_type").hide();
                $("#other_type").show();
            }
            else{
            	$("#other_type").hide();
                $("#class_type").show();
                fillInfoClass(create_type);
            }
        }


        function fillInfoClass(type){
            $.post('<%=request.getContextPath()%>/sp/spbusiness_getInfoClassList.html','type='+type,function(data){
                if(data.success){
                    var html = '';
                    for(var i=0;i<data.infoClasses.length;i++){
                        html+="<option value='"+data.infoClasses[i].guid+"'>"+data.infoClasses[i].name+"</option>";
                    }
                    $("#business_infoclass").html(html);
                }else{
                   alert(data.text);
                }
            },"json");
        }
        
    </script>
</head>

<body>
    <form>
        <div class="tab">
            <table width="100%"  border="0" class="formlist" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th colspan="4">
                            <span>业务流程新增<font color="#0f5dc2" style="font-weight:normal;"></font></span>
                        </th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td colspan="4">
                            <div class="bz">"<span class="red">*</span>"为必填项</div>
                            <div class="btn">
                                <button id="save">保 存</button>
                                <button id="cancel">取 消</button>
                            </div>
                        </td>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
<%--                        <th>--%>
<%--                            <span class="red"></span>编号--%>
<%--                        </th>--%>
<%--                        <td>--%>
<%--                            ${spBusiness.bid }--%>
<%--                            <input type="hidden" name="spBusiness.bid" value="${spBusiness.bid }"/> --%>
<%--                        </td>--%>
                        <th>
                            <span class="red"></span>业务名称
                        </th>
                        <td>
                            <input type="text" name="spBusiness.bname" value="${spBusiness.bname }"/> 
                            <input type="hidden" name="spBusiness.btype" value="${spBusiness.btype }"/> 
                            <input type="hidden" name="spBusiness.bcode" value="${spBusiness.bcode }"/> 
                        </td>
                        <th>
                            <span class="red"></span>创建类型
                        </th>
                        <td>
                            <select id="create_type">
                                <option value="">具体业务相关</option>
                                <option value="teacher">基础信息类相关</option>
                                <option value="business">业务信息类相关</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>业务模块
                        </th>
                        <td>
                            <select name="spBusiness.belongToSys">
                                <c:forEach items="${belongToSyses}" var="belongToSys">
                                    <option value="${belongToSys.gnmkdm }" >${belongToSys.gnmkmc }</option>
                                </c:forEach>
                            </select>
                        </td>
                        <th>
                        </th>
                        <td>
                        </td>
                    </tr>
                    <tr id="class_type">
                        <th>
                            <span class="red"></span>关联信息类
                        </th>
                        <td colspan="3">
                            <select id="business_infoclass"></select>
                        </td>
                    </tr>
                    <tr id = "other_type">
                        <th>
                            <span class="red"></span>业务类型
                        </th>
                        <td>
                            <select id="spBusiness_btype">
	                            <c:forEach items="${businessType}" var="t">
	                                <option value="${t.key }">${t.text }</option>
	                            </c:forEach>
                            </select>
                        </td>
                        <th>
                            <span class="red"></span>业务编码
                        </th>
                        <td>
                            <select id="spBusiness_bcode">
                                <c:forEach items="${businessCode}" var="t">
                                    <option value="${t.key }">${t.text }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <span class="red"></span>流程名称
                        </th>
                        <td colspan="3">
                            <select id="spbusiness_pid" name="spBusiness.pid">
                                <option value="">无 </option>
                                <c:forEach items="${procedureList}" var="procedure">
                                    <option value="${procedure.pid}" <c:if test="${procedure.pid eq spBusiness.pid}"> selected='selected'</c:if>>${procedure.pname}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    
                    <tr>
                        <th>
                            <span class="red"></span>初始化申报表单
                            <input type="hidden" id="spBusiness_billId" name="spBusiness.billId"/>
                        </th>
                        <td colspan="3" id="dybill_name">无
                        <!--  
                            <select name="spBusiness.billId" disabled="disabled">
                                <option value="">无 </option>
                                <c:forEach items="${spBillConfigs}" var="billConfig">
                                    <option value="${billConfig.id}" <c:if test="${billConfig.id eq spBusiness.billId}"> selected='selected'</c:if>>${billConfig.name}</option>
                                </c:forEach>
                            </select>
                        -->
                        </td>
                    </tr>
                    <tr id= "dybill_classList_tr" style="display: none">
                        <th>
                            <span class="red"></span>表单内容
                        </th>
                        <td colspan="3">
                            <div id="dybill_classList" style="overflow-y:auto;height: 100px;width: 100%">
                                
                            </div> 
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </form>
    
    <div id = "def_class_list">
        <c:forEach items="${billClassList}" var="billClass">
           <div>
               <input type="checkbox" name="classIds" value="${billClass.classId}" <c:if test="${billClass.checked eq true }"> checked="checked"</c:if>/>${billClass.className}
               <c:if test="${billClass.checked eq true  }">
                   <h2>
                       权限
                       <select name="classPrivilege">
                           <option value="SEARCH" <c:if test="${billClass.classesPrivilege eq 'SEARCH' }"> selected="selected"</c:if>>查询</option>
                           <option value="SEARCH_EDIT" <c:if test="${billClass.classesPrivilege eq 'SEARCH_EDIT' }"> selected="selected"</c:if>>查询-编辑</option>
                           <option value="SEARCH_ADD_DELETE" <c:if test="${billClass.classesPrivilege eq 'SEARCH_ADD_DELETE' }"> selected="selected"</c:if>>查询-增加-删除</option>
                           <option value="SEARCH_ADD_DELETE_EDIT" <c:if test="${billClass.classesPrivilege eq 'SEARCH_ADD_DELETE_EDIT' }"> selected="selected"</c:if>>查询-增加-删除-编辑</option>
                       </select>
                   </h2>
               </c:if>
           </div>
        </c:forEach>
    </div>
</body>
</html>
