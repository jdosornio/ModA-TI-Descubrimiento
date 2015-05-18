/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.negocio.delegate;

import iing.uabc.edu.mx.persistencia.dao.ServiceLocator;
import java.util.List;

/**
 *
 * @author Alex
 */
public class BaseDELEGATE {
    
    public BaseDELEGATE(){        
    }
    
    public void setEntity(Class clazz){
        ServiceLocator.getInstance().setEntity(clazz);
    }
    
    public void save(Object o){
        ServiceLocator.getInstance().saveOrUpdate(o);
    }
    
    public void update(Object o){
        ServiceLocator.getInstance().saveOrUpdate(o);        
    }
    
    public void delete(Object o){
        ServiceLocator.getInstance().delete(o);
    }
    
    public Object find(int id){
        return ServiceLocator.getInstance().find(id);
    }
    
    public List<Object> findAll(){
        return ServiceLocator.getInstance().findAll();
    }
}
