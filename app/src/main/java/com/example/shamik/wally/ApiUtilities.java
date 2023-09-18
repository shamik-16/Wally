package com.example.shamik.wally;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {

    private static Retrofit retrofit = null;
    public static final String API = "XFP4VRr8PPQhpwFwV0rOEiI7dNeQMkpY7KKi3lRg43Is8lNPSrxrp1w4";


    public static ApiInterface getApiInterface(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(ApiInterface.Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiInterface.class);

    }


}
