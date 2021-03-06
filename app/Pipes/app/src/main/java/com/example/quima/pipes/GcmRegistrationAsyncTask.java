package com.example.quima.pipes;

import android.os.AsyncTask;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.content.Context;
import java.io.IOException;
import android.widget.Toast;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Kóði fyrir GoogleCloud Messaging skilaboð
 * Ekki tókst að klára virknina en kóði skilinn eftir til að
 * klára síðar.
 * Created by katrineliasdottir on 07/03/15.


class GcmRegistrationAsyncTask extends AsyncTask<Void, Void, String> {

    private static Registration regService = null;
    private GoogleCloudMessaging gcm;
    private Context context;

    private static final String SENDER_ID = "622897435652";

    public GcmRegistrationAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (regService == null) {
            Registration.Builder builder = new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // Need setRootUrl and setGoogleClientRequestInitializer only for local testing,
                    // otherwise they can be skipped
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end of optional local run code

            regService = builder.build();
        }

        String msg = "";
        try {
            if (gcm == null) {
                gcm = GoogleCloudMessaging.getInstance(context);
            }
            String regId = gcm.register(SENDER_ID);
            msg = "Device registered, registration ID=" + regId;

            // You should send the registration ID to your server over HTTP,
            // so it can use GCM/HTTP or CCS to send messages to your app.
            // The request to your server should be authenticated if your app
            // is using accounts.
            regService.register(regId).execute();

        } catch (IOException ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }
        return msg;
    }

    @Override
    protected void onPostExecute(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Logger.getLogger("REGISTRATION").log(Level.INFO, msg);
    }
}
*/