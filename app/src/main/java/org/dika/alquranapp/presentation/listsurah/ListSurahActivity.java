package org.dika.alquranapp.presentation.listsurah;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import org.dika.alquranapp.R;
import org.dika.alquranapp.base.BaseActivity;
import org.dika.alquranapp.model.Surah;
import org.dika.alquranapp.presentation.listayat.ListAyatActivity;

import java.util.ArrayList;

import butterknife.BindView;


public class ListSurahActivity extends BaseActivity<ListSurahPresenter> implements ListSurahView, ListSurahAdapter.OnSurahItemClick {

    @BindView(R.id.listSurah)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainDrawer)
    DrawerLayout mainDrawer;

    @BindView(R.id.mainNavigation)
    NavigationView mainNavigation;

    private ListSurahAdapter listSurahAdapter;
    private String loadTerjemahan = LOAD_INDONESIA;

    @Override
    public ListSurahPresenter initPresenter() {
        return new ListSurahPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_surah);

        toolbar.setTitle(R.string.app_name);


        listSurahAdapter = new ListSurahAdapter(new SurahDiffCallback(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(listSurahAdapter);

        mPresenter.loadSurah(loadTerjemahan);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mainDrawer, toolbar, R.string.app_name, R.string.app_name);
        mainDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mainNavigation.getLayoutParams().width = Resources.getSystem().getDisplayMetrics().widthPixels / 2;
        mainNavigation.requestLayout();
        mainNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_indonesia:
                        loadTerjemahan = LOAD_INDONESIA;
                        mPresenter.loadSurah(loadTerjemahan);
                        break;
                    case R.id.menu_inggris:
                        loadTerjemahan = LOAD_ENGLISH;
                        mPresenter.loadSurah(loadTerjemahan);
                        break;
                    case R.id.about:
                        AlertDialog.Builder dialog = new AlertDialog.Builder(ListSurahActivity.this, R.style.CustomAlert);
                        dialog.setMessage("Dev by andika.mar");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();
                }

                mainDrawer.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onLoad(ArrayList<Surah> data) {
        listSurahAdapter.submitList(data);
    }

    @Override
    public void onCLick(Surah surah) {
        Intent ayat = new Intent(ListSurahActivity.this, ListAyatActivity.class);
        ayat.putExtra(ListAyatActivity.KEY_AYAT, surah.getAyat());
        ayat.putExtra(ListAyatActivity.KEY_SURAH, surah.getSurah());
        ayat.putExtra(ListAyatActivity.KEY_TERJEMAHAN, surah.getTerjemahan());
        ayat.putExtra(ListAyatActivity.KEY_LOAD_TERJEMAHAN, loadTerjemahan);
        startActivity(ayat);
    }

}
