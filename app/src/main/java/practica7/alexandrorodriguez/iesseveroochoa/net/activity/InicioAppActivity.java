package practica7.alexandrorodriguez.iesseveroochoa.net.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import practica7.alexandrorodriguez.iesseveroochoa.net.FirebaseContract;
import practica7.alexandrorodriguez.iesseveroochoa.net.R;
import practica7.alexandrorodriguez.iesseveroochoa.net.model.Conferencia;

public class InicioAppActivity extends AppCompatActivity {

    private static final String TAG = "P7";
    FirebaseAuth auth;
    private TextView tv_datosUser;
    private Button bCerrarSession;
    private Spinner sConferencias;
    private ArrayList<Conferencia> listaConferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        tv_datosUser = findViewById(R.id.tv_datosUser);
        bCerrarSession = findViewById(R.id.bCerrarSession);
        sConferencias = (Spinner)findViewById(R.id.sConferencias);

        auth = FirebaseAuth.getInstance();
        FirebaseUser usrFB= auth.getCurrentUser();
        tv_datosUser.setText(usrFB.getEmail());
        leerConferencias();

        bCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(InicioAppActivity.this, MainActivity.class));
                finish();
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