package cn.mapway.openapi.viewer.client.component.ace;

/**
 * Listener for command line enter events.
 */
public interface AceCommandLineListener {
	/**
	 * Notify subscriber (e.g. editor) that command was entered.
	 * @param command ss
	 */
	public void onCommandEntered(String command);
}
