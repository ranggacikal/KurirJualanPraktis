package com.example.kurirjualanpraktis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.kurirjualanpraktis.api.ConfigRetrofit;
import com.example.kurirjualanpraktis.model.login.ResponseItem;
import com.example.kurirjualanpraktis.model.login.ResponseLogin;
import com.example.kurirjualanpraktis.sharedPreferences.Pref;
import com.example.kurirjualanpraktis.sharedPreferences.SharedPrefManager;
import com.example.kurirjualanpraktis.sharedPreferences.VolleySingleton;
import com.example.kurirjualanpraktis.sharedPreferences.loginuser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    ImageView showPassBtn;

    Pref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.login_email);
        edtPassword = findViewById(R.id.login_pass);
        btnLogin = findViewById(R.id.btn_masuk);
        showPassBtn = findViewById(R.id.show_pass_btn);

        pref = new Pref(getApplicationContext());

        if (SharedPrefManager.getInstance(LoginActivity.this).isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if (email.isEmpty()){
                    edtEmail.setError("Email Tidak Boleh Kosong");
                    edtEmail.requestFocus();
                    return;
                }

                if (password.isEmpty()){
                    edtPassword.setError("Password Tidak Boleh Kosong");
                    edtPassword.requestFocus();
                    return;
                }
                login(email, password);
            }
        });

        showHidePass(showPassBtn);
    }

    private void showHidePass(View view) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(view.getId()==R.id.show_pass_btn){

                    if(edtPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye);
                        //Show Password
                        edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    }
                    else{
                        ((ImageView)(view)).setImageResource(R.drawable.icon_awesome_eye_slash);

                        //Hide Password
                        edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                    }
                }

            }
        });

    }

    private void login(String email, String password) {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("LOGIN");
        progressDialog.show();

        String URL_LOGIN = "https://jualanpraktis.net/android/kurir/login.php";
        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pref.setLoginMethod("login");
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("response");
                            //JSONObject userJson = obj.getJSONObject("login");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String getObject = jsonObject.toString();
                                JSONObject object = new JSONObject(getObject);

                                if (object.getString("status").equals("1")) {
                                    loginuser user = new loginuser(
                                            object.getString("id_kurir"),
                                            object.getString("token"),
                                            object.getString("nama"),
                                            object.getString("foto"),
                                            object.getString("ktp"),
                                            object.getString("jk"),
                                            object.getString("alamat"),
                                            object.getString("email"),
                                            object.getString("password"),
                                            object.getString("no_hp"),
                                            object.getString("imei"),
                                            object.getString("sim"),
                                            object.getString("stnk"),
                                            object.getString("jenis"),
                                            object.getString("merek"),
                                            object.getString("milik"),
                                            object.getString("status"),
                                            object.getString("date")

                                    );
                                    progressDialog.dismiss();
                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                    startActivity(new Intent(LoginActivity.this, MainActivity.class)
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                    Toast.makeText(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Harap lakukan verifikasi email terlebih dahulu.", Toast.LENGTH_SHORT).show();
                                }


                            }
                            //starting the profile activity


                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Email/Password salah & Cek koneksi anda", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //   progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}