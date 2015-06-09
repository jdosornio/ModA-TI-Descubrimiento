/*
 * Copyright (C) 2015 Instituto de Ingeniería, UABC.
 *
 * This file is part of ModA-TiDescubrimiento.
 *
 * ModA-TiDescubrimiento is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * ModA-TiDescubrimiento is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package iing.uabc.edu.mx.vista.webservices;

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

/**
 * Esta clase es utilizada para ofrecer servicios de persistencia mediante
 * peticiones HTTP. Tiene asignada una acción del acrónimo CRUD a cada verbo
 * HTTP del path del servidor.
 * 
 * - Para realizar una inserción de un objeto, se llama al verbo POST
 * - Para realizar una consulta de una clase, se llama al verbo GET
 * - Para realizar una modificación a un objeto, se llama al verbo PUT
 * - Para realizar una eliminación de un objeto, se llama al verbo DELETE
 * 
 * Cada método es explicado más detalladamente, esto es sólo una descripción
 * general.
 * 
 * @author Jesus Donaldo Osornio Hernández
 * @version 1, 8 Junio 2015
 */
@Stateless
@Path("base")
public class BaseResource {

    /**
     * Esta constante guarda el paquete en donde se encuentran agrupadas las
     * clases entidad de la aplicación (modelo). Sirve para poder realizar la
     * transformación de JSON a objeto java y viceversa de forma fácil.
     */
    private static final String ENTITY_PACKAGE = "iing.uabc.edu.mx.persistencia.modelo.";
    /**
     * Esta cadena almacenará el nombre de la clase que se proporcione para
     * identificar la clase del objeto que se desea registrar, modificar,
     * eliminar o consultar.
     */
    private String className;
    
    /**
     * Este método es utilizado para indicarle al base delegate de la aplicación
     * la clase del objeto que se registrará, modificará, eliminará o consultará
     * a continuación.
     * 
     * Recibe una cadena con el nombre de la clase para después transformarlo en
     * un .class que sea reconocible por el base delegate.
     * 
     * @param clazz la cadena con el nombre relativo de la clase (Sin los
     * paquetes). Ejemplo: para la clase en entidades.Objeto enviar Objeto como
     * parámetro.
     */
    private void setEntity(String clazz){
        try {
            //Obtiene el nombre absoluto de la clase (Con todo y los paquetes)
            this.className = ENTITY_PACKAGE + clazz;
            //Obtiene el .class a partir del nombre
            Class claz = Class.forName(className);
            //Indica al base delegate el .class que utilizará
            ServiceDelegateLocator.getInstance().setEntity(claz);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BaseResource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método es utilizado para registrar un objeto entidad para hacerlo
     * persistente. El método es invocado a través del verbo http POST, el cuál
     * recibe como parámetros en el path el nombre de la clase del objeto a
     * insertar y la cadena JSON que representa los datos del objeto a insertar.
     * 
     * Después de recibir estos parámetros se transforma la cadena JSON al objeto
     * java correspondiente y se hace persistente en la aplicación.
     * 
     * 
     * La forma de llamar a este método a través de AJAX es la siguiente:
     * (Puede variar dependiendo de la implementación)
     * 
     * Ejemplo:
     * 
     * //El objeto entidad a guardar con todos los atributos que no pueden ser
     * //nulos
     * var recurso = {
     *  descUrl: "http://example.com"
     * };
     * 
     * //Se invoca el verbo post del recurso en la página de ModA-TI_Descubrimiento
     * //Se le envía el nombre de la clase al final y se envía también el objeto
     * //entidad transformado en cadena JSON
     * 
     * $.post("http://localhost:8080/ModA-TI_Descubrimiento/webresources/base/Recurso",
     * {request: JSON.stringify(recurso)});
     * 
     * 
     * @param clazz la cadena de texto que representa el nombre de la clase del
     * objeto a registrar. Debe ser enviada al final del url del recurso
     * 
     * @param request la cadena JSON que representa el objeto a registrar y hacer
     * persistente
     */
    @POST    
    @Path("{class}")
    public void saveEntity(@PathParam("class") String clazz, @FormParam("request") String request) {
        //Se indica al base delegate la clase del objeto a registrar
        setEntity(clazz);
        //Se concatena todo lo necesario al JSON para que pueda ser transformado
        //a objeto fácilmente mediante XStream
        request = "{\"" + className + "\":" + request + "}";

        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        //Se transforma la cadena JSON a objeto java
        Object entity = xstream.fromXML(request);
        //Se hace persistente el objeto mediante el base delegate
        ServiceDelegateLocator.getInstance().save(entity);
    }

    /**
     * Este método es utilizado para obtener todos los objetos entidad de una 
     * clase determinada.
     * 
     * El método es invocado a través del verbo http GET, el
     * cuál recibe como parámetros en el path el nombre de la clase de los
     * objetos que se desea obtener.
     * 
     * Después de recibir el parámetro se indica a las capas inferiores la clase
     * que se utilizará para obtener todos los objetos de ella.
     * 
     * La forma de llamar a este método a través de AJAX es la siguiente:
     * (Puede variar dependiendo de la implementación)
     * 
     * Ejemplo:
     * 
     * //Se invoca el verbo get del recurso en la página de ModA-TI_Descubrimiento
     * //Se le envía el nombre de la clase al final.
     * 
     * $.get("http://localhost:8080/ModA-TI_Descubrimiento/webresources/base/Recurso",
     * {}, function(msg) {
     *  //Mostrar info de las entidades obtenidas
     * });
     * 
     * 
     * @param clazz la cadena de texto que representa el nombre de la clase de 
     * los objetos que se desea obtener. Debe ser enviada al final del url del
     * recurso
     * 
     * @return La lista de objetos resultante transformada a cadena JSON
     */
    @GET
    @Path("{class}")
    public String findAll(@PathParam("class") String clazz) {
        setEntity(clazz);
        //Obtener las entidades mediante el base delegate
        List entities = ServiceDelegateLocator.getInstance().findAll();
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //Regresar la lista de objetos transformada en JSON
        return xstream.toXML(entities);
    }

    /**
     * Este método es utilizado para obtener un objeto entidad de una clase
     * determinada y segun el id seleccionado.
     * 
     * El método es invocado a través del verbo http GET, el
     * cuál recibe como parámetros en el path el nombre de la clase del objeto
     * que se desea obtener y el id al que pertenece dicho objeto.
     * 
     * Después de recibir los parámetros se indica a las capas inferiores la clase
     * que se utilizará para obtener el objeto y se usa el id seleccionado para
     * buscar si existe un objeto asociado a ese id.
     * 
     * La forma de llamar a este método a través de AJAX es la siguiente:
     * (Puede variar dependiendo de la implementación)
     * 
     * Ejemplo:
     * 
     * //Se invoca el verbo get del recurso en la página de ModA-TI_Descubrimiento
     * //Se le envía el nombre de la clase al final seguida del id que se desea
     * buscar.
     * 
     * $.get("http://localhost:8080/ModA-TI_Descubrimiento/webresources/base/Recurso/5",
     * {}, function(msg) {
     *  //Mostrar info del objeto obtenido
     * });
     * 
     * 
     * @param clazz la cadena de texto que representa el nombre de la clase del
     * objeto que se desea obtener. Debe ser enviada al final del url del
     * recurso
     * 
     * @param id el id que se quiere buscar para obtener el objeto, debe ser
     * enviado al final del url del recurso, justo después del nombre de la clase
     * 
     * @return El objeto resultante si se encontró transformado a cadena JSON
     */
    @GET
    @Path("{class}/{id}")
    public String findById(@PathParam("class") String clazz,
            @PathParam("id") Integer id) {
        setEntity(clazz);
        Object entity = ServiceDelegateLocator.getInstance().find(id);
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //Regresa el objeto resultante de la búsqueda en cadena JSON
        return xstream.toXML(entity);
    }

    /**
     * Este método es utilizado para actualizar un objeto entidad ya existente en
     * la persistencia. El método es invocado a través del verbo http PUT, el
     * cuál recibe como parámetros en el path el nombre de la clase del objeto a
     * actualizar, el id al que pertenece el objeto a actualizar y la cadena
     * JSON que representa los datos del objeto a actualizar.
     * 
     * Después de recibir estos parámetros se transforma la cadena JSON al objeto
     * java correspondiente y se modifica en la persistencia de la aplicación.
     * 
     * 
     * La forma de llamar a este método a través de AJAX es la siguiente:
     * (Puede variar dependiendo de la implementación)
     * 
     * Ejemplo:
     * 
     * //El objeto entidad a actualizar con todos los atributos que no pueden ser
     * //nulos
     * var recurso = {
     *  descUrl: "http://example2.com"
     * };
     * 
     * //Se invoca el verbo post del recurso en la página de ModA-TI_Descubrimiento
     * //Se le envía el nombre de la clase al final y se envía también el objeto
     * //entidad transformado en cadena JSON
     * 
     * $.ajax({type: "PUT",
     * url:"http://localhost:8080/ModA-TI_Descubrimiento/webresources/base/Recurso",
     * data: JSON.stringify(recurso)});
     * 
     * 
     * @param clazz la cadena de texto que representa el nombre de la clase del
     * objeto a actualizar. Debe ser enviada al final del url del recurso
     * 
     * @param id el id de la entidad a actualizar
     * 
     * @param request la cadena JSON que representa el objeto a actualizar en la
     * persistencia
     */
    @PUT
    @Path("{class}/{id}")
    public void updateEntity(@PathParam("class") String clazz,
            @PathParam("id") Integer id, String request) {
        setEntity(clazz);
        //Se prepara la cadena JSON para ser transformada en objeto java
        request = "{\"" + className + "\":" + request + "}";
        XStream xstream = new XStream(new JettisonMappedXmlDriver());
        //Se transforma la cadena JSON a objeto java
        Object entity = xstream.fromXML(request);
        //Se actualiza el objeto java en la persistencia
        ServiceDelegateLocator.getInstance().update(entity);
    }
    
    /**
     * Este método es utilizado para eliminar un objeto entidad de una clase
     * determinada de la persistencia.
     * 
     * El método es invocado a través del verbo http DELETE, el cuál recibe como
     * parámetros en el path el nombre de la clase del objeto que se desea
     * eliminar y el id del objeto.
     * 
     * Después de recibir los parámetros se indica a las capas inferiores la clase
     * del objeto a eliminar y se elimina por medio del id ingresado
     * 
     * La forma de llamar a este método a través de AJAX es la siguiente:
     * (Puede variar dependiendo de la implementación)
     * 
     * Ejemplo:
     * 
     * //Se invoca el verbo delete del recurso en la página de ModA-TI_Descubrimiento
     * //Se le envía el nombre de la clase al final, seguida de el id del objeto.
     * 
     * $.ajax({type: "DELETE",
     * url:"http://localhost:8080/ModA-TI_Descubrimiento/webresources/base/Recurso/7"});
     * 
     * 
     * @param clazz la cadena de texto que representa el nombre de la clase de 
     * del objeto que se desea eliminar. Debe ser enviada al final del url del
     * recurso.
     * 
     * @param id el id del objeto a eliminar
     */
    @DELETE
    @Path("{class}/{id}")
    public void deleteEntity(@PathParam("class") String clazz, 
            @PathParam("id")Integer id) {
        setEntity(clazz);
        ServiceDelegateLocator.getInstance().delete(id);
    }

}