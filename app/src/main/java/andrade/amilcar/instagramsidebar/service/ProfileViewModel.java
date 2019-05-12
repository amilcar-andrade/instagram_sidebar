package andrade.amilcar.instagramsidebar.service;

import java.util.Collections;
import java.util.List;

import andrade.amilcar.instagramsidebar.model.GridItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;

public class ProfileViewModel extends ViewModel {

    private final ProfileService profileService;

    @Nullable
    private List<GridItem> cache;

    private ProfileViewModel(ProfileService service) {
        this.profileService = service;
    }

    @NonNull
    public Single<List<GridItem>> getProfileInfo() {
        if (cache != null && (!cache.isEmpty())) {
            return Single.just(cache);
        }

        return profileService.getHeaderAndPhotosAsync()
                .doOnSuccess(new Consumer<List<GridItem>>() {
                    @Override
                    public void accept(List<GridItem> gridItems) {
                        cache = Collections.unmodifiableList(gridItems);
                    }
                });
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
