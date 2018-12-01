package com.zfsoft.orcus.lang;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 数组工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class ArrayUtil {
	
	/**
	 * 根据指定的长度将线性位置转变为数组索引
	 * @param position 线性位置，不能小于0
	 * @param lens 数组长度，不能为null
	 * @return 数组索引
	 * @throws RuntimeException 如果position<0 或lens==null
	 */
	public static int[] getIndexes( int position, int[] lens ) {
		if( lens == null || "".equals(lens) ) {
			throw new RuntimeException("parameter [lens] is required; it cannot be null.");
		}
		
		if( position < 0 ) {
			throw new RuntimeException("parameter [lens] must greater than or quals to 0");
		}
		
		int[] indexes = new int[lens.length];
		int index = position;
		
		for( int i = lens.length - 1; i >= 0; i-- ) {
			indexes[i] = index % lens[i];
			index = (int)(index / lens[i]);
		}
		
		return indexes;
	}
	
	/**
	 * 在数组中新增一个元素
	 * <p>新增的元素在数组最后</p>
	 * @param array 原数组
	 * @param element 将被新增的元素
	 * @param elementType 元 素的类型，不能为null
	 * @return 包含新增元素的新数组
	 * @throws RuntimeException 如果elementType==null
	 */
	public static Object[] addElement( Object[] array, Object element, Class elementType ) {
		if( elementType == null ) {
			throw new RuntimeException("parameter [elementType] is required; it cannot be null.");
		}
		
		if( array == null || array.length == 0 ) {
			Object[] result = ( Object[] ) Array.newInstance( elementType, 1 );
			result[0] = element;
			return result;
		} else {
			Object[] result = ( Object[] ) Array.newInstance( elementType, array.length + 1 );
			System.arraycopy( array, 0, result, 0, array.length );
			result[array.length] = element;
			return result;
		}
		
	}
	
	/**
	 * 在数组中新增一批元素
	 * <p>新增的元素在数组最后</p>
	 * @param array 原数组
	 * @param elements 将被新增的元素
	 * @param elementType 元 素的类型，不能为null
	 * @return 包含新增元素的新数组
	 * @throws RuntimeException 如果elementType==null
	 */
	public static Object[] addElements( Object[] array, Object[] elements, Class elementType ) {
		if( elementType == null ) {
			throw new RuntimeException("parameter [elementType] is required; it cannot be null.");
		}
		
		if( elements == null || elements.length == 0 ) return array;
		
		if( array == null || array.length == 0 ) return elements;
		
		Object[] result = ( Object[] ) Array.newInstance( elementType, array.length + elements.length );
		
		System.arraycopy( array, 0, result, 0, array.length );
		System.arraycopy( elements, 0, result, array.length, elements.length );
		
		return result;
	}
	
	/**
	 * 在数组中删除指定的元素
	 * @param array 原数据
	 * @param index 将被删除的元素的索引
	 * @param elementType 元素的类型，不能为null
	 * @return 	<p>如果array == null 或 array.length == 0 则返回array</p>
	 * 			<p>如果index &lt; 0 或 index &gt;= array.length则返回array</p>
	 * 			<p>否则返回不包含被删除元素的心数组</p>
	 * @throws RuntimeException 如果elementType==null
	 */
	public static Object[] removeElement( Object[] array, int index, Class elementType ) {
		if( elementType == null ) {
			throw new RuntimeException( "parameter [elementType] is required; it cannot be null." );
		}
		
		if( array == null || array.length == 0 ) {
			return array;
		}
		
		if( index < 0 || index >= array.length ) {
			return array;
		}
		
		Object[] result = (Object[]) Array.newInstance( elementType, array.length - 1 );
		System.arraycopy( array, 0, result, 0, index );
		System.arraycopy( array, index + 1, result, index, array.length - index - 1 );
		
		return result;
	}
	
	/**
	 * 在数组中删除指定的元素
	 * @param array 原数组
	 * @param indexes 将被删除元素的索引
	 * @param elementType 元素的类型，不能为null
	 * @return	<p>如果array==null或array.length==0则返回array</p>
	 * 			<p>如果indexes==null或indexes.length==0则返回array</p>
	 * 			<p>否则返回不包含被删除元素的新数组</p>
	 * @throws RuntimeException 如果elementType==null
	 */
	public static Object[] removeElement( Object[] array, int[] indexes, Class elementType ) {
		if( elementType == null ) {
			throw new RuntimeException( "parameter [elementType] is required; it cannot be null." );
		}
		
		if( array == null || array.length == 0 ) {
			return array;
		}
		
		if( indexes == null || indexes.length == 0 ) {
			return array;
		}
		
		Object[] result = (Object[]) Array.newInstance( elementType, 0 );
		
		for (int i = 0; i < array.length; i++) {
			boolean exist = false;
			
			for ( int index : indexes ) {
				if( i == index ) {
					exist = true;
					break;
				}
			}
			
			if( exist ) continue;
			
			result = addElement( result, array[i], elementType);
		}
		
		return result;
	}
	
	/**
	 * 在数组中删除指定的元素
	 * @param array 原数据
	 * @param element 将被删除的元素
	 * @param elementType 元素的类型，不能为null
	 * @return	<p>如果array==null或array.length==0则返回array</p>
	 * 			<p>如果array不包含element则返回array</p>
	 * 			<p>否则返回不包含被删除元素的新数组</p>
	 */
	public static Object[] removeElement( Object[] array, Object element, Class elementType ) {
		if( elementType == null ) {
			throw new RuntimeException( "parameter [elementType] is required; it cannot be null." );
		}
		
		if( array == null || array.length == 0 ) {
			return array;
		}
		
		Object[] result = (Object[]) Array.newInstance( elementType, 0 );
		
		for (Object object : array) {
			if( array == element || array.equals( object ) ) {
				continue;
			}
			
			result = addElement( result, object, elementType);
		}
		
		return result;
	}
	
	/**
	 * 返回指定数组元素的值
	 * @param array 数组对象，不得为null且必须为数组
	 * @param index 数组元素的索引
	 * @return 数组元素的索引
	 * @throws RuntimeException 如果 array==null 或 array不为数组
	 */
	public static Object getElement( Object array, int index ) {
		return getElement(array, new int[]{index});
	}
	
	/**
	 * 返回指定数组元素的值
	 * @param array 数组对象，不得为null且必须为数组
	 * @param indexes 数组元素的索引，不能为null且长度必须大于0
	 * @return 元素的值
	 * @throws RuntimeException 如果 array==null 或 array不为数组
	 * 								或 indexes==null 或 indexes.length == 0
	 */
	public static Object getElement(Object array, int[] indexes) {
		if( array == null ){
			throw new RuntimeException("parameter [array] is required; it cannot be null.");
		}
		
		if( !array.getClass().isArray() ) {
			throw new RuntimeException("parameter [array] is not a array.");
		}
		
		if( indexes == null ) {
			throw new RuntimeException("parameter [indexes] is required; it cannot be null.");
		}
		
		if( indexes.length == 0 ) {
			throw new RuntimeException("The length of parameter [indexes] must greater than 0.");
		}
		
		Object obj = array;
		
		for (int i = 0; i < indexes.length; i++) {
			obj = Array.get(obj, indexes[i]);
		}
		
		return obj;
	}
	
	/**
	 * 设置指定数组元素的值
	 * @param array 数组对象
	 * @param index 数组元素的索引
	 * @param value 元素值
	 * @throws RuntimeException 如果 array==null 或 array不为数组
	 */
	public static void setElement( Object array, int index, Object value ) {
		setElement(array, new int[]{index}, value);
	}
	
	/**
	 * 设置指定数组元素的值
	 * @param array 数组对象
	 * @param indexes 数组元素的索引
	 * @param value 元素值
	 * @throws RuntimeException 如果 array==null 或 array不为数组
	 * 								或 indexes==null 或 indexes.length == 0
	 */
	public static void setElement(Object array, int[] indexes, Object value) {
		if( array == null ){
			throw new RuntimeException("parameter [array] is required; it cannot be null.");
		}
		
		if( !array.getClass().isArray() ) {
			throw new RuntimeException("parameter [array] is not a array.");
		}
		
		if( indexes == null ) {
			throw new RuntimeException("parameter [indexes] is required; it cannot be null.");
		}
		
		if( indexes.length == 0 ) {
			throw new RuntimeException("The length of parameter [indexes] must greater than 0.");
		}
		
		Object obj = array;
		
		for ( int i = 0; i < indexes.length - 1; i++ ) {
			obj = Array.get(obj, indexes[i]);
		}
		
		Array.set(obj, indexes[indexes.length-1], value);
	}
	
	/**
	 * 返回数组最基本的元素类型
	 * @param loadClass 类对象，不能为null
	 * @return 元素类型
	 * @throws RuntimeException 如果clazz==null或clazz不是数组类
	 */
	public static Class getBaseComponentType(Class clazz) {
		if( clazz == null ){
			throw new RuntimeException("parameter [array] is required; it cannot be null.");
		}
		
		if( !clazz.isArray() ) {
			throw new RuntimeException("parameter [array] is not a array.");
		}
		
		Class sub = clazz.getComponentType();
		Class btm = sub;
		
		while( sub != null ) {
			btm = sub;
			sub = sub.getComponentType();
		}
		
		return btm;
	}
	
	/**
	 * 返回数组的显示名
	 * @param clazz 数组对象
	 * @return
	 */
	public static String getDefineName( Class clazz ) {
		int dim = 0;
		
		Class sub = clazz.getComponentType();
		Class btm = sub;
		
		while( sub != null ) {
			btm = sub;
			dim++;
			sub = sub.getComponentType();
		}
		
		return btm.getName() + StringUtil.repeat("[]", dim);
	}
	
	/**
	 * 返回数组的维护
	 * @param clazz 类对象，不能为null
	 * @return
	 */
	public static int getDimension(Class clazz) {
		int dim = 0;
		
		Class sub = clazz.getComponentType();
		
		while( sub != null ) {
			dim++;
			sub = sub.getComponentType();
		}
		
		return dim;
	}
	
	/**
	 * 返回数组对象的长度
	 * @param array 数组对象
	 * @return 长度数组，如果array == null,则返回null
	 */
	public static int[] getLengths( Object array ){
		if( array == null ) {
			return null;
		}
		
		int dim = getDimension(array.getClass());
		
		int[] lens = new int[dim];
		
		lens[0] = Array.getLength(array);
		
		Object obj = array;
		
		for(int i = 1; i < dim; i++) {
			if( lens[i-1] > 0 ) {
				obj = Array.get(obj, 0);
				
				lens[i] = Array.getLength(obj);
			} else {
				break;
			}
		}
		
		return lens;
	}
	
	/**
	 * 返回数组对象的包含元素的个数
	 * 
	 * <p>如 Object[2]含有两个元素，Object[2][3]含有六个元素</p>
	 * @param lens 数组对象的长度数组
	 * @return 数组对象包含元素的个数
	 */
	public static int getSize( int[] lens ) {
		if( lens == null ) {
			throw new RuntimeException("parameter [lens] is required; it cannot be null.");
		}
		
		int size = 0;
		
		for( int i=0; i<lens.length; i++ ) {
			if( i == 0 ) {
				size = lens[0];
			} else {
				size *= lens[i];
			}
		}
		
		return size;
	}
	
	/**
	 * 返回数组对象的包含元素的个数
	 * <p>
	 * 如 Object[2]含有两个元素，Object[2][3]含有六个元素
	 * </p>
	 * @param array 数组对象
	 * @return 数组对象包含元素的个数
	 */
	public static int getSize(Object array) {
		if( array == null ) {
			throw new RuntimeException("parameter [array] is required; it cannot be null.");
		}
		
		return getSize( getLengths(array) );
	}
	
	/**
	 * 比较两个数组对象是否具有相同的长度（维数相同且每维的长度相同）
	 * @param array1 数组对象1
	 * @param array2 数组对象2
	 * @return 如果长度相同或比较的对象都为null则返回true，否则返回false
	 * @throws RuntimeException 如果array1或array2不是数组对象
	 */
	public static boolean isSameLength( Object array1, Object array2 ) {
		int[] lens1 = getLengths( array1 );
		int[] lens2 = getLengths( array2 );
		
		if( lens1 == null && lens2 == null ) return true;
		
		if( lens1 == null && lens2 != null ) return false;
		
		if( lens1 != null && lens2 == null ) return false;
		
		if( lens1.length != lens2.length ) return false;
		
		for (int i = 0; i < lens1.length; i++) {
			if( lens1[i] != lens2[i] ) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 比较两个数组对象是否具有相同数量的元素（如int[2][3]和int[6]具有相同数量的无素）
	 * @param array1 数组对象1
	 * @param array2 数组对象2
	 * @return 如果元素数量相同或比较的对象都为null则返回true，否则返回false
	 * @throws RuntimeException 如果array1或array2不是数组对象
	 */
	public static boolean isSameSize( Object array1, Object array2 ) {
		int[] lens1 = getLengths( array1 );
		int[] lens2 = getLengths( array2 );
		
		if( lens1 == null && lens2 == null ) return true;
		
		if( lens1 == null && lens2 != null ) return false;
		
		if( lens1 != null && lens2 == null ) return false;
		
		return ( getSize( lens1 ) == getSize( lens2 ) );
	}
	/**
	 * 对数组进行排序（不改变原数组）
	 * @param array 数组对象
	 * @param comparator the comparator to determine the order of the array. 
	 * 			A <tt>null</tt> value indicates that the elements' <i>natural ordering</i> should be used.
	 * @return 排序后的数组，如果array为null则返回null
	 * @throws RuntimeException 如果array不是数组对象
	 */
	public static Object sort( Object array, Comparator comparator ) {
		if( array == null ) return null;
		
		int[] lens = getLengths( array );
		int size = getSize( lens );
		Object[] values = new Object[size];
		
		for( int i = 0; i < size; i++ ) {
			values[i] = getElement( array, getIndexes( i, lens ) );
		}
		
		Arrays.sort( values, comparator );
		
		Object result = Array.newInstance( getBaseComponentType( array.getClass() ), lens );
		
		for( int i = 0; i < size; i++ ) {
			setElement( result, getIndexes( i, lens), values[i] );
		}
		
		return result;
	}
	
	/**
	 * 比较2个数组
	 * @param array1 数组对象1
	 * @param array2 数组对象2
	 * @return 如果两个数组一样则返回true，否则返回false
	 */
	public static boolean equals( Object array1, Object array2 ) {
		if( array1 == array2 ) return true;
		
		if( array1 == null && array2 == null ) return true;
		
		if( array1 == null && array2 != null ) return false;
		
		if( array1 != null && array2 == null ) return false;
		
		//类型不一致
		if( array1.getClass().equals( array2.getClass() ) == false ) {
			return false;
		}
		
		try {
			if( isSameLength( array1, array2 ) == false ) return false;
			
			int[] lens = getLengths( array1 );
			int size = getSize( lens );
			
			for( int i = 0; i < size; i++ ) {
				int[] indexes = getIndexes( i, lens );
				
				Object value1 = getElement( array1, indexes );
				Object value2 = getElement( array2, indexes );
				
				if( value1 == null && value2 != null ) return false;
				if( value1 != null && value1 == null ) return false;
				if( value1 != null && value2 != null && !value1.equals( value2 ) ) return false;
			}
			
			return true;
			
		} catch (Throwable t) {
			return false;
		}
		
	}
	
	/**
	 * 交换数组中两个对象的位置
	 * @param array 数组
	 * @param index1 对象索引(base 0)
	 * @param index2 对象索引(base 1)
	 * @return true表示进行了交换，fasle表示未进行交换
	 */
	public static boolean swap( Object[] array, int index1, int index2 ) {
		if( array == null  || array.length == 0 ) return false;
		
		if( index1 == index2 ) return false;
		
		if( index1 < 0 || index1 > array.length ) return false;
		
		if( index2 < 0 || index2 > array.length ) return false;
		
		Object object = array[index1];
		array[index1] = array[index2];
		array[index2] = object;
		
		return true;
	}

}
