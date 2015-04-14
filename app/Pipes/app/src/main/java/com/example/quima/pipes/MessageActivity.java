package com.example.quima.pipes;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Button;
import java.io.IOException;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.widget.EditText;


/**
 * Kóði fyrir GoogleCloud Messaging skilaboð
 * Ekki tókst að klára virknina en kóði skilinn eftir til að
 * klára síðar.
 * Created by katrineliasdottir on 07/03/15.
 */

public class MessageActivity extends Activity implements View.OnClickListener {

    Button Msg_button;
    EditText etRegId;
    String SENDER_ID = "622897435652";
    GoogleCloudMessaging gcm;
    String regId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Msg_button = (Button) findViewById(R.id.message_button);
        Msg_button.setOnClickListener(this);
    }

    public void getRegId() {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void...param){
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;
                } catch(IOException ex) {
                    msg = "Error: " + ex.getMessage();
                }
                return msg;
            }

            //@Override
            protected void onPosExecute(String msg) {
                etRegId.setText(msg + "\n");
            }
        }.execute(null,null,null);
    }

    @Override
    public void onClick(View v) {
        getRegId();
    }


}