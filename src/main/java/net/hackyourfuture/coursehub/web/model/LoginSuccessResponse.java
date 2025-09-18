package net.hackyourfuture.coursehub.web.model;

import net.hackyourfuture.coursehub.data.Role;

public record LoginSuccessResponse(Integer userId, String firstName, String lastName, String emailAddress, Role role) {}
