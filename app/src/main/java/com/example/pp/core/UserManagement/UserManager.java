package com.example.pp.core.UserManagement;

import lombok.Getter;
import lombok.Setter;

public class UserManager {

    @Setter
    @Getter
    private String userId;

    private UserManager() {
        userId = null;
    }

    private static UserManager instance;

    public static UserManager getInstance() {
        if (instance == null)
            instance = new UserManager();
        return instance;
    }

    public boolean hasUserId() {
        return userId != null;
    }
}
