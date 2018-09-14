package andrade.amilcar.instagramsidebar.model;

import android.support.annotation.NonNull;

public class PhotoItem implements GridItem {

    private final int id;

    PhotoItem(int id) {
        this.id = id;
    }

    @NonNull
    public String getUrl() {
        return "https://picsum.photos/300/300?image=" + id;
    }
}
