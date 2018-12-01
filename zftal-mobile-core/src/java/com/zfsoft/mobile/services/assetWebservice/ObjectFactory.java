
package com.zfsoft.mobile.services.assetWebservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.zfsoft.mobile.services.assetWebservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.zfsoft.mobile.services.assetWebservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetLastNoticeList }
     * 
     */
    public GetLastNoticeList createGetLastNoticeList() {
        return new GetLastNoticeList();
    }

    /**
     * Create an instance of {@link GetLastNoticeListResponse }
     * 
     */
    public GetLastNoticeListResponse createGetLastNoticeListResponse() {
        return new GetLastNoticeListResponse();
    }

    /**
     * Create an instance of {@link GetLastTodoTaskListResponse }
     * 
     */
    public GetLastTodoTaskListResponse createGetLastTodoTaskListResponse() {
        return new GetLastTodoTaskListResponse();
    }

    /**
     * Create an instance of {@link GetLastTodoTaskList }
     * 
     */
    public GetLastTodoTaskList createGetLastTodoTaskList() {
        return new GetLastTodoTaskList();
    }

}
