package com.jdt17.oauth.service.data.response.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OauthRegisterResponse {
    @JsonProperty("email")
    private String email;

    @JsonProperty("users_full_name")
    private String usersFullName;
}
