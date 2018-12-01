
package com.ts.CXFService.service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.ts.CXFServe.service package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.ts.CXFServe.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetUserIdAndRegistrationId }
     * 
     */
    public SetUserIdAndRegistrationId createSetUserIdAndRegistrationId() {
        return new SetUserIdAndRegistrationId();
    }

    /**
     * Create an instance of {@link GetPushMsg }
     * 
     */
    public GetPushMsg createGetPushMsg() {
        return new GetPushMsg();
    }

    /**
     * Create an instance of {@link SetUserIdAndRegistrationIdResponse }
     * 
     */
    public SetUserIdAndRegistrationIdResponse createSetUserIdAndRegistrationIdResponse() {
        return new SetUserIdAndRegistrationIdResponse();
    }

    /**
     * Create an instance of {@link GetSendMsg }
     * 
     */
    public GetSendMsg createGetSendMsg() {
        return new GetSendMsg();
    }

    /**
     * Create an instance of {@link PushResponse }
     * 
     */
    public PushResponse createPushResponse() {
        return new PushResponse();
    }

    /**
     * Create an instance of {@link Push }
     * 
     */
    public Push createPush() {
        return new Push();
    }

    /**
     * Create an instance of {@link GetPushMsgResponse }
     * 
     */
    public GetPushMsgResponse createGetPushMsgResponse() {
        return new GetPushMsgResponse();
    }

    /**
     * Create an instance of {@link GetSendMsgResponse }
     * 
     */
    public GetSendMsgResponse createGetSendMsgResponse() {
        return new GetSendMsgResponse();
    }

}
