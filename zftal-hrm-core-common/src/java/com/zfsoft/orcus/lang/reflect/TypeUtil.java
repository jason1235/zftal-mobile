package com.zfsoft.orcus.lang.reflect;

import java.util.HashMap;

import com.zfsoft.orcus.lang.ArrayUtil;
import com.zfsoft.orcus.lang.ClassUtil;
import com.zfsoft.orcus.lang.LocalReference;
import com.zfsoft.orcus.lang.StringUtil;

/**
 * 类型的工具类，提供获取、解析、注册、注销类型描述的功能。
 * 
 * @author <a href="mailto:linjian.lansle@gmail.com">林建</a>
 * @since 2006-10-21
 */
@SuppressWarnings("unchecked")
public class TypeUtil
{
    /**
     * 获取类型描述
     * 
     * <p>
     * 如果类型描述已注册，则返回注册的类型描述；
     * 如果类型描述未注册且类型为非动态类型，则解析及自动注册类型描述，并返回该类型描述；
     * 如果类型描述未注册且类型为动态类型，则抛出{@link java.lang.ClassNotFoundException}
     * </p>
     * 
     * @param className 类名
     * @return 类型描述
     * @throws com.lansle.lang.AssertionError 如果clazzName为空字符串
     * @throws java.lang.ClassNotFoundException 如果className指定的类不存在
     */
    public static Type getType( String className ) throws ClassNotFoundException
    {
        return getActor().getType( className );
    }
    
    /**
     * 注册类型描述
     * 
     * @param type 类型描述
     * @throws com.lansle.lang.AssertionError 如果type为null
     * @throws TypeAlreardyExistsException (runtime)如果type已存在
     */
    public static void register( Type type )
    {   
        getActor().register( type );
    }    
    
    /**
     * 注销类型描述
     * 
     * @param className 类名
     * @return 被注销的类型描述，null表示指定的类型描述不存在
     * @throws com.lansle.lang.AssertionError 如果clazzName为空字符串
     */
    public static Type deregister( String className )
    {   
        return getActor().deregister( className );
    }       
    
    /**
     * 判断指定的类型描述是否存在
     * 
     * @param className 类名
     * @return true表示指定的类型描述存在，false表示指定的类型描述不存在。
     * @throws com.lansle.lang.AssertionError 如果clazzName为空字符串
     */
    public static boolean contains( String className )
    {   
        return getActor().contains( className );
    }     
    
    /**
     * 获取指定对象的类型描述
     * 
     * @param obj 类名
     * @return 类型描述, 如果obj==null则返回null
     * @throws java.lang.ClassNotFoundException 如果className指定的类不存在
     */
    public static Type typeFor( Object obj ) throws ClassNotFoundException
    {
        if( obj == null )
        {
            return null;
        }
        
        //判断动态类型
        
        Class clazz = obj.getClass();
        
        if(  clazz.isArray() )
        {            
            int[] lens = ArrayUtil.getLengths( obj );
            int   size = ArrayUtil.getSize( lens );

            if( size == 0 )
            {
                return getType( clazz.getName() );
            }
            
            
            int[] idxs = ArrayUtil.getIndexes( 0, lens );
            Object o = ArrayUtil.getElement( obj, idxs );
            
            if( o == null )
            {
                return getType( clazz.getName() );
            }
            
            Type et = typeFor( o );
            return new TypeUtil().new TypeImpl( et, ArrayUtil.getDimension( clazz ) );            
        }

        if( obj instanceof SelfDescribed )
        {
            return ((SelfDescribed)obj).introspect().type();
        }
        
        return getType( clazz.getName() );
    }
    
    /**
     * 判断目标对象是否和源类型匹配(一致或是源类型的子类)
     * 
     * @param destObject 目标对象
     * @param sourceType 源类型
     * @return true表示匹配，false表示不匹配
     */
	public static boolean isAssignable( Object destObject, Type sourceType )
    {
        if( destObject == null || sourceType == null )
        {
            return false;
        }
        
        //判断动态类型
        if( sourceType.isDynamic() )
        {
            if( sourceType.getDimension() == 0 )
            {
                try
                {
                    return ( (SelfDescribed) destObject ).introspect().type()
                    .getQulifiedName().equals( sourceType.getQulifiedName() );
                }
                catch( Throwable t )
                {
                    return false;
                }                
            }
            
            if( sourceType.getDimension() != ArrayUtil.getDimension( destObject.getClass() ) )
            {
                return false;
            }
            
            int[] lens = ArrayUtil.getLengths( destObject );
            if( ArrayUtil.getSize( lens ) == 0 )
            {
                return true;
            }
            int[] idxs = ArrayUtil.getIndexes( 0, lens );
            Object o = ArrayUtil.getElement( destObject, idxs );
            
            try
            {
                return ( (SelfDescribed) o ).introspect().type()
                .getQulifiedName().equals( sourceType.getElement().getQulifiedName() );
            }
            catch( Throwable t )
            {
                return false;
            }            
        }
        
        
        //判断非动态类型
        try
        {
            return SingletonClassLoader.getInstance()
            		.loadClass( sourceType.getQulifiedName() )
            		.isAssignableFrom( destObject.getClass() );
        }
        catch( ClassNotFoundException e )
        {
            return false;
        }
    }
    
    
    /**
     * 判断目标对象是否和源类型一致
     * 
     * @param destObject 目标对象
     * @param sourceType 源类型
     * @return true表示一致，false表示不一致
     */
    public static boolean instanceOf( Object destObject, Type sourceType )
    {
        if( destObject == null || sourceType == null )
        {
            return false;
        }
        
        //判断动态类型
        if( sourceType.isDynamic() )
        {
            if( sourceType.getDimension() == 0 )
            {
                try
                {
                    return ( (SelfDescribed) destObject ).introspect().type()
                    .getQulifiedName().equals( sourceType.getQulifiedName() );
                }
                catch( Throwable t )
                {
                    return false;
                }                
            }
            
            if( sourceType.getDimension() != ArrayUtil.getDimension( destObject.getClass() ) )
            {
                return false;
            }
            
            int[] lens = ArrayUtil.getLengths( destObject );
            if( ArrayUtil.getSize( lens ) == 0 )
            {
                return true;
            }
            int[] idxs = ArrayUtil.getIndexes( 0, lens );
            Object o = ArrayUtil.getElement( destObject, idxs );
            
            try
            {
                return ( (SelfDescribed) o ).introspect().type()
                .getQulifiedName().equals( sourceType.getElement().getQulifiedName() );
            }
            catch( Throwable t )
            {
                return false;
            }            
        }
        
        
        //判断非动态类型
        try
        {
            return SingletonClassLoader.getInstance().loadClass(
                    sourceType.getQulifiedName() ).equals(
                    destObject.getClass() );
        }
        catch( ClassNotFoundException e )
        {
            return false;
        }
    }
    
    
    private static final LocalReference REF = new LocalReference() {
        protected Object createInstance() 
        {
            return new TypeUtil().new Actor();
        }
    };
    
    private static Actor getActor()
    {
        return (Actor)REF.get();
    }
    
    private class Actor
    {
        private HashMap<String, Type> _cache = new HashMap<String, Type>();
        
        private Type getType( String className ) throws ClassNotFoundException
        {
            if( className == null || "".equals(className) ) {
            	throw new RuntimeException("parameter [className] is required; it cannot be null.");
            }
            
            Type type = (Type)_cache.get( className );
            
            if( type != null )
            {
                return type;
            }
            
            if( isArray( className ) )
            {
                Type et = getType(  getElementClassName( className ) );
                return new TypeImpl( et, getArrayDimension( className ) );
            }
            
            Class clazz = SingletonClassLoader.getInstance().loadClass( className );
            if( SelfDescribed.class.isAssignableFrom( clazz ) )
            {
                throw new ClassNotFoundException( className );
            }
            
            type = new TypeImpl( clazz );
            _cache.put( type.getQulifiedName(), type );          
            return type;
        }
        
        private void register( Type type )
        {
        	if( type == null ) {
            	throw new RuntimeException("parameter [type] is required; it cannot be null.");
            }
            
            if( _cache.containsKey( type.getQulifiedName() ) )
            {
                throw new TypeAlreardyExistsException( type.getQulifiedName() );
            }
            
            _cache.put( type.getQulifiedName(), type );
        }
        
        private Type deregister( String className )
        {
        	if( className == null || "".equals(className) ) {
            	throw new RuntimeException("parameter [className] is required; it cannot be null.");
            }
            
            return (Type)_cache.remove( className );
        }
        
        private boolean contains( String className )
        {
        	if( className == null || "".equals(className) ) {
            	throw new RuntimeException("parameter [className] is required; it cannot be null.");
            }
            
            return _cache.containsKey( className );
        }
        
        private boolean isArray( String className )
        {
            if( className.indexOf( "[]" ) > 0 )
            {
                return true;
            }
            
            try
            {
                return Class.forName( className ).isArray();
            }
            catch( ClassNotFoundException e )
            {
                return false;
            }
        }        
        
        private String getElementClassName( String className ) throws ClassNotFoundException
        {
            if( className.indexOf( "[]" ) > 0 )
            {
                return StringUtil.replace( className, "[]", "" );
            }
            
            return ArrayUtil.getBaseComponentType( SingletonClassLoader.getInstance().loadClass( className ) ).getName();
        } 
        
        private int getArrayDimension( String className ) throws ClassNotFoundException
        {
            if( className.indexOf( "[]" ) > 0 )
            {
                return StringUtil.appear( className, "[]" );
            }
            
            return ArrayUtil.getDimension( SingletonClassLoader.getInstance().loadClass( className ) );
        } 
        
        /* 
         * @see java.lang.Object#finalize()
         */
        public void finalize() throws Throwable
        {
            _cache.clear();
            
            super.finalize();
        }
    }
    
    
    
    protected class TypeImpl implements Type
    {
		private static final long serialVersionUID = -3902505525470929302L;
		
		private boolean         primitive;
		
		private int             dimension;
		
		private String          name;
		
		private String          qulifiedName;
		
		private String          qulifiedPackageName;
		
		private Type            element;
        
        private TypeImpl( Class clazz )
        {
            primitive = clazz.isPrimitive();
            dimension = 0;
            name = ClassUtil.getShortClassName( clazz );
            qulifiedName = clazz.getName();
            qulifiedPackageName = ClassUtil.getPackageName( clazz );
            element = null;   
        }

        private TypeImpl( Type element, int dimension )
        {
            this.primitive = false;
            this.dimension = dimension;
            this.element   = element;
            this.name      = element.getName() + StringUtil.repeat( "[]", dimension );
            this.qulifiedName = element.getQulifiedName() + StringUtil.repeat( "[]", dimension );
            this.qulifiedPackageName = null;
            
        }
        
        public String getDynamicBeanClassName()
        {
            return null;
        }

        public boolean isDynamic()
        {
            if( element != null && element.isDynamic() )
            {
                return true;
            }
            
            return false;
        }

        public boolean isPrimitive()
        {
            return primitive;
        }

        public Type getElement()
        {
            return element;
        }

        public int getDimension()
        {
            return dimension;
        }

        public String getName()
        {
            return name;
        }

        public String getQulifiedName()
        {
            return qulifiedName;
        }

        public String getQulifiedPackageName()
        {
            return qulifiedPackageName;
        }
    }   
}
