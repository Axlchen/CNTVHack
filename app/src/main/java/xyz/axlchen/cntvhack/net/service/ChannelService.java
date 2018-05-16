package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import xyz.axlchen.cntvhack.data.entity.ChannelCategory;
import xyz.axlchen.cntvhack.data.entity.ChannelList;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;

public interface ChannelService {

    @GET("http://cbox.cntv.cn/json2015/zhiboshy/zbsyhd/index.json")
    Call<DataWrapper<ChannelCategory>> getChannelCategory();

    @GET
    Call<DataWrapper<ChannelList>> getChannelList(@Url String url);
}


