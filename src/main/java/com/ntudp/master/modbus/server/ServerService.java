package com.ntudp.master.modbus.server;

import de.re.easymodbus.server.ModbusServer;

import java.io.IOException;

public class ServerService {
    public void init(ModbusServer server, String stringPort) {
        int port = Integer.parseInt(stringPort);

        if (stringPort.matches("\\d+") && port > 1023 && port < 65536) {
            server.setPort(port);
        } else {
            throw new IllegalArgumentException("INVALID PORT NUMBER");
        }
    }

    public void start(ModbusServer server) {
        try {
            server.Listen();
        } catch (IOException e) {
            System.out.println("Can't start server");
        }
    }

    public void setCoilValue(ModbusServer server, int coil, boolean value) {
        server.coils[coil] = value;
    }
}
