package io.rewardnxt.vivekburada.admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.e1) TextView e1;
    @BindView(R.id.e2) TextView e2;
    @BindView(R.id.e3) TextView e3;
    @BindView(R.id.b1) TextView b1;
    @BindView(R.id.b2) TextView b2;
    @BindView(R.id.b3) TextView b3;
    @BindView(R.id.logout)
    LinearLayout logout;



    private ChirpConnect chirpConnect;
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    private String TAG="MainActivity";
    private String code="";


    private String time;
    private String key;
    private byte[] load;
    private String Section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        e1.setClickable(false);
        e2.setClickable(false);
        e3.setClickable(false);
        b1.setClickable(false);
        b2.setClickable(false);
        b3.setClickable(false);

        e1.setOnClickListener(this);
        e2.setOnClickListener(this);
        e3.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        Log.d(TAG, "logging");
        time = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()).getTime());

        init();

    }

    private void init() {

        String KEY = "";
        String SECRET = "";
        String CONFIG = "";

         chirpConnect = new ChirpConnect(this, KEY, SECRET);

        chirpConnect.setConfig(CONFIG, new ConnectSetConfigListener() {

            @Override
            public void onSuccess() {
                Log.i("setConfig", "Config successfully set.");
                chirpConnect.start();

                e1.setClickable(true);
                e2.setClickable(true);
                e3.setClickable(true);
                b1.setClickable(true);
                b2.setClickable(true);
                b3.setClickable(true);

            }

            @Override
            public void onError(ChirpError setConfigError) {
                Log.e("setConfig", setConfigError.getMessage());
            }
        });

        chirpConnect.setConfigFromNetwork(new ConnectSetConfigListener() {

            @Override
            public void onSuccess() {
                Log.i("setConfig", "Config succesfully set.");
                snack();


            }

            @Override
            public void onError(ChirpError setConfigError) {
                Log.e("setConfigError", setConfigError.getMessage());
            }
        });

        chirpConnect.setListener( new ConnectEventListener() {

            @Override
            public void onSending(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when a payload is being sent " + payload + " on channel: " + channel);
            }

            @Override
            public void onSent(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when a payload has been sent " + payload  + " on channel: " + channel);


            }

            @Override
            public void onReceiving(byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when the SDK is expecting a payload to be received on channel: " + channel);


            }

            @Override
            public void onReceived(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when a payload has been received " + payload  + " on channel: " + channel);
            }

            @Override
            public void onStateChanged(byte oldState, byte newState) {
                Log.v("chirpConnectDemoApp", "This is called when the SDK state has changed " + oldState + " -> " + newState);
            }

            @Override
            public void onSystemVolumeChanged(int old, int current) {
                Log.d("chirpConnectDemoApp", "This is called when the Android system volume has changed " + old + " -> " + current);
            }

        });

    }

    private void snack() {
        Snackbar snackbar = Snackbar
                .make(logout, "SDK Initialised", Snackbar.LENGTH_LONG);

        snackbar.show();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId() /*to get clicked view id**/) {


            // do something when the corky2 is clicked

            case R.id.e1:
                Section = "E";
                code ="e1";
                key = "000";

                sendUpdate(time+code+key);


                // do something when the corky3 is clicked
                break;
            case R.id.e2:
                Section = "E";
                code ="e2";
                key = "001";

                sendUpdate(time+code+key);

                // do something when the corky3 is clicked
                break;
            case R.id.e3:
                Section = "E";
                code ="e3";
                key = "011";

                sendUpdate(time+code+key);


                // do something when the corky3 is clicked
                break;
            case R.id.b1:
                Section = "B";
                code ="b1";
                key = "000";

                sendUpdate(time+code+key);

                // do something when the corky3 is clicked
                break;
            case R.id.b2:
                Section = "B";
                code ="b2";
                key = "001";

                sendUpdate(time+code+key);

                // do something when the corky3 is clicked
                break;
            case R.id.b3:
                Section = "B";
                code ="b3";
                key = "011";

                sendUpdate(time+code+key);


                // do something when the corky3 is clicked
                break;

        }

    }

    private void sendUpdate(String payload) {

        load = payload.getBytes();
        chirpConnect.send(load);



    }



    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            chirpConnect.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chirpConnect.start();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        chirpConnect.stop();
        try {
            chirpConnect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
