package model.Login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_de_stock.R;


import java.net.URL;
import java.util.List;

import controller.QueryService;
import model.Home.HomeActivity;
import controller.ApiUtils;
import controller.UserService;

import outils.Inventor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public  class LoginActivity extends AppCompatActivity  {

    private EditText username;
    private EditText password;
    private static String status;
    private EditText Port,hostname;
    private Button login;
    public ImageView menu;
    private ProgressBar loading;
  //  private ImageView link_regist;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    private SharedPreferences confPreferences;
    private SharedPreferences.Editor confPrefsEditor;

    private Boolean saveLogin;
    private Boolean saveConf;

    private static String URL_LOGIN;
//    private View v;
    private UserService userService;
    private QueryService queryService;


  //  private RadioButton secured;
   // private RadioButton non_secured;
 //   private String myText ;

  //  EditText editTextHost;
    // EditText editTextPort;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loading = (ProgressBar) findViewById(R.id.loading);
        menu = (ImageView) findViewById(R.id.menu);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        status="(status != 'OBSOLETE' and siteid = 'FLEET' and location like '%ATLANTA%')";
        login = (Button) findViewById(R.id.login);

       // link_regist = (ImageView) findViewById(R.id.link_regist);
        Port = (EditText) findViewById(R.id.Port);
        hostname = (EditText) findViewById(R.id.Hostname);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        confPreferences = getSharedPreferences("confPrefs", MODE_PRIVATE);
        confPrefsEditor = confPreferences.edit();

       // secured=findViewById(R.id.secured);
       // non_secured=findViewById(R.id.non_secured);



      // URL_LOGIN = "http://192.168.1.210:9876";
        URL_LOGIN = "http://maxgps.smartech-tn.com:9876";


      /*      if(secured.isChecked()){
                URL_LOGIN = "http://192.168.1.210:9876";
            }
            else if (non_secured.isChecked()){
                URL_LOGIN = "https://192.168.1.210:9876";
            }
*/
        userService = ApiUtils.getUserService(URL_LOGIN);
        queryService=ApiUtils.getQueryService(URL_LOGIN);

            //checkbox
            saveLogin = loginPreferences.getBoolean("saveLogin", false);
            if (saveLogin == true) {
                username.setText(loginPreferences.getString("username", ""));
                saveLoginCheckBox.setChecked(false);
            }
            saveLoginCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vie) {

                    if (vie == login) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

                        String user1 = username.getText().toString().trim();


                        if (saveLoginCheckBox.isChecked()) {
                            loginPrefsEditor.putBoolean("saveLogin", true);
                            loginPrefsEditor.putString("username", user1);

                            loginPrefsEditor.commit();

                        } else {
                            loginPrefsEditor.clear();
                            loginPrefsEditor.commit();
                        }

                    }
                }

                ;
            });



//contrôle de saisie quand je clique sur "login" btn ,  ken les champs fer8in ou nn + appel methode login()
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // valider les champs requis ...
                validateData();


            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // valider les champs requis ...
               conf();

            }
        });


/*
        //btn register yhezni "description."
        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, Description.class);

                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.anim, R.anim.anim2).toBundle();
                startActivity(i, bundle);
            }
        });


*/
    }



    private void validateData() {
        String mUser = username.getText().toString().trim();
        String mPass = password.getText().toString().trim();
      // String hNAME = hostname.getText().toString().trim();
      // String port = Port1.getText().toString().trim();

        if (!mUser.isEmpty() && !mPass.isEmpty() ){
            //do login
            doLogin(mUser, mPass);
        }
        else if (mUser.isEmpty() && mPass.isEmpty()){
            username.setError("Please insert Name");
            password.setError("Please insert password");
        }
        else if (mUser.isEmpty() && !mPass.isEmpty()){
            username.setError("Please insert Name");
        }
        else if (!mUser.isEmpty() && mPass.isEmpty()){
            password.setError("Please insert password");
        }

 }




    private void doLogin(final String username,final String password) {
        Call<Void> call = userService.login(username, password);

        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {


                    //login start main activity
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    query(username,password,status);
                    conf();
                } else {

                    Toast.makeText(LoginActivity.this, "Error " +response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("ERROR", response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error " +t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", t.getMessage());

            }

        });
    }



        public void query(String username, String password, String status){
            Call<List<Inventor>> call = queryService.query(username,password,status);

            call.enqueue(new Callback<List<Inventor>>() {

                @Override
                public void onResponse(Call<List<Inventor>> call, Response<List<Inventor>> response) {
                    if(!response.isSuccessful()){
                        Log.d("ERROR", response.message());

                    }
               else  Log.d("Code: ", String.valueOf(response.code()));

                }

                @Override
                public void onFailure(Call<List<Inventor>> call, Throwable t) {
                    Log.d("ERROR", t.getMessage());
                }
            });
        }





        private void conf (){

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


                        validate2(hostName,Port);
                       // dialogBuilder.dismiss();
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

    public void validate2(EditText host, EditText por) {
        String hNAME = host.getText().toString().trim();
        String port = por.getText().toString().trim();


        if (hNAME.isEmpty() && port.isEmpty()) {
            host.setError("Please insert hostname");
            por.setError("Please insert port");
        } else if (hNAME.isEmpty() && !port.isEmpty()) {
            host.setError("Please insert hostname");
        } else if (!hNAME.isEmpty() && port.isEmpty()) {
            por.setError("Please insert port");
        }

    }




    }
