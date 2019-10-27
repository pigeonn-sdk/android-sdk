package io.pigeonn.pigeonnsdk.libs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {


    AlertDialog mAlertDialog;


    public void alert(String message){
      try {
          if(message==null)
              return;
          if(message.toLowerCase().contains("unable to resolve host")){
              ondisconnected();
              return;
          }
          if(message.toLowerCase().contains("canceled")){
              return;
          }
          if(message.toLowerCase().contains("socket")){
              return;
          }
          if(message.toLowerCase().contains("attempt to invoke virtual method")){
              return;
          }
          new AlertDialog.Builder(this).setMessage(message).setTitle("Alert").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
              }
          }).show();
      }catch (WindowManager.BadTokenException e){

      }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout());
        bindViews();
        initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public abstract void initialize();

    public abstract int layout();

    public abstract void  bindViews();

    public void ondisconnected() {
        if (mAlertDialog == null || !mAlertDialog.isShowing())
            mAlertDialog = new AlertDialog.Builder(this)
                    .setMessage("No Internet Connection Available.").setTitle("Alert").setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


    public void typeface(int id, Typeface a) {
        ((TextView) findViewById(id)).setTypeface(a);
    }

    public void setText(int id, String text) {
        ((TextView) findViewById(id)).setText(text);
    }

    public String text(int id) {
        return ((TextView) findViewById(id)).getText().toString();
    }

    public void setHtml(int id,String text){
        ((TextView) findViewById(id)).setText(Html.fromHtml(text));
    }

    public boolean isPermission(Context c,String permission){
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public static float size(int dp) {
        return  (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
