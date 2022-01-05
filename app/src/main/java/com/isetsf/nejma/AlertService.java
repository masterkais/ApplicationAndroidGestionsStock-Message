package com.isetsf.nejma;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isetsf.nejma.domain.Message;
import com.isetsf.nejma.domain.UserMobile;

import java.lang.reflect.Type;
import java.util.List;

public class AlertService extends Service {
    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    int id_reservation;
    private List<Message> alerte;
    SessionManager session;
    UserMobile user;
    Intent myIntent;
    private PendingIntent pendingIntent;
    private NotificationManager notificationManager;
    private android.support.v4.app.NotificationCompat.Builder notificationBuilder;
    public AlertService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
        session=new SessionManager(context);
        user=session.getUser();

    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            System.out.println("Service is Runing  hellooooo Service....");
            if(session.getUser()!=null) {
                recupere();
            }
            stopSelf();
        }
    };

    private void recupere() {
        String URL=Services.SERVER+"ListeMessage?cin="+user.getCin();
        StringRequest stringRequest=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Gson gson=new Gson();
                    Type type=new TypeToken<List<Message>>(){}.getType();
                    List<Message> recived=gson.fromJson(response,type);
                    if(recived.size()==session.getCountMessages()){

                    }else{
                        showAlerte("vous avez recue un message",recived.get(recived.size()-1).getObjet(),"");
                        session.saveMessage(recived.size());
                    }

                }catch (Exception er){
                    er.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("volley error",error.toString());

                    }
                });
         Volley.newRequestQueue(context).add(stringRequest);

    }


    private void showAlerte(final String  title, final String message,String coupon){

                try {

                    myIntent = new Intent(context, MainActivity.class);

                    //Initialize PendingIntent
                    pendingIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //Initialize NotificationManager using Context.NOTIFICATION_SERVICE
                    notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationBuilder = new NotificationCompat.Builder(context)
                            .setContentTitle(title).setSmallIcon(R.drawable.ic_notifications_black_24dp).setContentIntent(pendingIntent)
                            .setContentText(message);
                    //add sound

                    //vibrate

                    notificationManager.notify(1, notificationBuilder.build());
                }catch (Exception er) {
                    er.printStackTrace();
                }

    }
    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}
