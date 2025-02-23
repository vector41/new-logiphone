package com.example.logiphone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.adapter.FavoriteListAdapter;
import com.example.logiphone.adapter.FavoriteListPaginate;
import com.example.logiphone.alert.ErrorDialog;
import com.example.logiphone.model.Favorite;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteList extends AppCompatActivity {
    private Controller controller;
    private ListView listView;
    private FavoriteListAdapter adapter;
    private final List<Favorite> favoriteList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int scrollPos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_list);

        findViewById(R.id.none_favorite_container).setVisibility(View.VISIBLE);
        findViewById(R.id.favorite_container).setVisibility(View.INVISIBLE);

        listView = findViewById(R.id.favorite_list);
        adapter = new FavoriteListAdapter(this, favoriteList);
        listView.setAdapter(adapter);

        controller = new Controller();
        loadFavoriteList(currentPage);

        BaseData.getInstance()._id = BaseData.getInstance().getAuthUserId(this);
        BaseData.getInstance()._searchKeyword = "";

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && !isLastPage && view.getVerticalScrollbarPosition() == totalItemCount - 1 && totalItemCount > 1) {
                    loadFavoriteList(++ currentPage);
                    scrollPos = scrollPos + 30;
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

        findViewById(R.id.btn_switch_logiscope_new).setOnClickListener(v -> {
            Intent intent = new Intent(this, NewLogiScopeList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_logiscope_old).setOnClickListener(v -> {
            Intent intent = new Intent(this, OldLogiScopeList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            BaseData.getInstance().clearAuthData(this);

            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        findViewById(R.id.favorite_search_input).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EditText keywordField = (EditText) findViewById(R.id.favorite_search_input);
                    BaseData.getInstance()._searchKeyword = keywordField.getText().toString();

                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    currentPage = 1;
                    loadFavoriteList(currentPage);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadFavoriteList(int page) {
        isLoading = true;

        new Thread(() -> {
            try {
                String response = controller.getFavoritesList(page);
                try {
                    Gson gson = new Gson();
                    FavoriteListPaginate paginate = gson.fromJson(response, FavoriteListPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                            findViewById(R.id.favorite_container).setVisibility(View.INVISIBLE);
                            findViewById(R.id.none_favorite_container).setVisibility(View.VISIBLE);
                        } else {
                            favoriteList.addAll(paginate.getData());
                            adapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition(scrollPos);
                            findViewById(R.id.favorite_container).setVisibility(View.VISIBLE);
                            findViewById(R.id.none_favorite_container).setVisibility(View.INVISIBLE);
                        }
                        isLoading = false;
                    });
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        ErrorDialog errorDialog = new ErrorDialog(this, getString(R.string.message_get_data_error));
                        errorDialog.show();
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> isLoading = false);
            }
        }).start();
    }
}
