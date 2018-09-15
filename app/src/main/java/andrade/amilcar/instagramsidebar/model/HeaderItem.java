package andrade.amilcar.instagramsidebar.model;

import android.support.annotation.NonNull;

public class HeaderItem implements GridItem {

    private final PhotoItem photo;
    private final String name;
    private final int posts;
    private final int followers;
    private final int following;

    public HeaderItem(@NonNull String name, int posts, int followers, int following) {
        this.name = name;
        this.photo = new PhotoItem(338);
        this.posts = posts;
        this.followers = followers;
        this.following = following;
    }

    public PhotoItem getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    public String getPosts() {
        return String.valueOf(posts);
    }

    public String getFollowers() {
        return String.valueOf(followers);
    }

    public String getFollowing() {
        return String.valueOf(following);
    }
}
