package andrade.amilcar.instagramsidebar.model;

import android.support.annotation.NonNull;

public class PhotoItem implements GridItem {

    private int id;

    private String author;

    @NonNull
    public String getUrl() {
        return "https://picsum.photos/400/400?image=" + id;
    }
}
