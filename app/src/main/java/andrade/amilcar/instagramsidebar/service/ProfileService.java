package andrade.amilcar.instagramsidebar.service;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.model.HeaderItem;
import andrade.amilcar.instagramsidebar.model.PhotoItem;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileService {

    private final PhotoApi api;
    private List<GridItem> cache = new ArrayList<>();

    private static final class SingletonHolder {
        static final ProfileService INSTANCE = new ProfileService();
    }

    private ProfileService() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(PhotoApi.class);
    }

    public static ProfileService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @NonNull
    public Single<List<GridItem>> getGridInfoAsync() {
        if (!(cache.isEmpty())) {
            return Single.just(cache);
        }

        return getHeaderItem()
                .zipWith(getPhotos(), new BiFunction<HeaderItem, List<PhotoItem>, List<GridItem>>() {
            @Override
            public List<GridItem> apply(HeaderItem header, List<PhotoItem> photos) throws Exception {
                List<GridItem> items = new ArrayList<>();
                items.add(header);
                items.addAll(photos.subList(9, 27));
                cache = items;
                return items;
            }
        }).subscribeOn(Schedulers.io());
    }

    private Single<HeaderItem> getHeaderItem() {
        HeaderItem item = new HeaderItem(null, null, 0, 0, 0);
        return Single.just(item);
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
