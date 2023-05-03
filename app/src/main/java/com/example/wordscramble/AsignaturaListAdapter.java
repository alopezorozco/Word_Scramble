package com.example.wordscramble;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wordscramble.models.Asignatura;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AsignaturaListAdapter extends RecyclerView.Adapter<AsignaturaListAdapter.AsignaturaViewHolder>{
    private final LayoutInflater mInflater; //mInflater permite mostrar la lista de elementos en la vista
    private List<Asignatura> listaAsignatura; //obtiene la lista de elementos de la base de datos
    private Context context;  //almacena el contexto de la actividad

    //constructor de la clase
    public AsignaturaListAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * recibe una lista de asignaturas, las asigna a la variable
     * listaAsignatura y actualiza la vista
     * @param asignaturas
     */
    public void setAsignaturas(List<Asignatura> asignaturas){
        listaAsignatura = asignaturas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AsignaturaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AsignaturaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AsignaturaViewHolder holder, int position) {
        //obtiene el elemento actual
        Asignatura asignatura = listaAsignatura.get(position);

        //configurar el listener para el evento onclick sobre el textview
        holder.asignaturaItemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //crear un intent para abrir la actividad deseada
                Intent intent = new Intent(view.getContext(), AdivinarPalabraActivity.class);

                //enviamos el id. y el nombre de la asignatura seleccionada a la otra Activity
                intent.putExtra("id", asignatura.getAsigId());
                intent.putExtra("asignatura", asignatura.getAsignombre());

                //iniciar la activity
                view.getContext().startActivity(intent);
            }
        });

        //asigna el valor al textview
        holder.asignaturaItemView.setText(asignatura.getAsignombre());
    }

    @Override
    public int getItemCount() {
        if (listaAsignatura!=null){
            return listaAsignatura.size();
        }else{
            return 0;
        }
    }

    //declaración de la clase interna ViewHolder
    class AsignaturaViewHolder extends RecyclerView.ViewHolder{
        private final TextView asignaturaItemView;

        public AsignaturaViewHolder(@NonNull View itemView) {
            super(itemView);
            asignaturaItemView = itemView.findViewById(R.id.nombre_asignatura);
        }
    }//fin de la clase interna
}
