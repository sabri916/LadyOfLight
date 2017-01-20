package om.gov.ita.ladyoflight;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://fcm.googleapis.com/fcm/send";
        JSONObject jsonObject = new JSONObject();


        //wake up request
        final JsonObjectRequest jsonWakeupRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.i(TAG, "response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","key=AIzaSyB9IFO8NLmnByS2atm75xDI7L2W7PrYCkE");
                return params;
            }

            @Override
            public byte[] getBody() {
                return "{\"to\": \"/topics/wakeup\",\"priority\":\"high\", \"data\": {\"message\": \"Thisa3ge!\"}}".getBytes();
            }
        };

        //start lights request
        final JsonObjectRequest jsonLightsRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Log.i(TAG, "response: " + response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, error.toString());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization","key=AIzaSyB9IFO8NLmnByS2atm75xDI7L2W7PrYCkE");
                return params;
            }

            @Override
            public byte[] getBody() {
                return "{\"to\": \"/topics/lights\",\"priority\":\"high\", \"data\": {\"message\": \"Thisa3ge!\"}}".getBytes();
            }
        };

        Button wakeUpButton = (Button) findViewById(R.id.btn_wake_up);
        final Button startLightsButton = (Button) findViewById(R.id.btn_start_lights);

        wakeUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "wake up clicked");
                queue.add(jsonWakeupRequest);
                startLightsButton.setEnabled(true);
            }
        });

        startLightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "start lights clicked");
                queue.add(jsonLightsRequest);
            }
        });
    }
}
