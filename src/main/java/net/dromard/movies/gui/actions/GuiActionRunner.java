package net.dromard.movies.gui.actions;

import java.util.Stack;

import javax.swing.JFrame;

import net.dromard.common.swing.InfiniteProgressPanel;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;

public final class GuiActionRunner {
	private static final InfiniteProgressPanel progress = new InfiniteProgressPanel();
	private static final Stack<GuiAction> actions = new Stack<GuiAction>();
	private JFrame application;
	private boolean running = false;
	
	public GuiActionRunner(final JFrame application) {
		this.application = application;
		progress.setFont(AppConf.getInstance().getPropertyAsFont(AppConstants.KEY_APPLICATION_FONT));
	}

	public void push(final GuiAction action) {
		actions.push(action);
		if (!running) {
			start();
		}
	}
	
	public void start() {
		running = true;
		progress.setPrimitiveWidth(application.getWidth()/5);
		application.setGlassPane(progress);
		progress.start();
		new Thread() {
			public void run() {
				try {
					while (actions.size() > 0) {
						GuiAction action = actions.pop();
						progress.setText(action.getMessage());
						action.run();
					}
				} finally {
					running = false;
					progress.stop();
					application.remove(progress);
					application.repaint();
				}
			}
		}.start();
	}
}
