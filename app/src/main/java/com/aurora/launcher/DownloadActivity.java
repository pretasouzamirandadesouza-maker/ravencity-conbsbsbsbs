package com.aurora.launcher;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class DownloadActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView porcentagem;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.progressBar);
        porcentagem = findViewById(R.id.txtPorcentagem);

        // 🔥 CRIAR PASTA AUTOMÁTICA
        File pasta = getExternalFilesDir(null);
        if (pasta != null) {
            File files = new File(pasta, "files");
            File samp = new File(pasta, "SAMP");

            if (!files.exists()) files.mkdirs();
            if (!samp.exists()) samp.mkdirs();
        }

        Handler handler = new Handler();

        new Thread(() -> {
            while (progress < 100) {
                progress++;
                try { Thread.sleep(40); } catch (Exception ignored) {}

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
