package org.bananalaba.springcdtemplate.service;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Getter
@Component
public class SecretsVaultService {

    @NonNull
    @Value("${db.connection.userName}")
    private final String dbUserName;
    @NonNull
    @Value("${db.connection.password}")
    private final String dbPassword;


}
