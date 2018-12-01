<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
    <script type="text/javascript">
    
    $(document).ready(function() {
        var array = $("li[name^='fdm_']");
        var farray = $("li[name='dmcd']");
        var fdmcd = $("li[name='dmcd']:first").attr("dmcd");
        $.each(array, function(i,n) {
            if ($(array[i]).attr("name") != ("fdm_" + fdmcd)) {
                $(array[i]).css('display','none');
            }
        });
        
        $.each(farray, function(i,n) {
            if (i != 0 && farray[i] != "") {
                $(farray[i]).attr('ocf', 'close');
            }
        });
        
        $("li.active").click(function() {
            var dmcd = "fdm_" + $(this).attr("dmcd");
            var nowstyle = $(this).attr("ocf");
            if (nowstyle == "open") {
                $(this).attr("ocf", "close");
                $("li[name='" + dmcd + "']").css('display','none');
            } else {
                $.each(array,function(i,n) {
                    $(array[i]).css('display','none');
                });
                
                $.each(farray,function(i,n) {
                    $(farray[i]).attr('ocf', 'close');
                });
                $(this).attr("ocf", "open");
                $("li[name='" + dmcd + "']").css('display','block');
            }
        });
        $("a.open_03").click(function(){
            $("a.open_03").css("background","");
            $(this).css("background","#DCE8F8");
            var menuName_1 = $("#li_${gnmkdm}",self.parent.document).html();
            var fcd = $(this).closest("li").attr("name").substr(4);
            var menuName_2 = $("li[dmcd='" + fcd + "']").find("span").html();
            var menuName_3 = $(this).find("span").html();
            $("#menuLink",self.parent.document).html(menuName_1+" > "+menuName_2+" > "+menuName_3);
        });

        var fa = $("li[name^='fdm_']:first").find("a");
        self.parent.frames['xg_rightFrame'].location.href=fa.attr("href");
        fa.click();
    });

</script>
</head>
    <body >
        <ul class="nav nav-pills nav-stacked menuborder">
            <s:if test="menuList != null && menuList.size() > 0">
                <s:iterator var="menu" value="menuList" status="sta">
                    <s:if test="#menu.sjMenu==null || #menu.sjMenu.isEmpty()">
                        <li role="presentation">
                            <a href="<%=jsPath %>${menu.DYYM}" target="xg_rightFrame" ><span>${menu.GNMKMC}</span></a>
                        </li>
                    </s:if>
                    <s:if test="#menu.sjMenu!=null && !#menu.sjMenu.isEmpty()">
                        <li role="presentation" class="active" name="dmcd" dmcd="${menu.GNMKDM}" ocf="open"><a href="#"><span>${menu.GNMKMC}</span></a></li>
                        <s:iterator var="zmenu" value="#menu.sjMenu" >
                            <li role="presentation" name="fdm_${menu.GNMKDM}">
                                <a href="<%=systemPath %>${zmenu.DYYM}" target="xg_rightFrame" class="open_03"><span>${zmenu.GNMKMC}</span></a>
                            </li>
                        </s:iterator>
                    </s:if>
                </s:iterator>
            </s:if>
            <s:else>
                <li role="presentation" class="disabled "><a href="#">暂无任何功能模块信息！</a></li>
            </s:else>
        </ul>
    </body>
</html>