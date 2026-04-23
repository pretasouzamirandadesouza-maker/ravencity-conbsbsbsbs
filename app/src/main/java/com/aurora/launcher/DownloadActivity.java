package com.aurora.launcher;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView porcentagem;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        // 1. Pedir Permissão e Criar a Pasta Primeiro
        verificarPermissoes();

        progressBar = findViewById(R.id.progressBar);
        porcentagem = findViewById(R.id.txtPorcentagem);

        if (progressBar == null || porcentagem == null) {
            finish();
            return;
        }

        iniciarDownloadFalso();
    }

    private void verificarPermissoes() {
        // Para Android 11 ou superior, abre a tela de "Acesso a todos os arquivos"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivity(intent);
            }
        }

        // Cria a pasta física na memória interna
        File pasta = new File(Environment.getExternalStorageDirectory(), "com.aurora.launcher");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    private void iniciarDownloadFalso() {
        Handler handler = new Handler();
        new Thread(() -> {
            while (progress < 100) {
                progress++;
                try {
                    Thread.sleep(40);
                } catch (Exception ignored) {}

                int finalProgress = progress;
                handler.post(() -> {
                    progressBar.setProgress(finalProgress);
                    porcentagem.setText(finalProgress + "%");
                });
            }

            runOnUiThread(() -> {
                startActivity(new Intent(DownloadActivity.this, MainActivity.class));
                finish();
            });
        }).start();
    }
}
