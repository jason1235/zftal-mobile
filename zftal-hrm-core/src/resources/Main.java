import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 * properties管理工具的代码
 * @author ChenMinming
 * @date 2014-7-29
 * @version V1.0.0
 */
public  class Main {
	
	
	private static String encryptKey = "7EV/Zzutjzg=";
	public static SecretKey loadKey()
		throws Exception
	{
		BASE64Decoder d = new BASE64Decoder();
		byte b[] = d.decodeBuffer(encryptKey);
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		return  keyFactory.generateSecret(dks);
	}
	public static String eCode(String needEncrypt) throws Exception {
	byte result[] = null;
	try {
		Cipher enCipher = Cipher.getInstance("DES");
		javax.crypto.SecretKey key = loadKey();
		enCipher.init(1, key);
		result = enCipher.doFinal(needEncrypt.getBytes());
		BASE64Encoder b = new BASE64Encoder();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		b.encode(result, bos);
		result = bos.toByteArray();
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return new String(result);
	}
		
	public static void main(String[] args) throws Exception {
		if(args==null||args.length==0){
			args = new String[]{"properties.xls"};
		}
		File f = new File(args[0]);
		Workbook wb = Workbook.getWorkbook(f);
		Sheet[] sheets = wb.getSheets();
		for (Sheet sheet : sheets) {
			Properties p = new Properties();
			int rows = sheet.getRows();
			if(rows<=1)continue;
			for(int i = 1;i<rows;i++){
				Cell[] c = sheet.getRow(i);
				if(c==null||c.length<2)continue;
				Cell keyCel =c[0];
				Cell valueCel =c[1];
				String key = keyCel.getContents();
				String value = valueCel.getContents();
				if(c.length>=4&&c[3]!=null){
					if("y".equals(c[3].getContents())){
						value = eCode(value);
					}
				}
				p.put(key, value);
			}
			FileOutputStream fout = new FileOutputStream(sheet.getName()+".properties");
			p.store(fout, "");
		}
		
	}
}
