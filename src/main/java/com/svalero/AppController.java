package com.svalero;

import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AppController {

    public Button btnIniciar, btnParar, btnPausar;
    public ProgressBar pbCronometro;
    public Label lblTiempo;
    public TextField tfTiempoInicial, tfTiempoFinal;

    private int tiempoInicial, tiempoFinal;
    private CronometroTask cronometroTask;

    @FXML
    public void iniciar(Event event){
        tiempoInicial = Integer.parseInt(tfTiempoInicial.getText());
        tiempoFinal = Integer.parseInt(tfTiempoFinal.getText());

        if (tiempoInicial > tiempoFinal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("VALOR INCORRECTOS");
            alert.setContentText("Santi, he hecho la comprobración :D");
            alert.show();
            return;
        }
        cronometroTask = new CronometroTask(tiempoInicial, tiempoFinal);

        cronometroTask.stateProperty().addListener((observableValue, state, t1) -> {
            if (t1 == Worker.State.RUNNING){
                pbCronometro.progressProperty().unbind();
                pbCronometro.progressProperty().bind(cronometroTask.progressProperty());

            }else if (t1 == Worker.State.SUCCEEDED){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TAREA FINALIZADA");
                alert.setContentText("YA HA TERMINADO LA CUENTA DE FORMA NATURAL");
                alert.show();
                lblTiempo.setText("FIN \n100%");
                tfTiempoFinal.clear();
                tfTiempoInicial.clear();
                tfTiempoInicial.requestFocus();
            }
        });
        cronometroTask.messageProperty().addListener((observableValue, s, t1) -> {
            lblTiempo.setText(t1);
        });
        new Thread(cronometroTask).start();
    }

    @FXML
    public void iniciarDesc(Event event){
        tiempoInicial = Integer.parseInt(tfTiempoInicial.getText());
        tiempoFinal = Integer.parseInt(tfTiempoFinal.getText());

        if (tiempoInicial < tiempoFinal){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("VALOR INCORRECTOS");
            alert.setContentText("Santi, he vuelto a hacer la comprobración :D");
            alert.show();
            return;
        }
        cronometroTask = new CronometroTask(tiempoInicial, tiempoFinal);

        cronometroTask.stateProperty().addListener((observableValue, state, t1) -> {
            if (t1 == Worker.State.RUNNING){
                pbCronometro.progressProperty().unbind();
                pbCronometro.progressProperty().bind(cronometroTask.progressProperty());

            }else if (t1 == Worker.State.SUCCEEDED){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("TAREA FINALIZADA");
                alert.setContentText("YA HA TERMINADO LA CUENTA DE FORMA NATURAL");
                alert.show();
                lblTiempo.setText("FIN \n0%");
                tfTiempoFinal.clear();
                tfTiempoInicial.clear();
                tfTiempoInicial.requestFocus();
            }
        });
        cronometroTask.messageProperty().addListener((observableValue, s, t1) -> {
            lblTiempo.setText(t1);
        });
        new Thread(cronometroTask).start();
    }

    @FXML
    public void pausar(Event event){
        if (!cronometroTask.isPausado()){
            cronometroTask.setPausado(true);
            btnPausar.setText("Continuar");
            lblTiempo.setText("Cuenta pausada");
        } else if (cronometroTask.isPausado()) {
            cronometroTask.setPausado(false);
            btnPausar.setText("Pausar");
        }
    }

    @FXML
    public void parar(Event event){
        if (cronometroTask.stateProperty().getValue() == Worker.State.RUNNING){
            cronometroTask.cancel();
            lblTiempo.setText("CANCELADA");
        }
    }


}
