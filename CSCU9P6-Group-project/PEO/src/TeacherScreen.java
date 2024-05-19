
package PEO.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the interface used by teachers. It is a Boundary class. In terms of
 * the MVC pattern, it is also a View and a Controller.
 */
@SuppressWarnings({ "deprecation", "serial" })
public class TeacherScreen extends JFrame implements ActionListener, Observer {
	JTabbedPane contentPane;
	private JPanel loginPanel, messagesPanel, meetingPanel, availabilityPanel, appointmentsPanel;
	private JTextArea messagesDisplay, meetingDisplay;
	private JScrollPane messagesScroll;

	private JButton login, viewNotifications, refreshMeeting;
	private JTextField teacherName, meetingPupil;

	private PEOSystemDatabase database;
	private Teacher loggedInUser;

	public TeacherScreen(PEOSystemDatabase database, int x, int y) {
		// Setting up GUI
		this.database = database;
		database.addObserver(this);
		setTitle("Teacher Screen");
		setLocation(x, y);
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JTabbedPane();
		loginPanel = createLoginPanel();

		contentPane.add("Login", loginPanel);
		setContentPane(contentPane);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Log in":
			logIn(teacherName.getText());
			break;
		case "Refresh Notifications":
			refreshNotifications();
			break;
		case "Refresh Meeting":
			refreshMeeting();
			break;
		}
	}
	
	

	@Override
	public void update(Observable o, Object arg) {

		if (loggedInUser != null) {
			messagesPanel = createMessagesPanel();
			availabilityPanel = createAvailabilityPanel();
			meetingPanel = createMeetingPanel();
			appointmentsPanel = createAppointmentsPanel();
			contentPane.removeAll();
			contentPane.add("View Messages", messagesPanel);
			contentPane.add("Manage Availability", availabilityPanel);
			contentPane.add("Manage Appoinments", appointmentsPanel);
			contentPane.add("Meet a Parent", meetingPanel);
			refreshMeeting();
			refreshNotifications();
		}
		if (arg != null && arg.equals("Notification") && messagesDisplay != null) {
			refreshNotifications();
		}
		
	}

	private JPanel createAppointmentsPanel() {
		JPanel appointmentsPanel = new JPanel();
		ArrayList<Timeslot> mySchedule = loggedInUser.getMyMeetingRoom().getSchedule();
		for (int i = 0; i < mySchedule.size(); i++) {
			Timeslot ts = mySchedule.get(i);
			if (ts.getBookedFor() != null) {
				appointmentsPanel.add(new JLabel(ts.getStartTime().toString()));
				appointmentsPanel.add(new JLabel(ts.getBookedFor().toString()));
				JButton b = new JButton("Cancel Appointment");
				b.setActionCommand(Integer.toString(i));
				b.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						database.cancelAppointmentManually(ts.getBookedFor().getName(), loggedInUser.getName(),
								Integer.parseInt(ae.getActionCommand()));
					}
				});
				appointmentsPanel.add(b);
				if (database.getSystemStatus() != 2) {
					b.setEnabled(false);
				}
			}

		}
		return appointmentsPanel;
	}

	private JPanel createAvailabilityPanel() {
		JPanel availabilityPanel = new JPanel();
		ArrayList<Timeslot> mySchedule = loggedInUser.getMyMeetingRoom().getSchedule();
			for (int i = 0; i < mySchedule.size(); i++) {
				JCheckBox isAvailable = new JCheckBox(mySchedule.get(i).getStartTime().toString());
				isAvailable.setSelected(mySchedule.get(i).isTeacherAvailable());
				isAvailable.setActionCommand(Integer.toString(i));
				if (database.getSystemStatus() != 1) {
					isAvailable.setEnabled(false);
				} else {
					isAvailable.setEnabled(true);
				}
				isAvailable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae) {
						database.switchAvailability(loggedInUser.getName(), Integer.parseInt(ae.getActionCommand()));
					}
					
				});
				availabilityPanel.add(isAvailable);
		}
		return availabilityPanel;
	}

	private JPanel createLoginPanel() {
		JPanel loginPanel = new JPanel();
		loginPanel.add(new JLabel("Not currently logged in"));
		loginPanel.add(new JLabel("Teacher Name: "));
		teacherName = new JTextField(20);
		loginPanel.add(teacherName);

		login = new JButton("Log in");
		loginPanel.add(login);
		login.addActionListener(this);
		return loginPanel;
	}

	private JPanel createMeetingPanel() {
		// TODO Auto-generated method stub
		JPanel meetingPanel = new JPanel();

		meetingPanel.add(new JLabel("Pupil:"));

		meetingPupil = new JTextField(20);
		meetingPupil.setEditable(false);
		meetingPanel.add(meetingPupil);
		meetingPupil.setEditable(false);

		refreshMeeting = new JButton("Refresh Meeting");
		meetingPanel.add(refreshMeeting);
		refreshMeeting.addActionListener(this);

		meetingDisplay = new JTextArea();
		meetingPanel.add(meetingDisplay);
		meetingDisplay.setEditable(false);
		
		if (database.getSystemStatus() != 4) {
			refreshMeeting.setEnabled(false);
		}

		return meetingPanel;
	}

	private JPanel createMessagesPanel() {
		JPanel messagesPanel = new JPanel();

		viewNotifications = new JButton("Refresh Notifications");
		messagesPanel.add(viewNotifications);
		viewNotifications.addActionListener(this);

		messagesDisplay = new JTextArea(5, 30);
		messagesScroll = new JScrollPane(messagesDisplay);
		messagesDisplay.setEditable(false);
		messagesPanel.add(messagesScroll);

		return messagesPanel;
	}

	private void logIn(String name) {
		if (database.hasTeacher(name)) {
			JOptionPane.showMessageDialog(this, "Login Successful");
			loggedInUser = database.getTeacher(name);
			loggedInUser.getMyMeetingRoom().addObserver(this);
			messagesPanel = createMessagesPanel();
			meetingPanel = createMeetingPanel();
			availabilityPanel = createAvailabilityPanel();
			appointmentsPanel = createAppointmentsPanel();

			contentPane.add("View Messages", messagesPanel);
			contentPane.add("Manage Availability", availabilityPanel);
			contentPane.add("Manage Appoinments", appointmentsPanel);
			contentPane.add("Meet a Parent", meetingPanel);
			refreshMeeting();
			contentPane.removeTabAt(0);
		} else {
			JOptionPane.showMessageDialog(this, "Unable to log in, please check typed name matches school records",
					"Login Error", JOptionPane.ERROR_MESSAGE);

		}
	}

	public void refreshMeeting() {
		if (!loggedInUser.getMyMeetingRoom().isOpen()) {
			meetingDisplay.setText("Meeting room is closed");
			meetingPupil.setText("None.");
		} else {
			MeetingRoom room = loggedInUser.getMyMeetingRoom();
			Timeslot slot = room.getTimeslot(room.getCurrentMeetingTimeslot());
			if (slot.getBookedFor() == null) {
				meetingDisplay.setText("Meeting room is open, no meeting scheduled.");
				meetingPupil.setText("None.");
			} else if (room.getParentPresent()) {
				meetingDisplay.setText("Meeting with " + slot.getBookedFor().getName());
				meetingPupil.setText(slot.getBookedFor().getName());
			} else {
				meetingDisplay.setText("Meeting scheduled with " + slot.getBookedFor().getName() + ", not present");
				meetingPupil.setText(slot.getBookedFor().getName());
			}
		}
	}
	
	private void refreshNotifications() {
		if (loggedInUser == null) {
			messagesDisplay.setText("Log in to view notifications");
		} else {
			ArrayList<Notification> notifs = database.viewNotifications(loggedInUser.getName(), true);
			messagesDisplay.setText("");
			for (Notification n : notifs) {
				messagesDisplay.append(n.toString() + "\n");
			}
		}
	}

}