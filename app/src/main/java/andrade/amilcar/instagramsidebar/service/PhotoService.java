package andrade.amilcar.instagramsidebar.service;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import andrade.amilcar.instagramsidebar.model.PhotoItem;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoService {

    private final PhotoApi api;
    private List<PhotoItem> cache = new ArrayList<>();

    private static final class SingletonHolder {
        static final PhotoService INSTANCE = new PhotoService();
    }

    private PhotoService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(PhotoApi.class);
    }

    public static PhotoService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @NonNull
    public Single<List<PhotoItem>> getPhotosAsync() {
        if (!(cache.isEmpty())) {
            Single.just(cache);
        }

        return api.getPhotos()
                .subscribeOn(Schedulers.io())
                .map(new Function<Response<List<PhotoItem>>, List<PhotoItem>>() {
                    @Override
                    public List<PhotoItem> apply(Response<List<PhotoItem>> response) throws Exception {
                        if (!response.isSuccessful()) {
                            return Collections.emptyList();
                        }

                        final List<PhotoItem> body = response.body();
                        if (body != null) {
                            cache = body.subList(10, 19);
                            return cache;
                        }
                        return Collections.emptyList();
                    }
                });
    }
}
