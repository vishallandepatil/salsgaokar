package com.example.vishallandepatil.incubatore.setting;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

public class NameEditDilog extends Dialog implements
        android.view.View.OnClickListener {
    public SettingFragment c;
    public Dialog d;
    public Button yes, no;
    public EditText etname;
    Incubatore incubatore;

    public NameEditDilog(Fragment a, Incubatore incubatore) {
        super(a.getActivity());
        // TODO Auto-generated constructor stub
        this.c = (SettingFragment) a;
        this.incubatore = incubatore;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.editnamedilog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        etname = (EditText) findViewById(R.id.txt_name);
        etname.setText(incubatore.getName());
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                try {
                    if (etname.getText().toString().length() > 0)
                    {   incubatore.setName(etname.getText().toString());
                    ((SettingFragment) c).updateIncubators(incubatore);
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getContext(),"Incubators Update Failed",Toast.LENGTH_SHORT).show();
                }
                dismiss();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }

    }

}
