package ftmk.bitp3453.helloclass;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Vector;

import ftmk.bitp3453.helloclass.databinding.ActivityStudentMainBinding;


public class StudentMainActivity extends AppCompatActivity {


    private void fnAdd(View view) {
        String fullName = binding.edtFullName.getText().toString();
        String studNo = binding.edtStudNum.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender = "";
        String state = binding.spnState.getSelectedItem().toString();

        if (binding.rbMale.isChecked())
            gender = binding.rbMale.getText().toString();
        else if (binding.rbFemale.isChecked())
            gender = binding.rbFemale.getText().toString();

        student = new Student(fullName, studNo, email, gender, birth, state);

        students.add(student);
        adapter.notifyItemInserted(students.size());


    }

    private ActivityStudentMainBinding binding;
    private Student student;

    //private Vector<Student> students;
    private StudentAdapter adapter;

    private DatePickerDialog datePicker;
    private StudentDB studentDB;
    private List<Student> students;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.fabAdd.setOnClickListener(this::fnAddToREST);

        binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                int mHour = cldr.get(Calendar.HOUR_OF_DAY);
                int mMinute = cldr.get(Calendar.MINUTE);
                String strDay = "";
                // date picker dialog
                datePicker = new DatePickerDialog(StudentMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                binding.edtBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        //students = new Vector<>();


        //binding.rcvStud.setAdapter(adapter);
        //binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));


        studentDB = new StudentDB(this);
        students = studentDB.fnGetAllExpenses();
        adapter = new StudentAdapter(getLayoutInflater(), students);

        binding.rcvStud.setAdapter(adapter);
        binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(binding.rcvStud);
    }
    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


            students.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged();
        }
    };

    private void fnAddToREST(View view) {

        String strFullName = binding.edtFullName.getText().toString();
        String strStudNo = binding.edtStudNum.getText().toString();
        String strEmail = binding.edtEmail.getText().toString();
        String strBirthdate = binding.edtBirthdate.getText().toString();
        String strGender = "";
        String strState = binding.spnState.getSelectedItem().toString();

        if (binding.rbMale.isChecked())
            strGender = binding.rbMale.getText().toString();
        else if (binding.rbFemale.isChecked())
            strGender = binding.rbFemale.getText().toString();
        student = new Student(strFullName, strStudNo, strEmail, strGender, strBirthdate, strState);

        students.add(student);
        String strURL = "http:/192.168.0.31/RESTAPI/rest_api.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), "Respond from server: " +
                            jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getParams() throws AuthFailureError {

                String fullName = binding.edtFullName.getText().toString();
                String studNo = binding.edtStudNum.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String birth = binding.edtBirthdate.getText().toString();
                String gender = "";
                String state = binding.spnState.getSelectedItem().toString();

                if (binding.rbMale.isChecked())
                    gender = binding.rbMale.getText().toString();
                else if (binding.rbFemale.isChecked())
                    gender = binding.rbFemale.getText().toString();

                Map<String,String> params = new HashMap<>();
                params.put("selectFn", "fnSaveData");
                params.put("studName", fullName);
                params.put ("studGender",gender);
                params.put("studDob", birth);
                params.put("studNo", studNo);
                params.put("studState", state);

                return params;
            }
        };
        requestQueue.add(stringRequest);

        studentDB = new StudentDB(this);
        studentDB.fnInsert(student);

    }

}



