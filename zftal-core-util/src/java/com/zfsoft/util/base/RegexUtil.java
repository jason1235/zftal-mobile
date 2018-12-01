package com.zfsoft.util.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则匹配工具
 * @author zhangqy
 * 
 */
public class RegexUtil {
    public static long parseRecordsNumInHtml(String content, String regex)
        throws Exception {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        String recordContent = null;

        if (matcher.find()) {
            recordContent = matcher.group();
        } else {
            throw new Exception("找不到匹配结果: content:" + content + " regex:" +
                regex);
        }

        pattern = Pattern.compile("[\\d]+"); //提取数字
        matcher = pattern.matcher(recordContent);

        StringBuffer recordBuffer = new StringBuffer();

        while (matcher.find()) {
            recordBuffer.append(matcher.group());
        }

        if (recordBuffer.toString().length() == 0) {
            throw new Exception("找不到匹配结果: content:" + content + " regex:" +
                regex);
        }

        return Long.parseLong(recordBuffer.toString());
    }
    
    public static boolean isContentMatche(String content,String regex) {
    	Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
