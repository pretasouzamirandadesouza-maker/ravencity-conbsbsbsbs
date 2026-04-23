package com.aurora.launcher;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Pedir permissão e criar a pasta assim que abrir o app
        verificarPermissoesECriarPasta();

        Button conectar = findViewById(R.id.btnConectar);
        conectar.setOnClickListener(v -> {
            startActivity(new Intent(this, MainGTA.class));
        });
    }

    private void verificarPermissoesECriarPasta() {
        // Se for Android 11 (API 30) ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                // Abre a tela de configurações para o usuário permitir
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }

        // 2. Criar a pasta na raiz para ela "aparecer" no celular
        File pasta = new File(Environment.getExternalStorageDirectory(), "com.aurora.launcher");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }
}
