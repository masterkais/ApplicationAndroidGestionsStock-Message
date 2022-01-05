package com.isetsf.nejma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.isetsf.nejma.domain.UserMobile;

/**
 * Created by medchiheb on 06/04/17.
 */

public class SessionManager {
    public  static String KEY_Cin="Cin";
    public  static String Key_Nom="Nom";
    public  static String KEY_Prenom="Prenom";
    public  static String KEY_Point="Point";
    public  static String KEY_Type="type";
    public  static String KEY_Login="login";
    public  static String KEY_Password="password";
    public  static String KEY_Mail="mail";
    public static String KEY_Messages="messages";

    private SharedPreferences pref;
    private SharedPreferences.Editor ed;

    public SessionManager (Context context){
        pref=context.getSharedPreferences("NejmaPref",context.MODE_PRIVATE);
        ed=pref.edit();

    }

    public void saveUser(String cin,String nom,String prenom, String type, int points,String login,String password,String mail){

        ed.putString(KEY_Cin,cin);
        ed.putString(Key_Nom,nom);
        ed.putString(KEY_Prenom,prenom);
        ed.putString(KEY_Login,login);
        ed.putString(KEY_Type,type);
        ed.putString(KEY_Password,password);
        ed.putString(KEY_Mail,mail);
        ed.putInt(KEY_Point,points);
        ed.commit();

    }

    public UserMobile getUser(){
        String cin=pref.getString(KEY_Cin,"");
        String nom=pref.getString(Key_Nom,"");
        String prenom=pref.getString(KEY_Prenom,"");

        String type=pref.getString(KEY_Type,"");
        int point=pref.getInt(KEY_Point,0);
        String password=pref.getString(KEY_Password,"");
        String login=pref.getString(KEY_Login,"");
        String mail=pref.getString(KEY_Mail,"");
        UserMobile ms=new UserMobile();
        ms.setCin(cin);
        ms.setNom(nom);
        ms.setPrenom(prenom);
        ms.setType(type);
        ms.setPoint(point);
        ms.setLogin(login);
        ms.setPassword(password);
        ms.setMail(mail);
        return ms;
    }

    public boolean isLoggedIn(){
      String cin=  pref.getString(KEY_Cin,"");
        if(cin.equals("")){
            return false;
        }
        else{
            return true;
        }
    }
    public void logout(Context c){
        ed.clear();
        ed.commit();
        Intent i=new Intent(c, LoginActivity.class);
        c.startActivity(i);
    }

    public int getCountMessages(){
        return pref.getInt(KEY_Messages,0);
    }

    public void saveMessage(int count){
        ed.putInt(KEY_Messages,count);
        ed.commit();

    }
}
