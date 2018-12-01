package com.zfsoft.workflow.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.zfsoft.util.base.StringUtil;

/**
 * 
 * 类描述：表达式工具类
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-5-6 上午10:01:20
 */
public class ScriptUtil {

	/**
	 * 
	 * 方法描述：执行字符串表达式
	 *
	 * @param: expression 逻辑运算表达式
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-6 上午10:10:41
	 */
	public final static boolean ExecuteStringScript(String expression) {
		boolean result = false;
		if(StringUtil.isNotEmpty(expression)){
			ScriptEngineManager manager = new ScriptEngineManager();
			ScriptEngine engine = manager.getEngineByName("js");		
			try {
				result = (Boolean) engine.eval(expression);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}		
		return result;
	}

	public static void main(String[] args) throws Exception {
		String str = "(a >= 0 && a <= 5)";
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		engine.put("a", 4);
		Object result = engine.eval(str);

		System.out.println("结果类型:" + result.getClass().getName() + ",计算结果:" + result);

	}
}