package lk.peruma.simpletodo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddTODOActivity extends AppCompatActivity {

    Button buttonSave, buttonCancel;
    EditText textTitle, textDescription, textDue, textTimeDue;
    DatePickerDialog dateDue;
    TimePickerDialog timeDue;
    Date dueDate, dueTime;

    private SimpleDateFormat dateFormatter, timeFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        Calendar newCalendar = Calendar.getInstance();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);

        buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        textTitle = (EditText) findViewById(R.id.editTextTitle);
        textDescription = (EditText) findViewById(R.id.editTextDescription);
        textDue = (EditText) findViewById(R.id.datePickerDueDate);
        textTimeDue = (EditText) findViewById(R.id.timePickerDueTime);

        textDue.setText(dateFormatter.format(newCalendar.getTime()));
        textTimeDue.setText(timeFormatter.format(newCalendar.getTime()));
        dueDate = dueTime = newCalendar.getTime();

        textTitle.addTextChangedListener(new TextWatcher() {
                                             @Override
                                             public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                             }

                                             @Override
                                             public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                 if(textTitle.getText().length()>0){
                                                     buttonSave.setEnabled(true);
                                                     textDue.setEnabled(true);
                                                     textDescription.setEnabled(true);
                                                     textTimeDue.setEnabled(true);
                                                 }
                                                 else{
                                                     buttonSave.setEnabled(false);
                                                     textDue.setEnabled(false);
                                                     textDescription.setEnabled(false);
                                                     textTimeDue.setEnabled(false);
                                                 }
                                             }

                                             @Override
                                             public void afterTextChanged(Editable s) {
                                             }
                                         }
        );

        textDue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDue.show();
                    }
                }
        );

        textTimeDue.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeDue.show();
                    }
                }
        );

        timeDue = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar newDateTime = Calendar.getInstance();
                newDateTime.set(newDateTime.get(Calendar.YEAR), newDateTime.get(Calendar.MONTH), newDateTime.get(Calendar.DAY_OF_MONTH),hourOfDay,minute);
                textTimeDue.setText(timeFormatter.format(newDateTime.getTime()));
                dueTime = newDateTime.getTime();
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MINUTE),true);


        dateDue = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                textDue.setText(dateFormatter.format(newDate.getTime()));
                dueDate = newDate.getTime();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        buttonCancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = textTitle.getText().toString();
                        String description = textDescription.getText().toString();

                        Calendar reminderDate = Calendar.getInstance();
                        int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(dueDate));
                        int month = Integer.parseInt(new SimpleDateFormat("MM").format(dueDate));
                        int day = Integer.parseInt(new SimpleDateFormat("dd").format(dueDate));
                        int hour = Integer.parseInt(new SimpleDateFormat("HH").format(dueTime));
                        int minutes = Integer.parseInt(new SimpleDateFormat("mm").format(dueTime));
                        reminderDate.set(year,month-1,day,hour,minutes);
                        Date due  =  reminderDate.getTime(); //dueDate; //getDateFromDatePicket(dateDue);

                        SimpleTODO simpleTODO = new SimpleTODO(AddTODOActivity.this, title, description, due);
                        if (simpleTODO.Save()) {
                            Toast.makeText(AddTODOActivity.this, "TODO Successfully Saved!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent("ListViewDataUpdated");
                            LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);

                            finish();
                        } else {
                            Toast.makeText(AddTODOActivity.this, "TODO Not Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


    }

    private static java.util.Date getDateFromDatePicket(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
