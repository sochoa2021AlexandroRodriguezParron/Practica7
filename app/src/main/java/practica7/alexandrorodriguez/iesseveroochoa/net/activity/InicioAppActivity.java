package practica7.alexandrorodriguez.iesseveroochoa.net.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import practica7.alexandrorodriguez.iesseveroochoa.net.R;

public class InicioAppActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private TextView tv_datosUser;
    private Button bCerrarSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_app);
        tv_datosUser = findViewById(R.id.tv_datosUser);
        bCerrarSession = findViewById(R.id.bCerrarSession);

        auth = FirebaseAuth.getInstance();
        FirebaseUser usrFB= auth.getCurrentUser();
        tv_datosUser.setText(usrFB.getEmail());

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
}