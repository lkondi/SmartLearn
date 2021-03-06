package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;
import java.io.*;
import java.util.ArrayList;

import com.example.lkondilidis.smartlearn.Interfaces.StatusUserFlag;
import com.example.lkondilidis.smartlearn.Interfaces.StatusUserJSONFlag;
import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.helpers.InputValidation;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerUserTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;

    private User currentuser;

    //FIREBASE TEST
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    public static final String USER_DETAIL_KEY = "currentuser";


    //FIREBASE TEST
    @Override
    protected void onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check if user is null
        if (firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            currentuser = readFileInEditor();
            intent.putExtra(USER_DETAIL_KEY, currentuser);
            loginUser(intent);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //FIREBASE TEST
        auth = FirebaseAuth.getInstance();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);

    }

    /**
     * listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                saveClicked();
                verifyLogin();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                finish();
                break;
        }
    }

    private void verifyLogin() {
        //FIREBASE TEST
        auth.signInWithEmailAndPassword(textInputEditTextEmail.getText().toString(), textInputEditTextPassword.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        loginUser(mainActivity);
                        //startActivity(mainActivity);
                        //finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void loginUser(Intent intent) {
        //currentuser = readFileInEditor();
        if(currentuser == null) {
            currentuser = new User();
            currentuser.setEmail(textInputEditTextEmail.getText().toString());
            currentuser.setPassword(textInputEditTextPassword.getText().toString());
        }

        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("POST");
        auth.setUrlPath("loginUser");
        auth.setPayload(currentuser.convertToJASON(StatusUserJSONFlag.STATUS_YOUR_USER_FLAG));
        ServerUserTask serverUserTask = new ServerUserTask(null, this, auth, currentuser, null, StatusUserFlag.SERVER_STATUS_LOGIN_USER);
        serverUserTask.execute();
        intent.putExtra(USER_DETAIL_KEY, currentuser);
        startActivity(intent);
    }

    public void saveClicked() {
        try {
            OutputStreamWriter out= new OutputStreamWriter(openFileOutput("storetext.txt", 0));
            out.write(textInputEditTextEmail.getText().toString()+"\n");
            out.write(textInputEditTextPassword.getText().toString());
            out.close();
            Toast.makeText(this, "The contents are saved in the file.", Toast.LENGTH_LONG).show();
        }
        catch (Throwable t) {
            Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public User readFileInEditor() {
        User user = new User();
        ArrayList<String> lines = new ArrayList<>();
        try {
            InputStream in = openFileInput("storetext.txt");
            if (in != null) {
                InputStreamReader tmp=new InputStreamReader(in);
                BufferedReader reader=new BufferedReader(tmp);
                String str;
                StringBuilder buf=new StringBuilder();
                while ((str = reader.readLine()) != null) {
                    buf.append(str);
                    lines.add(str);
                }
                in.close();
                for(int i=0; i<lines.size(); i++){
                    user.setEmail(lines.get(0));
                    user.setPassword(lines.get(1));
                }
            }
        }

        catch (java.io.FileNotFoundException e) {
        }
        catch (Throwable t) {
            Toast.makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG).show();
        }
        return user;
    }
}