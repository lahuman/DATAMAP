package kr.pe.lahuman.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;




/**
 * The Class DataMap.
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class DataMap<K, V>  extends HashMap<K, V>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    public DataMap() {
        super();
    }

    /**
	 * underscore('_') to Camel Case
	 * (all word translate small letter)
	 * ex) USEr_NAME => userName
	 * @param key
	 * @return
	 */
	private String convert2CamelCase(String key){
		if(key.indexOf('_') < 0 && Character.isLowerCase(key.charAt(0))){
			return key;
		}
		StringBuilder result = new StringBuilder();
		int len = key.length();
		boolean nextUpper = false;
		for(int i=0; i< len; i++){
			char currentChar = key.charAt(i);
			if(currentChar == '_'){
				nextUpper = true;
			}else{
				if(nextUpper){
					result.append(Character.toUpperCase(currentChar));
					nextUpper= false;
				}else{
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	@Override
	public V put(K key, V value){
		if(key instanceof String){
			return super.put((K) convert2CamelCase((String)key), value);
		}
		return super.put(key, value);
	}


	/**
	 * Instantiates a new data map.
	 *
	 * @param srcMapParam the src map param
	 */
	public DataMap (Map<K, V> srcMapParam){

		if( srcMapParam != null ){
			this.clear();
			Iterator<K> it = srcMapParam.keySet().iterator();
			while( it.hasNext() ){
				K key = it.next();
				V val = srcMapParam.get(key);
				this.put(key, val);
			}
		}
	}


	
	/**
	 * DataMap에 현재 담겨있는 키값을 String[] 배열로 리턴한다.
	 * 만약 값이 없을 경우는 null를 반환한다.
	 *
	 * @return the keys
	 */
	public String[] getKeys(){
		Set<K> keySet = this.keySet();
		if(keySet == null) return null;
		String[] keys = new String[keySet.size()]; 
		Iterator<K> iter = keySet.iterator();
		int count = 0;
		while(iter.hasNext()){
            Object key = iter.next();
            if(key instanceof String)
			    keys[count++] = (String)key;
            else
                keys[count++] = key.toString();
		}
		return keys;
	}



	/**
	 * Gets the string.
	 *
	 * @param key the key
	 *
	 * @return the string
	 */
	public String getString( String key ) {
		String rtn = "";
		try {
			rtn = (getString(key, ""));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * Gets the string.
	 * 키값에 해당하는 스트링 값을 반환합니다.</br>
	 * 해당 문자가 없을 null일 경우 대체 문자열로 변경합니다.
	 *
	 * @param key key value
	 * @return the string
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public String[] getStringValues(String key){
		String[] returnVal = null;
		
		Object obj = get( key );
		if( obj instanceof String[] ){
			returnVal = (String[])obj;
		}
		
		return returnVal;
	}	
	
	/**
	 * return 값을 String 형으로 변환 하여 return 한다.<br />String[] 이 return 될 경우 String[0] 값이 return 된다.
	 * 
	 *
	 * @param key the key
	 * @param replaceValue the replace value
	 * @return the string
	 * @throws SQLException the sQL exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String getString(String key, String replaceValue) throws SQLException, IOException{
		String returnVal = null;
		Object obj = get( key );
		if( obj instanceof String[] ){
			String[] strArr = (String[])obj;
			returnVal = strArr[0];
		}else if(obj instanceof String) {
			returnVal = (String)obj;
		}else if(obj instanceof Clob){
			Clob clob = (Clob)obj;
			StringBuffer strOut = new StringBuffer();
			String str = "";
			BufferedReader br = new BufferedReader(clob.getCharacterStream());
			
			while ((str = br.readLine()) != null) {
			 strOut.append(str+"\r\n");
			}
			returnVal = strOut.toString();
		}else{
			returnVal = (obj==null?replaceValue:obj.toString());
		}
		return returnVal == null?replaceValue:returnVal ;
	}

	/**
	 * Gets the int.
	 *
	 * @param key the key
	 *
	 * @return the int
	 */
	public int  getInt( String key ){
		return getInt(key, 0);
	}
	
	/**
	 * 키값에 해당하는 int 값을 반환합니다.</br>
	 * 해당 key값에 해당하는 값이 null일 경우 대체 숫자값으로 변경합니다.
	 *
	 * @param key the key
	 * @param replaceValue the replace value
	 * @return the int
	 */
	public int getInt(String key, int replaceValue){
		Object obj = get(key);
		try{
			if( obj == null ) {
				return replaceValue;
			}else if( obj instanceof String ){
				return Integer.parseInt( (String)obj );
			}else if( obj instanceof Integer ){
				return ((Integer) obj ).intValue();
			}else if( obj instanceof BigDecimal ){
				return ((BigDecimal) obj ).intValue();
			}else if( obj instanceof String[] ){
				String[] strArr = (String[])obj;
				return Integer.parseInt( strArr[0] );
			}else if( obj instanceof Long ){
				return ((Long)obj).intValue() ;
			}else if( obj instanceof Double ){
				return ((Double)obj).intValue() ;
			}else{
				return replaceValue;
			}
		}catch(Exception e){
			return replaceValue;
		}
	}
	
	
	public double  getDouble( String key ){
		return getDouble(key, 0);
	}
	
	
	public double getDouble(String key, int replaceValue){
		Object obj = get(key);
		try{
			if( obj == null ) {
				return replaceValue;
			}else if( obj instanceof String ){
				return Double.parseDouble( (String)obj );
			}else if( obj instanceof Integer ){
				return ((Integer) obj ).doubleValue();
			}else if( obj instanceof BigDecimal ){
				return ((BigDecimal) obj ).doubleValue();
			}else if( obj instanceof String[] ){
				String[] strArr = (String[])obj;
				return Double.parseDouble( strArr[0] );
			}else if( obj instanceof Long ){
				return ((Long)obj).doubleValue() ;
			}else if( obj instanceof Double ){
				return ((Double)obj).doubleValue() ;
			}else{
				return replaceValue;
			}
		}catch(Exception e){
			return replaceValue;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		Object key;
		Object val;
		Iterator<K> it = keySet().iterator();
        boolean isFirst = true;
		while( it.hasNext() ){
			key = it.next();
			val = get( key );
            if(isFirst){
                isFirst = false;
            }else{
                sb.append(", ");
            }
			sb.append("\"").append(key).append(":").append( val  ).append("\"");
		}
		sb.append("}");
		return sb.toString();
	}
}
