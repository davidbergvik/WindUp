package se.pikehunterz.david.windup;

import android.app.Activity;
import android.os.Bundle;

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



import java.io.Console;
import java.util.Random;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1;
        final TextView tv, tv1;
        b1 = (Button) findViewById(R.id.button);
        tv_windSpeed = (TextView) findViewById(R.id.textViewTest);
        tv1 = (TextView) findViewById(R.id.textView);
        rl_arrow = (RelativeLayout) findViewById(R.id.rl_arrow);
        iv_circle = (ImageView) findViewById(R.id.circle);
        isLoading = true;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPosition();
            }
        });




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
                for(int i = 0; i < responseData.getParameters().size(); i++ ){
                    Parameter data = responseData.getParameters().get(i);
                    windString = "mafakka" + data.getName();
                    if(data.getName().equals("gust")){
                        if(data.getValues().get(0) != null){
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

                //tv.setText((Double.toString(currentWind)));

            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Failed",t.getMessage());
            }
        });


     /*   LocationResult locResult = fetchPosition();
        showWindData(locResult);
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
