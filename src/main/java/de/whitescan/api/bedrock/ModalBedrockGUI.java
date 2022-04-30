package de.whitescan.api.bedrock;

import org.geysermc.cumulus.ModalForm;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.cumulus.response.ModalFormResponse;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import lombok.NonNull;

/**
 * 
 * @author Whitescan
 *
 */
public abstract class ModalBedrockGUI extends BedrockGUI {

	public ModalBedrockGUI(@NonNull String title, @NonNull String content, String button1, String button2) {
		super(title, content, ModalForm.of(title, content, button1, button2));
	}

	@Override
	protected void onResponse(FloodgatePlayer player, FormResponse response) {

		if (!response.isCorrect())
			return;

		if (response instanceof ModalFormResponse modalResponse)
			onModalResponse(player, modalResponse);

		getOpen().remove(player);

	}

	protected abstract void onModalResponse(FloodgatePlayer player, ModalFormResponse modalResponse);

}
