package lk.peruma.simpletodo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddTODOActivity extends AppCompatActivity {

    Button buttonSave, buttonCancel;
    EditText textTitle, textDescription;
    DatePicker dateDue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        textTitle = (EditText) findViewById(R.id.editTextTitle);
        textDescription = (EditText) findViewById(R.id.editTextDescription);

        dateDue = (DatePicker) findViewById(R.id.datePickerDueDate);

        buttonCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = textTitle.getText().toString();
                        String description = textDescription.getText().toString();
                        Date dueDate  = getDateFromDatePicket(dateDue);
                        SimpleTODO simpleTODO = new SimpleTODO(AddTODOActivity.this,title,description,dueDate);
                        if (simpleTODO.Save()){
                            Toast.makeText(AddTODOActivity.this, "TODO Successfully Saved!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(AddTODOActivity.this, "TODO Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );



    }

    private static java.util.Date getDateFromDatePicket(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
