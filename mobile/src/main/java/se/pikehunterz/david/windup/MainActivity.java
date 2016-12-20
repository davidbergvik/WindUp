package se.pikehunterz.david.windup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import se.pikehunterz.david.windup.data.model.Parameter;
import se.pikehunterz.david.windup.data.model.TimeSeries;
import se.pikehunterz.david.windup.data.model.Weather;
import se.pikehunterz.david.windup.data.remote.WeatherAPI;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
    RelativeLayout rl_arrow;
    ImageView iv_circle;
    TextView tv_windSpeed;
    boolean isLoading;
    int currentWindAngle;
    public Double currentWind;
    public String windString;
    public String windDirection;
    RotateAnimation ani_rotate;

    private Button btn_loadGPS;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button b1;
        b1 = (Button) findViewById(R.id.button);
        tv_windSpeed = (TextView) findViewById(R.id.textViewTest);
        rl_arrow = (RelativeLayout) findViewById(R.id.rl_arrow);
        iv_circle = (ImageView) findViewById(R.id.circle);
        isLoading = true;
        t = (TextView) findViewById(R.id.coordinates);
        btn_loadGPS = (Button) findViewById(R.id.buttonGPS);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        configureGPSButton();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPosition();
            }
        });
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
                Log.d("DEBUG_GPS","Location changed to: " + String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLatitude()));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("DEBUG_GPS","Status changed");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("DEBUG_GPS","provider enabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("DEBUG_GPS","provider disabled");
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        startSpinning();
    }
    private void loadPosition() {
        //Temp function for calculating curent wind angle

        WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Response<Weather> response, Retrofit retrofit) {
                int windAngle = 0;
                TimeSeries responseData = response.body().getTimeSeries().get(0);

                //Toast.makeText(new MainActivity(), "Your Message", Toast.LENGTH_LONG).show();
                isLoading = false;
                for (int i = 0; i < responseData.getParameters().size(); i++) {
                    Parameter data = responseData.getParameters().get(i);
                    windString = "mafakka" + data.getName();
                    if (data.getName().equals("gust")) {
                        if (data.getValues().get(0) != null) {
                            currentWind = data.getValues().get(0);
                            tv_windSpeed.setText(currentWind.toString());
                        }
                    }
                    if(data.getName().equals("wd")){
                        if(data.getValues().get(0) != null){
                            Log.d("DEBUG","FOund wind direction");
                            windAngle = (int) Math.round(data.getValues().get(0));
                            isLoading = false;
                            currentWindAngle = getMedianWindAngle(windAngle);
                        }
                    }
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("Failed",t.getMessage());
            }
        });

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configureGPSButton();
                break;
            default:
                break;
        }
    }

    void configureGPSButton(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        btn_loadGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 50000, 1, listener);
                Log.d("DEBUG_GPS","Location requested");
            }
        });
    }



    private int getMedianWindAngle(int windData){
        int medianAngle;
            medianAngle= windData;
        return medianAngle;
    }

    public void startSpinning() {
        ani_rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
        ani_rotate .setDuration(2000);
        ani_rotate .setRepeatCount(Animation.INFINITE);
        ani_rotate .setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {
                if(!isLoading){
                    ani_rotate.cancel();
                    animateToCurrentWindAngle(currentWindAngle);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });

        rl_arrow.startAnimation(ani_rotate );

    }
    private void animateToCurrentWindAngle(final int direction){
        RotateAnimation ani_setWindRotation = new RotateAnimation(0,direction, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
        ani_setWindRotation.setDuration(700);
        ani_setWindRotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                rl_arrow.setRotation(currentWindAngle);
            }
        });
        rl_arrow.startAnimation(ani_setWindRotation );

    }


}
