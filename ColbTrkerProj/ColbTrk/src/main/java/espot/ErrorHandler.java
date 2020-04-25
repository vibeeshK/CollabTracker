package espot;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import commonTechs.DisplayKeeper;

/**
 * Handles error situation - displays message box / quits as asked
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class ErrorHandler {

	public static synchronized void displayError(Commons inCommons, String inMsg, Exception inException) {
		System.out.println("at displayError start aa");
		displayError(null, inCommons, inMsg, inException);
	}

	public static synchronized void displayError(Commons inCommons, String inMsg) {
		displayError(null, inCommons, inMsg, null);
	}

	public static synchronized void displayError(Shell inMainShell, Commons inCommons, String inMsg) {
		System.out.println("at displayError start bb");
		System.out.println("at displayError start inMsg " + inMsg);
		
		displayError(inMainShell, inCommons, inMsg, null);
	}
	
	public static synchronized void displayError(Shell inMainShell, Commons inCommons, String inMsg, Exception inException) {
		System.out.println(inMsg);
		inCommons.logger.info(inMsg);

		Shell mainShell = null;
		if (inMainShell==null) {
			Display display = DisplayKeeper.getDisplay();
			mainShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.CLOSE
					| SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.MIN | SWT.MAX);
			mainShell.setLayout(new FillLayout());
		} else {
			mainShell = inMainShell;
		}
		MessageBox messageBox1 = new MessageBox(mainShell,
				SWT.ICON_WARNING | SWT.OK);
		messageBox1.setMessage(inMsg);
		System.out.println("Error Handler displayError");
		try {
			if (inException == null) {
				Commons.logger.error(inMsg + " at "+ inCommons.getCurrentTimeStamp());
				System.out.println("throwing exception at 1");
				throw new Exception(inMsg);
			} else {
				Commons.logger.error(inMsg + " at "+ inCommons.getCurrentTimeStamp());
				Commons.logger.error(inException);
				System.out.println("throwing exception at 2");
				throw new Exception(inException);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("caught error at 3");
			Commons.logger.error(e  + " at "+ inCommons.getCurrentTimeStamp());
		}
		
		int rc1 = messageBox1.open();
		if (rc1 == SWT.OK) {
			return;
		}
	}

	private static synchronized String getCallingMethodName(int inInternalCallCount) {
		// ensure to call this only from the point where the name is to be saved
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[ste.length - (inInternalCallCount + 1)].getMethodName();
	}

	public static synchronized void showErrorAndQuit(Shell inMainShell, Commons inCommons, String inMsg, Exception inException) {
		int internalCallCount = 0;
		showErrorAndQuit(inMainShell, inCommons, inMsg, inException, internalCallCount);
	}

	public static synchronized void showErrorAndQuit(Commons inCommons, String inMsg, Exception inException) {
		int internalCallCount = 0;
		showErrorAndQuit(null, inCommons, inMsg, inException, internalCallCount);
	}

	public static synchronized void showErrorAndQuit(Commons inCommons, String inMsg) {
		int internalCallCount = 0;
		showErrorAndQuit(null, inCommons, inMsg, null, internalCallCount);
	}
	
	public static synchronized void showErrorAndQuit(Shell inMainShell, Commons inCommons, String inMsg) {
		int internalCallCount = 0;
		showErrorAndQuit(inMainShell, inCommons, inMsg, null, internalCallCount);
	}
		
	public static synchronized void messageBoxNumericOnly(Shell inMainShell, Commons inCommons){
		displayError(inMainShell, inCommons, "Enter only numeric");
	}
	
	public static synchronized void showErrorAndQuit(Shell inMainShell, Commons inCommons, String inMsg, Exception inException, int inInternalCallCount) {
		inInternalCallCount++;		
		String callingMethodName =  getCallingMethodName(inInternalCallCount);

		Shell mainShell = null;
		if (inMainShell == null) {

			Display display = DisplayKeeper.getDisplay();

			mainShell = new Shell(display, SWT.APPLICATION_MODAL | SWT.CLOSE
					| SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.MIN | SWT.MAX);
			mainShell.setLayout(new FillLayout());
		} else {
			mainShell = inMainShell;
		}

		String exceptionString = "";
		if (inException!=null) {
			exceptionString = " --- " + inException.toString();
			System.err.println(inException.getMessage());
		}

		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		displayError(mainShell, inCommons, inMsg + " from method " + callingMethodName  + exceptionString);
		System.out.println("showErrorAndQuit2");
		System.out.println(inMsg);
		inCommons.logger.error(inMsg, inException);
		System.out.println(inException);
		System.exit(Commons.FATALEXITCODE);
	}

	public static synchronized boolean confirmationPopup(Display inDisplay, String inMsg) {
	
		Shell mainShell = new Shell(inDisplay, SWT.APPLICATION_MODAL | SWT.CLOSE
				| SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.MIN | SWT.MAX);
		mainShell.setLayout(new FillLayout());
		MessageBox confirmationMessageBox = new MessageBox(mainShell,
				SWT.ICON_QUESTION | SWT.YES | SWT.NO);
		confirmationMessageBox.setMessage(inMsg);

		System.out.println("Displaying the popup " + inMsg);
		int rc1 = confirmationMessageBox.open();
		if (rc1 == SWT.YES) {
			return true;
		} else {
			return false;
		}
	}

	public static synchronized void infoPopup(Display inDisplay, String inMsg) {
	
		Shell mainShell = new Shell(inDisplay, SWT.APPLICATION_MODAL | SWT.CLOSE
				| SWT.TITLE | SWT.BORDER | SWT.RESIZE | SWT.MIN | SWT.MAX);
		mainShell.setLayout(new FillLayout());
		MessageBox WarningMessageBox = new MessageBox(mainShell,
				SWT.ICON_INFORMATION | SWT.OK);
		WarningMessageBox.setMessage(inMsg);
		WarningMessageBox.open();

		System.out.println("Displaying the popup " + inMsg);
	}
}