package com.insworks.lib_drop_menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.inswork.lib_cloudbase.R;

public class DropMenuTestActivity extends Activity {

    private DropDownMenuView dropDownMenu;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_drop_menu_activity_drop_menu_test);

        dropDownMenu = (DropDownMenuView)findViewById(R.id.dropDownMenu);
        TextView textView = (TextView) findViewById(R.id.view_top);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if(!dropDownMenu.isOpen()){
                    dropDownMenu.open();
                }
            }
        });
    }


    public void open(View view) {
        if (!dropDownMenu.isOpen()) {
            dropDownMenu.open();
        } else {
            dropDownMenu.close();
        }
    }

}
