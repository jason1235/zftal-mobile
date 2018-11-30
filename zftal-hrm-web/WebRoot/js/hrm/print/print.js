// 直接打印
var print_type_int = "PRINT";
// 预览
var print_type_view = "PREVIEW";
// 预览维护
var print_type_setup = "PRINT_SETUP";
// 预览设计
var print_type_design = "PRINT_DESIGN";

/**
 * 打印
 * @param dataMap
 */
function createPrintPage(dataMap, type) {
    $.each(dataMap.mbs, function(j, mb) {
        LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
        LODOP.PRINT_INITA(0, 0, mb.bjkd, mb.bjgd, "打印" + mb.mbmc);
        LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='" + mb.bjlj + "'>");
        if (mb.properties != null) {
            var key = "";
            $.each(mb.properties, function(i, value) {
                if (value.xmType == "1") {
                    key = value.xm;
                    if (value.mark != "") {
                        key += "(" + value.mark;
                    }
                    LODOP.ADD_PRINT_TEXT(value.yzb,value.xzb,value.xmk,value.xmg, dataMap[key]);
                } else {
                    LODOP.ADD_PRINT_TEXT(value.yzb,value.xzb,value.xmk,value.xmg, value.nr);
                }
                
                LODOP.SET_PRINT_STYLEA(0, "FontName", value.font);
                LODOP.SET_PRINT_STYLEA(0, "FontSize", value.fontSize);
                if (value.bold == "1") {
                    LODOP.SET_PRINT_STYLEA(0, "Bold", 1);
                }
                
                if (value.italic == "1") {
                    LODOP.SET_PRINT_STYLEA(0, "Italic", 1);
                }
                
                if (value.underline == "1") {
                    LODOP.SET_PRINT_STYLEA(0, "Underline", 1);
                }
                
                if (value.fontLocation == "center") {
                    LODOP.SET_PRINT_STYLEA(0, "Alignment", 2);
                } else if (value.fontLocation == "right") {
                    LODOP.SET_PRINT_STYLEA(0, "Alignment", 3);
                }
            });
        }
        
        if (type == print_type_int) {
            LODOP.PRINT();
        } else if (type == print_type_view) {
            LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
            LODOP.PREVIEW();
        } else if (type == print_type_setup) {
            LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
            LODOP.PRINT_SETUP();
        } else if (type == print_type_design) {
            LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
            LODOP.PRINT_DESIGN();
        } else {
            LODOP.SET_SHOW_MODE("HIDE_GROUND_LOCK",true);//隐藏纸钉按钮
            LODOP.PREVIEW();
        }
    });
    
}