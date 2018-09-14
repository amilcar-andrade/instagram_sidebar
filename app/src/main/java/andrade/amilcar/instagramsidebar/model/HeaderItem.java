package andrade.amilcar.instagramsidebar.model;

public class HeaderItem implements GridItem {

    private final PhotoItem photo;
    private final String name;
    private final int posts;
    private final int followers;
    private final int following;

    public HeaderItem(String name, PhotoItem photo, int posts, int followers, int following) {
        this.name = name;
        this.photo = photo;
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

    public int getPosts() {
        return posts;
    }

    public int getFollowers() {
        return followers;
    }

    public int getFollowing() {
        return following;
    }
}
