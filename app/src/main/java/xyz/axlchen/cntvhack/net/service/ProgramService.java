package xyz.axlchen.cntvhack.net.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.axlchen.cntvhack.data.entity.DataWrapper;
import xyz.axlchen.cntvhack.data.entity.ProgramCategory;
import xyz.axlchen.cntvhack.data.entity.ProgramInfo;

public interface ProgramService {

    @GET("http://cbox.cntv.cn/json2015/media/mcindex/index.json")
    Call<DataWrapper<ProgramCategory>> getProgramCategory();

    //http://media.app.cntvwb.cn/vapi/media/cmlist.do?chid=EPGC1386744804340103&n=20&p=1
    @GET("vapi/media/cmlist.do")
    Call<DataWrapper<List<ProgramInfo>>> getProgramList(@Query("chid") String channelId, @Query("p") int page, @Query("n") int pageSize);
}
