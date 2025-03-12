package com.app.blogging.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String jwt;
    private boolean status;
    private String message;
    private String session;
}
