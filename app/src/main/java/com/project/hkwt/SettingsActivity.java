package com.project.hkwt;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    public TextView text_language_selection;
    public Button confirm_button;
    public Button cancel_button;

    RadioGroup langSelections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        text_language_selection = findViewById(R.id.txt_setting_lang);
        confirm_button = findViewById(R.id.saveSettings);
        cancel_button = findViewById(R.id.noSave);
        String lang = Settings.getLang();
        switch (lang){
            case "en":
                text_language_selection.setText("Switch Language:");
                confirm_button.setText("CONFIRM");
                cancel_button.setText("CANCEL");
                break;
            case "rCN":
                text_language_selection.setText("切换语言:");
                confirm_button.setText("确认");
                cancel_button.setText("取消");
                break;
            case "rHK":
                text_language_selection.setText("轉換語言:");
                confirm_button.setText("確認");
                cancel_button.setText("取消");
                break;
        }
        langSelections = findViewById(R.id.lang_group);
        langSelections.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lang_en:
                        Settings.setSelection("en");
                        setLocale("en");
                        break;
                    case R.id.lang_sc:
                        Settings.setSelection("sc");
                        setLocale("aa");
                        break;
                    case R.id.lang_tc:
                        Settings.setSelection("tc");
                        setLocale("ab");
                        break;
                }
            }
        });
        confirm_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Settings.setLang(Settings.getSelection());
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
    private void setLocale(String lang){
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Resources res = getResources();
    Configuration conf = res.getConfiguration();
    conf.locale = locale;
    res.updateConfiguration(conf, res.getDisplayMetrics());
    }
}
