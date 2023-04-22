package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterPlayerSessionRequest extends Request {
    private final String userId;
}
