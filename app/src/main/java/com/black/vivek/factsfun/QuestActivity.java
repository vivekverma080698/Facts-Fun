package com.black.vivek.factsfun;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class QuestActivity extends AppCompatActivity{
    Spinner spinner;
    String url;
    TextView fact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_activity);
        spinner = (Spinner)findViewById(R.id.spin);
        fact = (TextView)findViewById(R.id.textView2);
        final String [] type ={"trivia","year","date","math"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,type);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = spinner.getSelectedItemPosition();
                String mtype = type[item];
                url = "http://numbersapi.com/random/"+mtype;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void btnClick(View view) {
        if(url.length() != 0 ) {
            sendToServer();
        }
        else{
            Toast.makeText(this,"Fill the form",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendToServer() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(QuestActivity.this);
        StringRequest req = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        fact.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(QuestActivity.this,"No Internet Available"+url,Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(req);
    }
}