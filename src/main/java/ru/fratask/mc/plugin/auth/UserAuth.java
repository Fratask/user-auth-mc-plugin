package ru.fratask.mc.plugin.auth;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Plugin(id = "user-auth", name = "userAuth")
public class UserAuth {

    private static UserAuth instance;

    @Inject
    Logger logger;

    @Listener
    public void onServerStart(GameInitializationEvent event){
        logger.info("UserAuth has started!");
    }

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event){
        Player player = event.getTargetEntity();
        if (!player.hasPlayedBefore()){
            //First play
            event.setMessageCancelled(true);
            player.sendMessage(Text.of(TextColors.GREEN, "Hello, ", TextColors.YELLOW, player.getName(), TextColors.GREEN, "! Welcome to Fratask's Server!"));
        } else {
            //Played before
            event.setMessageCancelled(true);
            player.sendMessage(Text.of(TextColors.GREEN, "Hello, ", TextColors.YELLOW, player.getName(), TextColors.GREEN, "!"));
        }
        logger.info("Player " + player.getName() + " has joined! LAST_JOIN: " + player.lastPlayed().toString());
    }

    @Listener
    public void onPlayerLeft(ClientConnectionEvent.Disconnect event) {
        Player player = event.getTargetEntity();

        event.setMessageCancelled(true);
    }

    public static UserAuth getInstance() {
        if (instance == null){
            instance = new UserAuth();
        }
        return instance;
    }
}
