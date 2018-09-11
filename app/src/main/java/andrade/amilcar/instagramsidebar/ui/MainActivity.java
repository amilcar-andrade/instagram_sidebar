package andrade.amilcar.instagramsidebar.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.SidebarItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView sidebarList = findViewById(R.id.sidebar_list);
        sidebarList.setAdapter(new SidebarAdapter());
    }

    static class SidebarAdapter extends RecyclerView.Adapter<SidebarViewHolder> {
        final List<SidebarItem> items = SidebarItem.createSidebarItems();

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
    }

    static class SidebarViewHolder extends RecyclerView.ViewHolder {
        TextView item;

        SidebarViewHolder(View itemView) {
            super(itemView);
            item = (TextView) itemView;
        }

        void bind(SidebarItem i) {
            item.setText(i.getStringId());
            item.setCompoundDrawablesWithIntrinsicBounds(i.getDrawableId(), 0, 0, 0);
        }
    }
}
