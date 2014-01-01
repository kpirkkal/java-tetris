package tetris.model;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class BoardTest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;

    @Test
    public void newBoardIsFullOfEmptyCells() {
        Board board = new Board();
        
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                assertTrue(board.getBoardAt(x, y).isEmpty());
            }
        }
    }

}
