package com.software.pitagora_app_201;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.software.pitagora_app_201.model.Persona;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {

    public List<String> listCorreos = new ArrayList<String>();
    public List<String> listNumeros = new ArrayList<String>();
    boolean[] flag;
    EditText nomP, appP, correoP, passwordP, numP;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        nomP = findViewById(R.id.text_Nombre);
        appP = findViewById(R.id.text_Apellido);
        numP = findViewById(R.id.text_Numero);
        correoP = findViewById(R.id.text_correo_registro);
        passwordP = findViewById(R.id.text_contraseña_registro);

        inicializarFirebase();


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void limpiarCajas() {
        nomP.setText("");
        correoP.setText("");
        passwordP.setText("");
        numP.setText("");
        appP.setText("");
    }

    private void validacion() {
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();
        String app = appP.getText().toString();
        String numero = numP.getText().toString();
        if (nombre.equals("")){
            nomP.setError("Required");
        }
        else if (app.equals("")){
            appP.setError("Required");
        }
        else if (correo.equals("")){
            correoP.setError("Required");
        }
        else if (password.equals("")){
            passwordP.setError("Required");
        }
        else if (numero.equals("")){
            passwordP.setError("Required");
        }
    }

    public void onClick(View v) {
        //aca meti man
        String nombre = nomP.getText().toString();
        String correo = correoP.getText().toString();
        String password = passwordP.getText().toString();
        String numero = numP.getText().toString();
        String app = appP.getText().toString();
        switch (v.getId()) {

            case R.id.btn_register: {

                if (nombre.equals("") || correo.equals("") || password.equals("") || app.equals("") || numero.equals("") || app.equals("")) {
                    validacion();
                }
                else {

                    databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nombre1 = nomP.getText().toString();
                            String correo1 = correoP.getText().toString();
                            String password1 = passwordP.getText().toString();
                            String numero1 = numP.getText().toString();
                            String app1 = appP.getText().toString();
                            listCorreos.clear();
                            listNumeros.clear();
                            for (DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                                Persona p = objSnaptshot.getValue(Persona.class);
                                listCorreos.add(p.getCorreo());
                                listNumeros.add(p.getNumero());

                            }

                            /*for (String src : listCorreos){ Log.d("correo es:", src);}
                            for (String src : listNumeros){ Log.d("numero es:", src);}*/

                            if(listNumeros.contains(numero1) || listCorreos.contains(correo1)){
                                if(listNumeros.contains(numero1)) {
                                    Log.d("tagsalida", "si entra");
                                    numP.setError("Numero existente");
                                }
                                else{
                                    correoP.setError("Correo existente");
                                }


                            }else{
                                if(numP.getText().toString().equals("") || correoP.getText().toString().equals("")){
                                    //validacion();
                                    //listNumeros.clear();
                                    //listCorreos.clear();
                                    //limpiarCajas();
                                }else{
                                    Log.d("agregar", "agrego");
                                    Persona p = new Persona();
                                    //limpiarCajas();
                                    String nombr = nomP.getText().toString();
                                    String corre = correoP.getText().toString();
                                    String passwor = passwordP.getText().toString();
                                    String numer = numP.getText().toString();
                                    String ap = appP.getText().toString();

                                    p.setLocalid(UUID.randomUUID().toString());
                                    p.setNombre(nombr);
                                    p.setNumero(numer);
                                    p.setCorreo(corre);
                                    p.setPassword(passwor);
                                    p.setApellido(ap);
                                    databaseReference.child("Persona").child(p.getLocalid()).setValue(p);
                                    //cargarDatosFirebase(nombre, apellido, telefono, direccion);
                                    agregar();
                                    limpiarCajas();
                                    //listNumeros.clear();
                                    //listCorreos.clear();

                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
                    break;
                }
                break;
            }

        }

    }
    private void agregar(){
        Toast.makeText(this, "Agregado", Toast.LENGTH_LONG).show();
    }
