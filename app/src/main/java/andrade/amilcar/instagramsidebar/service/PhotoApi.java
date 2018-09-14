package andrade.amilcar.instagramsidebar.service;

import java.util.List;

import andrade.amilcar.instagramsidebar.model.PhotoItem;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

interface PhotoApi {

    // LATER: Change it to be a Single
    @GET("/list")
    Single<Response<List<PhotoItem>>> getPhotos();
}
