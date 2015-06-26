/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.modelo.RegistroBusqueda;
import iing.uabc.edu.mx.persistencia.util.JSON;
import java.util.ArrayList;


/**
 *
 * @author donniestorm
 */
public class TestJSONParser {

    public static void main(String[] args) {
        
        //Object test
        System.out.println("Test Object 1");
        System.out.println("------------------");
        RegistroBusqueda rb = (RegistroBusqueda) JSON.parse("{"
                + "\"id\": 1,"
                + "\"busqueda\": {"
                + "\"id\": 1,"
                + "\"descTermino\": \"Transformar cadena json a Java\""
                + "},"
                + "\"registro\": {"
                + "\"id\": 3,"
                + "\"version\": 0,"
                + "\"contexto\": {"
                + "\"id\": 1,"
                + "\"desc\": \"page\""
                + "},"
                + "\"proceso\": {"
                + "\"id\": 7,"
                + "\"desc\": \"busq-enc\""
                + "},"
                + "\"recurso\": {"
                + "\"id\": 12,"
                + "\"descUrl\": \"http://www.example.com/wp-admin/upgrade.php\"" 
                + "},"
                + "\"usuario\": {"
                + "\"id\": 4,"
                + "\"user\": \"admin\","
                + "\"pass\": \"admin\""
                + "},"
                + "\"fecha\": \"2015-06-21T07:58:22.662Z\"" 
                + "}"
                + "}", RegistroBusqueda.class);
        
        System.out.println("Id del objeto: " + rb.getId());
        System.out.println("Desc Termino de la busqueda: " + rb.getBusqueda().getDescTermino());
        System.out.println("Fecha del registro: " + rb.getRegistro().getFecha());
        System.out.println("Proceso del registro: "+ rb.getRegistro().getProceso());
        System.out.println("Url del recurso: "+ rb.getRegistro().getRecurso().getDescUrl());
        System.out.println("User del Usuario: " + rb.getRegistro().getUsuario().getUser());
        System.out.println("Version del registro: " + rb.getRegistro().getVersion());
        
        System.out.println("---------------------");
        System.out.println("Array test 1");
        //Array test
        ArrayList<Recurso> recursos = (ArrayList<Recurso>) JSON.parse("["
                + "{\"id\": 1, \"descUrl\": \"http://www.url.com\"},"
                + "{\"id\": 2, \"descUrl\": \"http://www.sega.com\"},"
                + "{\"descUrl\": \"http://127.0.0.1\"},"
                + "{},"
                + "{\"id\": 4}]", Recurso.class);
        
        
        System.out.println("----------------");
        for(Recurso recurso : recursos) {
            System.out.println("Id: " + recurso.getId());
            System.out.println("Url: " + recurso.getDescUrl());
        }
        
        System.out.println("-----------------");
        System.out.println("Object test 2");
        //Object test 2
        TestPojo pj = (TestPojo) JSON.parse("{"
                + "\"id\": 3,"
                + "\"name\": \"Prueba con este pojo que no existe\","
                + "\"recursos\": ["
                + "{\"id\": 3, \"descUrl\": \"www.unaurl.com\"},"
                + "{\"id\": 5, \"descUrl\": \"http://www.otraurl.com\"},"
                + "{\"id\": 7, \"descUrl\": \"http://www.unaurlmas.com\"}"
                + "]"
                + "}", TestPojo.class);
        
        System.out.println("Nombre del TestPojo: " + pj.getName());
        System.out.println("Url del recurso del TestPojo en la posicion 1: " + 
                pj.getRecursos().get(1).getDescUrl());
        
        //Stringifier
        System.out.println("Transforming objects back to json String");
        
        System.out.println("Registro Busqueda: " + JSON.stringify(rb));
        //Not working, have to fix it
//        System.out.println("List de Recursos: " + JSON.stringify(recursos));
        System.out.println("TestPojo: " + JSON.stringify(pj));
        
        //Not working either because of the date format when parsed
        //JSON.parse(JSON.stringify(rb), RegistroBusqueda.class);
        JSON.parse(JSON.stringify(pj), TestPojo.class);
    }
}