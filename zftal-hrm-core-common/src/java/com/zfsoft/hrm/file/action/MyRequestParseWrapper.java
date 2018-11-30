package com.zfsoft.hrm.file.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;
import org.apache.struts2.dispatcher.multipart.MultiPartRequest;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;


/** 
 * 重写struts2的request封装类 
 *  
 * @author scott.Cgi 
 * 
 */  
public class MyRequestParseWrapper extends JakartaMultiPartRequest {  
	
	static final Logger LOG = LoggerFactory.getLogger(MultiPartRequest.class);
    /**
     * 建议：取消该类的覆盖。
     */
	
//	public void parse(HttpServletRequest servletRequest, String saveDir)  
//    throws IOException {  
//		System.out.print("");
//	}
    @Override  
    public void parse(HttpServletRequest servletRequest, String saveDir)  
            throws IOException {  
    	String path = servletRequest.getRequestURL().toString();
    	if(path.indexOf("/file/")>0){
    		System.out.print("");
    	}else{
    		try {
                processUpload(servletRequest, saveDir);
            } catch (FileUploadException e) {
                if (LOG.isWarnEnabled()) {
            	LOG.warn("Unable to parse request", e);
                }
                errors.add(e.getMessage());
            }
    	}
    }
    
    private void processUpload(HttpServletRequest request, String saveDir) throws FileUploadException, UnsupportedEncodingException {
        for (FileItem item : parseRequest(request, saveDir)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found item " + item.getFieldName());
            }
            if (item.isFormField()) {
                processNormalFormField(item, request.getCharacterEncoding());
            } else {
                processFileField(item);
            }
        }
    }

    private void processFileField(FileItem item) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Item is a file upload");
        }

        // Skip file uploads that don't have a file name - meaning that no file was selected.
        if (item.getName() == null || item.getName().trim().length() < 1) {
            LOG.debug("No file has been uploaded for the field: " + item.getFieldName());
            return;
        }

        List<FileItem> values;
        if (files.get(item.getFieldName()) != null) {
            values = files.get(item.getFieldName());
        } else {
            values = new ArrayList<FileItem>();
        }

        values.add(item);
        files.put(item.getFieldName(), values);
    }

    private void processNormalFormField(FileItem item, String charset) throws UnsupportedEncodingException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Item is a normal form field");
        }
        List<String> values;
        if (params.get(item.getFieldName()) != null) {
            values = params.get(item.getFieldName());
        } else {
            values = new ArrayList<String>();
        }

        // note: see http://jira.opensymphony.com/browse/WW-633
        // basically, in some cases the charset may be null, so
        // we're just going to try to "other" method (no idea if this
        // will work)
        if (charset != null) {
            values.add(item.getString(charset));
        } else {
            values.add(item.getString());
        }
        params.put(item.getFieldName(), values);
    }

    private List<FileItem> parseRequest(HttpServletRequest servletRequest, String saveDir) throws FileUploadException {
        DiskFileItemFactory fac = createDiskFileItemFactory(saveDir);
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setSizeMax(maxSize);
        return upload.parseRequest(createRequestContext(servletRequest));
    }

    private DiskFileItemFactory createDiskFileItemFactory(String saveDir) {
        DiskFileItemFactory fac = new DiskFileItemFactory();
        // Make sure that the data is written to file
        fac.setSizeThreshold(0);
        if (saveDir != null) {
            fac.setRepository(new File(saveDir));
        }
        return fac;
    }
    
    /**
     * Returns the canonical name of the given file.
     *
     * @param filename  the given file
     * @return the canonical name of the given file
     */
    private String getCanonicalName(String filename) {
        int forwardSlash = filename.lastIndexOf("/");
        int backwardSlash = filename.lastIndexOf("\\");
        if (forwardSlash != -1 && forwardSlash > backwardSlash) {
            filename = filename.substring(forwardSlash + 1, filename.length());
        } else if (backwardSlash != -1 && backwardSlash >= forwardSlash) {
            filename = filename.substring(backwardSlash + 1, filename.length());
        }

        return filename;
    }

    /**
     * Creates a RequestContext needed by Jakarta Commons Upload.
     *
     * @param req  the request.
     * @return a new request context.
     */
    private RequestContext createRequestContext(final HttpServletRequest req) {
        return new RequestContext() {
            public String getCharacterEncoding() {
                return req.getCharacterEncoding();
            }

            public String getContentType() {
                return req.getContentType();
            }

            public int getContentLength() {
                return req.getContentLength();
            }

            public InputStream getInputStream() throws IOException {
                InputStream in = req.getInputStream();
                if (in == null) {
                    throw new IOException("Missing content in the request");
                }
                return req.getInputStream();
            }
        };
    }

}  