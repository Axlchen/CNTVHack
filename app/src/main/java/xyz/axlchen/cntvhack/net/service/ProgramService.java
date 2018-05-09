package xyz.axlchen.cntvhack.net.service;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProgramService {


    @GET("http://cbox.cntv.cn/json2015/media/mcindex/index.json")
    Call getProgramCategory();
}
