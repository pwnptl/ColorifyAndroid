package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterPlayerSessionRequest {
    private final String userId;
}
