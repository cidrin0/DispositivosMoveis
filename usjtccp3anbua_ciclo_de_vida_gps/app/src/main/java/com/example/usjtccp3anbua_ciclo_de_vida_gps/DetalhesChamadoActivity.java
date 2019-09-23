package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalhesChamadoActivity extends AppCompatActivity {

    private TextView descricaoChamadoTextView;
    private  TextView nomeFilaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_chamado);
        descricaoChamadoTextView = findViewById(R.id.descricaoChamadoTextView);
        nomeFilaTextView = findViewById(R.id.nomeFilaTextView);

        Intent intentOrigem = getIntent();
        String chamadoSelecionado = intentOrigem.getStringExtra("chamado_selecionado");

        String[] partes = chamadoSelecionado.split(":");
        String nome = partes[0];
        String descricaoChamado = partes[1];

        nomeFilaTextView.setText(nome);
        descricaoChamadoTextView.setText(descricaoChamado);
    }
}
