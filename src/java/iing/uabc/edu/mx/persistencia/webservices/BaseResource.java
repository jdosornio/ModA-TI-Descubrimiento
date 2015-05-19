/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.webservices;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import iing.uabc.edu.mx.negocio.delegate.ServiceDelegateLocator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author donniestorm
 */
@Stateless
@Path("base")
public class BaseResource {

    private static final String ENTITY_PACKAGE = "iing.uabc.edu.mx.persistencia.modelo.";
    private String className;
    
    private void setEntity(String clazz){
        try {
            this.className = ENTITY_PACKAGE + clazz;
            Class claz = Class.forName(className);
            ServiceDelegateLocator.getInstance().setEntity(claz);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @POST    
    @Path("{class}")
    public void saveEntity(@PathParam("class") String clazz, @FormParam("request") String request) {
        setEntity(clazz);
        request = "{\"" + className + "\":" + request + "}";

        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        Object entity = xstream.fromXML(request);
        ServiceDelegateLocator.getInstance().save(entity);
    }

    @GET
    @Path("{class}")
    public String findAll(@PathParam("class") String clazz) {
        setEntity(clazz);
        List entities = ServiceDelegateLocator.getInstance().findAll();
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return xstream.toXML(entities);
    }

    @GET
    @Path("{class}/{id}")
    public String findById(@PathParam("class") String clazz,
            @PathParam("id") Integer id) {
        setEntity(clazz);
        Object entity = ServiceDelegateLocator.getInstance().find(id);
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        return xstream.toXML(entity);
    }

    @PUT
    @Path("{class}/{id}")
    public void updateEntity(@PathParam("class") String clazz,
            @PathParam("id") Integer id, String request) {
        setEntity(clazz);
        request = "{\"" + className + "\":" + request + "}";
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        Object entity = xstream.fromXML(request);
        ServiceDelegateLocator.getInstance().update(entity);
    }
    
    @DELETE
    @Path("{class}/{id}")
    public void deleteEntity(@PathParam("class") String clazz, 
            @PathParam("id")Integer id) {
        setEntity(clazz);
        ServiceDelegateLocator.getInstance().delete(id);
    }

}
