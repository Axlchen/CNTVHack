package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ConfigService {

    @GET("http://cbox.cntv.cn/json2015/other/mobileconfig/index.json")
    Call<String> getUrlConfig();
}
