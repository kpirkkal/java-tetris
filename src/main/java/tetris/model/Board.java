package tetris.model;

import java.awt.Point;

/**
 * 20 [ ][ ][ ][X][X][X][X][ ][ ][ ]
 * 19 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 18 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 17 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 16 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 15 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 14 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 13 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 12 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 11 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 * 10 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  9 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  8 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  7 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  6 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  5 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  4 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  3 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  2 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *  1 [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
 *     1  2  3  4  5  6  7  8  9  10
 */
public class Board {

	private static final int DROP_X = 5;
	private static final int DROP_Y = 19;
	
	private static final int WIDTH = 10;
	private static final int HEIGHT = 20;
	
	private Point pieceCenter = new Point(DROP_X, DROP_Y);
	
	private BoardCell[][] board = new BoardCell[WIDTH][HEIGHT];
	
	private int lines = 0;
	
	public Board() {
		for (int y=0; y<HEIGHT; y++) {
			for (int x=0; x<WIDTH; x++) {
				board[x][y] = BoardCell.getEmptyCell();
			}
		}
	}
	
	public int getWidth() {
		return WIDTH;
	}
	
	public int getHeight() {
		return HEIGHT;
	}
	
	public int getLines() {
		return lines;
	}
	
	public BoardCell getBoardAt(int x, int y) {
		return board[x][y];
	}
	
	private boolean isRowFull(int y) {
		for (int x=0; x<WIDTH; x++) {
			if (board[x][y].isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	public void removeFullRows() {
		BoardCell[][] boardX = new BoardCell[WIDTH][HEIGHT];
		
		for (int y=0; y<HEIGHT; y++) {
			for (int x=0; x<WIDTH; x++) {
				boardX[x][y] = BoardCell.getEmptyCell();
			}
		}
		
		int full = 0;
		for (int y=0; y<HEIGHT; y++) {
			if (!isRowFull(y)) {
				for (int x=0; x<WIDTH; x++) {
					boardX[x][y-full] = board[x][y];
				}
			} else {
				full++;
			}
		}
		
		lines += full;
		board = boardX;
	}

	public boolean rotate(Piece piece) {
		return fit(piece.getPoints(), 0, 0);
	}
	
	public void moveLeft(Piece piece) {
		if (fit(piece.getPoints(), -1, 0))
			mv(piece, -1, 0);
	}
	
	public void moveRight(Piece piece) {
		if (fit(piece.getPoints(), 1, 0))
			mv(piece, 1, 0);
	}
	
	public boolean moveDown(Piece piece) {
		if (fit(piece.getPoints(), 0, -1)) {
			mv(piece, 0, -1);
			removeFullRows();
			return false;
		} else {
			addPieceToBoard(piece);
			return true;
		}
	}
	
	public boolean fit(Point [] points, int moveX, int moveY){
		for (Point point : points) {
			int x = pieceCenter.x + point.x + moveX;
			int y = pieceCenter.y + point.y + moveY;
			
			if (x<0 || x >= getWidth() || y>=getHeight() || y<0) {
				return false;
			}
			
			if (board[x][y].isEmpty() == false) {
				return false;
			}
		}

		return true;
	}
	
	public BoardCell[][] getBoardWithPiece(Piece piece) {
		BoardCell[][] dest = new BoardCell[WIDTH][HEIGHT];
		
		for (int y=0; y<HEIGHT; y++) {
			for (int x=0; x<WIDTH; x++) {
				dest[x][y] = board[x][y];
			}
		}
		
		// add piece
		for (Point point : piece.getPoints()) {
			int x = point.x + pieceCenter.x;
			int y = point.y + pieceCenter.y;
			dest[x][y] = BoardCell.getFullCell(piece.getType());
		}
		
		return dest;
	}
	
	/**
	 * "Draw" board.
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		
		for (int y=0; y<HEIGHT; y++) {
			for (int x=0; x<WIDTH; x++) {
				if (board[x][19-y].isEmpty()) {
					out.append("[ ]");
				} else {
					out.append("[X]");
				}
			}
			out.append("\n");
		}
		return out.toString();
	}

	
	private void mv(Piece piece, int moveX, int moveY) {
		pieceCenter = new Point(pieceCenter.x + moveX, pieceCenter.y + moveY);
	}
	
	private void addPieceToBoard(Piece piece) {
		for (Point point : piece.getPoints()) {
			int x = pieceCenter.x + point.x;
			int y = pieceCenter.y + point.y;
			board[x][y] = BoardCell.getFullCell(piece.getType());
		}
		resetPieceCenter();
	}

	private void resetPieceCenter() {
		pieceCenter.x = DROP_X;
		pieceCenter.y = DROP_Y;
	}

}
