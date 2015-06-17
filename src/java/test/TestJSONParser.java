/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author donniestorm
 */
public class TestJSONParser {

    public static void main(String[] args) {
        String json = "{\"descUrl\": \"http://example.com\"}";

        JsonReader reader = Json.createReader(new StringReader(json));

        JsonObject obj = reader.readObject();

        reader.close();

        Class clazz = Recurso.class;

        Field[] fields = clazz.getDeclaredFields();
        Object instance = null;
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(TestJSONParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Field field : fields) {
            try {
                Class type = field.getType();

//            if (type.equals(String.class)) {
//                System.out.println(obj.getString(field.getName()));
//            }
                System.out.println(obj.get(field.getName()));

                Method setMethod = clazz.getDeclaredMethod("set" + StringUtils
                        .capitalize(field.getName()), type);

                if (obj.get(field.getName()) != null) {
                    if (type.equals(String.class)) {
                        setMethod.invoke(instance, obj.getString(field.getName()));
                        System.out.println("Tipo devuelto por json.get(): " + obj.get(field.getName()).getClass());
                    }
                }

                System.out.println("Tipo del campo: " + type);

            } catch (IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(TestJSONParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("----------------");
        Recurso r = (Recurso) instance;

        System.out.println(r.getId());
        System.out.println(r.getDescUrl());

        System.out.println(obj);
        
        
        System.out.println("--------");
        
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        Recurso r2 = (Recurso) ServiceDelegateLocator.getInstance().find(1);
        
        System.out.println(r2.getId());
        System.out.println(r2.getDescUrl());
        
        
    }
}
