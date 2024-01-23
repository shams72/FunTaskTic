package com.example.funtasktic_app_;
import android.content.DialogInterface;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.funtasktic_app_.Model.ToDoModel;
import com.example.funtasktic_app_.Utils.DatabaseHandler;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import android.content.DialogInterface;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Calendar date;
    private EditText newDateText;
    private Button newTaskSaveButton;
    private DatabaseHandler db;
    private RadioButton highRadioButton, mediumRadioButton, lowRadioButton;
    private String selectedPriority = "";

    private String Username;

    public AddNewTask(String username) {
        this.Username = username;
    }

    public AddNewTask() {}



    public static AddNewTask newInstance(String username) {
        AddNewTask fragment = new AddNewTask();
        fragment.setUsername(username);
        return fragment;
    }

    // Setter method for Username
    public void setUsername(String username) {
        this.Username = username;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_task, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        newTaskText = view.findViewById(R.id.newTaskText);
        newTaskSaveButton = view.findViewById(R.id.newTaskButton);
        highRadioButton = view.findViewById(R.id.HighPriority);
        mediumRadioButton = view.findViewById(R.id.MediumPriority);
        lowRadioButton = view.findViewById(R.id.LowPriority);

        RadioGroup radioGroup = view.findViewById(R.id.PriorityGroup);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle radio button selection
                if (checkedId == R.id.HighPriority) {
                    selectedPriority = "High";
                } else if (checkedId == R.id.MediumPriority) {
                    selectedPriority = "Medium";
                } else if (checkedId == R.id.LowPriority) {
                    selectedPriority = "Low";
                }
            }
        });


        return view;
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        newTaskText = (getView().findViewById(R.id.newTaskText));
        newTaskSaveButton = getView().findViewById(R.id.newTaskButton);

        boolean isUpdate = false;
        final Bundle bundle = getArguments();

        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            assert task != null;

            if (task.length() > 0) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }



        db = new DatabaseHandler(getActivity());
        db.openDataBase();

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    newTaskSaveButton.setEnabled(false);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                } else {
                    newTaskSaveButton.setEnabled(true);
                    newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        final int[] index = {0};
        CalendarView calendarView = getView().findViewById(R.id.calendarView);


        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                String text ="";
                String updated_text="";
                String Date="";
                String NewDate="";


                if (newTaskText != null) {

                    if(newTaskText.getText().toString() == " "){
                        text += "Please Add a Text";
                    }else {
                        text +=  newTaskText.getText().toString();
                    }


                    if (date != null) {
                        // Extract date information from Calendar
                        int year = date.get(Calendar.YEAR);
                        int month = date.get(Calendar.MONTH) + 1; // Calendar months are zero-based
                        int day = date.get(Calendar.DAY_OF_MONTH);

                        Date = year + "-" + month + "-" + day;
                    }

                    if (finalIsUpdate) {
                        updated_text = newTaskText.getText().toString() ;

                        if (date != null) {
                            // Extract date information from Calendar
                            int year = date.get(Calendar.YEAR);
                            int month = date.get(Calendar.MONTH) + 1; // Calendar months are zero-based
                            int day = date.get(Calendar.DAY_OF_MONTH);
                            NewDate = " " + year + "-" + month + "-" + day;
                        }


                        db.updateTask(bundle.getInt("id"), updated_text);
                        if(selectedPriority!=""){
                        db.updatePriority(bundle.getInt("id"),selectedPriority);
                        }
                        if(NewDate!=""){
                        db.updateDate(bundle.getInt("id"),NewDate);
                        }
                    } else {
                        ToDoModel task = new ToDoModel();
                        task.setTask(text);
                        task.setStatus(0);
                        task.setPriority(selectedPriority);
                        task.setDate(Date);
                        task.setUserName(Username);
                        if(selectedPriority!=""){
                        db.insertTask(task);
                        }
                    }
                    dismiss();
                }

            }
        });


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Update the 'date' variable with the selected date
                date = Calendar.getInstance();
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener) {
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}


