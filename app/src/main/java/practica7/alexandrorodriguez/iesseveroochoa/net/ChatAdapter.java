package practica7.alexandrorodriguez.iesseveroochoa.net;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Mensaje> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull Mensaje mensaje) {
        holder.tv_Mensaje.setText(mensaje.toString());
        //si el mensaje es del usuario lo colocamos a la izquierda
        if(mensaje.getUsuario().equals(usuario)){
            holder.cvContenedor.setCardBackgroundColor(Color.YELLOW);
            holder.lytContenedor.setGravity(Gravity.LEFT);
        }else {
            holder.lytContenedor.setGravity(Gravity.RIGHT);
            holder.cvContenedor.setCardBackgroundColor(Color.WHITE);
        }
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_inicio_app, parent, false);

        return new ChatHolder(view);
    }

    public class ChatHolder extends RecyclerView.ViewHolder {
        private TextView tv_Mensaje;
        private ConstraintLayout cvContenedor;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            tv_Mensaje = itemView.findViewById(R.id.tv_Mensaje);
            cvContenedor = itemView.findViewById(R.id.cvContenedor);
        }
    }
}
