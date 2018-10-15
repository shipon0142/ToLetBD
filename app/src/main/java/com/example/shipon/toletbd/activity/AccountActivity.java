package com.example.shipon.toletbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shipon.toletbd.MyCallbackClass;
import com.example.shipon.toletbd.R;
import com.example.shipon.toletbd.models.CustomRenterLoginClass;

import static com.example.shipon.toletbd.activity.Main2Activity.USER;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_NAME;
import static com.example.shipon.toletbd.activity.Main2Activity.USER_PHONE;

public class AccountActivity extends AppCompatActivity {
    TextView nameAccountTV, phoneAccountTV, postOrRentTV;
    LinearLayout mainLayoutAccount, signInAccountLayout;
    public static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        nameAccountTV = findViewById(R.id.NameAccountTV);
        phoneAccountTV = findViewById(R.id.PhoneNoAccountTV);
        postOrRentTV = findViewById(R.id.PostOrRentTV);
        mainLayoutAccount = findViewById(R.id.MainLayoutAccount);
        signInAccountLayout = findViewById(R.id.SignInAccountLayout);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mainLayoutAccount.setVisibility(View.VISIBLE);
        if (USER.equals("owner")) {
            mainLayoutAccount.setVisibility(View.VISIBLE);
            signInAccountLayout.setVisibility(View.GONE);
            nameAccountTV.setText(USER_NAME.toString() + " (Owner)");
            phoneAccountTV.setText(USER_PHONE.toString());
            postOrRentTV.setText("  My Post");
        } else if (USER.equals("renter")) {
            mainLayoutAccount.setVisibility(View.VISIBLE);
            signInAccountLayout.setVisibility(View.GONE);
            nameAccountTV.setText(USER_NAME.toString() + " (Renter)");
            phoneAccountTV.setText(USER_PHONE.toString());
            postOrRentTV.setText("  My Rent");
        } else {
            mainLayoutAccount.setVisibility(View.GONE);
            signInAccountLayout.setVisibility(View.VISIBLE);
            nameAccountTV.setText("Guest");
            phoneAccountTV.setText("");
            mainLayoutAccount.setVisibility(View.GONE);
        }
    }

    public void clickAccountClose(View view) {
        this.finish();
        //overridePendingTransition(R.anim.slide_out_up ,R.anim.slide_in_up );
    }

    public void clickSigninAccount(View view) {
        check = true;
        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();

    }

    public void clickPostOrRentTV(View view) {
        if (USER.equals("owner")) {
            Intent i = new Intent(this, MyPostActivity.class);
            startActivity(i);
           // finish();
        } else {
            Intent i = new Intent(this, MyPostActivity.class);
            startActivity(i);
            //finish();
        }
    }

    public void myProfileClick(View view) {
        if (USER.equals("renter")) {

            Intent i = new Intent(this, RenterProfileActivity.class);
            i.putExtra("phn", USER_PHONE);
            startActivity(i);
          //  finish();
        } else {
            Intent i = new Intent(this, OwnerProfileActivity.class);
            i.putExtra("phn", USER_PHONE);
            startActivity(i);
            //finish();
        }
    }

    public void ClickSwitchAccount(View view) {
        if (USER.equals("renter")) {

            CustomRenterLoginClass customLoginClass=new CustomRenterLoginClass(AccountActivity.this, "o", new MyCallbackClass() {
                @Override
                public void callback(String value) {
                    if(value.equals("ok")){
                        onResume();
                    }

                }

            });
            customLoginClass.show();
            Window window = customLoginClass .getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //  finish();
        } else {
            CustomRenterLoginClass customLoginClass=new CustomRenterLoginClass(AccountActivity.this, "r", new MyCallbackClass() {
                @Override
                public void callback(String value) {
                    if(value.equals("ok")){
                        onResume();
                    }

                }

            });
            customLoginClass.show();
            Window window = customLoginClass .getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //finish();
        }
    }

    public void ClickSignOut(View view) {
        USER = "null";
       USER_PHONE = "null";
       USER_NAME = "null";
        Toast.makeText(getApplicationContext(),"Sign out Succesful",Toast.LENGTH_SHORT).show();
        onResume();
    }
}
