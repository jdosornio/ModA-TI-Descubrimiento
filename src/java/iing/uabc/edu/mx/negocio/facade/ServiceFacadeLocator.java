/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.negocio.facade;

/**
 *
 * @author Alex
 */
public class ServiceFacadeLocator {
    
    private static BaseFACADE facade;
    
    public static BaseFACADE getInstance(){
        if(facade == null){
            facade = new BaseFACADE();
        }
        return facade;
    }
}
