package andrade.amilcar.instagramsidebar.model;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class HeaderItem implements GridItem {

    @NonNull
    public abstract String name();
    public abstract int posts();
    public abstract int followers();
    public abstract int following();
    @NonNull
    public abstract PhotoItem photo();

    public static HeaderItem of(@NonNull String name, int posts, int followers, int following) {
        return new AutoValue_HeaderItem(name, posts, followers, following, PhotoItem.of(338));
    }
}
