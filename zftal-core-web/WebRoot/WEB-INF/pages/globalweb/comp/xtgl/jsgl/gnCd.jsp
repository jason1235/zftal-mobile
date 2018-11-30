<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<script language="javascript" defer="defer">
function czBtn(){
	var btns = document.getElementById('cz_btn').value;
	var all = document.getElementsByTagName('input');
	var checs = new Array(); 
	var count = 0;
	
	for(var i=0; i<all.length; i++){
		if(all[i].type == 'checkbox' && all[i].disabled == false){
			checs[count++] = all[i]
		}
	}
	
	for (var i=0; i<checs.length; i++){
		checs[i].checked = false;
	}
	if(btns!=null&&btns!=""){
		var btn = btns.split(",");
		for(var i=0; i<btn.length; i++){
			var obj = document.getElementById(btn[i]+'_lv4');
			if(obj!=null){
				obj.checked = true;
				}
			parentCsh(btn[i]+'_lv4');
		}
	}
}

function init(){

	var btns = document.getElementById('cz_btn').value;
	if(btns!=null&&btns!=""){
		var btn = btns.split(",");
	
	for(var i=0; i<btn.length; i++){
		var obj = document.getElementById(btn[i]+'_lv4');
		if(obj!=null){
			obj.checked = true;
			}
		parentCsh(btn[i]+'_lv4');
	}
	}
}

function parentCsh(id){
	
	var ids = id.split('_');

	// 上级代码
	var sjdm = ids[0];
	var lv = ids[2];
	var sjid = '';
	
	if(lv == 'lv1'){
		return;
	}else if(lv == 'lv2'){
		sjid = 'chec_'+sjdm+'_lv1';	
	}else if(lv == 'lv3'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv2';
	}else if(lv == 'lv4'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv3';
	}
	
	var sjobj = document.getElementById(sjid);
	if(sjobj!=null){
		sjobj.checked = true;
	}
	
	parentCsh(sjid);
}

// 全选
function selectAll(){
	var all = document.getElementsByTagName('input');
	var checs = new Array(); 
	var count = 0;
	var qx = $('#qx');
	var checked = qx.val() == 'no' ? true : false;
	for(var i=0; i<all.length; i++){
		if(all[i].type == 'checkbox' && all[i].disabled == false){
			checs[count++] = all[i]
		}
	}
	for (var i=0; i<checs.length; i++){
		checs[i].checked = checked;
	}
	 qx.val(qx.val() == 'no' ? 'yes' : 'no') ;
}

// 调换类名
function changeClass(obj){
	var as = getElementsByClass('current',document,'li');
	for(var i=0;i<as.length;i++){
		as[i].className = "";
	}
	
	obj.parentNode.className = "current";
}

// 更换模块
function changeMk(obj){
	var gnmkId = obj.id;
	var trs = getElementsByClass('tr_02',document,'tr');
	
	for(var i=0; i<trs.length; i++){
		var trId = trs[i].id.split("_");
		if(gnmkId == trId[0]){
			trs[i].style.display = "";
		}else{
			trs[i].style.display = "none";
		}
	}	
	changeClass(obj);
}

/**
 * 获取指定class的对象数组
 * @param searchClass 查询用class   
 * @param node 对象
 * @param tag  想要的标签
 * @return class数组
 * @author sjf
 */
function getElementsByClass(searchClass,node,tag) {
    var classElements = new Array();
    if ( node == null )
            node = document;
    if ( tag == null )
            tag = '*';
    var els = node.getElementsByTagName(tag);
    var elsLen = els.length;
    var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
    for (i = 0, j = 0; i < elsLen; i++) {
            if ( pattern.test(els[i].className) ) {
                    classElements[j] = els[i];
                    j++;
            }
    }
    return classElements;
}

// 点击checkbox
function checkboxClick(obj){
	childrenChoice(obj);
	parentsChoice(obj);
}


function childrenChoice(obj){
	var id = obj.id;
	var ids = id.split('_');
	
	if(ids[2] == 'lv4'){
		return;
	}
	
	// 自身代码
	var dm = ids[1];
	var chec = getElementsByJb(dm,'input',id);
	var checked = obj.checked;
	
	for(var i=0; i<chec.length; i++){
		if(!chec[i].disabled){
			chec[i].checked = checked;
		}
		if(chec[i].name != 'btns'){
			childrenChoice(chec[i]);
		}
	}
}

function parentsChoice(obj){
	var ids = obj.id.split('_');
	// 上级代码
	var sjdm = ids[0];
	var lv = ids[2];
	var sjid = '';
	
	if(lv == 'lv1'){
		return;
	}else if(lv == 'lv2'){
		sjid = 'chec_'+sjdm+'_lv1';	
	}else if(lv == 'lv3'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv2';
	}else if(lv == 'lv4'){
		sjid = sjdm.substring(0,sjdm.length-2) + '_' + sjdm + '_lv3';
	}
	
	var checked = obj.checked;
	
	var sjobj = document.getElementById(sjid);
	
	var broArr = getElementsByJb(sjdm,'input', sjid);
	
	for(var i=0; i<broArr.length; i++){
		if(broArr[i].checked){
			checked = true;
			break;
		}
	}
	if(checked==true){
		sjobj.checked = checked;
	}
	
	parentsChoice(sjobj);
}

function getElementsByJb(searchClass,tag,id) {
	 var ids = id.split('_');
	    var classElements = new Array();
	    var els;
	    if(ids[2] == 'lv1'){	// 一级
	    	els =  document.getElementById('tbody_'+ids[1]).getElementsByTagName(tag);
	    }else if(ids[2] == 'lv2'){ // 二级
	    	els = document.getElementById('tbody_'+ids[0]).getElementsByTagName(tag);
	    }else{
	    	//els = document.getElementById(id).parentNode.parentNode.nextSibling.getElementsByTagName(tag);
	    	els=jQuery(document.getElementById(id).parentNode.parentNode).next().find(tag);
	    }
	   // var elsLen = els.length;
	   var elsLen = els.length;
	    var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
	    for (i = 0, j = 0; i < elsLen; i++) {
	    	if ( pattern.test(els[i].className) ) {
	        	classElements[j] = els[i];
	            j++;
	    	}
	    }
	    return classElements;
}

</script>
</head>
<s:form action="/xtgl/yhgl_bcxgYhgl.html" method="post" theme="simple">
<div class="main_function" id="mkzx">
  <input type="hidden" id="qx" name="qx" value="no" />
  <input type="hidden" id="jsdm" name="jsdm" value='${model.jsdm}' />
  <input type="hidden" id="doType" name="doType" value="save" />
  <input type="hidden" id="pkValue" name="pkValue" value="${model.jsdm}" />
  
  <div class="function_list01">
    <h3><span>功能分配</span></h3>
    <ul>
		<s:iterator value="allGnmkList" id="gnmkOne" status="aa">
			  <li <s:if test="#aa.index==0">class="current"</s:if> >
			  	<input id="chec_${gnmkOne.gnmkdm }_lv1" value="${gnmkOne.gnmkdm }" type="checkbox" onclick="checkboxClick(this);"/>
			  	<a href="#" id="${gnmkOne.gnmkdm }" onclick="changeMk(this);return false;" >${gnmkOne.gnmkmc }</a>
			  </li>
		</s:iterator>
    </ul>
  </div>
  
  <div class="function_list02" id="" style="overflow: scroll;overflow-x:hidden; height: ${height }px">
  	<table width="100%" border="0">
  		<s:iterator value="allGnmkList" id="gnmkOne" status="index01">
  			<tbody id="tbody_${gnmkOne.gnmkdm }">
			<s:iterator value="#gnmkOne.childList"  id="gnmkTwo" status="index02">
				<s:iterator value="#gnmkTwo.childList" id="gnmkThr" status="index03">
					<tr id="${gnmkOne.gnmkdm }_${index03}" class="tr_02" 
					<s:if test="#index01.index!=0">style="display: none"</s:if> >
						<s:if test="#index03.index==0">
							<td style="width:120px" rowspan="${gnmkTwo.childSize }" class="list_02">
					            <label>
					              <input type="checkbox" value="${gnmkTwo.gnmkdm }" id="${gnmkOne.gnmkdm }_${gnmkTwo.gnmkdm }_lv2" class="${gnmkOne.gnmkdm }" onclick="checkboxClick(this);"/>
					              	${gnmkTwo.gnmkmc }
					       </td>
						</s:if>
						<td style="width:150px" class="list_03">
							<label>
					    	<input type="checkbox" value="${gnmkThr.gnmkdm }" name='sjgndmcbv' id="${gnmkTwo.gnmkdm }_${gnmkThr.gnmkdm }_lv3" class="${gnmkTwo.gnmkdm }" onclick="checkboxClick(this);" />
					        	${gnmkThr.gnmkmc }
					        </label>
					    </td>
					    
					    <td>
					    	<ul>
					    	
					    		<s:iterator value="#gnmkThr.btnList" id="btn">
						    		<li>
						    			<input type="checkbox" name="btns" value="${gnmkThr.gnmkdm }_${btn.czdm}" id="${gnmkThr.gnmkdm }_${btn.czdm}_lv4" onclick="checkboxClick(this);"
						    			 
						    			class="${gnmkThr.gnmkdm }"></input>${btn.czmc }
						    	</li>
					    		</s:iterator>
					    			
					    	</ul>
					    </td>
					</tr>
				</s:iterator>
			</s:iterator>
			</tbody>
		</s:iterator>
  	</table>
  </div>
  <div class="function_list03"></div>
</div>
</s:form>
<input type="hidden" id ="cz_btn"  name="cz_btn" value='${czans}'/>
<script type="text/javascript" defer="defer">
	init();
</script>

