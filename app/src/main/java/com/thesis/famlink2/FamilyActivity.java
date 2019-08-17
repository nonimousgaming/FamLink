package com.thesis.famlink2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.thesis.famlink2.data.Family;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Family> familyList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.fam_list);
        recyclerView.setVisibility(View.VISIBLE);
        familyList = new ArrayList<>();
        adapter = new FamilyAdapter(getApplicationContext(),familyList);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        String url = "https://famlink.000webhostapp.com/api/family";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(response.contentEquals("[]")) {
                        Toast.makeText(FamilyActivity.this, "No family uploaded yet", Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Family family = new Family();
                            family.setName(jsonObject.getString("name"));
                            familyList.add(family);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FamilyActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        }){
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", "1");
                return params;
            }
        };
        MySingleton.getInstance(FamilyActivity.this).addToRequestQueue(stringRequest);
    }
}
