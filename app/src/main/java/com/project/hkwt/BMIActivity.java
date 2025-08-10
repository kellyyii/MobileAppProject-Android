package com.project.hkwt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BMIActivity extends AppCompatActivity {

    Button btn_submit, about_back;
    EditText age_input, height_input, weight_input;
    TextView cal_bmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiactivity);

        btn_submit = findViewById(R.id.btn_submit);
        weight_input = findViewById(R.id.weight_input);
        height_input = findViewById(R.id.height_input);
        age_input = findViewById(R.id.age_input);
        cal_bmi = findViewById(R.id.cal_bmi);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(age_input.getText().toString().equals("")
                        || weight_input.getText().toString().equals("")|| height_input.getText().toString().equals("")){
                    Toast.makeText(BMIActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    calBMI(Double.parseDouble(height_input.getText().toString()), Double.parseDouble(weight_input.getText().toString()));

                }
            }

            public void calBMI(Double hei, Double wei) {
                DecimalFormat df = new DecimalFormat("#.0");
                Double bmi_value = wei / ((hei / 100) * (hei / 100));
                String str_bmi = df.format(bmi_value).toString();
                cal_bmi.setText(str_bmi);
                Toast.makeText(BMIActivity.this, "Your BMI is "+ str_bmi, Toast.LENGTH_SHORT).show();
            }
        });

    }
}