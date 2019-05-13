package andrade.amilcar.instagramsidebar.service;

import java.util.Collections;
import java.util.List;

import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.model.ProfileSate;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class ProfileViewModel extends ViewModel {

    private final ProfileService profileService;

    @Nullable
    private List<GridItem> cache;

    private ProfileViewModel(ProfileService service) {
        this.profileService = service;
    }

    @NonNull
    public Observable<ProfileSate> getProfileInfo() {
        if (cache != null && (!cache.isEmpty())) {
            return Observable.just(ProfileSate.loaded(cache));
        }

        return profileService.getHeaderAndPhotosAsync()
                .doOnSuccess(new Consumer<List<GridItem>>() {
                    @Override
                    public void accept(List<GridItem> gridItems) {
                        cache = Collections.unmodifiableList(gridItems);
                    }
                }).flatMapObservable(new Function<List<GridItem>, ObservableSource<ProfileSate>>() {
                    @Override
                    public ObservableSource<ProfileSate> apply(List<GridItem> gridItems) {
                        return Observable.just(ProfileSate.loaded(gridItems));
                    }
                }).onErrorReturn(new Function<Throwable, ProfileSate>() {
                    @Override
                    public ProfileSate apply(Throwable throwable) {
                        return ProfileSate.error(throwable);
                    }
                }).startWith(ProfileSate.loading());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final ProfileService service;

        public Factory(@NonNull ProfileService service) {
            this.service = service;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ProfileViewModel(service);
        }
    }
}
