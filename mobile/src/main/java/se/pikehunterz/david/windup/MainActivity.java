package se.pikehunterz.david.windup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.location.LocationResult;

import java.io.Console;
import java.util.Random;

public class MainActivity extends Activity {
    RelativeLayout rl_arrow;
    ImageView iv_circle;
    boolean isLoading;
    int currentWindAngle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoading = true;
        rl_arrow = (RelativeLayout) findViewById(R.id.rl_arrow);
        iv_circle = (ImageView) findViewById(R.id.circle);
        iv_circle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (isLoading) {
                    isLoading = false;
                    loadPosition();

                } else {
                    stopSpinning();
                    /*
                    startSpinning();
                    isLoading = true;
                    */
                }

            }

        });
        startSpinning();
    }
    private void loadPosition() {
        //Temp function for calculating curent wind angle
        int angle = 0;
     /*   LocationResult locResult = fetchPosition();
        showWindData(locResult);
     */
        currentWindAngle = getMedianWindAngle(angle);
    }
    /*
    //Get current postition of device from gps
    LocationResult fetchPosition(){
        LocationResult currentPosition;
        return currentPosition;
    }
    //Show wind data of current position
    void showWindData(LocationResult position){

    }
    */
    private int getMedianWindAngle(int windData){
        int medianAngle;
        if (windData == 0) {

            Random rand = new Random();
            windData = rand.nextInt(360);
            medianAngle = windData;
        }else{
            medianAngle= 45;
        }

        return medianAngle;
    }

    private void stopSpinning() {
        rl_arrow.clearAnimation();
    }

    public void startSpinning() {
        RotateAnimation ani_rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f , Animation.RELATIVE_TO_SELF,0.5f );
        ani_rotate .setDuration(2000);
        ani_rotate .setRepeatCount(Animation.INFINITE);
        ani_rotate .setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!isLoading){
                    stopSpinning();
                    animateToCurrentWindAngle(currentWindAngle);
                }
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
