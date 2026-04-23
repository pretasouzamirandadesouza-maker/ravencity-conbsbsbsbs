package com.aurora.launcher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DownloadActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    ProgressBar progressBar;
    TextView porcentagem;
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        progressBar = findViewById(R.id.progressBar);
        porcentagem = findViewById(R.id.txtPorcentagem);

        if (temPermissao()) {
            iniciarCarregamento();
        } else {
            pedirPermissao();
        }
    }

    private boolean temPermissao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
        }

        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED;
    }

    private void pedirPermissao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    STORAGE_PERMISSION_CODE
            );
        } else {
            iniciarCarregamento();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            boolean permitido = true;

            if (grantResults.length == 0) {
                permitido = false;
            } else {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        permitido = false;
                        break;
                    }
                }
            }

            if (permitido) {
                iniciarCarregamento();
            } else {
                finish();
            }
        }
    }

    private void iniciarCarregamento() {
        // força criação da pasta do app
        getExternalFilesDir(null);

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
