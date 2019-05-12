package andrade.amilcar.instagramsidebar.model;

import com.google.auto.value.AutoValue;

import java.util.Arrays;
import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

@AutoValue
public abstract class SidebarItem {

    @StringRes
    public abstract int stringId();

    @DrawableRes
    public abstract int drawableId();

    private static SidebarItem of(@StringRes int stringId, @DrawableRes int drawableId) {
        return new AutoValue_SidebarItem(stringId, drawableId);
    }

    public static List<SidebarItem> createSidebarItems() {
        return Arrays.asList(
                SidebarItem.of(R.string.saved, R.drawable.ic_bookmark_border_black_24dp),
                SidebarItem.of(R.string.discover_people, R.drawable.ic_person_add_black_24dp),
                SidebarItem.of(R.string.open_facebook, R.drawable.ic_public_black_24dp)
                );
    }
}
