package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MakeMoveRequest extends Request {
    private final String playerId;
    private final String gameId;
    private final int chosenColor;
}
