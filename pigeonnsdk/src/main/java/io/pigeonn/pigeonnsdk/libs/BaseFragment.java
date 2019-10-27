
package io.pigeonn.pigeonnsdk.libs;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import io.eka.ekav2.R;
import io.eka.ekav2.Singletons.FirebaseInstance.Firebase;

public abstract class BaseFragment extends Fragment {


    private boolean mHasInflated = false;
    private ViewStub mViewStub;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lazy_fragment, container, false);
        mViewStub = (ViewStub) view.findViewById(R.id.fragmentViewStub);
        mViewStub.setLayoutResource(layout());
        if (getUserVisibleHint() && !mHasInflated) {
            View inflatedView = mViewStub.inflate();
            oncreateview(inflatedView);
        }
        Firebase.INSTANCE.addScreenViewEvent(this.getClass().getName());
        return view;
    }

    public boolean isLocationPermission(Context c){
        String permission = Manifest.permission.ACCESS_FINE_LOCATION;
        int res = c.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }


    protected void setTypeface(View v, int id, Typeface typeface){
        TextView view = v.findViewById(id);
        view.setTypeface(typeface);
    }

    protected void setText(View v, int id, String typeface){
        TextView view = v.findViewById(id);
        view.setText(typeface);
    }

    protected void gone(View v, int id){
        v.findViewById(id).setVisibility(View.GONE);
    }

    protected void visible(View v, int id){
        v.findViewById(id).setVisibility(View.VISIBLE);
    }

    protected abstract void oncreateview(View inflatedView);

    @LayoutRes
    protected abstract int layout();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mViewStub != null && !mHasInflated) {
            View inflatedView = mViewStub.inflate();
            oncreateview(inflatedView);
        }
    }

    // Thanks to Noa Drach, this will fix the orientation change problem
    @Override
    public void onDetach() {
        super.onDetach();
        mHasInflated = false;
    }

    public void showMessage(String m){
        new AlertDialog.Builder(getContext()).setMessage(m).setTitle("Alert").setPositiveButton("Ok",(dialog, which) -> dialog.dismiss()).show();
    }

    public static float size(int dp) {
        return  (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
