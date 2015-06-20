/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanMap;
import org.hibernate.LazyInitializationException;
/**
 *
 * @author donniestorm
 * @param <T> Bean
 */
public class BeanManager<T> {
    private BeanMap bean;
    private Field[] fields;
    
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
        fields = instance.getClass().getDeclaredFields();
    }
    
    private void setBeanClass(Class<T> beanClass) {
        
        try {
            T instance = beanClass.newInstance();
            bean = new BeanMap(instance);
            fields = beanClass.getDeclaredFields();
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
        return fields;
    }
    
    public T getBean() {
        return (T) bean.getBean();
    }
    
}