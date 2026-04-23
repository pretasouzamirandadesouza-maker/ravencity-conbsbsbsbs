package com.aurora.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    private Button conectar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        escreverLog("MainActivity abriu");

        conectar = findViewById(R.id.btnConectar);

        if (conectar == null) {
            escreverLog("ERRO: botão não encontrado");
            return;
        }

        conectar.setOnClickListener(v -> {
            escreverLog("Clicou em entrar no servidor");

            try {
                // 🔥 TESTE: abrir tela simples
                startActivity(new Intent(MainActivity.this, AvisoActivity.class));

                escreverLog("Abriu AvisoActivity com sucesso");

            } catch (Exception e) {
                escreverLog("ERRO ao abrir AvisoActivity: " + e.toString());
                e.printStackTrace();
            }
        });
    }

    private void escreverLog(String texto) {
        try {
            File pasta = getExternalFilesDir(null);
            if (pasta == null) return;

            File logFile = new File(pasta, "log.txt");

            FileWriter writer = new FileWriter(logFile, true);
            writer.append(texto).append("\n");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
