package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.axlchen.cntvhack.data.entity.ShortVideoDetailInfo;
import xyz.axlchen.cntvhack.data.entity.ShortVideoList;
import xyz.axlchen.cntvhack.data.entity.TotalShortVideoList;

public interface ShortVideoService {

    @GET("video/getCboxMicroVideoListAction?serviceId=cbox")
    Call<TotalShortVideoList> getTotal(@Query("p") int page, @Query("n") int pageSize);

    @GET("video/getCboxMicroVideoListAction?serviceId=cbox")
    Call<ShortVideoList> getShortVideoListByCategory(@Query("id") String categoryId, @Query("p") int page, @Query("n") int pageSize);

    @GET("api/getHttpVideoInfo.do")
    Call<ShortVideoDetailInfo> getVideoInfo(@Query("pid") String videoId);
}