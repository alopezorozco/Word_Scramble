package com.example.wordscramble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.wordscramble.controllers.AsignaturaController;

public class MainActivity extends AppCompatActivity {
    private AsignaturaController asignaturaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        asignaturaController = new AsignaturaController(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AsignaturaListAdapter adapter = new AsignaturaListAdapter(this);
        asignaturaController.mostrarAsignaturas(recyclerView, adapter);
    }
}
