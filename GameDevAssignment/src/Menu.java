import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Menu implements MouseListener {

	Image thumbnail;
    Image quit;
    Image help;
    Image playGame;
	public void draw(Graphics g) 
	{
		if(Game.Status == Game.STATE.MENU)
		{
		ImageIcon thumbnailIcon = new ImageIcon("images/Buttons/Warrior.png");
		ImageIcon quitIcon = new ImageIcon("images/Buttons/Quit.png");
		ImageIcon helpIcon = new ImageIcon("images/Buttons/Help.png");
		ImageIcon playGameIcon = new ImageIcon("images/Buttons/Play.png");
		thumbnail = thumbnailIcon.getImage();
		quit = quitIcon.getImage();
		help = helpIcon.getImage();
		playGame = playGameIcon.getImage();
		g.drawImage(thumbnail,170,50,null);
		g.drawImage(playGame,160,100,null);
		g.drawImage(quit,160,200,null);
		g.drawImage(help,160,300,null);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int mouseX = e.getX();
		int mouseY = e.getY();
		
		if(mouseX >= 160 && mouseX <= 270)
		{
			if(mouseY >= 100 && mouseY <= 155) 
			{ 
				Game.Status = Game.STATE.GAME;
			}
			if(mouseY >= 200 && mouseY <= 255)
			{
				 System.exit(0); // Exit game
			}
			 if (mouseY >= 300 && mouseY<= 355)
             {
                 //Pressed Quit
                
                 Game.Status = Game.STATE.HELP;
             }
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
