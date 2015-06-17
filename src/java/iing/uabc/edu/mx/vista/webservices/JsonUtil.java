package iing.uabc.edu.mx.vista.webservices;


import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex
 */
public class JsonUtil {
    
    public static Object fromJson(String jsonData){
        
        int current = -1;
        JsonParser parser = Json.createParser(new StringReader(jsonData));
        ClassProperty currentResult= null;
        int startArray = -1;
        List<ClassProperty> result = null;
        
        while(parser.hasNext()){
            JsonParser.Event event = parser.next();
            if(current >= 0)
                currentResult = result.get(current);
            switch(event){
                case START_ARRAY:
                   startArray = current;
                   System.out.println(event.toString());
                   break;
                case END_ARRAY:
                    int tamano = result.size();
                    Collection col = null;
                    for (int i = startArray; i < tamano; i++) {
                        col = new HashSet();
                        col.add(result.get(startArray).getInstance());
                        result.remove(startArray);
                    }
                    result.get(startArray - 1).addValueCurrentProperty(col);
                    current = startArray - 1;
                   System.out.println(event.toString());
                   break;
                case START_OBJECT:
                    if(startArray != -1){
                        result.add(new ClassProperty(result.get(current)
                            .getInstance().getClass()));
                        current++;
                    }
                   System.out.println(event.toString());
                   break;
                case END_OBJECT:
                    if(current > 0 && startArray == -1){
                        result.get(current - 1)
                            .addValueCurrentProperty(currentResult.getInstance());
                        result.remove(current);
                        current--;
                    }
                   System.out.println(event.toString());
                   break;
                case VALUE_FALSE:
                   System.out.println(event.toString());
                   break;
                case VALUE_NULL:
                   System.out.println(event.toString());
                   break;
                case VALUE_TRUE:
                   System.out.println(event.toString());
                   break;
                case KEY_NAME:
                    if(current < 0){
                        try {
                            //Este bloque de codigo se ejecutura la primera vez para
                            //obtener la clase del Json
                            result = new ArrayList(); 
                            result.add(new ClassProperty(parser.getString()));
                            current++;
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        currentResult.setCurrentProperty(parser.getString());
                        
                        if(currentResult.isCurrentPropertyCollection()){
                            result.add(new ClassProperty(currentResult.getCollectionClass()));
                            current++;
                        }else if(!currentResult.isCurrentPropertyPrimitive()){
                            result.add(new ClassProperty(currentResult.getCurrentPropertyClass()));
                            current++;
                        }
                    }
                   System.out.println(event.toString() + " " +
                                      parser.getString() + " - ");
                   break;
                case VALUE_STRING:
                   if(currentResult.getCurrentPropertyClass() == Date.class){
                        SimpleDateFormat dateFormat = 
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            currentResult.addValueCurrentProperty(dateFormat.parse(parser.getString()));
                        } catch (ParseException ex) {
                            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   }else{
                       currentResult.addValueCurrentProperty(parser.getString());
                   }
                       
                   System.out.println(event.toString() + " " +
                                      parser.getString());
                   break;
                case VALUE_NUMBER:
                    Number value;
                   Class<?> type = currentResult.getCurrentPropertyClass();
                   if(type == Integer.class)
                       value = parser.getInt();
                   else if(type == Long.class)
                       value = parser.getLong();
                   else
                       value = parser.getBigDecimal();
                   currentResult.addValueCurrentProperty(value);
                   System.out.println(event.toString() + " " +
                                      parser.getString());
                   break;
            }
        }
        return result.get(0).getInstance();
    }
    
    public static String toJson(Object objectData){
        JSONObject json = new JSONObject(objectData);
        try {
            System.out.println(json.toString(3));
        } catch (JSONException ex) {
            Logger.getLogger(JsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json.toString();
    }
}
