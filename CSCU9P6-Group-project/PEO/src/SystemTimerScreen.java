package PEO.src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

@SuppressWarnings({ "serial", "deprecation" })
public class SystemTimerScreen extends JFrame implements ActionListener, Observer {

	private SystemTimer timer;
	private JButton advanceMinutesButton, advanceDayButton, setButton;
	private JTextField now, day, minutes;

	public SystemTimerScreen(SystemTimer timer, int x, int y) {
		// Setting up the GUI
		timer.addObserver(this);
		this.timer = timer;
		timer.addObserver(this);
		
		setTitle("System Timer Screen");
		setLocation(x, y);
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		add(new JLabel("System Timer"));
		
		now = new JTextField(15);
		now.setEditable(false);
		now.setText(this.timer.getNow().toString());
		add(now);
		
		advanceMinutesButton = new JButton("Advance time by 5 minutes");
		advanceMinutesButton.addActionListener(this);
		add(advanceMinutesButton);
		
		advanceDayButton = new JButton("Advance time by 1 day");
		advanceDayButton.addActionListener(this);
		add(advanceDayButton);
		
		add(new JLabel("Day:"));
		day = new JTextField();
		add(day);
		
		add(new JLabel("Minutes:"));
		minutes = new JTextField();
		add(minutes);
		
		setButton = new JButton("Set time");
		setButton.addActionListener(this);
		add(setButton);

		setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		now.setText(this.timer.getNow().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == advanceMinutesButton) {
			timer.advanceTime(5);
		} else if (e.getSource() == advanceDayButton) {
			timer.advanceDay(1);
		} else if (e.getSource() == setButton) {
			try {
				int setDay = Integer.parseInt(day.getText());
				int setTime = Integer.parseInt(minutes.getText());
				timer.setNow(setDay, setTime);
			} catch (NumberFormatException e1) {
				return;
			}

		}

	}

}
