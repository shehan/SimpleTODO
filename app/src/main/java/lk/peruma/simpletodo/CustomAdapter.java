package lk.peruma.simpletodo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static android.R.attr.resource;


public class CustomAdapter extends ArrayAdapter<SimpleTODO> {

    private SimpleDateFormat dateFormatter;

    public CustomAdapter(Context context, List<SimpleTODO> TODOs) {
        super(context,R.layout.custom_row, TODOs);

        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        SimpleTODO singleItem = getItem(position);
        TextView itemTitle = (TextView) customView.findViewById(R.id.textViewTitle);
        TextView itemDue = (TextView) customView.findViewById(R.id.textViewDue);

        itemTitle.setText(singleItem.getTitle());
        itemDue.setText("Due: " + dateFormatter.format(singleItem.getDue()));


        return customView;
    }
}
