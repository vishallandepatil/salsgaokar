package com.example.vishallandepatil.incubatore.setting;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vishallandepatil.incubatore.home.adpators.AdapterIncubatorList;
import com.example.vishallandepatil.incubatore.login.AppDatabase;

import com.example.vishallandepatil.incubatore.login.PrefManager;
import com.example.vishallandepatil.incubatore.R;
import com.example.vishallandepatil.incubatore.setting.database.Incubatore;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class SettingFragment extends Fragment {
    LinearLayout clinicinfo,addclicnicview,incubatore;
    EditText nameclinic,clinicadress,incubatorename;
    TextView lablename,lableAdress;
    Button btnaddclinic,incubatoraddbtn,cancel;
    ImageView editicon;
    ListView listincubators;
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
        cancel=view.findViewById(R.id.cancel);
        addclicnicview=view.findViewById(R.id.addclicnicview);
        editicon=view.findViewById(R.id.editicon);

        incubatore=view.findViewById(R.id.incubatore);
        lablename=view.findViewById(R.id.lablename);
        lableAdress=view.findViewById(R.id.lableAdress);
        listincubators = view.findViewById(R.id.listincubators);

        loadlist();
        final PrefManager prefManager=new PrefManager(getContext());
        settingLayout(prefManager);
        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLayout(prefManager);
            }
        });


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
                                                       new InsertIncubatore(incubatorename,incubator,getContext()).execute();


                                                   }


                                               }
                                           }
        );


        return  view;
    }

    private void loadlist() {



        final LiveData<List<Incubatore>> list1=  AppDatabase.getDatabase(getActivity()).uncubatorsDao().fetchAllIncubators();


        list1.observeForever(new Observer<List<Incubatore>>() {
            @Override
            public void onChanged(@Nullable List<Incubatore> incubatores) {
                if(incubatores!=null&&incubatores.size()>0) {
                    AdapterIncubatorList adapter = new AdapterIncubatorList(getActivity(), (ArrayList<Incubatore>) incubatores,SettingFragment.this);
                    listincubators.setAdapter(adapter);
                    listincubators.setVisibility(View.VISIBLE);
                }
                else
                {
                    listincubators.setVisibility(View.GONE);

                }
            }
        });



    }

    public   void updateIncubators(Incubatore incubatore)
    {
      //  long i=AppDatabase.getDatabase(getActivity()).uncubatorsDao().updateIncubatorNameByid(incubatore.getId(),incubatore.getName());
       // long i=AppDatabase.getDatabase(getActivity()).uncubatorsDao().updateIncubator(incubatore);
        new updateIncubatorsTask(getActivity(),incubatore).execute();
      /*  if(i>0)
      {
          Toast.makeText(getContext(),"Incubators Update Successfully",Toast.LENGTH_SHORT).show();
          loadlist();
      }
      else
      {
          Toast.makeText(getContext(),"Incubators Update Failed",Toast.LENGTH_SHORT).show();
      }*/


    }


    private class updateIncubatorsTask extends AsyncTask<Void, Void, Integer> {
        private Context context;
        Incubatore incubatore;

        public updateIncubatorsTask(Context context,Incubatore incubatore) {
            this.context=context;
            this.incubatore=incubatore;
        }


        @Override
        protected Integer doInBackground(Void... voids) {

            int a= AppDatabase.getDatabase(context).uncubatorsDao().updateIncubator(incubatore);

            return a;
        }

        @Override
        protected void onPostExecute(Integer integer) {

            super.onPostExecute(integer);

            if(integer>0)
            {
                Toast.makeText(getContext(),"Incubators Update Successfully",Toast.LENGTH_SHORT).show();
                loadlist();
            }
            else
            {
                Toast.makeText(getContext(),"Incubators Update Failed",Toast.LENGTH_SHORT).show();
            }
        }
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
 private void editLayout(final PrefManager prefManager) {

     clinicinfo.setVisibility(View.GONE);
     addclicnicview.setVisibility(View.VISIBLE);
     incubatore.setVisibility(View.GONE);
     cancel.setVisibility(View.VISIBLE);
     nameclinic.setText(prefManager.getName());
     clinicadress.setText(prefManager.getAdress());
     cancel.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             settingLayout(prefManager);
         }
     });


 }



    boolean validClinic(String name,String adress)
   {
       boolean result=true;
       if(name!=null&& name.length()>0)
       {

       }
       else
       {
           Toast.makeText(getContext(),"Enter Valid Clinic Name",Toast.LENGTH_LONG).show();

           result=false;
       }
       if(adress!=null&& adress.length()>0)
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
        if(name!=null&& name.length()>0)
        {

        }
        else
        {
            Toast.makeText(getContext(),"Enter Valid Incubator Name",Toast.LENGTH_LONG).show();

            result=false;
        }

        return result;

    }

 /*   Incubatore incubator=new Incubatore();
            incubator.setName(incubatorename.getText().toString());


    List<Incubatore>  list=new ArrayList<>();
            list.add(incubator);
            AppDatabase.getDatabase(getContext()).uncubatorsDao().insertAll(list);

            incubatorename.setText("");
            Toast.makeText(getContext(),"Incubatore Added Sucessfully...",Toast.LENGTH_LONG).show();
    loadlist();
    //getActivity().onBackPressed();
            return null;*/

    private    class InsertIncubatore extends AsyncTask<Void, Void, Long> {
        Incubatore incubator;
        Context context;
        EditText incubatorename;
        public InsertIncubatore(EditText incubatorename,Incubatore incubator,Context context) {
            this.incubator=incubator;
            this.context=context;
            this.incubatorename=incubatorename;

        }

        @Override
        protected Long doInBackground(Void... voids) {


            Long a= AppDatabase.getDatabase(context).uncubatorsDao().insertAll(incubator);
            return a;
        }
        @Override
        protected void onPostExecute(Long agentsCount) {


            if (agentsCount > 0) {
                //2: If it already exists then prompt user
                incubatorename.setText("");
                Toast.makeText(context, "Incubatore Added Sucessfully...", Toast.LENGTH_LONG).show();
                loadlist();

            } else {
                Toast.makeText(context, "Incubatore does not Added", Toast.LENGTH_LONG).show();

            }
        }

    }



}
