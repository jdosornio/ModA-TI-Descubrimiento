package test;


import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.UsuarioRecurso;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author donniestorm
 */
public class Test {
    
    public static void main(String[] args) {
        ServiceDelegateLocator.getInstance().setEntity(UsuarioRecurso.class);
        ServiceDelegateLocator.getInstance().findAll();
    }
}