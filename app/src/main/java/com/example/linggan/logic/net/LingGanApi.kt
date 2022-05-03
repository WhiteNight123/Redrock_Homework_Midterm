package com.example.linggan.logic.net

import com.example.linggan.logic.model.*
import retrofit2.http.*
import retrofit2.http.Query
import java.util.*

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */

interface LingGanApi {
    @GET("idea/idea")
    suspend fun getIdeaPage(): BaseData<IdeaData>

    @GET("idea/idea_detail")
    suspend fun getIdeaDetail(@Query("idea_detail_id") id: Int): BaseData<IdeaDetailData>

    @GET("color/page")
    suspend fun getSpectrumPage(): BaseData<SpectrumData>

    @GET("color/color_list")
    suspend fun getSpectrumList(
        @Query("theme_id") id: Int,
        @Query("page") page: Int
    ): BaseData<SpectrumListData>

    @GET("color/color_detail")
    suspend fun getSpectrumDetail(@Query("color_detail_id") id: Int): BaseData<SpectrumDetailData>

    @POST("user/register")
    suspend fun register(@Body data: RegisterBody): BaseData<String>

    @FormUrlEncoded
    @POST("user/long_login")
    suspend fun login(@Field("phone_number") phone: String): BaseData<LoginData>

    @POST("star/star")
    suspend fun star(@Header("Authorization") token: String, @Body data: StarBody): BaseData<String>

    @FormUrlEncoded
    @POST("star/delete_star")
    suspend fun unStar(
        @Header("Authorization") token: String,
        @Field("star_id") id: Int
    ): BaseData<Objects>

    @GET("star/star_list")
    suspend fun getStarList(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
    ): BaseData<StarData>
}