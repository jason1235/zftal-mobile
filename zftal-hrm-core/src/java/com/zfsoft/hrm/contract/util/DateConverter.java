package com.zfsoft.hrm.contract.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.zfsoft.util.date.DateTimeUtil;

public class DateConverter extends StrutsTypeConverter {

    @Override
    public String convertToString(Map arg0, Object arg1) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(arg1);
    }

    @Override
    public Object convertFromString(Map ctx, String[] value, Class arg2) {
        if (value[0] == null || value[0].trim().equals("")) {
            return null;
        }
        Date date = null;
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(value[0].indexOf("%20") != -1 || value[0].indexOf(" ") != -1){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                date = new Date(value[0]);
                date = df2.parse(DateTimeUtil.getFormatDate(date,"yyyy-MM-dd"));
//                date = df.parse(StringUtils.replace(value[0].trim(), "%20", " "));
            }else{
                date = df2.parse(value[0]);
            }
        } catch (ParseException pe) {
        }
        return date;
    }
} 