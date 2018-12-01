package com.zfsoft.hrm.baseinfo.infoclass.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoClassQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoClassService;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoClassUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.orcus.lang.LocalReference;
import com.zfsoft.common.spring.SpringHolder;

/**
 * 信息类信息缓存
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoClassCache {
	
	private static final LocalReference REF = new LocalReference() {
		
		@Override
		protected Object createInstance() {
			return new InfoClassCache().new Actor();
		}
	};
	
	private static Actor getActor() {
		return (Actor) REF.get();
	}
	
	/**
	 * 返回默认类型的动态Bean描述信息类列表（不含只读动态Bean描述信息）
	 * @param type 类型
	 * @return
	 */
	public static List<InfoClass> getInfoClasses( ) {
		
		return getInfoClasses( IConstants.INFO_CATALOG_TYPE_DEFAULT );
	}
	
	/**
	 * 返回指定类型的动态Bean描述信息类列表（不含只读动态Bean描述信息）
	 * @param type 类型
	 * @return
	 */
	public static List<InfoClass> getInfoClasses( String type ) {
		
		return getActor().getInfoClasses( type );
	}
	
	/**
	 * 获取动态Bean描述信息
	 * <p>
	 * 返回的动态Bean描述信息为克隆副本，对其操作不会对注册表产生任何影响
	 * </p>
	 * @param classId 信息类ID
	 */
	public static InfoClass getInfoClass( String classId ) {
		
		return getActor().getInfoClass( classId );
	}
	/**
	 * 获取动态Bean描述信息
	 * <p>
	 * 返回的动态Bean描述信息为克隆副本，对其操作不会对注册表产生任何影响
	 * </p>
	 * @param classId 信息类ID
	 */
	public static InfoClass getInfoClassByName( String name ) {
		
		return getActor().getInfoClassByName( name );
	}
	
	/**
	 * 返回指定类型的只读动态Bean描述信息
	 * <p>
	 * 所有类型有且仅有一个只读的动态Bean描述信息
	 * </p>
	 * @param type 类型
	 * @return
	 */
	public static InfoClass getOverallInfoClass( String type ) {
		
		return getActor().getOverallInfoClass( type );
	}
	
	/**
	 * 返回默认类型的只读动态Bean描述信息
	 * <p>
	 * 所有类型有且仅有一个只读的动态Bean描述信息
	 * </p>
	 * @return
	 */
	public static InfoClass getOverallInfoClass() {
		
		return getOverallInfoClass( IConstants.INFO_CATALOG_TYPE_DEFAULT );
	}
	
	/**
	 * 注册动态Bean描述信息
	 * @param clazz 动态Bean描述信息
	 */
	public static void register( InfoClass clazz ) {

		getActor().register( clazz );
	}
	
	/**
	 * 刷新指定的动态Bean描述信息
	 * @param classId 动态Bean描述信息ID
	 */
	public static void refresh( String classId ) {
		
		getActor().refresh( classId );
	}
	
	/**
	 * 注销动态Bean描述信息
	 * @param clazz 动态Bean描述信息ID
	 */
	public static void deregister( String classId ) {
	
		getActor().deregister( classId );
	}
	
	/**
	 * 判断是否存在指定的动态Bean描述信息
	 * @param classId 动态BeanID
	 * @return 	<li>true	表示存在指定的动态Bean描述信息；</li>
	 * 			<li>false	表示不存在指定的动态Bean描述信息;</li>
	 */
	public static boolean contains( String classId ) {
		
		return getActor().contains( classId );
	}
	
	private class Actor {
		
		private Map<String, InfoClass> cache = new HashMap<String, InfoClass>();
		
		private Map<String, InfoClass> overall = new HashMap<String, InfoClass>();
		
		private IInfoClassService service;
		
		/**
		 * 构造函数
		 */
		private Actor() {
			service = (IInfoClassService) SpringHolder.getBean("baseInfoClassService");

			init();
		}
		
		/**
		 * 初始化
		 */
		private void init() {
			
			List<InfoClass> classes = service.getFullList( new InfoClassQuery() );
			
			for (InfoClass clazz : classes) {
				register( clazz );
			}
		}
		
		/**
		 * 返回指定类型的动态Bean描述信息类列表（不含只读动态Bean描述信息）
		 * @param type 类型
		 * @return 动态Bean描述信息类列表
		 */
		private List<InfoClass> getInfoClasses( String type ) {
			if( type == null || "".equals( type ) ) {
				return null;
			}
			
			List<InfoClass> list = new ArrayList<InfoClass>();
			
			for ( String classId : cache.keySet() ) {
				if( cache.get( classId ).getTypeInfo().isEditable() == false ) {
					continue;
				}
				
				if( type.equals( cache.get( classId ).getCatalog().getType() ) ) {
					list.add( cache.get( classId ).clone() );
				}
			}
			
			Collections.sort(list, new Comparator<InfoClass>(){
				public int compare( InfoClass clazz1, InfoClass clazz2 ) {
					//信息类排序时，根据信息类目录的显示顺序进行排序
					//如果2个信息类在同一目录下则根据信息类是显示顺序进行排序
					int index1 = clazz1.getCatalog().getIndex();
					int index2 = clazz2.getCatalog().getIndex();
					
					if( index1 == index2 ) {
						index1 = clazz1.getIndex();
						index2 = clazz2.getIndex();
					}
					
					return index1 - index2;
				}
			});
			
			return list;
		}
		
		/**
		 * 返回指定的动态Bean描述信息
		 * @param classId 动态Bean描述信息ID
		 * @return
		 */
		private InfoClass getInfoClass( String classId ) {
			
			if( classId == null || "".equals( classId ) ) {
				return null;
			}
			
			InfoClass clazz = cache.get( classId );
			
			if( clazz == null ) {
				clazz = service.getInfoClass( classId );
				if(clazz == null){
					return null;
				}
				cache.put( classId, clazz );
			}
			
			return clazz.clone();
		}
		/**
		 * 返回指定的动态Bean描述信息
		 * @param classId 动态Bean描述信息ID
		 * @return
		 */
		private InfoClass getInfoClassByName( String name ) {
			
			if( name == null || "".equals( name ) ) {
				return null;
			}
			
			InfoClass clazz=null;
			
			for (InfoClass infoClass : cache.values()) {
				if(infoClass.getIdentityName().equalsIgnoreCase(name)){
					clazz=infoClass;
				}
			}
			if(clazz==null){
				return null;
			}
			return clazz.clone();
		}
		
		/**
		 * 返回指定类型的只读动态Bean描述信息
		 * <p>
		 * 所有类型有且仅有一个只读的动态Bean描述信息
		 * </p>
		 * @param type 类型
		 * @return
		 */
		private InfoClass getOverallInfoClass( String type ) {
			if( type == null || "".equals( type ) ) {
				return null;
			}
			
			for ( String classId : overall.keySet() ) {
				if( type.equals( overall.get( classId ).getCatalog().getType() ) ) {
					return overall.get( classId ).clone();
				}
			}
			
			return null;
		}
		
		/**
		 * 注册动态Bean描述信息
		 * @param clazz 动态Bean描述信息
		 */
		private void register( InfoClass clazz ) {
			if( clazz == null ) {
				return;
			}
			
			//对动态Bean描述信息进行注册
			cache.put( clazz.getGuid(), clazz );
			
			//如果该动态Bean为只读，则对另外再进行注册，方便以后获取只读动态Bean描述信息
			if( clazz.getGuid().equalsIgnoreCase(IConstants.INFO_CATALOG_PERSON_SUMMARY )) {
				overall.put( clazz.getGuid(), clazz );
			}
		}
		
		/**
		 * 刷新指定的动态Bean描述信息
		 * @param classId 动态Bean描述信息ID
		 */
		private void refresh( String classId ) {
			
			InfoClass clazz = service.getFullInfoClass( classId );
			
			//注销
			deregister(classId);
			
			//注册
			register( clazz );
			
		}
		
		/**
		 * 注销指定的动态Bean描述信息
		 * @param classId 动态Bean描述信息ID
		 */
		private void deregister( String classId ) {
			if( classId == null || "".equals( classId ) ) {
				return;
			}
			
			cache.remove( classId );
			overall.remove( classId );
		}
		
		/**
		 * 判断是否存在指定的动态Bean描述信息
		 * @param classId 动态Bean描述信息ID
		 * @return 	<li>true	表示存在指定的动态Bean描述信息；</li>
		 * 			<li>false	表示不存在指定的动态Bean描述信息;</li>
		 */
		private boolean contains( String classId ) {
			if( classId == null || "".equals( classId ) ) {
				return false;
			}
			
			return cache.containsKey( classId );
		}

		@Override
		protected void finalize() throws Throwable {
			cache.clear();
			overall.clear();
			
			super.finalize();
		}
	}
}
