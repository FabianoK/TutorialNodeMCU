package br.ufpr.c3sl.roboarm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //top, rotate, front, hand
    private int[] angles = {0,0,0,0};

    EditText edResp;

    String urlRobo = "0.0.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView hand = (ImageView)findViewById(R.id.btHand);
        ImageView left = (ImageView)findViewById(R.id.btLeft);
        ImageView right = (ImageView)findViewById(R.id.btRight);
        ImageView up = (ImageView)findViewById(R.id.btUp);
        ImageView down = (ImageView)findViewById(R.id.btDown);
        ImageView front = (ImageView)findViewById(R.id.btExtend);
        ImageView back = (ImageView)findViewById(R.id.btContract);

        edResp = (EditText)findViewById(R.id.edResp);

        try {
            urlRobo = Config.getServerURL(getBaseContext());
            ((TextView)findViewById(R.id.txtURL)).setText(urlRobo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        hand.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(0);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(0);

                Log.d("CLICADO", "MOVE(0) "+(event.getAction() == MotionEvent.ACTION_DOWN));
                return true;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(1);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(1);
                return true;
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(2);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(2);
                return true;
            }
        });

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(3);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(3);
                return true;
            }
        });

        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(4);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(4);
                return true;
            }
        });

        front.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(5);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(5);
                return true;
            }
        });

        back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    move(6);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    stopMove(6);
                return true;
            }
        });


    }



    private String formatAngle(int a){
        String ret = a+"";
        while(ret.length() < 3){
            ret = "0"+ret;
        }

        return ret;
    }


    private void stopMove(int id){

    }

    private void move(int id){
        //top, rotate, front, hand
        if(id == 0){
            if(angles[3] == 0)
                angles[3] = 45;
            else
                angles[3] = 0;
            httpClient(3, formatAngle(angles[id]));
        }else if(id == 1 && angles[1] < 50){
            angles[1] = angles[1]+5;
            httpClient(2, formatAngle(angles[1]));
        }else if(id == 2 && angles[1] > 0){
            angles[1] = angles[1]-5;
            httpClient(2, formatAngle(angles[1]));
        }else if(id == 3 && angles[0] < 180){
            angles[0] = angles[0]+5;
            httpClient(1, formatAngle(angles[0]));
        }else if(id == 4 && angles[0] > 0){
            angles[0] = angles[0]-5;
            httpClient(1, formatAngle(angles[0]));
        }else if(id == 5 && angles[1] > 0){
            angles[1] = angles[1]-5;
            httpClient(0, formatAngle(angles[1]));
        }else if(id == 6 && angles[1] < 65){
            angles[1] = angles[1]+5;
            httpClient(0, formatAngle(angles[1]));
        }

    }

    private void httpClient(int motor, String angle) {
        Log.d("MOVIMENTO", "http://"+urlRobo+"/?m="+motor+"&a="+angle);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url ="http://"+urlRobo+"/?m="+motor+"&a="+angle;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        edResp.setText(response.substring(0,100));
                        Log.d("LOG", "Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOG","That didn't work!"+error.getMessage());
            }
        });

        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.config, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.config:
                Intent i = new Intent(this, Config.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

}
