package com.svalero;

import javafx.concurrent.Task;
import javafx.scene.control.*;

public class CronometroTask extends Task<Integer> {

   private int tiempoInicial, tiempoFinal;
   private boolean pausado;

    public CronometroTask(int tiempoInicial,int tiempoFinal){
        this.tiempoInicial = tiempoInicial;
        this.tiempoFinal = tiempoFinal;
        pausado = false;
    }

    public boolean isPausado() {
        return pausado;
    }

    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    public int getTiempoInicial() {
        return tiempoInicial;
    }

    public void setTiempoInicial(int tiempoInicial) {
        this.tiempoInicial = tiempoInicial;
    }

    public int getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(int tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    @Override
    protected Integer call() throws Exception {
        updateMessage("Creando cuenta...");

        if (tiempoInicial > tiempoFinal) {
            for (int i = tiempoInicial; i >= tiempoFinal; i--) {

                Thread.sleep(1000);

                while (pausado) {
                    Thread.sleep(1000);
                }

                if (isCancelled()){
                    break;
                }
                int porcentaje = (int) (i * 100 / tiempoInicial);

                if (porcentaje == 50) {
                    updateMessage(i + "\n" + porcentaje + " % Completado. Vas por el 50% o lo has pasado");
                }else if (porcentaje == 75 || porcentaje > 75 && porcentaje <= 80) {
                    updateMessage(i + "\n" + porcentaje + " % Completado. Vas por el 75% o lo has pasado");
                } else{
                    updateMessage(i + "\n" + porcentaje + " % Completado");
                }
                //updateProgress(progreso, tiempoInicial);
                updateProgress(i, tiempoInicial);

            }
        } else {

            for (int i = tiempoInicial; i <= tiempoFinal; i++) {
                double progreso = (double) i / (double) tiempoFinal;
                Thread.sleep(1000);

                while (pausado) {
                    Thread.sleep(1000);
                }

                if (isCancelled()){
                    break;
                }
                int porcentaje = i * 100 / tiempoFinal;
                updateProgress(progreso, 1);
                //int porcentaje = i * 100 / tiempoFinal;
                if (porcentaje == 50) {
                    updateMessage(i + " de " + tiempoFinal + "\n" + porcentaje + " % Completado. Vas por el 50% o lo has pasado");
                }else if (porcentaje == 75 || porcentaje > 75 && porcentaje <= 80) {
                    updateMessage(i + " de " + tiempoFinal + "\n" + porcentaje + " % Completado. Vas por el 75% o lo has pasado");
                } else{
                    updateMessage(i + " de " + tiempoFinal + "\n" + porcentaje + " % Completado");
                }
            }
        }
        updateProgress(1, 1);
        updateMessage("Cuenta finalizada");
        return null;
    }
}
