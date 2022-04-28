package dev.sud.snakeandladder.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Board {
    private int size;
    private List<Ladder> ladders;
    private List<Snake> snakes;
    private Map<String,Integer> playerPieces;

    public Board(int size) {
        this.size = size;
        this.ladders = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.playerPieces = new HashMap<>();
    }
}
