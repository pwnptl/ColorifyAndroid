package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGameRequest extends Request {
    private final String currentPlayerId;
}
