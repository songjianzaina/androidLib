package com.insworks.lib_edittext;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;
import com.insworks.lib_edittext.widget.GeneralEditText;

public class EditTextTestActivity extends AppCompatActivity implements GeneralEditText.TextChangedListener, CompoundButton.OnCheckedChangeListener {

    GeneralEditText editText;
    TextView textView;
    CheckBox cbClear;
    CheckBox cbFormat;
    CheckBox cbPasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_edittext_activity_edittext);
        editText = (GeneralEditText) findViewById(R.id.edit_text);
        editText.setTextChangedListener(this);
        textView = (TextView) findViewById(R.id.text_view);
        cbClear = (CheckBox) findViewById(R.id.cb_clear);
        cbClear.setOnCheckedChangeListener(this);
        cbFormat = (CheckBox) findViewById(R.id.cb_format);
        cbFormat.setOnCheckedChangeListener(this);
        cbPasswordVisible = (CheckBox) findViewById(R.id.cb_password_visible);
        cbPasswordVisible.setOnCheckedChangeListener(this);
        editText.setShowSwitchPasswordVisible(true);
    }

    @Override
    public void onTextChanged(CharSequence str) {
        textView.setText(str);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int i = buttonView.getId();
        if (i == R.id.cb_clear) {
            editText.setShowClearView(isChecked);
        } else if (i == R.id.cb_format) {
            editText.setFormatText(isChecked);
            if (isChecked) {
                editText.setCondition(new GeneralEditText.Condition() {
                    @Override
                    public boolean getCondition(int i) {
                        return i == 2 || i == 8;
                    }
                });
            } else {
                editText.setCondition(null);
            }
        } else if (i == R.id.cb_password_visible) {
            editText.setShowSwitchPasswordVisible(isChecked);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
