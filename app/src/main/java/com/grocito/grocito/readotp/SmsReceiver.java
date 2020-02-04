package com.grocito.grocito.readotp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    private static SmsListener smsListener;
    Boolean b;
    String abcd, xyz;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            Log.i("onReceive", "onReceive call");
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    Log.i("sender_sms", sender);
//                    b = sender.endsWith("VDGRCITO");
                    String messageBody = smsMessage.getMessageBody();
                    abcd = messageBody.replaceAll("[^0-9]", "");

                    if (sender.equals("VDGRCITO")) {
                        smsListener.messageReceived(abcd);
                    } else {

                    }
                }
            }
        } catch (Exception e) {
            Log.i("Exception_error", ""+e);
        }

    }

    public static void bindListener(SmsListener mSmsListener) {
        smsListener = mSmsListener;
    }
}
