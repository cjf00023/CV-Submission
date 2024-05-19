package PEO.src;
import java.util.ArrayList;
import java.util.HashMap;

public class Pupil extends Person {

	/**
	 * This represents the parent or guardian of a pupil who is the primary contact with the school. It is expected that this person will keep other parents or guardians informed, and that they will attend the parents evening together if both (or all) wish to attend.
	 */
	private Person contactParent;
	
	private ArrayList<Teacher> taughtBy = new ArrayList<Teacher>();
	// This map associates Teacher Names with the integer representation of a booked timeslot
	private HashMap<String, Integer> bookedWith = new HashMap<String, Integer>();
	
	public Pupil(String name)
	{
		super(name);
	}

	public Person getContactParent()
	{
		return contactParent;
	}
	public void setContactParent(String contactParent)
	{
		this.contactParent = new Person(contactParent);
	}
	public ArrayList<Teacher> getTaughtBy(){
		return taughtBy;
	}
	public void addTeacher(Teacher teacherToAdd){
		if (!taughtBy.contains(teacherToAdd)) {
			taughtBy.add(teacherToAdd);
		}
	}

	public void removeTeacher(Teacher teacherToRemove) {
		if (taughtBy.contains(teacherToRemove)) {
			taughtBy.remove(teacherToRemove);
		}
	}
	public HashMap<String, Integer> getBookedWith() {
		return bookedWith;
	}

	public void putBookedWith(String t, int ts) {
		bookedWith.put(t, ts);
	}
	
	public void removeBookedWith(String t) {
		bookedWith.remove(t);
	}

	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return "Pupil: "+ getName() + ", Contact Parent: " + contactParent + ", Taught By: " + taughtBy;
	}
	
}
