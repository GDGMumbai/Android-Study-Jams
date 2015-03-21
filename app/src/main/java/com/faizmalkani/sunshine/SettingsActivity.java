package com.faizmalkani.sunshine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SettingsActivity extends ActionBarActivity
{
    RadioGroup mRadioUnit;
    RadioButton mRadioCelsius, mRadioFahrenheit;
    ImageButton mEditCity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("com.faizmalkani.sunshine", MODE_PRIVATE);
        final SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
        mRadioUnit = (RadioGroup) findViewById(R.id.radiogroup_unit);
        mRadioCelsius = (RadioButton) findViewById(R.id.radio_celsius);
        mRadioFahrenheit = (RadioButton) findViewById(R.id.radio_farenheit);
        if(mSharedPreferences.getString("unit", null).equalsIgnoreCase("fahrenheit"))
        {
            mRadioFahrenheit.setChecked(true);
            mRadioCelsius.setChecked(false);
        }
        else
        {
            mRadioCelsius.setChecked(true);
            mRadioFahrenheit.setChecked(false);
        }
        mRadioUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkedId == R.id.radio_celsius)
                {
                    mSharedPreferencesEditor.putString("unit", "celsius");
                    mSharedPreferencesEditor.commit();
                }
                else if (checkedId == R.id.radio_farenheit)
                {
                    mSharedPreferencesEditor.putString("unit", "fahrenheit");
                    mSharedPreferencesEditor.commit();
                }
            }
        });

        Toolbar mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setTitle("Settings");
        final TextView mCurrentCity = (TextView) findViewById(R.id.settings_current_city);
        mCurrentCity.setText(mSharedPreferences.getString("city_name", null));
        mEditCity = (ImageButton) findViewById(R.id.settings_edit_city);
        mEditCity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Dialog mDialog = new Dialog(SettingsActivity.this);
                mDialog.setContentView(R.layout.edit_city_dialog);
                final EditText mEditCityInput = (EditText)mDialog.findViewById(R.id.dialog_edit_input);
                final Button mDialogDone = (Button) mDialog.findViewById(R.id.dialog_done_button);
                mDialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if(mEditCityInput.getText().toString().length() != 0)
                        {
                            mSharedPreferencesEditor.putString("city_name", mEditCityInput.getText().toString());
                            mSharedPreferencesEditor.commit();
                            mCurrentCity.setText(mEditCityInput.getText().toString());
                            mDialog.dismiss();
                        }
                        else
                        {
                            mDialog.dismiss();
                        }
                    }
                });
                mDialog.show();

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            finish();
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_slide_out_bottom);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
