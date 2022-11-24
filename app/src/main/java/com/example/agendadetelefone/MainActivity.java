package com.example.agendadetelefone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

        EditText editText_Nome,editText_Telefone;
        Button button_salvar, button_consultar, button_fechar;

        SQLiteDatabase bd=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_Nome =(EditText) findViewById(R.id.editText_Nome);
        editText_Telefone =(EditText) findViewById(R.id.editText_Telefone);
        button_salvar = (Button) findViewById(R.id.button_Salvar);
        button_consultar = (Button) findViewById(R.id.button_Consultar);
        button_fechar = (Button) findViewById(R.id.button_Fechar);

        abrirBD();
        abrirOuCriarTabela();
        fecharBD();
    }

    public void abrirBD() {
        try {
            bd = openOrCreateDatabase("agendaTelefone", MODE_PRIVATE, null);
        } catch (Exception ex) {
            msg("Erro ao abrir ou criar banco de dados");
        }
    }

    public void abrirOuCriarTabela(){
            try {
                bd.execSQL("CREATE TABLE IF NOT EXISTS contatos (id INTEGER PRIMARY KEY, nome TEXT,telefone INTEGER);");

            } catch (Exception ex) {
                msg("Erro ao criar tabela");
            }
    }
    public void fecharBD(){
        bd.close();
    }
    public void inserirRegistro(View v){
        String st_nome,st_telefone;
        st_nome = editText_Nome.getText().toString();
        st_telefone = editText_Telefone.getText().toString();
        if (st_nome=="" || st_telefone==""){
            msg("Compos devem ser preenchidos");
            return;
        }
        abrirBD();
        try {
            bd.execSQL("INSERT INTO contatos (nome,telefone)VALUES('" + st_nome + "','" + st_telefone + "')");
        }catch (Exception ex){
            msg("Erro ao inserir contato");
        }finally {
            msg("Contato inserido");
        }
        fecharBD();
        editText_Nome.setText(null);
        editText_Telefone.setText(null);
    }

    public void abrir_tela_consultar(View v){
        Intent it_tela_consultar= new Intent(this,Consultar.class);
        startActivity(it_tela_consultar);
    }
    public void fechar_tela(View v) {
        this.finish();
    }
    public void msg(String txt){
        AlertDialog.Builder alertDBuilder=new AlertDialog.Builder(this);
        alertDBuilder.setMessage(txt);
        alertDBuilder.setNeutralButton("OK" ,null);
        alertDBuilder.show();
    }
}