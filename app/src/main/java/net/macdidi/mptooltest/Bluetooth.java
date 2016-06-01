package net.macdidi.mptooltest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import net.macdidi.mptooltest.R;

import java.util.ArrayList;
import java.util.Set;

public class Bluetooth extends Activity {
    public Button searchBtn;//搜索藍牙設備
    public Button exitBtn;//退出應用
    public Button discoverBtn;//設置可被發現
    public ToggleButton openBtn;//開關藍牙設備
    public Button serverbtn;
    public ListView listView;//藍牙設備清單
    public ArrayAdapter<String> adapter;
    public ArrayList<String> list = new ArrayList<String>();
    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> bondDevices;
    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        searchBtn = (Button) findViewById(R.id.btnSearch);
        exitBtn = (Button) findViewById(R.id.btnExit);
        discoverBtn = (Button) findViewById(R.id.btnDis);
        openBtn = (ToggleButton) findViewById(R.id.tbtnSwitch);
        serverbtn = (Button) findViewById(R.id.btnserver);
        listView = (ListView) findViewById(R.id.lvDevices);
        context = getApplicationContext();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        openBtn.setChecked(false);
        //注冊廣播接收信號
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);
        // 用BroadcastReceiver來取得搜索結果

        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        //每當掃描模式變化的時候，應用程序可以为通過ACTION_SCAN_MODE_CHANGED值來監聽全局的消息通知。比如，當設備停止被搜尋以後，該消息可以被系統通知給應用程序。

        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        //每當藍牙模塊被打開或者關閉，應用程序可以为通過ACTION_STATE_CHANGED值來監聽全局的消息通知。

        registerReceiver(searchReceiver, intent);
        //顯示已配對設備以及搜索未配對設備
        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (bluetoothAdapter.isDiscovering()) {
                    bluetoothAdapter.cancelDiscovery();
                }
                list.clear();
                bondDevices = bluetoothAdapter.getBondedDevices();

                for (BluetoothDevice device : bondDevices) {
                    String str = "	已配對完成	" + device.getName() + "	"
                            + device.getAddress();
                    list.add(str);
                    adapter.notifyDataSetChanged();
                }
                bluetoothAdapter.startDiscovery();
            }
        });
        //退出應用
        exitBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Bluetooth.this.finish();
            }
        });
        //設置藍牙設備可發現
        discoverBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent discoverIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 12);
                startActivity(discoverIntent);
            }
        });
        //開關藍牙設備
        openBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (openBtn.isChecked() == true) {
                    bluetoothAdapter.disable();
                } else if (openBtn.isChecked() == false) {
                    bluetoothAdapter.enable();
                }
            }
        });
        //作为服務端開启
        serverbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ServerThread serverThread = new ServerThread(bluetoothAdapter, context);
                Toast.makeText(context, "server 端启動", 120).show();
                serverThread.start();
            }
        });
        listView.setOnItemClickListener(new ItemClickListener());
    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "藍牙設備不可用", 120).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 3);
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    private final BroadcastReceiver searchReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            BluetoothDevice device = null;
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                    Toast.makeText(context, device.getName() + "", 120).show();
                    String str = "	未配對完成	" + device.getName() + "	"
                            + device.getAddress();
                    if (list.indexOf(str) == -1)// 防止重复添加
                        list.add(str);
                }
                adapter.notifyDataSetChanged();
            }

        }
    };

    public class ItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            // TODO Auto-generated method stub
            if (bluetoothAdapter.isDiscovering())
                bluetoothAdapter.cancelDiscovery();
            String str = list.get(arg2);
            String address = str.substring(str.length() - 17);
            BluetoothDevice btDev = bluetoothAdapter.getRemoteDevice(address);
            ClientThread clientThread = new ClientThread(btDev, context);
            clientThread.start();
        }
    }
}