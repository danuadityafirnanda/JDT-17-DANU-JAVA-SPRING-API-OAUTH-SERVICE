package com.jdt17.oauth.service.service.module;

import com.jdt17.oauth.service.data.response.RestApiResponse;
import com.jdt17.oauth.service.data.response.module.OauthLoginResponse;

public interface LoginInterface {
    RestApiResponse<OauthLoginResponse> login(String username, String password);
}
