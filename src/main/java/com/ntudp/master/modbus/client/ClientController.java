package com.ntudp.master.modbus.client;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import de.re.easymodbus.modbusclient.ModbusClient;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;


public class ClientController {

    private final ModbusClient client = new ModbusClient();
    private final ClientService service = new ClientService();
    private Timeline timeline = new Timeline();

    @FXML
    private JFXToggleButton firstToggle;
    @FXML
    private JFXToggleButton secondToggle;
    @FXML
    private JFXButton connectionBtn;
    @FXML
    private JFXButton exitBtn;
    @FXML
    private JFXTextField ipField;
    @FXML
    private JFXTextField portField;
    @FXML
    private Label headLabel;

    public void connect(ActionEvent event) {
        if (connectionBtn.getText().equals("CONNECT")) {
            service.init(client, ipField.getText(), portField.getText());
            service.connect(client);
            setConnectStatus();
            updateStatus();
        } else if (connectionBtn.getText().equals("DISCONNECT")) {
            try {
                service.disconnect(client);
                setDisconnectedStatus();
                timeline.stop();
            } catch (Exception e) {
                headLabel.setText(e.getMessage());
            }

        }
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    public void toggleAction(ActionEvent event) {
        if (firstToggle.isSelected()) {
            service.setCoilValue(client, 0, true);
        } else {
            service.setCoilValue(client, 0, false);
        }

        if (secondToggle.isSelected()) {
            service.setCoilValue(client, 1, true);
        } else {
            service.setCoilValue(client, 1, false);
        }
    }

    private void setConnectStatus() {
        headLabel.setText("CONNECTED");
        connectionBtn.setStyle("-fx-background-color: red");
        connectionBtn.setText("DISCONNECT");
        firstToggle.setDisable(false);
        secondToggle.setDisable(false);
        ipField.setEditable(false);
        portField.setEditable(false);
    }


    private void setDisconnectedStatus() {
        headLabel.setText("CLIENT");
        connectionBtn.setStyle("-fx-background-color: black");
        connectionBtn.setText("CONNECT");
        firstToggle.setDisable(true);
        secondToggle.setDisable(true);
        ipField.setEditable(true);
        portField.setEditable(true);
    }


    private void updateStatus() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1.0D), event -> {
            try {
                boolean[] toggleState = client.ReadCoils(0, 2);
                System.out.println("First coil value: " + toggleState[0] + " \n" +
                        "Second coil value: " + toggleState[1]);
                firstToggle.setSelected(toggleState[0]);
                secondToggle.setSelected(toggleState[1]);
            } catch (Exception e) {
                headLabel.setText("UNKNOWN ERROR");
                System.out.println(e.getMessage());
            }
        }
        ));
        timeline.setCycleCount(-1);
        timeline.play();
    }
}