package de.whitescan.api.bedrock;

import java.util.List;

import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class SimpleBedrockGUI extends BedrockGUI {

	public SimpleBedrockGUI(@NonNull String title, @NonNull String content, @NonNull List<ButtonComponent> buttons) {
		super(title, content, SimpleForm.of(title, content, buttons));
	}

	@Override
	protected void onResponse(FloodgatePlayer player, FormResponse response) {

		if (!response.isCorrect())
			return;

		if (response instanceof SimpleFormResponse simpleResponse)
			onSimpleResponse(player, simpleResponse);

		getOpen().remove(player);

	}

	protected abstract void onSimpleResponse(FloodgatePlayer player, SimpleFormResponse simpleResponse);

}
