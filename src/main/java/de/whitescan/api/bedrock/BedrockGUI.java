package de.whitescan.api.bedrock;

import java.util.HashSet;
import java.util.Set;

import org.geysermc.cumulus.Form;
import org.geysermc.cumulus.response.FormResponse;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Whitescan
 *
 */
@RequiredArgsConstructor
public abstract class BedrockGUI {

	@Getter
	@NonNull
	private String title;

	@Getter
	@NonNull
	private String content;

	@Getter
	@NonNull
	private Form form;

	@Getter
	private Set<FloodgatePlayer> open = new HashSet<>();

	public void open(FloodgatePlayer player) {

		getForm().setResponseHandler(responseData -> {
			FormResponse response = getForm().parseResponse(responseData);
			onResponse(player, response);
		});

		player.sendForm(getForm());
		open.add(player);

	}

	protected abstract void onResponse(FloodgatePlayer player, FormResponse response);

}
