package dev.sud.snakeandladder.services;


import dev.sud.snakeandladder.models.*;

import java.util.*;

public class GameService {
    private Board board;
    private int initialNumberOfPlayers;
    private Queue<Player> players; 
    private boolean isGameCompleted;

    private int noOfDices;

    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NO_OF_DICES = 1;

    public GameService(int boardSize) {
        this.board = new Board(boardSize);  
        this.players = new LinkedList<Player>();
        this.noOfDices = GameService.DEFAULT_NO_OF_DICES;
    }

    public GameService() {
        this(GameService.DEFAULT_BOARD_SIZE);
    }


    public void setNoOfDices(int noOfDices) {
        this.noOfDices = noOfDices;
    }

    public void setPlayers(List<Player> players) {
        this.players = new LinkedList<Player>();
        this.initialNumberOfPlayers = players.size();
        Map<String, Integer> playerPieces = new HashMap<String, Integer>();
        for (Player player : players) {
            this.players.add(player);
            playerPieces.put(player.getId(), 0);
        }
        board.setPlayerPieces(playerPieces);
    }

    public void setSnakes(List<Snake> snakes) {
        board.setSnakes(snakes); // Add snakes to board
    }

    public void setLadders(List<Ladder> ladders) {
        board.setLadders(ladders); // Add ladders to board
    }

    private int getNewPositionAfterGoingThroughSnakesAndLadders(int newPosition) {
        int previousPosition;

        do {
            previousPosition = newPosition;
            for (Snake snake : board.getSnakes()) {
                if (snake.getStart() == newPosition) {
                    newPosition = snake.getEnd();
                }
            }

            for (Ladder ladder : board.getLadders()) {
                if (ladder.getStart() == newPosition) {
                    newPosition = ladder.getEnd();
                }
            }
        } while (newPosition != previousPosition);
        return newPosition;
    }

    private void movePlayer(Player player, int positions) {
        int oldPosition = board.getPlayerPieces().get(player.getId());
        int newPosition = oldPosition + positions; // Based on the dice value, the player moves their piece forward that number of cells.

        int boardSize = board.getSize();

        if (newPosition > boardSize) {
            newPosition = oldPosition;
        } else {
            newPosition = getNewPositionAfterGoingThroughSnakesAndLadders(newPosition);
        }

        board.getPlayerPieces().put(player.getId(), newPosition);

        System.out.println(player.getName() + " rolled a " + positions + " and moved from " + oldPosition +" to " + newPosition);
    }

    private int getTotalValueAfterDiceRolls() {
        return DiceService.roll();
    }

    private boolean hasPlayerWon(Player player) {
        int playerPosition = board.getPlayerPieces().get(player.getId());
        int winningPosition = board.getSize();
        return playerPosition == winningPosition; // A player wins if it exactly reaches the position 100 and the game ends there.
    }

    private boolean isGameCompleted() {
        int currentNumberOfPlayers = players.size();
        return currentNumberOfPlayers < initialNumberOfPlayers;
    }

    public void startGame() {
        while (!isGameCompleted()) {
            int totalDiceValue = getTotalValueAfterDiceRolls();
            Player currentPlayer = players.poll();
            movePlayer(currentPlayer, totalDiceValue);
            if (hasPlayerWon(currentPlayer)) {
                System.out.println(currentPlayer.getName() + " wins the game");
                board.getPlayerPieces().remove(currentPlayer.getId());
            } else {
                players.add(currentPlayer);
            }
        }
    }
}
