/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rsreflection;

/**
 *
 * @author ben
 */
public class FieldInfo {
    
    private final String className, fieldName;
    private final int multiplier;
    
    public FieldInfo(String c, String f, int m){
        className = c;
        fieldName = f;
        multiplier = m;
    }
    
    public int getMultiplier(){
        return multiplier;
    }
    
    public String getClassName(){
        return className;
    }
    
    public String getFieldName(){
        return fieldName;
    }
    
    public void printFieldInfo(){
        System.out.println(className);
        System.out.println(fieldName);
        System.out.println(multiplier);
    }
}
