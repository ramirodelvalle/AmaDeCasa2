package com.infinitycr.amadecasa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VistaDeTalles extends AppCompatActivity implements View.OnClickListener {

    Button btnTalleRopaInteriorMujer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_de_talles);

        btnTalleRopaInteriorMujer = (Button) findViewById(R.id.btnTalleRopaInteriorMujer);
        btnTalleRopaInteriorMujer.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTalleRopaInteriorMujer:
                Intent intent = new Intent(this, TalleRopaInteriorMujer.class);   //busca la pantalla q va a abrir
                startActivity(intent);  //ABRE LA ACTIVITY
                break;
        }
    }
}
