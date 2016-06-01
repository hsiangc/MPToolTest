package net.macdidi.mptooltest;


import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

public class ClientThread extends Thread {
    public BluetoothSocket socket;
    public BluetoothDevice device;
    public Context context;
    public ClientThread(BluetoothDevice device,Context context){
        this.device = device;
        this.context = context;
    }
    public void run() {
        try {
            //創建一個Socket連接：只需要服務器在注冊時的UUID號
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            //連接
            socket.connect();
            //下面代碼作者偷懶，讀寫另外起一個線程最好
            //發送數據
            if (socket == null)
            {
                Toast.makeText(context, "鏈接失敗", 5000).show();
                return;
            }
            System.out.println("zhoulc client");
            while(true){
                try {
                    System.out.println("zhoulc client is in");
                    String msg = "hello everybody I am client";
                    OutputStream os = socket.getOutputStream();
                    os.write(msg.getBytes());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
