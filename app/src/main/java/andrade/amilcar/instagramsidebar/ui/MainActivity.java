package andrade.amilcar.instagramsidebar.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import javax.annotation.Nonnull;

import andrade.amilcar.instagramsidebar.R;
import andrade.amilcar.instagramsidebar.model.ProfileSate;
import andrade.amilcar.instagramsidebar.service.ProfileService;
import andrade.amilcar.instagramsidebar.service.ProfileViewModel;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Disposable disposable;
    private SlidingPaneLayout panel;
    private RecyclerView grid;
    private ProfileViewModel profileViewModel;
    private View progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        panel = findViewById(R.id.panel);
        progressBar = findViewById(android.R.id.progress);
        panel.setSliderFadeColor(Color.TRANSPARENT);
        panel.setParallaxDistance(getResources().getDimensionPixelSize(R.dimen.side_bar_width));
        grid = findViewById(R.id.grid);

        setupRecyclerView();
        setSupportActionBar(toolbar);
        final RecyclerView sidebarList = findViewById(R.id.sidebar_list);
        sidebarList.setAdapter(new SidebarAdapter());

        // View model
        ProfileViewModel.Factory factory = new ProfileViewModel.Factory(ProfileService.getInstance());
        profileViewModel = new ViewModelProvider(this, factory).get(ProfileViewModel.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposable = profileViewModel.getProfileInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProfileSate>() {
                    @Override
                    public void accept(ProfileSate profileSate) throws Exception {
                        profileSate.match(new com.spotify.dataenum.function.Consumer<ProfileSate.Loading>() {
                            @Override
                            public void accept(@Nonnull ProfileSate.Loading value) {
                                progressBar.setVisibility(View.VISIBLE);
                                panel.setVisibility(View.GONE);
                            }
                        }, new com.spotify.dataenum.function.Consumer<ProfileSate.Loaded>() {
                            @Override
                            public void accept(@Nonnull ProfileSate.Loaded value) {
                                progressBar.setVisibility(View.GONE);
                                panel.setVisibility(View.VISIBLE);
                                if (grid.getAdapter() == null) {
                                    grid.setAdapter(new GridAdapter(value.items()));
                                }
                            }
                        }, new com.spotify.dataenum.function.Consumer<ProfileSate.Error>() {
                            @Override
                            public void accept(@Nonnull ProfileSate.Error value) {
                                Toast.makeText(MainActivity.this, value.e().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
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
        if (disposable != null) {
            disposable.dispose();
        }
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
