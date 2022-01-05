package com.isetsf.nejma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.isetsf.nejma.domain.UserMobile;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.edit_login)
    EditText edit_login;
    @BindView(R.id.edit_password)
    EditText edit_password;
    ProgressDialog dialog;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        dialog=new ProgressDialog(this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth();
            }
        });
        session=new SessionManager(this);
        if(session.isLoggedIn()){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }

    }


    public  void auth(){
        dialog.setMessage("Loading...");
        dialog.show();
        String url=Services.SERVER+"UtilisateurService";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                UserMobile ms=gson.fromJson(response,UserMobile.class);
                if(ms!=null){
                  session.saveUser(ms.getCin(),ms.getNom(),ms.getPrenom(),ms.getType(),ms.getPoint(),ms.getLogin(),ms.getPassword(),ms.getMail());
                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                }else
                    TastyToast.makeText(getApplicationContext(), "Login failed ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volleyerror",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map=new HashMap();
                map.put("login",edit_login.getText().toString());
                map.put("password",edit_password.getText().toString());
                return map;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
