/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
/**
 *
 * @author donniestorm
 */
public class JSON {
    
    public static <T> T parse(String jsonString, Class<T> clazz) {
        //The resulting object represented as the json String
        T pojo = null;
        //Prepare POJO for json to POJO parsing
        BeanManager<T> manager = new BeanManager<>(clazz);
        
        //A list of visited references to avoid a infinite loop
        ArrayList visitedObjects = new ArrayList();
        
        visitedObjects.add(manager.getBean());
        
        try ( //For reading all the json string tree
                JsonParser parser = Json.createParser(new StringReader(jsonString))) {
            
            Event evt = parser.next();
            if(evt == Event.START_OBJECT) {
                System.out.println("Nuevo Objeto: " + clazz.getSimpleName());
                //Is an object
                parseObject(parser, visitedObjects, manager);
            }
            else if(evt == Event.START_ARRAY) {
                //Is an array
                System.out.println("Nuevo Arreglo");
                parseArray(parser, visitedObjects, manager);
            }
            
            pojo = manager.getBean();
        }
        
        return pojo;
    }
    
    private static void parseObject(JsonParser parser, List visitedObjects,
            BeanManager manager) {
        Event evt;
        String keyName = "";
        
        do {
            evt = parser.next();
            
            switch (evt) {
                case START_OBJECT:
                    System.out.println("Nuevo Objeto: " + manager.getType(keyName));
                    parseObject(parser, visitedObjects, null);
                    break;
                    
                case START_ARRAY:
                    System.out.println("Nuevo Arreglo");
                    parseArray(parser, visitedObjects, null);
                    break;
                    
                case KEY_NAME:
                    //Get attribute name
                    keyName = parser.getString();
                    System.out.println("KeyName: " + keyName);
                    break;
                    
                case VALUE_FALSE:
                    manager.setProperty(keyName, false);
                    System.out.println("Valor " + keyName + ": " + false);
                    break;
                    
                case VALUE_NULL:
                    manager.setProperty(keyName, null);
                    System.out.println("Valor " + keyName + ": null");
                    break;
                    
                case VALUE_NUMBER:
                    //double or int
                    if(parser.isIntegralNumber()) {
                        int value = parser.getInt();
                        manager.setProperty(keyName, value);
                        System.out.println("Valor " + keyName + ": " + value);
                    }
                    else {
                        double value = parser.getBigDecimal().doubleValue();
                        manager.setProperty(keyName, value);
                        System.out.println("Valor " + keyName + ": " + value);
                    }
                    break;
                    
                case VALUE_STRING:
                    manager.setProperty(keyName, parser.getString());
                    System.out.println("Valor " + keyName + ": " + parser.getString());
                    break;
                case VALUE_TRUE:
                    manager.setProperty(keyName, true);
                    System.out.println("Valor " + keyName + ": " + true);
                    break;
            }
        } while (evt != Event.END_OBJECT);
        
    }
    
    private static void parseArray(JsonParser parser, List visitedObjects,
            BeanManager manager) {
        
    }

}