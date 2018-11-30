<%@page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<div class="modal fade sl_mod" id="myModal_toji">
   <div class="modal-dialog">
       <div class="modal-content">
           <iframe id="myFrame" width="100%" frameborder="no" border="1"  marginheight="0px" marginwidth="0px" style="margin:0px 0px 0px 0px;"></iframe>
       </div>
   </div>
</div>
<div class="modal sl_mod" id="myAlert">
   <div class="modal-dialog">
       <div class="modal-content" style="width:500px;">
           <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
               <h4 class="modal-title text-left">提示信息</h4>
           </div>
           <div class="modal-body">
			   <div id="alertBody" class="row alertWarn">
                   <div class="col-xs-2" style="text-align:center;"><span id="img" class="glyphicon glyphicon-warning-sign" style="font-size:40px;"></span></div>
                   <div class="col-xs-9"><span id="alertContent" style="font-size:20px;"></span></div>
                   <div class="col-xs-1"></div>
               </div>
           </div>
           <div class="modal-footer">
               <button type="button" class="btn btn-primary" id="why_sure" data-dismiss="modal">确定</button>
               <button type="button" class="btn btn-primary" id="why_cancel" data-dismiss="modal">取消</button>
           </div>
       </div>
   </div>
</div>