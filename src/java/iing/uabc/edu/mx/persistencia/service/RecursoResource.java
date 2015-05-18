/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.service;

import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Alex
 */
@Stateless
@Path("recurso")
public class RecursoResource {
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(Recurso recurso){
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        ServiceDelegateLocator.getInstance().save(recurso);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Recurso recurso) {
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        recurso.setId(id);
        ServiceDelegateLocator.getInstance().update(recurso);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        ServiceDelegateLocator.getInstance().delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Recurso find(@PathParam("id") Integer id) {
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        Recurso r = (Recurso) ServiceDelegateLocator.getInstance().find(id);
        return r;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Recurso> findAll() {
        ServiceDelegateLocator.getInstance().setEntity(Recurso.class);
        List<Recurso> lista = new ArrayList<>();
        List<Object> listaObjetos = ServiceDelegateLocator.getInstance().findAll();
        for(int i = 0; i < listaObjetos.size(); i++){
            lista.add((Recurso) listaObjetos.get(i));
        }
        return lista;
    }
}
