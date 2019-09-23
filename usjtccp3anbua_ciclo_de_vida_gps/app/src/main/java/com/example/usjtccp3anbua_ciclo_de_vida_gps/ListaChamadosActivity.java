package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ListaChamadosActivity extends AppCompatActivity {

    private ListView chamadosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_chamados);
        chamadosListView = findViewById(R.id.chamadosListView);

        Intent intent = getIntent();
        String nomeFila = intent.getStringExtra("nome_fila");

        List<String> resultList = busca(nomeFila);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, resultList);
        chamadosListView.setAdapter(adapter);
        chamadosListView.setOnItemClickListener((adapterView, view, position, id) -> {
            String chamadoSelecionado = resultList.get(position);
            Intent intentDetalheChamado = new Intent(this, DetalhesChamadoActivity.class);
            intentDetalheChamado.putExtra("chamado_selecionado", chamadoSelecionado);
            startActivity(intentDetalheChamado);
        });
    }

    public List<String> busca(String nomeFila) {
        if(nomeFila == null || nomeFila.isEmpty())
            return geraListaChamados();

        return geraListaChamados().stream().
                filter(e -> e.toLowerCase().contains(nomeFila.toLowerCase())).
                collect(Collectors.toList());
    }

    public List<String> geraListaChamados() {
        return Arrays.asList(
                "Rede: Telefone quebrado",
                "Desktop: Telefone quebrado",
                "Telefonia: Telefone quebrado",
                "Servidores: Telefone quebrado",
                "Manutenção Sistema ERP: Telefone quebrado",
                "Projetos: Telefone quebrado",
                "Rede: Telefone quebrado"
        );
    }
}
