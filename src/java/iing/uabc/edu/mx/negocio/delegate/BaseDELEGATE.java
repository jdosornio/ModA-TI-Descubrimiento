/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.negocio.delegate;

import iing.uabc.edu.mx.negocio.facade.ServiceFacadeLocator;
import java.util.List;

/**
 *
 * @author Alex
 */
public class BaseDELEGATE {
    
    public BaseDELEGATE(){        
    }
    
    public void setEntity(Class clazz){
        ServiceFacadeLocator.getInstance().setEntity(clazz);
    }
    
    public void save(Object o){
        ServiceFacadeLocator.getInstance().save(o);
    }
    
    public void update(Object o){
        ServiceFacadeLocator.getInstance().update(o);        
    }
    
    public void delete(int id){
        ServiceFacadeLocator.getInstance().delete(find(id));
    }
    
    public Object find(int id){
        return ServiceFacadeLocator.getInstance().find(id);
    }
    
    public List<Object> findAll(){
        return ServiceFacadeLocator.getInstance().findAll();
    }
}
