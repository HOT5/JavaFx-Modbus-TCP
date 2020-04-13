
package com.ntudp.master.modbus.server;

import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXTextField;
/*     */ import com.jfoenix.controls.JFXToggleButton;
/*     */ import de.re.easymodbus.server.ICoilsChangedDelegator;
/*     */ import de.re.easymodbus.server.ModbusServer;
/*     */ import javafx.animation.FadeTransition;
/*     */
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.fxml.FXML;
/*     */ import javafx.scene.control.Label;
/*     */
/*     */
/*     */ import javafx.util.Duration;

import java.io.IOException;
import java.net.ServerSocket;


public class ServerController implements ICoilsChangedDelegator {

    private ModbusServer server = new ModbusServer();
    private ServerService service = new ServerService();

    @FXML
    private JFXToggleButton firstToggle;
    @FXML
    private JFXToggleButton secondToggle;
    private FadeTransition fadeOut = new FadeTransition(Duration.millis(3000.0D));
    @FXML
    private JFXButton startServerBtn;
    @FXML
    private JFXButton exitBtn;
    @FXML
    private JFXTextField ipField;
    @FXML
    private JFXTextField portField;
    @FXML
    private Label headLabel;

    @FXML
    public void initialize() throws IOException {
        server.setNotifyCoilsChanged(this);
        portField.setText(generateEmptyPort());
        fadeOut.setFromValue(1.0D);
        fadeOut.setToValue(0.0D);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
    }

    public void start(ActionEvent event) {
        try {
            service.init(server, portField.getText());
            service.start(server);
            setServerStatus();
        } catch (Exception e) {
            headLabel.setText(e.getMessage());
        }
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }


    public void toggleAction(ActionEvent event) {
        if (firstToggle.isSelected()) {
            service.setCoilValue(server, 1, true);
        } else {
            service.setCoilValue(server, 1, false);
        }

        if (secondToggle.isSelected()) {
            service.setCoilValue(server, 2, true);
        } else {
            service.setCoilValue(server, 2, false);
        }
    }

    private void setServerStatus() {
        headLabel.setText("SERVER STARTED");
        firstToggle.setDisable(false);
        secondToggle.setDisable(false);
        portField.setEditable(false);
        startServerBtn.setDisable(true);
    }

    public void coilsChangedEvent() {
        firstToggle.setSelected(server.coils[1]);
        secondToggle.setSelected(server.coils[2]);
    }

    private String generateEmptyPort() throws IOException {
        String port;
        ServerSocket socket = new ServerSocket(0);
        port = String.valueOf(socket.getLocalPort());
        socket.close();
        return port;
    }
}