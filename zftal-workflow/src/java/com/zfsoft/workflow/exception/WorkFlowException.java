/**
 * created since Mar 12, 2009
 */
package com.zfsoft.workflow.exception;

/**
 * @author taobao
 * @version $Id: ServiceException.java,v 0.1 Mar 12, 2009 1:57:57 PM taobao Exp $
 */
public class WorkFlowException extends BaseException {
    private static final long serialVersionUID = -1245376472841943513L;
    /** 序列值 */


    private boolean printable=true;

    /**
     *
     */
    public WorkFlowException() {
        super();
    }

    /**
     * @param message
     */
    public WorkFlowException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public WorkFlowException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public WorkFlowException(Throwable cause) {
        super(cause);
    }

    public WorkFlowException(Throwable cause,boolean printable){
        super(cause);
        this.printable=printable;
    }

    public WorkFlowException(boolean printable) {
        super();
        this.printable=printable;
    }

    /**
     * @param message
     */
    public WorkFlowException(String message,boolean printable) {
        super(message);
        this.printable=printable;
    }

    /**
     * @param message
     * @param cause
     */
    public WorkFlowException(String message, Throwable cause,boolean printable) {
        super(message, cause);
        this.printable=printable;
    }

    /**
     * @return the printable
     */
    public boolean isPrintable() {
        return printable;
    }

    /**
     * @param printable the printable to set
     */
    public void setPrintable(boolean printable) {
        this.printable = printable;
    }
}
