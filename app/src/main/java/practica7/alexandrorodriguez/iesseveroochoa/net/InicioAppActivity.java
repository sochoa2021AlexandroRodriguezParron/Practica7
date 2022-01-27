package practica7.alexandrorodriguez.iesseveroochoa.net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

}