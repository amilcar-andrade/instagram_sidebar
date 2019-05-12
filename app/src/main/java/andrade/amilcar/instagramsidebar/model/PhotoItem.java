package andrade.amilcar.instagramsidebar.model;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

import andrade.amilcar.instagramsidebar.gson.AutoGson;

@AutoValue @AutoGson
public abstract class PhotoItem implements GridItem {

    @SerializedName("id")
    public abstract int id();

    static PhotoItem of(int id) {
        return new AutoValue_PhotoItem(id);
    }

    public String url() {
        return "https://picsum.photos/300/300?image=" + id();
    }
}
