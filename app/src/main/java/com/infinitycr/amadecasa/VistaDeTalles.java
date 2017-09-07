package com.infinitycr.amadecasa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VistaDeTalles extends AppCompatActivity implements View.OnClickListener {

    Button btnTalleRopaInteriorMujer;
    Button btnTalleCorpinios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_de_talles);

        btnTalleRopaInteriorMujer = (Button) findViewById(R.id.btnTalleRopaInteriorMujer);
        btnTalleRopaInteriorMujer.setOnClickListener(this);
        btnTalleCorpinios = (Button) findViewById(R.id.btnTalleCorpinios);
        btnTalleCorpinios.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTalleRopaInteriorMujer:
                Intent intent = new Intent(this, TalleRopaInteriorMujer.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
            case R.id.btnTalleCorpinios:
                Intent intent2 = new Intent(this, TalleCoripinio.class);   //busca la pantalla q va a abrir
                startActivity(intent2);  //ABRE LA ACTIVITY
                break;
        }
    }
}
