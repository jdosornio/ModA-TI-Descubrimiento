/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.vista.webservices;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class ClassProperty {
    
    private Class clazz;
    private PropertyDescriptor[] properties;
    private PropertyDescriptor currentProperty;
    private Object instance;
    
    public ClassProperty(){
    }    
    
    public ClassProperty(String className) throws ClassNotFoundException{
        this(Class.forName(className));
    }
    
    public ClassProperty(Class clazz){
        this.clazz = clazz;
        try {
            properties = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
            instance = clazz.newInstance();
        } catch (IntrospectionException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(ClassProperty.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void setCurrentProperty(String propertyName) {
        for (PropertyDescriptor property : properties) {
            if(property.getName().equals(propertyName)){
                currentProperty = property;
                break;
            }
        }
    }
    
    public Object getInstance(){
        return this.instance;
    }
    
    public Class<?> getCurrentPropertyClass(){
        return currentProperty.getPropertyType();
    }
    
    public Class<?> getCollectionClass(){
        Class<?> result = null;
        if(isCurrentPropertyCollection()){
            Field field = null;
            try {
                field = getCurrentPropertyClass().
                        getDeclaredField(currentProperty.getDisplayName());
            } catch (NoSuchFieldException | SecurityException ex) {
                Logger.getLogger(ClassProperty.class.getName()).log(Level.SEVERE, null, ex);
            }
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            result = (Class<?>) type.getActualTypeArguments()[0];
        }
        return result;
    }
    
    public boolean isCurrentPropertyPrimitive(){
        return isPrimitiveOrWrapper(currentProperty.getPropertyType());
    }
    
    public boolean isCurrentPropertyCollection(){
        return isCollection(currentProperty.getPropertyType());
    }
    
    private boolean isPrimitiveOrWrapper(Class<?> type) {
        return (type.isPrimitive() && type != void.class) ||
            type == Double.class || type == Float.class || type == Long.class ||
            type == Integer.class || type == Short.class || type == Character.class ||
            type == Byte.class || type == Boolean.class || type == String.class ||
            type == Date.class;
    }
    
    private boolean isCollection(Class<?> type){
        return type.isArray() || Collection.class.isAssignableFrom(type)
            || Map.class.isAssignableFrom(type);
    }
    
    public void addValueCurrentProperty(Object value){
        try {
            currentProperty.getWriteMethod().invoke(instance, value);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(ClassProperty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
