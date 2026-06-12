package com.jdt17.oauth.service.service.module;

import com.jdt17.oauth.service.components.runtime.exception.CoreThrowHandler;
import com.jdt17.oauth.service.data.module.UserDTO;
import com.jdt17.oauth.service.data.request.OauthRegisterRequest;
import com.jdt17.oauth.service.data.request.RequestWrapperDTO;
import com.jdt17.oauth.service.data.response.RestApiError;
import com.jdt17.oauth.service.data.response.RestApiResponse;
import com.jdt17.oauth.service.data.response.module.OauthRegisterResponse;
import com.jdt17.oauth.service.service.repository.OauthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterImplementation implements RegisterInterface {

    private final OauthRepository oauthRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;

    @Override
    public RestApiResponse<OauthRegisterResponse> register(RequestWrapperDTO<OauthRegisterRequest> requestWrapperDTO) throws CoreThrowHandler {
        OauthRegisterRequest request = requestWrapperDTO.getRequest();

        Optional<UserDTO> existingUser = oauthRepository.getByUsersEmail(request.getOauthRegisterRequestEmail());
        if (existingUser.isPresent()) {
            throw new CoreThrowHandler(RestApiError.EMAIL_ALREADY_EXISTS, messageSource);
        }

        UserDTO newUser = UserDTO.builder()
                .usersFullname(request.getOauthRegisterRequestFullName())
                .usersEmail(request.getOauthRegisterRequestEmail())
                .usersCredentials(passwordEncoder.encode(request.getOauthRegisterRequestPassword()))
                .usersStatus(true)
                .usersCreatedDate(LocalDateTime.now())
                .usersUpdatedDate(LocalDateTime.now())
                .usersDevice(requestWrapperDTO.getRequestDevicePlatform())
                .build();

        oauthRepository.save(newUser);

        OauthRegisterResponse response = OauthRegisterResponse.builder()
                .email(newUser.getUsersEmail())
                .usersFullName(newUser.getUsersFullname())
                .build();

        return RestApiResponse.<OauthRegisterResponse>builder()
                .restApiResponseHttpCode(HttpStatus.CREATED.value())
                .restApiResponseResult(response)
                .restApiResponseMessage("KAMU BERHASIL MENDAFTAR")
                .build();
    }
}
