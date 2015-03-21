package com.faizmalkani.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FirstRunActivity extends ActionBarActivity
{
    EditText mFirstRunCity;
    ImageButton mFirstRunDone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstrun);
        SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("com.faizmalkani.sunshine", MODE_PRIVATE);
        final SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
        mFirstRunCity = (EditText) findViewById(R.id.firstrun_city_name);
        mFirstRunDone = (ImageButton) findViewById(R.id.firstrun_done);
        mFirstRunDone.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mFirstRunCity.getText().toString().length() != 0)
                {
                    mSharedPreferencesEditor.putString("city_name", mFirstRunCity.getText().toString());
                    mSharedPreferencesEditor.commit();
                    startActivity(new Intent(FirstRunActivity.this, MainActivity.class));
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter a city", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





}
