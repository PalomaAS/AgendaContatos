package com.example.agendadetelefone;

import static com.example.agendadetelefone.R.id.editText_Nome;
import static com.example.agendadetelefone.R.id.editText_Nome_Consultar;
import static com.example.agendadetelefone.R.id.editText_Telefone;
import static com.example.agendadetelefone.R.id.editText_Telefone_Consultar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Consultar extends AppCompatActivity {

    EditText ed_Nome, ed_Telefone;
    Button button_Anterior, button_Proximo, button_Voltar;

    SQLiteDatabase bd = null;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        ed_Nome = (EditText) findViewById(editText_Nome_Consultar);
        ed_Telefone = (EditText) findViewById(editText_Telefone_Consultar);
        button_Anterior = (Button) findViewById(R.id.button_Anterior);
        button_Proximo = (Button) findViewById(R.id.button_Proximo);
        button_Voltar = (Button) findViewById(R.id.button_Voltar);

        buscarDados();
    }

    public void voltar_tela(View v) {
        this.finish();
    }

    public void abrirBD() {
        try {
            bd = openOrCreateDatabase("agendaTelefone", MODE_PRIVATE, null);
        } catch (Exception ex) {
            msg("Erro ao abrir ou criar banco de dados");
        }
    }

    public void fecharBD() {
        bd.close();
    }

    public void buscarDados() {
        abrirBD();
        cursor = bd.query("contatos",

                new String[]{"nome", "telefone"},
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            mostrarDados();

        } else {
            msg("Nenhum registro encontrado");
        }
    }

    public void proximoRegistro(View v){
        try{
            cursor.moveToNext();
            mostrarDados();
        }catch (Exception ex){
            if(cursor.isAfterLast()) {
                msg("Não existem mais registros");
            }else{
                msg("Erro de registros");
            }
        }
    }

    public void anteriorRegistro(View v) {
        try{
            cursor.moveToPrevious();
            mostrarDados();
        }catch (Exception ex){
            if(cursor.isBeforeFirst()) {
                msg("Não existem mais registros");
            }else{
                msg("Erro de registros");
            }
        }
    }

    @SuppressLint("Range")
    public void mostrarDados(){
        ed_Nome.setText(cursor.getString(cursor.getColumnIndex("nome")));
        ed_Telefone.setText(cursor.getString(cursor.getColumnIndex("telefone")));

    }

    public void msg (String txt){
        AlertDialog.Builder alertDBuilder = new AlertDialog.Builder(this);
        alertDBuilder.setMessage(txt);
        alertDBuilder.setNeutralButton("OK", null);
        alertDBuilder.show();
    }



    }






