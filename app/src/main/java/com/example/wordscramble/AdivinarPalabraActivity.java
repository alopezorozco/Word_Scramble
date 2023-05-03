package com.example.wordscramble;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordscramble.controllers.PalabraController;

public class AdivinarPalabraActivity extends AppCompatActivity {
    //declaración de variables
    private int idAsig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adivinar_palabra);

        Intent intent = getIntent();

        //obtenemos el id. de la asignatura
        idAsig = intent.getIntExtra("id", 0);

        //Creamos una intancia del controller de esta Activity
        PalabraController miController = new PalabraController(this, this);

        //llamamos al método para mostrar la palabra a adivinar
        miController.mostrarPalabra(idAsig);
    }






}
