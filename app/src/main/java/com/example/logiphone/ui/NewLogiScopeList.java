package com.example.logiphone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.logiphone.BaseData;
import com.example.logiphone.Controller;
import com.example.logiphone.R;
import com.example.logiphone.adapter.NewLogiCompanyAdapter;
import com.example.logiphone.adapter.NewLogiCompanyPaginate;
import com.example.logiphone.adapter.NewLogiScopeListAdapter;
import com.example.logiphone.adapter.NewLogiScopeListPaginate;
import com.example.logiphone.alert.ErrorDialog;
import com.example.logiphone.model.NewLogiCompany;
import com.example.logiphone.model.NewLogiScope;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NewLogiScopeList extends AppCompatActivity {
    private Controller controller;
    private ListView listView;
    private NewLogiScopeListAdapter userAdapter;
    private NewLogiCompanyAdapter companyAdapter;
    private final List<NewLogiScope> newLogiScopeUserList = new ArrayList<>();
    private final List<NewLogiCompany> newLogiScopeCompanyList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isOrderSelected = false;
    private boolean isCompanySelected = false;
    private int scrollPos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_logiscope_list);

        listView = findViewById(R.id.new_logiscope_list);
        listView.setAdapter(null);
        userAdapter = new NewLogiScopeListAdapter(this, newLogiScopeUserList);
        companyAdapter = new NewLogiCompanyAdapter(this, newLogiScopeCompanyList);

        controller = new Controller();
        loadLogiScopeUserList(currentPage);

        BaseData.getInstance()._sort = "normal";
        BaseData.getInstance()._searchKeyword = "";

        if (BaseData.getInstance()._isSelectedNewFilter) {
            findViewById(R.id.new_logiscope_search_setting).setVisibility(View.VISIBLE);
        }

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && !isLastPage && (firstVisibleItem + visibleItemCount == totalItemCount) && totalItemCount > 1) {
                    if (isCompanySelected) {
                        userAdapter.clear();
                        companyAdapter.notifyDataSetInvalidated();
                        loadLogiScopeCompanyList(++ currentPage);
                    } else {
                        companyAdapter.clear();
                        userAdapter.notifyDataSetInvalidated();
                        loadLogiScopeUserList(++ currentPage);
                    }

                    scrollPos = scrollPos + 30;
                }
            }
        });

        findViewById(R.id.btn_switch_add_new).setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfileCreate.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_favorites).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoriteList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_old_logiscope).setOnClickListener(v -> {
            Intent intent = new Intent(this, OldLogiScopeList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_new_logi_collapse).setOnClickListener(v -> {
            LinearLayout searchSetting = findViewById(R.id.new_logiscope_search_setting);

            if (searchSetting.getVisibility() == View.VISIBLE) {
                searchSetting.setVisibility(View.GONE);
                BaseData.getInstance()._isSelectedNewFilter = false;
            } else {
                searchSetting.setVisibility(View.VISIBLE);
                BaseData.getInstance()._isSelectedNewFilter = true;
            }
        });

        findViewById(R.id.search_target_user).setOnClickListener(v -> {
            findViewById(R.id.search_target_user).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_target_company).setBackgroundResource(R.drawable.btn_search_setting_disable);

            findViewById(R.id.btn_switch_add_new).setVisibility(View.VISIBLE);

            currentPage = 1;
            listView.setAdapter(null);
            isCompanySelected = false;
            loadLogiScopeUserList(currentPage);
        });

        findViewById(R.id.search_target_company).setOnClickListener(v -> {
            findViewById(R.id.search_target_company).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_target_user).setBackgroundResource(R.drawable.btn_search_setting_disable);

            findViewById(R.id.btn_switch_add_new).setVisibility(View.GONE);

            currentPage = 1;
            listView.setAdapter(null);
            isCompanySelected = true;
            loadLogiScopeCompanyList(currentPage);
        });

        findViewById(R.id.search_method_normal).setOnClickListener(v -> {
            isOrderSelected = false;
            BaseData.getInstance()._sort = "normal";

            currentPage = 1;
            listView.setAdapter(null);
            userAdapter.clear();
            companyAdapter.clear();

            if (isCompanySelected) {
                companyAdapter.notifyDataSetInvalidated();
                loadLogiScopeCompanyList(currentPage);
            } else {
                userAdapter.notifyDataSetInvalidated();
                loadLogiScopeUserList(currentPage);
            }

            findViewById(R.id.search_method_normal).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_method_order).setBackgroundResource(R.drawable.btn_search_setting_disable);
        });

        findViewById(R.id.search_method_order).setOnClickListener(v -> {
            isOrderSelected = true;
            BaseData.getInstance()._sort = "kana";

            currentPage = 1;
            listView.setAdapter(null);
            userAdapter.clear();
            companyAdapter.clear();

            if (isCompanySelected) {
                companyAdapter.notifyDataSetInvalidated();
                loadLogiScopeCompanyList(currentPage);
            } else {
                userAdapter.notifyDataSetInvalidated();
                loadLogiScopeUserList(currentPage);
            }

            findViewById(R.id.search_method_order).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_method_normal).setBackgroundResource(R.drawable.btn_search_setting_disable);
        });

        findViewById(R.id.btn_logout).setOnClickListener(v -> {
            BaseData.getInstance().clearAuthData(this);

            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        findViewById(R.id.new_logiscope_search_input).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EditText keywordField = (EditText) findViewById(R.id.new_logiscope_search_input);
                    BaseData.getInstance()._searchKeyword = keywordField.getText().toString();

                    currentPage = 1;
                    userAdapter.clear();
                    companyAdapter.clear();
                    if (isCompanySelected) {
                        companyAdapter.notifyDataSetInvalidated();
                        loadLogiScopeCompanyList(currentPage);
                    } else {
                        userAdapter.notifyDataSetInvalidated();
                        loadLogiScopeUserList(currentPage);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadLogiScopeUserList(int page) {
        isLoading = true;
        listView.setAdapter(userAdapter);

        new Thread(() -> {
            try {
                String response = controller.getNewLogiScopeUserList(page);
                try {
                    Gson gson = new Gson();
                    NewLogiScopeListPaginate paginate = gson.fromJson(response, NewLogiScopeListPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                        } else {
                            newLogiScopeUserList.addAll(paginate.getData());
                            userAdapter.notifyDataSetInvalidated();
                            listView.smoothScrollToPosition(scrollPos);
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

    private void loadLogiScopeCompanyList(int page) {
        isLoading = true;
        listView.setAdapter(companyAdapter);
        Log.e("scroll", String.valueOf(scrollPos));

        new Thread(() -> {
            try {
                String response = controller.getNewLogiScopeCompanyList(page);
                try {
                    Gson gson = new Gson();
                    NewLogiCompanyPaginate paginate = gson.fromJson(response, NewLogiCompanyPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                        } else {
                            newLogiScopeCompanyList.addAll(paginate.getData());
                            companyAdapter.notifyDataSetInvalidated();
                        }
                        isLoading = false;
                        listView.smoothScrollToPosition(scrollPos);
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
