package com.thesis.famlink2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText editTextRegisterEmail, editTextRegisterPassword;
    Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeViews();
        initializeEvents();
    }

    private void initializeViews() {
        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail);
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    private void initializeEvents() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextRegisterEmail.getText().toString();
                String password = editTextRegisterPassword.getText().toString();
                registerUser(email, password);
            }
        });
    }

    private void registerUser(final String email, final String password) {
        String url = "https://famlink.000webhostapp.com/api/register";
        if(email.equals("") || password.equals("")) {
            Toast.makeText(this, "Please fill up the fields", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");
                        String status = jsonObject.getString("status");
                        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
                        if(status.equals("400")) {
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(RegistrationActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }){
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            MySingleton.getInstance(RegistrationActivity.this).addToRequestQueue(stringRequest);
        }
    }
}
