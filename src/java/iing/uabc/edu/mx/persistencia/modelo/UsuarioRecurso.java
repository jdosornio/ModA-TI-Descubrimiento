package iing.uabc.edu.mx.persistencia.modelo;
// Generated Jun 8, 2015 9:23:00 PM by Hibernate Tools 4.3.1



/**
 * UsuarioRecurso generated by hbm2java
 */
public class UsuarioRecurso  implements java.io.Serializable {


     private int id;
     private Recurso recurso;
     private Usuario usuario;
     private int visitas;

    public UsuarioRecurso() {
    }

    public UsuarioRecurso(int id, Recurso recurso, Usuario usuario) {
       this.id = id;
       this.recurso = recurso;
       this.usuario = usuario;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
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

    public int getVisitas() {
        return visitas;
    }
    
    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }


}


