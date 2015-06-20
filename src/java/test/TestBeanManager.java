/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.util.BeanManager;
import java.lang.reflect.Field;

/**
 *
 * @author donniestorm
 */
public class TestBeanManager {
    
    public static void main(String[] args) {
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        Recurso r = (Recurso) ServiceDelegateLocator.getInstance().find(1);
        
        BeanManager<Recurso> manager = new BeanManager<>(r);
        
        Field[] fields = manager.getFields();
        
        for(Field field : fields) {
            System.out.println("Nombre del campo: " + field.getName());
            
            System.out.println("Valor del atributo: " + manager.getProperty(field.getName()));
        }
    }
}