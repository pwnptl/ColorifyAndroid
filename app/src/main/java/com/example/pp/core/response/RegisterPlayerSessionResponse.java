package com.example.pp.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterPlayerSessionResponse {
    final boolean registered;
    final String sessionId;
}
