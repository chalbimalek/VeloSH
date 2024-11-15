package com.shirazmsalmi.runbackend.Response;

import com.shirazmsalmi.runbackend.Entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private User user;
    private Boolean followedByAuthUser;
}