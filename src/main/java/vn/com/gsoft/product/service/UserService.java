package vn.com.gsoft.product.service;

import vn.com.gsoft.product.model.system.Profile;

import java.util.Optional;

public interface UserService {
    Optional<Profile> findUserByToken(String token);
}
