package test;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Busqueda;
import iing.uabc.edu.mx.persistencia.modelo.Registro;
import iing.uabc.edu.mx.vista.webservices.JsonUtil;

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
        String json = "{\"recurso\":{\"descUrl\":\"www.prueba.com\",\"registros\":null,\"id\":1,\"usuarios\":null},\"fecha\":\"2015-06-16 16:22:42.0\",\"proceso\":{\"registros\":null,\"id\":1,\"desc\":\"Descubrimiento\"},\"busquedas\":[{\"registros\":null,\"id\":1,\"usuarios\":null,\"descTermino\":\"termino1\"}],\"usuario\":{\"pass\":\"123\",\"busquedas\":null,\"registros\":null,\"id\":1,\"recursos\":null,\"user\":\"alex\"},\"id\":1,\"version\":0,\"contexto\":{\"registros\":null,\"id\":1,\"desc\":\"Dentro\"}}";
        String request = "{\"iing.uabc.edu.mx.persistencia.modelo.Registro\":" + json + "}";
        Registro r = (Registro) JsonUtil.fromJson(request);
        System.out.println("Registro: " +
                "\nID: " + r.getId() + 
                "\nVersion: " + r.getVersion() + 
                "\nContexto: " + r.getContexto().getId() + " - " + r.getContexto().getDesc() +
                "\nProceso: " + r.getProceso().getId() + " - " + r.getProceso().getDesc() +
                "\nRecurso: " + r.getRecurso().getId() + " - " + r.getRecurso().getDescUrl()
                );
        for(Busqueda b : r.getBusquedas())
            System.out.println("Busqueda: " + b.getId() + " - " + b.getDescTermino());
        
//        ServiceDelegateLocator.getInstance().setEntity(Registro.class);
//        Registro r = (Registro) ServiceDelegateLocator.getInstance().find(1);
//        String json = JsonUtil.toJson(r);
//        System.out.println(json);
        
//        Registro pruebaRegistro = (Registro) JsonUtil.fromJson(json);
    }
}
