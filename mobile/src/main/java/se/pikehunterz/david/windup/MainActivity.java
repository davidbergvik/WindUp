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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;




public class MainActivity extends Activity {
    RelativeLayout rl_arrow;
    ImageView iv_circle;
    boolean isLoading = true;

    public Double currentWind;
    public String windString;
    public String windDirection;


    private Button b;
    private TextView t;
    private LocationManager locationManager;
    private LocationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button b1, b2;
        final TextView tv, tv1, tv2;
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.buttonGPS);
        tv = (TextView) findViewById(R.id.textViewTest);
        tv1 = (TextView) findViewById(R.id.textView);
        tv2 = (TextView) findViewById(R.id.coordinates);
        rl_arrow = (RelativeLayout) findViewById(R.id.rl_arrow);
        iv_circle = (ImageView) findViewById(R.id.circle);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeatherAPI.Factory.getInstance().getWeather().enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Response<Weather> response, Retrofit retrofit) {

                        TimeSeries responseData = response.body().getTimeSeries().get(0);

                        //Toast.makeText(new MainActivity(), "Your Message", Toast.LENGTH_LONG).show();

                        for (int i = 0; i < responseData.getParameters().size(); i++) {
                            Parameter data = responseData.getParameters().get(i);
                            windString = "mafakka" + data.getName();
                            if (data.getName().equals("gust")) {
                                if (data.getValues().get(0) != null) {
                                    currentWind = data.getValues().get(0);
                                    tv.setText(currentWind.toString());
                                }
                            }
                            if (data.getName().equals("wd")) {
                                if (data.getValues().get(0) != null) {
                                    windDirection = data.getValues().get(0).toString();
                                    tv1.setText(windDirection);
                                }
                            }

                        }

                        //tv.setText((Double.toString(currentWind)));

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e("Failed", t.getMessage());
                    }
                });



        }

        });

        setContentView(R.layout.activity_main);

        t = (TextView) findViewById(R.id.coordinates);
        b = (Button) findViewById(R.id.buttonGPS);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                Toast.makeText(getApplicationContext(), String.valueOf(location.getLatitude()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5, 0, listener);
            }
        });

        iv_circle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isLoading) {
                    stopSpinning();
                    isLoading = false;
                } else {
                    startSpinning();
                    isLoading = true;
                }

            }

        });

        loadPosition();
    }



    private void loadPosition() {
        startSpinning();

     /*   LocationResult locResult = fetchPosition();
        showWindData(locResult);
        /* When done:
        stopSpinning();
*/
    }
    /*
    //Get current postition of device from gps
    LocationResult fetchPosition(){
        LocationResult currentPosition;
        return currentPosition;
    }
    //Show wind data of current position
    void showWindData(LocationResult position){
>>>>>>> 9e5990e94107640485686ffb9018583cca43aabe

    }
    */
    private void stopSpinning() {
        rl_arrow.clearAnimation();
    }

    public void startSpinning() {
        Animation loadingSpin = null;
        RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        rl_arrow.startAnimation(rotate);
    }

}
