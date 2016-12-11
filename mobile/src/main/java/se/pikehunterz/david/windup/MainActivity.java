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

public class MainActivity extends Activity {

    public Double currentWind;

    public String windString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1;
        final TextView tv;
        b1 = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textViewTest);

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

    }
}
