package com.aurora.launcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        escreverLog("MainActivity abriu");

        try {
            escreverLog("Abrindo MainGTA direto");

            Intent intent = new Intent(MainActivity.this, MainGTA.class);
            startActivity(intent);

        } catch (Exception e) {
            escreverLog("ERRO ao abrir MainGTA: " + e.toString());
            e.printStackTrace();
        }

        finish(); // fecha a MainActivity
    }

    private void escreverLog(String texto) {
        try {
            File pasta = getExternalFilesDir(null);
            if (pasta == null) return;

            File logFile = new File(pasta, "log.txt");

            FileWriter writer = new FileWriter(logFile, true);
            writer.append(texto + "\n");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
