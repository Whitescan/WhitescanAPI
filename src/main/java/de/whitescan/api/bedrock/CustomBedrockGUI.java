package de.whitescan.api.bedrock;

import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import lombok.NonNull;

/**
 *
 * @author Whitescan
 *
 */
public abstract class CustomBedrockGUI extends BedrockGUI {

	public CustomBedrockGUI(@NonNull String title, @NonNull String content, @NonNull Form form) {
		super(title, content, form);
	}

	@Override
	protected void onResponse(FloodgatePlayer player, FormResponse response) {

		if (!response.isCorrect())
			return;

		if (response instanceof CustomFormResponse customResponse)
			onCustomResponse(player, customResponse);

		getOpen().remove(player);

	}

	protected abstract void onCustomResponse(FloodgatePlayer player, CustomFormResponse customResponse);

}
