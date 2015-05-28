package com.isil.am2.examplerest;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.isil.am2.examplerest.model.entity.response.LoginResponse;
import com.isil.am2.examplerest.view.fragments.MainFragment;
import com.isil.am2.examplerest.view.listeners.OnFragmentListener;


public class MainActivity extends ActionBarActivity implements OnFragmentListener{

    private static final String TAG = "MainActivity";
    private MainFragment mainFragment;
    private LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getExtras()!=null)
        {
            loginResponse= (LoginResponse)getIntent().getExtras().getSerializable("ENTITY");
            Log.i(TAG, " loginResponse " + loginResponse);
        }

        if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }
}
