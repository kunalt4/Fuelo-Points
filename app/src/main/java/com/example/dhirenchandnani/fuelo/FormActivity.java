package com.example.dhirenchandnani.fuelo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.FormatException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import static com.example.dhirenchandnani.fuelo.FormActivity.resultsMain;
import static com.example.dhirenchandnani.fuelo.R.id.spinner;

/**
 * Created by Dhiren Chandnani on 21-10-2016.
 */
public class FormActivity extends AppCompatActivity implements SpinnerDetails.AsyncResponse {


    Spinner spinner;
    public static final String PA="PA";
    public static final String BN="BN";
    public static final String Date="Date";
    public static final String Time="Time";
    public static final String Litres="Litres";
    public static final String CAR="Cars";
    public static final String PetrolName="PetrolName";
    public static String resultsMain = "DC_KT";
    public static String[] results,results1,results2, results3;
    String Marker_title="";


    @Override
    public void processFinish(String result){

        if(result != null && !result.isEmpty()){
//            results = result.split("/// ///");
//            results1 = results[0].split("///");
//            results2 = results[1].split("///");


//            if(results1[0].trim().equals("Success")) {
//                Log.e("check2",results1[0]);
//            results1 = Arrays.copyOfRange(results1,1,results1.length);
//             LoginActivity.userid = results[1];

//             Log.d("RESULTSMAIN",resultsMain);
//               }


        }
        else{
            Toast toast= Toast.makeText(this, "Unable to connect to the server!", Toast.LENGTH_SHORT);
            toast.setMargin(150,150);
            toast.show();
        }

        Log.d("OPOPOPOP",result);
        FormActivity.resultsMain = result;
        Log.d("RESULTSMAIN",resultsMain);
        Log.d("RESULTSMAIN2", FormActivity.resultsMain);

        PopulateSpinner();
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        new SpinnerDetails(this).execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_window);

        Intent intent = getIntent();

        Marker_title = intent.getStringExtra("TITLE");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final EditText form_BNo = (EditText)findViewById(R.id.billNo);
        final EditText form_petrol_amount = (EditText)findViewById(R.id.petrolAmnt);
        final TextView form_date = (TextView)findViewById(R.id.editText3);
        final TextView form_time = (TextView)findViewById(R.id.editText4);
        final EditText form_nol = (EditText)findViewById(R.id.nOL);
        final TextView form_pName = (TextView)findViewById(R.id.petrolName);

//        new SpinnerDetails(this).execute();


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().hide();
        TextView myTextView2=(TextView)findViewById(R.id.label_upload);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        myTextView2.setTypeface(typeFace);


        SimpleDateFormat dateF = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        SimpleDateFormat timeF = new SimpleDateFormat("hh:mm", Locale.getDefault());
        String date = dateF.format(Calendar.getInstance().getTime());
        String time = timeF.format(Calendar.getInstance().getTime());

        form_date.setText(date);
        form_time.setText(time);
        form_pName.setText(Marker_title);


        fab.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {

                                     String n_date = form_date.getText().toString();
                                     String n_time = form_time.getText().toString();
                                     String bn = form_BNo.getText().toString();
                                     String pa = form_petrol_amount.getText().toString();
                                     String noL = form_nol.getText().toString();
                                     String carV = spinner.getSelectedItem().toString();
                                     String pName = form_pName.getText().toString();

                                     if(!bn.equals("") && !pa.equals("") && !noL.equals("")){


                                     Intent intent2 = new Intent(FormActivity.this, ViewFormActivity.class);
                                     intent2.putExtra(PA, pa);
                                     intent2.putExtra(CAR, carV);
                                     intent2.putExtra(BN,bn);
                                     intent2.putExtra(Date,n_date);
                                     intent2.putExtra(Time,n_time);
                                     intent2.putExtra(Litres,noL);
                                     intent2.putExtra(PetrolName, pName);
                                     startActivity(intent2);
                                     }

                                     else{
                                         Toast toast= Toast.makeText(FormActivity.this, "All fields are required!", Toast.LENGTH_SHORT);
                                         toast.setMargin(150,150);
                                         toast.show();
                                     }
                                 }
                             });



    }

    public void PopulateSpinner() {

        spinner = (Spinner)findViewById(R.id.spinner);
        Log.d("SPINNER-----",resultsMain);
        results = resultsMain.split("@@@@");
        results1 = results[1].split(" ");
        results2 = results1[0].split("///");
        results3 = results1[1].split("///");
//        results1 = Arrays.copyOfRange(results1,1,results1.length);

       Log.d("RESULTS:",results1[0]+"---"+results1[1]);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item,results2); //selected item will look like a spinner set from XML

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);

    }






}

