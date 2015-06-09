package iing.uabc.edu.mx.persistencia.modelo;
// Generated Jun 8, 2015 9:23:00 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Recurso generated by hbm2java
 */
public class Recurso  implements java.io.Serializable {


     private Integer id;
     private String descUrl;
     private Set registros = new HashSet(0);
     private Set usuarioRecursos = new HashSet(0);

    public Recurso() {
    }

	
    public Recurso(String descUrl) {
        this.descUrl = descUrl;
    }
    public Recurso(String descUrl, Set registros, Set usuarioRecursos) {
       this.descUrl = descUrl;
       this.registros = registros;
       this.usuarioRecursos = usuarioRecursos;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescUrl() {
        return this.descUrl;
    }
    
    public void setDescUrl(String descUrl) {
        this.descUrl = descUrl;
    }
    public Set getRegistros() {
        return this.registros;
    }
    
    public void setRegistros(Set registros) {
        this.registros = registros;
    }
    public Set getUsuarioRecursos() {
        return this.usuarioRecursos;
    }
    
    public void setUsuarioRecursos(Set usuarioRecursos) {
        this.usuarioRecursos = usuarioRecursos;
    }




}


