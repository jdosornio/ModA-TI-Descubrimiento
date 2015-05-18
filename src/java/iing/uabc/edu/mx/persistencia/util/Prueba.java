/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import iing.uabc.edu.mx.persistencia.modelo.Usuario;
import org.hibernate.Session;

/**
 *
 * @author Jesus Donaldo
 */
public class Prueba {
    
    public static void main(String[] args) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        
        s.createCriteria(Usuario.class).list();
        
        s.close();
        HibernateUtil.getSessionFactory().close();
    }
}