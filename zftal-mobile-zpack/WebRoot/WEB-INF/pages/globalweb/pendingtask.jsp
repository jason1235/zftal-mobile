<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/globalweb/head/v4_url.ini"%>

    <div id="pendingtask" class="openmessage"
            style="width: 240px; height: 127px; position: absolute; bottom: 0; z-index: 9999; right: 0px; display: none;">
            <div class="title">
                <h2>
                    系统消息
                </h2>
            </div>
            <div class="messagecon">
                <p class="mesnum">
                    您有
                    <em id="tasktotaldiv">0</em>
                    <!-- 
    <embed name='soundControl'  id='soundControl' src='images/sound.mp3' mastersound hidden='true' loop='0' autostart='false' >
    </embed> -->
                    条新消息
                </p>
                <p>
                    <a href="#" onclick="closemove();toWaitTask();">查看</a>
                    <a href="#" onclick="closemove();">暂不查看</a>
                </p>
            </div>
        </div>
        <!-- 消息提醒代码开始 -->
        <script language="javascript">
       
    function detect()  {
        window.clearInterval(timemachine);
        window.clearInterval(autospecoler);
        window.clearInterval(timemachineclose);
        Moveon();
        return ;
    }
    /*********************************消息滑动DIV效果 总消息*******************************************/
     var timemachine=null;//打开定时器
     var timemachineclose=null;//关闭定时器
     var autospecoler=null;
     var movelimit=0;//当前移动长度
     var movetotal;//总长度
     var permove=3;//每次移动的数量
      
     function Moveon(){
        if(timemachineclose!=null){
             return ;
         }
        if(timemachine!=null){
            return ;
        }
        
    //  mainbody.style.overflowY="hidden";
        //先设置长度
      document.getElementById("pendingtask").style.bottom="-"+document.getElementById("pendingtask").style.height;
      
      document.getElementById("pendingtask").style.display="";
     
      movetotal=document.getElementById("pendingtask").style.height;//总长度
      movetotal=parseInt(movetotal.substring(0,movetotal.indexOf("px")));
      movelimit=0;//清空计算器
        if(parseInt(document.getElementById("pendingtask").style.bottom.substring(0,document.getElementById("pendingtask").style.bottom.indexOf("px")))>=1){
            document.getElementById("pendingtask").style.bottom="-"+document.getElementById("pendingtask").style.height;
        }
      
       timemachine=window.setInterval("upToTop()",1);
       //自动关闭时间
       autospecoler=window.setInterval("autoClosebySix()",30000);
     }
     var autoclosetimer=null;
    function autoClosebySix(){
        closemove();
        window.clearInterval(autoclosetimer);
        autoclosetimer=null;
    }
     function closemove(){
         if(timemachine!=null)
         return ;
             if(timemachineclose!=null)
         return ;
     
     // mainbody.style.overflowY="hidden";
       movetotal=document.getElementById("pendingtask").style.height;//总长度
       movetotal=parseInt(movetotal.substring(0,movetotal.indexOf("px")));
       movelimit=movetotal;//设置计数器为总长度
       timemachineclose=window.setInterval("upToBottom()",1);
     }
     function upToTop(){
          movelimit+=permove;
          document.getElementById("pendingtask").style.bottom=(parseInt(document.getElementById("pendingtask").style.bottom.substring(0,document.getElementById("pendingtask").style.bottom.indexOf("px")))+permove)+"px";
          
         if(movelimit>=movetotal){
             //mainbody.style.overflowY="";
             window.clearInterval(timemachine);
             timemachine=null;
         }
    }
    
    function upToBottom(){
         movelimit-=permove;
         document.getElementById("pendingtask").style.bottom=(parseInt(document.getElementById("pendingtask").style.bottom.substring(0,document.getElementById("pendingtask").style.bottom.indexOf("px")))-permove)+"px";
        if(movelimit<=0){
             window.clearInterval(timemachineclose);
             timemachineclose=null;
             document.getElementById("pendingtask").style.display="none";
             // mainbody.style.overflowY="";
        }
    }
    
    //马上处理
    function toWaitTask(){
    	var targetCode = 'N701020';
        var topCode = targetCode.substring(0,3);
        $("[name='quickId']:hidden").val(targetCode);
        $("a[id='li_"+topCode+"']").click();
    }
    //$(function(){
    //	$.post(_path+"/message/message_listData.html","",function(data){
    //        if(data.success){
    //        	 window.document.getElementById("tasktotaldiv").innerHTML=data.sumcount;
    //        	 if(data.sumcount > 0){
    //        		 detect();
    //        	 }
    //        }else{
    //            alert(data.text);
    //        }
    //    },"json");
    	
     //});
</script>