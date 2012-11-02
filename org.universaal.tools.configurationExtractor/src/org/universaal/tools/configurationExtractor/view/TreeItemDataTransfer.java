package org.universaal.tools.configurationExtractor.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.eclipse.swt.dnd.ByteArrayTransfer;
import org.eclipse.swt.dnd.TransferData;

/**
 * Transfer class for handling DND of TreeItemData
 * 
 * @author Clay Gerrard<br>
 * http://thoughtsbyclayg.blogspot.com/2008/04/swt-java-left-hand-tabs-with-drag-and.html
 */
class TreeItemDataTransfer extends ByteArrayTransfer {
	private static final String TreeItemData_TRANSFER_NAME = "TreeItemData_TRANSFER";
	private static final int TreeItemData_TRANSFER_ID = registerType(TreeItemData_TRANSFER_NAME);
	private static TreeItemDataTransfer instance = new TreeItemDataTransfer();

	public static TreeItemDataTransfer getInstance() {
		return instance;
	}

	protected String[] getTypeNames() {
		return new String[] { TreeItemData_TRANSFER_NAME };
	}

	protected int[] getTypeIds() {
		return new int[] { TreeItemData_TRANSFER_ID };
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (object == null || !(object instanceof TreeItemData))
			return;

		TreeItemData myType = (TreeItemData) object;

		if (isSupportedType(transferData)) {
			try {
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(stream);
				// write out each member in your custom dataType
				out.writeUTF(myType.Text);
				out.writeUTF(myType.Data);
				out.close();

				super.javaToNative(stream.toByteArray(), transferData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} // end javaToNative

	public Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null)
				return null;

			TreeItemData myType = new TreeItemData();

			try {
				ByteArrayInputStream stream = new ByteArrayInputStream(buffer);
				DataInputStream in = new DataInputStream(stream);
				// read in each member in your custom dataType
				myType.Text = in.readUTF();
				myType.Data = in.readUTF();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return myType;
		} else {
			return null;
		}
	} // end nativetoJava

	public static class TreeItemData {
		// all the data to be held by each treeItem
		String Text = "";
		String Data = "";
	} // new members must be explicitly handled in nativeToJava & javaToNative

} // end TreeItemDataTransfer