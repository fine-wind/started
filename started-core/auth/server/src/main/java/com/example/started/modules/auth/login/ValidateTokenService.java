package com.example.started.modules.auth.login;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class ValidateTokenService {
    private static final String SITEVERIFY_URL = "https://challenges.cloudflare.com/turnstile/v0/siteverify";
    private final String secretKey = "your-secret-key";
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean validateToken(String token, String remoteip) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", secretKey);
        params.add("response", token);
        if (remoteip != null) {
            params.add("remoteip", remoteip);
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        TurnstileResponse body = new TurnstileResponse();
        try {
            ResponseEntity<TurnstileResponse> response = restTemplate.postForEntity(SITEVERIFY_URL, request, TurnstileResponse.class);
            body = response.getBody();
        } catch (Exception e) {
            log.error("token validation failed : {}", token, e);
        }
        return Objects.nonNull(body) && body.isSuccess();
    }

}

@Data
class TurnstileResponse {
    private boolean success = false;
    private List<String> errorCodes;
}