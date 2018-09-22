package com.example.root.gogetitserver.remote;

import com.example.root.gogetitserver.model.MyResponse;
import com.example.root.gogetitserver.model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAArEGYwzU:APA91bGTBsfOciavfzLe4z9kr9vVdCOO1YqRNjeQOZ1Rt1HZVj5YLDM9s7FGYV66ta4JeWEg5ULpNW_ONBu5MsNlqAWBCmXH6i3ffh86eE-s31FpnnfUh98ie4D7NGqVja7d4l7YB-wiqcbWi7pVtVbOLKQ4kYuwdg"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
