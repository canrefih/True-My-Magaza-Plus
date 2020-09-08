package com.truebilisim.mymagaza;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {


    private WebView webView;
    private CustomWebViewClient webViewClient;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.btngiris:

                    Intent intend = getIntent();
                    String pID = getIntent().getStringExtra("ID");
                    String pSifre = getIntent().getStringExtra("Sifre");


                        if (pID == null) {
                            try {
                                FileInputStream fis_ID = openFileInput("pID");
                                InputStreamReader isr_ID = new InputStreamReader(fis_ID);
                                BufferedReader br_ID = new BufferedReader(isr_ID);
                                pID = br_ID.readLine();
                                fis_ID.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                FileInputStream fis_Sifre = openFileInput("pSifre");
                                InputStreamReader isr_Sifre = new InputStreamReader(fis_Sifre);
                                BufferedReader br_Sifre = new BufferedReader(isr_Sifre);
                                pSifre = br_Sifre.readLine();
                                fis_Sifre.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        webView.loadUrl("http://www.truebilisim.com/myiphone/index.php?id=" + pID + "&sifre=" + pSifre);


                    return true;
                case R.id.btnayarlar:
                    Intent intent = new Intent(getApplicationContext(), AyarlarActivity.class);
                    startActivity(intent);
                    return true;

                case R.id.btnbarkod:
                    Intent indent = new Intent(getApplicationContext(), BarkodActivity.class);
                    startActivity(indent);
                    return true;
            }
            return false;
        }
    };

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Bu method açılan sayfa içinden başka linklere tıklandığında açılmasına yarıyor.
            //Bu methodu override etmez yada edip içini boş bırakırsanız ilk url den açılan sayfa dışında başka sayfaya geçiş yapamaz

            view.loadUrl(url);//yeni tıklanan url i açıyor
            return true;
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState ){
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = findViewById(R.id.webView);
        webViewClient = new CustomWebViewClient();
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(webViewClient);

        if (savedInstanceState != null)
            ((WebView)findViewById(R.id.webView)).restoreState(savedInstanceState);
        else {

            Intent intend = getIntent();
            String pID = getIntent().getStringExtra("ID");
            String pSifre = getIntent().getStringExtra("Sifre");
            String pBarkod = getIntent().getStringExtra("Barkod");

            if (pID==null){
                try{
                    FileInputStream fis_ID = openFileInput("pID");
                    InputStreamReader isr_ID = new InputStreamReader(fis_ID);
                    BufferedReader br_ID = new BufferedReader(isr_ID);
                    pID = (String) br_ID.readLine();
                    fis_ID.close();
                }catch(Exception e){
                    e.printStackTrace(); }

                try{
                    FileInputStream fis_Sifre = openFileInput("pSifre");
                    InputStreamReader isr_Sifre = new InputStreamReader(fis_Sifre);
                    BufferedReader br_Sifre = new BufferedReader(isr_Sifre);
                    pSifre = br_Sifre.readLine();
                    fis_Sifre.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pBarkod!=null) {
                webView.loadUrl("http://www.truebilisim.com/myiphone/true/mymagazaplus/barkod.php?barkod="+pBarkod);
                getIntent().removeExtra("pBarkod");
            }
            else {
                webView.loadUrl("http://www.truebilisim.com/myiphone/index.php?id=" + pID + "&sifre=" + pSifre);

            }
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
@Override
    protected void onStart(){
        super.onStart();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.getMenu().getItem(0).setChecked(true);
}



}
