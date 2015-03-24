package hhcard.cocktailgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class PourLiquidScreen extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccel;
    private cBluetooth bl = null;

    private String address;			// MAC-address from settings (MAC-����� ���������� �� ��������)
    private boolean show_Debug;		// show debug information (from settings) (����������� ���������� ���������� (�� ��������))
    private boolean BT_is_connect;	// bluetooth is connected (���������� ��� �������� ���������� ��������� �� Bluetooth)
    private String commandL1;		// command symbol for LED1 from settings (������ ������� ������ ��������� �� ��������)

    private int poured = 0;         // amount of liquid poured

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pourliquid);

        address = (String) getResources().getText(R.string.default_MAC);
        commandL1 = (String)"A";

        loadPref();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bl = new cBluetooth(this, mHandler);
        bl.checkBTState();

        mHandler.postDelayed(sRunnable, 600000);
        //finish();

    }
    private static class MyHandler extends Handler {
        private final WeakReference<PourLiquidScreen> mActivity;

        public MyHandler(PourLiquidScreen activity) {
            mActivity = new WeakReference<PourLiquidScreen>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PourLiquidScreen activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case cBluetooth.BL_NOT_AVAILABLE:
                        Log.d(cBluetooth.TAG, "Bluetooth is not available. Exit");
                        Toast.makeText(activity.getBaseContext(), "Bluetooth is not available", Toast.LENGTH_SHORT).show();
                        activity.finish();
                        break;
                    case cBluetooth.BL_INCORRECT_ADDRESS:
                        Log.d(cBluetooth.TAG, "Incorrect MAC address");
                        Toast.makeText(activity.getBaseContext(), "Incorrect Bluetooth address", Toast.LENGTH_SHORT).show();
                        break;
                    case cBluetooth.BL_REQUEST_ENABLE:
                        Log.d(cBluetooth.TAG, "Request Bluetooth Enable");
                        BluetoothAdapter.getDefaultAdapter();
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        activity.startActivityForResult(enableBtIntent, 1);
                        break;
                    case cBluetooth.BL_SOCKET_FAILED:
                        Toast.makeText(activity.getBaseContext(), "Socket failed", Toast.LENGTH_SHORT).show();
                        //activity.finish();
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);

    private final static Runnable sRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    public void onSensorChanged(SensorEvent e) {
        String cmdSend;
        boolean pouring;
        float xRaw, yRaw;		// RAW-value from Accelerometer sensor (RAW-�������� �� ��������������)

        TextView pouredAmountTextView = (TextView)findViewById(R.id.pouredAmount);

        xRaw = e.values[0];
        yRaw = e.values[1];

        if(yRaw < -0.3) {    // passed 90 degree tilt to left
            pouring = true;
            poured += 1;
        }
        else
            pouring = false;


        poured = Math.min(poured, 1000);
        int threshold = 5;

        if(poured > threshold*4)
            cmdSend = "D";
        else if(poured > threshold*3)
            cmdSend = "C";
        else if(poured > threshold*2)
            cmdSend = "B";
        else if(poured > threshold)
            cmdSend = "A";
        else
            cmdSend = "E";

        // for now we only consider right-handed people

//        xAxis = Math.round(xRaw*pwmMax/xR);
//        yAxis = Math.round(yRaw*pwmMax/yMax);
//
//        if(xAxis > pwmMax) xAxis = pwmMax;
//        else if(xAxis < -pwmMax) xAxis = -pwmMax;		// negative - tilt right (�����. �������� - ������ ������)
//
//        if(yAxis > pwmMax) yAxis = pwmMax;
//        else if(yAxis < -pwmMax) yAxis = -pwmMax;		// negative - tilt forward (�����. �������� - ������ ������)
//        else if(yAxis >= 0 && yAxis < yThreshold) yAxis = 0;
//        else if(yAxis < 0 && yAxis > -yThreshold) yAxis = 0;
//
//        if(xAxis > 0) {		// if tilt to left, slow down the left engine (���� �����, �� �������� ����� �����)
//            motorRight = yAxis;
//            if(Math.abs(Math.round(xRaw)) > xR){
//                motorLeft = Math.round((xRaw-xR)*pwmMax/(xMax-xR));
//                motorLeft = Math.round(-motorLeft * yAxis/pwmMax);
//                //if(motorLeft < -pwmMax) motorLeft = -pwmMax;
//            }
//            else motorLeft = yAxis - yAxis*xAxis/pwmMax;
//        }
//        else if(xAxis < 0) {		// tilt to right (������ ������)
//            motorLeft = yAxis;
//            if(Math.abs(Math.round(xRaw)) > xR){
//                motorRight = Math.round((Math.abs(xRaw)-xR)*pwmMax/(xMax-xR));
//                motorRight = Math.round(-motorRight * yAxis/pwmMax);
//                //if(motorRight > -pwmMax) motorRight = -pwmMax;
//            }
//            else motorRight = yAxis - yAxis*Math.abs(xAxis)/pwmMax;
//        }
//        else if(xAxis == 0) {
//            motorLeft = yAxis;
//            motorRight = yAxis;
//        }
//
//        if(motorLeft > 0) {			// tilt to backward (������ �����)
//            directionL = "-";
//        }
//        if(motorRight > 0) {		// tilt to backward (������ �����)
//            directionR = "-";
//        }
//        motorLeft = Math.abs(motorLeft);
//        motorRight = Math.abs(motorRight);
//
//        if(motorLeft > pwmMax) motorLeft = pwmMax;
//        if(motorRight > pwmMax) motorRight = pwmMax;
//
//        cmdSendL = String.valueOf(commandLeft+directionL+motorLeft+"\r");
//        cmdSendR = String.valueOf(commandRight+directionR+motorRight+"\r");


        pouredAmountTextView.setText(String.valueOf(poured));

        if(BT_is_connect) bl.sendData(cmdSend);

        TextView textX = (TextView) findViewById(R.id.textViewX);
        TextView textY = (TextView) findViewById(R.id.textViewY);
        TextView textCMD = (TextView) findViewById(R.id.textViewCMD);

        if(show_Debug){
            textX.setText(String.valueOf("X:" + String.format("%.1f",xRaw)));
            textY.setText(String.valueOf("Y:" + String.format("%.1f",yRaw)));
            textCMD.setText(String.valueOf("CMD:" + cmdSend));
        }
        else{
            textX.setText("");
            textY.setText("");
        }

    }

    private void loadPref(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        address = mySharedPreferences.getString("pref_MAC_address", address);			// the first time we load the default values
        show_Debug = mySharedPreferences.getBoolean("pref_Debug", false);
        commandL1 = mySharedPreferences.getString("pref_commandL1", commandL1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BT_is_connect = bl.BT_Connect(address, false);
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bl.BT_onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadPref();
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

}
