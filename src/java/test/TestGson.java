/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;

/**
 *
 * @author donniestorm
 */
public class TestGson {
    
    public static void main(String[] args) {
        Gson gson = new Gson();
        
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        Recurso r = (Recurso) ServiceDelegateLocator.getInstance().find(1);
        
        System.out.println(gson.toJson(r));
    }
}
