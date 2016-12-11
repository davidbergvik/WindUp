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



public class MainActivity extends Activity {
    RelativeLayout rl_arrow;
    ImageView iv_circle;
    boolean isLoading = true;

    public Double currentWind;
    public String windString;
    public String windDirection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1;
        final TextView tv, tv1;
        b1 = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textViewTest);
        tv1 = (TextView) findViewById(R.id.textView);
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

                        for(int i = 0; i < responseData.getParameters().size(); i++ ){
                            Parameter data = responseData.getParameters().get(i);
                            windString = "mafakka" + data.getName();
                            if(data.getName().equals("gust")){
                                if(data.getValues().get(0) != null){
                                    currentWind = data.getValues().get(0);
                                    tv.setText(currentWind.toString());
                                }
                            }
                            if(data.getName().equals("wd")){
                                if(data.getValues().get(0) != null){
                                    windDirection = data.getValues().get(0).toString();
                                    tv1.setText(windDirection);
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
