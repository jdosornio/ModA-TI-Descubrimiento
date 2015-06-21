/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.persistencia.modelo.Registro;
import iing.uabc.edu.mx.persistencia.modelo.RegistroBusqueda;
import iing.uabc.edu.mx.persistencia.util.JSON;


/**
 *
 * @author donniestorm
 */
public class TestJSONParser {

    public static void main(String[] args) {
        
        RegistroBusqueda rb = JSON.parse("{"
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
                + "\"usuario:\" {"
                + "\"id\": 4,"
                + "\"user\": \"admin\","
                + "\"pass\": \"admin\""
                + "},"
                + "\"fecha\": \"2010-01-01T12:00:00Z\"" 
                + "}"
                + "}", RegistroBusqueda.class);
        
        System.out.println(rb.getId());
        System.out.println(rb.getBusqueda().getDescTermino());
    }
}