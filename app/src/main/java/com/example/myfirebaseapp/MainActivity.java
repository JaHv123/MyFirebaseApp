package com.example.myfirebaseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirebaseapp.Models.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText nombrePersona, apellidosPersona,correoPersona,passwordPersona;
    ListView listVPersona;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombrePersona = findViewById(R.id.txt_NombrePersona);
        apellidosPersona = findViewById(R.id.txt_apellidosPersona);
        correoPersona = findViewById(R.id.txt_correoPersona);
        passwordPersona = findViewById(R.id.txt_passwordPersona);

        listVPersona = findViewById(R.id.lv_datosPersona);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_name,menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nombre = nombrePersona.getText().toString();
        String apellidos = apellidosPersona.getText().toString();
        String correo = correoPersona.getText().toString();
        String password = passwordPersona.getText().toString();


        switch (item.getItemId()){
            case R.id.icon_add:{
                if (nombre.equals("") || apellidos.equals("") || correo.equals("") || password.equals("")){
                    validacion();
                }else {
                    Persona p = new Persona();
                    p.setId(UUID.randomUUID().toString());
                    p.setNombre(nombre);
                    p.setApellidos(apellidos);
                    p.setCorreo(correo);
                    p.setPassword(password);

                    databaseReference.child("Persona").child(p.getId()).setValue(p);

                    Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT).show();
                    limpiarCajas();
                }
                break;
            }

            case R.id.icon_save:{
                Toast.makeText(this,"Actualizado",Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.icon_delete:{
                Toast.makeText(this,"Eliminado",Toast.LENGTH_SHORT).show();
                break;
            }

            default:break;

        }
        return true;
    }

    private void limpiarCajas() {
        nombrePersona.setText("");
        apellidosPersona.setText("");
        correoPersona.setText("");
        passwordPersona.setText("");
    }

    private void validacion() {
        String nombre = nombrePersona.getText().toString();
        String apellidos = apellidosPersona.getText().toString();
        String correo = correoPersona.getText().toString();
        String password = passwordPersona.getText().toString();


        if (nombre.equals("")){
            nombrePersona.setError("Required");
        }else if (apellidos.equals("")){
            apellidosPersona.setError("Required");
        }else if (correo.equals("")){
            correoPersona.setError("Required");
        }else if (password.equals("")){
            passwordPersona.setError("Required");
        }
    }
}
