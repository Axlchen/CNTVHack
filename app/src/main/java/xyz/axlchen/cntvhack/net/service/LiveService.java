package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.axlchen.cntvhack.data.entity.ChannelLiveInfo;

public interface LiveService {

    //    http://vdn.live.cntv.cn/api2/live.do?channel=pa://cctv_p2p_hdtiyuip9&client=android&tsp=1526608161&uid=5284047f4ffb4e04824a2fd1d1f0cd62&vc=286F8CE000A8F5EF3118F8834A366F54&vn=3&wlan=w
    @GET("api2/live.do?client=android")
    Call<ChannelLiveInfo> getChannelLiveInfo(@Query(value = "channel",encoded = true) String channel);
}
