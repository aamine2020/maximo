package com.example.gestion_de_stock;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jackrutorial.webService.ApiUtils;
import com.jackrutorial.webService.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// page login
public  class LoginActivity extends AppCompatActivity  {

    private EditText username;
    private EditText password;
  //  private EditText Port1,hostname;
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
        login = (Button) findViewById(R.id.login);
       // link_regist = (ImageView) findViewById(R.id.link_regist);
       // Port = (EditText) findViewById(R.id.Port);

        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        confPreferences = getSharedPreferences("confPrefs", MODE_PRIVATE);
        confPrefsEditor = confPreferences.edit();

       // secured=findViewById(R.id.secured);
       // non_secured=findViewById(R.id.non_secured);



       URL_LOGIN = "http://192.168.1.210:9876";


      /*      if(secured.isChecked()){
                URL_LOGIN = "http://192.168.1.210:9876";
            }
            else if (non_secured.isChecked()){
                URL_LOGIN = "https://192.168.1.210:9876";
            }
*/
        userService = ApiUtils.getUserService(URL_LOGIN);

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

/*
        if (!mUser.isEmpty() && !mPass.isEmpty() && !hNAME.isEmpty() && !port.isEmpty()) {
                //do login
                doLogin(mUser, mPass);

        } else if (mUser.isEmpty() && mPass.isEmpty() && hNAME.isEmpty() && port.isEmpty()) {
            username.setError("Please insert Name");
            password.setError("Please insert password");
            Hostname.setError("Please insert hostname");
            Port.setError("Please insert port");
        } else if (mUser.isEmpty() && mPass.isEmpty() && hNAME.isEmpty() && !port.isEmpty()) {
            username.setError("Please insert Name");
            password.setError("Please insert password");
            Hostname.setError("Please insert hostname");
        } else if (mUser.isEmpty() && mPass.isEmpty() && !hNAME.isEmpty() && !port.isEmpty()) {
            username.setError("Please insert Name");
            password.setError("Please insert password");
        } else if (mUser.isEmpty() && !mPass.isEmpty() && !hNAME.isEmpty() && !port.isEmpty()) {
            username.setError("Please insert Name");
        } else if (mPass.isEmpty() && !hNAME.isEmpty() && !port.isEmpty() && mUser.isEmpty()) {
            password.setError("Please insert password");
        } else if (!port.isEmpty() && hNAME.isEmpty() && !mPass.isEmpty() && !mUser.isEmpty()) {
            Hostname.setError("Please insert hostname");
        } else if (port.isEmpty() && !mUser.isEmpty() && !mPass.isEmpty() && !hNAME.isEmpty()) {
            Port.setError("Please insert port");
        }
        else if (!mUser.isEmpty() && mPass.isEmpty() && hNAME.isEmpty() && port.isEmpty()){
            password.setError("Please insert password");
            Hostname.setError("Please insert hostname");
            Port.setError("Please insert port");
        }
        else if (!mPass.isEmpty() && mUser.isEmpty() && hNAME.isEmpty() && port.isEmpty()){
            username.setError("Please insert Name");
            Hostname.setError("Please insert hostname");
            Port.setError("Please insert port");

        }
        else if (mUser.isEmpty() && mPass.isEmpty() && !hNAME.isEmpty() && port.isEmpty()){
            username.setError("Please insert Name");
            password.setError("Please insert password");
            Port.setError("Please insert port");
        }
        else if (mUser.isEmpty() && mPass.isEmpty() && hNAME.isEmpty() && !port.isEmpty()){
            username.setError("Please insert Name");
            password.setError("Please insert password");
            Hostname.setError("Please insert hostname");
        }

*/

    }



    private void doLogin(final String username,final String password) {
        Call<Void> call = userService.login(username, password);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {

                    //login start main activity
                    Intent intent = new Intent(LoginActivity.this, Description.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
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

// alertedialogue


/*
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(LoginActivity.this);
                mydialog.setTitle("Hostname");

                final EditText Input = new EditText(LoginActivity.this);
                Input.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(Input);

                mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myText = Input.getText().toString();
                        Toast.makeText(LoginActivity.this, "Hostname" + myText, Toast.LENGTH_LONG).show();

                    }
                });

                mydialog.setPositiveButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });


            }


        });

 */

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


           // setHP(this,hostName.getText().toString(),Port.getText().toString());

            Button button2 = (Button) dialogView.findViewById(R.id.Ok);
            Button button1 = (Button) dialogView.findViewById(R.id.buttonCancel);




            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    final String Host = sharedPreferences.getString("hostname", "");
                    String portt = sharedPreferences.getString("port", "");
*/




                     //   String port = editTextPort.getText().toString().trim();

                        //  String prenom = .getText().toString();
                        // String ageStr = Port.getText().toString();
                        //    int age = Integer.parseInt(ageStr);

                    /*    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("hostname", String.valueOf(Hostname));
                        editor.putString("port", String.valueOf(Port));

                        editor.apply();*/

                        //editor.remove("prenom");
                        //editor.apply();

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
