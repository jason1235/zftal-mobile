package base;

import com.zfsoft.util.encrypt.DBEncrypt;

public class DBEncryptTest {

	public static void main(String[] args) {
		DBEncrypt tool = new DBEncrypt();
		
		System.out.println( tool.dCode( "Kbs2u6NELkMD+i6RnR+aSRYguMAm9SijEILz1FLGIT4shFz/smsRY5+Y60A4RnMl".getBytes() ) );
		System.out.println( tool.dCode( "LUm00eLKmJmfmOtAOEZzJQ==".getBytes() ) );
		System.out.println( tool.eCode("hrm_v500") ) ;
	}
}
