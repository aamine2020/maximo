package model.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_de_stock.R;

public class Configuration extends AppCompatActivity {
    private SharedPreferences confPreferences;
    private SharedPreferences.Editor confPrefsEditor;
    private Boolean saveConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        confPreferences = getSharedPreferences("confPrefs", MODE_PRIVATE);
        confPrefsEditor = confPreferences.edit();}
        private void conf () {

            final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog, null);

            final EditText hostName = dialogView.findViewById(R.id.Hostname);
            final EditText Port = dialogView.findViewById(R.id.Port);


            saveConf = confPreferences.getBoolean("saveConf", false);
            if (saveConf == true) {
                hostName.setText(confPreferences.getString("hostname", ""));
                Port.setText(confPreferences.getString("port", ""));
            }




            Button button2 = (Button) dialogView.findViewById(R.id.Ok);
            Button button1 = (Button) dialogView.findViewById(R.id.buttonCancel);


            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(hostName.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(Port.getWindowToken(), 0);
                    String host = hostName.getText().toString().trim();
                    String po = Port.getText().toString().trim();


                    confPrefsEditor.putBoolean("saveConf", true);
                    confPrefsEditor.putString("hostname", host);
                    confPrefsEditor.putString("port", po);
                    confPrefsEditor.commit();


                    dialogBuilder.dismiss();
                }
            });

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // DO SOMETHINGS

                    dialogBuilder.dismiss();
                }
            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();

        }

    }
