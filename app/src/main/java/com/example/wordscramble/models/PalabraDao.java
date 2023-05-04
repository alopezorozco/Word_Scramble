package com.example.wordscramble.models;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wordscramble.GlobalVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PalabraDao {
    private Palabra palabra = null;
    private Context context;
    private RequestQueue mRequestQueue;
    public static int idAsignatura;

    public PalabraDao(Context context){
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(this.context);
    }

    /**
     * Obtiene la palabra a adivinar de la base de datos
     * @param onCallBack
     * @return
     */
    public PalabraDao obtenerPalabra(final CallBack onCallBack){
        //URL del endpoint
        String url = GlobalVariables.URL+"palabra-a-adivinar/" + Uri.encode(String.valueOf(idAsignatura));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{
                    JSONArray jsonArray = new JSONArray(response);

                    // Recorremos el array JSON y almacenamos los datos en una lista
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //obtenemos los datos con base a las claves del JSON
                        int idPalabra = jsonObject.getInt("idPalabra");
                        String nombreAsignatura = jsonObject.getString("palabraAAdivinar");
                        String descripcion = jsonObject.getString("palDescripcion");
                        int ponderacion = jsonObject.getInt("ponderacion");


                        palabra = new Palabra(idPalabra, nombreAsignatura, descripcion, ponderacion);
                    }//fin del for
                    onCallBack.onSuccess(palabra);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onCallBack.onFail(e.toString());
                }catch (Exception ex){
                    try {
                        throw new Exception("No exiten palabras a adivinar en esta categoría");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });




        mRequestQueue.add(stringRequest);
        return null;
    }//fin del método

    //método para guardar la palabra encontrada
    public void guadarAcierto(){
        //creamos un objeto JSONObject con los datos a enviar
        JSONObject jsonObject = new JSONObject();

        User user = new User(1);
        Palabra palabra = new Palabra(1);

            try{
                jsonObject.put("Usuario", user);
                jsonObject.put("Palabra", palabra);
                //jsonObject.put("", 5);
            }catch(JSONException ex){
                ex.printStackTrace();
            }

        //definimos la URL
        String url = GlobalVariables.URL + "/save-acierto/acierto";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    //manejamos la respuesta del servidor
                },
                error ->{
                    //manejamos el error de la solicitud
                });

        mRequestQueue.add(jsonObjectRequest);
    }


    public interface CallBack {
        void onSuccess(Palabra palabra);
        void onFail(String msg);
    }
}
