package lk.peruma.simpletodo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;


public class CustomAdapter extends ArrayAdapter<SimpleTODO> {

    public CustomAdapter(Context context, List<SimpleTODO> TODOs) {
        super(context,R.layout.custom_row, TODOs);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        SimpleTODO singleFoodItem = getItem(position);
        TextView itemTitle = (TextView) customView.findViewById(R.id.textViewTitle);
        TextView itemDue = (TextView) customView.findViewById(R.id.textViewDue);

        itemTitle.setText(singleFoodItem.getTitle());
        itemDue.setText(singleFoodItem.getDue().toString());


        return customView;
    }
}
