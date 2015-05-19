/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.negocio.facade;

import iing.uabc.edu.mx.persistencia.dao.ServiceDaoLocator;
import java.util.List;

/**
 *
 * @author Alex
 */
public class BaseFACADE {
    
    public BaseFACADE(){        
    }
    
    public void setEntity(Class clazz){
        ServiceDaoLocator.getInstance().setEntity(clazz);
    }
    
    public void save(Object o){
        ServiceDaoLocator.getInstance().saveOrUpdate(o);
    }
    
    public void update(Object o){
        ServiceDaoLocator.getInstance().saveOrUpdate(o);        
    }
    
    public void delete(Object o){
        ServiceDaoLocator.getInstance().delete(o);
    }
    
    public Object find(int id){
        return ServiceDaoLocator.getInstance().find(id);
    }
    
    public List<Object> findAll(){
        return ServiceDaoLocator.getInstance().findAll();
    }
}
