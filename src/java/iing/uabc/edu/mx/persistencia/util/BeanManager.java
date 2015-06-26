/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanMap;
import org.hibernate.LazyInitializationException;
import org.apache.commons.beanutils.PropertyUtils;

/**
 *
 * @author donniestorm
 * @param <T> Bean
 */
public class BeanManager<T> {
    private BeanMap bean;
    
    public BeanManager(String fullClassName) {
        
        try {
            //Get class by name
            Class<T> clazz = (Class<T>) Class.forName(fullClassName);
            setBeanClass(clazz);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BeanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BeanManager(Class<T> clazz) {
        setBeanClass(clazz);
    }
    
    public BeanManager(T instance) {
        bean = new BeanMap(instance);
    }
    
    private void setBeanClass(Class<T> beanClass) {
        
        try {
            T instance = beanClass.newInstance();
            bean = new BeanMap(instance);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(BeanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setProperty(String propertyName, Object value) {
        
        bean.put(propertyName, value);
    }
    
    public Object getProperty(String propertyName) {
        Object property;
        
        try {
            property = bean.get(propertyName);
            //force the atributte to throw lazy exception if any
            if(property != null) {
                //If the value exists then try if it isnt lazy initializated.
                property.toString();
            }
            
        } catch(LazyInitializationException ex) {
            //Logger.getLogger(BeanManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Lazy Initialized Property, ignoring....");
            property = null;
        }
        return property;
    }
    
    public Field[] getFields() {
        return bean.getBean().getClass().getDeclaredFields();
    }
    
    public Class getType(String propertyName) {
        Class type = null;
        
        try {
            type = PropertyUtils.getPropertyDescriptor(bean.getBean(), propertyName)
                    .getPropertyType();
        } catch (SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            Logger.getLogger(BeanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return type;
    }
    
    public Class getCollectionElementType(String propertyName) {
        Class elementType = null;
        
        try {
            Field collectionField = bean.getBean().getClass()
                    .getDeclaredField(propertyName);
            
            ParameterizedType collectionType = (ParameterizedType) collectionField.
                    getGenericType();
            
            elementType = (Class) collectionType.getActualTypeArguments()[0];
            
        } catch (NoSuchFieldException | SecurityException ex) {
            Logger.getLogger(BeanManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return elementType;
    }
    
    public T getBean() {
        return (T) bean.getBean();
    }
    
}