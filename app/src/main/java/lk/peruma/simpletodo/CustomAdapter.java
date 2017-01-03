package lk.peruma.simpletodo;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.R.attr.customNavigationLayout;
import static android.R.attr.drawable;
import static android.R.attr.resource;


public class CustomAdapter extends ArrayAdapter<SimpleTODO> {

    private SimpleDateFormat dateFormatter;

    ImageView imageDelete, imageEdit, imageCompleted;

    public CustomAdapter(Context context, List<SimpleTODO> TODOs) {
        super(context,R.layout.custom_row, TODOs);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa", Locale.US);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.custom_row,parent,false);

        SimpleTODO singleItem = getItem(position);
        TextView itemTitle = (TextView) customView.findViewById(R.id.textViewTitle);
        TextView itemDue = (TextView) customView.findViewById(R.id.textViewDue);
        imageEdit = (ImageView) customView.findViewById(R.id.imageButtonEdit);
        imageDelete = (ImageView) customView.findViewById(R.id.imageButtonDelete);
        imageCompleted = (ImageView) customView.findViewById(R.id.imageButtonCompleted);

        imageCompleted.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleTODO singleItem = getItem(position);
                        if(singleItem.IsCompleted()) {
                            singleItem.SetAsNotStarted();
                            Toast.makeText(v.getContext(), singleItem.getTitle() + " set as not completed!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            singleItem.SetAsCompleted();
                            Toast.makeText(v.getContext(), singleItem.getTitle() + " has been completed!", Toast.LENGTH_SHORT).show();
                        }

                        NotificationManager manager = (NotificationManager) v.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.cancel(singleItem.getId().intValue());

                        Intent intent = new Intent("ListViewDataUpdated");
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                    }
                }
        );

        imageDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                        View parentView = (View) v.getParent();
                        TextView t = (TextView)  parentView.findViewById(R.id.textViewTitle);
                        String s = t.getText().toString();
                        */

                        //Put up the Yes/No message box
                        AlertDialog.Builder builder = new AlertDialog.Builder(CustomAdapter.this.getContext());
                        builder
                                .setTitle("Delete TODO's")
                                .setMessage("Are you sure? This action cannot be reverted.")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        SimpleTODO singleItem = getItem(position);
                                        if (singleItem.Delete()) {
                                            Toast.makeText(CustomAdapter.this.getContext(), singleItem.getTitle()+" has been deleted!", Toast.LENGTH_SHORT).show();

                                            NotificationManager manager = (NotificationManager)CustomAdapter.this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                            manager.cancel(singleItem.getId().intValue());

                                            Intent intent = new Intent("ListViewDataUpdated");
                                            LocalBroadcastManager.getInstance(CustomAdapter.this.getContext()).sendBroadcast(intent);
                                        }
                                        else{
                                            Toast.makeText(CustomAdapter.this.getContext(), "Unable to delete TODO", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
                    }
                }
        );

        itemTitle.setText(singleItem.getTitle());
        itemDue.setText("Due: " + dateFormatter.format(singleItem.getDue()));

        if(singleItem.IsCompleted()){
            itemTitle.setPaintFlags(itemTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            itemDue.setPaintFlags(itemDue.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            itemTitle.setEnabled(false);
            itemDue.setEnabled(false);
            imageEdit.setEnabled(false);

            Resources res = getContext().getResources();
            Drawable img = res.getDrawable(R.drawable.ic_action_edit_disabled);
            imageEdit.setImageDrawable(img);
            img = res.getDrawable(R.drawable.ic_action_undo);
            imageCompleted.setImageDrawable(img);
        }

        /*
        else
        {
           itemTitle.setPaintFlags(itemTitle.getPaintFlags() | (~Paint.STRIKE_THRU_TEXT_FLAG));
           itemDue.setPaintFlags(itemDue.getPaintFlags() | (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        */

        return customView;
    }
}
