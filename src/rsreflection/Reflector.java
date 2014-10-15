/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsreflection;

import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ben
 */
public class Reflector extends URLClassLoader {
	

	private HashMap<String, FieldInfo> hookMap; 
	
	public Reflector(URL[] urls) {
		super(urls);
	}

	public Object getFieldValue(String identifier){
		FieldInfo f = hookMap.get(identifier);
		return getFieldValueByDetails(f.getClassName(), f.getFieldName());
	}
	
	public Object getFieldValueByDetails(String className, String fieldName) {

		Object o = null;
		try {
			Field field = loadClass(className).getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(null);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Reflector.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(Reflector.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(Reflector.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NoSuchFieldException ex) {
			Logger.getLogger(Reflector.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(Reflector.class.getName()).log(Level.SEVERE, null, ex);
		}

		return o;
	}

	public void setHooks(HashMap<String, FieldInfo> hookMap) {
		this.hookMap = hookMap;
	}

}
