package practica7.alexandrorodriguez.iesseveroochoa.net.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import practica7.alexandrorodriguez.iesseveroochoa.net.FirebaseContract;
import practica7.alexandrorodriguez.iesseveroochoa.net.R;
import practica7.alexandrorodriguez.iesseveroochoa.net.model.Empresa;

public class EmpresaActivity extends AppCompatActivity implements View.OnClickListener {
    /**ATRIBUTOS**/
    private TextView tv_direccion;
    private TextView tv_telefono;
    private Empresa empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        tv_direccion = findViewById(R.id.tv_direccion);
        tv_telefono = findViewById(R.id.tv_telefono);
        obtenDatosEmpresa();
    }

    /**
     * OBTENEMOS LOS DATOS DE LA EMPRESAS
     */
    private void obtenDatosEmpresa() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(FirebaseContract.EmpresaEntry.COLLECTION_NAME).document(FirebaseContract.EmpresaEntry.ID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                //leemos los datos
                empresa = documentSnapshot.toObject(Empresa.class);
                //mostramos los datos y asignamos eventos
                asignaValoresEmpresa();
            }
        });
    }

    /**
     * ASIGNAMOS LOS VALORES A CADA EMPRESA
     */
    private void asignaValoresEmpresa() {
        String direccion = getResources().getString(R.string.tv_direccionFormateado, empresa.getDireccion());
        String telefono = getResources().getString(R.string.tv_telefonoFormateado, empresa.getTelefono());
        tv_direccion.setText(direccion);
        tv_telefono.setText(telefono);

        tv_direccion.setOnClickListener(this);
        tv_telefono.setOnClickListener(this);
    }

    //Le damos función al click de los text view
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_direccion:
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, empresa.getUriLocalizacion());
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            case R.id.tv_telefono:
                startActivity(new Intent(Intent.ACTION_VIEW,empresa.getUriTelefono()));
                break;
        }
    }
}