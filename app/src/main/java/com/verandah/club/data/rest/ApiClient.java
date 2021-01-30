package com.verandah.club.data.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static ApiInterface getApiInterface() {
        return getRetrofit().create(ApiInterface.class);
    }

    private static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new MyResultObjectAdapterFactory())
                .create();


        return new Retrofit.Builder()
                .baseUrl("https://theverandahclub.com/api/")
              //  .baseUrl("https://www.teplar.in/demo/the-verandah-club/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               // .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient(getHttpLoggingInterceptor()))
                .build();
    }

    private static OkHttpClient getOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(getResponseInterceptor())
                .addInterceptor(new ApiInterceptor())
                .build();
    }


    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public static class MyResultObjectAdapterFactory implements TypeAdapterFactory {

        @Override
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            TypeAdapter<T> defaultAdapter = gson.getDelegateAdapter(this, type);
            return (TypeAdapter<T>) new MyResultObjectAdapter(defaultAdapter,type);
        }

        public class MyResultObjectAdapter<T> extends TypeAdapter<T> {

            protected TypeAdapter<T> defaultAdapter;
            TypeToken<T> type;

            public MyResultObjectAdapter(TypeAdapter<T> defaultAdapter, TypeToken<T> type) {
                this.defaultAdapter = defaultAdapter;
                this.type = type;
            }

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                defaultAdapter.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
            /*
            This is the critical part. So if the value is a string,
            Skip it (no exception) and return null.
            */

/*


                Class<?> rawType = type.getClass();
                boolean excludeClass = this.excludeClassChecks(rawType);
                final boolean skipSerialize = excludeClass || this.excludeClassInStrategy(rawType, true);
                final boolean skipDeserialize = excludeClass || this.excludeClassInStrategy(rawType, false);
                return !skipSerialize && !skipDeserialize ? null

*/


                // is List

                //Log.d("Class name",type.getRawType().getSimpleName());
/*                if(isInheritedClass(RetroObject.class ,type.getRawType())){

                    if (in.peek() != JsonToken.BEGIN_OBJECT) {
                        in.skipValue();
                        return null;
                    }
                }*/

                return defaultAdapter.read(in);
            }
        }
    }


        public static boolean isInheritedClass(Class<?> parent, Class<?> child) {
            if (parent == null || child == null) {
                return false;
            } else {
                if (parent.isAssignableFrom(child)) {
                    // is child or same class
                    return parent.isAssignableFrom(child.getSuperclass());
                } else {
                    return false;
                }
            }
        }

    /*{
    "status": 400,
    "message": "Email Id already exist",
    "data": ""
}
*/

/*    private static JSONObject getResult(List<String> dList , JSONObject jsonObjectResult){

        JSONObject jsonObject = new JSONObject();

        Iterator<String> keys = jsonObjectResult.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject jsonObjectStep = jsonObjectResult.optJSONObject(key);
            int pos  = ConversionUtils.parseInt(key.replaceAll("[^0-9]", ""))-1;

            try {
                jsonObject.putOpt(dList.get(pos),jsonObjectStep);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }*/

/*
    private static JSONObject getDetails(List<String> dList , JSONObject jsonObjectResult) throws JSONException {

        JSONObject jsonObject = new JSONObject();

        Iterator<String> keys = jsonObjectResult.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            Object jsonObjectStep = jsonObjectResult.get(key);
            int pos  = ConversionUtils.parseInt(key.replaceAll("[^0-9]", ""))-1;

            try {
                jsonObject.putOpt(dList.get(pos),jsonObjectStep);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }
*/



    private static Interceptor getResponseInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();


                Response response = chain.proceed(request);
                String rawJson = response.body().string();

                // todo : --> POST http://torippo-api.pofitechnologies.com/api/Booking_enquiry_restaurant
               // rawJson = rawJson.replace("\"Rc_decided_time\":\"\"","\"Rc_decided_time\":{}");
                rawJson = rawJson.replace("late_checkout_time_percent\":\"\"","late_checkout_time_percent\":{}");

                String resultJson;

                try {

                    JSONObject jsonObject = new JSONObject(rawJson);
                    if (jsonObject.has("data")) {
                        Object objectData = jsonObject.get("data");


                        if (objectData instanceof String) {
                            String stringData = (String) objectData;
                            if (stringData.isEmpty()) {
                                jsonObject.put("data", null);
                            }
                        }


                        if (objectData instanceof JSONObject) {
                            JSONObject jsonObjectData = (JSONObject) objectData;
                            if(jsonObjectData.has("result")){
                              /*  JSONArray jsonArray = jsonObjectData.optJSONArray("result");

                                String restaurantType = jsonObjectData.has("Restaurant_type")?jsonObjectData.getString("Restaurant_type"):"";

                                JSONObject jsonObjectResult =  jsonArray.optJSONObject(0);
                                String categoryId = jsonObjectResult.getJSONObject("step1").getString("Service_id");

                                List<String> dList = Category.getStepList(Category.fromResponse(categoryId),restaurantType);

                                jsonArray.remove(0);
                                jsonArray.put(0, getDetails(dList,jsonObjectResult));

                                JSONArray jsonArrayDetails = jsonObjectData.optJSONArray("details");

                                JSONObject jsonObjectDetails =  jsonArrayDetails.optJSONObject(0);
                                jsonArrayDetails.remove(0);
                                jsonArrayDetails.put(0, getDetails(dList,jsonObjectDetails));*/
                            }

                            ///////////////
                            if (jsonObjectData.has("Rest_table_time")) {
                                Object objectTable = jsonObjectData.get("Rest_table_time");

                                if (objectTable instanceof JSONObject) {
                                    JSONObject jsonObjectTable = (JSONObject) objectTable;
                                    if (jsonObjectTable.has("Rc_decided_time")) {
                                        Object objectTime = jsonObjectTable.get("Rc_decided_time");

                                        if (objectTime instanceof String) {
                                            String stringData = (String) objectTime;
                                            if (stringData.isEmpty()) {
                                                jsonObjectTable.put("Rc_decided_time", null);
                                            }
                                        }

                                    }
                                }
                            }
                        }

                        resultJson = jsonObject.toString();
                    } else {
                        resultJson = rawJson;
                    }
                } catch (JSONException e) {
                    resultJson = rawJson;
                    e.printStackTrace();
                }

                // Re-create the response before returning it because body can be read only once
                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), resultJson)).build();
            }
        };
    }
}
