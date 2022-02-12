package practica7.alexandrorodriguez.iesseveroochoa.net;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import practica7.alexandrorodriguez.iesseveroochoa.net.model.Mensaje;

public class ChatAdapter extends FirestoreRecyclerAdapter<Mensaje, ChatAdapter.ChatHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    private String usuario;
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Mensaje> options, String usuario) {
        super(options);
        this.usuario = usuario;
    }

    /**
     * Nos permitirá actualizar el recycler view
     * @param holder
     * @param position
     * @param mensaje
     */
    @Override
    protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull Mensaje mensaje) {
        holder.tvMensaje.setText(mensaje.toString());
        //si el mensaje es del usuario lo colocamos a la izquierda
        if(mensaje.getUsuario().equals(usuario)){
            holder.cvContenedor.setCardBackgroundColor(Color.YELLOW);
            holder.lytContenedor.setGravity(Gravity.LEFT);
        }else {
            holder.lytContenedor.setGravity(Gravity.RIGHT);
            holder.cvContenedor.setCardBackgroundColor(Color.WHITE);
        }
    }

    /**
     * Nos permitirá añadirle al recyclerView el layout que utilizará
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mensaje_item, parent, false);

        return new ChatHolder(view);
    }

    /**
     * Clase que hace referencia a los elementos del layout
     */
    public class ChatHolder extends RecyclerView.ViewHolder {
        private TextView tvMensaje;
        private CardView cvContenedor;
        private LinearLayout lytContenedor;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            cvContenedor = itemView.findViewById(R.id.cvContenedor);
            lytContenedor = itemView.findViewById(R.id.lytContenedor);
        }
    }
}
