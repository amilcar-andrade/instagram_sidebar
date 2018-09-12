package andrade.amilcar.instagramsidebar.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.model.HeaderItem;
import andrade.amilcar.instagramsidebar.model.PhotoItem;

public class GridAdapter<T extends GridItem> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<T> items;

    GridAdapter(@NonNull List<T> items) {
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

        void bind(@NonNull PhotoItem item) {
            Picasso.get().load(item.getUrl()).into(imageView);
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {

        HeaderHolder(View itemView) {
            super(itemView);
        }

        void bind(@NonNull HeaderItem item) {

        }
    }
}
