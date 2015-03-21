package com.faizmalkani.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import zh.wang.android.apis.yweathergetter4a.WeatherInfo;
import zh.wang.android.apis.yweathergetter4a.YahooWeather;
import zh.wang.android.apis.yweathergetter4a.YahooWeatherInfoListener;


public class MainActivity extends ActionBarActivity implements YahooWeatherInfoListener
{
    Toolbar mToolbar;
    private YahooWeather mYahooWeather = YahooWeather.getInstance(5000, 5000, true);
    String mCityName;
    TextView mTempText, mConditionText, mLocationText;
    SharedPreferences mSharedPreferences;
    List<String> mConditions = new ArrayList<String>();
    ListView mConditionsList;
    ListAdapter mConditionsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getApplicationContext().getSharedPreferences("com.faizmalkani.sunshine", MODE_PRIVATE);
        if (mSharedPreferences.getString("city_name", null) == null)
        {
            startActivity(new Intent(this, FirstRunActivity.class));
            overridePendingTransition(R.anim.abc_fade_out, R.anim.abc_slide_in_bottom);
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mToolbar.setTitle("Sunshine");
        setSupportActionBar(mToolbar);
        mTempText = (TextView) findViewById(R.id.text_temp);
        mConditionText = (TextView) findViewById(R.id.text_condition);
        mLocationText = (TextView) findViewById(R.id.text_location);
        mConditionsList = (ListView) findViewById(R.id.conditions_list);
        mCityName = mSharedPreferences.getString("city_name", null);
        mYahooWeather.queryYahooWeatherByPlaceName(getApplicationContext(), mCityName, this);
    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo)
    {
        if(weatherInfo != null)
        {

            if(mSharedPreferences.getString("unit", "celsius").equalsIgnoreCase("fahrenheit"))
            {
                mTempText.setText(String.valueOf(weatherInfo.getCurrentTempF()) + "°");
            }
            else
            {
                mTempText.setText(String.valueOf(weatherInfo.getCurrentTempC()) + "°");
            }
            mConditionText.setText(weatherInfo.getCurrentText());
            mLocationText.setText(mCityName);
            mConditions.clear();
            mConditions.add(weatherInfo.getAtmosphereHumidity() + "%");
            mConditions.add(weatherInfo.getAtmospherePressure() + "mB");
            mConditions.add(weatherInfo.getWindSpeed() + "km/h");
            mConditionsListAdapter = new ListAdapter(mConditions, getApplicationContext());
            mConditionsList.setAdapter(mConditionsListAdapter);
            mConditionsListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mCityName = mSharedPreferences.getString("city_name", null);
        mYahooWeather.queryYahooWeatherByPlaceName(getApplicationContext(), mCityName, this);
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            startActivity(new Intent(this, SettingsActivity.class));
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            return true;
        }

        else if (id == R.id.action_about)
        {
            startActivity(new Intent(this, AboutActivity.class));
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
