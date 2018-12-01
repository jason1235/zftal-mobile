/**
 * 我的应用，菜单模块
 *     
 * @author yjd
 * @time 2012-5-7
 */
//初始化已选增加按钮     要求页面 dom 结构不能改变
function cshAn(cdBh){
	var zjCd=$("#zj_"+cdBh);
	zjCd.attr("class","cygn_disabled")
}
//删除当前应用
function scAn(obj){
	var dqAn=$(obj);//当前按钮连接
	var dqUl=dqAn.parent().parent();//当前Ul
	var dqBh=dqAn.attr("id");//当前按钮ID
	var gnmkdm=dqBh.replace("sc_","");
	var xBh="zj_"+gnmkdm;//新按钮ID
	dqAn.parent().remove();
	var xAn=$("#"+xBh);//新的按钮对象
	//改变   按钮A   class
	xAn.attr("class","cygn_add");
	//如果删除了所有的  菜单  提示
	if(dqUl.children()==null || dqUl.children().html()==null){
		dqUl.append("<li id='zwszyy'><a href='javascript:' target='framecon'><span>暂未设置应用</span></a></li>");
	}
	scWdyy(gnmkdm);
}


//增加当前应用
function zjAn(obj){
	//控制我的应用长度
	if($("#wdyyCd").children().length==10){
		alert("只能使用10个我的应用!");
		return ;
	}
	//修改当前按钮样式
	var dqAn=$(obj);//当前增加按钮
	var dqBh=dqAn.attr("id");//当前增加按钮ID
	if("cygn_disabled"==dqAn.attr("class")){
		return false;
	}
	//删除提示   '暂未设置应用'菜单
	var zwszyy=$("#zwszyy");
	if(zwszyy !=null && zwszyy.length > 0){
		zwszyy.remove();
	}
	var kbLi=dqAn.parent().clone();//拷贝li结构obj
	//改变增加按钮的样式， 让它不能再增加    拷贝后在修改
	dqAn.attr('class','cygn_disabled')
	var xAn=kbLi.children().next();//获取拷贝的A链接 dom OBJ;
	var gnmkdm=dqBh.replace("zj_","");
	var xBh="sc_"+gnmkdm;
	//改变   新按钮A   class
	xAn.attr('class','cygn_delete')
	//改变   新按钮A  id
	xAn.attr("id",xBh);
	xAn.removeAttr("name");
	//改变  新 按钮A  onclick
	xAn.attr("onclick","scAn(this);return false;");
	$("#wdyyCd").append("<li>"+kbLi.html()+"</li>");
	
	zjWdyy(gnmkdm);//保存当前菜单
}

//显示 我的应用 
function xsWdyy(){
	$("#wdyyCd").toggle();
}

//增加我的应用
function zjWdyy(gnmkdm){
	//获取要保存的  菜单代码
	var fjgndmObj=$("#fjgndm");
	var fjgndm="";
	if(fjgndmObj!=null){
		fjgndm=fjgndmObj.val();
	}
	var gnmkdms={"gnmkdm":gnmkdm,"fjgndm":fjgndm};
	$.ajax({
		url:_path+"/xtgl/wdyy_bcWdyy.html",
		type: "post",
		dataType:"json",
		data:gnmkdms,
		success:function(wdyyList){	
		}
	 });
}

//删除我的应用
function scWdyy(gnmkdm){
	//获取要保存的  菜单代码
	var fjgndmObj=$("#fjgndm");
	var fjgndm="";
	if(fjgndmObj!=null){
		fjgndm=fjgndmObj.val();
	}
	var gnmkdms={"gnmkdm":gnmkdm,"fjgndm":fjgndm};
	$.ajax({
		url:_path+"/xtgl/wdyy_scWdyy.html",
		type: "post",
		dataType:"json",
		data:gnmkdms,
		success:function(wdyyList){	
		}
	 });
}