package practica7.alexandrorodriguez.iesseveroochoa.net.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import practica7.alexandrorodriguez.iesseveroochoa.net.ChatAdapter;
import practica7.alexandrorodriguez.iesseveroochoa.net.FirebaseContract;
import practica7.alexandrorodriguez.iesseveroochoa.net.R;
import practica7.alexandrorodriguez.iesseveroochoa.net.model.Conferencia;
import practica7.alexandrorodriguez.iesseveroochoa.net.model.Mensaje;

public class InicioAppActivity extends AppCompatActivity {

    private static final String TAG = "P7";
    FirebaseAuth auth;
    private TextView tv_datosUser;
    private Button bCerrarSession;
    private Spinner sConferencias;
    private ArrayList<Conferencia> listaConferencias;
    private TextView tvConferencia;
    private Conferencia conferenciaActual;
    private EditText et_Texto;
    private ImageButton ib_Envio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        tv_datosUser = findViewById(R.id.tv_datosUser);
        bCerrarSession = findViewById(R.id.bCerrarSession);
        sConferencias = (Spinner)findViewById(R.id.sConferencias);
        tvConferencia = findViewById(R.id.tvConferencia);
        et_Texto = findViewById(R.id.et_Texto);
        ib_Envio = findViewById(R.id.ib_Envio);
        conferenciaActual = new Conferencia();

        auth = FirebaseAuth.getInstance();
        FirebaseUser usrFB= auth.getCurrentUser();
        tv_datosUser.setText(usrFB.getEmail());
        leerConferencias();
        iniciarConferenciasIniciadas();
        ib_Envio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });

        bCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(InicioAppActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    private void enviarMensaje() {
        String body = et_Texto.getText().toString();
        String usuario = tv_datosUser.getText().toString();
        if (!body.isEmpty()) {
            //usuario y mensaje
            Mensaje mensaje = new Mensaje(usuario, body);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(FirebaseContract.ConferenciaEntry.COLLECTION_NAME)
                    //documento conferencia actual
                    .document(conferenciaActual.getId())
                    //subcolección de la conferencia
                    .collection(FirebaseContract.ChatEntry.COLLECTION_NAME)
                    //añadimos el mensaje nuevo
                    .add(mensaje);
            et_Texto.setText("");
            ChatAdapter chat = new ChatAdapter(mensaje);
            ocultarTeclado();
        }
    }
    /**
     * Permite ocultar el teclado
     */
    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(et_Texto.getWindowToken(), 0);
        }
    }


    private void iniciarConferenciasIniciadas() {
        //https://firebase.google.com/docs/firestore/query-data/listen
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef =
                db.collection(FirebaseContract.ConferenciaIniciadaEntry.COLLECTION_NAME).document(FirebaseContract.ConferenciaIniciadaEntry.ID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    String conferenciaIniciada=snapshot.getString(FirebaseContract.ConferenciaIniciadaEntry.CONFERENCIA);
                        tvConferencia.setText(getResources().getString(R.string.tvConferencia, conferenciaIniciada));
                    Log.d(TAG, "Conferencia iniciada: " + snapshot.getData());
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.i_cambiarActivity:
                startActivity(new Intent(InicioAppActivity.this, EmpresaActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void leerConferencias() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listaConferencias=new ArrayList<Conferencia>();
        db.collection(FirebaseContract.ConferenciaEntry.COLLECTION_NAME)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " +
                                        document.getData());
                                listaConferencias.add(document.toObject(Conferencia.class));
                            }
                            cargaSpinner();
                        } else {
                            Log.d(TAG, "Error getting documents: ",
                                    task.getException());
                        }
                    }
                });
    }

    private void cargaSpinner() {
        ArrayList<String> nombres = new ArrayList<>();
        for(Conferencia conferencia : listaConferencias){
            nombres.add(conferencia.getNombre());
        }
        sConferencias.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nombres));

        sConferencias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Conferencia conferencia = listaConferencias.get(position);
                conferenciaActual = conferencia;

                String fecha = getResources().getString(R.string.mensaje_fecha, conferencia.getFecha());
                String horario = getResources().getString(R.string.mensaje_horario, conferencia.getHorario());
                String sala = getResources().getString(R.string.mensaje_sala, conferencia.getSala());

                String mensaje = conferencia.getNombre()+"\n"+fecha+"\n"+horario+"\n"+sala;

                AlertDialog.Builder dialog = new AlertDialog.Builder(InicioAppActivity.this);
                dialog.setTitle(getResources().getString(R.string.titulo_dialog_conferencia));
                dialog.setMessage(mensaje);
                dialog.setPositiveButton(getResources().getString(R.string.ok), null);
                dialog.create();
                dialog.show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}