package com.mrsabpata;

public class Simulation {

    int width; //Board size
    int height; //Board size
    int[][] board; // 2d array to store board size

    //constructor to create a board object
    public Simulation(int width, int height) {
        this.width = width;
        this.height = height;
        this.board = new int[width][height];
    }

    //method to print the board
    public void printBoard() {
        System.out.println("---");
        for (int y = 0; y < height; y++) {
            String line = "|";
            for (int x = 0; x < width; x++) {
                if (this.board[x][y] == 0) {
                    line += "."; //using += to make thing simple.
                } else {
                    line += "*";
                }
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("---\n");
    }

    public void setAlive(int x, int y) {
        this.board[x][y] = 1;
    }

    public void setDead(int x, int y) {
        this.board[x][y] = 0;
    }

    public int countAliveNeighbours(int x, int y) {
        int count = 0;

        count += getState(x - 1, y - 1);
        count += getState(x, y - 1);
        count += getState(x + 1, y - 1);

        count += getState(x - 1, y);
        count += getState(x + 1, y);

        count += getState(x - 1, y + 1);
        count += getState(x, y + 1);
        count += getState(x + 1, y + 1);

        return count;
    }

    public int getState(int x, int y) { // check state of the neighbours to avoid negative array value
        if (x < 0 || x >= width) {
            return 0;
        }

        if (y < 0 || y >= height) {
            return 0;
        }

        return this.board[x][y];
    }

    //to move from one board to another and each cell
    public void step() {
        int[][] newBoard = new int[width][height]; //create a new board to void changing in current board

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int aliveNeighbours = countAliveNeighbours(x, y); //have a count of the neighbour

                if (getState(x, y) == 1) { //Rule 1: Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                    if (aliveNeighbours < 2) {
                        newBoard[x][y] = 0;
                    } else if (aliveNeighbours == 2 || aliveNeighbours == 3) { //Rule 2: Any live cell with two or three live neighbours lives on to the next generation.
                        newBoard[x][y] = 1;
                    } else if (aliveNeighbours > 3) { //Rule 3: Any live cell with more than three live neighbours dies, as if by overpopulation.
                        newBoard[x][y] = 0;
                    }
                } else {
                    if (aliveNeighbours == 3) { //Rule 4: Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
                        newBoard[x][y] = 1;
                    }
                }

            }
        }

        this.board = newBoard;
    }

    public static void main(String[] args) {
        Simulation simulation = new Simulation(8, 5);

        simulation.setAlive(2, 2);
        simulation.setAlive(3, 2);
        simulation.setAlive(4, 2);

        simulation.printBoard();

        simulation.step();

        simulation.printBoard();

        simulation.step();

        simulation.printBoard();

    }
}
