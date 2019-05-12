package andrade.amilcar.instagramsidebar.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import andrade.amilcar.instagramsidebar.gson.AutoValueAdapterFactory;
import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.model.HeaderItem;
import andrade.amilcar.instagramsidebar.model.PhotoItem;
import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ProfileService {

    private final PhotoApi api;

    private static final class SingletonHolder {
        static final ProfileService INSTANCE = new ProfileService();
    }

    private ProfileService() {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new AutoValueAdapterFactory())
                .create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(PhotoApi.class);
    }

    public static ProfileService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @NonNull
    Single<List<GridItem>> getHeaderAndPhotosAsync() {
        return getHeaderItem()
                .zipWith(getPhotos(), new BiFunction<HeaderItem, List<PhotoItem>, List<GridItem>>() {
            @Override
            public List<GridItem> apply(HeaderItem header, List<PhotoItem> photos) throws Exception {
                List<GridItem> items = new ArrayList<>();
                items.add(header);
                items.addAll(photos.subList(10, 28));
                return items;
            }
        }).subscribeOn(Schedulers.io());
    }

    private Single<HeaderItem> getHeaderItem() {
        return Single.just(HeaderItem.of("Amilcar Andrade", 27, 106, 173));
    }

    private Single<List<PhotoItem>> getPhotos() {
        return  api.getPhotos()
                .map(new Function<Response<List<PhotoItem>>, List<PhotoItem>>() {
                    @Override
                    public List<PhotoItem> apply(Response<List<PhotoItem>> response) throws Exception {
                        if (!response.isSuccessful()) {
                            return Collections.emptyList();
                        }

                        final List<PhotoItem> body = response.body();
                        if (body != null) {
                            return body;
                        }
                        return Collections.emptyList();
                    }
                });
    }

}
