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
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
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
    private int amountToPour;
    private int amountAlreadyInCup;
    private MediaPlayer mp;
    private String cmdPrev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pourliquid);

        Intent pourIngredient = getIntent();
        amountToPour = pourIngredient.getExtras().getInt("amountToPour");
        amountAlreadyInCup = pourIngredient.getExtras().getInt("amountAlreadyInCup");
        poured += amountAlreadyInCup;
        amountToPour += amountAlreadyInCup;
        cmdPrev = "Z";

        mp = MediaPlayer.create(this,R.raw.sink);

        address = (String) getResources().getText(R.string.default_MAC);
        commandL1 = "A";

        loadPref();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        bl = new cBluetooth(this, mHandler);
        bl.checkBTState();

        mHandler.postDelayed(sRunnable, 600000);
        //finish();

    }

    public void onClick(View view) {
        Intent goBackToChooseIngredient = new Intent();
        goBackToChooseIngredient.putExtra("pouredAmount", poured);
        setResult(RESULT_OK, goBackToChooseIngredient);
        mp.release();
        finish();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<PourLiquidScreen> mActivity;

        public MyHandler(PourLiquidScreen activity) {
            mActivity = new WeakReference<>(activity);
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
        String cmdSend = "X";
        float xRaw, yRaw;		// RAW-value from Accelerometer sensor (RAW-�������� �� ��������������)

        TextView pouredAmountTextView = (TextView)findViewById(R.id.pouredAmount);

        xRaw = e.values[0];
        yRaw = e.values[1];

        if(yRaw < -0.3) {    // passed 90 degree tilt to left = pouring
            poured += 1;
            mp.start();
        }

        poured = Math.min(poured, 100);
        int threshold = 10;

        if(poured > threshold*4) {
            cmdSend = "H";
        }
        else if(poured > threshold*3) {
            cmdSend = "G";
            if(amountToPour == threshold*3){
                cmdSend = "F";
            }
        }
        else if(poured >= threshold*2) {
            cmdSend = "E";
            if(amountToPour == threshold*2){
                cmdSend = "D";
            }
        }
        else if(poured >= threshold) {
            cmdSend = "C";
            if(amountToPour == threshold){
                cmdSend = "B";
            }
        }
        else {
            cmdSend = "A";
        }

        pouredAmountTextView.setText(String.valueOf(poured)+"ml");

        if(BT_is_connect && (!cmdPrev.equals(cmdSend))){
            bl.sendData(cmdSend);
            cmdPrev = cmdSend;
        }

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
        mp.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        loadPref();
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub
    }

}
