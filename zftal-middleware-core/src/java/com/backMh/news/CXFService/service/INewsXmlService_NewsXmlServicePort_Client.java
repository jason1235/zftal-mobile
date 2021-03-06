
package com.backMh.news.CXFService.service;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.backMh.news.CXFService.service.impl.NewsXmlServiceService;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2012-11-06T09:49:02.140+08:00
 * Generated source version: 2.5.2
 * 
 */
public final class INewsXmlService_NewsXmlServicePort_Client {

    private static final QName SERVICE_NAME = new QName("http://service.news.logic.webservice.general.zfsoft.com/", "NewsXmlServiceService");

    private INewsXmlService_NewsXmlServicePort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = NewsXmlServiceService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        NewsXmlServiceService ss = new NewsXmlServiceService(wsdlURL, SERVICE_NAME);
        INewsXmlService port = ss.getNewsXmlServicePort();  
        
        {
            System.out.println("Invoking getNewsInfo...");
            java.lang.String _getNewsInfo_id = "";
            java.lang.String _getNewsInfo__return = port.getNewsInfo(_getNewsInfo_id);
            System.out.println("getNewsInfo.result=" + _getNewsInfo__return);


            }
            {
            System.out.println("Invoking getTypeNewsPageListNew...");
            java.lang.String _getTypeNewsPageListNew_newtype = "";
            int _getTypeNewsPageListNew_start = 0;
            int _getTypeNewsPageListNew_size = 0;
            java.lang.String _getTypeNewsPageListNew__return = port.getTypeNewsPageListNew(_getTypeNewsPageListNew_newtype, _getTypeNewsPageListNew_start, _getTypeNewsPageListNew_size);
            System.out.println("getTypeNewsPageListNew.result=" + _getTypeNewsPageListNew__return);


            }
            {
            System.out.println("Invoking getAllNewsTypeList...");
            java.lang.String _getAllNewsTypeList__return = port.getAllNewsTypeList();
            System.out.println("getAllNewsTypeList.result=" + _getAllNewsTypeList__return);


            }
            {
            System.out.println("Invoking getTypeNewsPageList...");
            java.lang.String _getTypeNewsPageList_newtype = "";
            int _getTypeNewsPageList_start = 0;
            int _getTypeNewsPageList_size = 0;
            java.lang.String _getTypeNewsPageList__return = port.getTypeNewsPageList(_getTypeNewsPageList_newtype, _getTypeNewsPageList_start, _getTypeNewsPageList_size);
            System.out.println("getTypeNewsPageList.result=" + _getTypeNewsPageList__return);


            }
            {
            System.out.println("Invoking getTypeIdNewsPageList...");
            java.lang.String _getTypeIdNewsPageList_tid = "";
            int _getTypeIdNewsPageList_start = 0;
            int _getTypeIdNewsPageList_size = 0;
            java.lang.String _getTypeIdNewsPageList__return = port.getTypeIdNewsPageList(_getTypeIdNewsPageList_tid, _getTypeIdNewsPageList_start, _getTypeIdNewsPageList_size);
            System.out.println("getTypeIdNewsPageList.result=" + _getTypeIdNewsPageList__return);


            }
            {
            System.out.println("Invoking getIndexNewsList...");
            int _getIndexNewsList_start = 0;
            int _getIndexNewsList_size = 0;
            java.lang.String _getIndexNewsList__return = port.getIndexNewsList(_getIndexNewsList_start, _getIndexNewsList_size);
            System.out.println("getIndexNewsList.result=" + _getIndexNewsList__return);


            }
            {
            System.out.println("Invoking getIndexNewsListNew...");
            int _getIndexNewsListNew_start = 0;
            int _getIndexNewsListNew_size = 0;
            java.lang.String _getIndexNewsListNew__return = port.getIndexNewsListNew(_getIndexNewsListNew_start, _getIndexNewsListNew_size);
            System.out.println("getIndexNewsListNew.result=" + _getIndexNewsListNew__return);


            }
            {
            System.out.println("Invoking getTypeIdNewsPageListNew...");
            java.lang.String _getTypeIdNewsPageListNew_tid = "";
            int _getTypeIdNewsPageListNew_start = 0;
            int _getTypeIdNewsPageListNew_size = 0;
            java.lang.String _getTypeIdNewsPageListNew__return = port.getTypeIdNewsPageListNew(_getTypeIdNewsPageListNew_tid, _getTypeIdNewsPageListNew_start, _getTypeIdNewsPageListNew_size);
            System.out.println("getTypeIdNewsPageListNew.result=" + _getTypeIdNewsPageListNew__return);


            }

        System.exit(0);
    }

}
