package org.universaal.tools.configurationExtractor.view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.TreeItem;
import org.universaal.tools.configurationExtractor.data.GeneralUCConfig;
import org.universaal.tools.configurationExtractor.data.ItemType;
import org.universaal.tools.configurationExtractor.view.TreeItemDataTransfer.TreeItemData;

/**
 * Allows the dragging of tree items with some restrictions:
 * <ul>
 * <li> only panels and the GerneralConfig are allowed at the root
 * <li> variables and URIs are only allowed inside of panels
 * <li> the GeneralConfig is only allowed directly at the top
 * </ul>
 * 
 * @author schwende
 */
public class DragnDropHelper {

	private static Tree tree;
	private CEView view;

	/**
	 * create a drag&drop helper for a specified View
	 * @param view CEView
	 */
	public DragnDropHelper(CEView view) {
		this.view = view;
	}

	/**
	 * enable Drag&Drop for a specified Tree
	 * @param t Tree to enable drag&drop
	 */
	public void enableDragnDrop(Tree t) {
		tree = t;

		Transfer[] types = new Transfer[] { TreeItemDataTransfer.getInstance() };
		int operations = DND.DROP_MOVE;

		final DragSource source = new DragSource(tree, operations);
		source.setTransfer(types);
		final TreeItem[] dragSourceItem = new TreeItem[1];
		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
				TreeItem[] selection = tree.getSelection();
				if (selection.length > 0 && selection[0].getItemCount() == 0) {
					event.doit = true;
					dragSourceItem[0] = selection[0];
				} else {
					event.doit = false;
				}
			};

			public void dragSetData(DragSourceEvent event) {
				TreeItemData data = new TreeItemData();
				data.Text = dragSourceItem[0].getText();
				data.Data = String.valueOf(dragSourceItem[0].getData());
				event.data = data;
			}

			public void dragFinished(DragSourceEvent event) {
				if (event.detail == DND.DROP_MOVE)
					dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
			}
		});

		DropTarget target = new DropTarget(tree, operations);
		target.setTransfer(types);
		target.addDropListener(new DropTargetAdapter() {
			public void dragOver(DropTargetEvent event) {
				event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
				if (event.item != null) {
					TreeItem item = (TreeItem) event.item;
					Point pt = Display.getCurrent().map(null, tree, event.x,
							event.y);
					Rectangle bounds = item.getBounds();
					if (pt.y < bounds.y + bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
					} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
						event.feedback |= DND.FEEDBACK_INSERT_AFTER;
					} else {
						event.feedback |= DND.FEEDBACK_SELECT;
					}
				}
			}

			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				String text = ((TreeItemData) event.data).Text;
				int data = Integer.parseInt(((TreeItemData) event.data).Data);
				
				if (event.item == null) { // item dragged to the bottom -> will be located at the root
					if (view.getConfigItem(data).getItemType() == ItemType.VARIABLE) {
						System.err.println("drag not allowed");
						event.detail = DND.DROP_NONE;
						return;
					}
					TreeItem item = new TreeItem(tree, SWT.NONE);
					item.setText(text);
					item.setData(data);
					
				} else { // item dragged to a specific place
					TreeItem item = (TreeItem) event.item;
					int dataCursorItem = (Integer) item.getData();
					Point pt = Display.getCurrent().map(null, tree, event.x,
							event.y);
					Rectangle bounds = item.getBounds();
					TreeItem parent = item.getParentItem();
					
					if (parent != null) { // the dragged location has a parent item
						TreeItem[] items = parent.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							if (view.getConfigItem(data).getItemType() != ItemType.VARIABLE || view.getConfigItem(data) instanceof GeneralUCConfig || dataCursorItem == 0) {
								System.err.println("drag not allowed");
								event.detail = DND.DROP_NONE;
								return;
							}
							TreeItem newItem = new TreeItem(parent, SWT.NONE,
									index);
							newItem.setText(text);
							newItem.setData(data);
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (view.getConfigItem(data).getItemType() != ItemType.VARIABLE || view.getConfigItem(data) instanceof GeneralUCConfig || dataCursorItem == 0) {
								System.err.println("drag not allowed");
								event.detail = DND.DROP_NONE;
								return;
							}
							TreeItem newItem = new TreeItem(parent, SWT.NONE,
									index + 1);
							newItem.setText(text);
							newItem.setData(data);
						} else {
							System.err.println("drag not allowed");
							event.detail = DND.DROP_NONE;
							return;
						}

					} else { // the dragged location has no parent item
						TreeItem[] items = tree.getItems();
						int index = 0;
						for (int i = 0; i < items.length; i++) {
							if (items[i] == item) {
								index = i;
								break;
							}
						}
						if (pt.y < bounds.y + bounds.height / 3) {
							if (view.getConfigItem(data).getItemType() == ItemType.VARIABLE || pt.y < 10 || view.getConfigItem(data) instanceof GeneralUCConfig || dataCursorItem == 0) {
								System.err.println("drag not allowed");
								event.detail = DND.DROP_NONE;
								return;
							}
							TreeItem newItem = new TreeItem(tree, SWT.NONE,
									index);
							newItem.setText(text);
							newItem.setData(data);
						} else if (pt.y > bounds.y + 2 * bounds.height / 3) {
							if (view.getConfigItem(data).getItemType() == ItemType.VARIABLE || pt.y < 10 || view.getConfigItem(data) instanceof GeneralUCConfig || dataCursorItem == 0) {
								System.err.println("drag not allowed");
								event.detail = DND.DROP_NONE;
								return;
							}
							TreeItem newItem = new TreeItem(tree, SWT.NONE,
									index + 1);
							newItem.setText(text);
							newItem.setData(data);
						} else {
							if (view.getConfigItem(data).getItemType() != ItemType.VARIABLE || view.getConfigItem(data) instanceof GeneralUCConfig || dataCursorItem == 0) {
								System.err.println("drag not allowed");
								event.detail = DND.DROP_NONE;
								return;
							}
							TreeItem newItem = new TreeItem(item, SWT.NONE);
							newItem.setText(text);
							newItem.setData(data);
						}
					}

				}
			}
		});

	}

}
