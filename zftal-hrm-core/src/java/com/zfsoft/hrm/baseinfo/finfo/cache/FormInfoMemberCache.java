package com.zfsoft.hrm.baseinfo.finfo.cache;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfo;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMemberProperty;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.FormInfoFactory;
import com.zfsoft.orcus.lang.ArrayUtil;
import com.zfsoft.orcus.lang.LocalReference;

/**
 * 信息维护组成成员缓存
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoMemberCache {
	
	private static final LocalReference REF = new LocalReference() {
		
		@Override
		protected Object createInstance() {
			
			return new FormInfoMemberCache().new Actor();
		}
	};
	
	private static Actor getActor() {
		return (Actor) REF.get();
	}

	/**
	 * 返回指定组的组成员描述信息
	 * @param name 组名
	 * @return
	 */
	public static FormInfoMember[] getInfoMembers( String name ) {
	
		return getActor().getInfoMembers( name );
	}
	
	/**
	 * 获取指定成员的描述信息
	 * @param name 成员所属组
	 * @param classId 成员所使用信息类ID
	 * @return
	 */
	public static FormInfoMember getInfoMember( String name, String classId ) {
		
		return getActor().getInfoMember( name, classId );
	}
	
	/**
	 * 注册指定的成员信息
	 * @param member 注册的成员信息
	 */
	public static void register( FormInfoMember member ) {
		
		getActor().register( member );
	}
	
	/**
	 * 注销指定的成员信息
	 * @param name 成员组名称
	 * @param classId 成员所使用信息类的全局ID
	 */
	public static void deregister( String name, String classId ) {
		
		getActor().deregister(name, classId);
	}
	
	/**
	 * 判断是否存在指定成员信息
	 * @param member 成员信息
	 * @return
	 */
	public static boolean contains( FormInfoMember member ) {
		
		return false;
	}
	
	private class Actor {
		
		private Map<String, FormInfoMember> _cache = new HashMap<String, FormInfoMember>();
		
		private static final String LINK = ";";
		
		private Actor() {
			init();
		}
		
		/**
		 * 初始化
		 */
		private void init() {
			List<FormInfoMember> members = new ArrayList<FormInfoMember>();
			
			for ( FormInfoMember member : members ) {
				
				register( member );
			}
		}
		
		/**
		 * 返回指定组的组成员描述信息
		 * @param name 组名称
		 * @return 组成员描述信息
		 */
		private FormInfoMember[] getInfoMembers( String name ) {
			FormInfoMember[] result = new FormInfoMember[0];

			if( name == null || "".equals( name ) ) {
				return result;
			}
			
			for ( String key : _cache.keySet() ) {
				String _name = key.split( LINK )[0];
				if( _name.equals( name ) ) {
					result = (FormInfoMember[]) ArrayUtil.addElement( result, _cache.get(key), FormInfoMember.class );
				}
			}
			
			result = (FormInfoMember[]) ArrayUtil.sort( result,  new Comparator<FormInfoMember>() {

				@Override
				public int compare( FormInfoMember member1, FormInfoMember member2 ) {
					int index1 = member1.getIndex();
					int index2 = member2.getIndex();
					
					return index1 - index2;
				}
			});
			
			return result;
		}
		
		/**
		 * 获取指定成员的描述信息
		 * @param name 成员所属组
		 * @param classId 成员所使用信息类ID
		 * @return 成员的描述信息
		 */
		private FormInfoMember getInfoMember( String name, String classId ) {
			if( name == null || "".equals(name) ||
					classId == null || "".equals( classId ) ) {
				
				return null;
			}
			
			return _cache.get( makeKey( name, classId ) );
		}
		
		/**
		 * 判断指定成员是否存在
		 * @param name 成员组组名
		 * @param classId 组成成员所使用信息类ID
		 * @return true:存在    /  false:不存在
		 */
		private boolean exist( String name, String classId ) {
			if( getInfoMember(name, classId) == null ) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * 注册指定的成员信息
		 * @param member 成员信息
		 */
		private void register( FormInfoMember member ) {
			if( member == null || member.getName() == null || member.getClassId() == null ) {
				return;
			}
			
			String key = makeKey( member.getName(), member.getName() );
			
			_cache.put( key, member );
		}
		
		/**
		 * 注销指定的成员信息
		 * @param name 成员所属组组名
		 * @param classId 成员所使用信息类ID
		 */
		private void deregister( String name, String classId ) {
			if( name == null || classId == null ) {
				return;
			}
			
			deregister( makeKey(name, classId) );
		}
		
		/**
		 * 注销指定的成员信息
		 * @param key 成员的联合组建
		 */
		private void deregister( String key ) {
			if( key == null ) {
				return;
			}
			
			_cache.remove( key );
		}
		
		/**
		 * 校验成员
		 */
		private void verifyMember() {
			FormInfo[] infos = FormInfoFactory.getInfos();
			
			for ( FormInfo info : infos ) {
				List<InfoClass> classes = InfoClassCache.getInfoClasses( info.getType() );
				
				//删除多余的信息类
				for( String key : _cache.keySet() ) {
					String classId = key.split( LINK )[1];
					
					if( InfoClassCache.contains( classId ) ) {
						continue;
					}
					
					deregister(key);
				}
				
				//补齐所有的信息类
				for ( InfoClass clazz : classes ) {
					if( exist( info.getName(), clazz.getGuid() ) ) {
						continue;
					}
					//TODO  开发中... 
//					FormInfoMember member = FormInfoUtil.transform( clazz );
//					member.setName( info.getName() );
					
//					register( member );
				}
			}
		}
		
		/**
		 * 生成主键Key
		 * @param name 成员组名称
		 * @param classId 成员所使用的信息类ID
		 * @return 联合主键
		 */
		private String makeKey( String name, String classId ) {
			return name + LINK + classId;
		}
		
	}
}
