package io.rewardnxt.vivekburada.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login_1_wsdActivity extends AppCompatActivity  implements View.OnClickListener{


@BindView(R.id.login) FrameLayout login;
@BindView(R.id.email) EditText email;
@BindView(R.id.password) EditText password;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1_wsd);
        ButterKnife.bind(this);
        login.setOnClickListener(this);
        //TODO Uncomment below function in production mode
//        checkIfAlreadyLoggedIn();



    }

    @Override
    public void onClick(View view) {


        switch (view.getId() /*to get clicked view id**/) {


                // do something when the corky2 is clicked

            case R.id.login:

                // do something when the corky3 is clicked


                String _email = email.getText().toString().trim();
                String _password = password.getText().toString().trim();

                if(TextUtils.isEmpty(_email)){
                    email.setError("Email cannot be Empty");
                    email.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
                    email.setError("Please Enter a Valid Email");
                    email.requestFocus();
                    return;
                }

                if(_password.length() < 6 ){
                    password.setError("Password must be atleast 6 characters");
                    password.requestFocus();
                    return;
                }


                startActivity(new Intent(Login_1_wsdActivity.this, MainActivity.class));
                finish();


                /*
                //TODO Uncomment this function on production!
                firebaseAuth.signInWithEmailAndPassword(_email,_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){


                            startActivity(new Intent(Login_1_wsdActivity.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Login_1_wsdActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                */



                break;
            default:
                break;
        }



    }

    /*
    //TODO UNCOMMENT ON PRODUCTION If the user is already logged in,  Then we redirect him to the MainScreen
    private void checkIfAlreadyLoggedIn() {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    */

}
