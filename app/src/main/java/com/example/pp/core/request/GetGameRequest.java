package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetGameRequest extends Request {
    private final String gameId;
}
