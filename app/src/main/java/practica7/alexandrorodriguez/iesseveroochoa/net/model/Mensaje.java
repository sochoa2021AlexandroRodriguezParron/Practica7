package practica7.alexandrorodriguez.iesseveroochoa.net.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Mensaje {
    /**ATRIBUTOS**/
    private String usuario;
    private String body;
    //@ServerTimestamp permite que sea el servidor el que asigne la hora al crear el documento
    @ServerTimestamp
    private Date fechaCreacion;

    /**CONSTRUCTORES**/
    public Mensaje() {
    }
    public Mensaje(String usuario, String body) {
        this.usuario = usuario;
        this.body = body;
    }
    public Mensaje(String usuario, String body, Date fechaCreacion) {
        this.usuario = usuario;
        this.body = body;
        this.fechaCreacion = fechaCreacion;
    }
    /**GETTERS & SETTERS**/
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return usuario+"=>"+body;
    }
}
