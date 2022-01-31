package practica7.alexandrorodriguez.iesseveroochoa.net.model;

import android.net.Uri;

import com.google.firebase.firestore.GeoPoint;

public class Empresa {
    /**ATRIBUTOS**/
    private String nombre;
    private String direccion;
    private String telefono;
    private GeoPoint localizacion;

    /**CONSTRUCTORES**/
    public Empresa(String nombre, String direccion, String telefono, GeoPoint localizacion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.localizacion = localizacion;
    }
    public Empresa() {}

    /**GETTERS & SETTERS**/
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public GeoPoint getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(GeoPoint localizacion) {
        this.localizacion = localizacion;
    }

    /**OTROS MÉTODOS**/
    public Uri getUriTelefono(){
        return Uri.parse("tel:"+telefono);
    }
    public Uri getUriLocalizacion() {
        return Uri.parse("geo:"+localizacion.getLatitude()+","+
                localizacion.getLongitude());
    }
}
