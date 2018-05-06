package xyz.axlchen.cntvhack.net.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.axlchen.cntvhack.data.entity.ShortVideoList;
import xyz.axlchen.cntvhack.data.entity.TotalShortVideoList;
import xyz.axlchen.cntvhack.data.entity.VideoDetailInfo;

public interface ShortVideoService {

    @GET("video/getCboxMicroVideoListAction?serviceId=cbox")
    Call<TotalShortVideoList> getTotal(@Query("p") int page, @Query("n") int pageSize);

    @GET("video/getCboxMicroVideoListAction?serviceId=cbox")
    Call<ShortVideoList> getShortVideoListByCategory(@Query("id") String categoryId, @Query("p") int page, @Query("n") int pageSize);

    @GET("api/getHttpVideoInfo.do")
    Call<VideoDetailInfo> getVideoInfo(@Query("pid") String videoId);
}