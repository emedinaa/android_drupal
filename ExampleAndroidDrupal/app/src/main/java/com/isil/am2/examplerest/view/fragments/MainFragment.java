package com.isil.am2.examplerest.view.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.isil.am2.examplerest.MainActivity;
import com.isil.am2.examplerest.R;
import com.isil.am2.examplerest.model.entity.response.LoginResponse;
import com.isil.am2.examplerest.view.listeners.OnFragmentListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG ="MainFragment" ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentListener mListener;
    private TextView tviUsername;
    private EditText eteTitle, eteBody, eteType;
    private Button btnAgregar;
    private View vLoading;

    private String title, body, type;
    private RequestQueue queue;
    private LoginResponse entity;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        entity= ((MainActivity)getActivity()).getLoginResponse();

        tviUsername= (TextView)getView().findViewById(R.id.tviUsername);
        eteTitle= (EditText)getView().findViewById(R.id.eteTitle);
        eteBody= (EditText)getView().findViewById(R.id.eteBody);
        eteType= (EditText)getView().findViewById(R.id.eteType);
        vLoading = getView().findViewById(R.id.vLoading);

        btnAgregar= (Button)getView().findViewById(R.id.btnAgregar);
        tviUsername.setText("Bienvenido " + "");

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title= eteTitle.getText().toString().trim();
                body= eteBody.getText().toString().trim();
                type= eteType.getText().toString().trim();

                addArticle();
            }
        });
    }

    private void addArticle()
    {

        vLoading.setVisibility(View.VISIBLE);
        queue = Volley.newRequestQueue(getActivity());

        String url = getString(R.string.url_drupal_article);
        Log.i("HomeActivity", "url " + url);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.i("HomeActivity", "response "+response.toString());
                        GsonBuilder gsonb = new GsonBuilder();
                        Gson gson = gsonb.create();

                        /*loginResponse=null;
                        try
                        {
                            loginResponse= gson.fromJson(response.toString(),LoginResponse.class);
                            if(loginResponse!=null)
                            {
                                Log.i(TAG, "loginResponse "+loginResponse.toString());

                                gotoHome();
                            }

                        }catch (Exception e)
                        {

                        }*/
                        vLoading.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i("HomeActivity", "Error: " + error.getMessage());
                // hide the progress dialog

                vLoading.setVisibility(View.GONE);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //return super.getParams();

                Map<String,String> nParams = new HashMap<String, String>();

                nParams.put("title", title);
                nParams.put("body", body);
                nParams.put("type", type);

                Log.d(TAG, "POST params " + nParams.toString());
                return nParams;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                //params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("Cookie",entity.getCookie());
                params.put("X-CSRF-Token", entity.getToken());

                return params;
            }
        };
        queue.add(jsonObjReq);
    }



}
