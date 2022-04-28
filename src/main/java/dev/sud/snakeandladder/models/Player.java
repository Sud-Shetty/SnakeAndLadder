package dev.sud.snakeandladder.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Player {
    private String id;
    private String name;

    public Player(String name){
        this.id=UUID.randomUUID().toString();
        this.name=name;
    }
}
