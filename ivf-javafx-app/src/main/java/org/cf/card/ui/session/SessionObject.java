package org.cf.card.ui.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SessionObject<K, T> { // singleton

	private Map<K, T> sessionObject = new HashMap<K, T>();

	public SessionObject() {
	}

	public T getComponent(K key) {
		return sessionObject.get(key);
	}

	public Collection<T> getComponents() {
		return new ArrayList<T>(sessionObject.values());
	}

	public void setComponent(K key, T value) {
		sessionObject.put(key, value);
	}

	public void removeComponent(K key) {
		sessionObject.remove(key);
	}

	public void destory() {
		sessionObject.clear();
	}
}
