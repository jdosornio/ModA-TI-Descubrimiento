/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import iing.uabc.edu.mx.persistencia.modelo.Contexto;
import iing.uabc.edu.mx.persistencia.modelo.Proceso;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.modelo.Registro;
import iing.uabc.edu.mx.persistencia.modelo.Usuario;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.lang3.time.DateFormatUtils;
import test.TestPojo;

/**
 * Restrictions: The bean has to follow the java bean naming convention for
 * fields and accesors. If the bean throws some kind of lazy exception when
 * reading a field, it has to be from Hibernate.
 *
 * Every entity has to have a primary key field if you are planning to map
 * bidirectional associations. The name for this field has to be 'id' if you are
 * using xml mapping files instead of annotations.
 *
 * You cannot parse a json array containing not domain specific pojos directly.
 * Example: String, ArrayList, etc.
 *
 * When parsing a json String, the .class has to be the most specific one that
 * is represented by the json String.
 *
 * There cannot be arrays inside of arrays, nor domain pojos that declare
 * Collections of Collections, it can only be a collection of domain pojos,
 * containing another collection of domain pojos, String or primitive types, and
 * so on.
 *
 * The declared collections inside pojos has to be declared explicitly with the
 * class of the elements contained in the collection. Example: instead of List
 * elements = new List(); use List<Element> elements = new List<>(); You also
 * need to create new instances of collections right from the beginning in each
 * pojo instance.
 *
 * There cannot be collections of different element types, (Polymorphic), unless
 * every element shares exactly the same attribute names, so there will be no
 * problem with the parsing.
 *
 * @version 1
 * @author donniestorm
 */
public class JSON {

    public static <T> Object parse(String jsonString, Class<T> clazz) {
        //The resulting object represented as the json String
        Object result = null;

        try ( //For reading all the json string tree
                JsonParser parser = Json.createParser(new StringReader(jsonString))) {

            System.out.println("------------------Parsing Start--------------------");
            Event evt = parser.next();
            if (evt == Event.START_OBJECT) {
                System.out.println("Nuevo Objeto: " + clazz.getSimpleName());

                //Prepare POJO for json to POJO parsing
                BeanManager<T> manager = new BeanManager<>(clazz);
                //Is an object
                result = parseObject(parser, manager);
            } else if (evt == Event.START_ARRAY) {
                //Is an array
                System.out.println("Nueva Colleccion de clase: " + clazz.getSimpleName());

                //Prepare collection for json to Collection parsing
                CollectionManager<T> manager
                        = new CollectionManager<>(new ArrayList<>(), clazz);

                result = parseArray(parser, manager);
            }

            System.out.println("------------------Parsing End--------------------");
        }

        return result;
    }

    private static Object parseObject(JsonParser parser, BeanManager manager) {
        Event evt;
        String keyName = "";

        do {
            evt = parser.next();

            switch (evt) {
                case START_OBJECT:
                    Class objectClass = manager.getType(keyName);
                    System.out.println("Nuevo Objeto: " + objectClass.getSimpleName());
                    //Create new BeanManager for the new object with its class
                    BeanManager objectManager = new BeanManager(objectClass);

                    Object result = parseObject(parser, objectManager);

                    manager.setProperty(keyName, result);
                    break;

                case START_ARRAY:
                    //Get element class of the collection
                    Class elementClass = manager.getCollectionElementType(keyName);

                    System.out.println("Nueva Colleccion de clase: "
                            + elementClass.getSimpleName());

                    //Get collection instance from the bean
                    Collection collection = (Collection) manager.getProperty(keyName);

                    //Create new collection manager with the given class
                    CollectionManager collectionManager
                            = new CollectionManager(collection, elementClass);

                    collection = parseArray(parser, collectionManager);

                    manager.setProperty(keyName, collection);
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
                    if (parser.isIntegralNumber()) {
                        int value = parser.getInt();
                        manager.setProperty(keyName, value);
                        System.out.println("Valor " + keyName + ": " + value);
                    } else {
                        double value = parser.getBigDecimal().doubleValue();
                        manager.setProperty(keyName, value);
                        System.out.println("Valor " + keyName + ": " + value);
                    }
                    break;

                case VALUE_STRING:
                    String value = parser.getString();

                    //Validate if the field is of type Date
                    if (manager.getType(keyName) == Date.class) {
                        Calendar cal = DatatypeConverter.parseDateTime(value);
                        Date date = cal.getTime();
                        manager.setProperty(keyName, date);

                        System.out.println("Valor " + keyName + ": " + date);
                    } else {
                        manager.setProperty(keyName, value);
                        System.out.println("Valor " + keyName + ": "
                                + value);
                    }
                    break;
                case VALUE_TRUE:
                    manager.setProperty(keyName, true);
                    System.out.println("Valor " + keyName + ": " + true);
                    break;
            }
        } while (evt != Event.END_OBJECT);

        return manager.getBean();
    }

    private static Collection parseArray(JsonParser parser,
            CollectionManager manager) {

        Event evt;
        Class elementClass = manager.getElementClass();
        int pos = 0;

        do {
            evt = parser.next();

            switch (evt) {
                case START_OBJECT:
                    //The most expected case of all in an array
                    System.out.println("Nuevo Objeto: " + elementClass.getSimpleName()
                            + " del arreglo en la pos: " + pos);

                    BeanManager objectManager = new BeanManager(elementClass);

                    Object result = parseObject(parser, objectManager);

                    manager.addElement(result);
                    pos++;

                    break;

//                case START_ARRAY:
//                    //Only possible if array of arrays in json which is unlikely
//                    //Also if in the pojo there is a recursive collection diferent
//                    //from arraylist then there will be some problems.
//                    
////                    System.out.println("Nueva Colleccion de clase: " + 
////                            elementClass.getSimpleName() + "dentro de otro "
////                            + "arreglo en la pos: " + pos);
////                    
////                    CollectionManager<T> collectionManager = new 
////                        CollectionManager<>(new ArrayList<>(), elementClass);
////                    
////                    Collection<T> collection = parseArray(parser, collectionManager);
////                    manager.addElement(collection);
////                    pos++;
//                    break;
                //if it is an array of booleans.... but no.
                case VALUE_FALSE:
                    System.out.println("Agregando false al arreglo en la pos: "
                            + pos);

                    manager.addElement(false);
                    pos++;
                    break;

                case VALUE_NULL:
                    System.out.println("Agregando null al arreglo en la pos: "
                            + pos);

                    manager.addElement(null);
                    pos++;
                    break;

                case VALUE_NUMBER:
                    //double or int
                    if (parser.isIntegralNumber()) {
                        int value = parser.getInt();
                        System.out.println("Agregando " + value + " al arreglo en la pos: "
                                + pos);

                        manager.addElement(value);
                        pos++;
                    } else {
                        double value = parser.getBigDecimal().doubleValue();
                        System.out.println("Agregando " + value + " al arreglo en la pos: "
                                + pos);

                        manager.addElement(value);
                        pos++;
                    }
                    break;

                case VALUE_STRING:
                    String value = parser.getString();

                    //Validate if the field is of type Date
                    if (manager.getElementClass() == Date.class) {
                        Calendar cal = DatatypeConverter.parseDateTime(value);
                        Date date = cal.getTime();

                        System.out.println("Agregando " + date + " al arreglo en la pos: "
                                + pos);

                        manager.addElement(date);
                        pos++;
                    } else {
                        System.out.println("Agregando " + value + " al arreglo en la pos: "
                                + pos);

                        manager.addElement(value);
                        pos++;
                    }
                    break;
                case VALUE_TRUE:
                    System.out.println("Agregando true al arreglo en la pos: "
                            + pos);

                    manager.addElement(true);
                    pos++;
                    break;
            }
        } while (evt != Event.END_ARRAY);

        return manager.getCollection();
    }

    public static String stringify(Object data) {
        StringWriter writer = new StringWriter();
        //Multiple objects has to be in a collection, not an array
        try (JsonGenerator generator = Json.createGenerator(writer)) {

            System.out.println("------------------Stringify Start--------------------");
            //Multiple objects has to be in a collection, not an array
            if (data instanceof Collection) {
                
                CollectionManager collectionManager =
                        new CollectionManager((Collection)data, data.getClass());

                System.out.println("Nueva Colleccion [] de clase: "
                        + data.getClass().getSimpleName());

                generator.writeStartArray();

                stringifyArray(generator, collectionManager);

                generator.writeEnd();
                
            } else {
                //A regular object
                BeanManager manager = new BeanManager(data);

                System.out.println("Nuevo Objecto {}: " + data.getClass().getSimpleName());
                generator.writeStartObject();

                stringifyObject(generator, manager);

                generator.writeEnd();
            }

            System.out.println("------------------Stringify End--------------------");
        }

        return writer.toString();
    }

    private static void stringifyObject(JsonGenerator generator, BeanManager manager) {

        String keyName;

        Field[] fields = manager.getFields();

        //Read every field and transform  it to a json property string
        for (Field field : fields) {
            Class fieldType = manager.getType(field.getName());
            keyName = field.getName();
            Object value = manager.getProperty(keyName);

            System.out.println("KeyName: " + keyName);
            System.out.println("Valor " + keyName + ": " + value);

            if (value == null) {
                //Set to null the property
                generator.writeNull(keyName);
                continue;
            }
            //Is a String
            if (fieldType == String.class) {
                generator.write(keyName, String.valueOf(value));
            } //Is a Date
            else if (fieldType == Date.class) {
                String date = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT
                        .format(value);

                generator.write(keyName, date);

            } //Is a integer
            else if (fieldType == Integer.class || fieldType == Integer.TYPE) {
                generator.write(keyName, (int) value);
            } //Is a double
            else if (fieldType == Double.class || fieldType == Double.TYPE) {
                generator.write(keyName, (double) value);
            } //Is boolean
            else if (fieldType == Boolean.class || fieldType == Boolean.TYPE) {
                generator.write(keyName, (boolean) value);
            } //Is a collection
            else if (value instanceof Collection) {
                Class elementClass = manager.getCollectionElementType(keyName);

                System.out.println("Nueva Colleccion [] de clase: "
                        + elementClass.getSimpleName());

                generator.writeStartArray(keyName);

                //Create new collection manager with the given class
                CollectionManager collectionManager
                        = new CollectionManager((Collection) value, elementClass);
                
                stringifyArray(generator, collectionManager);

                generator.writeEnd();
            } else {
                //Is a object... probably
                BeanManager objectManager = new BeanManager(value);

                System.out.println("Nuevo Objecto {}: " + value.getClass().getSimpleName());
                generator.writeStartObject(keyName);

                stringifyObject(generator, objectManager);

                generator.writeEnd();
            }
        }

    }

    public static void stringifyArray(JsonGenerator generator, CollectionManager manager) {

        //Get size of the array to start stringifying elements
        //Note that the elements of this array has to be of the same class
        Collection collection = manager.getCollection();
        Class elementType = manager.getElementClass();

        System.out.println("Coleccion de tamanio " + collection.size());

        int i = 0;
        //Read every element and transform  it to a json array element string
        for (Iterator it = collection.iterator(); it.hasNext();) {
            Object element = it.next();

            System.out.println("Elemento: [" + i + "], Valor: " + element);

            if (element == null) {
                //Set to null the property
                generator.writeNull();
                continue;
            }

            //Is a String
            if (elementType == String.class) {
                generator.write(String.valueOf(element));
            } //Is a Date
            else if (elementType == Date.class) {
                String date = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT
                        .format(element);

                generator.write(date);

            } //Is a integer
            else if (elementType == Integer.class || elementType == Integer.TYPE) {
                generator.write((int) element);
            } //Is a double
            else if (elementType == Double.class || elementType == Double.TYPE) {
                generator.write((double) element);
            } //Is boolean
            else if (elementType == Boolean.class || elementType == Boolean.TYPE) {
                generator.write((boolean) element);
                
            } //Is a collection (Not implemented)
            else if (element instanceof Collection) {
//                Class elementClass = manager.getCollectionElementType(keyName);
//
//                System.out.println("Nueva Colleccion [] de clase: "
//                        + elementClass.getSimpleName());
//
//                generator.writeStartArray(keyName);
//
//                //Create new collection manager with the given class
//                CollectionManager collectionManager
//                        = new CollectionManager((Collection) value, elementClass);
//
//                generator.writeEnd();
            } else {
                //Is a object... probably
                BeanManager objectManager = new BeanManager(element);

                System.out.println("Nuevo Objecto {}: " + element.getClass().getSimpleName());
                generator.writeStartObject();

                stringifyObject(generator, objectManager);

                generator.writeEnd();
            }
        }
    }

    public static void main(String[] args) {
        TestPojo tp = new TestPojo();

        tp.setId(1);
        tp.setName("lel");
        tp.setOk(true);
        List<Recurso> recursos = new ArrayList<>();

        recursos.add(new Recurso("hohoho"));
        recursos.add(new Recurso("http://www,com"));
        recursos.add(new Recurso("Relatorio.com"));

        tp.setRecursos(recursos);

        String test = JSON.stringify(new Registro(new Contexto("page"),
                new Proceso("busq-enc"), new Recurso("*.com"), new Usuario("donnie", "pass"), new Date()));

        JSON.parse(test, Registro.class);

        System.out.println(JSON.stringify(tp));

        System.out.println(test);
        
        JSON.parse(JSON.stringify(tp), TestPojo.class);

        System.out.println(JSON.stringify(recursos));
        
        JSON.parse(JSON.stringify(recursos), Recurso.class);
    }
}
