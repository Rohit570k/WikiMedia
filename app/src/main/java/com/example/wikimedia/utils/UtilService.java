package com.example.wikimedia.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.example.wikimedia.data.models.APIError;
import com.example.wikimedia.data.webservice.RetrofitClient;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class UtilService {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter =
                RetrofitClient.retrofit()
                        .responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    public static  void openUrlInBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
