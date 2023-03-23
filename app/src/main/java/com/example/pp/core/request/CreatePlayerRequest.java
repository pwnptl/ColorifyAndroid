package com.example.pp.core.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreatePlayerRequest extends Request{
    private String name;
}
