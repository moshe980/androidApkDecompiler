package com.classy.survivegame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Activity_Menu extends AppCompatActivity {
    private MaterialButton menu_BTN_start;
    private TextInputEditText menu_EDT_id;

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViews();
        initViews();
    }

    private void initViews() {
        this.menu_BTN_start.setOnClickListener(new View.OnClickListener() {
            /* class com.classy.survivegame.Activity_Menu.AnonymousClass1 */

            public void onClick(View v) {
                Activity_Menu.this.makeServerCall();
            }
        });
    }

    private void findViews() {
        this.menu_BTN_start = (MaterialButton) findViewById(R.id.menu_BTN_start);
        this.menu_EDT_id = (TextInputEditText) findViewById(R.id.menu_EDT_id);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void makeServerCall() {
        new Thread() {
            /* class com.classy.survivegame.Activity_Menu.AnonymousClass2 */

            public void run() {
                String data = Activity_Menu.getJSON(Activity_Menu.this.getString(R.string.url));
                Log.d("pttt", data);
                if (data != null) {
                    Activity_Menu activity_Menu = Activity_Menu.this;
                    activity_Menu.startGame(activity_Menu.menu_EDT_id.getText().toString(), data);
                }
            }
        }.start();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startGame(String id, String data) {
        String state = data.split(",")[Integer.parseInt(String.valueOf(id.charAt(7)))];
        Intent intent = new Intent(getBaseContext(), Activity_Game.class);
        intent.putExtra(Activity_Game.EXTRA_ID, id);
        intent.putExtra(Activity_Game.EXTRA_STATE, state);
        startActivity(intent);
    }

    public static String getJSON(String url) {
        String data = "";
        HttpsURLConnection con = null;
        try {
            HttpsURLConnection con2 = (HttpsURLConnection) new URL(url).openConnection();
            con2.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line + "\n");
            }
            br.close();
            data = sb.toString();
            if (con2 != null) {
                try {
                    con2.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (MalformedURLException ex2) {
            ex2.printStackTrace();
            if (0 != 0) {
                con.disconnect();
            }
        } catch (IOException ex3) {
            ex3.printStackTrace();
            if (0 != 0) {
                con.disconnect();
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    con.disconnect();
                } catch (Exception ex4) {
                    ex4.printStackTrace();
                }
            }
            throw th;
        }
        return data;
    }
}
