/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Usuario;
import iing.uabc.edu.mx.persistencia.modelo.UsuarioBusqueda;
import iing.uabc.edu.mx.persistencia.modelo.UsuarioRecurso;

/**
 *
 * @author donniestorm
 */
public class TestHibernate {
    
    public static void main(String[] args) {
        
        ServiceDelegateLocator.getInstance().setEntity(Usuario.class);
        Usuario u = (Usuario) ServiceDelegateLocator.getInstance().find(1);
        
        ServiceDelegateLocator.getInstance().setEntity(UsuarioRecurso.class);
        UsuarioRecurso usRec = (UsuarioRecurso) ServiceDelegateLocator.getInstance().find(1);
        
        if(u.equals(usRec.getUsuario())) {
            System.out.println("Iguales!!");
        }
        
        System.out.println(u.getId());
        System.out.println(usRec.getUsuario().getId());
        
        System.out.println(u);
        
        System.out.println(usRec.getUsuario());
    }
}