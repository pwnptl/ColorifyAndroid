package com.example.pp.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGameResponse {
    private final String gameId;
    private final String status;
}