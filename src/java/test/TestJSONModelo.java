/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.modelo.Usuario;
import iing.uabc.edu.mx.persistencia.modelo.UsuarioRecurso;
import iing.uabc.edu.mx.persistencia.util.JSON;


/**
 *
 * @author donniestorm
 */
public class TestJSONModelo {
    
    public static void main(String[] args) {
        
        String jsonRecurso = "{\"descUrl\": \"https://www.google.com.mx\"}";
        String jsonUsuario = "{\"user\":\"donniestorm\", \"pass\": \"eehehehhehe\"}";
        String jsonUsuarioRecurso;
        
        Recurso r = (Recurso) JSON.parse(jsonRecurso, Recurso.class);
        
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        ServiceDelegateLocator.getInstance().save(r);
        
        Usuario u = (Usuario) JSON.parse(jsonUsuario, Usuario.class);
        
        ServiceDelegateLocator.getInstance().setEntity(Usuario.class);
        ServiceDelegateLocator.getInstance().save(u);
        
        jsonUsuarioRecurso = "{\"recurso\": " + JSON.stringify(r) + ", \"usuario\": " + 
                JSON.stringify(u) + ", \"visitas\": 1}";
        
        UsuarioRecurso ur = (UsuarioRecurso) JSON.parse(jsonUsuarioRecurso,
                UsuarioRecurso.class);
        
        ServiceDelegateLocator.getInstance().setEntity(UsuarioRecurso.class);
        ServiceDelegateLocator.getInstance().save(ur);
    }
}
