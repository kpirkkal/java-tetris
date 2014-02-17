package tetris.model;

public class Game {

    private final Board board = new Board();
    private Piece currentPiece;
    private Piece nextPiece;

    private boolean playing = false;
    private boolean paused = false;
    private boolean dropping = false;
    private boolean gameOver = false;

    private int freeFallIterations;
    private int totalScore;

    public BoardCell[][] getBoardCells() {
        return board.getBoardWithPiece(currentPiece);
    }

    public Piece getNextPiece() {
        return nextPiece;
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
        return board.getFullLines();
    }

    public int getLevel() {
        if ((board.getFullLines() >= 1) && (board.getFullLines() <= 90)) {
            return 1 + ((board.getFullLines() - 1) / 10);
        } else if (board.getFullLines() >= 91) {
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

    public boolean isPaused() {
        return paused;
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
            board.addPieceToBoard(currentPiece);
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