package com.verandah.club.data.rest;

import com.google.gson.JsonObject;
import com.verandah.club.data.model.BaseResponse;
import com.verandah.club.data.model.VersionUpdateResponse;
import com.verandah.club.data.model.category.CategoryResponse;
import com.verandah.club.data.model.home.Article;
import com.verandah.club.data.model.home.MainResponse;

import java.util.List;

import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface ApiInterface {

    /*LinkedIn*/
    @FormUrlEncoded
    @POST
    Call<JsonObject> requestInboxList(@Url String url,
                                      @Field("userid") String userId,
                                      @Field("booking_numbers[]") List<String> bookingNumber);

    @GET
    Call<JsonObject> getLinkedInoAuthToken(@Url String url);

    @GET
    Call<JsonObject> getLinkedInProfile(@Url String url);

    @GET("about")
    Single<BaseResponse<AboutResponse>> getAbout();

    @GET("update_checker")
    Single<BaseResponse<VersionUpdateResponse>> checkUpdate();

    String v = "v1/";

    @Multipart
    @Headers("x-api-key: 5e32e5b6-21e7-4bc4-ba0e-679b6fbd0ba5")
    @POST(v+"home")
    Single<BaseResponse<MainResponse>> requestMain(
            @Part("fcm") RequestBody fcm,
            @Part("issue_id") RequestBody issue_id
    );

    @Multipart
    @Headers("x-api-key: 5e32e5b6-21e7-4bc4-ba0e-679b6fbd0ba5")
    @POST(v+"category")
    Single<BaseResponse<CategoryResponse>> requestCategory(
            @Part("category_id") RequestBody category_id,
            @Part("issue_id") RequestBody issue_id
    );

    @Multipart
    @Headers("x-api-key: 5e32e5b6-21e7-4bc4-ba0e-679b6fbd0ba5")
    @POST(v+"article")
    Single<BaseResponse<Article>> requestArticle(
            @Part("article_id") RequestBody article_id,
            @Part("issue_id") RequestBody issue_id
    );

    @Multipart
    @Headers("x-api-key: 5e32e5b6-21e7-4bc4-ba0e-679b6fbd0ba5")
    @POST(v+"sponsored_ads")
    Single<BaseResponse<Article>> requestSponserAd(
            @Part("sponsored_ad_id") RequestBody article_id,
            @Part("issue_id") RequestBody issue_id
    );

}
