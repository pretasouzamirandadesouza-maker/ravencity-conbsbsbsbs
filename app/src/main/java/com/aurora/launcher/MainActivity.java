package com.aurora.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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
            escreverLog("ERRO: btnConectar não encontrado no layout");
            Toast.makeText(this, "Erro: botão não encontrado", Toast.LENGTH_LONG).show();
            return;
        }

        conectar.setOnClickListener(v -> {
            escreverLog("Clicou em entrar no servidor");

            try {
                Intent intent = new Intent(MainActivity.this, SAMP.class);
                startActivity(intent);
                escreverLog("Tentou abrir SAMP");
            } catch (Exception e) {
                escreverLog("ERRO ao abrir SAMP: " + e.toString());
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "Erro ao abrir o jogo", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void escreverLog(String texto) {
        try {
            File pasta = getExternalFilesDir(null);
            if (pasta == null) {
                return;
            }

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
