package mx.edumat.modati.servicios.modelo;
// Generated 18/05/2015 06:41:42 AM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Registro generated by hbm2java
 */
public class Registro  implements java.io.Serializable {


     private Integer id;
     private int version;
     private Contexto contexto;
     private Proceso proceso;
     private Recurso recurso;
     private Usuario usuario;
     private Date fecha;
     private Set busquedas = new HashSet(0);

    public Registro() {
    }

	
    public Registro(Contexto contexto, Proceso proceso, Recurso recurso, Usuario usuario, Date fecha) {
        this.contexto = contexto;
        this.proceso = proceso;
        this.recurso = recurso;
        this.usuario = usuario;
        this.fecha = fecha;
    }
    public Registro(Contexto contexto, Proceso proceso, Recurso recurso, Usuario usuario, Date fecha, Set busquedas) {
       this.contexto = contexto;
       this.proceso = proceso;
       this.recurso = recurso;
       this.usuario = usuario;
       this.fecha = fecha;
       this.busquedas = busquedas;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public int getVersion() {
        return this.version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    public Contexto getContexto() {
        return this.contexto;
    }
    
    public void setContexto(Contexto contexto) {
        this.contexto = contexto;
    }
    public Proceso getProceso() {
        return this.proceso;
    }
    
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
    public Recurso getRecurso() {
        return this.recurso;
    }
    
    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Set getBusquedas() {
        return this.busquedas;
    }
    
    public void setBusquedas(Set busquedas) {
        this.busquedas = busquedas;
    }




}


