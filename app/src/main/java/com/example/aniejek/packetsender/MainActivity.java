package com.example.aniejek.packetsender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (socket != null) {
            Button send = findViewById(R.id.button);
            send.setOnClickListener(new ClickHandler(this, socket));
        }
        else {
            TextView messageView = findViewById(R.id.textView);
            String errorMessage = "Nie udało się stworzyć socketu.";
            messageView.setText(errorMessage);
        }
    }
}
