/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import java.io.StringReader;
import java.util.HashSet;
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
        HashSet visitedEntities = new HashSet();
        
        try ( //For reading all the json string tree
                JsonParser parser = Json.createParser(new StringReader(jsonString))) {
            
            Event evt = parser.next();
            if(evt == Event.START_OBJECT) {
                //Is an object
                parseObject(parser);
            }
            else if(evt == Event.START_ARRAY) {
                //Is an array
                
            }
        }
        
        return pojo;
    }
    
    private static void parseObject(JsonParser parser) {
        
        
    }
}