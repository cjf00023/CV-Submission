
package PEO.src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the interface used by the PEO system administrator. It is a Boundary
 * class. In terms of the MVC pattern, it is also a View and a Controller.
 */
@SuppressWarnings({ "deprecation", "serial" })
public class AdministratorScreen extends JFrame implements ActionListener, Observer {

	private JTabbedPane contentPane;
	private JPanel systemPanel, logsPanel, teachersPanel, pupilsPanel;
	private JTextArea systemDisplay, logsDisplay, teachersDisplay, pupilsDisplay;
	private JScrollPane systemScroll, logsScroll, teachersScroll, pupilsScroll;

	private JButton setTimes, setMeetingLength, setUpSystem, addPupil, editPupil, removePupil, addTeacher, editTeacher,
			removeTeacher, viewMeetingLog, viewMeetingLogByPupil, viewMeetingLogByTeacher, viewMeetingLogByTimeslot,
			clearNotifications, clearMeetingLogs;
	private JSpinner bookingOpenDay, bookingOpenTime, bookingCloseDay, bookingCloseTime, parentsEndDay, parentsEndTime,
			parentsStartDay, parentsStartTime, meetingDuration;
	private JTextField addingTeacherName, removingTeacherName, currentTeacherName, newTeacherName, teacherSubject,
			currentPupilName, newPupilName, contactParent, viewLogsInput;
	JDialog displayStatusMessage;

	private PEOSystemDatabase database;

	private final int TEXT_FIELD_LENGTH = 20;

	public AdministratorScreen(PEOSystemDatabase database, int x, int y) {
		// Setting up the GUI
		this.database = database;
		database.addObserver(this);
		setTitle("Administrator Screen");
		setLocation(x, y);
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		contentPane = new JTabbedPane();
		systemPanel = createSystemPanel();
		logsPanel = createLogsPanel();
		teachersPanel = createTeachersPanel();
		pupilsPanel = createPupilsPanel();

		contentPane.addTab("System", systemPanel);
		contentPane.addTab("Meeting Logs", logsPanel);
		contentPane.addTab("Teachers", teachersPanel);
		contentPane.addTab("Pupils", pupilsPanel);

		setContentPane(contentPane);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Add Teacher":
			if (database.addTeacher(currentTeacherName.getText(), teacherSubject.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updateTeachersDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to add Teacher, teacher with that name is already in the staff list", "Duplicate Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Edit Teacher":
			if (database.editTeacher(currentTeacherName.getText(), newTeacherName.getText(),
					teacherSubject.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updateTeachersDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to edit Teacher, no teacher with that name is in the staff list", "Missing Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Remove Teacher":
			if (database.removeTeacher(currentTeacherName.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updateTeachersDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to remove Teacher, no teacher with that name is on the stafflist", "Missing Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Add Pupil":
			if (database.addPupil(currentPupilName.getText(), contactParent.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updatePupilsDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to add Pupil, pupil with that name is already in the school roll", "Duplicate Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Edit Pupil":
			if (database.editPupil(currentPupilName.getText(), newPupilName.getText(), contactParent.getText(),
					addingTeacherName.getText(), removingTeacherName.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updatePupilsDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to edit pupil, no pupil with that name is in the school roll", "Missing Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "Remove Pupil":
			if (database.removePupil(currentPupilName.getText())) {
				JOptionPane.showMessageDialog(this, "Edit Successful");
				updatePupilsDisplay();
			} else {
				JOptionPane.showMessageDialog(this,
						"Unable to remove pupil, no pupil with that name is on the school roll", "Missing Error",
						JOptionPane.ERROR_MESSAGE);
			}
			break;
		case "View Meeting Log":
			logsDisplay.setText("");
			for (String s : database.viewMeetingLog()) {
				logsDisplay.append(s + "\n");
			}
			break;
		case "View Meeting Log By Pupil":
			logsDisplay.setText("");
			for (String s : database.viewMeetingLogsByPupil(viewLogsInput.getText())) {
				logsDisplay.append(s + "\n");
			}
			break;
		case "View Meeting Log By Teacher":
			logsDisplay.setText("");
			for (String s : database.viewMeetingLogsByTeacher(viewLogsInput.getText())) {
				logsDisplay.append(s + "\n");
			}
			break;
		case "View Meeting Log By Timeslot":
			logsDisplay.setText("");
			try {
				int timeslot = Integer.parseInt(viewLogsInput.getText());
				for (String s : database.viewMeetingLogsInTimeslot(timeslot)) {
					logsDisplay.append(s + "\n");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			break;
		case "Save the times":
			setTimes();
			break;
		case "Save the meeting duration":
			setMeetingLength();
			break;
		case "Set up system":
			setUpSystem();
			break;
		case "Clear old notifications":
			database.clearNotifications();
			break;
		case "Clear old meeting logs":
			database.clearMeetingLogs();
			break;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		updateButtons(database.getSystemStatus());
	}

	public boolean updateButtons(int systemStatus) {
		if (systemStatus == 0) {
			setTimes.setEnabled(true);
			setUpSystem.setEnabled(true);
			setMeetingLength.setEnabled(true);
			clearMeetingLogs.setEnabled(true);
			clearNotifications.setEnabled(true);

			addPupil.setEnabled(true);
			editPupil.setEnabled(true);
			removePupil.setEnabled(true);

			addTeacher.setEnabled(true);
			editTeacher.setEnabled(true);
			removeTeacher.setEnabled(true);
		} else {
			setTimes.setEnabled(false);
			setUpSystem.setEnabled(false);
			setMeetingLength.setEnabled(false);
			clearMeetingLogs.setEnabled(false);
			clearNotifications.setEnabled(false);

			addPupil.setEnabled(false);
			editPupil.setEnabled(false);
			removePupil.setEnabled(false);

			addTeacher.setEnabled(false);
			editTeacher.setEnabled(false);
			removeTeacher.setEnabled(false);
		}
		return true;
	}

	private JPanel createSystemPanel() {

		JPanel systemPanel = new JPanel();
		systemPanel.add(new JLabel("Bookings Open Time:"));

		bookingOpenDay = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, bookingOpenDay);
		systemPanel.add(bookingOpenDay);
		bookingOpenTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, bookingOpenTime);
		systemPanel.add(bookingOpenTime);

		systemPanel.add(new JLabel("Bookings Close Time:"));
		bookingCloseDay = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, bookingCloseDay);
		systemPanel.add(bookingCloseDay);
		bookingCloseTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, bookingCloseTime);
		systemPanel.add(bookingCloseTime);

		systemPanel.add(new JLabel("Parents Evening Start Time:"));
		parentsStartDay = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, parentsStartDay);
		systemPanel.add(parentsStartDay);
		parentsStartTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, parentsStartTime);
		systemPanel.add(parentsStartTime);

		systemPanel.add(new JLabel("Parents Evening End Time:"));
		parentsEndDay = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, parentsEndDay);
		systemPanel.add(parentsEndDay);
		parentsEndTime = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		setTextFieldColumns(6, parentsEndTime);
		systemPanel.add(parentsEndTime);

		systemPanel.add(new JLabel("Meeting Length in Minutes:"));
		meetingDuration = new JSpinner(new SpinnerNumberModel(5, 5, null, 5));
		setTextFieldColumns(6, meetingDuration);
		systemPanel.add(meetingDuration);

		setTimes = new JButton("Save the times");
		systemPanel.add(setTimes);
		setTimes.addActionListener(this);

		setMeetingLength = new JButton("Save the meeting duration");
		systemPanel.add(setMeetingLength);
		setMeetingLength.addActionListener(this);

		setUpSystem = new JButton("Set up system");
		systemPanel.add(setUpSystem);
		setUpSystem.addActionListener(this);

		systemDisplay = new JTextArea(5, 20);
		systemScroll = new JScrollPane(systemDisplay);
		systemDisplay.setEditable(false);
		systemPanel.add(systemScroll);

		return systemPanel;
	}

	private JPanel createLogsPanel() {
		JPanel logsPanel = new JPanel();

		logsDisplay = new JTextArea(5, 20);
		logsScroll = new JScrollPane(logsDisplay);
		logsDisplay.setEditable(false);
		logsPanel.add(logsScroll);

		viewMeetingLog = new JButton("View Meeting Log");
		logsPanel.add(viewMeetingLog);
		viewMeetingLog.addActionListener(this);

		viewLogsInput = new JTextField(TEXT_FIELD_LENGTH);
		logsPanel.add(viewLogsInput);

		viewMeetingLogByPupil = new JButton("View Meeting Log By Pupil");
		logsPanel.add(viewMeetingLogByPupil);
		viewMeetingLogByPupil.addActionListener(this);

		viewMeetingLogByTeacher = new JButton("View Meeting Log By Teacher");
		logsPanel.add(viewMeetingLogByTeacher);
		viewMeetingLogByTeacher.addActionListener(this);

		viewMeetingLogByTimeslot = new JButton("View Meeting Log By Timeslot");
		logsPanel.add(viewMeetingLogByTimeslot);
		viewMeetingLogByTimeslot.addActionListener(this);

		clearNotifications = new JButton("Clear old notifications");
		logsPanel.add(clearNotifications);
		clearNotifications.addActionListener(this);

		clearMeetingLogs = new JButton("Clear old meeting logs");
		logsPanel.add(clearMeetingLogs);
		clearMeetingLogs.addActionListener(this);

		return logsPanel;
	}

	private JPanel createTeachersPanel() {
		JPanel teachersPanel = new JPanel();
		teachersPanel.add(new JLabel("Current Teacher Name: "));
		currentTeacherName = new JTextField(TEXT_FIELD_LENGTH);
		teachersPanel.add(currentTeacherName);

		teachersPanel.add(new JLabel("New Teacher Name: "));
		newTeacherName = new JTextField(TEXT_FIELD_LENGTH);
		teachersPanel.add(newTeacherName);

		teachersPanel.add(new JLabel("Subject: "));
		teacherSubject = new JTextField(TEXT_FIELD_LENGTH);
		teachersPanel.add(teacherSubject);

		addTeacher = new JButton("Add Teacher");
		teachersPanel.add(addTeacher);
		addTeacher.addActionListener(this);

		editTeacher = new JButton("Edit Teacher");
		teachersPanel.add(editTeacher);
		editTeacher.addActionListener(this);
		removeTeacher = new JButton("Remove Teacher");
		teachersPanel.add(removeTeacher);
		removeTeacher.addActionListener(this);

		teachersDisplay = new JTextArea(5, 20);
		teachersScroll = new JScrollPane(teachersDisplay);
		teachersDisplay.setEditable(false);
		teachersPanel.add(teachersScroll);

		return teachersPanel;
	}

	private JPanel createPupilsPanel() {
		JPanel pupilsPanel = new JPanel();

		pupilsPanel.add(new JLabel("Current Pupil Name: "));
		currentPupilName = new JTextField(TEXT_FIELD_LENGTH);
		pupilsPanel.add(currentPupilName);

		pupilsPanel.add(new JLabel("New Pupil Name: "));
		newPupilName = new JTextField(TEXT_FIELD_LENGTH);
		pupilsPanel.add(newPupilName);

		pupilsPanel.add(new JLabel("Contact Parent: "));
		contactParent = new JTextField(TEXT_FIELD_LENGTH);
		pupilsPanel.add(contactParent);

		pupilsPanel.add(new JLabel("Name of Teacher to add: "));
		addingTeacherName = new JTextField(TEXT_FIELD_LENGTH);
		pupilsPanel.add(addingTeacherName);

		pupilsPanel.add(new JLabel("Name of Teacher to remove: "));
		removingTeacherName = new JTextField(TEXT_FIELD_LENGTH);
		pupilsPanel.add(removingTeacherName);

		addPupil = new JButton("Add Pupil");
		pupilsPanel.add(addPupil);
		addPupil.addActionListener(this);
		editPupil = new JButton("Edit Pupil");
		pupilsPanel.add(editPupil);
		editPupil.addActionListener(this);
		removePupil = new JButton("Remove Pupil");
		pupilsPanel.add(removePupil);
		removePupil.addActionListener(this);

		pupilsDisplay = new JTextArea(5, 20);
		pupilsScroll = new JScrollPane(pupilsDisplay);
		pupilsDisplay.setEditable(false);
		pupilsPanel.add(pupilsScroll);
		return pupilsPanel;
	}

	private void updateTeachersDisplay() {
		StringBuilder sb = new StringBuilder();
		for (Teacher s : database.getStaffList()) {
			sb.append(s.toString());
			sb.append("\n");
		}
		teachersDisplay.setText(sb.toString());
	}

	private void updatePupilsDisplay() {
		StringBuilder sb = new StringBuilder();
		for (Pupil s : database.getSchoolRoll()) {
			sb.append(s.toString());
			sb.append("\n");
		}
		pupilsDisplay.setText(sb.toString());
	}

	private void showSystemStatusMessage(String systemStatusMessage) {
		JOptionPane displaySystemStatusMessage = new JOptionPane();
		displaySystemStatusMessage.setMessage(systemStatusMessage);
		displayStatusMessage = displaySystemStatusMessage.createDialog(null, "System Status");
		displayStatusMessage.setVisible(true);
	}

	private JFormattedTextField getTextField(JSpinner spinner) {
		JComponent editor = spinner.getEditor();
		if (editor instanceof JSpinner.DefaultEditor) {
			return ((JSpinner.DefaultEditor) editor).getTextField();
		} else {
			System.err.println("Unexpected editor type: " + spinner.getEditor().getClass()
					+ " isn't a descendant of DefaultEditor");
			return null;
		}
	}

	private void setTextFieldColumns(int col, JSpinner spinner) {
		JFormattedTextField ftf = getTextField(spinner);
		if (ftf != null) {
			ftf.setColumns(col); // specify more width than we need
		}
	}

	private void setTimes() {
		DateAndTime bo = new DateAndTime((int) bookingOpenDay.getValue(), (int) bookingOpenTime.getValue());
		DateAndTime bc = new DateAndTime((int) bookingCloseDay.getValue(), (int) bookingCloseTime.getValue());
		DateAndTime ps = new DateAndTime((int) parentsStartDay.getValue(), (int) parentsStartTime.getValue());
		DateAndTime pe = new DateAndTime((int) parentsEndDay.getValue(), (int) parentsEndTime.getValue());
		if (database.setTimes(bo, bc, ps, pe)) {
			systemDisplay.setText("Times were set");
		} else {
			systemDisplay.setText("Times are not in expected sequence.");
		}

	}

	private void setMeetingLength() {
		if (database.setMeetingLength((int) meetingDuration.getValue()) == false) {
			systemDisplay.setText("Meeting Duration could not be set");
		} else {
			systemDisplay.setText("Meeting Duration was set");
		}
	}

	private void setUpSystem() {
		if (database.areTimesValid(null, null, null, null)) {
			if (database.prepareForBookings()) {
				systemDisplay.setText("System is now set up.");
				return;
			}
		}
		systemDisplay.setText("System setup failed");
	}

}