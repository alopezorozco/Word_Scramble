package com.example.wordscramble.models;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaDao {
    private List<Asignatura> asignaturas;
    private Context context;
    private RequestQueue mRequestQueue;

    public AsignaturaDao(Context context) {
        this.context = context;
        asignaturas = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this.context);
    }

    /**
     * recupera todas las asignaturas de la base de datos
     * @param onCallBack
     */
    public void getAllAsignatures(final CallBack onCallBack){
        //URL del endpoint
        String url = "http://192.168.1.79:8080/asignatura";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Asignatura asignatura = new Asignatura();
                try{
                    JSONArray jsonArray = new JSONArray(response);

                    // Recorremos el array JSON y almacenamos los datos en una lista
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //obtenemos los datos con base a las claves del JSON
                        int asigId = jsonObject.getInt("asigId");
                        String nombreAsignatura = jsonObject.getString("asigNombre");

                        //agregamos los datos a la lista
                        asignaturas.add(new Asignatura(asigId, nombreAsignatura));
                    }
                    onCallBack.onSuccess(asignaturas);
                } catch (JSONException e) {
                    e.printStackTrace();
                    onCallBack.onFail(e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(stringRequest);
    }

    public interface CallBack {
        void onSuccess(List<Asignatura> asignaturas);
        void onFail(String msg);
    }
}
