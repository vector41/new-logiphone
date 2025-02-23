package com.example.logiphone;

import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Controller {
    public HttpCommunication communication = new HttpCommunication();

    public String login(String email, String password) throws IOException {
        String endpoint = "login?email=" + email + "&password=" + password;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getFavoritesList(int page) throws IOException {
        int user_id = BaseData.getInstance()._id;
        String keyword = BaseData.getInstance()._searchKeyword;
        String endpoint = "get-favorite-list?user_id=" + user_id + "&keyword=" + keyword + "&page=" + page;
        Log.e("endpoint", endpoint);

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getFavoriteAddList(int page) throws IOException {
        String endpoint = "get-favorite-add-list?page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getLogiPhoneList(int page) throws IOException {
        String keyword = BaseData.getInstance()._searchKeyword;
        String sort = BaseData.getInstance()._sort;
        String endpoint = "get-logiphone-list?keyword=" + keyword + "&sort=" + sort + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getNewLogiScopeUserList(int page) throws IOException {
        int id = BaseData.getInstance()._id;
        String keyword = BaseData.getInstance()._searchKeyword;
        String sort = BaseData.getInstance()._sort;
        String endpoint = "get-logiscope-list?id=" + id + "&keyword=" + keyword + "&sort=" + sort + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getNewLogiScopeCompanyList(int page) throws IOException {
        int id = BaseData.getInstance()._id;
        String keyword = BaseData.getInstance()._searchKeyword;
        String sort = BaseData.getInstance()._sort;
        String endpoint = "get-all-companies?id=" + id + "&keyword=" + keyword + "&sort=" + sort + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getOldLogiScopeUserList(int page) throws IOException {
        int id = BaseData.getInstance()._id;
        String keyword = BaseData.getInstance()._searchKeyword;
        String sort = BaseData.getInstance()._sort;
        String endpoint = "get-oldlogi-list?id=" + id + "&keyword=" + keyword + "&sort=" + sort + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getOldLogiScopeCompanyList(int page) throws IOException {
        int id = BaseData.getInstance()._id;
        String keyword = BaseData.getInstance()._searchKeyword;
        String sort = BaseData.getInstance()._sort;
        String endpoint = "get-old-all-companies?id=" + id + "&keyword=" + keyword + "&sort=" + sort + "&page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getProfileDetail() throws IOException {
        int id = BaseData.getInstance()._id;
        int userId = BaseData.getInstance()._selectedUserId;
        int type = BaseData.getInstance()._type;
        String endpoint = "get-profile?id=" + id + "&user_id=" + userId + "&type=" + type;
        Log.e("EndPoint", endpoint);

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getCompanyDetail() throws IOException {
        int id = BaseData.getInstance()._selectedCompanyId;
        int type = BaseData.getInstance()._type;
        String endpoint = "get-company-details?id=" + id + "&type=" + type;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String changeFavoriteState() throws IOException {
        int userId = BaseData.getInstance()._id;
        int selectedId = BaseData.getInstance()._selectedUserId;
        int type = BaseData.getInstance()._type;
        String endpoint = "change-favorite-list?user_id=" + userId + "&selected_id=" + selectedId + "&type=" + type;
        Log.e("EndPoint", endpoint);

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getCompanyList(int page) throws IOException {
        String endpoint = "get-all-companies?page=" + page;

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String getMemberList(int page) throws IOException {
        int companyId = BaseData.getInstance()._selectedCompanyId;
        int type = BaseData.getInstance()._type;
        String endpoint = "get-member-list?company_id=" + companyId + "&type=" + type + "&page=" + page;
        Log.e("endpoint", endpoint);

        try (Response response = communication.client.newCall(communication.buildHttpGetRequest(endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String addNewUser(String endpoint, String payload) throws IOException {
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json; charset=utf-8"));

        try (Response response = communication.client.newCall(communication.bulidHttpPostRequest(body, endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }

    public String updateUser(String endpoint, String payload) throws IOException {
        RequestBody body = RequestBody.create(payload, MediaType.parse("application/json; charset=utf-8"));

        try (Response response = communication.client.newCall(communication.bulidHttpPostRequest(body, endpoint)).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Exception: " + response);
            }
        }
    }
}
