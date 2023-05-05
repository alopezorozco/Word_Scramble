package com.example.wordscramble.models;

import android.content.Context;
import android.net.Uri;

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

public class PalabraDao {
    private Palabra palabra = null;
    private Context context;
    private RequestQueue mRequestQueue;
    public static int idAsignatura;
    boolean respuesta;

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
                    //utilizamos una variable de tipo JSON ya que retorna un string en formato JSON
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
                        onCallBack.onSuccess(palabra);
                    }//fin del for

                } catch (JSONException e) {
                    e.printStackTrace();
                    onCallBack.onFail(e.toString());
                }catch (Exception ex){
                    try {
                        throw new Exception("No exiten palabras a adivinar en esta categoría");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }//fin del catch
                }//fin del catch
            }//fin del método

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(stringRequest);
        return null;
    }//fin del método

    /*public void guardarAcierto(int idUsuario, int idPalabra, final CallBack onCallBack){
        //URL del endpoint
        String url = GlobalVariables.URL+"guardar-acierto/"+Uri.encode(String.valueOf(idUsuario))+"/" +
                Uri.encode(String.valueOf(idPalabra));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = "Los datos han sido guardados";
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    throw new Exception("prueba");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mRequestQueue.add(stringRequest);
    }//fin del método guardarAcierto*/

    //método para guardar la palabra encontrada
    public void guadarAcierto(int idUsuario, int idPalabra, final CallBackValidation onCallBack){
        String url = GlobalVariables.URL+"guardar-acierto/"+Uri.encode(String.valueOf(idUsuario))+"/" +
                Uri.encode(String.valueOf(idPalabra));

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    response = "true";
                    onCallBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    throw new Exception("Error al realizar la petición");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        mRequestQueue.add(stringRequest);
    }


    /**
     * Retorna un objeto de tipo palabra si la operación fue exitosa
     */
    public interface CallBack {
        void onSuccess(Palabra palabra);
        void onFail(String msg);
    }

    /**
     * Retorna true o false si la operación fue exitosa
     */
    public interface CallBackValidation{
        void onSuccess(String respuesta);
        void onFail(String msg);
    }
}
