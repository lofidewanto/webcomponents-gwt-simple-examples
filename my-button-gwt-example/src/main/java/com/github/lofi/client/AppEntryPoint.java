package com.github.lofi.client;

import com.github.lofi.client.components.MyButton;
import com.github.lofi.client.es5.Es5CustomElementHelper;
import com.google.gwt.core.client.EntryPoint;

public class AppEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// Workaround to register the Web Component for ES5 specification
		Es5CustomElementHelper.registerEs5("my-button", MyButton.class, MyButton::init);
	}
}
