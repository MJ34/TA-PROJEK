package com.tugasakhir.service;

import com.tugasakhir.domain.*;
import com.tugasakhir.domain.security.PasswordResetToken;
import com.tugasakhir.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    User createUser (User user, Set<UserRole> userRoles);

    User save (User user);

    User findById (Long id);

    User findByUsername (String username);

    User findByEmail(String email);

    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    void updateUserShipping(UserShipping userShipping, User user);

    void setUserDefaultShipping(Long userShippingId, User user);

}
