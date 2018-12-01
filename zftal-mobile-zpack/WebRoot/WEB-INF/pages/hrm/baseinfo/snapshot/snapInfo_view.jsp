<%@ page language="java" import="java.util.*,com.zfsoft.hrm.config.ICodeConstants" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <script type="text/javascript">
    // 信息类列表表示状态true-展开 false-收起
    var showInfo = false;
    // 信息类属性表示状态true-展开 false-收起
    var showProperty = false;
    var tableTitel = "tableTitel";
    var tableList = "tableList";
    var propertyTitel = "propertyTitel";
    var propertyList = "propertyList";
    var arrAll = new Array();
    var firstTagId;
    var snapDataTitle = "快照数据";
    
    $(function(){
      // 初期
      init();
    
      // 信息类列表收起/展开
      $("#tableTitel").click(function(){
        showInfo = fixedWindowByTitle(!showInfo, tableTitel, tableList);
      });

      // 信息类属性信息列表收起/展开
      $("#propertyTitel").click(function(){
        showProperty = fixedWindowByTitle(!showProperty, propertyTitel, propertyList);
      });

      $("#search").click(getSearchList);
      $("#output").click(exportInfoList);
    });
  
    // 初期
    function init() {
      var arrDefault = new Array();
      firstTagId = "${defaultInfo.guid}";
      arrDefault[0] = "${defaultInfo.identityName}";
      arrDefault[1] = "${defaultInfo.name}";
      arrDefault[2] = "${defaultInfo.guid}";
      $("#showClassId").val(firstTagId);
      arrAll.push(arrDefault);
    
      // 信息类列表表示初期状态
      fixedWindowByTitle(showInfo, tableTitel, tableList);

      // 信息类属性表示初期状态
      fixedWindowByTitle(showProperty, propertyTitel, propertyList);

      $("#chk_${defaultInfo.identityName}").attr("checked", "checked");
      
      addProperty("init");
    }
  
    // 增加信息类属性信息
    function addProperty(val) {
      
      setTagRoll();

      var obj = $("#catalogs ul:first");
      var htmls = "";
      obj.children().remove();
    
      for (var i = 0; i < arrAll.length; i++) {
        htmls = htmls + "<li name='tag'><a href='#'><span>" + arrAll[i][1] + "</span></a><input type='hidden' value='" + arrAll[i][2] + "'/></li>";
      }
      obj.append(htmls);
    
      $("#catalogs li:first").addClass("ha");
      
      $("#catalogs a").click(function(){
        var classId = $(this).next("input[type='hidden']").val();
        // 选中的选项卡不是当前选项卡
        if (classId != $("#showClassId").val()) {
          $("#catalogs li").removeClass("ha");
          $(this).closest("li").addClass("ha");
          getPropertyData(classId);
          getInfoList(classId);
          $("#showClassId").val(classId);
          $("#snapshotList").html(snapDataTitle + "_" + $(this).find("span").html());
        }
      });
      
      if ($("#catalogs li:first").length > 0) {
        $("#snapshotList").html(snapDataTitle + "_" + $("#catalogs li:first").find("span").html());
        getTagPerporty(val);
        $("#search").removeAttr("disabled");
        $("#output").removeAttr("disabled");
      } else {
        $("#snapshotList").html(snapDataTitle);
        $("#snap_content").empty();
        $("#infoView").empty();
        $("#showClassId").val("");
        firstTagId = "";
        $("#search").attr("disabled", true);
        $("#output").attr("disabled", true);
      }
    }
    
    function setTagRoll() {
      var i = 0;
      //$(".btn_up").attr('class','btn_up_disable');
      $(".btn_up_down .btn_down").live('click',function() {
        var ulHeight = $("#catalogs ul").height();
        var len = Math.ceil(ulHeight/26);
        if(!$(this).parents("#catalogs").children("ul").is(":animated")) {
          if(i < len - 1) {
            i++;
            $(this).siblings("span").attr("class","btn_up");
            $(this).parents("#catalogs").children("ul").animate({marginTop:(-26*i)+"px"},200);
          }
          if(i == len-1) {
            //$(this).attr('class','btn_down_disable');
          }
        }
      });
      $(".btn_up_down .btn_up").live('click',function() {
        var ulHeight = $("#catalogs ul").height();
        var len = Math.ceil(ulHeight/26);
        if(!$(this).parents("#catalogs").children("ul").is(":animated")) {
          if(i > 0 ) {
            i--;
            $(this).siblings("span").attr("class","btn_down");
            $(this).parents("#catalogs").children("ul").animate({marginTop:(-26*i)+"px"},200);
          }
          if(i == 0){
            //$(this).attr('class','btn_up_disable');
          }
        }
      });
    }
    // 取得属性
    function getTagPerporty(val) {

      var firstTagObj = $("#catalogs li:first input[type='hidden']");
      
      if (firstTagObj.length > 0) {
        // 初期
        if (val == "init") {
          getPropertyData(firstTagObj.val());
          getInfoList(firstTagObj.val());
        } else if (val == "change" && firstTagId != firstTagObj.val()) {
          getPropertyData(firstTagObj.val());
          getInfoList(firstTagObj.val());
          firstTagId = firstTagObj.val();
          $("#showClassId").val(firstTagId);
        }
      }
    }
  
    function getPropertyData(classId) {
      var successCall = function(d) {
        try{
          var data = $.parseJSON(d);
          if(data.success == false) {
            tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
            $("#window-sure").click(function() {
            divClose();
          });
          }
        } catch(e) {
          $("#snap_content").empty();
          $("#snap_content").append(d);
          //var pageSize = $("#snap").val();
          //fillRows(pageSize, "list_head2", "list_body2", false);//填充空行
        }
      };

      $("#windown-content").unbind("ajaxStart");
      var param = $("#snap").serialize();
      $.ajax({
        url:_path+"/baseinfo/snapshotInfo_property.html?classId=" + classId,
        type:"post",
        data:param,
        cache:false,
        dataType:"html",
        success:successCall
      });
    }
    
    // 查询
    function getSearchList() {
       getInfoList($("#showClassId").val());
    }
    
    // 查询
    function getInfoList(classId) {
      var successCall = function(d) {
        try{
          var data = $.parseJSON(d);
          if(data.success == false) {
            tipsWindown("提示信息","text:"+data.html,"340","120","true","","true","id");
            $("#window-sure").click(function() {
            divClose();
          });
          }
        } catch(e) {
          $("#infoView").empty();
          $("#infoView").append(d);
          var pageSize = $("#info").val();
          fillRows(pageSize, "", "", false);//填充空行
        }
      };

      $("#windown-content").unbind("ajaxStart");
      var p1 = $("input[name='hdnExpress'], select[name='snapTimeStart'], select[name='snapTimeEnd'], input[name='dwm'], input[name='gh'], input[name='xm']").serialize();
      var param = $("#info").serialize();
      $.ajax({
        url:_path+"/baseinfo/snapshotInfo_infoView.html?" + p1 + "&classId=" + classId,
        type:"post",
        data:param,
        cache:false,
        dataType:"html",
        success:successCall
      });
    }
    
    // 导出
    function exportInfoList() {
      var content = '<form id="exportForm" method="post" action="<%=request.getContextPath()%>/baseinfo/snapshotInfo_export.html">';
      var p1 = $("input[name='hdnExpress'], select[name='snapTimeStart'], select[name='snapTimeEnd'], input[name='dwm'], input[name='gh'], input[name='xm']").serialize();
      content +=    '';
      content +=    '</form>';
      $('body').append(content);
      $("#exportForm").attr("action","<%=request.getContextPath()%>/baseinfo/snapshotInfo_export.html?" + p1 + "&classId=" + $("#showClassId").val());
      $('#exportForm').submit();
      $('#exportForm').remove();
    }
  
    // 展开/收起
    function fixedWindowByTitle(flg, titleName, lstName) {
      if (flg) {
        $("#" + lstName).show();
        $("#" + titleName + " a").text("收起");
        $("#" + titleName + " a").removeClass().addClass("up");
        return true;
      } else {
        $("#" + lstName).hide();
        $("#" + titleName + " a").text("展开");
        $("#" + titleName + " a").removeClass().addClass("down");
        return false;
      }
    }
  
    // 展开/收起信息类
    function fixedWindow(obj) {
      // 0：收起状态、1：展开状态
      var showFlg = $("#show_" + obj).val();
      // 信息类展示区域
      var showObj =  $("#ls_" + obj);
    
      // 收起状态
      if (showFlg == "0") {
        showObj.show();
        $("#show_" + obj).val("1");
      } else {
        showObj.hide();
        $("#show_" + obj).val("0");
      }
    }
  
    // 全选中
    function selectAll(obj) {
      $("#ls_" + obj + " [type='checkbox']").attr("checked", "checked");
      $("#ls_" + obj + " [type='checkbox']").each(changeObj);
    }
  
    // 全取消
    function cancelAll(obj) {
      $("#ls_" + obj + " [type='checkbox']").removeAttr("checked");
      $("#ls_" + obj + " [type='checkbox']").each(changeObj);
    }
    
    function changeObj() {
      var id = $(this).attr("id").split("chk_")[1];
      selectObj(this, $(this).nextAll("#hdn_inm_" + id).val(),
                $(this).nextAll("#hdn_nm_" + id).val(),
                $(this).nextAll("#hdn_cid_" + id).val());
    }
  
    function selectObj(obj, id, name, classId) {
      var arrTable = new Array();
      var arrAllength = arrAll.length;
      var isHave = false;
      var startIndex = -1;
      arrTable[0] = id;
      arrTable[1] = name;
      arrTable[2] = classId;

      // 选中的时候
      if (obj.checked) {
        for (var i = 0; i < Number(arrAllength); i++) {
          if(arrAll[i][2] == classId) {
            isHave = true;
            break;
          }
        }
        // 没有元素
        if (!isHave) {
          // 添加
          arrAll.push(arrTable);
        }
      } else {
        for (var j = 0; j < Number(arrAllength); j++) {
          if(arrAll[j][2] == classId) {
            startIndex = j;
            break;
          }
        }

        if (startIndex != -1) {
          // 删除
          arrAll.splice(startIndex, 1);
        }
      }
      
      addProperty("change");
    }
    
    function saveProperty() {
        var param = $("input[name='showClassId'], input[name='propertyId']:checked").serialize();
        $.post('<%=request.getContextPath()%>/baseinfo/snapshotInfo_save.html' , param, function(data){
            var callback = function(){
                window.location.href = location.href;
            };
            processDataCall(data, callback);
        },"json");
    }

    var changePage = function(){
        var classId = $("#showClassId").val();
        getInfoList(classId);
    };
  </script>
</head>

<body>
  <!-- 信息类列表 -->
  <!-- Start -->
  <div>
    <input type="hidden" id="showClassId" name="showClassId" value=""/>
    <div class="datetitle_01" style="font-weight:bold;">信息类列表</div>
    <div id="tableTitel" class="more--item_bottom" style="clear:both;margin-bottom:5px"><p><a href="#" ></a></p></div>
    <div class="mes_list" id="tableList" style="display:block;">
      <s:iterator value="infoList" var="catalog" status="statusH">
      <div class="datetitle_01">
        <input type="hidden" id="show_${catalog.guid}" value="0"/>
        <!-- 信息类分类 -->
        <h2 style="text-align:left;width:500px;" onclick="fixedWindow('${catalog.guid}');">
          <a href="#" class="up">${catalog.name}</a>
          <input type="hidden" id="hdnCat_${catalog.guid}" value="${catalog.guid}"/>
        </h2>
        <h2 style="text-align:right;width:150px;"><a onclick="selectAll('${catalog.guid}');">全选择</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="cancelAll('${catalog.guid}')">全取消</a></h2>
      </div>
      <div id="ls_${catalog.guid}" class="mes_list_con" style="display:none;">
        <ul>
        <s:iterator value="#catalog.classes" var="infoClass" status="statusD">
          <li>${infoClass.name}
            <input type="checkbox" id="chk_${infoClass.identityName}" onclick="selectObj(this, '${infoClass.identityName}', '${infoClass.name}', '${infoClass.guid}');"/>
            <input type="hidden" id="hdn_inm_${infoClass.identityName}" value="${infoClass.identityName}"/>
            <input type="hidden" id="hdn_nm_${infoClass.identityName}" value="${infoClass.name}"/>
            <input type="hidden" id="hdn_cid_${infoClass.identityName}" value="${infoClass.guid}"/>
          </li>
        </s:iterator>
        </ul>
      </div>
      <div class="clear"></div>
    </s:iterator>
    </div>
  </div>
  <!-- End -->
  
  <!-- 信息类属性信息 -->
  <!-- Start -->
  <div>
    <form action="baseinfo/snapshotInfo_property.html" method="post" id="snap">
    <div class="datetitle_01" style="font-weight:bold;">属性信息</div>
      <div id="propertyTitel" class="more--item_bottom" style="clear:both;margin-bottom:5px"><p><a href="#"></a></p></div>
      <div id="propertyList">
        <div class="comp_title" id="catalogs">
          <ul style="width:85%"></ul>
          <div class="btn_up_down"><span class="btn_up"></span><span class="btn_down"></span></div>
        </div>
        <!-- 属性信息 -->
        <div id="snap_content" class="mes_list">
        </div>
      </div>
    </form>
  </div>
  
  <!-- 快照数据 -->
  <!-- Start -->
    <div class="datetitle_01" style="font-weight:bold;" id="snapshotList">快照数据</div>
    <div align="right">
      <button id="search" class="brn_cx">查询</button>
      <button id="output" class="brn_cx">导出</button>
    </div>
    <form action="baseinfo/snapshotInfo_infoView.html" method="post" id="info">
    <div id="infoView" style="width:750px;">
      
    </div>
    </form>
  <!-- End -->
</body>
</html>