
package PEO.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the interface used by parents. It is a Boundary class. In terms of
 * the MVC pattern, it is also a View and a Controller.
 */
@SuppressWarnings({ "deprecation", "serial" })
public class ParentScreen extends JFrame implements ActionListener, Observer {
	private JTabbedPane contentPane;
	private JPanel loginPanel, messagesPanel, appointmentsPanel, meetingPanel;

	private JLabel[] bookingLabels;
	private JTextField pupilName, meetingTeacher;
	private JTextArea messagesDisplay, meetingDisplay;
	private JScrollPane messagesScroll;

	private ButtonGroup[] appointmentGroups;
	private JButton login, viewNotifications, joinMeeting, leaveMeeting, bookManually, bookAutomatically,
			refreshMeeting;

	private PEOSystemDatabase database;
	private Pupil loggedInUser;
	private ArrayList<Teacher> taughtBy;
	private Teacher currentMeeting;

	public ParentScreen(PEOSystemDatabase database, int x, int y) {
		// Setting up the GUI
		this.database = database;
		database.addObserver(this);
		setTitle("Parent Screen");
		setLocation(x, y);
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JTabbedPane();
		loginPanel = createLoginPanel();

		contentPane.addTab("Login", loginPanel);
		setContentPane(contentPane);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Log in":
			logIn(pupilName.getText());
			break;
		case "Refresh Notifications":
			refreshNotifications();
			break;
		case "Book selected Appointments":
			bookSelectedAppointments();
			break;
		case "Book Appointments Automatically":
			bookAutomatically();
			break;
		case "Join Meeting":
			Teacher target = database.getTeacher(meetingTeacher.getText());
			joinMeeting(target);
			break;
		case "Leave Meeting":
			if (currentMeeting != null) {
				currentMeeting.getMyMeetingRoom().leave(loggedInUser);
				currentMeeting = null;
			}
			meetingDisplay.setText("");
			break;
		case "Refresh Meeting":
			refreshMeeting();
			break;
		default:
			System.out.println("BUTTON NOT IMPLEMENTED CORRECTLY ERROR");
			break;
		}
	}

	public void refreshMeeting() {
		if (currentMeeting == null) {
			return;
		}
		if (currentMeeting.getMyMeetingRoom().getCurrentMeetingBookedFor() == loggedInUser) {
			meetingDisplay.setText("Attending meeting");
		} else if (currentMeeting.getMyMeetingRoom().getParentsWaitingInLobby().contains(loggedInUser)) {
			meetingDisplay.setText("Waiting in lobby...");
		} else {
			meetingDisplay.setText("You have no future appointments scheduled with this teacher");
			currentMeeting.getMyMeetingRoom().leave(loggedInUser);
			currentMeeting = null;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (loggedInUser != null) {
			messagesPanel = createMessagesPanel();
			appointmentsPanel = createAppointmentsPanel();
			meetingPanel = createMeetingPanel();
			contentPane.removeAll();
			contentPane.addTab("View Messages", messagesPanel);
			contentPane.addTab("Manage appointments", appointmentsPanel);
			contentPane.addTab("Meet a Teacher", meetingPanel);
			refreshMeeting();
			refreshNotifications();
		}
		if (arg != null && arg.equals("Notification") && messagesPanel != null) {
			refreshNotifications();
		}
	}

	private JPanel createAppointmentsPanel() {
		JPanel appointmentsPanel = new JPanel();
		appointmentsPanel.setLayout(new BoxLayout(appointmentsPanel, BoxLayout.PAGE_AXIS));
		taughtBy = loggedInUser.getTaughtBy();
		if (taughtBy.size() == 0) {
			appointmentsPanel.add(new JLabel("Pupil has no teachers, so no appointments can be made"));
			return appointmentsPanel;
		}

		appointmentGroups = new ButtonGroup[taughtBy.size()];
		if (bookingLabels == null || database.getSystemStatus() == 1) {
			bookingLabels = new JLabel[taughtBy.size()];
		}
		for (int i = 0; i < taughtBy.size(); i++) {
			JPanel smallerPanel = new JPanel();

			Teacher currentTeacher = taughtBy.get(i);
			smallerPanel.add(new JLabel(currentTeacher.toString()));
			if (bookingLabels[i] == null || database.getSystemStatus() == 1) {
				bookingLabels[i] = new JLabel("Current appointment: None.");
			}
			smallerPanel.add(bookingLabels[i]);
			ArrayList<Integer> ts = database.getAllAvailableTimeslots(currentTeacher.getName());
			appointmentGroups[i] = new ButtonGroup();
			JRadioButton rb = new JRadioButton("none");
			rb.setSelected(true);
			rb.setActionCommand("none");
			appointmentGroups[i].add(rb);
			smallerPanel.add(rb);

			for (int j = 0; j < ts.size(); j++) {
				rb = new JRadioButton(
						currentTeacher.getMyMeetingRoom().getTimeslot(ts.get(j)).getStartTime().toString());
				rb.setActionCommand(ts.get(j).toString());
				appointmentGroups[i].add(rb);
				smallerPanel.add(rb);
				if (database.getSystemStatus() != 2) {
					rb.setEnabled(false);
				}
			}
			appointmentsPanel.add(smallerPanel);
		}
		bookManually = new JButton("Book selected Appointments");
		bookManually.addActionListener(this);
		bookAutomatically = new JButton("Book Appointments Automatically");
		bookAutomatically.addActionListener(this);
		if (database.getSystemStatus() != 2) {
			bookManually.setEnabled(false);
			bookAutomatically.setEnabled(false);
		}
		appointmentsPanel.add(bookManually);
		return appointmentsPanel;
	}

	private JPanel createLoginPanel() {
		JPanel loginPanel = new JPanel();
		loginPanel.add(new JLabel("Not currently logged in"));
		loginPanel.add(new JLabel("Pupil Name: "));
		pupilName = new JTextField(20);
		loginPanel.add(pupilName);
		login = new JButton("Log in");
		loginPanel.add(login);
		login.addActionListener(this);
		return loginPanel;
	}

	private JPanel createMeetingPanel() {
		JPanel meetingPanel = new JPanel();

		meetingPanel.add(new JLabel("Teacher:"));

		meetingTeacher = new JTextField(20);
		meetingPanel.add(meetingTeacher);

		joinMeeting = new JButton("Join Meeting");
		meetingPanel.add(joinMeeting);
		joinMeeting.addActionListener(this);

		leaveMeeting = new JButton("Leave Meeting");
		meetingPanel.add(leaveMeeting);
		leaveMeeting.addActionListener(this);

		refreshMeeting = new JButton("Refresh Meeting");
		meetingPanel.add(refreshMeeting);
		refreshMeeting.addActionListener(this);

		if (database.getSystemStatus() != 4) {
			joinMeeting.setEnabled(false);
			leaveMeeting.setEnabled(false);
			refreshMeeting.setEnabled(false);
		}

		meetingDisplay = new JTextArea();
		meetingPanel.add(meetingDisplay);
		meetingDisplay.setEditable(false);

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
		if (database.hasPupil(name)) {
			JOptionPane.showMessageDialog(this, "Login Successful");
			loggedInUser = database.getPupil(name);
			messagesPanel = createMessagesPanel();
			appointmentsPanel = createAppointmentsPanel();
			meetingPanel = createMeetingPanel();
			contentPane.removeTabAt(0);
			contentPane.addTab("View Messages", messagesPanel);
			contentPane.addTab("Manage appointments", appointmentsPanel);
			contentPane.addTab("Meet a Teacher", meetingPanel);
			refreshMeeting();

		} else {
			JOptionPane.showMessageDialog(this, "Unable to log in, please check typed name matches school records",
					"Login Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void refreshNotifications() {
		messagesDisplay.setText("");
		ArrayList<Notification> notifs = database.viewNotifications(loggedInUser.getName(), false);
		for (Notification n : notifs) {
			messagesDisplay.append(n.toString() + "\n");
		}
	}

	private void bookSelectedAppointments() {
		boolean successful = true;
		ArrayList<String> selections = new ArrayList<String>();
		for (int i = 0; i < appointmentGroups.length; i++) {
			String actionCommand = appointmentGroups[i].getSelection().getActionCommand();
			if (selections.contains(actionCommand)) {
				JOptionPane.showMessageDialog(this, "Clashing appointments selected, please change selection",
						"Booking Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			selections.add(actionCommand);
		}
		for (int i = 0; i < appointmentGroups.length; i++) {
			String actionCommand = selections.get(i);
			String currentTeacher = taughtBy.get(i).getName();
			if (actionCommand.equals("none")) {
				loggedInUser.removeBookedWith(currentTeacher);
				bookingLabels[i].setText("Current appointment: None");
			} else {
				int selectedTime = Integer.parseInt(actionCommand);
				if (loggedInUser.getBookedWith().containsKey(currentTeacher)) {
					if (loggedInUser.getBookedWith().get(currentTeacher) != selectedTime) {
						database.cancelAppointmentManually(loggedInUser.getName(), currentTeacher,
								loggedInUser.getBookedWith().get(currentTeacher));
					}
				}
				successful = database.bookAppointmentManually(loggedInUser.getName(), currentTeacher, selectedTime)
						&& successful;
				bookingLabels[i].setText(
						"Current appointment: " + taughtBy.get(i).getMyMeetingRoom().getTimeslot(i).getStartTime());
				loggedInUser.putBookedWith(currentTeacher, selectedTime);
			}
		}
		if (successful) {
			JOptionPane.showMessageDialog(this, "Selected Appointments were booked", "Booking Confirmed",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Something went wrong", "Booking Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void bookAutomatically() {
		ArrayList<Integer> usedSlots = new ArrayList<Integer>();
		for (int x = 0; x < taughtBy.size(); x++) {
			Teacher teacher = taughtBy.get(x);
			int freeTimeslot = database.getFirstAvailableTimeslotExcludingTimeslots(teacher.getName(), usedSlots);
			if (freeTimeslot == -1) {
				JOptionPane.showMessageDialog(this,
						"An error occured, please check notifications for information on which appointments were successfully booked",
						"Booking Error", JOptionPane.ERROR_MESSAGE);
				break;
			} else {
				usedSlots.add(freeTimeslot);
			}
		}
		if (taughtBy.size() == usedSlots.size()) {
			for (int x = 0; x < taughtBy.size(); x++) {
				database.bookAppointmentManually(loggedInUser.getName(), taughtBy.get(x).getName(), usedSlots.get(x));
				loggedInUser.putBookedWith(taughtBy.get(x).getName(), x);
				bookingLabels[x].setText(
						"Current appointment: " + taughtBy.get(x).getMyMeetingRoom().getTimeslot(x).getStartTime());
			}
		}
	}

	private void joinMeeting(Teacher target) {
		if (target == null) {
			meetingDisplay.setText("Teacher doesn't exist");
		} else {
			if (currentMeeting != null) {
				if (currentMeeting == target) {
					return;
				} else {
					currentMeeting.getMyMeetingRoom().leave(loggedInUser);
					currentMeeting = null;
				}
			}
			currentMeeting = target;
			MeetingRoom room = target.getMyMeetingRoom();
			if (!room.isOpen()) {
				meetingDisplay.setText("Teacher's meeting room is closed");
				return;
			}
			room.join(loggedInUser);
			if (room.getCurrentMeetingBookedFor() == loggedInUser) {
				meetingDisplay.setText("Attending meeting");
			} else if (room.getParentsWaitingInLobby().contains(loggedInUser)) {
				meetingDisplay.setText("Waiting in lobby...");
			} else {
				meetingDisplay.setText("You have no future appointments scheduled with this teacher");
			}
		}
	}

}