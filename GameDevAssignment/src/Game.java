
import java.awt.*;



import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.*;


import game2D.*;

// Game demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also add code to the 'init'
// method that will initialise event handlers etc. 

// Student ID: 2912082


@SuppressWarnings("serial")


public class Game extends GameCore 
{
	// Useful game constants
	static int screenWidth = 512;
	static int screenHeight = 384;

	// Game constants
   private float	gravity = 0.0010f;    
    // Game state flags
  private boolean jumping = false;
  private boolean moveRight = false;
  private boolean moveLeft = false;
  private boolean debug = true;
  private boolean collision = false;
  private boolean playerDeath = false;
  private boolean falling = true;
  private boolean gameOver = false;
  private int playerHeight;
  
  public static STATE Status = STATE.MENU;
  private Menu menu;
  private Dead dead;
  private Help help;
  private Finished finished;
  public enum STATE //
  {
      MENU,
      GAME,
      HELP,
      DEAD,
      FINISHED
  }
  
  private int spawnX = 100;
  private int spawnY = 100;
  private float posX;
  private float posY;

    // Game resources specifically all of the annimations that are to be used by the system
  private Animation idleLeft = null;
  private Animation idleRight = null;
  private Animation walk = null;
  private Animation dying = null;
  private Animation jump = null;
  private Animation walkBack = null;

  	//Stage Resources for the two levels
  private Image backgroundOne = null;
  private Image backgroundTwo = null;
  private int levelOne = 0;
  private int levelTwo = 0;
  private int level = 1;
  private Sound themeMusic = null;
  //Player stats variables in order to hold the number of jumps,player lives and if the user sprite can be killed
  private int jumpCount = 0;		
  private int playerLives= 3;
	// Ints representing the positions (in til;es) of the tiles surrounding the player
    int yTop; // Tile above the player
    int xTop;
    
    int xBottom; // Tile below the player
    int yBottom;
    
    int xRight; // Tile to the right of the player
    int yRight;
    
    int xLeft; // Tile to the left of the player
    int yLeft;
	
  //Sprite Systems
   private Sprite enemyOne = null;
   private Sprite enemyTwo = null;
   private Sprite	player = null;
   private Animation enemywalkE1 = null;
   private Animation enemywalkE2 = null ;
    ArrayList<Sprite> Sprites = new ArrayList<Sprite>();

    TileMap tmap = new TileMap();	// Our tile map, note that we load it in init()
    
    long coinCollection;         			// The score will be the total time elapsed since a crash
    private int mouseclk = 0;
    private int clkx = 0;
    private int clky = 0;

    /**
	 * The obligatory main method that creates
     * an instance of our class and starts it running
     * 
     * @param args	The list of parameters this program might use (ignored)
     * @throws InterruptedException 
     * @throws LineUnavailableException 
     * @throws UnsupportedAudioFileException 
     * @throws IOException 
     */
    public static void main(String[] args) {

        Game gct = new Game();
        gct.init("map.txt");
        // Start in windowed mode with the given screen height and width
        gct.run(false,screenWidth,screenHeight);
    }

    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers.
     * 
     * This shows you the general principles but you should create specific
     * methods for setting up your game that can be called again when you wish to 
     * restart the game (for example you may only want to load animations once
     * but you could reset the positions of sprites each time you restart the game).
     *  
     */
    public void init(String map) 
    {         
        
     Sprite s;
        // Load the tile map and print it out so we can check it is valid
        tmap.loadMap("maps", map);
        
        setSize(tmap.getPixelWidth()/4, tmap.getPixelHeight());
        setVisible(true);
        
        	//All of the animations that will be used in the program
			
        // Create a set of background sprites that we can 
        // rearrange to give the illusion of motion               
        idleRight = new Animation();
        idleRight.loadAnimationFromSheet("images/soldier/Idle.png", 5, 1, 60);
        
        idleLeft = new Animation();
        idleLeft.loadAnimationFromSheet("images/soldier/Idle.png", 5, 1, 60);        
        walk = new Animation();
        walk.loadAnimationFromSheet("images/soldier/Walk.png", 8, 1, 60);
        
        walkBack = new Animation();
        walkBack.loadAnimationFromSheet("images/soldier/WalkBack.png",8,1,60);
        dying = new Animation();
        dying.loadAnimationFromSheet("images/soldier/Dead.png",4,1,60);
        
        jump = new Animation();
        jump.loadAnimationFromSheet("images/soldier/Jump.png",7,1,60);
        
                
        enemywalkE1 = new Animation();
        enemywalkE1.loadAnimationFromSheet("images/soldier/WalkforwardE1.png", 8,1,60);
        enemywalkE2 = new Animation();
        enemywalkE2.loadAnimationFromSheet("images/soldier/WalkforwardE1Left.png",8,1,60);
        
        // Initialise the player with an animation and the enemies with a starting sprite
        player = new Sprite(idleRight);
        enemyOne = new Sprite(enemywalkE1);
        enemyTwo = new Sprite(enemywalkE1);
      
        backgroundOne = loadImage("images/Background_01.png");
        backgroundTwo = loadImage("images/Background_02.png");
        if(level == 1)
        	{themeMusic();}
        //prevent the audio being initialized multiple times
        //looping over and over again
        //Initialisation of the screens to be used
        menu = new Menu();
        dead = new Dead();
        help = new Help();
        finished = new Finished();
        
        
        // Create 2 enemies at random positions off the screen
        // to the right
      
        
        initialiseGame();
        
        Sprites.add(enemyOne);
        Sprites.add(enemyTwo);
        Sprites.add(player);
        System.out.println(tmap);
        	}
//Theme music to be played from a MIDI source
    //can play midi files but midi files sound awful so wav file
    public void themeMusic() 
    {
    	Sound backgroundMusic = new Sound("sounds/moonshine.wav");
    	backgroundMusic.playMusic("sounds/moonshine.wav");
    	if(level == 1) 
    	{
    		
    	}
    	if(level == 2)
    	{
    		
    	}
    }
    //If the game is not over play on
    public void initialiseGame()
    {
    	if(level == 1)
    	{
    		tmap.loadMap("maps", "map.txt");
    		spawnX = 100;
    		spawnY = 100;
    		 player.setX(spawnX); //set the spawns
             player.setY(spawnY);
             enemyOne.setX(547); 
             enemyOne.setY(224);
             enemyTwo.setX(800); 
             enemyTwo.setY(180);
             enemyOne.setBorderLeft(520);
              enemyOne.setBorderRight(583);
             enemyTwo.setBorderLeft(711);
             enemyTwo.setBorderRight(890);
             
        player.show();
        enemyOne.show();
        enemyTwo.show();
    	}
    	
    	if(level == 2)
    	{	
    		spawnY = 80;
    		tmap.loadMap("maps", "map2.txt");
    		 player.setX(spawnX); // get x & y coordinates of this tile
             player.setY(spawnY);
               enemyOne.setX(240); // get x & y of the sprite
             enemyOne.setY(80);
             enemyTwo.setX(650); // get x & y coordinates of the sprite
             enemyTwo.setY(80);
             enemyOne.setBorderLeft(235);
             enemyOne.setBorderRight(326);
            enemyTwo.setBorderLeft(611);
            enemyTwo.setBorderRight(681);
             
             player.show();
             enemyOne.show();
             enemyTwo.show();
                        
    	}
    	
    }
    //Simple implementation of paralax error the example on the  first screen is better than the secon largely due to the pattern i
    //Og the chosen file
    public int parallax(Image background,int scrollSpeed)
    {
    	int offsetX = screenWidth / 2 - Math.round(player.getX()) - tmap.getMapWidth();
        offsetX = Math.min(offsetX,0);
        offsetX = Math.max(offsetX, screenWidth - tmap.getPixelWidth());
        
        
        scrollSpeed = offsetX * (screenWidth + background.getWidth(null)) / (screenWidth * scrollSpeed - tmap.getMapWidth());
        
        return scrollSpeed;		
    }
    
    //resets the player based on the spawn position
    public void respawn() 
    {
    	coinCollection =0;
    	playerLives--;
    	Sound damage = new Sound("sounds/Damaged.wav"); // load damage sound effect
        damage.start(); // start thread
       player.setAnimation(dying);
    	if(playerLives > 0) 
    	{
    		initialiseGame();
    	}
    	else if(playerLives <= 0)
    	{
    	  	Sound gameFinished = new Sound("sounds/dsJingle.wav"); // Load level complete sound
            gameFinished.start();
            gameOver = true;
   
    	}
    	
    	
    }
    /**
     * Draw the current state of the game. Note the sample use of
     * debugging output that is drawn directly to the game screen.
     * @param graphic 
     */
    public void draw(Graphics2D g)
    {    	
    	// Be careful about the order in which you draw objects - you
    	// should draw the background first, then work your way 'forward'

    	// First work out how much we need to shift the view in order to
    	// see where the player is. To do this, we adjust the offset so that
        // it is relative to the player's position along with a shift
        int xo = -(int)player.getX() + 200;
        int yo = -(int)player.getY() + 250; 
        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        if(Status == STATE.GAME)
        {
        	ArrayList<Sprite> sprites = new ArrayList<>();
        	sprites.add(enemyOne);
        	sprites.add(enemyTwo);
        
        if(backgroundOne == null || backgroundTwo == null )
        {
        g.setColor(Color.blue);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawString("loading please wait", xo, yo);
        }
        if(level == 1) 
        {
        	levelOne = parallax(backgroundOne,30);
            g.drawImage(backgroundOne,levelOne,0,null);
            enemyOne.draw(g);
            enemyTwo.draw(g);
            

        }
        else if(level == 2)
        {
            levelTwo = parallax(backgroundTwo,20);
            g.drawImage(backgroundOne,levelTwo,0,null);
            enemyOne.draw(g);
            enemyTwo.draw(g);
        }
        // Apply offsets to tile map and draw  it
        tmap.draw(g,xo,yo); 

        // Apply offsets to player and draw 
        player.setOffsets(xo, yo);
        player.draw(g);
         
        	//Apply offsets to the enemy sprites and draw
        for (Sprite s : sprites)
        {
            s.setOffsets(xo, yo); // set the offsets for each sprite
            checkOnScreen(g, s, xo, yo); // check if they are on screen, drawing them only if they are (saves resources)
        }

        
        //Coins collected and plaer lives appear on screen
        String msg = String.format("Score: %d", coinCollection);
        String lvs = String.format("Lives Remaining: %d", playerLives);
        g.setColor(Color.darkGray);
        g.drawString(msg, getWidth() - 100, 50);
        g.drawString(lvs, getWidth() - 150, 80);        
        if (debug)
        {
        	// When in debug mode, you could draw borders around objects
            // and write messages to the screen with useful information.
            // Try to avoid printing to the console since it will produce 
            // a lot of output and slow down your game.
            tmap.drawBorder(g, xo, yo, Color.black);

            g.setColor(Color.red);
        	player.drawBoundingBox(g);
        		
        	g.drawString(String.format("Player: %.0f,%.0f", player.getX(),player.getY()),
        								getWidth() - 100, 70);
        }
        }
        else if (Status == STATE.DEAD)
        {
        	this.addMouseListener(new Dead());
        	dead.draw(g);
        }
        else if (Status == STATE.HELP) // If in the 'Help' state
        {
            this.addMouseListener(new Help());
            help.draw(g);
        }
        else if (Status == STATE.FINISHED) // If in the 'Complete' state
        {
            this.addMouseListener(new Finished());
            finished.draw(g);
        }
        else if (Status == STATE.MENU) // If in the 'Menu' state
        {
            this.addMouseListener(new Menu()); // Add a mouse listener for this menu
            menu.draw(g); // call the render method for this menu
        }
        
    }

    /**
     * Update any sprites and check for collisions
     * 
     * @param elapsed The elapsed time between this call and the previous call of elapsed
     */    
    public void update(long elapsed)
    {
    	
        // Make adjustments to the speed of the sprite due to gravity
    		ArrayList<Sprite> sprites = new ArrayList<>();
            sprites.add(player);
            sprites.add(enemyOne);
            sprites.add(enemyTwo);
            for(Sprite s: sprites)
            {
            	s.setVelocityY(s.getVelocityY() + (gravity * elapsed));
            }
        spriteMovement(elapsed); 
        player.update(elapsed);
        // Then check for any collisions that may have occurred
        handleScreenEdge(player, tmap, elapsed);
        checkTileCollision(player, tmap,elapsed); 
        // Update animations and positions
        for (Sprite s : sprites)
        {
            s.update(elapsed);
        }

        // Check for tile map collisions
        for (Sprite s : sprites)
        {
            checkTileCollision(s, tmap,elapsed);
        }
        // Check for sprite collisions
        collisionBetweenSprites();
    
    	if(playerLives <= 0)
    	{
    		player.setX(spawnX);
			player.setY(spawnY);
    		Game.Status = Game.STATE.DEAD;
    		
    	}
    }
    	
    
    public void checkOnScreen(Graphics2D g, Sprite s, int xo, int yo)
    {
        Rectangle rect = (Rectangle) g.getClip(); // Create a rectangle around the edges of the screen
        int xc, yc; // variables to register the position of the

        // get the x and y position of the sprite
        xc = (int) (xo + s.getX());
        yc = (int) (yo + s.getY());

        if (rect.contains(xc, yc)) // if the sprite's coordinates are within the rectangle border
        {
            s.show(); // show the sprite
            s.draw(g); // draw them to the screen
        }
        else
        {
            s.hide(); // hide the sprite
        }
    }
    
    /**
     * Checks and handles collisions with the edge of the screen. You should generally
     * use tile map collisions to prevent the player leaving the game area. This method
     * is only included as a temporary measure until you have properly developed your
     * tile maps.
     * 
     * @param s			The Sprite to check collisions for
     * @param tmap		The tile map to check 
     * @param elapsed	How much time has gone by since the last call
     */
    public void handleScreenEdge(Sprite s, TileMap tmap, long elapsed)
    {
    	// This method just checks if the sprite has gone off the bottom screen.
    	// Ideally you should use tile collision instead of this approach
    	
    	float difference = s.getY() + s.getHeight() - tmap.getPixelHeight();
        if (difference > 0)
        {
        	// Put the player back on the map according to how far over they were
        	s.setY(tmap.getPixelHeight() - s.getHeight() - (int)(difference)); 
        	
        }
    }
     private void collisionBetweenSprites() 
    {
    	
             // Add enemies to ArrayList for easier processing
             ArrayList<Sprite> enemies = new ArrayList<>();
             enemies.add(enemyOne);
             enemies.add(enemyTwo);
             // set Collision back to false;
             boolean collision = false;

             for (Sprite enemy : enemies)
             {
                 if (boundingBoxCollision(player, enemy)) // if the player and enemy bounding circles collide..
                 {
                     if (player.getVelocityY() > 0.2f) // if the player is "landing" on the enemy
                     {
                         Sound enemyDeath = new Sound("sounds/defeat.wav"); // play the enemy death sound
                         enemyDeath.start();
                         enemy.stop(); // stop their movement
                         enemy.hide(); // hide them
                         enemy.setX(900); // move them out of bounds
                         enemy.setY(900);
                         
                         
                     }
                     else // if the enemy walked into the player, or they jumped into it
                     {
                         collision = true; // set collided to true
                     }
                 }
             }

             if (collision)
             {
                 
                 // if this collision would kill the player
                	 player.stop();
                	 respawn(); // decrement remaining life and check for lives remaining
                    collision = false;
                 }

             }   	
     
    /**
     * Override of the keyPressed event defined in GameCore to catch our
     * own events
     * 
     *  @param e The event that has been generated
     */
    public void keyPressed(KeyEvent e) 
    { 
    	int key = e.getKeyCode();
    	
		switch (key)
		{
			case KeyEvent.VK_SPACE :jumping = true; break;
			case KeyEvent.VK_RIGHT  : moveRight = true; break;
			case KeyEvent.VK_LEFT  : moveLeft = true; break;
			case KeyEvent.VK_S 		: Sound s = new Sound("sounds/caw.wav"); 
									  s.start();
									  break;
			case KeyEvent.VK_ESCAPE : stop(); break;
			case KeyEvent.VK_B 		: debug = !debug; break; // Flip the debug state
			default :  break;
		}
    
    }

    /** Use the sample code in the lecture notes to properly detect
     * a bounding box collision between sprites s1 and s2.
     * 
     * @return	true if a collision may have occurred, false if it has not.
     */
    public boolean boundingBoxCollision(Sprite s1, Sprite s2)
    { int dx, dy, min; // variables to calculate collision between 2 sprites
    dx = (int) (((int) s1.getX() + (s1.getWidth() - 0.75) / 2) - ((int) s2.getX() + (s2.getWidth() -0.75)/ 2)); // get the x distance between the centre point of sprite one and sprite two
    dy = (int) (((int) s1.getY() + (s2.getHeight() - 0.75 )/ 2) - ((int) s2.getY() + (s2.getHeight() -0.75) / 2)); // get the y distance between the centre point of sprite one and sprite two
    min = s2.getWidth() / 2 + s2.getWidth() / 2; // take the width of both sprites and divide them by two
    return (((dx * dx) + (dy * dy)) < (min * min)); // return true if the bounding circles overlap
       	
    }
    
    /**
     * Check and handles collisions with a tile map for the
     * given sprite 's'. Initial functionality is limited...
     * 
     * @param s			The Sprite to check collisions for
     * @param tmap		The tile map to check 
     */

    public void checkTileCollision(Sprite s, TileMap tmap ,long elapsed)
    {

    	// Gets the value for the right hand side of a tile
    	  int tileX = (int) (s.getX() / tmap.getTileWidth());
          //the y position of the tile (in tiles)
          int tileY = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight());

          int yc = tmap.getTileYC(tileX, tileY) - tmap.getTileHeight();

          if (tmap.getTileChar(tileX, tileY) == 'g' || tmap.getTileChar(tileX, tileY) == 'r' || tmap.getTileChar(tileX, tileY) == 'o' ||
          		tmap.getTileChar(tileX, tileY) == 'u' || tmap.getTileChar(tileX, tileY) == 'n' ||
          		tmap.getTileChar(tileX, tileY) == 'd') // If touching ground tile
          {
              if (s.getVelocityY() > 0) // resets Y velocity to prevent falling through the ground
              {
                  s.setVelocityY(0);
              }
              s.setY((float) (tileY * tmap.getTileHeight()) - s.getHeight()); // Set the player's Y position to just above the bottom of the tile they're on
              if (s.equals(player)) // reset variables to allow the player to jump again
              {
                  jumpCount = 0;
                  falling = false;
              }
          }
         
          checkxyCollisions(s,elapsed);
          
           if(tmap.getTileChar(tileX,tileY -1) == 'Z' && s.equals(player))
          {
        	  Sound damaged = new Sound("sounds/Damaged.wav");
        	  damaged.start(); // Thread runs
        	  respawn();
        	  
        			  
          }
          
          
          if (tmap.getTileChar(tileX,tileY -1) == '1' && s.equals(player) ) 
        	  	  
          {
              Sound collect = new Sound("sounds/coinCollected.wav"); // Load collect sound
              collect.start(); // Run thread
              coinCollection++; // increment gems collected by the appropriate value
              tmap.setTileChar('.', tileX, tileY - 1); // coin replaced with a space
          }
          
          
          
          
          if (tmap.getTileChar(tileX, tileY - 1) == '*' && s.equals(player))
          {
        	  player.stop();
  			if (level == 1) {
  				level = 2;

  				newLevel(s);
  				// If the player has completed the second level, level gets set to one and the
  				// nextLevel method gets called
  			} else if (level == 2) {
  				level = 3;

  				Game.Status = Game.STATE.FINISHED;
  			}
  			System.out.println("Level " + level);
          }
          }
    
    public void enemySprites(Sprite s, long elapsed) 
    { 
    	// Get position of tile above the sprite
        xTop = (int) ((s.getX() / tmap.getTileWidth()) + 0.5);
        yTop = (int) (s.getY() / tmap.getTileHeight());

        // Get position of tile below the sprite
        xBottom = (int) (s.getX() / tmap.getTileWidth() + 0.5);
        yBottom = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight());

        // Get position of tile to the right of the sprite
        xRight = (int) (s.getX() / tmap.getTileWidth() + 0.75);
        yRight = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight() - 0.75);

        // Get position of tile to the left of the player
        xLeft = (int) (s.getX() / tmap.getTileWidth() );
        yLeft = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight() - 0.75);

        // if the tile above is a 'ground' tile
        if ((tmap.getTileChar(xTop, yTop) == 'g' || tmap.getTileChar(xTop, yTop) == 'r' || tmap.getTileChar(xTop, yTop) == 'o' || tmap.getTileChar(xTop, yTop) == 'u' || tmap.getTileChar(xTop, yTop) == 'd' || tmap.getTileChar(xTop, yTop) == 'd' ) && s.getVelocityY() > 0) {
            s.setVelocityY(0); // stop the sprite
                    }
        // if the tile to the right is a wall tile
        while ((tmap.getTileChar(xRight, yRight) == 'g' || tmap.getTileChar(xRight, yRight) == 'r' || tmap.getTileChar(xRight, yRight) == 'o' || tmap.getTileChar(xRight, yRight) == 'u' || tmap.getTileChar(xRight, yRight) == 'n' || tmap.getTileChar(xRight, yRight) == 'd' || tmap.getTileChar(xRight, yRight) == 's') && s.getVelocityX() > 0) {
            s.setVelocityX(0); // stop sprite
            s.setX(xRight * tmap.getTileWidth() - s.getImage().getWidth(null));
            s.setVelocityY(0);
        }
        // if the tile to the left is a wall tile
        while ((tmap.getTileChar(xLeft, yLeft) == 'g' || tmap.getTileChar(xLeft, yLeft) == 'r' || tmap.getTileChar(xLeft, yLeft) == 'o' || tmap.getTileChar(xLeft, yLeft) == 'u' || tmap.getTileChar(xLeft, yLeft) == 'n' || tmap.getTileChar(xLeft, yLeft) == 'd' || tmap.getTileChar(xLeft, yLeft) == 's') && s.getVelocityX() < 0) {
            s.setVelocityX(0); // stop sprite
            s.setX(xLeft * tmap.getTileWidth() + tmap.getTileWidth());
            s.setVelocityY(0);
        }
        // if the tile below is a 'ground' tile
        if (tmap.getTileChar(xBottom, yBottom) == 'g' || tmap.getTileChar(xBottom, yBottom) == 'r' || tmap.getTileChar(xBottom, yBottom) == 'o' || tmap.getTileChar(xBottom, yBottom) == 'u' || tmap.getTileChar(xBottom, yBottom) == 'n' || tmap.getTileChar(xBottom, yBottom) == 'd') {
            s.setVelocityY(0); // stops the sprite
            
        }
     	 
    
    
    }
    
    
    public void checkxyCollisions(Sprite s, long elapsed) 
    {

    	// Get position of tile above the sprite
        xTop = (int) ((s.getX() / tmap.getTileWidth()) + 0.5);
        yTop = (int) (s.getY() / tmap.getTileHeight());

        // Get position of tile below the sprite
        xBottom = (int) (s.getX() / tmap.getTileWidth() + 0.5);
        yBottom = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight());

        // Get position of tile to the right of the sprite
        xRight = (int) (s.getX() / tmap.getTileWidth() + 0.75);
        yRight = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight() - 0.75);

        // Get position of tile to the left of the player
        xLeft = (int) (s.getX() / tmap.getTileWidth() );
        yLeft = (int) ((s.getY() + s.getHeight()) / tmap.getTileHeight() - 0.75);

        // if the tile above is a 'ground' tile
        if ((tmap.getTileChar(xTop, yTop) == 'g' || tmap.getTileChar(xTop, yTop) == 'r' || tmap.getTileChar(xTop, yTop) == 'o' || tmap.getTileChar(xTop, yTop) == 'u' || tmap.getTileChar(xTop, yTop) == 'd' || tmap.getTileChar(xTop, yTop) == 'd' ) && s.getVelocityY() > 0) {
            s.setVelocityY(0);
            s.shiftY(-2);// stop the sprite
                    }
        // if the tile to the right is a wall tile
        while ((tmap.getTileChar(xRight, yRight) == 'g' || tmap.getTileChar(xRight, yRight) == 'r' || tmap.getTileChar(xRight, yRight) == 'o' || tmap.getTileChar(xRight, yRight) == 'u' || tmap.getTileChar(xRight, yRight) == 'n' || tmap.getTileChar(xRight, yRight) == 'd' || tmap.getTileChar(xRight, yRight) == 's') && s.getVelocityX() > 0) {
            s.setVelocityX(0); // stop sprite
            s.setX(xRight * tmap.getTileWidth() - s.getImage().getWidth(null));
            s.setVelocityY(0);
        }
        // if the tile to the left is a wall tile
        while ((tmap.getTileChar(xLeft, yLeft) == 'g' || tmap.getTileChar(xLeft, yLeft) == 'r' || tmap.getTileChar(xLeft, yLeft) == 'o' || tmap.getTileChar(xLeft, yLeft) == 'u' || tmap.getTileChar(xLeft, yLeft) == 'n' || tmap.getTileChar(xLeft, yLeft) == 'd' || tmap.getTileChar(xLeft, yLeft) == 's') && s.getVelocityX() < 0) {
            s.setVelocityX(0); // stop sprite
            s.setX(xLeft * tmap.getTileWidth() + tmap.getTileWidth());
            s.setVelocityY(0);
        }
        // if the tile below is a 'ground' tile
        if (tmap.getTileChar(xBottom, yBottom) == 'g' || tmap.getTileChar(xBottom, yBottom) == 'r' || tmap.getTileChar(xBottom, yBottom) == 'o' || tmap.getTileChar(xBottom, yBottom) == 'u' || tmap.getTileChar(xBottom, yBottom) == 'n' || tmap.getTileChar(xBottom, yBottom) == 'd') {
            s.setVelocityY(0); // stops the sprite
            s.shiftY(2); // 
        }
        
        //If the player ends up hitting a spike tyle
        if(tmap.getTileChar(xBottom, yBottom) == 'z' || tmap.getTileChar(xLeft,yLeft) == 'z' || tmap.getTileChar(xTop,yTop) == 'z' || tmap.getTileChar(xRight,yRight ) == 'Z')
        {
        	if (s.getVelocityY() > 0) // resets Y velocity to prevent falling through the ground
            {
                s.setVelocityY(0);
            }
     	  
     	  Sound damaged = new Sound("sounds/Damaged.wav");
     	  damaged.start(); // Thread runs
     	  respawn();
        }
        
    
    }
    public void newLevel(Sprite s) 
    {
    	if (level == 1) // if on level 1
        {
            // Load the tile map and print it out so we can check it is valid
            init("map.txt");
            initialiseGame();
        }
    
    	if (level == 2) // if on level 2
        {
            // Load the tile map and print it out so we can check it is valid
    		 init("map2.txt");
             initialiseGame();
        }
    	if(level == 3)
    	{
    		Game.Status = Game.STATE.FINISHED;
    	}
    	
    }
    
    public void spriteMovement(long elapsed) 
    {
    	
	player.setAnimationSpeed(0.3f);
    	
    	
    	if(falling = true)
    	{
    		player.setVelocityY(player.getVelocityY() +  (gravity * elapsed));
    		
    		
    	}
    	
    	 if (moveRight) // if moving right
         {
             // variables for calculating future position
             posX = player.getX() + player.getImage().getWidth(null);
             posY = player.getY() + player.getImage().getHeight(null) / 2;

             if (tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 'g' || tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 'r' 
            		 || tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 'u' || tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 'n' ||
            		 tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 'd' || tmap.getTileChar((int) (posX + 0.2f), (int) posY) == 's')
             {
                 player.setVelocityX(0);
             }
             else
             {
                 player.setVelocityX(0.1f); // move player to the right
                 player.setAnimation(walk);
             }
         }
    	 
    	 if(moveLeft) // if moving left
     	{         
              // variables for calculating future position
              posX = player.getX() + player.getImage().getWidth(null);
              posY = player.getY() + player.getImage().getHeight(null) / 2;

              if (tmap.getTileChar((int) (posX - 0.2f),(int) posY) == 'g' ||
            		  tmap.getTileChar((int) (posX - 0.2f), (int) posY) == 'r' || tmap.getTileChar((int) (posX - 0.2f), (int) posY) == 'o' 
            		  || tmap.getTileChar((int) (posX - 0.2f), (int) posY) == 'u' || tmap.getTileChar((int) (posX - 0.2f), (int) posY) == 'n' || tmap.getTileChar((int) (posX - 0.02f), (int) posY) == 'd')
              {
                  player.setVelocityX(0);
              }
              else
              {
                  player.setVelocityX(-0.1f); // move player to the left
                             
                 	  player.setAnimation(walkBack);
              }
          }
          
    if (jumping) {
		// if jumps go above two, the player can no longer jump, caps the player to only
		// be able to double jump
		if (jumpCount < 2) {
			// The player must be on the ground in order to jump
			if (player.getVelocityY() >= 0) {
				player.setVelocityY(-0.375f);
				player.setAnimation(jump);
				jumping = false;
				// Increments the jumps counter
				jumpCount++;
			}
		}
	}
    ArrayList<Sprite> enemies = new ArrayList<>();
    enemies.add(enemyOne);
    enemies.add(enemyTwo);

    for(Sprite enemy: enemies) 
    {
    if((enemy.getX() > enemy.getBorderLeft() && enemy.getVelocityX() < 0) || (enemy.getX() < enemy.getBorderRight() && enemy.getVelocityX() > 0))
	{
		enemy.setVelocityX( - enemy.getVelocityX());
	}
	if(enemy.getVelocityX() < 0)
	{
		enemy.setVelocityX(0.02f);
		enemy.setAnimation(enemywalkE1);
		
	}
	else 
	{
		enemy.setVelocityX(-0.02f);
		enemy.setAnimation(enemywalkE2);
	}
    
	enemy.update(elapsed);
    }
    
}
    
    	
    
    

    
    public void updateAnimation(Animation playerAnimation,float animationSpeed ,float playerVelocity)
    {
    	player.setAnimation(playerAnimation);
    	player.setAnimationSpeed(animationSpeed);
    	player.setVelocityX(playerVelocity);
    	
    }
    
    public void gameOver() 
    {
    	player.setVelocityX(0);
    	player.setVelocityY(0);
    	player.hide();
    }
    
	public void keyReleased(KeyEvent e) { 

		int key = e.getKeyCode();

		switch (key)
		{
		case KeyEvent.VK_SPACE : jumping = false;
		break;
		case KeyEvent.VK_ESCAPE : stop(); break;
		case KeyEvent.VK_RIGHT  : moveRight = false;
		updateAnimation(idleRight,0.2f,0);
		break;
		case KeyEvent.VK_LEFT  : moveLeft = false; 
		updateAnimation(idleLeft,-0.2f,0);
		break;
		default: break;
		}
	}
	
	public void mousePressed(MouseEvent e) 
	{
		mouseclk = e.getButton();
		
		clkx = e.getX();
		clky = e.getY();
		
	if(mouseclk == MouseEvent.BUTTON1)
	{
		if(clkx >= 270 && clkx <= 335 && clky >= 281 && clky <= 304  ) 
		{	
			init("map.txt");
		}
		if(clkx >= 415 && clkx <= 465 && clky >= 281 && clky <= 304);
		{
			stop();
		}
	}
	
	}

}


