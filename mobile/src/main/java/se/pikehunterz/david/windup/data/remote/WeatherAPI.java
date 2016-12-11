package se.pikehunterz.david.windup.data.remote;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import se.pikehunterz.david.windup.data.model.Weather;

/**
 * Created by jespersjoberg on 2016-12-10.
 */

public interface WeatherAPI {

    String BASE_URL = "http://opendata-download-metanalys.smhi.se/api/category/mesan1g/version/1/geotype/point/lon/16/lat/58/";


    @GET("data.json")
    Call<Weather> getWeather();


    class Factory{

        private static WeatherAPI service;

        public static WeatherAPI getInstance() {


            if (service == null) {

            Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build();


            service = retrofit.create(WeatherAPI.class);
                return service;
            }
            else{
                return service;
            }

        }

    }


}
