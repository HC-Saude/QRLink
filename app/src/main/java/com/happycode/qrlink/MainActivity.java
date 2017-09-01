package com.happycode.qrlink;

//Atividade principal do app. Lembrete: para atualizar a base de dados devemos editá-la diretamente
//na pasta assets/databases/paths.db utilizando algum programa como o DB Browser, e devemos também
//lembrar de incrementar a versão da db no DatabaseOpenHelper!

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.File;
import java.util.List;

public class MainActivity extends Activity  {

    private  String sDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);                            //SETANDO FULLSCREEN
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);



        DatabaseAccess db = DatabaseAccess.getInstance(this);
                                                                            //acessando a db
        db.open();

        dbEntry item;
        item = db.getEntry("init");

        db.close();                                                             //fechando a db

        sDir=Environment.getExternalStorageDirectory().toString()+item.getLocal();      //setando o diretorio de videos


        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                 //função executada ao escanear um codigo
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();


                String src = selectVideo(result.getContents());                         //seleção do vídeo pelo codigo lido

                //Toast.makeText(this, "selected: " + src, Toast.LENGTH_LONG).show();

                if (src.equalsIgnoreCase("notFound")){                                  //erro de codigo não encontrado na db
                    Toast.makeText(this, "Código inválido!\nCódigo lido: " + result.getContents(), Toast.LENGTH_LONG).show();
                    return;
                }

                if (src.equalsIgnoreCase("NoURL")){                                     //erro de url
                    Toast.makeText(this, "URL faltando!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (src.startsWith("/")){                                               //checando se é um vídeo local
                    startPlayback(src);
                }
                else{
                    startYoutube(src);
                }




            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void logoClick(View v) {                                         //função do clique no logo

//        IntentIntegrator integrator = new IntentIntegrator(this);               //
//        integrator.setBeepEnabled(false);                                       //operações para inicializar e executar
//        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);    //o IntentIntegrator que executa o scan
//        integrator.initiateScan();                                              //

        Intent i = new Intent(getApplicationContext(), ArActivity.class);
        startActivity(i);


    }


    public String selectVideo(String video) {                               //função para selecionar o video
        DatabaseAccess db = DatabaseAccess.getInstance(this);               //a ser tocado pelo string parâmetro

        db.open();

        dbEntry item;                                                       //abertura, consulta e fechamento da db
        item = db.getEntry(video);
        db.close();

        if (item.getId().isEmpty()){                                        //caso o codigo escaneado não esteja na db
            return "notFound";
        }

        File att = new File(sDir+item.getLocal());

        if (att.exists()){
            return (sDir+item.getLocal());                                  //checando se o arquivo local de vídeo existe
        }
        else{                                                               //se não houver vídeo local
            if (item.getUrl().isEmpty() || item.getUrl().equalsIgnoreCase("NoURL")){
                return "noURL";                                             //erro de url inexistente na db
            }
            else {
                return (item.getUrl());
            }
        }



    }

    public void startPlayback(String path) {                                    //função para iniciar a atividade
                                                                                //do player local (videoview)
        Intent i = new Intent(getApplicationContext(), Playback.class);

        i.putExtra("src",path);                                                 //colocando o caminho do arquivo no intent
                                                                                //para acessá-lo na outra atividade
        startActivity(i);

    }

    public void startYoutube(String path) {                                     //função para iniciar a atividade
                                                                                //do player do youtube
        Intent i = new Intent(getApplicationContext(), YoutubePlayback.class);

        i.putExtra("ID",path);                                                  //colocando o ID do video no intent
                                                                                //para acessá-lo na outra atividade
        startActivity(i);
    }

    private boolean canResolveIntent(Intent intent) {
        List<ResolveInfo> resolveInfo = getPackageManager().queryIntentActivities(intent, 0);
        return resolveInfo != null && !resolveInfo.isEmpty();
    }

}