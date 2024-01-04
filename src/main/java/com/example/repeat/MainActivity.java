package com.example.repeat;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private final int Storage = 1;
    int i;
    boolean ischecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button b1 = findViewById(R.id.b1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText ed1 = findViewById(R.id.ed1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText ed2 = findViewById(R.id.ed2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText t1 = findViewById(R.id.t1);
        Button b2 = findViewById(R.id.button2);
        b1.setOnClickListener(v -> {
            ischecked = CheckAllFields(ed1, ed2);
            if (ischecked) {

                int n = Integer.parseInt(ed2.getText().toString());
                String s = ed1.getText().toString();
                StringBuilder l = new StringBuilder();

                for (i = 1; i <= n; ++i) {
                    l.append(s).append("\n");
                }
                t1.setText(l.toString());
            }

        });
        b2.setOnClickListener(v -> addNotification());




    }

    @SuppressWarnings("deprecation")
    private void addNotification() {
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.back)
                .setContentTitle("Notify")
                .setContentText("Hi Prathab H R U");
        Intent notificationIntent= new Intent(this,MainActivity.class);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());


    }

    private boolean CheckAllFields(TextView ed1, TextView ed2) {
        if(ed1.length()==0){
            ed1.setError("this field is required");
            return false;
        }
        if(ed2.length()==0){
            ed2.setError("this field is required");
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.login){
           Toast.makeText(MainActivity.this,"LOGIN",Toast.LENGTH_SHORT).show();

       } else if (item.getItemId()==R.id.follow) {
           Uri uri= Uri.parse("https://instagram.com/_decent_fel_low/");
           startActivity(new Intent(Intent.ACTION_VIEW,uri));
       }
       else if(item.getItemId()==R.id.storage){
           if(ContextCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
               Toast.makeText(MainActivity.this,"Already granted",Toast.LENGTH_SHORT).show();
           }
           else {
               requestStoragePermission();
           }

       }
       else if(item.getItemId()==R.id.notify){
           Toast.makeText(MainActivity.this,"Notify",Toast.LENGTH_SHORT).show();


       }
    return super.onOptionsItemSelected(item);

    }


    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission Needed")
                    .setPositiveButton("ok", (dialog, which) -> ActivityCompat.requestPermissions(MainActivity.this, new String[]{ READ_EXTERNAL_STORAGE},Storage))
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();


        }else{
            ActivityCompat.requestPermissions(this, new String[]{ READ_EXTERNAL_STORAGE},Storage);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         if(requestCode == Storage){
             if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                 Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
             }else{
                 Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
             }
         }
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, @NonNull Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }



}
