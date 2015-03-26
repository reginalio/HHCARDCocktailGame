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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;


public class ShakeItScreen extends Activity implements SensorEventListener{

    private SensorManager mSensorManager;
    private static final int SHAKE_THRESHOLD = 20;
    private Sensor mAccel;
    private cBluetooth bl = null;

    private String address;			// MAC-address from settings (MAC-����� ���������� �� ��������)
    private boolean show_Debug;		// show debug information (from settings) (����������� ���������� ���������� (�� ��������))
    private boolean playBGM;
    private boolean BT_is_connect;	// bluetooth is connected (���������� ��� �������� ���������� ��������� �� Bluetooth)
    private long lastUpdate = System.currentTimeMillis();
    private float x,y,z;
    private float last_x, last_y, last_z = 0;

    private float shakedAmount = 0;
    ProgressBar progressBar;
    Button goToStirButton;
    TextView debugTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shakeit);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        debugTextView = (TextView) findViewById(R.id.shakedAmountTextView);
        goToStirButton = (Button)findViewById(R.id.goToStirButton);
        goToStirButton.setVisibility(View.GONE);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mSensorManager.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        address = (String) getResources().getText(R.string.default_MAC);
        loadPref();

        bl = new cBluetooth(this, mHandler);
        bl.checkBTState();

        mHandler.postDelayed(sRunnable, 600000);
        //finish();

    }

    public void goToStir(View view) {
        if(BT_is_connect) bl.sendData("Z");

        Intent goToStirScreen = new Intent(this, StirringScreen.class);
//        goToStirScreen.putExtra("cocktailChosen", cocktailChosen);
        startActivity(goToStirScreen);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<ShakeItScreen> mActivity;

        public MyHandler(ShakeItScreen activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ShakeItScreen activity = mActivity.get();
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
        String cmdSend = "S";

        long curTime = System.currentTimeMillis();
        // only allow one update every 100ms.
        if ((curTime - lastUpdate) > 100) {
            long diffTime = (curTime - lastUpdate);
            Log.d("sensor", "diffTime = " + diffTime);

            lastUpdate = curTime;

            x = e.values[0];
            y = e.values[1];
            z = e.values[2];

            float speed = Math.abs(x+y+z-last_x-last_y-last_z) / diffTime * 1000;

            if (speed > SHAKE_THRESHOLD) {
                if(BT_is_connect && cmdSend.equals("S")){
                    bl.sendData(cmdSend);
                    cmdSend = "meh";
                }
                shakedAmount += speed/40;
                progressBar.setProgress((int)shakedAmount);
                Log.d("sensor", "shake detected w/ speed: " + speed);
//                Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
            }
            last_x = x;
            last_y = y;
            last_z = z;

            if(show_Debug)
                debugTextView.setText(String.valueOf("shake:" + String.format("%.1f",shakedAmount)));

            if(shakedAmount > 100)
                goToStirButton.setVisibility(View.VISIBLE);

        }
    }

    private void loadPref(){
        SharedPreferences mySharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        address = mySharedPreferences.getString("pref_MAC_address", address);			// the first time we load the default values
        show_Debug = mySharedPreferences.getBoolean("pref_Debug", false);
        playBGM = mySharedPreferences.getBoolean("pref_BGM", false);
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


    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

}
