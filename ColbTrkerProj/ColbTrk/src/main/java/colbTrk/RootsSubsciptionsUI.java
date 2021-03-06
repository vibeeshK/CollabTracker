package colbTrk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.w3c.dom.Document;

/**
 * UI for the maintenance of root subscription and default root setting
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RootsSubsciptionsUI {

	CommonUIData commonUIData = null;
	private Shell mainShell = null;
	private ArrayList<String> allRootNicksList = null;
	HashMap<String, RootPojo> publishedRootsMap = null;
	HashMap<String, RootPojo> subscribedRootsMap = null;
	ArrayList<String> subscribedRootNicks = null;
	Document subscribedRootsDoc = null;
	SubscribedRootsPojo subscribedRootsPojo = null;

	private TableEditor[] rootSysLoginIDs_TableEditors;
	
	public RootsSubsciptionsUI(CommonUIData inCommonUIData) {
		commonUIData = inCommonUIData;
		subscribedRootsPojo = new SubscribedRootsPojo(commonUIData);

		Commons.logger.info("RootsSubsciptionsUI started at " + commonUIData.getCommons().getCurrentTimeStamp());
	}

	public void refreshRootsSubsriptionsUI() {
		mainShell.close();

		displayRootsSubsriptionsUI();
	}

	public void displayRootsSubsriptionsUI() {

		mainShell = new Shell(commonUIData.getColbTrkDisplay(), SWT.APPLICATION_MODAL | SWT.CLOSE
				| SWT.TITLE | SWT.BORDER | SWT.RESIZE);
		mainShell.setImage(new Image(commonUIData.getColbTrkDisplay(), commonUIData.getCommons().applicationIcon));
		mainShell.setText("Root Maintenance");
		mainShell.setLayout(new GridLayout(1, false));
		mainShell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		System.out.println("RootsSubsriptionsUI.displayRootsSubsriptionsUI() bef");

		publishedRootsMap = commonUIData.getRootPojoMap();
		
		allRootNicksList = new ArrayList<String>();
		allRootNicksList.addAll(publishedRootsMap.keySet());
		System.out
				.println("RootsSubsriptionsUI.displayRootsSubsriptionsUI() aft22");

		mainShell.setLayout(new GridLayout(1, false));

		Table table = new Table(mainShell, SWT.BORDER);

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		String[] columnHeaders = new String[] {"RootNick", "RootString",
				"RootType", "RemoteAccesserType", "FileSeparator", "SysLogin", "SaveID", 
				"Subscriptions", "Default"};

		for (int i = 0; i < columnHeaders.length; i++) {
			TableColumn column = new TableColumn(table, SWT.NONE);
			column.setText(columnHeaders[i]);
			if (columnHeaders[i].equalsIgnoreCase("RootString")) {
				column.setWidth(200);
			} else {
				column.setWidth(100);
			}
		}
		int screenMaxNum = allRootNicksList.size();

		for (int i = 0; i < screenMaxNum; i++) {
			// create table rows - one for a new artifact and as many for
			// already in-progress
			new TableItem(table, SWT.NONE);
		}

		TableItem[] items = table.getItems();

		rootSysLoginIDs_TableEditors = new TableEditor[screenMaxNum];
				
		for (int ScreenRowNum = 0; ScreenRowNum < screenMaxNum; ScreenRowNum++) {

			int columnCount = 0;
			RootPojo dbRootPojo = publishedRootsMap.get(allRootNicksList
					.get(ScreenRowNum));
			System.out.println("ScreenRowNum = " + ScreenRowNum);
			System.out.println("commons.defaultUIRootNick = " + commonUIData.getCurrentRootNick());
			
			System.out.println("allRootNicksList.get(ScreenRowNum) = "
					+ allRootNicksList.get(ScreenRowNum));
			System.out.println("dbRootPojo Nick = " + dbRootPojo.rootNick);
			System.out.println("dbRootPojo file separator = "
					+ dbRootPojo.fileSeparator);

			TableEditor editor = new TableEditor(table);
			Text rootNickTx = new Text(table, SWT.READ_ONLY);
			rootNickTx.setText(dbRootPojo.rootNick);
			editor.grabHorizontal = true;
			editor.setEditor(rootNickTx, items[ScreenRowNum], columnCount++);

			editor = new TableEditor(table);
			Text rootStringTx = new Text(table, SWT.READ_ONLY);
			rootStringTx.setText(dbRootPojo.rootString);
			editor.grabHorizontal = true;
			editor.setEditor(rootStringTx, items[ScreenRowNum], columnCount++);

			editor = new TableEditor(table);
			Text rootTypeTx = new Text(table, SWT.READ_ONLY);
			rootTypeTx.setText(dbRootPojo.rootType);
			editor.grabHorizontal = true;
			editor.setEditor(rootTypeTx, items[ScreenRowNum], columnCount++);

			editor = new TableEditor(table);
			Text remoteAccessorTx = new Text(table, SWT.READ_ONLY);
			//removing the first node in display as its same for all accessers
			remoteAccessorTx.setText(StringUtils.split(dbRootPojo.remoteAccesserType,".")[1]);
			editor.grabHorizontal = true;
			editor.setEditor(remoteAccessorTx, items[ScreenRowNum],
					columnCount++);

			editor = new TableEditor(table);
			Text fileSeparatorTx = new Text(table, SWT.READ_ONLY);
			fileSeparatorTx.setText(dbRootPojo.fileSeparator);
			editor.grabHorizontal = true;
			editor.setEditor(fileSeparatorTx, items[ScreenRowNum],
					columnCount++);

			if (dbRootPojo.rootType.equalsIgnoreCase(RootPojo.RegRootType)) {
				// "SysLogin" starts

				rootSysLoginIDs_TableEditors[ScreenRowNum] = new TableEditor(table);
				Text sysLoginTx = new Text(table, SWT.NONE);
				
				String rootSysLoginID = "";
				try {
					rootSysLoginID = commonUIData.getCommons().readRootSysLoginIDFromClienSideProperties(dbRootPojo.rootNick);
				} catch (IOException e2) {
					ErrorHandler.showErrorAndQuit(commonUIData.getCommons(), "Error in RootsSubsriptionsUI displayRootsSubsriptionsUI reading rootSysLoginID of " + dbRootPojo.rootNick, e2);
				}
				sysLoginTx.setText(rootSysLoginID!=null?rootSysLoginID:"");
				rootSysLoginIDs_TableEditors[ScreenRowNum].grabHorizontal = true;
				rootSysLoginIDs_TableEditors[ScreenRowNum].setEditor(sysLoginTx, items[ScreenRowNum],
						columnCount++);

				// "SysLogin" ends
			
				// "Save" Starts
				editor = new TableEditor(table);
				Button userNameSaveButton = new Button(table, SWT.PUSH);
				userNameSaveButton.setText("Save");
				userNameSaveButton.setToolTipText("Save the user name for " + dbRootPojo.rootNick);
				userNameSaveButton.setData(Commons.SCREENROWNUMLIT, ScreenRowNum);

				System.out.println("RootNum = "
						+ userNameSaveButton.getData(Commons.SCREENROWNUMLIT));
				
				userNameSaveButton.pack();
				editor.minimumWidth = userNameSaveButton
						.getSize().x;
				editor.horizontalAlignment = SWT.LEFT;
				editor.setEditor(userNameSaveButton,
						items[ScreenRowNum], columnCount++);
				
				userNameSaveButton
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						Button eventButton = (Button) e.getSource();
						System.out.println("eventButton = "
								+ eventButton);
						Integer screenRowNum = (Integer) eventButton
								.getData(Commons.SCREENROWNUMLIT);
						System.out
								.println("selected screenRowNum = "
										+ screenRowNum);

						String selectedRootSysLoginID = ((Text) rootSysLoginIDs_TableEditors[screenRowNum].getEditor()).getText();

						if (selectedRootSysLoginID == null || selectedRootSysLoginID.isEmpty()) {
							MessageBox messageBox5 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
							messageBox5.setMessage("SysLoginID shall not be blank while saving for a root");
							int rc4 = messageBox5.open();
							return;
						}
						
						saveRootNickOfSelectedRow(screenRowNum);
					}
				});

				// "Save" Ends
				
				// Subscriptions related process starts
				
				editor = new TableEditor(table);
				Button maintainRootButton = new Button(table, SWT.PUSH);
				maintainRootButton.setData(Commons.SCREENROWNUMLIT, ScreenRowNum);

				System.out.println("RootNum = "
						+ maintainRootButton.getData(Commons.SCREENROWNUMLIT));

				if (subscribedRootsPojo.doesRootNickExist(dbRootPojo.rootNick)) {
					maintainRootButton.setText("UnSubscribe");
					maintainRootButton
							.addSelectionListener(new SelectionAdapter() {
								@Override
								public void widgetSelected(SelectionEvent e) {
									Button eventButton = (Button) e.getSource();
									System.out.println("eventButton = "
											+ eventButton);
									Integer screenRowNum = (Integer) eventButton
											.getData(Commons.SCREENROWNUMLIT);
									System.out
											.println("selected screenRowNum = "
													+ screenRowNum);

									if (allRootNicksList
									.get(screenRowNum).equalsIgnoreCase(commonUIData.getCurrentRootNick())) {
										MessageBox messageBox3 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
										messageBox3.setMessage("Default root shall not be unsubscribed");
										int rc3 = messageBox3.open();
										return;										
									}
									
									subscribedRootsPojo.removeSubscription(allRootNicksList
											.get(screenRowNum));
									
									Commons.logger.info("RootsSubsciptionsUI displayRootsSubsriptionsUI subscription removed for "
											+ allRootNicksList.get(screenRowNum)
											+ " at " + commonUIData.getCommons().getCurrentTimeStamp());
									
									refreshRootsSubsriptionsUI();
								}
							});

				} else {
					maintainRootButton.setText("Subscribe");
					maintainRootButton.setToolTipText("Subscribe to " + dbRootPojo.rootNick);
					maintainRootButton
							.addSelectionListener(new SelectionAdapter() {

								public void widgetSelected(SelectionEvent e) {
									Button eventButton = (Button) e.getSource();
									System.out.println("eventButton = "
											+ eventButton);
									Integer screenRowNum = (Integer) eventButton
											.getData(Commons.SCREENROWNUMLIT);
									System.out
											.println("selected screenRowNum = "
													+ screenRowNum);
									
									if (publishedRootsMap.get(allRootNicksList
									.get(screenRowNum)).requiresInternet){
										if (!commonUIData.getCommons().isInternetAvailable()){
											MessageBox messageBox1 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
											messageBox1.setMessage("Internet access is required for this root, but you dont have");
											int rc1 = messageBox1.open();
											return;
										}
									}
									
									String selectedRootSysLoginID = ((Text) rootSysLoginIDs_TableEditors[screenRowNum].getEditor()).getText();

									if (selectedRootSysLoginID == null || selectedRootSysLoginID.isEmpty()) {
										MessageBox messageBox5 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
										messageBox5.setMessage("SysLoginID shall not be blank while subscribing to a root");
										int rc4 = messageBox5.open();
										return;
									}

									saveRootNickOfSelectedRow(screenRowNum);
									
									subscribedRootsPojo.addSubscription(allRootNicksList
											.get(screenRowNum));

									Commons.logger.info("RootsSubsciptionsUI displayRootsSubsriptionsUI subscription added for "
											+ allRootNicksList.get(screenRowNum)
											+ " at " + commonUIData.getCommons().getCurrentTimeStamp());

									// Set all relevances to be shown in catalog display by
									// refreshing pickedRelevance table
									
									// Before adding all relevance, delete current relevances any to avoid duplicates
									commonUIData.getCatelogPersistenceManager()
									.unPickAllRelevance(commonUIData.getCommons().getCurrentRootNick());

									commonUIData.getCatelogPersistenceManager()
									.pickAllRelevances(commonUIData.getCommons().getCurrentRootNick());

									refreshRootsSubsriptionsUI();
								}
							});
				}

				maintainRootButton.pack();
				editor.minimumWidth = maintainRootButton
						.getSize().x;
				editor.horizontalAlignment = SWT.LEFT;
				editor.setEditor(maintainRootButton,
						items[ScreenRowNum], columnCount++);
				
				// Subscriptions related process ends

				// Default rootnick setting starts
				if (!commonUIData.getCurrentRootNick()
						.equalsIgnoreCase(dbRootPojo.rootNick)) {

					System.out.println("this is NOT the default root");
					
					editor = new TableEditor(
							table);
					Button defaultNickSettingButton = new Button(table,
							SWT.PUSH);
					defaultNickSettingButton.setData(Commons.SCREENROWNUMLIT,
							ScreenRowNum);

					System.out.println("RootNum = "
							+ defaultNickSettingButton.getData(Commons.SCREENROWNUMLIT));
					defaultNickSettingButton.setText("Set as Default");
					defaultNickSettingButton.setToolTipText("Set " + dbRootPojo.rootNick + " as the Default Root");
					defaultNickSettingButton
							.addSelectionListener(new SelectionAdapter() {

								public void widgetSelected(SelectionEvent e) {
									Button eventButton = (Button) e.getSource();
									System.out.println("eventButton = "
											+ eventButton);
									Integer screenRowNum = (Integer) eventButton
											.getData(Commons.SCREENROWNUMLIT);
									System.out
											.println("selected screenRowNum = "
													+ screenRowNum);
									System.out
									.println("allRootNicksList.get(screenRowNum) is " 
											+ allRootNicksList
											.get(screenRowNum));
									
									if (!subscribedRootsPojo.doesRootNickExist(allRootNicksList
											.get(screenRowNum))) {
										MessageBox messageBox2 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
										messageBox2.setMessage("You need to first subscribe to this root before making it default");
										messageBox2.open();
										return;										
									}
									
									try {
										commonUIData.getCommons().setDefaultUIRootNick(allRootNicksList
												.get(screenRowNum));
									} catch (IOException e1) {
										ErrorHandler.showErrorAndQuit(commonUIData.getCommons(), "Error in RootsSubsriptionsUI displayRootsSubsriptionsUI", e1);
									}

									commonUIData.refresh();
									
									//16June2020: moved relevance pick option to catalog display screen	
									//MessageBox alertMessageBox = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
									//alertMessageBox.setMessage("Please Choose Relevances now for the artifacts to be shown in Catalogue Display screen");
									//alertMessageBox.open();
									
									refreshRootsSubsriptionsUI();
								}
							});
					defaultNickSettingButton.pack();
					editor.minimumWidth = defaultNickSettingButton
							.getSize().x;
					editor.horizontalAlignment = SWT.LEFT;
					editor.setEditor(
							defaultNickSettingButton, items[ScreenRowNum],
							columnCount++);

				} else {
					System.out.println("this is THE default root");
					
					editor = new TableEditor(table);
					Text defaultNickSettingButtonTx = new Text(table,
							SWT.READ_ONLY);
					defaultNickSettingButtonTx.setText("Default-Root");
					editor.grabHorizontal = true;
					editor.setEditor(defaultNickSettingButtonTx, items[ScreenRowNum],
							columnCount++);
					defaultNickSettingButtonTx.pack();
				}

				// Default rootnick setting ends

				//if (commonUIData.getCurrentRootNick()
				//		.equalsIgnoreCase(dbRootPojo.rootNick)) {
				//	editor = new TableEditor(table);
				//	Button relevancebutton = new Button(table, SWT.PUSH);
				//	relevancebutton.setText("Choose");
				//	relevancebutton.pack();
				//	relevancebutton.setToolTipText(
				//			"To choose Relevances of the artifacts to be shown in Catalogue Display screen");
				//	
				//	editor.minimumWidth = relevancebutton.getSize().x;
				//	editor.horizontalAlignment = SWT.LEFT;
				//	editor.setEditor(relevancebutton,
				//			items[ScreenRowNum], columnCount++);
				//
				//	relevancebutton.setData(Commons.SCREENROWNUMLIT, ScreenRowNum);
				//
				//	System.out.println("set data = "
				//			+ relevancebutton.getData(Commons.SCREENROWNUMLIT));
				//
				//	relevancebutton.addSelectionListener(new SelectionAdapter() {
				//
				//		public void widgetSelected(SelectionEvent e) {
				//			Button eventButton = (Button) e.getSource();
				//
				//			System.out.println("invoking Relevance Pick UI");
				//
				//			System.out.println("eventButton = " + eventButton);
				//			Integer ScreenRowNum = (Integer) eventButton
				//					.getData(Commons.SCREENROWNUMLIT);
				//			System.out.println("selectedRowNum = " + ScreenRowNum);
				//			String rootNick = allRootNicksList.get(ScreenRowNum);
				//			System.out.println("rootNik = " + rootNick);
				//
				//			RelevancePickUI relevancePickUI = new RelevancePickUI(commonUIData);
				//			relevancePickUI.displayRelevancePickUI();
				//			refreshRootsSubsriptionsUI();
				//		}
				//	});
				//}
			}
		}

		mainShell.pack();
		mainShell.layout(true);
		mainShell.open();

		while (!mainShell.isDisposed()) {
			if (!commonUIData.getColbTrkDisplay().readAndDispatch()) {
				if (commonUIData.getArtifactDisplayOkayToContinue()) {
					commonUIData.getColbTrkDisplay().sleep();
				} else {
					break;
				}
			}
		}
	}
	
	private void saveRootNickOfSelectedRow(int inScreenRowNum) {
	
		String selectedRootNick = publishedRootsMap.get(allRootNicksList
				.get(inScreenRowNum)).rootNick;
		String selectedRootSysLoginID = ((Text) rootSysLoginIDs_TableEditors[inScreenRowNum].getEditor()).getText();
		System.out
		.println("selected SysLoginID of screenRow is "
					+ selectedRootSysLoginID);
		System.out
		.println("selected RootNick of screenRow is "
					+ selectedRootNick);

		MessageBox messageBox4 = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK);
		messageBox4.setMessage("Ensure to validate your RootSysLoginID in UserListing");
		int rc4 = messageBox4.open();
		
		try {
			commonUIData.getCommons().setRootSysLoginIDInClienSideProperties(
					selectedRootNick,
					selectedRootSysLoginID);
			commonUIData.getCommons().refreshDefaultRootSysUserName();
		} catch (IOException e1) {

			ErrorHandler.showErrorAndQuit(commonUIData.getCommons(), 
					"Error in RootsSubsriptionsUI saveRootNickOfSelectedRow while saving " + selectedRootSysLoginID 
					+ " for rootNick " + selectedRootNick, e1);
		}
		Commons.logger.info("RootsSubsciptionsUI selectedRootSysLoginID " + selectedRootSysLoginID
								+ " set for selectedRootNick " + selectedRootNick
								+ " at " + commonUIData.getCommons().getCurrentTimeStamp());
	}	
}