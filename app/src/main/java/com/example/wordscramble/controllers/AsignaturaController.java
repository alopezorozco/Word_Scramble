package com.example.wordscramble.controllers;

import android.content.Context;
import android.widget.Toast;

import com.example.wordscramble.AsignaturaListAdapter;
import com.example.wordscramble.models.Asignatura;
import com.example.wordscramble.models.AsignaturaDao;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class AsignaturaController {
    private Context context;

    public AsignaturaController(Context context){
        this.context = context;
    }

    //muestra las asignaturas en la aplicación
    public void mostrarAsignaturas(RecyclerView recyclerView, AsignaturaListAdapter adapter){
        //llamamos al método obtenerAsignaturas
        AsignaturaDao asignaturaDao = new AsignaturaDao(this.context).obtenerAsignaturas(new AsignaturaDao.CallBack() {
            @Override
            public void onSuccess(List<Asignatura> asignaturas) {
                recyclerView.setAdapter(adapter);
                adapter.setAsignaturas(asignaturas);
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(recyclerView.getContext(), msg.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
