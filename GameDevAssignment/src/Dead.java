import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;


public class Dead implements MouseListener {
		
		Image gameOver;
		Image quit;
		Image main;
	
		public void draw(Graphics g) 
		{
			if(Game.Status == Game.STATE.DEAD)
			{
			ImageIcon gameOverIcon = new ImageIcon("images/Buttons/GameOver.png");
			ImageIcon quitIcon = new ImageIcon("images/Buttons/Quit.png");
			ImageIcon mainIcon = new ImageIcon("images/Buttons/MainMenu.png");
			gameOver = gameOverIcon.getImage();
			quit = quitIcon.getImage();
			main = mainIcon.getImage();
			g.drawImage(gameOver,130,50,null);
			g.drawImage(quit,160,200,null);
			g.drawImage(main,160,250,null);
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			// Mouse press method will handle it 
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int mouseX = e.getX();
			int mouseY = e.getY();
			
			if(mouseX >= 160 && mouseX <= 340)
			{
				if(mouseY >= 200 && mouseY <= 240) 
				{ 
					Game.Status = Game.STATE.MENU;
				}
				if(mouseY >= 250 && mouseY <= 290)
				{
					System.exit(0);
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
