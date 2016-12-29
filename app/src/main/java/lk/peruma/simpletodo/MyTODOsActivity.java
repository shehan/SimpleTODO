package lk.peruma.simpletodo;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyTODOsActivity extends AppCompatActivity {

    private ListView mainListView;
    private FloatingActionButton actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_todos);

        mainListView = (ListView) findViewById(R.id.myTODOListView);
        actionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        ListAdapter adapter  = new CustomAdapter(this, SimpleTODO.GetAllTODOs(this));
        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(MyTODOsActivity.this, "Clicked: "+item, Toast.LENGTH_SHORT).show();
                    }
                }
        );


        actionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MyTODOsActivity.this, "FAB Clicked!", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}
