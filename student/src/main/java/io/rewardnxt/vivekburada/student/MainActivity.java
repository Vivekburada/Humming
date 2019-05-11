package io.rewardnxt.vivekburada.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    @BindView(R.id.update)
    TextView updateUI;
    @BindView(R.id.root)
    LinearLayout root;


    private ChirpConnect chirpConnect;
    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;
    private String TAG="MainActivity";
    private String code="Hello";
    private KonfettiView viewKonfetti;
    private int mValue = 1;


    private  View view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

                final KonfettiView konfettiView = findViewById(R.id.konfettiView);

                DisplayMetrics display = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(display);

                konfettiView.build()
                        .addColors(Color.YELLOW, Color.WHITE, Color.BLACK)
                        .setDirection(0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5))
                        .setPosition(-50f, display.widthPixels + 50f, -50f, -50f)
                        .streamFor(300, 5000L);


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
