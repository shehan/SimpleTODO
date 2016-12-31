package lk.peruma.simpletodo;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


public class NotificationActionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Integer notificationId = intent.getIntExtra("NotificationID", 0);
        SimpleTODO simpleTODO = SimpleTODO.GetTODOByID(context,notificationId.longValue());

        String intentText = intent.getAction();
        switch (intentText){
            case "Done": simpleTODO.SetAsCompleted();break;
            case "Delete":simpleTODO.Delete();break;
            case "Close":break;
        }

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);

        Intent listIntent = new Intent("ListViewDataUpdated");
        LocalBroadcastManager.getInstance(context).sendBroadcast(listIntent);
    }
}

