package cn.mapway.openapi.viewer.client.component.ace;

/**
 * Listener for selection change events.
 */
public interface AceSelectionListener {
	/**
	 * Emitted when the cursor selection changes.
	 * 
	 * @param selection the {@link AceSelection}
	 */
	public void onChangeSelection(AceSelection selection);
}
