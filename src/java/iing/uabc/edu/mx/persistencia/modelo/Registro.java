package iing.uabc.edu.mx.persistencia.modelo;
// Generated Jun 8, 2015 9:23:00 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.Objects;

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

    public Registro() {
    }

    public Registro(Contexto contexto, Proceso proceso, Recurso recurso, Usuario usuario, Date fecha) {
        this.contexto = contexto;
        this.proceso = proceso;
        this.recurso = recurso;
        this.usuario = usuario;
        this.fecha = fecha;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + this.version;
        hash = 83 * hash + Objects.hashCode(this.contexto);
        hash = 83 * hash + Objects.hashCode(this.proceso);
        hash = 83 * hash + Objects.hashCode(this.recurso);
        hash = 83 * hash + Objects.hashCode(this.usuario);
        hash = 83 * hash + Objects.hashCode(this.fecha);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Registro other = (Registro) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.version != other.version) {
            return false;
        }
        if (!Objects.equals(this.contexto, other.contexto)) {
            return false;
        }
        if (!Objects.equals(this.proceso, other.proceso)) {
            return false;
        }
        if (!Objects.equals(this.recurso, other.recurso)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return Objects.equals(this.fecha, other.fecha);
    }

}