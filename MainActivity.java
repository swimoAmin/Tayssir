package com.swimo.taysirmedical;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getConsultationTraitment();
    }

    public void getConsultationTraitment(){

        AsyncHttpClient client = new AsyncHttpClient();


        client.get("http://taysirsolutions.cloudapp.net/DMI/Api/ConsultationApi/getProcedure", new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String res) {
                List<String> responseList = new ArrayList<String>();
                try {
                    JSONObject obj = new JSONObject(res);

                    String CurrentString = obj.getString("Data");
                    String[] separated = CurrentString.split(",");

                    for (int i = 0; i < separated.length; i++) {
                        String name = separated[i].replace("\"","");
                        responseList.add(name);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_dropdown_item_1line, responseList);
                AutoCompleteTextView textView = (AutoCompleteTextView)
                        findViewById(R.id.autoCompleteTextView);
                textView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

            }
        });


    }
}
