package andrade.amilcar.instagramsidebar.service;

import java.util.List;

import andrade.amilcar.instagramsidebar.model.PhotoItem;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

interface PhotoApi {

    @GET("/list")
    Single<Response<List<PhotoItem>>> getPhotos();
}
