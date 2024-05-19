import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;
public class Help implements MouseListener {

	Image instructions;
	Image main;
	Image quit;
		
	public void draw(Graphics g)
	{
		if(Game.Status == Game.STATE.HELP) {
		ImageIcon instructionIcon = new ImageIcon("images/Buttons/Instructions.png");
		ImageIcon quitIcon = new ImageIcon("images/Buttons/Quit.png");
		ImageIcon mainIcon = new ImageIcon("images/Buttons/MainMenu.png");
		instructions = instructionIcon.getImage();
		quit = quitIcon.getImage();
		main = mainIcon.getImage();
		g.drawImage(instructions,140,75,null);
		g.drawImage(quit,160,225,null);
		g.drawImage(main,160,325,null);
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
		
		if(mouseX >= 160 && mouseX <= 370)
		{
			if(mouseY >= 225 && mouseY <=275) 
			{ 
				
				System.exit(0); // Exit game
			}
			if(mouseY >= 325 && mouseY <= 375)
			{
				Game.Status = Game.STATE.MENU;
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
