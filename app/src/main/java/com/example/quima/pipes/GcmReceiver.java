package com.example.quima.pipes;

        import android.support.v4.content.WakefulBroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.app.Activity;
        import android.content.ComponentName;

/**
 * Created by katrineliasdottir on 07/03/15.
 */
public class GcmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context,Intent intent) {
        ComponentName comp = new ComponentName(context.getPackageName(),
                GcmIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}