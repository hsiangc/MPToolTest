package net.macdidi.mptooltest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    private ListView TestList;
    private TextView testResult;
    private List<MpItem> items;
    private MpAdapter itemAdapter;

    private Time mFirstTime = new Time();
    private Time mSecondTime = new Time();
    private Handler mHandler = new Handler();
    private boolean result;

    public BluetoothAdapter mBluetoothAdapter;
    public int REQUEST_ENABLE_BT = 1;

    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestList = (ListView) findViewById(R.id.listView);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        testResult = (TextView) findViewById(R.id.result);
        IntentFilter intent = new IntentFilter();
        intent.addAction(BluetoothDevice.ACTION_FOUND);// 用BroadcastReceiver來取得搜索結果
        intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED); //每當掃描模式變化的時候，應用程序可以为通過ACTION_SCAN_MODE_CHANGED值來監聽全局的消息通知。比如，當設備停止被搜尋以後，該消息可以被系統通知給應用程序。
        intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED); //每當藍牙模塊被打開或者關閉，應用程序可以为通過ACTION_STATE_CHANGED值來監聽全局的消息通知。
        registerReceiver(searchReceiver, intent);


        items = new ArrayList<MpItem>();
        items.add(new MpItem(1, "method1", "-"));
        items.add(new MpItem(2, "method2", "-"));
        items.add(new MpItem(3, "method3", "-"));
        items.add(new MpItem(4, "method4", "-"));
        items.add(new MpItem(5, "method5", "-"));
        items.add(new MpItem(6, "method6", "-"));
        items.add(new MpItem(7, "method7", "-"));
        items.add(new MpItem(8, "method8", "-"));
        items.add(new MpItem(9, "method9", "-"));
        items.add(new MpItem(10, "method10", "-"));
        items.add(new MpItem(11, "method11", "-"));
        items.add(new MpItem(12, "method12", "-"));
        items.add(new MpItem(13, "method13", "-"));

        itemAdapter = new MpAdapter(this, R.layout.item_list, items);
        TestList.setAdapter(itemAdapter);
    }

    public void startTest(View view) {

        //int numItem = items.size();

        checkRTC();
        checkBT();


    }

    public void checkRTC() {
        mFirstTime.setToNow();
        // mDoneRTC = false;
        mHandler.postDelayed(TimerCompare, 2 * 1000);

    }

    public Runnable TimerCompare = new Runnable() {
        @Override
        public void run() {
            mSecondTime.setToNow();
            Log.d("test", "RTC02 " + mSecondTime.second);
            if (mSecondTime.second == mFirstTime.second + 2) {
                MpItem item = itemAdapter.getItem(2);
                item.mResult = "Pass";
                itemAdapter.setData(2, item);
            }
        }
    };

    public void checkBT() {
        result = false;
        if (mBluetoothAdapter != null) {
            // Device does not support Bluetooth
            testResult.setText("Have Bluetooth");
            //退出程序
            //test_bluetooth.this.finish();
        }

        do {
            mBluetoothAdapter.enable();
        } while(!mBluetoothAdapter.isEnabled());

        mBluetoothAdapter.startDiscovery();
        timer.schedule(new DateTask(), 20000);
    }

    public class DateTask extends TimerTask {
        @Override
        public void run() {
            if(result==false) {
                Log.d("test:", "NoBluetooth");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MpItem item = itemAdapter.getItem(3);
                        item.mResult = "Failed";
                        itemAdapter.setData(3, item);
                    }
                });

            }
        }
    }


        private final BroadcastReceiver searchReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                BluetoothDevice device = null;
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                        Log.d("test:", "Bluetooth");
                        if (device.getAddress().equals("F4:5C:89:A4:2C:50")) {
                            result = true;
                            Log.d("test:", "BT match");
                            MpItem item = itemAdapter.getItem(3);
                            item.mResult = "Pass";
                            itemAdapter.setData(3, item);
                        }
                    }
                }

            }
        };

        //跳到網路上的藍芽範本
        public void bluetooth(View view) {
        }
    }

