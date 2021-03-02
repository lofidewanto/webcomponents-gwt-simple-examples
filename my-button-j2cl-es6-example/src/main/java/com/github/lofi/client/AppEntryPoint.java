package com.github.lofi.client;

import java.util.logging.Logger;

import com.github.lofi.client.components.MyButton;

import elemental2.dom.DomGlobal;

public class AppEntryPoint {
	
	private static Logger logger = Logger.getLogger(AppEntryPoint.class.getName());

	public void onModuleLoad() {
		logger.info("Start J2CL...");

		DomGlobal.customElements.define("my-button", MyButton.class);
	}
}
