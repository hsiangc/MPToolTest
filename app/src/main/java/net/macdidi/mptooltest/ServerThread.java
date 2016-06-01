package net.macdidi.mptooltest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class  ServerThread extends Thread {
    public BluetoothServerSocket mserverSocket;
    public BluetoothAdapter bluetoothAdapter;
    public BluetoothSocket socket;
    public Context context;
    public ServerThread(BluetoothAdapter bluetoothAdapter,Context context) {
        this.bluetoothAdapter = bluetoothAdapter;
        this.context = context;
    }

    public void run() {

        try {
			/* 創建一個藍牙服務器
			 * 参數分別：服務器名稱、UUID	 */
            mserverSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(Bluetoothprotocol.PROTOCOL_SCHEME_RFCOMM,
                    UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
//			/* 接受客戶端的連接請求 */
            socket = mserverSocket.accept();
            //下面代碼作者偷懶，讀寫另外起一個線程最好
            //接收數據
            byte[] buffer = new byte[1024];
            int bytes;
            InputStream mmInStream = null;

            try {
                mmInStream = socket.getInputStream();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("zhoulc server");
            while(true){
                if( (bytes = mmInStream.read(buffer)) > 0 )
                {
                    byte[] buf_data = new byte[bytes];
                    for(int i=0; i<bytes; i++)
                    {
                        buf_data[i] = buffer[i];
                    }
                    String s = new String(buf_data);
                    System.out.println(s+"zhoulc server is in");
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
