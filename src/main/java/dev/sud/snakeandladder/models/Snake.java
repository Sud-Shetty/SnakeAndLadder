package dev.sud.snakeandladder.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Snake {
    private int start;
    private int end;

    public Snake(int start,int end){
        this.start=start;
        this.end=end;
    }
}
