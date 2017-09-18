package br.ufpr.c3sl.roboarm;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Config extends AppCompatActivity {
    public static String FILENAME = "config";
    public static String serverURL="";
    private EditText edURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        edURL = (EditText)findViewById(R.id.edURL);

        try {
            Config.getConfig(getBaseContext());
            edURL.setText(Config.getServerURL(getBaseContext()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((ImageButton)findViewById(R.id.btConfirm)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    RegisterConfig(edURL.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void RegisterConfig(String serverName) throws IOException {
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(("serverName="+serverName).getBytes());
        serverURL=serverName;
        edURL.setText(serverURL);
        fos.close();
    }

    public static String getServerURL(Context context) throws IOException {
        if(serverURL.equals("")){
            Config.getConfig(context);
        }
        return serverURL;
    }

    public static String getConfig(Context context) throws IOException {
        String config = "";
        FileInputStream fis = context.openFileInput(Config.FILENAME);
        if (fis != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while (( line = bufferedReader.readLine() ) != null) {
                String[] infos = line.split("=");
                if(infos[0].equals("serverName"))
                    serverURL = infos[1];
                config+=line;
            }
            fis.close();
        }

        return config;
    }
}
