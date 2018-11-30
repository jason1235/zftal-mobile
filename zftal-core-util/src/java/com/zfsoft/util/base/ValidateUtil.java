package com.zfsoft.util.base;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


/**
 * 特殊字符检查的工具类
 * @author zhangqy
 * @version v1.0.0
 */
public class ValidateUtil {
    /*
     * 定义需要检查的特殊字符正则表达式，比如URL
     */

    //private final static String URL_VALIDATE = "\\A(http://|https://)(((((\\w+(-*\\w*)+)\\.)+((com)|(net)|(edu)|(gov)|(org)|(biz)|(aero)|(coop)|(info)|(name)|(pro)|(museum)|(tv))(\\.([a-z]{2}))?)|((\\w+(-*\\w*)+)\\.(cn)$)(\\.([a-z]{2}))?)|((\\w+(-*\\w*)+)\\.(cn)))((/|\\?)\\S*)*)+$";
    private final static String URL_VALIDATE =
        "\\A(http://|https://)(((((\\w+(-*\\w*)+)\\.)+(" +
        ConfigurationResourceUtil.getValue("URL_CHECK_RULE") +
        ")(\\.([a-z]{2}))?)|((\\w+(-*\\w*)+)\\.(cn)))((/|\\?)\\S*)*)+$";
    private final static String WORD_INVALIDATE = "<|>|\\[|\\]|\\{|\\}|『|』|※|○|●|◎|§|△|▲|☆|★|◇|◆|□|▼|㊣|﹋|⊙|〒|ㄅ|ㄆ|ㄇ|ㄈ|ㄉ|ㄊ|ㄋ|ㄌ|ㄍ|ㄎ|ㄏ|ㄐ|ㄑ|ㄒ|ㄓ|ㄔ|ㄕ|ㄖ|ㄗ|ㄘ|ㄙ|ㄚ|ㄛ|ㄜ|ㄝ|ㄞ|ㄟ|ㄢ|ㄣ|ㄤ|ㄥ|ㄦ|ㄧ|ㄨ|ㄩ|■|▄|▆|\\*|@|#|&|\\^|\\\\";
    private final static String URL_INVALIDATE = "<|>|\\[|\\]|\\{|\\}|『|』|※|○|●|◎|§|△|▲|☆|★|◇|◆|□|▼|㊣|﹋|⊙|〒|ㄅ|ㄆ|ㄇ|ㄈ|ㄉ|ㄊ|ㄋ|ㄌ|ㄍ|ㄎ|ㄏ|ㄐ|ㄑ|ㄒ|ㄓ|ㄔ|ㄕ|ㄖ|ㄗ|ㄘ|ㄙ|ㄚ|ㄛ|ㄜ|ㄝ|ㄞ|ㄟ|ㄢ|ㄣ|ㄤ|ㄥ|ㄦ|ㄧ|ㄨ|ㄩ|■|▄|▆|\\*|@|\\^|\\\\";
    private final static String EMAIL_INVALIDATE = "<|>|\\[|\\]|\\{|\\}|『|』|※|○|●|◎|§|△|▲|☆|★|◇|◆|□|▼|㊣|﹋|⊙|〒|ㄅ|ㄆ|ㄇ|ㄈ|ㄉ|ㄊ|ㄋ|ㄌ|ㄍ|ㄎ|ㄏ|ㄐ|ㄑ|ㄒ|ㄓ|ㄔ|ㄕ|ㄖ|ㄗ|ㄘ|ㄙ|ㄚ|ㄛ|ㄜ|ㄝ|ㄞ|ㄟ|ㄢ|ㄣ|ㄤ|ㄥ|ㄦ|ㄧ|ㄨ|ㄩ|■|▄|▆|\\*|#|&|\\^|\\\\";
    /**
	 * 
	 * 原有功能是在@符号之前至少有2个字符，为了配合代理自助系统统一验证，现在修改为@符号之前至少有1个字符
	 * old: private final static String EMAIL_VALIDATE = "^(\\w+[-|\\.]?)+\\w@(\\w+(-\\w+)?\\.)+[a-zA-Z]{2,}$";
	 * */
	private final static String EMAIL_VALIDATE = "^(\\w+[-|\\.]?)+@(\\w+(-\\w+)?\\.)+[a-zA-Z]{2,}$";
	
    private final static String POST_NUMBER_VALIDATE = "\\d{6}";
    private final static String PWD_VALIDATE = "(?=\\w*\\d)(?=\\w*[a-zA-Z])\\w{6,12}";
    private final static String DECIMAL_VALIDATE = "\\A(\\d*)\\.?\\d{1,2}";

    //private final static String DOMAIN_VALIDATE = "((\\w+(-*\\w*)+)\\.((com)|(net)|(edu)|(gov)|(org)|(biz)|(aero)|(coop)|(info)|(name)|(pro)|(museum)|(tv))(\\.([a-z]{2}))?)|((\\w+(-*\\w*)+)\\.(cn)$)";

    //|\\u0x00|\\u0x01|\\u0x03|\\u0x04|\\u0x05|\\u0x06|\\u0x07|\\u0x08|\\u0x0B|\\u0x0C|\\u0x0E|\\u0x0F|\\u0x10|\\u0x11|\\u0x12|\\u0x13|\\u0x14|\\u0x15|\\u0x16|\\u0x17|\\u0x18|\\u0x19|\\u0x1A|\\u0x1B|\\u0x1C|\\u0x1D|\\u0x1E|\\u0x1F
    public static boolean isValidReg(String strReg) {
        return !hasCrossScriptRisk(strReg, WORD_INVALIDATE);
    }

    /**
     * 判断URL是否合法
     *   <pre>
     *   <li>特殊字符列表请参照{@link #WORD_INVALIDATE}
     *   <li>URL合法正则表达式列表请参照{@link #URL_VALIDATE}
     *   </pre>
     * @param strReg 要检查的URL
     * @return boolean 如果URL不合法，返回false；否则返回true
     */
    public static boolean isValidURL(String strReg) {
        if (!hasCrossScriptRisk(strReg, URL_INVALIDATE)) {
            return hasCrossScriptRisk(strReg, URL_VALIDATE);
        }

        return false;
    }

    /**
     * 判断EMAIL是否合法
     *   <pre>
     *     首先校验EMAIL中是否包含特殊字符，然后检查EMAIL是否格式正确。
     *     <li>特殊字符列表请参照{@link #EMAIL_INVALIDATE}</li>
     *     <li>EMAIL合法证则表达式列表请参照{@link #EMAIL_VALIDATE}</li>
     *   </pre>
     * @param strReg EMAIL字符数据
     * @return boolean 如果EMAIL不合法，返回false；否则返回true
     */
    public static boolean isValidMail(String strReg) {
        if (!hasCrossScriptRisk(strReg, EMAIL_INVALIDATE)) {
            strReg = StringUtils.trim(strReg);

            Pattern p = Pattern.compile(EMAIL_VALIDATE, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(strReg);

            return m.matches();
        }

        return false;
    }

    /**
     * 判断邮编号码是否合法
     *   <pre>
     *     检查邮编号码是否为数字型，并且长度为6。
     *     <li>特殊字符列表请参照{@link #WORD_INVALIDATE}</li>
     *     <li>邮编号码合法正则表达式列表情参照{@link #POST_NUMBER_VALIDATE}</li>
     *   </pre>
     * @param strReg 邮编号码
     * @return boolean 如果邮编不合法，返回false；否则返回true
     */
    public static boolean isValidPost(String strReg) {
        if (isValidReg(strReg)) {
            strReg = StringUtils.trim(strReg);

            Pattern p = Pattern.compile(POST_NUMBER_VALIDATE,
                    Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(strReg);

            return m.matches();
        }

        return false;
    }

    /**
     * 判断密码是否合法
     *  <pre>
     *    检查密码是否符合规则：必须以A-Z或a-z的字符，并且只能包括字符和数字，长度为6到12
     *    <li>特殊字符列表请参照{@link #WORD_INVALIDATE}</li>
     *    <li>密码合法证则表达式列表情参照{@link #PWD_VALIDATE}</li>
     *  </pre>
     * @param strReg 要校验的密码
     * @return boolean 如果密码不合法，返回false；否则返回true
     */
    public static boolean isValidPwd(String strReg) {
        if (isValidReg(strReg)) {
            Pattern p = Pattern.compile(PWD_VALIDATE, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(strReg);

            return m.matches();
        }

        return false;
    }

    /**
     * 判断货币金额是否合法
     *   <pre>
     *     检查货币金额是否符合规则：必须为数字类型，且小数位数不能超过2位。
     *     <li>特殊字符列表请参照{@link #WORD_INVALIDATE}</li>
     *     <li>货币金额合法正则表达式列表请参照{@link #DECIMAL_VALIDATE}</li>
     *   </pre>
     * @param strReg 要检查的货币金额
     * @return boolean 如果货币金额非法，返回false；否则返回true
     */
    public static boolean isValidDecimal(String strReg) {
        if (isValidReg(strReg)) {
            strReg = StringUtils.trim(strReg);

            Pattern p = Pattern.compile(DECIMAL_VALIDATE,
                    Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(strReg);

            return m.matches();
        }

        return false;
    }

    /**
     * 从字符数据中将双字节以上的字符过滤掉
     *   <pre>
     *   如数据为"I Love 中国, My 祖国."，则过滤后的结果为"I Love, My ."
     *   </pre>
     * @param strReg 要过滤得数据
     * @return String 返回已经过滤双字节后的数据
     */
    public static String trimMultiBytes(String strReg) {
        if (StringUtils.isNotBlank(strReg)) {
            //包含双字节以上的字符
            if (strReg.length() < strReg.getBytes().length) {
                String str = null;

                for (int i = 0; i < strReg.length(); i++) {
                    str = StringUtils.substring(strReg, i, i + 1);

                    if (str.length() < str.getBytes().length) {
                        strReg = StringUtils.substring(strReg, 0, i) +
                            StringUtils.substring(strReg, i + 1, strReg.length());
                        i--;
                    }
                }
            }
        }

        return strReg;
    }

    /**
     * 将数据特殊符号内的数据用其他数据代替
     * <pre>
     *   如可以将<与>之间的所有字符替换为" "，其中也包括<与>
     * </pre>
     * @param strReg 要转化的字符数据
     * @param str1 特殊符号的正则表达式，如<[^\\w]*|[\\w]*>，是指将<与>之间的数据替换，包括<与>
     * @param str2 替换的字符
     * @return String 返回替换后的字符数据
     */
    public static String replaceAll(String strReg, String str1, String str2) {
        if (StringUtils.isNotBlank(strReg) && StringUtils.isNotEmpty(str1)) {
            Pattern p = Pattern.compile(str1);
            Matcher m = p.matcher(strReg);

            return m.replaceAll(str2);
        } else {
            return strReg;
        }
    }

    /**
     * 将字符数据中的所有双引号转换为单引号
     * <pre>
     *   如数据为<code>You said, "I will go today."</code>会被转化为<code>You said, 'I will go today.'</code>
     * </pre>
     * @param strReg 要转化的字符数据
     * @return String 返回转化后的数据
     */
    public static String replaceQuotation(String strReg) {
        if (StringUtils.isNotBlank(strReg)) {
            if (StringUtils.contains(strReg, "\"")) {
                return StringUtils.replace(strReg, "\"", "'");
            } else {
                return strReg;
            }
        }

        return strReg;
    }

    /**
     * 从URL中解析出一级域名
     * <pre>
     *   域名解析规则如下：
     *   <li>如果URL中包含"http://"，则去掉<li>
     *   <li>如果URL中包含"/"，则去掉"/"后的所有字符</li>
     *   <li>如果URL中包含"?"，则去掉"?"后的所有字符</li>
     *   <li>根据{@link #DOMAIN_VALIDATE}所定义的规则解析</li>
     *   <li>不包含{@link #WORD_INVALIDATE}所定义的特殊字符</li>
     *   如URL为http://p4p.yahoo.net/index.htm，则解析后的域名为yahoo.net
     * </pre>
     * @param strReg 要解析的URL，可以包含http://，/，？等字符
     * @return String 得到解析后的一级域名，如果URL格式不合法，则返回NULL
     */
    public static String getDomainFromUrl(String strReg) {
//        if (StringUtils.isNotBlank(strReg)) {
//            strReg = StringUtils.trim(strReg);
//            strReg = StringUtils.toLowerCase(strReg);
//
//            if (StringUtils.contains(strReg, "//")) {
//                //strReg = StringUtils.substringAfterLast(strReg, "//");
//                strReg = StringUtils.substringAfter(strReg, "//");
//            }
//
//            if (StringUtils.contains(strReg, "/")) {
//                strReg = StringUtils.substringBefore(strReg, "/");
//            }
//
//            if (StringUtils.contains(strReg, "?")) {
//                strReg = StringUtils.substringBefore(strReg, "?");
//            }
//
//            if (StringUtils.contains(strReg, "#")) {
//                strReg = StringUtils.substringBefore(strReg, "#");
//            }
//
//            Pattern p = Pattern.compile(DOMAIN_VALIDATE);
//            Matcher m = p.matcher(strReg);
//
//            String strAll = m.replaceAll("");
//
//            if (StringUtils.isEmpty(strAll)) {
//                return strReg;
//            } else {
//                return StringUtils.substring(strReg, strAll.length(),
//                    strReg.length());
//            }
//
//            //return StringUtils.substringAfterLast(strReg, strAll);
//        } else {
//            return strReg;
//        }
    	
    	String domain = getTopDomain(strReg);
    	domain = domain == null ? "" : domain;
    	return domain;
    }

    /**
     * 检查输入的数据中是否有特殊字符
     * @param qString 要检查的数据
     * @param regx 特殊字符正则表达式
     * @return boolean 如果包含正则表达式<code>regx</code>中定义的特殊字符，返回true；
     *                 否则返回false
     */
    public static boolean hasCrossScriptRisk(String qString, String regx) {
        if (StringUtils.isNotBlank(qString)) {
            qString = StringUtils.trim(qString);

            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);

            return m.matches();
        }

        return false;
    }
    
    
    /**
     * 检查输入的数据中是否符合正则表达式
     * @param qString 要检查的数据
     * @param regx 特殊字符正则表达式
     * @return boolean 如果包含正则表达式<code>regx</code>中定义的特殊字符，返回true；
     *                 否则返回false
     */
    public static boolean matchStringReg(String qString, String regx) {
        if (StringUtils.isNotBlank(qString)) {
            qString = StringUtils.trim(qString);

            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);

            return m.matches();
        }

        return false;
    }

    /*        public static boolean hasCrossScriptRisk(String qString, String regx) {
                    boolean hasRisk = false;
                    if (StringUtils.isNotBlank(qString)) {
                            try {
                                    org.apache.regexp.RE re = new org.apache.regexp.RE(regx, 1);
    
                                    int iStart = 0;
                                    int iLength = qString.length();
                                    String tmpValue = null;
    
                                    do {
                                            if (iLength - iStart >= 100)
                                                    tmpValue = StringUtils.substring(qString, iStart, iStart+100);
                                            else
                                                    tmpValue = qString;
    
                                            iStart += 100;
    
                                            if (re.match(tmpValue))
                                            {
                                                    hasRisk = true;
                                                    iStart = iLength;
                                            }
                                    } while (iStart < iLength);
                            }
                            catch(Exception e)
                            {
                                    hasRisk = false;
                            }
                    }
                    return hasRisk;
            }
    */
    
    public static String getTopDomain(String url) {
    	if(url == null) {
    		return null;
    	}
    	StringBuilder tmp = new StringBuilder(url);
		String[] domains = new String[]{"com", "net", "edu", "gov", "org", "asia", "mobi", "biz", "cc", "tv", "name", "info", "aero", "coop", "pro", "museum"}; //, "中国", "公司"
		
		if(tmp.indexOf("://") != -1) {
			tmp.delete(0, tmp.indexOf("://") + 3);
		}

		if(tmp.indexOf("/") != -1) {
			tmp.delete(tmp.indexOf("/"), tmp.length());
		}

		if(tmp.indexOf(":") != -1) {
			tmp.delete(tmp.indexOf(":"), tmp.length());
		}

		if(tmp.indexOf("..") != -1) {
			return null;
		}

//		Pattern p = Pattern.compile("(\\w*|\\.*)+");
//		Matcher m = p.matcher(tmp);
//		boolean b = m.matches();
//		if(b) {
			String testString = tmp.toString();
			String[] words = testString.split("\\.");
			if(words.length > 1) {
				boolean isInDomains = false;
				for (String d : domains) {
					if(d.equalsIgnoreCase(words[words.length-2])) {
						isInDomains = true;
						break;
					}
				}
				if(isInDomains) {
					if(words.length > 2) {						
						return tmp.substring(tmp.indexOf(words[words.length-3]));
					}
				} else {
					return tmp.substring(tmp.lastIndexOf(words[words.length-2]));
				}
			}
//		}
		return null;
    }
    
    public static void main(String[] args) {
        /*
        System.out.println("Check : "+isValidReg("-fdsk.fj中?国:d=s1322354"));
        System.out.println("Check URL : "+isValidURL("http://adtaobao.allyes.com/main/adfclick?db=adtaobao&bid=586,583,5&cid=24648,8,1&sid=32365&show=ignore&url=http://search1.taobao.com/browse/0/t-g,zsumvpos6th6y----------------40-list-commend-0-all-0.htm"));
        System.out.println("Replace All : "+replaceAll("1234<,.中ugo>xx>5<6", "<+[^>]*[^<]*>+|<", " "));
        System.out.println("Replace Quotation : "+replaceQuotation("Hello \"MM\", Peter's answer."));
        System.out.println("Check Email : "+isValidMail("l-u@234.com"));
        System.out.println("Check Post : "+isValidPost("123456"));
        System.out.println("Check PWD : "+isValidPwd("4tertr"));
        System.out.println("Check Decimal : "+isValidDecimal("49x9"));
        */
        System.out.println("DOMAIN :" + getTopDomain("https://s-ohu.com.cn:8080"));
    }
}
