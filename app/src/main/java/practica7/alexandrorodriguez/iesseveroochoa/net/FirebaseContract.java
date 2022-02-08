package practica7.alexandrorodriguez.iesseveroochoa.net;

public class FirebaseContract {
    public static class EmpresaEntry{
        public static final String COLLECTION_NAME = "empresas";
        public static final String ID= "ochoa";
        public static final String NOMBRE= "nombre";
        public static final String DIRECCION= "direccion";
        public static final String TELEFONO= "telefono";
        public static final String LOCALIZACION= "localizacion";
    }
    public static class ConferenciaEntry{
        public static final String COLLECTION_NAME = "conferencias";
        public static final String ID= "id";
        public static final String ENCURSO = "encurso";
        public static final String FECHA = "fecha";
        public static final String HORARIO = "horario";
        public static final String NOMBRE = "nombre";
        public static final String PLAZAS = "plazas";
        public static final String PONENTE = "ponente";
        public static final String SALA = "sala";
    }
}
