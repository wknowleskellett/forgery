package dev.williamknowleskellett.forgery;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Forgery implements ModInitializer {
	public static final String MOD_ID = "forgery";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ForgeryItems.initialize();
		ForgeryRecipe.initialize();
	}
}