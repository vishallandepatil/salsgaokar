package com.example.vishallandepatil.incubatore.reading;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.SettingFragment;


public class UserActionDilog extends Dialog {


    public Button yes, no;

    //View.OnClickListener onClickListener;

    public UserActionDilog(Activity a ) {
        super(a);
        // TODO Auto-generated constructor stu

        setCancelable(false);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
    //    this.onClickListener = onClickListener;
        yes.setOnClickListener(onClickListener);
        no.setOnClickListener(onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.useraction);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);


    }


}
