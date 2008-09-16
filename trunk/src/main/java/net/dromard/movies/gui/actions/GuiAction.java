package net.dromard.movies.gui.actions;

public abstract class GuiAction implements Runnable {
	private String message = "";
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}



	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
