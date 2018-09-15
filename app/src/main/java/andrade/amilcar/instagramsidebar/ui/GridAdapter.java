package andrade.amilcar.instagramsidebar.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.model.HeaderItem;
import andrade.amilcar.instagramsidebar.model.PhotoItem;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<GridItem> items;

    GridAdapter(@NonNull List<GridItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case R.layout.list_item_header:
                return new HeaderHolder(inflater.inflate(viewType, parent, false));
            case R.layout.list_item_photo:
                return new PhotoHolder(inflater.inflate(viewType, parent, false));
        }
        throw new UnsupportedOperationException("Unknown view type");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final GridItem item = items.get(position);
        if (item instanceof HeaderItem && holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).bind((HeaderItem) item);
        } else if (item instanceof PhotoItem && holder instanceof PhotoHolder) {
            ((PhotoHolder) holder).bind((PhotoItem) item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof PhotoItem ? R.layout.list_item_photo : R.layout.list_item_header;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        PhotoHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }

        void bind(final @NonNull PhotoItem item) {
            Picasso.get().load(item.getUrl()).into(imageView);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView followers;
        private TextView posts;
        private TextView following;
        private ImageView imageView;

        HeaderHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            followers = itemView.findViewById(R.id.followers);
            following = itemView.findViewById(R.id.following);
            posts = itemView.findViewById(R.id.posts);
            imageView = itemView.findViewById(R.id.image);
        }

        void bind(@NonNull final HeaderItem item) {
            // https://stackoverflow.com/a/37756752/1768722
            Picasso.get()
                    .load(item.getPhoto().getUrl())
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(imageView.getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            imageView.setImageDrawable(imageDrawable);
                        }

                        @Override
                        public void onError(Exception e) {
                            // no-op
                        }
                    });
            name.setText(item.getName());
            followers.setText(item.getFollowers());
            posts.setText(item.getPosts());
            following.setText(item.getFollowing());
        }
    }
}
