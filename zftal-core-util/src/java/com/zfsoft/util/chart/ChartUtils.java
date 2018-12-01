package com.zfsoft.util.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;


public class ChartUtils {
    public static final String TITLE_KEY = "title";
    public static final String X_NAME_KEY = "xname";
    public static final String Y_NAME_KEY = "yname";
    public static final String ROW_KEY = "rowkey";
    public static final String COLUMN_KEY = "columnkey";
    public static final String SHOW_CHART_FRAME = "ChartFrame";
    public static final String SHOW_CHART_PANEL = "ChartPanel";
    public static final String IMAGE_TYPE_JPEG = "jpeg";
    public static final String IMAGE_TYPE_PNG = "png";
    
    /**
     * 显示3D柱形图-默认风格
     * @throws Exception 
     */
    public static void showBarChart3D(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String showType) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, true, true, false);
        showWinBarChart(chart, showType);
    }
    
    /**
     * 显示3D柱形图-柱形颜色自定义
     * @throws Exception 
     */
    public static void showBarChart3D(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String showType, List<String> colors) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, true, true, false);
        if (colors != null && colors.size() > 0) {
            setStyle(chart, colors);
        }
        showWinBarChart(chart, showType);
    }

    /**
     * 显示3D柱形图-自定义风格
     * @throws Exception 
     */
    public static void showBarChart3D(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String showType, List<String> colors, boolean legend, boolean tooltips, boolean urls) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, legend, tooltips, urls);
        if (colors != null && colors.size() > 0) {
            setStyle(chart, colors);
        }
        showWinBarChart(chart, showType);
    }
    
    /**
     * 取得3D柱形图图片-默认风格
     * @return
     * @throws Exception 
     */
    public static String getBarChart3DImage(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String path, String imageName, String imageType) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, true, true, false);
        return createImage(chart, path, imageName, imageType, data[0].length);
    }
    
    /**
     * 取得3D柱形图图片-柱形颜色自定义
     * @return
     * @throws Exception 
     */
    public static String getBarChart3DImage(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String path, String imageName, String imageType, List<String> colors) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, true, true, false);
        if (colors != null && colors.size() > 0) {
            setStyle(chart, colors);
        }
        return createImage(chart, path, imageName, imageType, data[0].length);
    }
    
    /**
     * 取得3D柱形图图片-自定义风格
     * @return
     * @throws Exception 
     */
    public static String getBarChart3DImage(Map<String, String> mt, Map<String, String[]> rck, double[][] data, String path, String imageName, String imageType, List<String> colors, boolean legend, boolean tooltips, boolean urls) throws Exception {
        JFreeChart chart = createBarChart3D(mt, rck, data, legend, tooltips, urls);
        if (colors != null && colors.size() > 0) {
            setStyle(chart, colors);
        }
        return createImage(chart, path, imageName, imageType, data[0].length);
    }
    
    /**
     * 柱形图
     * @return
     * @throws Exception 
     */
    private static JFreeChart createBarChart3D(Map<String, String> mt, Map<String, String[]> rck, double[][] data, boolean legend, boolean tooltips, boolean urls) throws Exception {
        String title = "";
        String categroy = "";
        String value = "";
        
        if (mt != null) {
            if (mt.containsKey(TITLE_KEY)) {
                title = mt.get(TITLE_KEY);
            }
            if (mt.containsKey(X_NAME_KEY)) {
                categroy = mt.get(X_NAME_KEY);
            }
            
            if (mt.containsKey(Y_NAME_KEY)) {
                value = mt.get(Y_NAME_KEY);
            }
        }
        
        if (rck == null || rck.size() == 0) {
            throw new Exception("柱形名称/纵轴名称没有设置！");
        } else {
            if (!rck.containsKey(ROW_KEY)) {
                throw new Exception("柱形名称没有设置！");
            }
            if (!rck.containsKey(COLUMN_KEY)) {
                throw new Exception("纵轴名称没有设置！");
            }
        }
        
        if (data == null || data.length == 0) {
            throw new Exception("没有数据！");
        } else {
            if (rck.get(ROW_KEY).length != data.length || rck.get(COLUMN_KEY).length != data[0].length) {
                throw new Exception("数据设置不正确！");
            }
        }
        
        DefaultCategoryDataset dataset = (DefaultCategoryDataset) DatasetUtilities.createCategoryDataset(rck.get(ROW_KEY), rck.get(COLUMN_KEY), data);
        
        JFreeChart chart = ChartFactory.createBarChart3D(title, categroy, value, dataset, PlotOrientation.VERTICAL, true, true, false);
        // 整个窗口的背景色 默认为白色
//        chart.setBackgroundPaint(Color.red)
        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 20));
        chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
        
        // 表区域
        CategoryPlot plot = chart.getCategoryPlot();
        // 横轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 15));
        domainAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));

        // 纵轴
        ValueAxis rangeAxis = plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 15));
        rangeAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 15));
        return chart;
    }
    
    /**
     * 设置风格
     * @param chart
     * @param colors
     */
	private static void setStyle(JFreeChart chart, List<String> colors) {
        // 表区域
        CategoryPlot plot = chart.getCategoryPlot();
        // 表区域背景色 默认为灰色
//        plot.setBackgroundPaint(Color.blue);
        // 横坐标网格线颜色 默认白色
//        plot.setDomainGridlinePaint(Color.red);
        // 横坐标网格线是否可见 默认不可见
//        plot.setDomainGridlinesVisible(true);
        // 纵坐标网格线颜色 默认白色
//        plot.setRangeGridlinePaint(Color.blue);
        // 纵坐标网格线是否可见 默认可见
//        plot.setRangeGridlinesVisible(false);
    
        BarRenderer3D renderer = (BarRenderer3D)plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setItemLabelFont(new Font("黑体", Font.PLAIN, 9));
        renderer.setItemLabelsVisible(true);
        // 设置柱体颜色
        for (int i = 0; i < colors.size(); i++) {
            if (!StringUtils.isEmpty(colors.get(i).trim())) {
                renderer.setSeriesPaint(i, new Color(Integer.parseInt(colors.get(i).trim(), 16)));
            }
        }
        
        // 设置数据柱外轮廓线是否显示 默认不显示
//        renderer.setDrawBarOutline(true);
        plot.setRenderer(renderer);
    }
    
    /**
     * 显示窗口
     * @param chart
     */
    private static void showWinBarChart(JFreeChart chart, String showType) {
        if (SHOW_CHART_FRAME.equals(showType)) {
            ChartFrame cf = new ChartFrame("Test", chart);
            cf.pack();
            cf.setVisible(true);
        } else {
            ChartPanel p = new ChartPanel(chart);
            JFrame frame = new JFrame();
            Container c = frame.getContentPane();
            c.add(p, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    }
    
    private static String createImage(JFreeChart chart, String path, String imageName, String imageType, int x) throws Exception {
        FileOutputStream out = null;
        String fileName = "";
        if (StringUtils.isEmpty(path)) {
            throw new Exception("没有到处路径！");
        }
        
        if (StringUtils.isEmpty(imageName)) {
            throw new Exception("没有设置图片名称！");
        }
        
        try {
            fileName = path + "\\" + imageName;
            if (IMAGE_TYPE_JPEG.equals(imageType)) {
                fileName += ".jpeg";
            } else {
                fileName += ".png";
            }
            File outFile = new File(fileName);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            
            out = new FileOutputStream(fileName);
            if (IMAGE_TYPE_JPEG.equals(imageType)) {
                ChartUtilities.writeChartAsJPEG(out, chart, x * 160, x * 80);
            } else {
                ChartUtilities.writeChartAsPNG(out, chart, x * 160, x * 80);
            }
            out.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }
}
