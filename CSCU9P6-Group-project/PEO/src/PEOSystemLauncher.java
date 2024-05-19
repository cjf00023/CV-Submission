package PEO.src;

/**
 * The main class which launches the PEO system.
 * For demonstation purposes, this class also launches all of the UI Screens.
 */
public class PEOSystemLauncher {

	public static void main(String[] args) {
		PEOSystemDatabase peo = new PEOSystemDatabase();
		SystemTimer st = new SystemTimer();
		
		new TimeHandler(st, peo);
		
		new AdministratorScreen(peo, 20, 20);
		
		new TeacherScreen(peo, 420, 20);
		new TeacherScreen(peo, 820, 20);
		new TeacherScreen(peo, 1220, 20);
		
		new ParentScreen(peo, 420, 320);
		new ParentScreen(peo, 820, 320);
		new ParentScreen(peo, 1220, 320);
		new ParentScreen(peo, 420, 620);
		new ParentScreen(peo, 820, 620);
		new ParentScreen(peo, 1220, 620);
		
		new SystemTimerScreen(st, 20, 320);
	}
}