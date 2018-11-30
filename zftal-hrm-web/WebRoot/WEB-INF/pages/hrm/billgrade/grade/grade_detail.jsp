<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@page import="com.zfsoft.hrm.config.ICodeConstants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@taglib prefix="code" uri="/WEB-INF/code.tld" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@include file="/commons/hrm/head.ini" %>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/hrm/code.js"></script>
    <script type="text/javascript">
    $(function(){ 
    	$(".grade_formbox").hide();
        $($(".grade_formbox")[0]).show();
    	$("#grade_comp_title li:first").addClass("ha");
        $("#grade_comp_title a").click(function(){
            var id = $(this).attr("id");
            $("#grade_comp_title li").removeClass("ha");
            $(this).closest("li").addClass("ha");
            $(".grade_formbox").hide();
            $("#table_"+id).show();
        });
    })
    

    function callBackForSort(sortFieldName,asc){
        $("#sortFieldName").val(sortFieldName);
        $("#asc").val(asc);
        $("#selectForm").submit();
    }

    function show(id,obj){
        var hrmStr = $(obj).html();
        if(hrmStr == '查看得分项'){
            $(obj).html("隐藏得分项");
            $("#declareTR_"+id).show();
        }else{
            $(obj).html("查看得分项");
            $("#declareTR_"+id).hide();
        }
    }
    </script>
</head>
<body>
 <div class="grade_formbox1">
        <c:if test="${spBillGradeResult == null}">
            <div class="page_prompt">
                <div class="page_promptcon">
                  <h3>温馨提示：</h3>
                  <p><font color="#0f5dc2">没有找到任何信息，可能尚无有效评分</font></p>
                </div>
                <p>&nbsp;</p>
            </div>
        </c:if>
        <c:if test="${spBillGradeResult.sumScore==0}">
            <div class="page_prompt">
                <div class="page_promptcon">
                  <h3>温馨提示：</h3>
                  <p><font color="#0f5dc2">当前评分为0分，不存在任何得分项</font></p>
                </div>
                <p>&nbsp;</p>
            </div>
        </c:if>
        <c:if test="${spBillGradeResult.sumScore > 0}">
        <h3 class="datetitle_01">
	        <span>总计得分：<font color="#0457A7" style="font-weight:normal;" id="tip">${spBillGradeResult.sumScore}</font>
	        <c:if test="${spBillGradeResult.spBillGradeLevel!=null}">
	           (<font color="red" style="font-weight:normal;">${spBillGradeResult.spBillGradeLevel.desc}</font>)
	        </c:if>
	        </span>
	    </h3>
    <div class="compTab">
            <div class="comp_title" id="grade_comp_title">
              <ul style="width:90%">
                  <c:forEach items="${billClassList}" var="billClass">
                    <c:if test="${spBillGradeResult.resultConditionMap[billClass.id]!=null }">
                        <li id="tab_${billClass.id }"><a href="#" id="${billClass.id }"><span>${billClass.name }（得分：${spBillGradeResult.scoreMap[billClass.id]}）</span></a></li>
                    </c:if>
                  </c:forEach>
              </ul>
               <div class="btn_up_down" sizcache="13" sizset="0">
                <span id="grade_comp_title_up" class="btn_up"></span>
                <span id="grade_comp_title_down" class="btn_down"></span>
              </div>
              <script>
              $(function(){   
                  var ulHeight=$("#grade_comp_title ul").height();   
                  var len=Math.ceil(ulHeight/26);   
                  var i=0;
                  $("#grade_comp_title ul").css({marginTop:(-26*i)+"px"});
                  if(i==0){
                       $("#grade_comp_title_up").attr('class','btn_up_disable');
                  } 
                  if(len==i+1){     
                      $("#grade_comp_title_down").attr('class','btn_down_disable');    
                  }  
                  $(".btn_up_down #grade_comp_title_down").live('click',function(){
                       if(!$(this).parents("#grade_comp_title").children("ul").is(":animated")){
                            if(i<len-1){    
                                i++;    
                                $(this).siblings("span").attr("class","btn_up");    
                                $(this).parents("#grade_comp_title").children("ul").animate({marginTop:(-26*i)+"px"},200);    
                            }    
                            if(i==len-1){     
                                $(this).attr('class','btn_down_disable');    
                            }    
                        }   
                   });   
                   $(".btn_up_down #grade_comp_title_up").live('click',function(){    
                       if(!$(this).parents("#grade_comp_title").children("ul").is(":animated")){    
                           if(i>0){    
                               i--;    
                               $(this).siblings("span").attr("class","btn_down");    
                               $(this).parents("#grade_comp_title").children("ul").animate({marginTop:(-26*i)+"px"},200);    
                           }    
                           if(i==0){     
                               $(this).attr('class','btn_up_disable');    
                           }    
                       }   
                    });
              })
              </script>
            </div>
            <c:forEach items="${billClassList}" var="billClass">
               <c:if test="${spBillGradeResult.resultConditionMap[billClass.id]!=null }">
                <div class="grade_formbox" id = "table_${billClass.id }">
				    <div style="overflow: auto;">
				        <table width="100%" class="dateline" id="tiptab">
				            <thead id="list_head">
				                <tr>
				                    <td>条件描述</td>
				                    <td>单条分值</td>
				                    <td>此项得分</td>
				                    <td>操作</td>
				                </tr>
				            </thead>
				            <tbody id="list_body">
				                <c:forEach items="${spBillGradeResult.resultConditionMap[billClass.id]}" var="resultCondition" varStatus="st">
				                    <tr name="tr">
				                        <td>${resultCondition.conditionText}</td>
				                        <td>${resultCondition.score }</td>
				                        <td>${resultCondition.sumScore }</td>
				                        <td><a href="#" style="color: blue; text-decoration: underline;" onclick="show('${resultCondition.conditionId }',this)">查看得分项</a></td>
				                    </tr>
				                    <tr id="declareTR_${resultCondition.conditionId }" style="display: none;">
				                       <td colspan="12">
				                            <div class="con_overlfow">
											    <table summary="" class="dateline tablenowrap" align="" width="100%">
											
											    <thead>
											        <tr>
											        <c:forEach items="${billClass.commonBillPropertys }" var="billProperty">
											            <th id="${billProperty.id }" style="text-align: right;" >
											                ${billProperty.name }
											            </th>
											        </c:forEach>
											        </tr>
											    </thead>
											    <tbody>
											    <c:forEach items="${resultCondition.xmlValueEntities}" var="valueEntity">
											        <tr id="entity_${valueEntity.id}" name="${valueEntity.id}" type="${valueEntity.entityType}">
											        <c:forEach items="${billClass.commonBillPropertys }" var="billProperty">
											            <td> ${valueEntity.newViewMap[billProperty.id] }
											                <div class="history" style="display: none;">${valueEntity.viewMap[billProperty.id] }</div></td>
											        </c:forEach>
											        </tr>
											    </c:forEach>
											    </tbody>
											    </table>
											    </div>
				                       </td>
				                    </tr>
				                </c:forEach>
				            </tbody>
				        </table>
				    </div>
				</div>
			</c:if>
		</c:forEach>
	</div>
	</c:if>
  </div>
</body>