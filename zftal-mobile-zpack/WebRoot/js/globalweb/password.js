ratingMsgs = new Array(6);
   ratingMsgColors = new Array(6);
   barColors = new Array(6);
   ratingMsgs[0] = "弱";
   ratingMsgs[1] = "弱";
   ratingMsgs[2] = "中";
   ratingMsgs[3] = "强";
    ratingMsgs[4] = "强";
    ratingMsgs[5] = "未评级"; //假如出现无法检测的状况
   ratingMsgColors[0] = "#aa0033";
   ratingMsgColors[1] = "#aa0033";
   ratingMsgColors[2] = "#f5ac00";
   //ratingMsgColors[3] = "#6699cc";
   ratingMsgColors[3] = "#093";
   ratingMsgColors[4] = "#093";
  ratingMsgColors[5] = "#676767";
   barColors[0] = "#aa0033";
   barColors[1] = "#aa0033";
   barColors[2] = "#ffcc33";
   //barColors[3] = "#6699cc";
   barColors[3] = "#093";
   barColors[4] = "#093";
   barColors[5] = "#676767";
   var che=0;
   var min_passwd_len = 6; 
   function CreateRatePasswdReq(pwd) {
	   che=0;
     if (!isBrowserCompatible) {
       return;
     }
       
   // if(!document.getElementById) return false;
   // var pwd = document.getElementById("xkl");
     if(!pwd) return false; 
     passwd=pwd.value;
     if (passwd.length < min_passwd_len)   {
       if (passwd.length > 0) {
         DrawBar(0);
       } else {
         ResetBar();
       }
     } else {
     //在长度检测后，检测密码组成复杂度
       rating = checkPasswdRate(passwd);
       che=rating;
       DrawBar(rating);

     }
   }

	function getElement(name) {
		if (document.all) {
			return document.all(name);
		}
		return document.getElementById(name);
	}

	function DrawBar(rating) {
		var posbar = getElement('posBar');
		var negbar = getElement('negBar');
		var passwdRating = getElement('passwdRating');
		var barLength = getElement('passwdBar').width;
		if (rating >= 0 && rating <= 4) { //We successfully got a rating
			if (rating == 0 || rating == 1) {
				posbar.style.width = barLength / 3 * 1 + "px";
				negbar.style.width = barLength / 3 * 2 + "px";
				
			}else if(rating == 2){
				posbar.style.width = barLength / 3 * 2 + "px";
				negbar.style.width = barLength / 3 * 1 + "px";
			}else {
				posbar.style.width = barLength + "px";
				negbar.style.width = "0px";
			}
		} 
		//else {
		//	posbar.style.width = "0px";
		//	negbar.style.width = barLength + "px";
		//	rating = 5; // Not rated Rating
		//}
		if(rating == 0 || rating == 1){
			passwdRating.innerHTML = "弱";
		}else if(rating == 2){
			passwdRating.innerHTML = "中";
		}else{
			passwdRating.innerHTML = "强";
		}
		posbar.style.background = barColors[rating];
		//passwdRating.innerHTML = "<font color='" + ratingMsgColors[rating] +
        //                     "'>"
		//		+ ratingMsgs[rating] + "</font>";
	}

	//Resets the password strength bar back to its initial state without any message showing.
	function ResetBar() {
		var posbar = getElement('posBar');
		var negbar = getElement('negBar');
		var passwdRating = getElement('passwdRating');
		var barLength = getElement('passwdBar').width;
		posbar.style.width = "0px";
		negbar.style.width = barLength + "px";
		passwdRating.innerHTML = "";
	}
	/* Checks Browser Compatibility */
	var agt = navigator.userAgent.toLowerCase();
	var is_op = (agt.indexOf("opera") != -1);
	var is_ie = (agt.indexOf("msie") != -1) && document.all && !is_op;
	var is_mac = (agt.indexOf("mac") != -1);
	var is_gk = (agt.indexOf("gecko") != -1);
	var is_sf = (agt.indexOf("safari") != -1);
	function gff(str, pfx) {
		var i = str.indexOf(pfx);
		if (i != -1) {
			var v = parseFloat(str.substring(i + pfx.length));
			if (!isNaN(v)) {
				return v;
			}
		}
		return null;
	}
	function Compatible() {
		if (is_ie && !is_op && !is_mac) {
			var v = gff(agt, "msie ");
			if (v != null) {
				return (v >= 6.0);
			}
		}
		if (is_gk && !is_sf) {
			var v = gff(agt, "rv:");
			if (v != null) {
				return (v >= 1.4);
			} else {
				v = gff(agt, "galeon/");
				if (v != null) {
					return (v >= 1.3);
				}
			}
		}
		if (is_sf) {
			var v = gff(agt, "applewebkit/");
			if (v != null) {
				return (v >= 124);
			}
		}
		return false;
	}

	/* We also try to create an xmlhttp object to see if the browser supports it */
	var isBrowserCompatible = Compatible();

	//CharMode函数 
	//测试某个字符是属于哪一类. 
	function CharMode(iN) {
		if (iN >= 48 && iN <= 57) //数字 
			return 1;
		if (iN >= 65 && iN <= 90) //大写字母 
			return 2;
		if (iN >= 97 && iN <= 122) //小写 
			return 4;
		else
			return 8; //特殊字符 
	}
	//bitTotal函数 
	//计算出当前密码当中一共有多少种模式 
	function bitTotal(num) {
		var modes = 0;
		for (i = 0; i < 4; i++) {
			if (num & 1)
				modes++;
			num >>>= 1;
		}
		return modes;
	}
	//checkStrong函数 
	//返回密码的强度级别 
	function checkPasswdRate(sPW) {
		if (sPW.length < min_passwd_len)
			return 0; //密码太短 
		var Modes = 0;
		for (i = 0; i < sPW.length; i++) {
			//测试每一个字符的类别并统计一共有多少种模式. 
			Modes |= CharMode(sPW.charCodeAt(i));
		}
		che=Modes;
		return bitTotal(Modes);
	}


//CharMode函数  
//测试某个字符是属于哪一类.  
	var strong = true;
     function CharMode(iN){  
          if (iN>=48 && iN <=57) //数字   
              return 1;  
          if (iN>=65 && iN <=90) //大写字母   
              return 2;  
          if (iN>=97 && iN <=122)  //小写 字母 
              return 4;  
             else  
              return 8; //特殊字符 
         }  

     //bitTotal函数  
     //计算出当前密码当中一共有多少种模式 
     function bitTotal(num){  
         var modes=0;  
       for (var i=0;i<4;i++){  
           if (num & 1) modes++;  
             num>>>=1;  
          }  
       return modes;  
      }  

  
     //checkStrong函数  
     //返回密码的强度级别  
     function checkStrong(sPW){  
         if (sPW.length<6)  
             return 0; //密码太短  
          var Modes=0;  
          for (i=0;i<sPW.length;i++){  
            //测试每一个字符的类别并统计一共有多少种模式.  
          Modes|=CharMode(sPW.charCodeAt(i));  
               }  
           return bitTotal(Modes);  
         }  

      //pwStrength函数  
      //当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示提示
     function pwStrength(pwd){ 
          if (pwd==null||pwd==''){  
             hideMMshow();
           }  
          else{  
            S_level=checkStrong(pwd); 
            switch(S_level) {  
            case 0:
				   showErrMsg("密码太短,请及时修改!");
            	   strong = false;
            	   backOpertion(1);
            	   
            	   break;   
            case 1: 
	               hideMMshow();
	               strong = true;
	               backOpertion(2);
	         	   //showErrMsg("密码强度太弱,请及时修改!");
                   //strong = false;
            	   break;
            case 2: 
	               hideMMshow();
	               backOpertion(3);
	         	   //showErrMsg("密码强度太弱,请及时修改!");
	               strong = true;
         	   break;
            default:
					strong = true;
                    hideMMshow();
           }  
        }     
}
     
     function backOpertion(level){
    	 if(level == 1){
    		 $("#strengthResult_span1").css("background","#71c624");
    		 $("#strengthResult_span2").css("background","#ddd");
    		 $("#strengthResult_span3").css("background","#ddd");
    		 $("#strengthResult_tip1").css("display","block");
    		 $("#strengthResult_tip2").css("display","none");
    		 $("#strengthResult_tip3").css("display","none");
    	 }else if(level == 2){
    		 $("#strengthResult_span1").css("background","#71c624");
    		 $("#strengthResult_span2").css("background","#71c624");
    		 $("#strengthResult_span3").css("background","#ddd");
    		 $("#strengthResult_tip1").css("display","none");
    		 $("#strengthResult_tip2").css("display","block");
    		 $("#strengthResult_tip3").css("display","none");
    	 }else if(level == 3){
    		 $("#strengthResult_span1").css("background","#71c624");
    		 $("#strengthResult_span2").css("background","#71c624");
    		 $("#strengthResult_span3").css("background","#71c624");
    		 $("#strengthResult_tip1").css("display","none");
    		 $("#strengthResult_tip2").css("display","none");
    		 $("#strengthResult_tip3").css("display","block");
    	 }else{
    		 $("#strengthResult_span1").css("background","#ddd");
    		 $("#strengthResult_span2").css("background","#ddd");
    		 $("#strengthResult_span3").css("background","#ddd");
    		 $("#strengthResult_tip1").css("display","none");
    		 $("#strengthResult_tip2").css("display","none");
    		 $("#strengthResult_tip3").css("display","none");
    	 }
     }
