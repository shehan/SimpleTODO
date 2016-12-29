package lk.peruma.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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
import static android.R.attr.resource;


public class CustomAdapter extends ArrayAdapter<SimpleTODO> {

    private SimpleDateFormat dateFormatter;

    ImageView imageDelete;
    ImageView imageEdit;

    public CustomAdapter(Context context, List<SimpleTODO> TODOs) {
        super(context,R.layout.custom_row, TODOs);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
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

        imageDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        View parentView = (View) v.getParent();

                        TextView t = (TextView)  parentView.findViewById(R.id.textViewTitle);
                        String s = t.getText().toString();

                        SimpleTODO singleItem = getItem(position);
                        singleItem.Delete();
                        Toast.makeText(v.getContext(), singleItem.getTitle()+" has been deleted!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent("ListViewDataUpdated");
                        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
                    }
                }
        );

        itemTitle.setText(singleItem.getTitle());
        itemDue.setText("Due: " + dateFormatter.format(singleItem.getDue()));


        return customView;
    }
}
