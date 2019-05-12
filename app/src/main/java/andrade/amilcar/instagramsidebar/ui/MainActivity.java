package andrade.amilcar.instagramsidebar.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.GridItem;
import andrade.amilcar.instagramsidebar.service.ProfileService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Disposable disposable;
    private SlidingPaneLayout panel;
    private RecyclerView grid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        panel = findViewById(R.id.panel);
        panel.setSliderFadeColor(Color.TRANSPARENT);
        panel.setParallaxDistance(getResources().getDimensionPixelSize(R.dimen.side_bar_width));
        grid = findViewById(R.id.grid);

        setupRecyclerView();
        setSupportActionBar(toolbar);
        final RecyclerView sidebarList = findViewById(R.id.sidebar_list);
        sidebarList.setAdapter(new SidebarAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposable = ProfileService.getInstance()
                .getGridInfoAsync()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GridItem>>() {
                    @Override
                    public void accept(List<GridItem> photoItems) throws Exception {
                        if (grid.getAdapter() == null) {
                            grid.setAdapter(new GridAdapter(photoItems));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposable.dispose();
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

    private void setupRecyclerView() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) grid.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 3 : 1;
            }
        });
        grid.addItemDecoration(new GridMarginDecoration(getResources().getDimensionPixelSize(R.dimen.grid_item_spacing)));
        grid.setHasFixedSize(true);
    }
}
