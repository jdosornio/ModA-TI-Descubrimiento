package iing.uabc.edu.mx.persistencia.dao;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import iing.uabc.edu.mx.persistencia.util.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;

/**
 *
 * @author Alex
 * @param <T>
 */
public class BaseDAO<T> implements InterfaceDAO<T> {
    
    protected Class<T> clazz;
    
    public BaseDAO(){
    }
    
    public BaseDAO(Class<T> clazz){
        this.clazz = clazz;
    }
    
    public void setEntity(Class<T> clazz){
        this.clazz = clazz;
    }
    
    @Override
    public void saveOrUpdate(T t) {
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().saveOrUpdate(t);
//            HibernateUtil.commitTransaction();
            System.out.println("Guardado exitoso");
        }catch(HibernateException e){
            HibernateUtil.rollbackTransaction();
        }finally{
            HibernateUtil.closeSession();
        }
    }

    @Override
    public T find(int id) {
        T t = null;
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            t = (T) HibernateUtil.getSession().load(clazz, id);
//            HibernateUtil.commitTransaction();
        }catch(HibernateException e){
            HibernateUtil.rollbackTransaction();
        }finally{
            HibernateUtil.closeSession();
        }
        return t;
    }

    @Override
    public List<T> findAll() {
        List<T> ts = null;
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().createCriteria("from " + clazz.getName());
//            HibernateUtil.commitTransaction();
        }catch(HibernateException e){
            HibernateUtil.rollbackTransaction();
        }finally{
            HibernateUtil.closeSession();
        }
        return ts;
    }

    @Override
    public void delete(T t) {
        try{
            HibernateUtil.getSession();
            HibernateUtil.beingTransaccion();
            HibernateUtil.getSession().delete(t);
            System.out.println("Eliminado exitoso");
//            HibernateUtil.commitTransaction();
        }catch(HibernateException e){
            HibernateUtil.rollbackTransaction();
        }finally{
            HibernateUtil.closeSession();
        }
    }
    
}
