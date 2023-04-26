package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;

public interface UserService {

    User save(User user);

    User update(User user);
}
