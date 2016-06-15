package com.testerhome.android.app.networks;

import com.testerhome.android.app.models.TopicDetailResponse;
import com.testerhome.android.app.models.TopicsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bin Li on 2016/6/14.
 */

public interface TesterHomeService {

    @GET("topics.json")
    Observable<TopicsResponse> getTopicsByType(@Query("type") String type,
                                               @Query("offset") int offset);

    @GET("topics/{id}.json")
    Observable<TopicDetailResponse> getTopicById(@Path("id") String id);
}
