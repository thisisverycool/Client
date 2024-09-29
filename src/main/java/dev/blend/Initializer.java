package dev.blend;

import net.fabricmc.api.ModInitializer;

public class Initializer implements ModInitializer {

	@Override
	public void onInitialize() {
		Client.instance.init();
	}

}