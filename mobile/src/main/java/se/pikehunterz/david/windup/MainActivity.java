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

public class MainActivity extends Activity {
    RelativeLayout rl_arrow;
    ImageView iv_circle;
    boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl_arrow = (RelativeLayout) findViewById(R.id.rl_arrow);
        iv_circle = (ImageView) findViewById(R.id.circle);
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
