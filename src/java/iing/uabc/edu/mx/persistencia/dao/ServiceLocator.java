package iing.uabc.edu.mx.persistencia.dao;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Alex
 */
public class ServiceLocator {
    
    private static BaseDAO baseDAO;    
    
    public static BaseDAO getInstance(){
        if(baseDAO == null){
            baseDAO = new BaseDAO();
        }
        return baseDAO;
    }
    
}
