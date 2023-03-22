package com.example.pp.core.request;


public class GetPlayerRequest extends Request {

    private String playerId;

    // todo : use lombok for constructors.
    // AllArgsConstructor
    public GetPlayerRequest(String playerId) {
        this.playerId = playerId;
    }

    // NoArgsConstructor
    public GetPlayerRequest() {
    }

    // Getter

    public String getPlayerId() {
        return playerId;
    }

    // Setter

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
}
