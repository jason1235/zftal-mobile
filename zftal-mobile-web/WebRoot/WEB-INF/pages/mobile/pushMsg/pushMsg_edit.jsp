<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
    <script type="text/javascript">
    
        $(function(){
            $("#cancel").click(function(){
                modalClose();
                return false;
            });
            
            $(".close").click(function(){
                modalClose();
                return false;
            });
        });
        
    </script>
</head>

<body>
        <div class="sl_mod modal-content">
            <div class="modal-header">
                <button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title text-left" id="myModalLabel">历史推送查看</h4>
            </div>
            <div class="modal-body">
                <div class="tab">
        <table align="center" class="formlist">
        <tbody id="form1">
            <tr>
                <th width="15%">推送时间</th>
                <td width="35%">
                    <input type="text" name="guid" class="form-control" value="${pushMsg.tssj }" />
                </td>
                <th width="15%">推送内容</th>
                <td width="35%"><input maxlength="1000" placeholder="不要超过1000个字符哦(^_^)" type="text" name="name" class="form-control" value="${pushMsg.tsnr }"/></td>
                
            </tr>
            
        </tbody>
    </table>
  </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="cancel">取 消</button>
            </div>
        </div>
</body>
</html>
<%--
<script type="text/javascript">

</script>
<div id="testID">    
  <div class="tab">
		<table align="center" class="formlist">
		<thead>
			<tr>
				<th colspan="4">
					<span>历史推送查看<font color="#0f5dc2" style="font-weight:normal;"></font></span>
				</th>
			</tr>
		</thead>
		<tbody id="form1">
			<tr>
				<th width="15%">推送时间</th>
				<td width="35%">
					<input type="text" name="guid" class="text_nor" style="width:180px" value="${pushMsg.tssj }" />
				</td>
				<th width="15%">推送内容</th>
				<td width="35%"><input type="text" name="name" class="text_nor" style="width:180px" value="${pushMsg.tsnr }"/></td>
				
			</tr>
			
		</tbody>
    <tfoot>
      <tr>
        <td colspan="4">
          <div class="btn">
            <button name="cancel" onclick='divClose();'>取 消</button>
          </div>
          </td>
      </tr>
		</tfoot>
	</table>
  </div>
</div> --%>
