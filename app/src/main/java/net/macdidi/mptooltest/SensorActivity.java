package net.macdidi.mptooltest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView angleText;
    private TextView acceText;
    private TextView Xangle;
    private TextView Yangle;
    private TextView Zangle;
    private TextView Xacce;
    private TextView Yacce;
    private TextView Zacce;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

    private static final float NS2S = 1.0f / 1000000000.0f;
    private long timestamp;
    private float angle[] = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        acceText = (TextView) findViewById(R.id.acceText);
        angleText = (TextView) findViewById(R.id.angleText);
        Xacce = (TextView) findViewById(R.id.xacce);
        Yacce = (TextView) findViewById(R.id.yacce);
        Zacce = (TextView) findViewById(R.id.zacce);
        Xangle = (TextView) findViewById(R.id.xangle);
        Yangle = (TextView) findViewById(R.id.yangle);
        Zangle = (TextView) findViewById(R.id.zangle);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //加速儀
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // x,y,z分别存储坐标轴x,y,z上的加速度
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // 根据三个方向上的加速度值得到总的加速度值a
            float a = (float) Math.sqrt(x * x + y * y + z * z);
            acceText.setText("a--->" + a);

            // 传感器从外界采集数据的时间间隔为10000微秒
            //showTextView.setText("magneticSensor.getMinDelay()--->"+ magneticSensor.getMinDelay());

            // 加速度传感器的最大量程
            acceText.setText("event.sensor.getMaximumRange()--->"
                    + event.sensor.getMaximumRange());

            Xacce.setText("x--->" + x);
            Yacce.setText("y--->" + y);
            Zacce.setText("z--->" + z);

        }

        //陀螺儀
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            //从 x、y、z 轴的正向位置观看处于原始方位的设备，如果设备逆时针旋转，将会收到正值；否则，为负值
            //if (timestamp != 0) {

            // 得到两次检测到手机旋转的时间差（纳秒），并将其转化为秒
            final float dT = (event.timestamp - timestamp) * NS2S;

            // 将手机在各个轴上的旋转角度相加，即可得到当前位置相对于初始位置的旋转弧度
            angle[0] += event.values[0] * dT;
            angle[1] += event.values[1] * dT;
            angle[2] += event.values[2] * dT;

            // 将弧度转化为角度
            float anglex = (float) Math.toDegrees(angle[0]);
            float angley = (float) Math.toDegrees(angle[1]);
            float anglez = (float) Math.toDegrees(angle[2]);

            Xangle.setText("anglex--->" + anglex);
            Yangle.setText("angley--->" + angley);
            Zangle.setText("anglez--->" + anglez);
            //angleText.setText("gyroscopeSensor.getMinDelay()--->" + gyroscopeSensor.getMinDelay());
        }

        //将当前时间赋值给timestamp
        timestamp = event.timestamp;
        angleText.setText(String.valueOf(timestamp));
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//TODO Auto-generated method stub

    }

    @Override
    protected void onPause() {
//TODO Auto-generated method stub
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}

