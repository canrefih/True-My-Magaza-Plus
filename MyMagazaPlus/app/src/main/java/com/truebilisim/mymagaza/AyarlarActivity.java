package com.truebilisim.mymagaza;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class AyarlarActivity extends AppCompatActivity {
  Button button;
  EditText pID, pSifre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayarlar);

        //Kaydedilen Bilgileri Getir
        try{
            FileInputStream fis_ID = openFileInput("pID");
            InputStreamReader isr_ID = new InputStreamReader(fis_ID);
            BufferedReader br_ID = new BufferedReader(isr_ID);
            EditText tv_ID = (EditText) findViewById(R.id.eID);
            tv_ID.setText(br_ID.readLine());
            fis_ID.close();
        }catch(Exception e){
            e.printStackTrace(); }

        try{
            FileInputStream fis_Sifre = openFileInput("pSifre");
            InputStreamReader isr_Sifre = new InputStreamReader(fis_Sifre);
            BufferedReader br_Sifre = new BufferedReader(isr_Sifre);
            EditText tv_Sifre = (EditText) findViewById(R.id.eSifre);
            tv_Sifre.setText(br_Sifre.readLine());
            fis_Sifre.close();
        }catch(Exception e){
            e.printStackTrace(); }

        pSifre = (EditText)  findViewById(R.id.eSifre);
        pID    = (EditText)  findViewById(R.id.eID);

        button  = (Button) findViewById(R.id.btnKaydet);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                pSifre = (EditText)  findViewById(R.id.eSifre);
                pID    = (EditText)  findViewById(R.id.eID);
                try {
                    FileOutputStream fos_ID = openFileOutput("pID", Context.MODE_PRIVATE);
                    fos_ID.write( pID.getText().toString().getBytes());
                    fos_ID.close();
                }catch(Exception e){
                    e.printStackTrace();}
                try {
                    FileOutputStream fos_Sifre = openFileOutput("pSifre", Context.MODE_PRIVATE);
                    fos_Sifre.write( pSifre.getText().toString().getBytes());
                    fos_Sifre.close();
                }catch(Exception e){
                    e.printStackTrace();}


                pSifre.onEditorAction(IME_ACTION_DONE);//Klavye Gizleme
                Toast.makeText(AyarlarActivity.this,"Başarı İle Kaydedildi!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ID", pID.getText().toString());
                intent.putExtra("Sifre", pSifre.getText().toString());

                startActivity(intent);
                finish();
            }
        });
    }


}

