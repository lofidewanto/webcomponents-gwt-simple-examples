package com.github.lofi.client;

import java.util.logging.Logger;

import com.github.lofi.client.components.MyButton;
import com.github.lofi.client.es5.Es5CustomElementHelper;

public class AppEntryPoint {
	
	private static Logger logger = Logger.getLogger(AppEntryPoint.class.getName());

	public void onModuleLoad() {
		logger.info("Start J2CL...");

		// Workaround to register the Web Component for ES5 specification
		Es5CustomElementHelper.registerEs5("my-button", MyButton.class, MyButton::init);
	}
}
