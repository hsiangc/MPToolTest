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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private ListView TestList;
    private TextView timeTest;
    private TextView testResult;
    private List<MpItem> items;
    private MpAdapter itemAdapter;

    private Time mFirstTime = new Time();
    private Time mSecondTime = new Time();
    private Handler mHandler = new Handler();
    private boolean result;

    public BluetoothAdapter mBluetoothAdapter;
    public int REQUEST_ENABLE_BT = 1;
    public ArrayList<String> list = new ArrayList<String>();
    public ArrayAdapter<String> adapter;
    Set<BluetoothDevice> bondDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestList = (ListView) findViewById(R.id.listView);
        timeTest = (TextView) findViewById(R.id.timetest);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        testResult = (TextView) findViewById(R.id.result);

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

        boolean bCheckRTC;
        bCheckRTC = checkRTC();
        Log.d("macbear_debug", "RTC " + bCheckRTC);


//         for(int i=1;i < 13; i++)
//         {
//             if(items.get(i).getPass()=="pass")
//                resultpass = true;
//             else
//                 resultpass = false;
//         }
//
//         if(resultpass==true)
//             Result.setText("Pass");


    }

    public boolean checkRTC(){
        mFirstTime.setToNow();
        // mDoneRTC = false;
        mHandler.postDelayed(TimerCompare, 2*1000);
        Log.d("test", "RTC01 " + mFirstTime.second );
        if(result==true)
            return true;
        else
            return false;

    }
    public Runnable TimerCompare = new Runnable() {
        @Override
        public void run() {
            mSecondTime.setToNow();
            Log.d("test", "RTC02 " + mSecondTime.second );
            if(mSecondTime.second == mFirstTime.second+2) {
                result = true;
                MpItem item = itemAdapter.getItem(2);
                item.mResult = "Pass";
                itemAdapter.setData(2, item);
            }
            else
                result = false;
        }
    };

    public void clickBT(View view) {
        if (mBluetoothAdapter != null) {
            // Device does not support Bluetooth
            testResult.setText("HAVE BT");
            //退出程序
            //test_bluetooth.this.finish();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            //打開藍牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            timeTest.setText("QAQ");}

        if (mBluetoothAdapter.isEnabled())

        {

            //注冊廣播接收信號
            IntentFilter intent = new IntentFilter();
            intent.addAction(BluetoothDevice.ACTION_FOUND);
            // 用BroadcastReceiver來取得搜索結果

            registerReceiver(searchReceiver, intent);

            Log.d("debug", "TEST1");

//            if (mBluetoothAdapter.isDiscovering())
//            {
//                mBluetoothAdapter.cancelDiscovery();
//            }

            bondDevices = mBluetoothAdapter.getBondedDevices();
            Log.d("debug", "TEST2");


            for (BluetoothDevice device : bondDevices) {
                Log.d("debug", "TEST3");

                if (device.getAddress() == "F4-5C-89-A4-2C-50") {
                    Log.d("debug", "TEST4");

                    testResult.setText("True!");
                }
                Log.d("debug", "TEST5");

//                String str = "	已配對完成	" + device.getName() + "	"
//                        + device.getAddress();
//                list.add(str);
//                adapter.notifyDataSetChanged();
            }
            Log.d("debug", "TEST6");

            mBluetoothAdapter.startDiscovery();
            Log.d("debug", "TEST7");
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


    //跳到網路上的藍芽範本
    public void bluetooth(View view){

        startActivity(new Intent(this, Bluetooth.class));
    }
}

