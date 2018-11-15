package com.example.vishallandepatil.incubatore.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishallandepatil.incubatore.login.DBHelper;
import com.example.vishallandepatil.incubatore.login.PrefManager;
import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import java.util.ArrayList;

import landepatil.vishal.sqlitebuilder.Query;

public class SettingFragment extends Fragment {
    LinearLayout clinicinfo,addclicnicview,incubatore;
    EditText nameclinic,clinicadress,incubatorename;
    TextView lablename,lableAdress;
    Button btnaddclinic,incubatoraddbtn;
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_setting, container, false);
        clinicinfo=view.findViewById(R.id.clinicinfo);
        addclicnicview=view.findViewById(R.id.addclicnicview);
        nameclinic=view.findViewById(R.id.nameclinic);
        clinicadress=view.findViewById(R.id.clinicadress);
        incubatorename=view.findViewById(R.id.incubatorename);
        incubatoraddbtn=view.findViewById(R.id.incubatoraddbtn);
        btnaddclinic=view.findViewById(R.id.btnaddclinic);
        addclicnicview=view.findViewById(R.id.addclicnicview);
        incubatore=view.findViewById(R.id.incubatore);
        lablename=view.findViewById(R.id.lablename);
        lableAdress=view.findViewById(R.id.lableAdress);


        final PrefManager prefManager=new PrefManager(getContext());
        settingLayout(prefManager);

        btnaddclinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name= nameclinic.getText().toString();
               String adress= clinicadress.getText().toString();
               if(validClinic(name, adress))
               {
                   prefManager.storeClinicdata(name,adress);
                   settingLayout(prefManager);


               }
               else
               {

                   Toast.makeText(getContext(),"Enter Valid Clinic Information",Toast.LENGTH_LONG).show();
               }



            }
        });
        incubatoraddbtn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {

                                                   if(validIncubatore( incubatorename.getText().toString()))
                                                   {
                                                       Incubatore incubator=new Incubatore();
                                                       incubator.setName(incubatorename.getText().toString());
                                                       ArrayList<Incubatore> list= (ArrayList) Query.createQuery(new DBHelper(getContext())).load(Incubatore.class);
                                                       incubator.setId(list.size()+1);
                                                       Query.createQuery(new DBHelper(getContext())).insert(incubator);
                                                       incubatorename.setText("");
                                                       Toast.makeText(getContext(),"Incubatore Added Sucessfully...",Toast.LENGTH_LONG).show();
                                                       getActivity().onBackPressed();
                                                   }


                                               }
                                           }
        );


        return  view;
    }

    private void settingLayout(PrefManager prefManager) {
        if(prefManager.getName()==null)
        {
            clinicinfo.setVisibility(View.GONE);
            addclicnicview.setVisibility(View.VISIBLE);
            incubatore.setVisibility(View.GONE);


        }
        else
        {

            clinicinfo.setVisibility(View.VISIBLE);
            addclicnicview.setVisibility(View.GONE);
            incubatore.setVisibility(View.VISIBLE);
            lablename.setText(prefManager.getName());
            lableAdress.setText(prefManager.getAdress());





        }
    }

    boolean validClinic(String name,String adress)
   {
       boolean result=true;
       if(name!=null&& name.length()>5)
       {

       }
       else
       {
           Toast.makeText(getContext(),"Enter Valid Clinic Name",Toast.LENGTH_LONG).show();

           result=false;
       }
       if(adress!=null&& adress.length()>5)
       {

       }
       else
       {
           Toast.makeText(getContext(),"Enter Valid Clinic Address",Toast.LENGTH_LONG).show();

           result=false;
       }
       return result;

   }
    boolean validIncubatore(String name)
    {
        boolean result=true;
        if(name!=null&& name.length()>5)
        {

        }
        else
        {
            Toast.makeText(getContext(),"Enter Valid Incubator Name",Toast.LENGTH_LONG).show();

            result=false;
        }

        return result;

    }

}
