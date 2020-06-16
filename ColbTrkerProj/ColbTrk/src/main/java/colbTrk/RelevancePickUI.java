package colbTrk;

import java.util.ArrayList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * UI that lets the user to choose the relevance of interest for catalog display
 * 
 * @author Vibeesh Kamalakannan
 *
 */
public class RelevancePickUI {

	private static String RelevancePojoLIT = "RelevancePojo";
	private Shell mainShell = null;
	ArrayList<RelevancePojo> relevancePojoList = null;
	CommonUIData commonUIData = null;

	private Composite buttonRibbon = null;
	private Composite bodyPane = null;
	
	public RelevancePickUI(CommonUIData inCommonUIData) {
		commonUIData = inCommonUIData;

		//mainShell = new Shell(commonUIData.getColbTrkDisplay(), SWT.APPLICATION_MODAL|SWT.CLOSE|SWT.TITLE|SWT.BORDER|SWT.RESIZE);
		//mainShell.setImage(new Image(commonUIData.getColbTrkDisplay(), commonUIData.getCommons().applicationIcon));		
		//mainShell.setText("Relevance Pick");
		//mainShell.setLayout(new RowLayout());


		mainShell = new Shell(commonUIData.getColbTrkDisplay(),SWT.APPLICATION_MODAL
								|SWT.CLOSE|SWT.TITLE|SWT.BORDER|SWT.RESIZE|SWT.MAX|SWT.MIN);
		mainShell.setLayout(new GridLayout(1, false));
		mainShell.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mainShell.setImage(new Image(commonUIData.getColbTrkDisplay(), commonUIData.getCommons().applicationIcon));
		mainShell.setText("Relevance Pick");

		setButtonRibbon();

		bodyPane = new Composite(mainShell, SWT.NONE);		
		bodyPane.setLayout(new FillLayout());
		bodyPane.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	private void setButtonRibbon(){
		buttonRibbon = new Composite (mainShell, SWT.NONE);
		buttonRibbon.setLayout(new FillLayout(SWT.HORIZONTAL));
		buttonRibbon.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		{
			Button btnRefresh = new Button(buttonRibbon, SWT.NONE);
			btnRefresh.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Refreshing");
					refreshCreateArtifactUI();
				}
			});
			btnRefresh.setBounds(10, 10, 120, 25);
			btnRefresh.setText("Refresh");
			btnRefresh.setToolTipText("Refresh screen");
			btnRefresh.pack();
		}

		{
			Button btnSelectAll = new Button(buttonRibbon, SWT.NONE);
			btnSelectAll.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {

					System.out.println("Selecting all");

					// before adding all relevances, delete current relevances any to avoid duplicates
					commonUIData.getCatelogPersistenceManager()
					.unPickAllRelevance(commonUIData.getCommons().getCurrentRootNick());

					commonUIData.getCatelogPersistenceManager()
					.pickAllRelevances(commonUIData.getCommons().getCurrentRootNick());
					
					refreshCreateArtifactUI();
				}
			});
			btnSelectAll.setBounds(10, 10, 120, 25);
			btnSelectAll.setText("Select All");
			btnSelectAll.setToolTipText("Select All Relevances");
			btnSelectAll.pack();
		}
		
		{
			Button btnSelectNone = new Button(buttonRibbon, SWT.NONE);
			btnSelectNone.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					System.out.println("Select None");

					commonUIData.getCatelogPersistenceManager()
					.unPickAllRelevance(commonUIData.getCommons().getCurrentRootNick());
					
					refreshCreateArtifactUI();
				}
			});
			btnSelectNone.setBounds(10, 10, 120, 25);
			btnSelectNone.setText("Select None");
			btnSelectNone.setToolTipText("Deselect all relevances");
			btnSelectNone.pack();
		}
		
	}
	public void refreshCreateArtifactUI() {
		Control[] oldControls = bodyPane.getChildren();
		for (Control oldControl : oldControls) {
		    oldControl.dispose();
		}
		displayRelevancePickUI();
	}

	public void displayRelevancePickUI() {

		relevancePojoList = commonUIData.getCatelogPersistenceManager()
				.readRelevances(commonUIData.getCommons().getCurrentRootNick());

		final Tree tree = new Tree(bodyPane, SWT.CHECK);

		TreeItem item = null;
		TreeItem[] parentTreeItems = new TreeItem[relevancePojoList.size()];
		String[] prevRelevanceNodes = new String[] { "" };
		String[] relevanceNodes;
		boolean pathChanged = false;
		for (int relevanceCount = 0; relevanceCount < relevancePojoList.size(); relevanceCount++) {
			System.out.println("relevanceList[" + relevanceCount + "]"
					+ relevancePojoList.get(relevanceCount).relevance);
			relevanceNodes = relevancePojoList.get(relevanceCount).relevance
					.split("\\\\");
			System.out.println("reached out");
			pathChanged = false;
			for (int nodeCount = 0; nodeCount < relevanceNodes.length; nodeCount++) {
				System.out.println("nodeCount = " + nodeCount);
				System.out.println("relevanceNodes.length = "
						+ relevanceNodes.length);
				System.out.println("relevanceNodes[" + nodeCount + "] = "
						+ relevanceNodes[nodeCount]);
				System.out.println("prevRelevanceNodes.length = "
						+ prevRelevanceNodes.length);
				if (relevanceCount == 0) {
					if (nodeCount < (relevanceNodes.length - 1)) {
						if (nodeCount == 0) {
							item = new TreeItem(tree, SWT.NONE);
							System.out
									.println("node added1111 at " + nodeCount);
						} else {
							item = new TreeItem(parentTreeItems[nodeCount - 1],
									SWT.NONE);
							System.out
									.println("node added2222 at " + nodeCount);
						}
						item.setText("Filler");
					} else {
						if (nodeCount == 0) {
							item = new TreeItem(tree, SWT.CHECK);
							System.out
									.println("node added3333 at " + nodeCount);
						} else {
							item = new TreeItem(parentTreeItems[nodeCount - 1],
									SWT.CHECK);
							System.out
									.println("node added4444 at " + nodeCount);
						}
						item
								.setText(relevancePojoList.get(relevanceCount).relevance);
						item
								.setChecked(relevancePojoList
										.get(relevanceCount).RelevancePicked);
						item.setData(RelevancePojoLIT, relevancePojoList
								.get(relevanceCount));
					}
					parentTreeItems[nodeCount] = item;
				} else if (nodeCount > (prevRelevanceNodes.length - 1)
						|| !relevanceNodes[nodeCount]
								.equals(prevRelevanceNodes[nodeCount])) {
					if (nodeCount > (prevRelevanceNodes.length - 1)) {
						if (nodeCount < (relevanceNodes.length - 1)) {
							item = new TreeItem(parentTreeItems[nodeCount - 1],
									SWT.NONE);
							System.out
									.println("node added5555 at " + nodeCount);

							item.setText("Filler");
						} else {
							item = new TreeItem(parentTreeItems[nodeCount - 1],
									SWT.CHECK);
							System.out
									.println("node added6666 at " + nodeCount);
							item
									.setText(relevancePojoList
											.get(relevanceCount).relevance);
							item.setChecked(relevancePojoList
									.get(relevanceCount).RelevancePicked);
							item.setData(RelevancePojoLIT, relevancePojoList
									.get(relevanceCount));

						}
					} else {
						if (pathChanged
								|| !relevanceNodes[nodeCount]
										.equals(prevRelevanceNodes[nodeCount])) {
							pathChanged = true;
							if (nodeCount < relevanceNodes.length - 1) {
								if (nodeCount == 0) {
									item = new TreeItem(tree, SWT.NONE);
									System.out.println("node added7777 at "
											+ nodeCount);
								} else {
									item = new TreeItem(
											parentTreeItems[nodeCount - 1],
											SWT.NONE);
									System.out.println("node added8888 at "
											+ nodeCount);
								}
								item.setText("Filler");
							} else {
								item = new TreeItem(
										parentTreeItems[nodeCount - 1],
										SWT.CHECK);
								item.setText(relevancePojoList
										.get(relevanceCount).relevance);
								item.setChecked(relevancePojoList
										.get(relevanceCount).RelevancePicked);
								item.setData(RelevancePojoLIT, relevancePojoList
										.get(relevanceCount));
								System.out.println("node added9999 at "
										+ nodeCount);
							}
						}
					}
					parentTreeItems[nodeCount] = item;
				}
			}
			prevRelevanceNodes = relevanceNodes;
		}
		for (int parentNodeCount = 0; parentNodeCount < parentTreeItems.length
				&& parentTreeItems[parentNodeCount] != null; parentNodeCount++) {
			System.out.println("parentNodeCount = " + parentNodeCount);
			System.out.println("parentTreeItems = "
					+ parentTreeItems[parentNodeCount].getText());

			parentTreeItems[parentNodeCount].setExpanded(true);
		}

		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (event.detail == SWT.CHECK) {
					TreeItem item = (TreeItem) event.item;
					//boolean checked = item.getChecked();
					//if (item.getData(RelevancePojoLIT) != null) {
					//	if (checked) {
					//		commonUIData.getCatelogPersistenceManager()
					//				.pickRelevance((RelevancePojo) item
					//						.getData(RelevancePojoLIT));
					//	} else {
					//		commonUIData.getCatelogPersistenceManager()
					//				.unPickRelevance((RelevancePojo) item
					//						.getData(RelevancePojoLIT));
					//	}
					//	Commons.logger.info("RelevancePickUI checked " + checked);						
					//}
					if (item.getData(RelevancePojoLIT) != null) {
						RelevancePojo relevancePojo = (RelevancePojo) item.getData(RelevancePojoLIT);
						boolean checked = item.getChecked();
						if (checked) {
							commonUIData.getCatelogPersistenceManager()
									.pickRelevance(relevancePojo);
						} else {
							commonUIData.getCatelogPersistenceManager()
									.unPickRelevance(relevancePojo);
						}
						Commons.logger.info("RelevancePickUI Relevance selection set as " + checked + " for " + relevancePojo.relevance);
					}
				}
			}
		});

		mainShell.setData(tree);
		tree.pack();
		bodyPane.pack();
		mainShell.pack();
		mainShell.open();		
		shellDisposeHolder();

		//while (!mainShell.isDisposed()) {
		//	if (!commonUIData.getColbTrkDisplay().readAndDispatch()) {
		//		if (commonUIData.getArtifactDisplayOkayToContinue()) {
		//			commonUIData.getColbTrkDisplay().sleep();
		//		} else {
		//			break;
		//		}
		//	}			
		//}
	}
	
	private void shellDisposeHolder() {
		while (!mainShell.isDisposed()) {
			if (!commonUIData.getColbTrkDisplay().readAndDispatch()) {
				if (commonUIData.getArtifactDisplayOkayToContinue()) {
					commonUIData.getColbTrkDisplay().sleep();
				} else {
					break;
				}
			}
		}
		System.out.println("here disposing....");
		mainShell.dispose();
	}	
}