package wags.Common;


import wags.admin.ProblemCreationPanel;
import wags.logical.view.LogicalProblem;
import wags.magnet.view.MagnetProblem;
import wags.views.concrete.AccountPage;
import wags.views.concrete.DefaultPage;
import wags.views.concrete.Editor;
import wags.views.concrete.LMEditTab;
import wags.views.concrete.LogicalTab;
import wags.views.concrete.Login;
import wags.views.concrete.MagnetTab;
import wags.views.concrete.ProblemPage;
import wags.views.concrete.ProgrammingTab;
import wags.views.concrete.ReviewTab;
import wags.views.concrete.SectionTab;
import wags.views.concrete.StudentTab;
import wags.views.concrete.WagsViewImpl;

/**
 * @author   Dakota Murray
 * @version  21 July 2014
 * 
 * A class which provides singleton access to all views and application wide objects. Views and UI 
 * elements are expensive to instantiate. The ClientFactory ensures that only one instance of a view 
 * can ever be instantiated. 
 *
 * It is pointless to document each individual method in this class as they all follow an identical format. 
 * A static null instance of the object is created as a field. Whenever a class requests access to a 
 * ClientFactory object for the first time then that object is instantiated. If the object being requested has
 * already been instantiated then the existing instance is returned. 
 */
public class ClientFactory {
	
	private static AppController app;
	private static Login login;
	private static WagsViewImpl wags;
	private static AppModel model;
	private static Editor editor;
	private static AccountPage accountPage;
	private static DefaultPage defaultPage;
	private static ProblemCreationPanel mpcPanel;
	private static SectionTab sectionTab;
	private static LogicalTab logicalTab;
	private static LMEditTab logicalCreation;
	private static MagnetTab magnetManagement;
	private static StudentTab studentTab;
	private static ProgrammingTab progTab;
	private static ReviewTab reviewTab;
	private static ProblemPage problemPage;
	private static MagnetProblem magnetProblem;
	private static LogicalProblem logicalProblem;
	
	public static View findViewByToken(String token) {
		switch(token) {
		case Tokens.PROBLEMS:
			return getMagnetPageView();
		case Tokens.LOGICALCREATION:
			return getLogicalCreationTab();
		case Tokens.LOGICALMANAGEMENT:
			return getLogicalManagementTab();
		case Tokens.MAGNETMANAGEMENT:
			return getMagnetManagementTab();
		case Tokens.MANAGESECTION:
			return getSectionTab();
		case Tokens.MANAGESTUDENT:
			return getStudentManagementTab();
		case Tokens.PROGRAMMINGMANAGEMENT:
			return getProgrammingTab();
		case Tokens.REVIEW:
			return getReviewTab();
		case Tokens.MAGNETPROBLEM:
			return getMagnetProblemView();
		case Tokens.LOGICALPROBLEM:
			return getLogicalProblemView();
		case Tokens.ACCOUNT:
			return getAccountView();
		case Tokens.DEFAULT:
			return getDefaultView();
		default: 
			return null;
		}
	}
	
	
	public static AppController getAppController()
	{
		if (app == null) {
			app = new AppController();
		}
		return app;
	}
	
	public static AppModel getAppModel()
	{
		if (model == null) {
			model = new AppModel();
		}
		return model;
	}
	
	public static WagsViewImpl getWagsView()
	{
		if (wags == null) {
			wags = new WagsViewImpl();
		}
		return wags;
	}
	
	public static Login getLoginView()
	{
		if( login == null) {
			login = new Login();
		}
		return login;
	}
	
	public static Editor getEditorView()
	{
		if( editor == null) {
			editor = new Editor();
		}
		return editor;
	}
	
	public static AccountPage getAccountView()
	{
		if (accountPage == null)
		{
			accountPage = new AccountPage();
		}
		return accountPage;
	}
	
	public static DefaultPage getDefaultView()
	{
		if( defaultPage == null)
		{
			defaultPage = new DefaultPage();
		}
		return defaultPage;
	}
	
	public static ProblemCreationPanel getMagnetProblemCreationTab()
	{
		if (mpcPanel == null) {
			mpcPanel = new ProblemCreationPanel();
		}
		return mpcPanel;
	}
	
	public static SectionTab getSectionTab()
	{
		if (sectionTab == null) {
			sectionTab = new SectionTab();
		}
		return sectionTab;
	}
	
	public static LogicalTab getLogicalManagementTab()
	{
		if (logicalTab == null) {
			logicalTab = new LogicalTab();
		}
		return logicalTab;
	}
	
	public static LMEditTab getLogicalCreationTab()
	{
		if (logicalCreation == null) {
			logicalCreation = new LMEditTab();
		}
		return logicalCreation;
	}
	
	public static MagnetTab getMagnetManagementTab()
	{
		if (magnetManagement == null) {
			magnetManagement = new MagnetTab();
		}
		return magnetManagement;
	}
	
	public static StudentTab getStudentManagementTab()
	{
		if (studentTab == null) {
			studentTab = new StudentTab();
		}
		return studentTab;
	}
	
	public static ProgrammingTab getProgrammingTab()
	{
		if (progTab == null) {
			progTab = new ProgrammingTab();
		}
		return progTab;
	}
	
	public static ReviewTab getReviewTab()
	{
		if (reviewTab == null) {
			reviewTab = new ReviewTab();
		}
		return reviewTab;
	}
	
	public static ProblemPage getMagnetPageView()
	{
		if (problemPage == null) {
			problemPage = new ProblemPage();
		}
		return problemPage;
	}
	
	public static MagnetProblem getMagnetProblemView()
	{
		if (magnetProblem == null) {
			magnetProblem = new MagnetProblem();
		}
		return magnetProblem;
	}
	
	public static LogicalProblem getLogicalProblemView()
	{
		if(logicalProblem == null) {
			logicalProblem = new LogicalProblem();
		}
		return logicalProblem;
	}
	
	
}
