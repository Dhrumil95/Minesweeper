
public class Board {

    public final char concealed = 0;
    public final char concealedMine = 1;
    public final char revealed = 2;
    public final char mine = 3;
    public final char markedAsMine = 4;				//an indice other han a mine was marked
    public final char markedAsMine_mine = 5;			//truely a mine was marked
    public final char markedAsPotentialMine = 6;		//an indice other than a mine was questioned
    public final char markedAsPotentialMine_mine = 7;	//truely a mine was questioned

    public int rows;
    public int cols;
    public int numMines;
    public char[][] status;

    /*
        constructor , initializes objects
     */
    public Board(int row, int col, int mines) {
        int i = 0;

        numMines = mines;
        rows = row;
        cols = col;
        status = new char[row][col];

    }

    /* 
    Parameters:
            board:	the game board
            numMines:	the number of mines to be placed on the board
    Return: None
     */
    public void setBoard() {
        int r = 0;
        int c = 0;
        int i = 0;
        int isNew = 1;

        for (r = 0; r < rows; r++) {
            for (c = 0; c < cols; c++) {
                status[r][c] = concealed;
            }
        }

        for (i = 0; i < numMines; i++) {
            isNew = 1;
            while (isNew == 1) // make sure it does not overwrite an already filled bomb
            {
                r = (int) (Math.random() * rows);
                c = (int) (Math.random() * cols);

                if (status[r][c] == concealed) {
                    status[r][c] = concealedMine;
                    System.out.print("Placing mine at " + r + ", " + c + "\n");
                    isNew = 0;
                }
            }
        }
    }

    /*
        function to print board to console
     */
    public void displayBoard(Board b) {
        int r = 0;
        int c = 0;
        System.out.print("\n");
        for (r = b.rows - 1; r >= 0; r--) {
            if (r + 1 < 10) {
                System.out.print("   " + r + "   ");
            } else {
                System.out.print("  " + r + "   ");
            }
            for (c = 0; c < b.cols; c++) {
                if (b.status[r][c] == concealed || b.status[r][c] == concealedMine) {
                    System.out.print("#  ");
                } else if (b.status[r][c] == mine) {
                    System.out.print("*  ");
                } else if (b.status[r][c] == markedAsMine || b.status[r][c] == markedAsMine_mine) {
                    System.out.print("!  ");
                } else if (b.status[r][c] == markedAsPotentialMine || b.status[r][c] == markedAsPotentialMine_mine) {
                    System.out.print("?  ");
                } else {
                    System.out.print(b.status[r][c] + "  ");
                }

            }
            System.out.print("\n");
        }
        System.out.print("\n       ");
        for (c = 0; c < b.cols; c++) {
            System.out.print(c + "  ");
        }
        System.out.print("\n");

    }

    /* function used to check if indice contains a mine, returns true if yes else false */
    public int checkMine(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            if (status[row][col] == mine || status[row][col] == concealedMine || status[row][col] == markedAsMine_mine || status[row][col] == markedAsPotentialMine_mine) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /* function used to count number of mines within the surrounding area */
    public int CountSurrounding(int row, int col) {
        int count = 0;
        count += checkMine(row + 1, col);
        count += checkMine(row - 1, col);
        count += checkMine(row, col + 1);
        count += checkMine(row, col - 1);
        count += checkMine(row + 1, col + 1);
        count += checkMine(row + 1, col - 1);
        count += checkMine(row - 1, col + 1);
        count += checkMine(row - 1, col - 1);
        return count;
    }

    /* function used to reveal surrounding indices having no mines*/
    public void ShowSurrounding(int row, int col) {
        int count = 0;
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            if (status[row][col] < '0' && status[row][col] != mine) {
                count = CountSurrounding(row, col);
                status[row][col] = (char)(count + (int)'0');
                if (status[row][col] != mine && count == 0) {
                    ShowSurrounding(row + 1, col);
                    ShowSurrounding(row - 1, col);
                    ShowSurrounding(row, col + 1);
                    ShowSurrounding(row, col - 1);
                    ShowSurrounding(row + 1, col + 1);
                    ShowSurrounding(row + 1, col - 1);
                    ShowSurrounding(row - 1, col + 1);
                    ShowSurrounding(row - 1, col - 1);
                }
            }
        }
    }
    
    public void RevealAll()
    {
    	int c = 0;
    	int r = 0;

    	for (r = 0; r < rows; r++)
    	{
    		for (c = 0; c < cols; c++)
    		{
    			if (status[r][c] == mine || status[r][c] == concealedMine || status[r][c] == markedAsMine_mine || status[r][c] == markedAsPotentialMine_mine)
    			{
    				status[r][c] = mine;
    			}
    			else
    				ShowSurrounding(r, c);
    		}
    	}
    }
}
