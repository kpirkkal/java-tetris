package tetris.model;

public class Game {

    private final Board board = new Board();
    private Piece currentPiece;
    private Piece nextPiece;

    private boolean playing = false;
    private boolean paused = false;
    private boolean dropping = false;
    private boolean gameOver = false;

    // level 1-10
    private int freeFallIterations = 0;
    private int totalScore;

    public enum GameState {
        PLAYING, PAUSED, GAME_OVER
    }

    public BoardCell[][] getBoardWithPiece() {
        return board.getBoardWithPiece(currentPiece);
    }

    public Board getBoard() {
        return board;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public long getIterationDelay() {
        return (long) (((11 - getLevel()) * 0.05) * 1000);
    }

    public int getScore() {
        return ((21 + (3 * getLevel())) - freeFallIterations);
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getLines() {
        return board.getLines();
    }

    public int getLevel() {
        if ((board.getLines() >= 1) && (board.getLines() <= 90)) {
            return 1 + ((board.getLines() - 1) / 10);
        } else if (board.getLines() >= 91) {
            return 10;
        } else {
            return 1;
        }
    }

    public void startGame() {
        paused = false;
        dropping = false;
        currentPiece = Piece.getRandomPiece();
        nextPiece = Piece.getRandomPiece();
        playing = true;
    }

    public void stopGame() {
        playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void pauseGame() {
        paused = !paused;
    }

    public void moveLeft() {
        board.moveLeft(currentPiece);
    }

    public void moveRight() {
        board.moveRight(currentPiece);
    }

    public void moveDown() {
        if (board.moveDown(currentPiece)) {
            if (freeFallIterations == 0) {
                playing = false;
                gameOver = true;
            } else {
                dropping = false;
                currentPiece = nextPiece;
                nextPiece = Piece.getRandomPiece();
                totalScore += getScore();
                freeFallIterations = 0;
            }
        } else {
            freeFallIterations++;
        }
    }

    public void rotate() {
        Piece rot = currentPiece.rotate();
        if (board.rotate(rot)) {
            currentPiece = rot;
        }
    }

    public void drop() {
        dropping = true;
    }

    public boolean isDropping() {
        return dropping;
    }

}
