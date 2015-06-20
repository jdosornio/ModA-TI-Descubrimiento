/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.util.JSON;


/**
 *
 * @author donniestorm
 */
public class TestJSONParser {

    public static void main(String[] args) {
        
        JSON.parse("{}", Recurso.class);
    }
}
