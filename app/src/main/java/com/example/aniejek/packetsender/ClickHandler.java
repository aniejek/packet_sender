package com.example.aniejek.packetsender;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

class ClickHandler implements android.view.View.OnClickListener {
    //private final MainActivity parentActivity;
    private static int PORT = 5005;
    private DatagramSocket socket;
    private EditText ipInput;
    private EditText byteInput;
    private TextView output;
    public ClickHandler(MainActivity parentActivity, DatagramSocket socket){
        //this.parentActivity = parentActivity;
        this.socket = socket;
        this.byteInput = parentActivity.findViewById(R.id.data);
        this.ipInput = parentActivity.findViewById(R.id.address);
        this.output = parentActivity.findViewById(R.id.textView);
    }
    private byte[] getBytesFromByteString(String string){
        String[] byteStrings = string.split("\\.");
        List<Byte> byteList = new ArrayList<>();
        for (String b : byteStrings){
            b = b.toLowerCase();
            char firstDigit;
            char secondDigit = b.charAt(0);
            if (b.length() > 1)
            {
                firstDigit = b.charAt(1);
            }
            else
            {
                firstDigit = secondDigit;
                secondDigit = '0';
            }
            firstDigit -= '0';
            if (firstDigit > 10) {
                firstDigit -= ('a' - '0' - 10);
            }
            secondDigit -= '0';
            if (secondDigit > 10) {
                secondDigit -= ('a' - '0' - 10);
            }
            secondDigit <<= 4;
            byte byteVal = (byte) (firstDigit | secondDigit);
            byteList.add(byteVal);
        }
        byte[] bytes = new byte[byteList.size()];
        int i = 0;
        for (byte b : byteList){
            bytes[i++] = b;
        }
        return bytes;
    }

    @Override
    protected void finalize() throws Throwable {
        socket.close();
        super.finalize();
    }

    @Override
    public void onClick(View view) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ipInput.getText().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (address != null){
            String byteInput = this.byteInput.getText().toString();
            byte[] bytes = getBytesFromByteString(byteInput);
            DatagramPacket datagramPacket = null;
            datagramPacket = new DatagramPacket(bytes, bytes.length, address, PORT);
            new SendPackageTask(socket, datagramPacket).execute();
            String message = "Datagram was sent.";
            output.setText(message);
        }
        else {
            String errorMessage = "IP Address cannot be gotten.";
            output.setText(errorMessage);
        }
    }
}
