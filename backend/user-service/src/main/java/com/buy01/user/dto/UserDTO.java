package com.buy01.user.dto;

import com.buy01.user.model.Role;

// for internal id and role checks
public class UserDTO {
    private String id;
    private Role role;

    public UserDTO() {}

    public UserDTO(String id, Role role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }
}

