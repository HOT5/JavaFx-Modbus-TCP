package com.ntudp.master.modbus.client;

import de.re.easymodbus.modbusclient.ModbusClient;
/*    */ import java.io.IOException;

public class ClientService {

    public void init(ModbusClient client, String ip, String stringPort) {
        int port = Integer.parseInt(stringPort);
        if (ip.matches("\\b(?:\\d{1,3}\\.){3}\\d{1,3}")) {
            if (stringPort.matches("\\d+") && port > 1023 && port < 65536) {
                client.setipAddress(ip);
                client.setPort(port);
            } else {
                throw new IllegalArgumentException("INVALID PORT NUMBER");
            }
        } else {
            throw new IllegalArgumentException("INVALID IP ADDRESS");
        }
    }

    public void connect(ModbusClient client) {
        try {
            client.Connect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect(ModbusClient client) throws IOException {
        client.Disconnect();
    }

    public void setCoilValue(ModbusClient client, int coil, boolean value) {
        try {
            client.WriteSingleCoil(coil, value);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
