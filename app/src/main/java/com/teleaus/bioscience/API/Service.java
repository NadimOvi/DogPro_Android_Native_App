package com.teleaus.bioscience.API;

import com.teleaus.bioscience.Fragments.DiseasInfoFragment;
import com.teleaus.bioscience.LocalDatabase.HistoryShowMainModel;
import com.teleaus.bioscience.LocalDatabase.QueryModels;
import com.teleaus.bioscience.Model.Associated_diseases;
import com.teleaus.bioscience.Model.BreedHistoryMainModel;
import com.teleaus.bioscience.Model.BreedInfoModel;
import com.teleaus.bioscience.Model.BreedMainModelList;
import com.teleaus.bioscience.Model.DiseaseInfoModel;
import com.teleaus.bioscience.Model.FilterModelClass;
import com.teleaus.bioscience.Model.MainModelClass;
import com.teleaus.bioscience.Model.getBreedHistoryListModel;
import com.teleaus.bioscience.Model.getPhysicalAttibutesModel;

import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    @Multipart
    @POST("classify_breed/")
    Call<MainModelClass> postImage(@Header("X-Auth-Token") String token,
                                    @Part MultipartBody.Part photo);


    @POST("breeds_list/")
    Call<BreedMainModelList> postBreedList(@Header("X-Auth-Token") String token);

    @POST("breeds_list/")
    Call<BreedMainModelList> postBreedListSearch( @Query("query") String search,
                                                @Header("X-Auth-Token") String token);

    @POST("breed_info/")
    Call<BreedInfoModel> postBreedListInfo(@Query("breed_name") String breedname);

    @GET("history")
    Call<BreedHistoryMainModel> breedHistory();

    @POST("{id}")
    Call<JSONObject> postFeedBack(@Path("id") Integer id);

    @GET("history")
    Call<HistoryShowMainModel> getHistoryList(@Query("ids") String id);

    @POST("query_parameters/")
    Call<QueryModels> queryRetrofitCall(@Header("X-Auth-Token") String token);

    @POST("search_breed/")
    Call<FilterModelClass> querySearchAll(@Header("X-Auth-Token") String token,
                                           @Query("height_range") List<Integer> height_range,
                                           @Query("weight_range") List<Integer> weight_range,
                                           @Query("temperaments_list") List<String> temperaments_list,
                                           @Query("utilization_list") List<String> utilization_list);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeight(@Header("X-Auth-Token") String token,
                                           @Query("height_range") List<Integer> height_range);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithWeight(@Header("X-Auth-Token") String token,
                                                 @Query("weight_range") List<Integer> weight_range);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithTemperament(@Header("X-Auth-Token") String token,
                                                 @Query("temperaments_list") List<String> temperaments_list);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithUtilization(@Header("X-Auth-Token") String token,
                                                      @Query("utilization_list") List<String> utilization_list);

    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeightAndWeight(@Header("X-Auth-Token") String token,
                                                 @Query("height_range") List<Integer> height_range,
                                                          @Query("weight_range") List<Integer> weight_range);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeightAndTemperament(@Header("X-Auth-Token") String token,
                                                          @Query("height_range") List<Integer> height_range,
                                                               @Query("temperaments_list") List<String> temperaments_list);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeightAndUtilization(@Header("X-Auth-Token") String token,
                                                               @Query("height_range") List<Integer> height_range,
                                                               @Query("utilization_list") List<String> utilization_list);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithWeightAndTemperament(@Header("X-Auth-Token") String token,
                                                               @Query("weight_range") List<Integer> weight_range,
                                                               @Query("temperaments_list") List<String> temperaments_list);
    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithWeightAndUtilization(@Header("X-Auth-Token") String token,
                                                               @Query("weight_range") List<Integer> weight_range,
                                                               @Query("utilization_list") List<String> utilization_list);

    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithTemperamentAndUtilization(@Header("X-Auth-Token") String token,
                                                                    @Query("temperaments_list") List<String> temperaments_list,
                                                                    @Query("utilization_list") List<String> utilization_list);

    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeightAndWeightAndTemperament(@Header("X-Auth-Token") String token,
                                                                        @Query("height_range") List<Integer> height_range,
                                                                        @Query("weight_range") List<Integer> weight_range,
                                                                        @Query("temperaments_list") List<String> temperaments_list);

    @POST("search_breed/")
    Call<FilterModelClass> querySearchWithHeightAndWeightAndUtilization(@Header("X-Auth-Token") String token,
                                                                             @Query("height_range") List<Integer> height_range,
                                                                             @Query("weight_range") List<Integer> weight_range,
                                                                             @Query("utilization_list") List<String> utilization_list);

    @POST("get_breed_history_list/")
    Call<getBreedHistoryListModel> getBreedHistory(@Header("X-Auth-Token") String token);

    @POST("get_physical_attributes_list/")
    Call<getPhysicalAttibutesModel> getPhysicalAttibutes(@Header("X-Auth-Token") String token);

    @POST("disease_info/")
    Call<DiseaseInfoModel> getDiseaseInfoModel(@Query("disease_id") String disease_id,
                                               @Header("X-Auth-Token") String token);
}

