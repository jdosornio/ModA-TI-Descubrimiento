package iing.uabc.edu.mx.persistencia.modelo;
// Generated Jun 8, 2015 9:23:00 PM by Hibernate Tools 4.3.1



/**
 * UsuarioBusqueda generated by hbm2java
 */
public class UsuarioBusqueda  implements java.io.Serializable {


     private int id;
     private Busqueda busqueda;
     private Usuario usuario;
     private int veces;
     
    public UsuarioBusqueda() {
    }

    public UsuarioBusqueda(int id, Busqueda busqueda, Usuario usuario) {
       this.id = id;
       this.busqueda = busqueda;
       this.usuario = usuario;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Busqueda getBusqueda() {
        return this.busqueda;
    }
    
    public void setBusqueda(Busqueda busqueda) {
        this.busqueda = busqueda;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public int getVeces() {
        return veces;
    }
    
    public void setVeces(int veces) {
        this.veces = veces;
    }

}