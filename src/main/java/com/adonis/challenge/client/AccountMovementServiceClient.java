package com.adonis.challenge.client;

import com.adonis.challenge.presentation.presenter.AccountPresenter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "account-movement-service", url = "${feign.client.account-movement}")
public interface AccountMovementServiceClient {

    @PostMapping("/getAccountByClientId")
    List<AccountPresenter> getAccountsByClientId(String clientId);
}
