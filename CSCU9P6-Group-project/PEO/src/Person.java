package PEO.src;

/**
 * This class is inherited by pupil and teachers, and used directly for contactParents of pupils
 */
public class Person {

	/**
	 * For simplicity we assume that name is a unique identifier for a person. In the real system, this may instead be implemented by something else such as an email address.
	 */
	private String name;

	public Person(String n)
	{
		this.name = n;
	}
	
	public String getName()
	{
	 	return name;

	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * This version of equals is case-sensitive.
	 */
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		if(!(o instanceof Person)) {
			return false;
		}
		Person other = (Person) o;
		return name.equals(other.getName());
	}
	
	// toString() was implemented for display purposes in the screen classes.
	public String toString() {
		return name;
	}
}