package com.zfsoft.hrm.print.action;

import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.html.ParseFactory;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewParse;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.dataorigin.service.IDataOriginService;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.hrm.print.entity.PrintTemplateEntity;
import com.zfsoft.hrm.print.entity.PrintTemplateProperty;
import com.zfsoft.hrm.print.service.IPrintTemplateService;

/**
 * 
 * @author Administrator
 *
 */
public class PrintTemplateAction extends HrmAction {
    /**
     * 
     */
    private static final long serialVersionUID = 4059900873721251916L;
    private IPrintTemplateService printTemplateService;
    private IDataOriginService dataOriginService;
    private PrintTemplateEntity model;
    private PrintTemplateEntity query;
    private PrintTemplateProperty property;
    private String jumpId;
    private String imageId;
    private String recordId;
    
    /**
     * 打印模板一览
     * @return
     */
    public String page() {
        List<PrintTemplateEntity> ret = printTemplateService.getPrintTemplateList();
        
        if (ret == null || ret.size() == 0) {
            return "page";
        }
        
        if (query != null && !StringUtils.isEmpty(query.getId())) {
            model = printTemplateService.findById(query.getId());
        } else {
            model = ret.get(0);
        }
        
        Map<String, String> param = new HashMap<String, String>();
        param.put("ywId", model.getMbbzm());
        List<XmlBillProperty> xbps = dataOriginService.getPropertiesByParam(param);
        
        this.getValueStack().set("fonts", GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        this.getValueStack().set("templates", ret);
        this.getValueStack().set("template", model);
        this.getValueStack().set("properties", model.getProperties());
        this.getValueStack().set("items", xbps);
        
        return "page";
    }
    
    /**
     * 添加/修改模板画面
     * @return
     * @throws InterruptedException 
     */
    public String edit() throws InterruptedException {
        if (model != null) {
            PrintTemplateEntity q = new PrintTemplateEntity();
            
            if (!StringUtils.isEmpty(model.getId())) {
                model = printTemplateService.findById(model.getId());
            }
            q.setMbbzm(model.getMbbzm());
            List<PrintTemplateEntity> models = printTemplateService.findByParam(q);
            
            if (models == null || models.size() == 0) {
                jumpId = "readd";
                String imageHtml = getImageHtml("", "edit");
                this.getValueStack().set("imageHtml", imageHtml);
                return "edit";
            }
            for (PrintTemplateEntity m : models) {
                m.setImageHtml(getImageHtml(m.getBjid(), "view"));
            }
            this.getValueStack().set("models", models);
        } else {
            String imageHtml = getImageHtml("", "edit");
            this.getValueStack().set("imageHtml", imageHtml);
        }
        return "edit";
    }
    
    /**
     * 添加属性画面
     * @return
     */
    public String editProperty() {
        Map<String, String> param = new HashMap<String, String>();
        param.put("ywId", model.getMbbzm());
        List<XmlBillProperty> xbps = dataOriginService.getPropertiesByParam(param);
        this.getValueStack().set("items", xbps);
        this.getValueStack().set("fonts", GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        return "editProperty";
    }
    
    /**
     * 编辑
     * @return
     */
    public String editImage() {
        this.getValueStack().set("data", getImageHtml(imageId, "edit"));
        return DATA;
    }
    
    /**
     * 保存
     * @return
     */
    public String saveImage() {
        ImageDB im = null;
        String path = "";
        BufferedImage img;
        Map<String, String> ret = new HashMap<String, String>();
        
        try {
            im = ImageDBUtil.getImageDBByGuid(model.getBjid());
            if (im == null) {
                ret.put("error", "yes");
                ret.put("msg", "请上传图片");
                this.getValueStack().set("data", ret);
                return DATA;
            }
            path = getSession().getServletContext().getRealPath("/") + "img/" + im.getFileName();
            Byte_File_Object.getFileFromBytes(im.getFileContent(), path);
            
            img = ImageIO.read(new FileInputStream(path));
            
            model.setBjlj("../img/" + im.getFileName());
            
            model.setBjkd(img.getWidth());
            model.setBjgd(img.getHeight());
            
            if (StringUtils.isEmpty(model.getId())) {
                printTemplateService.addTemplate(model);
                ret.put("recordId", model.getId());
            } else {
                PrintTemplateEntity nowEntity = printTemplateService.findById(model.getId());
                model.setNr(nowEntity.getNr());
                printTemplateService.modifyTemplate(model);
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ret.put("vhtml", getImageHtml(model.getBjid(), "view"));

        this.getValueStack().set("data", ret);
        return DATA;
    }
    
    /**
     * 添加/修改模板
     * @return
     */
    public String saveTemplate() {

        String[] bjid = getRequest().getParameterValues("bjid");
        String[] mbmc = getRequest().getParameterValues("mbmc");
        
        PrintTemplateEntity insertDB = null;
        ImageDB im = null;
        String path = "";
        BufferedImage img;
        
        try {
            for (String b : bjid) {
                if (StringUtils.isEmpty(b)) {
                    setErrorMessage("请上传图片");
                    this.getValueStack().set("data", getMessage());
                    return DATA;
                }
            }
            
            for (int i = 0; i < mbmc.length; i++) {
                insertDB = new PrintTemplateEntity();
                insertDB.setMbbzm(model.getMbbzm());
                
                insertDB.setMbmc(mbmc[i]);
                
                insertDB.setBjid(bjid[i]);
                
                im = ImageDBUtil.getImageDBByGuid(bjid[i]);
                path = getSession().getServletContext().getRealPath("/") + "img/" + im.getFileName();
                Byte_File_Object.getFileFromBytes(im.getFileContent(), path);
                
                img = ImageIO.read(new FileInputStream(path));

                
                insertDB.setBjlj("../img/" + im.getFileName());
                
                insertDB.setBjkd(img.getWidth());
                insertDB.setBjgd(img.getHeight());
                
                if (StringUtils.isEmpty(insertDB.getId())) {
                    printTemplateService.addTemplate(insertDB);
                } else {
                    PrintTemplateEntity nowEntity = printTemplateService.findById(insertDB.getId());
                    insertDB.setNr(nowEntity.getNr());
                    printTemplateService.modifyTemplate(insertDB);
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.getValueStack().set("data", getMessage());
        return DATA;
    }
    
    /**
     * 保存单个属性
     * @return
     */
    public String saveProperty() {
        
        PrintTemplateEntity nowEntity = printTemplateService.findById(model.getId());
        List<PrintTemplateProperty> ps = nowEntity.getProperties();
        
        if (ps == null) {
            ps = new ArrayList<PrintTemplateProperty>();
        }
        ps.add(initProperty(property));
        nowEntity.setProperties(ps);
        printTemplateService.modifyTemplate(nowEntity);
        
        this.getValueStack().set("data", getMessage());
        return DATA;
    }
    
    /**
     * 保存属性
     * @return
     */
    public String saveAll() {
        PrintTemplateEntity nowEntity = printTemplateService.findById(model.getId());
        List<PrintTemplateProperty> ps = new ArrayList<PrintTemplateProperty>();
        
        // 项目类别
        String[] xmType = getRequest().getParameterValues("xmType");
        // 项目
        String[] xm = getRequest().getParameterValues("xm");
        // 内容
        String[] nr = getRequest().getParameterValues("nr");
        // 横坐标
        String[] xzb = getRequest().getParameterValues("xzb");
        // 纵坐标
        String[] yzb = getRequest().getParameterValues("yzb");
        // 项目宽
        String[] xmk = getRequest().getParameterValues("xmk");
        // 项目高
        String[] xmg = getRequest().getParameterValues("xmg");
        // 字体
        String[] font = getRequest().getParameterValues("font");
        // 字体大小
        String[] fontSize = getRequest().getParameterValues("fontSize");
        // 粗体
        String[] bold = getRequest().getParameterValues("bold");
        // 斜体
        String[] italic = getRequest().getParameterValues("italic");
        // 下划线
        String[] underline = getRequest().getParameterValues("underline");
        // 字体位置
        String[] fontLocation = getRequest().getParameterValues("fontLocation");
        // 下标
        String[] mark = getRequest().getParameterValues("mark");

        if (xm != null && xm.length > 0) {
            for (int i = 0; i < xm.length; i++) {
                PrintTemplateProperty p = new PrintTemplateProperty();
                p.setXmType(xmType[i]);
                p.setXm(xm[i]);
                p.setNr(nr[i]);;
                p.setXzb(xzb[i]);
                p.setYzb(yzb[i]);
                p.setXmk(xmk[i]);
                p.setXmg(xmg[i]);
                p.setFont(font[i]);
                p.setFontSize(fontSize[i]);
                p.setBold(bold[i]);
                p.setItalic(italic[i]);
                p.setUnderline(underline[i]);
                p.setFontLocation(fontLocation[i]);
                p.setMark(mark[i]);

                ps.add(initProperty(p));
            }
        }

        nowEntity.setProperties(ps);
        printTemplateService.modifyTemplate(nowEntity);
        
        this.getValueStack().set("data", getMessage());
        return DATA;
    }
    
    /**
     * 删除
     * @return
     */
    public String remove() {
        printTemplateService.remove(model.getId());
        this.getValueStack().set("data", getMessage());
        return DATA;
    }
    
    /**
     * 添加背景
     * @return
     */
    public String addImage() {
        this.getValueStack().set("data", getImageHtml("", "edit"));
        return DATA;
    }
    
    /**
     * 初始化
     * @param property
     * @return
     */
    private PrintTemplateProperty initProperty(PrintTemplateProperty property) {
        // 内容
        if (StringUtils.isEmpty(property.getNr())) {
            property.setNr(property.getXm());
        }

        // 横坐标
        if (StringUtils.isEmpty(property.getXzb())) {
            property.setXzb("0");
        }

        // 纵坐标
        if (StringUtils.isEmpty(property.getYzb())) {
            property.setYzb("0");
        }

        // 项目宽
        if (StringUtils.isEmpty(property.getXmk())) {
            property.setXmk("125");
        }

        // 项目高
        if (StringUtils.isEmpty(property.getXmg())) {
            property.setXmg("25");
        }

        // 字体
        if (StringUtils.isEmpty(property.getFont())) {
            property.setFont("宋体");
        }

        // 字体大小
        if (StringUtils.isEmpty(property.getFontSize())) {
            property.setFontSize("15");
        }
        
        // 粗体
        if (StringUtils.isEmpty(property.getBold())) {
            property.setBold("0");
        }
        
        // 斜体
        if (StringUtils.isEmpty(property.getItalic())) {
            property.setItalic("0");
        }
        
        // 下划线
        if (StringUtils.isEmpty(property.getUnderline())) {
            property.setUnderline("0");
        }
        
        // 字体位置
        if (StringUtils.isEmpty(property.getFontLocation())) {
            property.setFontLocation("left");
        }
        return property;
    }
    
    /**
     * 上传背景
     * @param value 
     * @return
     */
    private String getImageHtml(String value, String type) {
        String imageHtml = "";
        InfoProperty prop = new InfoProperty();
        
        prop.setFieldName("bjid");
        prop.setFieldType(Type.IMAGE);
        prop.setSize(1024);
        prop.setWidth(512);
        prop.setHeight(512);
        
        if ("edit".equals(type)) {
            imageHtml = ParseFactory.EditParse(prop, value);
        } else {
            imageHtml = ViewParse.parse(prop, value);
        }
        
        return imageHtml;
    }

    /**
     * @return the printTemplateService
     */
    public IPrintTemplateService getPrintTemplateService() {
        return printTemplateService;
    }

    /**
     * @param printTemplateService the printTemplateService to set
     */
    public void setPrintTemplateService(IPrintTemplateService printTemplateService) {
        this.printTemplateService = printTemplateService;
    }

    /**
     * @return the model
     */
    public PrintTemplateEntity getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(PrintTemplateEntity model) {
        this.model = model;
    }

    /**
     * @return the query
     */
    public PrintTemplateEntity getQuery() {
        return query;
    }

    /**
     * @param query the query to set
     */
    public void setQuery(PrintTemplateEntity query) {
        this.query = query;
    }

    /**
     * @return the property
     */
    public PrintTemplateProperty getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(PrintTemplateProperty property) {
        this.property = property;
    }

    /**
     * @return the jumpId
     */
    public String getJumpId() {
        return jumpId;
    }

    /**
     * @param jumpId the jumpId to set
     */
    public void setJumpId(String jumpId) {
        this.jumpId = jumpId;
    }

    /**
     * @return the imageId
     */
    public String getImageId() {
        return imageId;
    }

    /**
     * @param imageId the imageId to set
     */
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    /**
     * @return the recordId
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the dataOriginService
     */
    public IDataOriginService getDataOriginService() {
        return dataOriginService;
    }

    /**
     * @param dataOriginService the dataOriginService to set
     */
    public void setDataOriginService(IDataOriginService dataOriginService) {
        this.dataOriginService = dataOriginService;
    }

}
