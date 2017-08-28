package info.itvincent.rsaandroid.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.security.PublicKey;

import info.itvincent.rsaandroid.RSAAndroid;
import info.itvincent.rsaandroid.RSAException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RSAAndroid";
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.encode_button:
                    EditText editText = (EditText) findViewById(R.id.encodeRaw_edit);
                    String raw = editText.getText().toString();
                    try {
                        byte[] result = RSAAndroid.encode(raw.getBytes(), mPublicKey);
                        String base64 = new String(Base64.encode(result, Base64.NO_WRAP));
                        EditText editTextOutput = (EditText)findViewById(R.id.encodeOutput_edit);
                        editTextOutput.setText(base64);
                    } catch (RSAException e) {
                        Log.e(TAG, "", e);
                    }
                    break;
                case R.id.decode_button:
                    EditText editTextDecode = (EditText) findViewById(R.id.decodeRaw_edit);
                    String rawDecode = editTextDecode.getText().toString();
                    try {
                        byte[] decodeBase64Bytes = Base64.decode(rawDecode, Base64.NO_WRAP);
                        byte[] decodeBytes = RSAAndroid.decode(decodeBase64Bytes, mPrivateKey);
                        String decodeString = new String(decodeBytes);
                        EditText editTextDecodeOutput = (EditText) findViewById(R.id.decodeOutput_edit);
                        editTextDecodeOutput.setText(decodeString);
                    } catch (RSAException e) {
                        Log.e(TAG, "", e);
                    }
                    break;
                case R.id.button_copy:
                    EditText from = (EditText)findViewById(R.id.encodeOutput_edit);
                    EditText to = (EditText) findViewById(R.id.decodeRaw_edit);
                    to.setText(from.getText().toString());
                    break;
            }
        }
    };
    private HandlerThread mHandlerThead;
    private Handler mHandler;
    private PublicKey mPublicKey;
    private PrivateKey mPrivateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews(savedInstanceState);

        mHandlerThead = new HandlerThread("rsa");
        mHandlerThead.start();
        mHandler = new Handler(mHandlerThead.getLooper());

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream inputStream = getBaseContext().getResources().openRawResource(R.raw.publickey);
                    String string = convertStreamToString(inputStream);
                    mPublicKey = RSAAndroid.openSSLPemStringToPublicKey(string);
                    Log.i(TAG, "public key init done");

                    InputStream inputStream2 = getBaseContext().getResources().openRawResource(R.raw.privatekey);
                    String string2 = convertStreamToString(inputStream2);
                    mPrivateKey = RSAAndroid.openSSLPemStringToPrivateKey(string2);
                    Log.i(TAG, "private key init done");
                } catch (Exception e) {
                    Log.e(TAG, "", e);
                }
            }
        });
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private void initViews(Bundle savedInstanceState) {
        findViewById(R.id.encode_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.decode_button).setOnClickListener(mOnClickListener);
        findViewById(R.id.button_copy).setOnClickListener(mOnClickListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            try {
                byte[] result = Base64.encode("abcdefg".getBytes("UTF-8"), Base64.NO_WRAP);
                Toast.makeText(this, new String(result), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(TAG, "", e);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
