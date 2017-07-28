package org.cf.card.ui.session;

public class Session {

	@SuppressWarnings("rawtypes")
	private static SessionObject sessionObject;

	@SuppressWarnings("rawtypes")
	private Session() {
		sessionObject = new SessionObject();
	}

	private static class SingletonSessionHelper {
		private static final Session INSTANCE = new Session();
	}

	public static Session getInstance() {
		return SingletonSessionHelper.INSTANCE;
	}

	/** Getter and setter */
	@SuppressWarnings("rawtypes")
	public SessionObject getSessionObject() {
		return sessionObject;
	}

}
