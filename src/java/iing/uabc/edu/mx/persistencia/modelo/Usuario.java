package iing.uabc.edu.mx.persistencia.modelo;
// Generated Jun 8, 2015 9:23:00 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private Integer id;
     private String user;
     private String pass;
     private Set<Registro> registros = new HashSet(0);
     private Set<Recurso> recursos = new HashSet(0);
     private Set<Busqueda> busquedas = new HashSet(0);

    public Usuario() {
    }

	
    public Usuario(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }
    
    public Usuario(String user, String pass, Set<Registro> registros, Set<Recurso> recursos, Set<Busqueda> busquedas) {
       this.user = user;
       this.pass = pass;
       this.registros = registros;
       this.recursos = recursos;
       this.busquedas = busquedas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUser() {
        return this.user;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public Set<Registro> getRegistros() {
        return this.registros;
    }
    
    public void setRegistros(Set<Registro> registros) {
        this.registros = registros;
    }

    public Set<Recurso> getRecursos() {
        return this.recursos;
    }
    
    public void setRecursos(Set<Recurso> recursos) {
        this.recursos = recursos;
    }

    public Set<Busqueda> getBusquedas() {
        return this.busquedas;
    }
    
    public void setBusquedas(Set<Busqueda> busquedas) {
        this.busquedas = busquedas;
    }




}


