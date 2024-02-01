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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddNewTask extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newTaskText;
    private Calendar date;
    private Button newTaskSaveButton;
    private DatabaseHandler db;
    private RadioButton highRadioButton, mediumRadioButton, lowRadioButton;
    private String selectedPriority = "High";
    private String Username;

    boolean isUpdate = false;

    public AddNewTask(String username) {
        this.Username = username;
    }

    public AddNewTask() {}

    public static AddNewTask newInstance(String username) {
        AddNewTask fragment = new AddNewTask();
        fragment.setUsername(username);
        return fragment;
    }

    public void setUsername(String username) {
        this.Username = username;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());
        db.openDataBase();

        final Bundle bundle = getArguments();

        if (bundle != null) {
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);

            // Fetching the current priority of the task
            int taskId = bundle.getInt("id", -1); // Get task id, default to -1 if not found
            if (taskId != -1) {
                String currentPriority = db.getPriorityByTask(task, Username);
                // Check the corresponding radio button based on the current priority
                if ("High".equals(currentPriority)) {
                    highRadioButton.setChecked(true);
                } else if ("Medium".equals(currentPriority)) {
                    mediumRadioButton.setChecked(true);
                } else if ("Low".equals(currentPriority)) {
                    lowRadioButton.setChecked(true);
                }
                selectedPriority = currentPriority != null ? currentPriority : selectedPriority;
            }

            if (task.length() > 0) {
                newTaskSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
        }

        newTaskText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
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
            public void afterTextChanged(Editable s) {}
        });

        date = Calendar.getInstance();
        CalendarView calendarView = getView().findViewById(R.id.calendarView);
        calendarView.setDate(date.getTimeInMillis(), false, true);

        newTaskSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newTaskText.getText().toString().trim();
                String Date = "";

                if (date != null) {
                    // Extract date information from Calendar
                    int year = date.get(Calendar.YEAR);
                    int month = date.get(Calendar.MONTH) + 1; // Calendar months are zero-based
                    int day = date.get(Calendar.DAY_OF_MONTH);
                    Date = year + "-" + month + "-" + day;
                }

                if (isUpdate) {
                    db.updateTask(bundle.getInt("id"), text);
                    db.updatePriority(bundle.getInt("id"), selectedPriority);
                    db.updateDate(bundle.getInt("id"), Date);
                } else {
                    ToDoModel task = new ToDoModel();
                    task.setTask(text);
                    task.setStatus(0);
                    task.setPriority(selectedPriority);
                    task.setDate(Date);
                    task.setUserName(Username);
                    db.insertTask(task);
                }
                dismiss();
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