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
@Path("/base")
public class BaseResource {

    private static final String ENTITY_PACKAGE = "iing.uabc.edu.mx.persistencia.modelo.";

    @POST
    public void saveEntity(@FormParam("request") String request) {

        try {
            //Get json object
            JSONObject jsonObj = new JSONObject(request);

            //Attribute class contains the class object
            String className = jsonObj.getString("class");

            className = ENTITY_PACKAGE + className;

            //entity attribute contains the entity in json to be saved
            String jsonEntity = jsonObj.getString("entity");

            //Prepare json string to be parsed into an entity object
            jsonEntity = "{\"" + className + "\":" + jsonEntity + "}";

            XStream xstream = new XStream(new JettisonMappedXmlDriver());

            //Obtain the object and the class for that object
            Object entity = xstream.fromXML(jsonEntity);
            Class clazz = Class.forName(className);

            ServiceDelegateLocator.getInstance().setEntity(clazz);
            ServiceDelegateLocator.getInstance().save(entity);

        } catch (JSONException | ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    public String findEntity(@QueryParam("request") String request) {
        String response = "";

        try {
            //Get json object
            JSONObject jsonObj = new JSONObject(request);

            //Attribute class contains the class object
            String className = jsonObj.getString("class");

            className = ENTITY_PACKAGE + className;

            //Obtain the class of the object
            Class clazz = Class.forName(className);

            switch (jsonObj.getString("action")) {

                case "findAll":
                    response = findAll(clazz);
                    break;

                case "findById":
                    int id = jsonObj.getInt("id");
                    response = findById(clazz, id);

                    break;

                default:
                    System.out.println("No action for that");
            }

        } catch (JSONException | ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return response;
    }

    private String findAll(Class clazz) {

        ServiceDelegateLocator.getInstance().setEntity(clazz);
        List entities = ServiceDelegateLocator.getInstance().findAll();

        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        System.out.println(xstream.toXML(entities));

        return xstream.toXML(entities);
    }

    private String findById(Class clazz, int id) {

        ServiceDelegateLocator.getInstance().setEntity(clazz);
        Object entity = ServiceDelegateLocator.getInstance().find(id);

        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        System.out.println(xstream.toXML(entity));

        return xstream.toXML(entity);
    }

    @PUT
    @Path("{id}")
    public void updateEntity(@PathParam("id") Integer id, String request) {

        try {
            //Get json object
            JSONObject jsonObj = new JSONObject(request);

            //Attribute class contains the class object
            String className = jsonObj.getString("class");

            className = ENTITY_PACKAGE + className;

            //entity attribute contains the entity in json to be updated
            String jsonEntity = jsonObj.getString("entity");

            //Prepare json string to be parsed into an entity object
            jsonEntity = "{\"" + className + "\":" + jsonEntity + "}";

            XStream xstream = new XStream(new JettisonMappedXmlDriver());

            //Obtain the object and the class for that object
            Object entity = xstream.fromXML(jsonEntity);
            Class clazz = Class.forName(className);

            ServiceDelegateLocator.getInstance().setEntity(clazz);
            ServiceDelegateLocator.getInstance().update(entity);

        } catch (JSONException | ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @DELETE
    @Path("{id},{className}")
    public void deleteEntity(@PathParam("id")Integer id, @PathParam("className")
            String className) {
        
        
        try {
            className = ENTITY_PACKAGE + className;
            Class clazz = Class.forName(className);
            
            ServiceDelegateLocator.getInstance().setEntity(clazz);
            //Delete object
            ServiceDelegateLocator.getInstance().delete(id);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
