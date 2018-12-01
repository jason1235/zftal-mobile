/*
 * 基础应用JavaScript
 * ZFSOFT_HRM
 */
var ie = document.all ? true : false;

//window.alert = locationAlert;

/**
 * 弹出无模式对话框
 * @param url 路径
 * @param w 宽度
 * @param h 高度
 * @param scro 是否允许出现滚动条
 */
function showTopWin(url, w, h, scro) {
	var info = "";
	
	if(scro == null) {
		info = "Status:YES;resizable=yes;dialogWidth:" + w + "px;dialogHeight:" + h + "px;help:no;scroll:no";
	} else {
		info = "Status:YES;resizable=yes;dialogWidth:" + w + "px;dialogHeight:" + h + "px;help:no;scroll:yes";
	}
	
	if( url.indexOf("?") != "-1" ) {
		url += "&timestamp=" + Math.random();
	}
	else {
		url += "?timestamp=" + Math.random();
	}
	return showModalDialog(url, window, info);
}

/**
 * 显示弹出层
 * @param title 标题
 * @param url 路径
 * @param width 宽度
 * @param height 高度
 * @return
 */
function showWindow(title, url, width, height, callBack) {
	showWindowV2(title, url, width, height, callBack);
}

/**
 * 弹出层关闭
 * <p>用于text等弹出时使用</p>
 * @return
 */
function divClose()
{
	divLayerClose();
	//iFClose();
	$("#windownbg").remove();
	$("#windown-box").fadeOut("slow",function(){$(this).hide();});
}

function alertDivClose()
{
	$("#windownbg").remove();
	$("#windown-box").fadeOut("slow",function(){$(this).hide();});
}
/**
 * 弹出确认操作层
 * @param message 提示信息内容
 */
function showConfirm(message)
{
	var text = "<div class=\"open_prompt\">"
			+ "  <table width=\"100%\" border=\"0\" class=\"table01\">"
			+ "    <tr>"
			+ "      <td width=\"109\"><div class=\"img img_why01\"></div></td>"
			+ "      <th><p>" + message + "</p></th>"
			+ "    </tr>"
			+ "    <tr>"
			+ "      <td colspan=\"2\" align=\"center\" class=\"btn01\">"
			+ "        <input type=\"button\" id=\"why_sure\" class=\"button\" value=\"确 定\" />"
			+ "        <input type=\"button\" id=\"why_cancel\" class=\"button\" value=\"取 消\" />"
			+ "      </td>"
			+ "    </tr>"
			+ "  </table>"
			+ "</div>";
	
	tipsWindown("提示信息", "text:"+text, "340", "120", "true", "", "true", "id");
	//tipsWindown("提示信息", text, "340", "140");
}

var alert1=window.alert;
window.alert = showWarning;
/**
 * 弹出警告层
 * @param message 提示信息
 */
function showWarning(message,time,callback)
{
	var text = "<div class=\"open_prompt\">"
			+ "  <table width=\"100%\" border=\"0\" class=\"table01\">"
			+ "    <tr>"
			+ "      <td width=\"109\"><div class=\"img img_prompt\"></div></td>"
			+ "      <th><p>" + message + "</p></th>"
			+ "    </tr>"
			+ "    <tr>"
			+ "      <td colspan=\"2\" align=\"center\" class=\"btn01\">"
			+ "        <input type=\"button\" id=\"war_sure\" onclick=\"alertDivClose();\" class=\"button\" value=\"确 定\" />"
			+ "      </td>"
			+ "    </tr>"
			+ "  </table>"
			+ "</div>";
	
	tipsWindown("提示信息", "text:"+text, "340", "120", "true", time, "true", "id",callback);
	//tipsWindown("提示信息", text, "340", "140");
}

/**
 * 数字的格式,只能输入正整数
 * 使用：<input type="text" style="ime-mode:disabled" onkeypress="check_int_dot(this);" />
 */
function check_int_dot(element){
    if (window.event.keyCode > 57 || window.event.keyCode <= 46){
        window.event.returnValue = false;   
    } 
    else {
        if(window.event.keyCode==47){
            window.event.returnValue = false;
        }
    }
}

/**
 * 通用处理ajax返回信息，默认成功提示框，消退时间为2秒，
 * 特殊处理可传入回调函数来处理，已默认包含了窗口关闭函数
 * @param data ajax请求返回信息
 * @param callback 回调函数 可为null
 * @returns
 * <pre>示例
 * var callback = function(){
 * 		window.location.reload();
 * };
 * processDataCall(data, callback);
 * </pre>
 */
function processDataCall(data,callback){
	if(data.success){
		divClose();
		tipsWindown("提示信息","text:"+data.html,"340","120","true","1000","true","id",callback);
		//tipsWindown("提示信息", data.html, "340", "140");
	}else{
		tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
		//tipsWindown("提示信息", data.html, "340", "140");
	}
	
	$("#window-sure").click(function() {
		//divClose();
		//注释，避免回调2次
		if( data.success ) {
			//callback();
		}else{
			alertDivClose();
		}
	});
}

/**
 * 选择人员（单选）
 * @param type 人员类型
 * <pre>
 * 示例：
 * &lt;input type="text" onclick="selectPersonSingle('teacher')" />
 * </pre>
 * @return 人员信息数组，array[ 值, 姓名 ]
 */
function selectPersonSingle( type) {
	
	return showTopWin( _path + "/tools/selectPerson_single.html?type=" + type, 840, 520, null );
}

/**
 * 选择人员（单选）
 * @param type 人员类型
 * <pre>
 * 示例：&lt;code:selectPerson name="model.staffid" id="staffid" />
 * </pre>
 */
function selectPersonSingleX( obj, type ,callBack) {
	var result = selectPersonSingle( type );
	
	if(!result){
		result=window.returnValue;
	}
	
	if( result == null ) return;
	
	if(obj!=null){
		$(obj).prev().val( result[0] );
		$(obj).val( result[1] );
	}
	if(callBack!=null){
		callBack(result[0]);
	}
}

/**
 * 选择人员（多选）
 * @param type 人员类型
 * <pre>
 * 示例：
 * &lt;input type="text" onclick="selectPersonMultiple('teacher')" />
 * </pre>
 * @return 人员信息数组，array[ 值, 姓名 ]，如：['001;002;003','张三、李四、王五']
 */
function selectPersonMultiple( type) {
	
	return showTopWin( _path + "/tools/selectPerson_multiple.html?type=" + type, 840, 520, null );
}

/**
 * 选择人员（多选）
 * @param type 人员类型
 * <pre>
 * 示例：&lt;code:selectPerson name="model.staffid" id="staffid" />
 * </pre>
 */
function selectPersonMultipleX( obj, type ,callBack) {
	
	var result = selectPersonMultiple( type );
	if(!result){
		result=window.returnValue;
	}
	if( result == null ) return;
	if(obj!=null){
		$(obj).prev().val( result[0] );
		$(obj).val( result[1] );
	}
	if(callBack!=null){
		callBack(result[0]);
	}
}

/**
 * 初始化select控件默认值
 * @param name 控件name
 * @param value 值
 */
function initSelect(name,value){
	$("select[name='"+name+"'] > option").each(function(){
		if($(this).val()==value){
			$(this).attr("selected","selected");
		}
	});
}

/**
 * 初始化radio控件默认值
 * @param name
 * @param value
 */
function initRadio(name,value){
	$("input[type='radio'][name='"+name+"']").each(function(){
		if($(this).val()==value){
			$(this).attr("checked","checked");
		}
		});
}

/**
 * 使用浮动层显示元素的title内容
 * @param obj
 */
function tooltips(obj){
	var x = 0;  //设置偏移量
	var y = 20;
	var padding_right = 20;
	
	var t = $(obj);
	t.mouseover(function(e){
       	this.myTitle = this.title; //提取出title值，也可以是其他属性，根据需要调整
		this.title = "";	
	    var tooltip = "<div id='tooltip' style='z-index:9999;display:none;position:absolute;max-width:300px;text-align:left;border:1px solid #cccc00;background:#ffffe3;color:#000;padding:2px 4px;line-height:1.6;'>"+ (this.myTitle.length>0?this.myTitle:'无') +"<\/div>"; //创建 div 元素
		$("body").append(tooltip);	//把它追加到文档中
		$("#tooltip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			}).show("fast");	  //设置x坐标和y坐标，并且显示
    });
	t.mouseout(function(){		
		this.title = this.myTitle;
		$("#tooltip").remove();   //移除 
    });
    t.mousemove(function(e){
		$("#tooltip")
			.css({
				"top": (e.pageY+y) + "px",
				"left": checkX(e.pageX)  + "px"
			});
	});

    function checkX(mouseX){
    	var width = $(document).width();
    	var w = $("#tooltip").width();
    	var border = width-w-x-padding_right;
    	if(mouseX+x<=border){
    		return mouseX+x;
    	}else{
    		return border;
    	}
    }
}

function goUrl(url){
	if (/MSIE (\d+\.\d+);/.test(navigator.userAgent) || /MSIE(\d+\.\d+);/.test(navigator.userAgent)){
	    var referLink = document.createElement('a');
	    referLink.href = url;
	    document.body.appendChild(referLink);
	    referLink.click();
	} else {
	    location.href = url;
	}	
}
