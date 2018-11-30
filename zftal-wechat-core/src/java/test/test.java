
package test;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.zfsoft.wechat.common.entity.button.Button;
import com.zfsoft.wechat.common.entity.button.ClickButton;
import com.zfsoft.wechat.common.entity.button.ComplexButton;
import com.zfsoft.wechat.common.entity.button.Menu;
import com.zfsoft.wechat.common.entity.button.ViewButton;
import com.zfsoft.wechat.common.entity.message.Article;
import com.zfsoft.wechat.common.entity.message.Music;
import com.zfsoft.wechat.common.entity.message.Video;
import com.zfsoft.wechat.common.utils.WeChatSendMessageUtil;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 Date date = new Date(1468824903000l);
		 SimpleDateFormat time=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); 
		 System.out.println(time.format(date));
		// 第三方用户唯一凭证֤
//		String appId = WeChatSystemHolder.getPropertiesValue("appId");
		// 第三方用户唯一凭证密钥
//		String appSecret = WeChatSystemHolder.getPropertiesValue("appSecret");
		
		// 获取access_token
//		AccessToken at = WeChatCommonUtil.getAccessToken(appId, appSecret);
//		AccessToken at = new AccessToken();
//		at.setAccessToken("lmSpwbXBeUGZYVb9lqKo0dbsWcY9ThgE8B0PHb9TGdAadDyFv0uzTfeHNYwaYOjs8MJG05gOK-RxA8ZOQ-9yanRz4bc6oGM8e1DN0pUcGwQ");
//		at.setExpiresIn(7200);
//		if (null != at) {
//			// 创建菜单
//			boolean result = WeChatMenuUtil.createMenu(getMenu(), at.getAccessToken());
//		}
		
		/*System.out.println(WeChatSendMessageUtil.makeTextCustomMessage("oooopppid", "现在\"测试\""));
		
		System.out.println(WeChatSendMessageUtil.makeImageCustomMessage("oooopppid", "mmmmmid"));
		
		System.out.println(WeChatSendMessageUtil.makeVoiceCustomMessage("oooopppid", "mmmmmid"));
		
		Music m = new Music();
		m.setDescription("现在\"测试\"");
		m.setTitle("现在\"测试头\"");
		m.setThumb_media_id("thumb_Media_Id");
		m.setMusicurl("adasdsad");
		m.setHqmusicurl("hqmusicurl");
		System.out.println(WeChatSendMessageUtil.makeMusicCustomMessage("oooopppid", m));
		
		List<Article> la = new ArrayList<Article>();
		Article a = new Article();
		a.setDescription("现在\"测试\"");
		a.setTitle("现在\"测试头\"");
		a.setPicurl("picurl");
		a.setUrl("url");
		la.add(a);
		a = new Article();
		a.setDescription("现在\"1测试\"");
		a.setTitle("现在\"1测试头\"");
		a.setPicurl("picurl1");
		a.setUrl("url1");
		la.add(a);
		System.out.println(WeChatSendMessageUtil.makeNewsCustomMessage("oooopppid",la));
		
		Video v = new Video();
		v.setMedia_id("media_id");
		v.setThumb_media_id("thumb_Media_Id");
		System.out.println(WeChatSendMessageUtil.makeVideoCustomMessage("oooopppid", v));*/
		
		/*String b = "xxxx\r\ndsdasd";
		String[] aaa = b.split("\r\n");
		System.out.println(aaa[0]);
		System.out.println(aaa[1]);*/
	}
	
	/**
	 * 创建菜单
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		ClickButton btn01 = new ClickButton();
		btn01.setName("测试");
		btn01.setType("click");
		btn01.setKey("11");

		ViewButton btn02 = new ViewButton();
		btn02.setName("进入人事系统");
		btn02.setType("click");
		btn02.setUrl("www.baidu.com");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("人事系统");
		mainBtn1.setSub_button(new Button[] { btn01, btn02});

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1 });

		return menu;
	}

}
