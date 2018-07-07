package com.example.aniejek.packetsender;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.xml.parsers.SAXParserFactory;

public class SendPackageTask extends AsyncTask<Void, Void, Void> {
    private DatagramSocket socket;
    private DatagramPacket packet;

    public SendPackageTask(DatagramSocket socket, DatagramPacket packet){
        this.packet = packet;
        this.socket = socket;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
