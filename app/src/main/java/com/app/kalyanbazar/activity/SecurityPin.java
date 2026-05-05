package com.app.kalyanbazar.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.kalyanbazar.R;
import com.app.kalyanbazar.utils.Constants;
import com.app.kalyanbazar.utils.MyApplication;
import com.app.kalyanbazar.utils.PFCodeView;

import com.google.gson.JsonObject;


import java.net.URLEncoder;

public class SecurityPin extends AppCompatActivity
{
     private PFCodeView mCodeView;
    private String mCode = "1234";
    ProgressDialog pDialog;
    private final boolean mIsCreateMode = true;
    SharedPreferences prefs;
    private View mDeleteButton;
    TextView button_left, tvnumber;
    String security_pin;
    String paymentmethod;
    String point;
    String userid, whatappnumber;
    String screen;
    String note = "Something";
    String phone = "";
     JsonObject internalObject = new JsonObject();
   // IGoogleApi mservice;
    RelativeLayout mainrelay;
    RelativeLayout rl1;
    private final PFCodeView.OnPFCodeListener mCodeListener = new PFCodeView.OnPFCodeListener() {
        @Override
        public void onCodeCompleted(String code) {
            Log.e("code", "    " + screen + "    " + paymentmethod + "          " + point);
            if (code.equals(security_pin)) {
                SharedPreferences sharedpreferences = getSharedPreferences("MyPrf", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.remove("pinenter");
                editor.apply();
                if (screen.equals("1")) {
                    Intent intent = new Intent(SecurityPin.this, HomeDashboardActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else if (screen.equals("2")) {
                 //   apiuserwithdrawfundrequest();
                } else {
                }

            } else {
                cleanCode();
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    v.vibrate(500);
                }
                Animation shake = AnimationUtils.loadAnimation(SecurityPin.this, R.anim.shake);
                mCodeView.startAnimation(shake);

            }
        }

        @Override
        public void onCodeNotCompleted(String code) {
            if (mIsCreateMode) {
                // mNextButton.setVisibility(View.INVISIBLE);
                return;
            }
        }
    };
    private void initKeyViews() {
        findViewById(R.id.button_0).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_1).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_2).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_3).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_4).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_5).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_6).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_7).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_8).setOnClickListener(mOnKeyClickListener);
        findViewById(R.id.button_9).setOnClickListener(mOnKeyClickListener);
    }
    private final View.OnClickListener mOnKeyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof TextView) {
                final String string = ((TextView) v).getText().toString();
                if (string.length() != 1) {
                    return;
                }
              Log.e("code","  LL     "+string);
                final int codeLength = mCodeView.input(string);
                configureRightButton(codeLength);
            }
        }
    };
    private final View.OnClickListener mOnDeleteButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int codeLength = mCodeView.delete();
            configureRightButton(codeLength);
        }
    };
    private final View.OnLongClickListener mOnDeleteButtonOnLongClickListener
            = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCodeView.clearCode();
            configureRightButton(0);
            return true;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_pin);

        prefs = getSharedPreferences("MyPrf", Context.MODE_PRIVATE);
       // security_pin = prefs.getString("security_pin", null);
       // security_pin = "1122";
        security_pin = MyApplication.tinyDB.getString(Constants.SharedPref.USERPIN,"");
        Log.e("PinEnter","PinENter==>>>Secu"+security_pin);
        Intent intent = getIntent();

        phone = intent.getStringExtra("phone");
        userid = prefs.getString("user_id", null);
       // phone = prefs.getString("phone", null);
        whatappnumber = prefs.getString("whatappnumber", null);
        Log.e("PinEnter","PinENter==>>>phone"+phone);
        Log.e("PinEnter","PinENter==>>>whatappnumber"+whatappnumber);

        mainrelay = findViewById(R.id.mainrelay);
        tvnumber = findViewById(R.id.tvnumber);
        rl1 = findViewById(R.id.rl1);
        tvnumber.setText(whatappnumber);

        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    PackageManager packageManager = SecurityPin.this.getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone="+ "+91"+phone +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }else {
                    }
                } catch(Exception e) {
                    Log.e("ERROR WHATSAPP",e.toString());
                }
            }
        });
        tvnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
            }
        });

        paymentmethod = getIntent().getStringExtra("paymentmethod");
        point = getIntent().getStringExtra("point");
        screen = getIntent().getStringExtra("screen");
        note = getIntent().getStringExtra("note");

        if(screen.equals("1")) {
            SharedPreferences sharedpreferences = getSharedPreferences("MyPrf", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("pinenter", "1234");
            editor.apply();
        }else{
        }

        mCodeView = findViewById(R.id.code_view);
        initKeyViews();
        mDeleteButton = findViewById(R.id.button_delete);
        button_left = findViewById(R.id.button_left);
        mDeleteButton.setOnClickListener(mOnDeleteButtonClickListener);
        mDeleteButton.setOnLongClickListener(mOnDeleteButtonOnLongClickListener);
        mCodeView.setListener(mCodeListener);

        button_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanCode();
            }
        });
       // mservice = CommonUrl.getGoogleApi();
     //   pDialog = Utility.progressDialog(SecurityPin.this);
      //  pDialog.dismiss();
    }
    private void configureRightButton(int codeLength) {
        if (mIsCreateMode) {
            if (codeLength > 0) {
                mDeleteButton.setVisibility(View.VISIBLE);
                button_left.setVisibility(View.VISIBLE);
            } else {
                mDeleteButton.setVisibility(View.GONE);
                button_left.setVisibility(View.GONE);
            }
            return;
        }

        if (codeLength > 0) {
            //  mFingerprintButton.setVisibility(View.GONE);
            mDeleteButton.setVisibility(View.VISIBLE);
            mDeleteButton.setEnabled(true);
            return;
        }

        mDeleteButton.setEnabled(false);

    }
    private void cleanCode() {
        mCode = "";
        mCodeView.clearCode();
    }


}

