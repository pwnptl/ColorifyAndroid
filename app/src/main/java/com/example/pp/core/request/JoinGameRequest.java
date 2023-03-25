package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JoinGameRequest extends Request {
    private final String playerId;
    private final String gameId;
}
