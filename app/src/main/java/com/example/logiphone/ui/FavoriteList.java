package com.example.logiphone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.adapter.FavoriteListAdapter;
import com.example.logiphone.adapter.FavoriteListPaginate;
import com.example.logiphone.model.Favorite;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FavoriteList extends AppCompatActivity {
    Controller controller = null;
    private ListView listView;
    private FavoriteListAdapter adapter;
    private List<Favorite> favoriteList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isEmpty = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        if (controller == null) {
            controller = new Controller();
        }

        findViewById(R.id.none_favorite_container).setVisibility(View.VISIBLE);
        findViewById(R.id.favorite_container).setVisibility(View.GONE);

        listView = findViewById(R.id.favorite_list);
        adapter = new FavoriteListAdapter(this, favoriteList);
        listView.setAdapter(adapter);

        getFavoriteList(currentPage);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && !isLastPage && (firstVisibleItem + visibleItemCount >= totalItemCount) && totalItemCount > 0) {
                    getFavoriteList(++ currentPage);
                }
            }
        });

        findViewById(R.id.btn_visible_favorite_add).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoriteAddGroup.class);
            startActivity(intent);
        });

        findViewById(R.id.visible_favorite_add_list).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoriteAddGroup.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_logiphone).setOnClickListener(v -> {
            Intent intent = new Intent(this, LogiPhoneList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_logiscope).setOnClickListener(v -> {
            Intent intent = new Intent(this, LogiScopeList.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void getFavoriteList(int page) {
        isLoading = true;
        isEmpty = true;

        new Thread(() -> {
            try {
                String response = controller.getFavoriteList(BaseData.getInstance()._id, page);

                if (!Objects.equals(response, "")) {
                    Log.e("Response", response);
                    Gson gson = new Gson();
                    FavoriteListPaginate paginate = gson.fromJson(response, FavoriteListPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getFavoriteList().isEmpty()) {
                            isLastPage = true;
                        } else {
                            favoriteList.addAll(paginate.getFavoriteList());
                            adapter.notifyDataSetInvalidated();
                        }
                        isLoading = false;
                    });

                    isEmpty = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> isLoading = false);
            }
        }).start();

        if (!isEmpty) {
            findViewById(R.id.favorite_container).setVisibility(View.VISIBLE);
            findViewById(R.id.none_favorite_container).setVisibility(View.GONE);
        }
    }
}
