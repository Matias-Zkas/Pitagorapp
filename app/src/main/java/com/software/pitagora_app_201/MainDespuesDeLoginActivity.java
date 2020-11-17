package com.software.pitagora_app_201;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.software.pitagora_app_201.model.Persona;

import java.io.Serializable;

public class MainDespuesDeLoginActivity extends AppCompatActivity {
TextView nombre;
TextView puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_despues_de_login);
        Button btn_start = (Button) findViewById(R.id.btn_Start_after);
        Button btn_glosario = (Button) findViewById(R.id.btn_glosary_after);

        nombre= (TextView) findViewById(R.id.text_nombre_usuario);
        puntaje= (TextView) findViewById(R.id.text_puntaje);
        Persona  usuarioLog = (Persona) getIntent().getSerializableExtra("usuario");
        nombre.setText(usuarioLog.getNombre());
        puntaje.setText(usuarioLog.getPuntajeTotal());

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), SelectorNivelActivity.class);
                intent.putExtra("usuario", usuarioLog);
                startActivityForResult(intent, 0);
            }
        });

        btn_glosario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), GlosarioInicioActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}