/**
 * created since Mar 12, 2009
 */
package com.zfsoft.workflow.exception;

/**
 * @author taobao
 * @version $Id: BaseException.java,v 0.1 Mar 12, 2009 1:51:47 PM taobao Exp $
 */
public class BaseException extends RuntimeException {
    /** 序列值 */
    private static final long serialVersionUID = 8550032191827568558L;

    private boolean printable=true;

    /**
     *
     */
    public BaseException() {
        super();
    }

    /**
     * @param message
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(Throwable cause,boolean printable){
        super(cause);
        this.printable=printable;
    }

    public BaseException(boolean printable) {
        super();
        this.printable=printable;
    }

    /**
     * @param message
     */
    public BaseException(String message,boolean printable) {
        super(message);
        this.printable=printable;
    }

    /**
     * @param message
     * @param cause
     */
    public BaseException(String message, Throwable cause,boolean printable) {
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
