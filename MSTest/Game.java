import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Game extends JFrame implements ActionListener {

    private final int top_x = 10;
    private Score[] score;
    private final int ROWS = 10;
    private final int COLUMNS = 10;
    private JLabel statusBar;
    private JButton Minefield[][];
    private Board b;
    private int row = 0;
    private int col = 0;
    Score c_score;
    long startTime = 0;
    long endTime = 0;
    boolean isOver = false;
    boolean firstClick = false;
    //Menu objects
    JMenuBar menuBar;
    JMenu menu, submenu;
    JMenuItem menuItem;
    JPanel scoreBar;
    JPanel field;
    Label score_label;
    Label m;
    ImageIcon mine = new ImageIcon("Resources/mine.png");
    ImageIcon question = new ImageIcon("Resources/question.png");
    ImageIcon flag= new ImageIcon("Resources/flag.png");
    public void init()
    {
    	isOver = false;
    	firstClick = false;
    	b = new Board(ROWS,COLUMNS,10);
        b.setBoard();
        c_score = new Score();
        c_score.points = 0;
        m = new Label("Mines: 10");
        score_label.setText("Timer: 0");
        Runnable timeRunnable = new Runnable() {
            public void run() {
            	if(firstClick && isOver == false)
            	{
            	 endTime = System.currentTimeMillis();
              	 c_score.points = (int)(endTime - startTime)/1000;
              	 score_label.setText("Timer: " + c_score.points);
            	}
            }
        };
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(timeRunnable, 0, 1, TimeUnit.SECONDS);
        
    }
    //constructor to load Game class objects
    public Game() {
        
    	score_label = new Label("Timer: 0");
    	init();
    	
        score = new Score[top_x];
        for (int i = 0; i < top_x; i++) {
            score[i] = new Score();
        }
       
        readFromFile("score.txt");
        
        loadMenuButtons();
        scoreBar = new JPanel();
        scoreBar.setLayout(new GridLayout(2,1));
        
        field = new JPanel();
        field.setLayout(new GridLayout(ROWS, COLUMNS));
        //field.setSize(600,100);
        
        //setLayout(new GridLayout(ROWS, COLUMNS));
        Minefield = new JButton[ROWS][COLUMNS];
        buildButtonField();
        
        scoreBar.add(m);
        scoreBar.add(score_label);
        JButton reset = new JButton("reset");
        reset.addActionListener(this);
        scoreBar.add(reset);
        add(scoreBar, BorderLayout.NORTH);
        add(field, BorderLayout.CENTER);
        setSize(600, 700);
        show();
    }

    //function to load menu buttons
    public void loadMenuButtons() {
        //Create the menu bar.
        menuBar = new JMenuBar();
        //Build the first menu.
        menu = new JMenu("Game");
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menu.setMnemonic('G');
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Reset");
        menuItem.setMnemonic(KeyEvent.VK_R);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Resets top 10 score");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a group of JMenuItems
        menuItem = new JMenuItem("Top 10");
        menuItem.setMnemonic(KeyEvent.VK_T);;
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Display's Top 10 score");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a group of JMenuItems
        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_E);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Exits the program");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menu.getAccessibleContext().setAccessibleDescription(
                "Help");
        menu.setMnemonic('L');
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Help");
        menuItem.setMnemonic(KeyEvent.VK_H);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "Help information");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //a group of JMenuItems
        menuItem = new JMenuItem("About");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.getAccessibleContext().setAccessibleDescription(
                "About information");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        setJMenuBar(menuBar);

    }
   
    

    //function to listen for menu clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonClicked = e.getActionCommand();
        String s = "";

        if (buttonClicked.equals("Exit")) {
            s = "Exiting Game\n";
            JOptionPane.showMessageDialog(null, s);
            System.exit(0);
        } else if (buttonClicked.equals("Reset")) {
            for (int i = 0; i < top_x; i++) {
                score[i].playerName = "";
                score[i].points = 0;
            }
            s = "Scores have been reset\n";
            JOptionPane.showMessageDialog(null, s); // show appropriate dialog
        } else if (buttonClicked.equals("Top 10")) {
            s = s + "TOP 10 Score:\n";
            for (int i = 0; i < top_x; i++) {
                s = s + (i + 1) + ". " +score[i].playerName + ": " + score[i].points + "\n";
            }
            JOptionPane.showMessageDialog(null, s); // show appropriate dialog
        } else if(buttonClicked.equals("Help"))
        {
            s = s + "How to play the game?\n\nIf a mine is revealed the game ends.\n" +
                    "The game continues if a empty square(zero) is found.\nIf a number is revealed, it tells you how many (hidden) mines are in the eight surrounding squares.\n" 
                  + "Using this information, make your way through all the squares without hitting a mine.\n\n" +
                    "The right-click continues in three cycles. \n" + 
                    "1. First click puts a mine flag on the square.\n" +
                    "2. Second click puts a question mark on the square.\n" +
                    "3. Third click restores the square.\n";
           JOptionPane.showMessageDialog(null, s); // show appropriate dialog

         }
         else if(buttonClicked.equals("About")) {
             s = s + "CS 342 Project 2 - Minesweeper\nSpring 2016\nUniversity of Illinois at Chicago" + 
                     "\n\nAuthors:\nDhrumil Patel\ndpate85@uic.edu\n\nYue Yu\nyyu31@uic.edu";
             JOptionPane.showMessageDialog(null, s); // show appropriate dialog
         } else if(buttonClicked.equals("reset")) {
        	 init();
        	 refresh();
         }
    }

    // function to handle mouse clicks 
    private class MouseClickHandler extends MouseAdapter {
    	int r = 0;
    	int c = 0;
        public void mouseClicked(MouseEvent e) {
        	
        }
    }
    
    
    // function to build minefield
    public void buildButtonField() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                row = r;
                col = c;
                Minefield[r][c] = new JButton(" ");
                Minefield[r][c].addMouseListener (new MouseClickHandler()
                {
                	int r = row;
                	int c = col;
                    public void mouseClicked(MouseEvent e) {
                    	
                    
                    			
                    				  if (SwingUtilities.isLeftMouseButton(e))
                    	              {
                    	                  leftMouseFunc(r,c);
                    	              }
                    	              else if (SwingUtilities.isRightMouseButton(e) || e.isMetaDown())
                    	              {
                    	                  rightMouseFunc(r,c);
                    	              }
                    	              
                    	              refresh();
                    			
                    		
                    
                    }
                }); 
                field.add(Minefield[r][c]);
            }
        }
    }

    //function to udpate the minefield
    public void refresh()
    {
        //System.out.print("refresh\n");
    	  int count = 0;
          for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                
                if(b.status[r][c] == b.concealed || b.status[r][c] == b.concealedMine)
                {
                    Minefield[r][c].setText(" ");
		    Minefield[r][c].setIcon(null);
                    
                }
                else if(b.status[r][c] == b.mine)
                {
                    //Minefield[r][c].setText("*");
                    Minefield[r][c].setIcon(mine);
                }
                else if(b.status[r][c] == b.markedAsMine || b.status[r][c] == b.markedAsMine_mine)
                {
                    //Minefield[r][c].setText("M");
                    Minefield[r][c].setIcon(flag);
                    if(b.status[r][c] == b.markedAsMine_mine)
                    	count++;
                }
                else if(b.status[r][c] == b.markedAsPotentialMine || b.status[r][c] == b.markedAsPotentialMine_mine)
                {
                    //Minefield[r][c].setText("?");
                	 Minefield[r][c].setIcon(question);
                }
                else
                {
                    char t = (char) (b.status[r][c]);
                    String text = Character.toString(t);
                    Minefield[r][c].setText(text);
                    
                }
            }
          }
         
    	  
          
          
          if(count == 10 && isOver == false)
          {
        	  endTime = System.currentTimeMillis();
        	  c_score.points = (int)(endTime - startTime);
        	  JOptionPane.showMessageDialog(null, "Congratz, you win!");
        	  JFrame frame = new JFrame("InputDialog");
        	  String s = (String)JOptionPane.showInputDialog(frame,"Enter your name");
        	  c_score.playerName = s;
        	  updateScore();
        	  
          }     
    }
    
    //function to read scores from file
    public void readFromFile(String fileName) 
    {
        FileInputStream in = null;

        try {
           try {
			in = new FileInputStream(fileName);
			 for(int i = 0; i < top_x; i++)
	           {
	        	   StringBuilder builder = new StringBuilder();
	        	   int ch;
	        	  
	        	   try {
					while((ch = in.read()) != ' '){
					       builder.append((char)ch);
					   }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	   score[i].points = Integer.parseInt(builder.toString());
	        	   builder = new StringBuilder();
	        	   try {
					while((ch = in.read()) != '\n'){
					       builder.append((char)ch);
					   }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	   score[i].playerName = builder.toString();
	           }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
        
          
        }finally {
           if (in != null) {
              try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
        }
    }
    
    //function to write scores to file
    public void writeToFile(String fileName) throws IOException
    {
        FileOutputStream out = null;

        try {
           out = new FileOutputStream(fileName);
           
         
           for(int i = 0; i < top_x; i++)
           {
        	   String output = score[i].points + " " + score[i].playerName + "\n";
               out.write(output.getBytes(Charset.forName("UTF-8")));
           }
        }finally {
           if (out != null) {
              out.close();
           }
        }
    }
    
    //function to update score list
    public void updateScore()
    {
    	int i = top_x-1;
    	while(i > 0 && c_score.points < score[i].points)
    	{
    		i--;
    	}
    	if(c_score.points > score[i].points)
    	{
    		i++;
    	}
    	if(i >= 0 && i < top_x)
    	{
    		score[i].playerName = c_score.playerName;
    		score[i].points = c_score.points;
    	}
    	
    	try {
			writeToFile("score.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    // function to perform Left mouse tasks
    public void leftMouseFunc(int r, int c)
    {
        if(firstClick == false)
        {
        	 startTime = System.currentTimeMillis();
        	 firstClick = true;
        }
        if(b.status[r][c] == b.concealed || b.status[r][c] == b.concealedMine)
        {
            if(b.status[r][c] == b.concealedMine)
            {
            	 JOptionPane.showMessageDialog(null, "BOOM!, you loose");
            	 b.RevealAll();
            	 isOver = true;
            	 refresh();
            }
            else
            {
                b.ShowSurrounding(r, c);
                
            }
        }
    }
    
    // function to perform right mouse tasks
    public void rightMouseFunc(int r, int c)
    {
        
       
        if((b.status[r][c] == b.concealed || b.status[r][c] == b.concealedMine) && b.numMines > 0)
        {
            if(b.status[r][c] == b.concealedMine)
            {
                b.status[r][c] = (char)b.markedAsMine_mine;
            }
            else
                b.status[r][c] = (char)b.markedAsMine;
            b.numMines--;
        }
        else if(b.status[r][c] == b.markedAsMine_mine|| b.status[r][c] == b.markedAsMine)
        {
            
            if (b.status[r][c] == b.markedAsMine)
            {
            	b.status[r][c] = (char)b.markedAsPotentialMine;
            }
            else
            	b.status[r][c] = (char)b.markedAsPotentialMine_mine;
            b.numMines++;
        }
        else if(b.status[r][c] == b.markedAsPotentialMine || b.status[r][c] == b.markedAsPotentialMine_mine)
        {
             if (b.status[r][c] == b.markedAsPotentialMine)
             {
                 b.status[r][c] = (char)b.concealed;
             }
             else
                  b.status[r][c] = (char)b.concealedMine;
        }
    }
    
    public static void main(String[] args) {
        Game b = new Game();

        b.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );
    }
}
