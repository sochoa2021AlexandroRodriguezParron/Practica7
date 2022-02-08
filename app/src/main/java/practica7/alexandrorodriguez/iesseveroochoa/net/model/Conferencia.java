package practica7.alexandrorodriguez.iesseveroochoa.net.model;

import com.google.firebase.Timestamp;

public class Conferencia {
    /**ATRIBUTOS**/
    private String id;
    private boolean enCurso;
    private Timestamp fecha;
    private String horario;
    private String nombre;
    private int plazas;
    private String ponente;
    private String sala;
    /**CONSTRUCTORES**/
    public Conferencia(String id, boolean enCurso, Timestamp fecha, String horario, String nombre, int plazas, String ponente, String sala) {
        this.id = id;
        this.enCurso = enCurso;
        this.fecha = fecha;
        this.horario = horario;
        this.nombre = nombre;
        this.plazas = plazas;
        this.ponente = ponente;
        this.sala = sala;
    }
    public Conferencia() {}
    /**GETTERS & SETTERS**/
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean isEnCurso() {
        return enCurso;
    }
    public void setEnCurso(boolean enCurso) {
        this.enCurso = enCurso;
    }
    public Timestamp getFecha() {
        return fecha;
    }
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getPlazas() {
        return plazas;
    }
    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }
    public String getPonente() {
        return ponente;
    }
    public void setPonente(String ponente) {
        this.ponente = ponente;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
    @Override
    public String toString() {
        return "Conferencia{" +
                "id='" + id + '\'' +
                ", enCurso=" + enCurso +
                ", fecha=" + fecha +
                ", horario='" + horario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", plazas=" + plazas +
                ", ponente='" + ponente + '\'' +
                ", sala='" + sala + '\'' +
                '}';
    }
}
