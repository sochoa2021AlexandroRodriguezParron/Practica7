package practica7.alexandrorodriguez.iesseveroochoa.net;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaDrm;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                    new FirebaseAuthUIActivityResultContract(),
                    new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                        @Override
                        public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                            onSignInResult(result);
                        }
                    }
            );


    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            //si estamos autenticados abrimos la actividad principal
            startActivity(new Intent(this, InicioAppActivity.class));
        } else {
            // No puede autenticar
            String msg_error = "";
            if (response == null) {
                // User pressed back button
                msg_error = "Es necesario autenticarse";
            } else if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                msg_error = "No hay red disponible para autenticarse";
            } else { //if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                msg_error = "Error desconocido al autenticarse";
            }
            Toast.makeText(this, msg_error, Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void createSignInIntent() {
        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance().createSignInIntentBuilder()
                // Si quisieramos varios proveedores de autenticación. Mirar la documentación oficial, ya que cambia de una versión a otra
                //https://firebase.google.com/docs/auth/android/firebaseui?hl=es-419#sign_in
                //icono que mostrará, a mi no me funciona
                .setLogo(R.drawable.logo)
                .setIsSmartLockEnabled(false)//para guardar contraseñas y usuario: true
                .build();
        signInLauncher.launch(signInIntent);
        // [END auth_fui_create_intent]
    }

    private void comprobarAutenticacion() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Primero, verificamos la existencia de una sesión.
        if (auth.getCurrentUser() != null) {
            finish();// Cerramos la actividad.
            // Abrimos la actividad que contiene el inicio de la funcionalidad de la app.
            startActivity(new Intent(this, InicioAppActivity.class));
        } else {//en otro caso iniciamos FirebaseUI
            createSignInIntent();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comprobarAutenticacion();
    }
}