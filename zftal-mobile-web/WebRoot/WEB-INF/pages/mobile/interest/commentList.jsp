<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="ct" uri="/custom-code"%>
<%@ taglib uri="/WEB-INF/infoclasstag.tld" prefix="clazz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <%@ include file="/commons/hrm/head_v5.ini" %>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/laydate/theme/default/laydate.css" type="text/css"/>
    <style>
    	 .form-group{
      		margin-bottom:inherit;
        }
        .control-label{
        	line-height:34px;
        }
        .toolbox button{
        	margin:5px 0;
        }
        .modal-header{
        	background:#2587de;
        	color:#fff;
        }
        .close{
        	color:#fff;
            opacity:1;
        }
    </style>
    <script type="text/javascript" defer="" src="<%=request.getContextPath() %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/js/hrm/code.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/assets/js/Sortable.min.js"></script>
	
    <script type="text/javascript">
        window.returnValue = null;

        $(function(){
            //监听【查询】按钮点击事件
           /* $("#btn_cx").click( function() {
                $("form").submit();
            });*/

            //监听【取消】按钮点击事件
            $("#btn_qx").click( function() {
                //window.close();
            	//window.parent.modalClose();
            	window.parent.$('#myModal_toji').modal('hide');
        		return false;
            });
            
            $(".close").click(function(){
	        	window.parent.$('#myModal_toji').modal('hide');
	        	return false;
	        });
            
            $(":checkbox[type='checkbox']").click(function(){
               
            	$(":checkbox[type='checkbox']").not(this).attr('checked',false);
            	
				//$(':checkbox[type="checkbox"]').removeAttr('checked');
			});

            //监听【确定】按钮点击事件
            $("#btn_qd").click( function() {
                var $checks = $("input[name='cbo']:checked");
					$check = $checks.get(0);
                console.log($($check).val());
                	var id = $($check).val();
                $.ajax({
                	type:"post",
                	url:"<%=request.getContextPath() %>/interest/interest_delInterestComment.html",
                	data:{
                		interestId:$($check).val()
                	},
                	success:function(data){
                	//window.parent.$('#myModal_toji').modal('hide');
                		if(data.success){
                			console.log('11111111111111111111');
							location.reload();
							processCall(data, callback);
                		}
                		
                	}
                });
            });

            //FixTable("MyTable", 0, 443, 340); 
            $("input[name='ghList']").each(function(){
                var tr = $("#MyTable_tableData").find("tr[pk=\"pk_"+$(this).val()+"\"]");
               $(tr).find("input[name='cbo']").attr("checked", true);
               $(tr).attr("class", "current");
           });
            function removeItem(value){
                var tr = $("#MyTable_tableData").
                     find("tr[pk=\"pk_"+value+"\"]");
                $(tr).find("input[name='cbo']").attr("checked", false);
                $(tr).removeClass("current");
            }
        });
    </script>
</head>
  
<body>
<div class="modal-header">
    <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <h4 class="modal-title text-left" id="myModalLabel">评论信息</h4>
   
</div>
<div class="container_ydht sl_all_bg modal-content">
    <form action="<%=request.getContextPath() %>/market/market_htCommentList.html" method="post">
    	<input type="hidden" name="commentQuery.marketId" id="marketId" value="${commentQuery.marketId}"/>
        <!--<div class="form-horizontal sl_all_form modal-body">
            <div class="row">
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">用户名</label>
                        <div class="col-xs-7">
                            <input name="yhglQuery.zgh" class="form-control" value="${yhglQuery.zgh }"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-6">
                    <div class="form-group">
                        <label for="" class="col-xs-5 control-label">姓名</label>
                        <div class="col-xs-7">
                            <input name="yhglQuery.xm" class="form-control" value="${yhglQuery.xm }"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>-->
        <div class="row sl_aff_btn">
            <div class="col-xs-12">
                <div class="toolbox" style="text-align:right;width:100%;">
                    <div class="btn-group" id="but_ancd">
                       <!-- <button type="button" class="btn btn-primary" id="btn_cx">查 询</button>-->
                        <button type="button" class="btn btn-primary" id="btn_qd">删 除</button>
                        <button type="button" class="btn btn-primary" id="btn_qx">取 消</button>
                    </div>
                </div>
            </div>
        </div>

        <table id="MyTable" class="table table-striped sl_all_bg table-bordered text-left" style="table-layout: fixed;">
        	
            <thead id="list_head">
                <tr>
                    <th style="width:64px;"></th>
                    <th>留言内容</th>
                    <th>留言人</th>
                    <th>留言时间</th>
                </tr>
            </thead>
            <tbody id="list_body" >
            	
                <c:forEach items="${list}" var="p" varStatus="i">
                <tr pk="pk_${p.id}">
                    <td>
                        <input type="checkbox" name="cbo" value="${p.id}" />
                    </td>
                    <td style="white-space:nowrap;overflow: hidden;text-overflow:ellipsis" title="${p.content }">${p.content }</td>
                    <td>${p.userName}</td>
                    <td>${p.createTime}</td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <ct:page pageList="${list }" query="${commentQuery }" />
    </form>
</div>

    <script type="text/javascript">
        
    </script>
</body>
</html>