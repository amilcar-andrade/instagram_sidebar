package andrade.amilcar.instagramsidebar.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.List;

import andrade.amilcar.instagramsidebar.R;

public final class SidebarItem {

    @StringRes
    private final int stringId;

    @DrawableRes
    private final int drawableId;

    private SidebarItem(@StringRes int stringId, @DrawableRes int drawableId) {
        this.stringId = stringId;
        this.drawableId = drawableId;
    }

    public static List<SidebarItem> createSidebarItems() {
        return Arrays.asList(
                new SidebarItem(R.string.saved, R.drawable.ic_bookmark_border_black_24dp),
                new SidebarItem(R.string.discover_people, R.drawable.ic_person_add_black_24dp),
                new SidebarItem(R.string.open_facebook, R.drawable.ic_public_black_24dp)
                );
    }

    @StringRes
    public int getStringId() {
        return stringId;
    }

    @DrawableRes
    public int getDrawableId() {
        return drawableId;
    }
}
