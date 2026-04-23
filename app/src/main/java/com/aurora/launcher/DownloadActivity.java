package com.aurora.launcher;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView porcentagem;
    private int progress = 0;
    private boolean carregamentoIniciado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.progressBar);
        porcentagem = findViewById(R.id.txtPorcentagem);

        if (progressBar == null || porcentagem == null) {
            finish();
            return;
        }

        verificarPermissoesEContinuar();
    }

    private void verificarPermissoesEContinuar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } catch (Exception e) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);
                }
                return;
            }
        }

        criarPastaVisivel();
        iniciarDownloadFalso();
    }

    private void criarPastaVisivel() {
        File pasta = new File("/storage/emulated/0/RavenCityRoleplay");
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!carregamentoIniciado) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    criarPastaVisivel();
                    iniciarDownloadFalso();
                }
            }
        }
    }

    private void iniciarDownloadFalso() {
        if (carregamentoIniciado) return;
        carregamentoIniciado = true;

        Handler handler = new Handler(Looper.getMainLooper());

        new Thread(() -> {
            while (progress < 100) {
                progress++;
                try {
                    Thread.sleep(40);
                } catch (Exception ignored) {
                }

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
