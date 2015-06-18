/*
 * Copyright (C) 2015 Instituto de Ingeniería, UABC.
 *
 * This file is part of ModA-TiDescubrimiento.
 *
 * ModA-TiDescubrimiento is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * ModA-TiDescubrimiento is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package iing.uabc.edu.mx.vista.webservices;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Esta clase es utilizada para realizar la conversion de un objeto bean a un
 * tipo de datos JSON  y viceversa.
 * @author Alex
 */
public class JsonUtil {
    
    /**
     * Este metodo realiza la conversion de una cadena de texto en formato JSON
     * a su bean correspondiente.
     * 
     * La cadena de texto debera tener el siguiente formato:
     * {
     *  "clase":{
     *      "atributo1":"valor1",
     *      "atributo2":"valor2"
     *   }
     * }
     * 
     * En "clase" del formato anterior, se debera incluir el paquete donde
     * se localiza la clase.
     * 
     * @param jsonData Cadena de texto en formato JSON
     * @return Se regresa el objeto bean del JSON
     */
    public static Object fromJson(String jsonData){
        Object result = null;
        JSONObject json = null;
        try {
            //Se crea el objeto JSON de la cadena
            json = new JSONObject(jsonData);
            for(Iterator it = json.keys(); it.hasNext();){
                String className = (String) it.next();
                //Se obtiene la clase y se intancia el bean
                result = Class.forName(className).newInstance();
                //Se obtiene la estructura JSON
                json = json.getJSONObject(className);
                //Se llama el metodo privado fromJson que crea el bean
                fromJson(json, result);
            }            
        } catch (JSONException | ClassNotFoundException | InstantiationException 
                | IllegalAccessException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Este metodo se encarga de darle los valores del objeto JSON al bean recibido.
     * @param json Objeto de JSON con la estructura del bean
     * @param object Objeto bean 
     * @return Se regresa el mismo objeto bean con la estructura de objeto JSON
     */
    private static Object fromJson(JSONObject json, Object object){
        //Se itera por las llave del JSON
        for(Iterator it = json.keys(); it.hasNext();){
            String key = (String) it.next();
            try {
                Object element = json.get(key);
                System.out.println("KEY: " + key);
                //Se verifica si el elemento de llave es un objecto JSON, un 
                //arrelo JSON o un objeto primitivo
                if(element instanceof JSONObject){
                    //Se obtiene el bean correspondiente a la estructura del 
                    //objeto de JSON, utilizando este metodo recursivamente
                    Object value = fromJson(
                        (JSONObject) element, 
                            getInstanceOfProperty(object, key));
                    //Se asigna el valor a la propiedad del bean
                    setValue(object, key, value);
                }else if(element instanceof JSONArray){
                    JSONArray array = (JSONArray) element;
                    //Se obtiene la coleccion correspondiente a la estructura
                    //del arreglo de JSON
                    Collection col = fromJson(array, object, key);
                    //Se asigna el valor a la propiedad del bean
                    setValue(object, key, col);
                }else{
                    if(element != JSONObject.NULL)
                        //En caso de no ser nulo, se asigna el valor a la propiedad del bean
                        setValue(object, key, element);
                }
            } catch (JSONException ex) {
                Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        return object;
    }
    
    /**
     * Este metodo se encarga de crear una coleccion de objetos por medio de un
     * arreglo de JSON.
     * 
     * @param array Arreglo de JSON a convertir en coleccion
     * @param object Objeto al que pertenece la coleccion
     * @param nameCollection Nombre de la propiedad de la coleccion
     * @return Regresa la coleccion, ya sea de objetos primitivos, de beans o de mas arreglos
     */
    private static Collection fromJson(JSONArray array, Object object, String nameCollection){
        Collection result = new HashSet();
        Class<?> clazz = null;
        //Se itera por cada elemento del arreglo
        for (int i = 0; i < array.length(); i++) {
            try {
                Object element = array.get(i);
                System.out.println("Element[" + i + "]");
                if(element instanceof JSONObject){
                    //Si es la primera vez que se detecta un objeto de JSON, se
                    //identifica a que clase pertenece este objeto
                    if(clazz == null)
                        //Se obtiene la clase del objeto
                        clazz = getClassOfCollection(object, nameCollection);
                    //Se crea el objeto y se anade a la coleccion
                    result.add(fromJson((JSONObject) element, clazz.newInstance()));
                }else if(element instanceof JSONArray){
                    //Se llama este metodo recursivamente al contar con arreglos
                    //anidados
                    result.addAll(fromJson((JSONArray) element, object, nameCollection));
                }else{
                    //Si es un arreglo de objeto primitivos, estos son simplemente anadidos.
                    result.add(element);
                }
            } catch (JSONException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        return result;
    }
    
    /**
     * Este metodo asigna el valor de la propiedad de un objeto dado.
     * 
     * @param object Objeto bean a asignar el valor
     * @param nameProperty Nombre de la propiedad a asignar
     * @param value Valor que se asignara
     */
    private static void setValue(Object object, String nameProperty, Object value){
        try {
            //Se identificar las propiedades del objeto beam
            PropertyDescriptor[] properties = Introspector.getBeanInfo(object.getClass())
                    .getPropertyDescriptors();
            //Se itera por las propiedades para encontrarla a traves del nombre
            for (PropertyDescriptor property : properties) {
                if(property.getDisplayName().equals(nameProperty)){
                    //Se esta propiedad es una Fecha se le asigna un formato esspefico
                    if(property.getPropertyType() == Date.class){
                        SimpleDateFormat dateFormat = 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = dateFormat.parse((String) value);
                    }
                    //Se llama el metodo de escritura de la propiedad (Setter
                    property.getWriteMethod().invoke(object, value);
                    System.out.println(nameProperty + " - " + value.toString());
                    break;
                }
            }
        } catch (IntrospectionException | IllegalAccessException 
                | IllegalArgumentException | InvocationTargetException | ParseException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Este metodo obtiene una instancia de la propiedad del objeto. Este metodo
     * se ejecuta si la propiedad es una clase compleja, no primitiva.
     * 
     * @param object Objeto al que le pertenece la propiedad
     * @param nameProperty Nombre de la propiedad a instanciar
     * @return Regresa la instancia de la propiedad dad
     */
    private static Object getInstanceOfProperty(Object object, String nameProperty){
        Object result = null;
        try {
            //Se obtienen las propiedades del objeto
            PropertyDescriptor[] properties = Introspector.getBeanInfo(object.getClass())
                    .getPropertyDescriptors();
            //Se itera a traves de las propiedades para encontrarla con el nombre dado
            for (PropertyDescriptor property : properties) {
                if(property.getDisplayName().equals(nameProperty)){
                    //Se obtiene el tipo de la propiedad y se crea una nueva instancia
                    result = property.getPropertyType().newInstance();
                    break;
                }
            }
        } catch (IntrospectionException | IllegalArgumentException 
                | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Este metodo obtiene la clase de una propiedad de un objeto, esta propiedad
     * es una coleccion.
     * 
     * @param object Objeto al que le pertenece la coleccion
     * @param nameCollection Nombre de la coleccion
     * @return Regresa la clase que almacena la coleccion
     */
    private static Class<?> getClassOfCollection(Object object, String nameCollection){
        Class<?> result = null;
        try {
            //Se obtiene el campo con el nombre de la coleccion
            Field field = object.getClass().getDeclaredField(nameCollection);
            //Se obtiene el tipo de coleccion
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            //Se obtiene los argumentos de clase que tiene la coleccion (Generics)
            result = (Class<?>) type.getActualTypeArguments()[0];
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException 
                ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    /**
     * Este metodo convierte un objeto Bean en una cadena de texto con formato JSON.
     * 
     * @param objectData Objeto a convertir
     * @return Regresa una cadena de texto con formato JSON
     */
    public static String toJson(Object objectData){
        //Se hace la conversiona traves del constructor del objeto JSON
        JSONObject json = new JSONObject(objectData);
        try {
            System.out.println(json.toString(3));
        } catch (JSONException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }
    
}
