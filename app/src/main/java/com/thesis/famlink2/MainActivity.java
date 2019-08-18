package com.thesis.famlink2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editTextLoginEmail, editTextLoginPassword;
    Button buttonLogin;
    TextView textViewClickRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        initializeEvents();
    }

    public void initializeViews() {
        editTextLoginEmail = findViewById(R.id.editTextRegisterEmail);
        editTextLoginPassword = findViewById(R.id.editTextRegisterPassword);
        buttonLogin = findViewById(R.id.buttonRegister);
        textViewClickRegister = findViewById(R.id.textViewClickRegister);
    }

    private void initializeEvents() {

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextLoginEmail.getText().toString();
                String password = editTextLoginPassword.getText().toString();
                loginUser(email, password);
            }
        });

        textViewClickRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginUser(final String email, final String password) {
        String url = "https://famlink.000webhostapp.com/api/login";
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
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        if(status.equals("400")) {
                            Intent intent = new Intent(MainActivity.this, FamilyActivity.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
            MySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
        }
    }
}
