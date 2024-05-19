import java.awt.*;
import java.awt.event.*;

import javax.swing.ImageIcon;

public class Finished implements MouseListener{

	Image finish;
	Image quit;
	Image main;
	
	public void draw(Graphics g)
	{
		if(Game.Status == Game.STATE.FINISHED)
		{

		ImageIcon finishIcon = new ImageIcon("images/Buttons/GameComplete.png");
		ImageIcon quitIcon = new ImageIcon("images/Buttons/Congratulations.png");
		ImageIcon mainIcon = new ImageIcon("Images/Buttons/MainMenu.png");
		finish = finishIcon.getImage();
		quit = quitIcon.getImage();
		main = mainIcon.getImage();
		g.drawImage(finish,140,75,null);
		g.drawImage(quit,170,225,null);
		g.drawImage(main,170,325,null);
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
		

		if(mouseX >= 170 && mouseX <= 370)
		{
			if(mouseY >= 325 && mouseY <=375) 
			{ 
				
				System.exit(0); // Exit game
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
