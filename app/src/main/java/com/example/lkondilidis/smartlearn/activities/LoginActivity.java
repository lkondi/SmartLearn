package com.example.lkondilidis.smartlearn.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkondilidis.smartlearn.R;
import com.example.lkondilidis.smartlearn.helpers.SQLITEHelper;
import com.example.lkondilidis.smartlearn.model.User;
import com.example.lkondilidis.smartlearn.helpers.InputValidation;
import com.example.lkondilidis.smartlearn.serverClient.ApiAuthenticationClient;
import com.example.lkondilidis.smartlearn.services.ServerTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

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
    private SQLITEHelper databaseHelper;

    private User currentuser = new User();

    //FIREBASE TEST
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    public static final String USER_DETAIL_KEY = "currentuser";

    //TODO: most of this code should be on the server side. i.e. Validating user and password, initialising the database

    //FIREBASE TEST
    @Override
    protected void onStart() {
        super.onStart();

        //initObjects();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //check if user is null
        if (false){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            String email = firebaseUser.getEmail();
            currentuser = databaseHelper.getUserEmail(email);
            intent.putExtra(USER_DETAIL_KEY, currentuser);
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
        databaseHelper = new SQLITEHelper(activity);
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
                //verifyFromSQLite();
                verifyLogin();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
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
                        mainActivity.putExtra(USER_DETAIL_KEY, currentuser);
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
        currentuser.setEmail(textInputEditTextEmail.getText().toString().trim());
        currentuser.setPassword(textInputEditTextPassword.getText().toString().trim());
        ApiAuthenticationClient auth = new ApiAuthenticationClient(getString(R.string.path), currentuser.getEmail(), currentuser.getPassword());
        auth.setHttpMethod("POST");
        auth.setUrlPath("loginUser");
        auth.setPayload(currentuser.convertToJASON());
        ServerTask serverTask = new ServerTask(null, this, auth, currentuser, intent, 0);
        serverTask.execute();
    }

    /**
     * validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            //get currentuser
            currentuser = databaseHelper.getUserEmail(textInputEditTextEmail.getText().toString().trim());

            //FIREBASE TEST
            //Intent mainActivity = new Intent(this, MainActivity.class);
            //mainActivity.putExtra(USER_DETAIL_KEY, currentuser);
            //startActivity(mainActivity);


        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}