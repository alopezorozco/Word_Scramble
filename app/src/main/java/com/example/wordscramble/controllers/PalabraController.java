package com.example.wordscramble.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wordscramble.AdivinarPalabraActivity;
import com.example.wordscramble.R;
import com.example.wordscramble.models.Palabra;
import com.example.wordscramble.models.PalabraDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PalabraController {
    private Context context;
    private AdivinarPalabraActivity view;

    //variables para las views
    private TextView palabraAAdivinar;  //se le asigna la palabra obtenida desde la bd
    private TextView palabraDesordenada; //muestra la palabra desordenada
    private TextView ayuda; //muestra inform. de ayuda sobre la palabra a adivinar
    private Button btnAyuda; //hace visible la ayuda de la palabra a adivinar
    private EditText respuesta; //respuesta del usuario
    private Button btnOk; //compara si lo que escribió el usuario coincide con la palabra a adivinar



    public PalabraController(Context context, AdivinarPalabraActivity view){
        this.context = context;
        this.view = view;

        //creamos apuntadores hacia las views
        palabraAAdivinar = (TextView) view.findViewById(R.id.palabra_a_adivinar);

        palabraDesordenada = (TextView) view.findViewById(R.id.palabra_desordenada);

        ayuda = (TextView) view.findViewById(R.id.texto_ayuda);


        btnAyuda = (Button) view.findViewById(R.id.boton_ayuda);

        respuesta = (EditText) view.findViewById(R.id.edPalabraOrdenada);

        btnOk = (Button) view.findViewById(R.id.aceptar);



        //ocultamos algunas views claves
        palabraAAdivinar.setVisibility(View.INVISIBLE);
        ayuda.setVisibility(View.INVISIBLE);

        //listen del botón btnayuda
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarAyuda();
            }
        });

        //comprobamos si coinciden las palabras
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compararPalabras();
            }
        });
    }

    //comparar si el usuario adivinó la palabra
    private void compararPalabras() {
        String palabraDeUsuario = respuesta.getText().toString().toLowerCase();
        String palabraOrigen = palabraAAdivinar.getText().toString().toLowerCase();

        if (palabraOrigen.equals(palabraDeUsuario)){
            Toast.makeText(this.context, "Correcto", Toast.LENGTH_LONG).show();

            //creamos un objeto de tipo PalabraDao y llamamos al método para actualizar la
            //bd
            PalabraDao palabraDao = new PalabraDao(this.context);
            palabraDao.guadarAcierto();
        }
    }

    //muestra inform. sobre la palabra a adivinar
    private void mostrarAyuda() {
        ayuda.setVisibility(View.VISIBLE);
    }


    /**
     * Muestra la palabra a adivinar en el layout
     * @param idAsignatura
     */
    public void mostrarPalabra(int idAsignatura) {

        PalabraDao.idAsignatura = idAsignatura; //pasamos el Id. de la asignatura
        PalabraDao palabraDao = new PalabraDao(this.context).obtenerPalabra(new PalabraDao.CallBack() {

            @Override
            public void onSuccess(Palabra palabra) {
                try {
                     //asignamos sus valores de manera asyncrona

                     palabraAAdivinar.setText(palabra.getPalabra().toLowerCase());
                     palabraDesordenada.setText(desordenarPalabra(palabra.getPalabra().toLowerCase()));
                     ayuda.setText(palabra.getDescripcion());
                }catch(Exception ex){
                    Toast.makeText(context, "No existen palabras a adivinar" +
                            " en esta asignatura", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Desordena la palabra obtenida desde la bd
     * @param palabraBD
     * @return
     */
    private String desordenarPalabra(String palabraBD) {
        char[] caracteres = palabraBD.toCharArray();
        List<Character> listaCaracteres = new ArrayList<>();
        for (char c : caracteres) {
            listaCaracteres.add(c);
        }
        Collections.shuffle(listaCaracteres);
        StringBuilder sb = new StringBuilder();
        for (Character c : listaCaracteres) {
            sb.append(c);
        }

        return sb.toString();
    }

}
