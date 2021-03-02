package com.github.lofi.client;

import com.github.lofi.client.components.MyButton;
import com.github.lofi.client.es5.Es5CustomElementHelper;

public class AppEntryPoint {

	public void onModuleLoad() {
		// Workaround to register the Web Component for ES5 specification
		Es5CustomElementHelper.registerEs5("my-button", MyButton.class, MyButton::init);
	}
}
