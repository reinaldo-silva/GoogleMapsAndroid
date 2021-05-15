package com.example.atividademaps1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class List extends AppCompatActivity {
    private ListView dataListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        RequestQueue queue = Volley.newRequestQueue(this);
        final ArrayList<JSONObject> jsonArray = new ArrayList<>();
        String url = "https://restcountries.eu/rest/v2/lang/it";
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray >() {
                    @Override
                    public void onResponse(JSONArray  response) {
                        ArrayList<String> lista = new ArrayList();
                        dataListView = (ListView) findViewById(R.id.list);
                        try {
                        for (int i = 0; i < response.length(); i++) {

                                JSONObject object = (JSONObject) response.get(i);
                                lista.add(object.getString("name"));
                                jsonArray.add(object);

                                System.out.println(jsonArray.get(0).getJSONArray("latlng").get(0));

                        }
                        dataListView.setAdapter(new ArrayAdapter<String>(
                                getApplicationContext(),
                                android.R.layout.simple_list_item_1,
                                android.R.id.text1,
                                lista
                        ));

                            dataListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(List.this, MapsActivity.class);
                                    try {
                                        intent.putExtra("LAT", jsonArray.get(position).getJSONArray("latlng").get(0).toString());
                                        intent.putExtra("LONG", jsonArray.get(position).getJSONArray("latlng").get(1).toString());
                                        startActivity(intent);
                                    } catch(JSONException err) {
                                        System.out.println("Deu ruim" + err);
                                    }
                                }
                            });


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        }){
        };
        queue.add(stringRequest);
    }
}