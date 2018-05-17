package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.axlchen.cntvhack.data.entity.NowEpgInfo;

public interface EpgService {

    @GET("epg/nowepg?serviceId=cbox")
    Call<NowEpgInfo> getNowEpg(@Query("c") String channel);
}
