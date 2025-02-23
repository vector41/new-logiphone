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
import com.example.logiphone.adapter.FavoriteAddListAdapter;
import com.example.logiphone.adapter.FavoriteAddListPaginate;
import com.example.logiphone.alert.ErrorDialog;
import com.example.logiphone.model.Favorite;
import com.example.logiphone.model.FavoriteAdd;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteAddGroup extends AppCompatActivity {
    private Controller controller;
    private ListView listView;
    private FavoriteAddListAdapter adapter;
    private List<FavoriteAdd> favoriteAddList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_add_group);

        listView = findViewById(R.id.favorite_add_list);
        adapter = new FavoriteAddListAdapter(this, favoriteAddList);
        listView.setAdapter(adapter);

        controller = new Controller();
        loadFavoriteAddList(currentPage);

        BaseData.getInstance()._searchKeyword = "";

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && !isLastPage && (firstVisibleItem + visibleItemCount == totalItemCount) && totalItemCount > 0) {
                    loadFavoriteAddList(++ currentPage);
                }
            }
        });

        findViewById(R.id.btn_arrow_back).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoriteList.class);
            startActivity(intent);
        });

        findViewById(R.id.favorite_add_search_input).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EditText keywordField = (EditText) findViewById(R.id.favorite_search_input);
                    BaseData.getInstance()._searchKeyword = keywordField.getText().toString();

                    adapter.clear();
                    adapter.notifyDataSetInvalidated();
                    currentPage = 1;
                    loadFavoriteAddList(currentPage);
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadFavoriteAddList(int page) {
        isLoading = true;

        new Thread(() -> {
            try {
                String response = controller.getFavoriteAddList(page);
                try {
                    Gson gson = new Gson();
                    FavoriteAddListPaginate paginate = gson.fromJson(response, FavoriteAddListPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                        } else {
                            favoriteAddList.addAll(paginate.getData());
                            adapter.notifyDataSetInvalidated();
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
            }
        }).start();
    }
}
