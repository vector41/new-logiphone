package com.example.logiphone.ui;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.logiphone.adapter.OldLogiCompanyAdapter;
import com.example.logiphone.adapter.OldLogiCompanyPaginate;
import com.example.logiphone.adapter.OldLogiScopeListAdapter;
import com.example.logiphone.adapter.OldLogiScopeListPaginate;
import com.example.logiphone.alert.ErrorDialog;
import com.example.logiphone.model.OldLogiCompany;
import com.example.logiphone.model.OldLogiScope;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OldLogiScopeList extends AppCompatActivity {
    private Controller controller;
    private ListView listView;
    private OldLogiScopeListAdapter userAdapter;
    private OldLogiCompanyAdapter companyAdapter;
    private final List<OldLogiScope> oldLogiScopeUserList = new ArrayList<>();
    private final List<OldLogiCompany> oldLogiScopeCompanyList = new ArrayList<>();
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isOrderSelected = false;
    private boolean isCompanySelected = false;
    private int scrollPos = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_logiscope_list);

        listView = findViewById(R.id.old_logiscope_list);
        listView.setAdapter(null);
        userAdapter = new OldLogiScopeListAdapter(this, oldLogiScopeUserList);
        companyAdapter = new OldLogiCompanyAdapter(this, oldLogiScopeCompanyList);

        controller = new Controller();
        loadOldLogiscopeUserList(currentPage);

        BaseData.getInstance()._sort = "normal";
        BaseData.getInstance()._searchKeyword = "";

        if (BaseData.getInstance()._isSelectedOldFilter) {
            findViewById(R.id.old_logiscope_search_setting).setVisibility(View.VISIBLE);
        }

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) { }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (!isLoading && !isLastPage && (firstVisibleItem + visibleItemCount == totalItemCount) && totalItemCount > 1) {
                    loadOldLogiscopeUserList(++ currentPage);
                    if (isCompanySelected) {
                        userAdapter.clear();
                        companyAdapter.notifyDataSetInvalidated();
                        loadOldLogiscopeCompanyList(++ currentPage);
                    } else {
                        companyAdapter.clear();
                        userAdapter.notifyDataSetInvalidated();
                        loadOldLogiscopeUserList(++ currentPage);
                    }

                    scrollPos = scrollPos + 30;
                }
            }
        });

        findViewById(R.id.btn_switch_favorites).setOnClickListener(v -> {
            Intent intent = new Intent(this, FavoriteList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_switch_new_logiscope).setOnClickListener(v -> {
            Intent intent = new Intent(this, NewLogiScopeList.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_old_logi_collapse).setOnClickListener(v -> {
            LinearLayout searchSetting = findViewById(R.id.old_logiscope_search_setting);

            if (searchSetting.getVisibility() == View.VISIBLE) {
                searchSetting.setVisibility(View.GONE);
                BaseData.getInstance()._isSelectedOldFilter = false;
            } else {
                searchSetting.setVisibility(View.VISIBLE);
                BaseData.getInstance()._isSelectedOldFilter = true;
            }
        });

        findViewById(R.id.search_target_user).setOnClickListener(v -> {
            findViewById(R.id.search_target_user).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_target_company).setBackgroundResource(R.drawable.btn_search_setting_disable);

            currentPage = 1;
            listView.setAdapter(null);
            isCompanySelected = false;
            loadOldLogiscopeUserList(currentPage);
        });

        findViewById(R.id.search_target_company).setOnClickListener(v -> {
            findViewById(R.id.search_target_company).setBackgroundResource(R.drawable.btn_search_setting_active);
            findViewById(R.id.search_target_user).setBackgroundResource(R.drawable.btn_search_setting_disable);

            currentPage = 1;
            listView.setAdapter(null);
            isCompanySelected = true;
            loadOldLogiscopeCompanyList(currentPage);
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
                loadOldLogiscopeCompanyList(currentPage);
            } else {
                userAdapter.notifyDataSetInvalidated();
                loadOldLogiscopeUserList(currentPage);
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
                loadOldLogiscopeCompanyList(currentPage);
            } else {
                userAdapter.notifyDataSetInvalidated();
                loadOldLogiscopeUserList(currentPage);
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

        findViewById(R.id.old_logiscope_search_input).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EditText keywordField = (EditText) findViewById(R.id.old_logiscope_search_input);
                    BaseData.getInstance()._searchKeyword = keywordField.getText().toString();

                    currentPage = 1;
                    userAdapter.clear();
                    companyAdapter.clear();
                    if (isCompanySelected) {
                        companyAdapter.notifyDataSetInvalidated();
                        loadOldLogiscopeCompanyList(currentPage);
                    } else {
                        userAdapter.notifyDataSetInvalidated();
                        loadOldLogiscopeUserList(currentPage);
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

    private void loadOldLogiscopeUserList(int page) {
        isLoading = true;
        listView.setAdapter(userAdapter);

        new Thread(() -> {
            try {
                String response = controller.getOldLogiScopeUserList(page);
                try {
                    Gson gson = new Gson();
                    OldLogiScopeListPaginate paginate = gson.fromJson(response, OldLogiScopeListPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                        } else {
                            oldLogiScopeUserList.addAll(paginate.getData());
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

    private void loadOldLogiscopeCompanyList(int page) {
        isLoading = true;
        listView.setAdapter(companyAdapter);

        new Thread(() -> {
            try {
                String response = controller.getOldLogiScopeCompanyList(page);
                try {
                    Gson gson = new Gson();
                    OldLogiCompanyPaginate paginate = gson.fromJson(response, OldLogiCompanyPaginate.class);

                    runOnUiThread(() -> {
                        if (paginate.getData().isEmpty()) {
                            isLastPage = true;
                        } else {
                            oldLogiScopeCompanyList.addAll(paginate.getData());
                            companyAdapter.notifyDataSetInvalidated();
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
}
