package andrade.amilcar.instagramsidebar.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.PhotoItem;
import andrade.amilcar.instagramsidebar.service.PhotoService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private SlidingPaneLayout panel;
    private RecyclerView grid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        panel = findViewById(R.id.panel);
        panel.setSliderFadeColor(Color.TRANSPARENT);
        grid = findViewById(R.id.grid);
        grid.addItemDecoration(new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));

        setSupportActionBar(toolbar);
        final RecyclerView sidebarList = findViewById(R.id.sidebar_list);
        sidebarList.setAdapter(new SidebarAdapter());

        PhotoService.getInstance().getPhotosAsync()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PhotoItem>>() {
            @Override
            public void accept(List<PhotoItem> photoItems) throws Exception {
                grid.setAdapter(new GridAdapter(photoItems));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_hamburger:
                if (panel.isOpen()) {
                    panel.closePane();
                } else {
                    panel.openPane();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
