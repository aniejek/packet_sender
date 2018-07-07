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
    private Byte[] getBytesFromByteString(String string){
        String[] byteStrings = string.split("\\.");
        List<Byte> byteList = new ArrayList<Byte>();
        for (String b : byteStrings){
            b = b.toLowerCase();
            char first = b.charAt(0);
            char second = b.charAt(1);
            first -= '0';
            if (first > 10) {
                first -= ('a' - '0' - 10);
            }
            second -= '0';
            if (second > 10) {
                second -= ('a' - '0' - 10);
            }
            second <<= 4;
            byte byteVal = (byte) (first | second);
            byteList.add(byteVal);
        }
        return byteList.toArray(new Byte[byteList.size()]);
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
            String bInput = byteInput.getText().toString();
            Byte[] bytes = getBytesFromByteString(bInput);
            byte[] bajtyhehe = new byte[bytes.length];
            int i = 0;
            for (Byte b : bytes){
                bajtyhehe[i++] = (byte) b;
            }
            DatagramPacket datagramPacket = null;
            datagramPacket = new DatagramPacket(bajtyhehe, bytes.length, address, PORT);
            new SendPackageTask(socket, datagramPacket).execute();
        }
        else {
            String errorMessage = "Nie udalo sie uzyskac adresu IP.";
            output.setText(errorMessage);
        }
    }
}
