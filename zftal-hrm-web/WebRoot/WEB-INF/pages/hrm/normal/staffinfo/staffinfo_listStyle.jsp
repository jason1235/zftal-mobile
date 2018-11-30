<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@taglib prefix="ct" uri="/custom-code"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@ include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/inputPrompt.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/imageUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/fileUpload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.ui.core.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ui.all.css" type="text/css" media="all" />
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/lockTableTitle.js"></script>
    <script type="text/javascript"> 
   
    $(function(){
        hasBusiness=${hasBusiness};
        doPermission();
        $("#log").click(function(e){
            var gh = $("input[name='gh']").val();
            goUrl(_path+"/baseinfo/dynalog_userPage.html?classId=${clazz.guid}&gh="+gh);
            e.stopPropagation();
        });
        $("#btn_add").click(function(){
            if(hasBusiness){
                $.post(_path+"/infochange/infochange_create.html?query.classId=${query.clazz.guid}&guid=","",function(data){
                    if(data.success){
                        location.href=_path+"/infochange/infochange_detail.html?query.classId=${query.clazz.guid}&infoChange.id="+data.infoChange.id;
                    }else{
                        alert(data.message);
                    }
                },"json");
            }else{
                showWindow("修改","<%=request.getContextPath()%>/normal/staffBatch_modify.html?classId=${query.clazz.guid}"
                        +"&gh=${bean.values.gh }&editable=couldModify", 750, '${230+heightOffset}');
            }
        });

        $(".btn_xxxx_bj a").click(function(){//编辑按钮及切换提交按钮
            var checked=$("input[id='ids']:checked");
            if(checked.length==0){
                alert("请选择行");
                return false;
            }else if(checked.length>1){
                alert("只能对单行进行操作");
                return false;
            }
            var current = $("#list_body tr.current");
            var id = current.attr("id");
            if(id==null){
                id="";
            }
            if(hasBusiness){
                $.post(_path+"/infochange/infochange_create.html?query.classId=${query.clazz.guid}&guid="+id,"",function(data){
                    if(data.success){
                        location.href=_path+"/infochange/infochange_detail.html?query.classId=${query.clazz.guid}&infoChange.id="+data.infoChange.id;
                    }else{
                        alert(data.message);
                    }
                },"json");
            }else{
                showWindow("修改","<%=request.getContextPath()%>/normal/staffBatch_modify.html?classId=${query.clazz.guid}&instanceId="+id
                        +"&gh=${bean.values.gh }&editable=couldModify", 750, '${230+heightOffset}');
            }
        });

        $(".btn_xxxx_cx a").click(function(){//查询
            var checked=$("input[id='ids']:checked");
            if(checked.length==0){
                alert("请选择行");
                return false;
            }else if(checked.length>1){
                alert("只能对单行进行操作");
                return false;
            }
            var current = $("#list_body tr.current");
            var id = current.attr("id");
            if(id==null){
                return;
            }
            showWindow("查看","<%=request.getContextPath()%>/normal/staffBatch_modify.html?classId=${query.clazz.guid}&instanceId="+id
                        +"&gh=${bean.values.gh }", 750, '${230+heightOffset}');
        });

        $(".btn_xxxx_sc a").click(function(){//编辑按钮及切换提交按钮
            var checked=$("input[id='ids']:checked");
            if(checked.length==0){
                alert("请选择行");
                return false;
            }else if(checked.length>1){
                alert("只能对单行进行操作");
                return false;
            }
            var current = $("#list_body tr.current");
            var id = current.attr("id");
            if(id==null){
                id="";
            }
            showConfirm("确定要删除吗？");
            $("#why_cancel").click(function(){
                divClose();
            });
            $("#why_sure").click(function(){
                var param = "globalid="+id+ "&classId=${query.clazz.guid}";
                $.post(_path + '/normal/staffInfo_delete.html',param,function(data){
                    var callback = function(){
                        location.href=_path+"/normal/staffInfo_list.html?classId=${query.clazz.guid}";
                    };
                    processDataCall(data, callback);
                },"json");
            });
        });

         $(".btn_ck").click(function(){
             location.href = _path+"/infochange/infochange_page.html?query.classId=${clazz.guid}&query.status=INITAIL";
         });
         refreshButton();
         fillRows("20", "", "", false);//填充空行

         if($("#MyTable").width()>800){
             FixTable("MyTable", 4, 790, null);
             $("tr[name='list_tr']").click(function(){
                 var index = $(this).attr("xuhaobianma");
                 var tr = $("#MyTable_tableColumn tr[xuhaobianma = '"+index+"']")
                 var curclass = $(this).attr("class");
                 var gh = $(this).attr("xuhaobianma");
                 $("input[name='ids']").removeAttr("checked");
                 $("tr").removeClass("current");
                 $("tr[name='list_tr']").css("background","rgb(255, 255, 255)");
                 if(curclass == null || curclass == '') {
                     $("tr[xuhaobianma='"+index+"']").attr("class", "current");
                     $("#MyTable_tableColumn tr[xuhaobianma='"+index+"']").find("input[name='ids']").attr("checked","checked");
                 }
                 refreshButton();
             });

        }else{
            $("#list_body tr[name='list_tr']").click(function(){
                var curclass = $(this).attr("class");
                $("input[name='ids']").removeAttr("checked");
                $("tr").removeClass("current");
                if(curclass == null || curclass == '') {
                    $(this).attr("class", "current");
                    $(this).find("input[name='ids']").attr("checked","checked");
                }
                refreshButton();
            });
        }

         $("#list_body tr[name='list_tr']").dblclick(function(){
             var id = $(this).find("input[id='ids']").val();
             if(id==null){
                 return;
             }
             showWindow("查看","<%=request.getContextPath()%>/normal/staffBatch_modify.html?classId=${query.clazz.guid}&instanceId="+id
                         +"&gh=${bean.values.gh }", 750, '${230+heightOffset}');
         });
    });

    /*
     *排序回调函数
     */
     function callBackForSort(sortFieldName,asc){
         $("#sortFieldName").val(sortFieldName);
         $("#asc").val(asc);
         $("#selectForm").submit();
     }

     function refreshButton(){
         var checked=$("input[id='ids']:checked");
         $(".btn_xxxx li").hide();
            $("#btn_add").show();
         if(checked.length==0){
             //$("div.buttonbox li").show();
         }else if(checked.length==1){
             $(".btn_xxxx_bj").show();
             $(".btn_xxxx_sc").show();
             $(".btn_xxxx_cx").show();
         }
     }

     function reflashPage(){
         location.href=_path+"/normal/staffInfo_list.html?classId=${query.clazz.guid}";
     }

     function doPermission(){
         var title = $("#${clazz.guid }");
            var permissions = $(title).find("h3>div>span");
            if($(permissions).filter(":contains('xg')").length==0){
                $(title).find(".btn_xxxx_bj").remove();
            }
            if($(permissions).filter(":contains('sc')").length==0){
                $(title).find(".btn_xxxx_sc").remove();
            }
            if($(permissions).filter(":contains('zj')").length==0){
                $(title).find("#btn_add").remove();
            }
            if("${!clazz.moreThanOne}"=="true"){
                if($("input[id='ids']").length>0){
                    $(title).find("#btn_add").remove();
                }
            }
        }

    </script>
  </head>
  <body>
  <div id="position-fixed" style="top:0; background:#fff;">
     <div class="title_xxxx">
        <span class="people_xx">${bean.values.xm } （职工号： ${bean.values.gh }）<input type="hidden" name="gh" value="${bean.values.gh }"/><input type="hidden" name="globalid" value="${bean.values.globalid }"/></span>
        <c:if test="${hasBusiness }">
            &emsp;<a class="btn_ck">审核查看</a>
        </c:if>
    </div>
  </div>
    
    <div class="demo_xxxx" id="${clazz.guid }" >
    <h3 class="college_title" style="cursor: pointer;">
        <span class="title_name">${clazz.name }</span>
        <!-- <a id="log" style="cursor:pointer;">日志查询</a> -->
        <div style="display:none;">
        <c:forEach items="${ancdModelList }" var="btn">
        <span>${btn.czdm }</span>
        </c:forEach>
        </div>
    </h3>
    <div name="conDiv">
    <ul class="btn_xxxx">
        <li class="btn_xxxx_cx"><a>查看</a></li>
        <li class="btn_xxxx_sc"><a>删除</a></li>
        <c:if test="${hasBusiness}">
           <li class="btn_xxxx_bj"><a>变更申请</a></li>
           <li id="btn_add" class="btn_xxxx_bc"><a>添加申请</a></li>
        </c:if>
        <c:if test="${!hasBusiness}">
           <li class="btn_xxxx_bj"><a>编辑</a></li>
           <li id="btn_add" class="btn_xxxx_bc"><a>增加</a></li>
        </c:if>
        
    </ul>
    <form id="selectForm" enctype="application/x-www-form-urlencoded" method="post" >
                 <input type="hidden" id="sortFieldName" name="sortFieldName" value="${sortFieldName}"/>
                 <input type="hidden" id="asc" name="asc" value="${asc}"/>
                 <input type="hidden" name='bol' value="true" />
                 <input type="hidden" id="showMore" name='showMore' value="${showMore }" />
            <div class="con_overlfow">
                <table summary="" class="dateline tablenowrap" align="" width="100%"  id="MyTable">
                    <thead id="list_head">
                        <tr>
                            <td width="4%">
                                <input type="checkbox" disabled="disabled" id="allCheckBoxDel"/>
                            </td>
                            <c:forEach items="${query.clazz.viewables}" var="p">
                                <c:if test="${p.fieldName eq sortFieldName}">
                                    <td class="sort_title_current_${asc }" id="${p.fieldName }">${p.name }
                                    </td>
                                </c:if>
                                <c:if test="${p.fieldName != sortFieldName}">
                                    <td class="sort_title" id="${p.fieldName }">${p.name }
                                    </td>
                                </c:if>
                            </c:forEach>
                        </tr>
                    </thead>
                    <tbody id="list_body">
                        <c:forEach items="${pageList}" var="dynaBean">
                            <tr name="list_tr" id="${dynaBean.values['globalid']}">
                                <td><input type="checkbox" id='ids' name="ids" value="${dynaBean.values['globalid']}"/></td>
                                <c:forEach items="${query.clazz.viewables}" var="infoProperty">
                                    <td>${dynaBean.viewHtml[infoProperty.fieldName]}</td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <ct:page pageList="${pageList }" />
         </form>
         </div>
    </div>
  </body>
</html>
