package com.example.wordscramble.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.wordscramble.AdivinarPalabraActivity;
import com.example.wordscramble.R;
import com.example.wordscramble.models.Palabra;
import com.example.wordscramble.models.PalabraDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;

public class PalabraController {
    private Context context;
    private AdivinarPalabraActivity view;
    private int idAsignatura;


    //variables para las views
    private TextView palabraAAdivinar;  //se le asigna la palabra obtenida desde la bd
    private TextView palabraDesordenada; //muestra la palabra desordenada
    private TextView ayuda; //muestra inform. de ayuda sobre la palabra a adivinar
    private Button btnAyuda; //hace visible la ayuda de la palabra a adivinar
    private EditText respuesta; //respuesta del usuario
    private Button btnOk; //compara si lo que escribió el usuario coincide con la palabra a adivinar

    private TextView idPalabra; //almacena la clave de la palabra a adivinar



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

        idPalabra = (TextView) view.findViewById(R.id.idPalabra);



        //ocultamos algunas views claves
        palabraAAdivinar.setVisibility(View.INVISIBLE);
        ayuda.setVisibility(View.INVISIBLE);
        idPalabra.setVisibility(View.INVISIBLE);


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
            guardarPalabra();

            try{
                Thread.sleep(2000);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
            limpiarVista();
            mostrarPalabra(idAsignatura);
        }else{
            Toast.makeText(this.context,"Las palabras no coinciden", Toast.LENGTH_LONG).show();
        }

        respuesta.setText("");
        respuesta.requestFocus();
        ayuda.setVisibility(View.INVISIBLE);
    }

    private void limpiarVista() {
        respuesta.setText("");
        ayuda.setText("");
        palabraDesordenada.setText("");
        palabraDesordenada.setText("");
    }

    /**
     * Guarda la palabra en la base de datos
     */
    private void guardarPalabra() {
        //creamos un objeto de tipo PalabraDao y llamamos al método para actualizar la         //bd
        int idP = Integer.parseInt(idPalabra.getText().toString());
        PalabraDao palabraDao = new PalabraDao(this.context);

        palabraDao.guadarAcierto(1, idP, new PalabraDao.CallBackValidation() {
            @Override
            public void onSuccess(String respuesta) {
                if (Boolean.valueOf(respuesta)){
                        Toast.makeText(context, "Felicidades, has " +
                                "adivinado la palabra y se ha incrementado" +
                                "tu puntuación", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Felicidades, has " +
                            "adivinado la palabra pero no se pudo " +
                            "registrar tu nueva puntuación", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFail(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
            }
        });
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
        this.idAsignatura = idAsignatura;
        PalabraDao.idAsignatura = idAsignatura; //pasamos el Id. de la asignatura
        PalabraDao palabraDao = new PalabraDao(this.context).obtenerPalabra(new PalabraDao.CallBack() {

            @Override
            public void onSuccess(Palabra palabra) {
                try {
                     //asignamos sus valores de manera asyncrona
                     idPalabra.setText(Integer.toString(palabra.getIdPalabra()));
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
    }//fin del método

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
