package com.example.todoapp2.ui.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp2.BaseApplication;
import com.example.todoapp2.R;
import com.example.todoapp2.di.component.DaggerAddComponent;
import com.example.todoapp2.di.modules.AddModule;
import com.example.todoapp2.ui.main.MainActivity;

import java.util.Calendar;

import javax.inject.Inject;

import static android.widget.Toast.LENGTH_SHORT;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AddContract.View {

    @Inject
    AddContract.Presenter addPresenter;
    @Inject
    Context context;

    private EditText nameEditText;
    private TextView dateTextView;
    private Spinner categorySpinner;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Button addTaskButton, cancelButton;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nameEditText = findViewById(R.id.name_edit_text);
        dateTextView = findViewById(R.id.date_text_view);
        categorySpinner = findViewById(R.id.spinner);
        addTaskButton = findViewById(R.id.add_task_button);
        cancelButton = findViewById(R.id.cancel_button);

        DaggerAddComponent.builder()
                .appComponent(BaseApplication.get(this).component())
                .addModule(new AddModule(this))
                .build()
                .inject(this);

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDatePicker();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                setDate(year, month, dayOfMonth);
            }
        };

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameEditText.getText().toString().isEmpty() || dateTextView.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), getString(R.string.fields_are_empty), LENGTH_SHORT).show();
                }
                else {
                    insertTask();
                }

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainActivity();
            }
        });

        initSpinner();

    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddActivity.this,
                android.R.style.Theme_Holo_Dialog_MinWidth,
                onDateSetListener,
                year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    private void setDate(int year, int month, int dayOfMonth) {
        month = month + 1;
        String date = dayOfMonth + "." + month + "." + year;
        dateTextView.setText(date);
    }

    private void initSpinner() {
        arrayAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(arrayAdapter);
        categorySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void onMainActivity() {
        startActivity(new Intent(AddActivity.this, MainActivity.class));
    }

    private void insertTask() {
        String name = nameEditText.getText().toString();
        String date = dateTextView.getText().toString();

        addPresenter.addTaskClick(name, date, category);
    }


    @Override
    public void addTaskSuccess() {
        Toast.makeText(this, getString(R.string.task_added), Toast.LENGTH_SHORT).show();
        onMainActivity();
    }

    @Override
    public void addTaskError() {
        onAlertDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        addPresenter.closeRealm();
    }

    private void onAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle(getString(R.string.operation_failed));
        builder.setMessage(getString(R.string.do_you_want_to_repeat));
        builder.setCancelable(true);
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertTask();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
