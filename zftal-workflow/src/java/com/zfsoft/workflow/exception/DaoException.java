/**
 * created since Mar 12, 2009
 */
package com.zfsoft.workflow.exception;

/**
 * @author taobao
 * @version $Id: DaoException.java,v 0.1 Mar 12, 2009 1:57:31 PM taobao Exp $
 */
public class DaoException extends BaseException {
    private static final long serialVersionUID = -4131602370503139777L;
    /** 序列值 */

    private boolean printable=true;

    /**
     *
     */
    public DaoException() {
        super();
    }

    /**
     * @param message
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    public DaoException(Throwable cause,boolean printable){
        super(cause);
        this.printable=printable;
    }

    public DaoException(boolean printable) {
        super();
        this.printable=printable;
    }

    /**
     * @param message
     */
    public DaoException(String message,boolean printable) {
        super(message);
        this.printable=printable;
    }

    /**
     * @param message
     * @param cause
     */
    public DaoException(String message, Throwable cause,boolean printable) {
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
