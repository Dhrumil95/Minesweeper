import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Minesweeper Testing
 * Dhrumil Patel, dpate85@uic.edu
 * Yue Yu, yyu31@uic.edu
**/

public class MSTest {
	
	@Test
	public void testBoardA() {
	    Board x;
		x = new Board(10,20,5);
		assertEquals(10,x.rows);
		assertEquals(20,x.cols);
		assertEquals(5,x.numMines);
		assertNotNull(x.status);
		
	}
	
	@Test
	public void testBoardB() {
	    Board y;
		y= new Board(1,2,3);
		assertEquals(1,y.rows);
		assertEquals(2,y.cols);
		assertEquals(3,y.numMines);
		assertNotNull(y.status);
		
	}
    
	@Test
	public void testSetBoard() {
	    int r = 0;
	    int c = 0;
	    int numberOfMines=0;
	    
		Board x;
		x = new Board(10,20,5);
		x.setBoard();
		
        for (r = 0; r < x.rows; r++) {
            for (c = 0; c < x.cols; c++) {
            	if(x.status[r][c]!=x.concealed){
            		numberOfMines++;
            	}
            }
        }
        assertEquals(5,numberOfMines);
	}
	
	@Test
	public void testCheckMineA() {
	    int row = 5;
	    int col = 6;
	    
		Board x;
		x = new Board(10,20,5);
		assertEquals(0,x.checkMine(row,col));
		
		x.status[row][col]=x.mine;
		assertEquals(1,x.checkMine(row,col));
			
		Board y;
		y= new Board(10,20,5);
		row = 11;
		col = 21;
		assertEquals(0,y.checkMine(row,col));		
		
	}

	@Test
	public void testCheckMineB() {
	    int row = 2;
	    int col = 3;
	    
		Board x;
		x = new Board(20,40,10);
		assertEquals(0,x.checkMine(row,col));
		
		x.status[row][col]=x.concealedMine;
		assertEquals(1,x.checkMine(row,col));
			
		Board y;
		y= new Board(20,40,10);
		row = 5;
		col = 45;
		assertEquals(0,y.checkMine(row,col));		
		
	}
	
	@Test
	public void testCheckMineC() {
	    int row = 2;
	    int col = 3;
	    
		Board x;
		x = new Board(20,40,10);
		assertEquals(0,x.checkMine(row,col));
		
		x.status[row][col]=x.concealed;
		assertEquals(0,x.checkMine(row,col));
			
		Board y;
		y= new Board(20,40,10);
		row = 5;
		col = 45;
		assertEquals(0,y.checkMine(row,col));		
		
	}
	
	@Test
	public void testCountSurroundingA() {
		int row = 5;
	    int col = 6;
		Board x;
		x = new Board(10,20,5);
		assertEquals(0,x.CountSurrounding(row, col));
		x.status[row+1][col]=x.mine;
		assertEquals(1,x.CountSurrounding(row, col));
		x.status[row+1][col+1]=x.mine;
		assertEquals(2,x.CountSurrounding(row, col));
	}

	@Test
	public void testCountSurroundingB() {
		int row = 5;
	    int col = 10;
		Board x;
		x = new Board(10,20,5);
		assertEquals(0,x.CountSurrounding(row, col));
		x.status[row-1][col]=x.concealedMine;
		assertEquals(1,x.CountSurrounding(row, col));
		x.status[row-1][col-1]=x.concealedMine;
		assertEquals(2,x.CountSurrounding(row, col));
		x.status[row][col-1]=x.concealedMine;
		assertEquals(3,x.CountSurrounding(row, col));
	}
	
	@Test
	public void testCountSurroundingC() {
		int row = 8;
	    int col = 15;
		Board x;
		x = new Board(20,30,5);
		assertEquals(0,x.CountSurrounding(row, col));
		x.status[row-1][col]=x.markedAsMine_mine;
		assertEquals(1,x.CountSurrounding(row, col));
		x.status[row-1][col-1]=x.markedAsMine_mine;
		assertEquals(2,x.CountSurrounding(row, col));
		x.status[row][col-1]=x.markedAsMine_mine;
		assertEquals(3,x.CountSurrounding(row, col));
		x.status[row+1][col]=x.markedAsMine_mine;
		assertEquals(4,x.CountSurrounding(row, col));
	}
	
	@Test
	public void testCountSurroundingD() {
		int row = 8;
	    int col = 15;
		Board x;
		x = new Board(20,30,5);
		assertEquals(0,x.CountSurrounding(row, col));
		x.status[row-1][col]=x.markedAsPotentialMine_mine;
		assertEquals(1,x.CountSurrounding(row, col));
		x.status[row-1][col-1]=x.markedAsPotentialMine_mine;
		assertEquals(2,x.CountSurrounding(row, col));
		x.status[row][col-1]=x.markedAsPotentialMine_mine;
		assertEquals(3,x.CountSurrounding(row, col));
		x.status[row+1][col]=x.markedAsPotentialMine_mine;
		assertEquals(4,x.CountSurrounding(row, col));
		x.status[row+1][col+1]=x.markedAsPotentialMine_mine;
		assertEquals(5,x.CountSurrounding(row, col));
	}
	
	@Test
	public void testRevealAllA() {
		Board x;
		x = new Board(10,20,5);
		x.status[5][6] = x.concealedMine;
		x.RevealAll();
		assertTrue(x.status[5][6]==x.mine);
		
	}
	
	@Test
	public void testRevealAllB() {
		Board y;
		y = new Board(10,20,5);
		y.status[8][16] = y.markedAsMine_mine;
		y.RevealAll();
		assertTrue(y.status[8][16]==y.mine);
	}
	
	@Test
	public void testRevealAllC(){
		Board z;
		z = new Board(10,20,5);
		z.status[9][19] = z.markedAsPotentialMine_mine;
		z.RevealAll();
		assertTrue(z.status[9][19]==z.mine);
	}
	
	@Test
	public void testScore() {
		Score x;
		x = new Score();
		assertTrue(x.points==Integer.MAX_VALUE);
	}

	@Test
	public void testInit(){
		Game y = new Game();
		y.isOver = true;
		y.firstClick = true;
		assertTrue(y.isOver);
		assertTrue(y.firstClick);
		y.init();
		assertFalse(y.isOver);
		assertFalse(y.firstClick);
		assertNotNull(y.c_score);
		assertNotNull(y.m);
	}
	
	@Test
	public void testGame(){
		Game y = new Game();
		assertNotNull(y.score_label);
		assertNotNull(y.scoreBar);
		assertNotNull(y.field);
	}
	
	@Test
	public void testLoadMenuButtons(){
		Game y = new Game();
		y.loadMenuButtons();
		assertNotNull(y.menuBar);
		assertNotNull(y.menu);
		assertNotNull(y.menuItem);
	}
	
}
