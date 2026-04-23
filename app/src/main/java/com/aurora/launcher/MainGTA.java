package com.aurora.launcher;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;

import com.aurora.launcher.gui.util.Utils;
import com.wardrumstudios.utils.WarMedia;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainGTA extends WarMedia {

    public static MainGTA self = null;
    static String vmVersion;
    private boolean once = false;

    // Definição do caminho da pasta raiz do seu launcher
    private static final String CAMINHO_RAIZ = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.aurora.launcher";

    static {
        vmVersion = null;
        System.out.println("**** Loading SO's");

        // 1. CARREGANDO A BASS (Obrigatória para o áudio)
        try {
            System.loadLibrary("bass");
            System.out.println("bass OK");
        } catch (Throwable t) {
            System.out.println("Erro crítico: libbass não encontrada!");
            t.printStackTrace();
        }

        try {
            vmVersion = System.getProperty("java.vm.version");
            System.out.println("vmVersion " + vmVersion);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        try {
            System.loadLibrary("ImmEmulatorJ");
            System.out.println("ImmEmulatorJ OK");
        } catch (Throwable t) {
            System.out.println("Erro ao carregar ImmEmulatorJ");
            t.printStackTrace();
        }

        try {
            System.loadLibrary("GTASA");
            System.out.println("GTASA OK");
        } catch (Throwable t) {
            System.out.println("Erro ao carregar GTASA");
            t.printStackTrace();
        }

        try {
            System.loadLibrary("samp");
            System.out.println("samp OK");
        } catch (Throwable t) {
            System.out.println("Erro ao carregar samp");
            t.printStackTrace();
        }
    }

    // Método de log ajustado para salvar na pasta visível do launcher
    private void log(String texto) {
        try {
            File pasta = new File(CAMINHO_RAIZ);
            if (!pasta.exists()) pasta.mkdirs();

            File logFile = new File(pasta, "log_gta.txt");
            FileWriter writer = new FileWriter(logFile, true);
            writer.append(texto).append("\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void installCrashLogger() {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try {
                File pasta = new File(CAMINHO_RAIZ);
                if (!pasta.exists()) pasta.mkdirs();

                File crashFile = new File(pasta, "crash_gta.txt");
                FileWriter writer = new FileWriter(crashFile, true);

                writer.append("THREAD: ").append(thread.getName()).append("\n");
                writer.append("ERRO: ").append(String.valueOf(throwable)).append("\n");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                throwable.printStackTrace(pw);

                writer.append(sw.toString()).append("\n");
                writer.append("------------\n");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public boolean ServiceAppCommand(String str, String str2) {
        return false;
    }

    public int ServiceAppCommandValue(String str, String str2) {
        return 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        log("onActivityResult req=" + requestCode + " result=" + resultCode);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        log("onConfigurationChanged");
    }

    @Override
    public void onCreate(Bundle bundle) {
        installCrashLogger();
        log("MainGTA onCreate inicio");

        if (!once) {
            once = true;
            log("Primeira execucao MainGTA");
        }

        System.out.println("MainGTA onCreate");
        self = this;
        wantsMultitouch = true;
        wantsAccelerometer = true;

        super.onCreate(bundle);

        try {
            Utils.currentContext = this;
            log("Utils.currentContext definido");
        } catch (Throwable t) {
            log("Erro ao definir Utils.currentContext: " + t);
            t.printStackTrace();
        }

        log("MainGTA onCreate fim");
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        log("onKeyDown: " + keyCode);
        return super.onKeyDown(keyCode, keyEvent);
    }

    @Override
    public void onPause() {
        log("onPause");
        super.onPause();
    }

    @Override
    public void onRestart() {
        log("onRestart");
        super.onRestart();
    }

    @Override
    public void onResume() {
        log("onResume");
        super.onResume();
    }

    @Override
    public void onStart() {
        log("onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        log("onStop");
        super.onStop();
    }
}
