package andrade.amilcar.instagramsidebar.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.SidebarItem;

class SidebarAdapter extends RecyclerView.Adapter<SidebarAdapter.SidebarViewHolder> {
    private final List<SidebarItem> items = SidebarItem.createSidebarItems();

    @Override
    public SidebarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SidebarViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_sidebar, parent, false));
    }

    @Override
    public void onBindViewHolder(SidebarViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SidebarViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        SidebarViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView;
        }

        void bind(SidebarItem i) {
            item.setText(i.stringId());
            // See: https://android-developers.googleblog.com/2013/03/native-rtl-support-in-android-42.html
            item.setCompoundDrawablesRelativeWithIntrinsicBounds(i.drawableId(), 0, 0, 0);
        }
    }
}