package ru.fratask.mc.plugin.auth.test;

import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import ru.fratask.mc.plugin.auth.UserAuth;

import java.lang.reflect.Field;
import java.time.Instant;

public class UserAuthTest {

    @Test
    public void userAuthTest() throws NoSuchFieldException, IllegalAccessException {
        UserAuth userAuth = UserAuth.getInstance();
        Field loggerField = userAuth.getClass().getDeclaredField("logger");
        loggerField.setAccessible(true);
        loggerField.set(userAuth, Mockito.mock(Logger.class));
        ClientConnectionEvent.Join event = Mockito.mock(ClientConnectionEvent.Join.class);
        Player player = Mockito.mock(Player.class);
        int counter = 0;
        Mockito.when(player.getName()).thenReturn("Player");
        Value<Instant> instantValue = Mockito.mock(Value.class);
        Mockito.when(instantValue.toString()).thenReturn("123");
        Mockito.when(player.lastPlayed()).thenReturn(instantValue);
        Mockito.when(event.getTargetEntity()).thenReturn(player);

        Mockito.when(event.getTargetEntity().hasPlayedBefore()).thenReturn(false);
        UserAuth.getInstance().onPlayerJoin(event);
        counter++;

        Mockito.verify(event.getTargetEntity(), Mockito.times(counter)).sendMessage((Text) Mockito.any());

        Mockito.when(event.getTargetEntity().hasPlayedBefore()).thenReturn(true);
        UserAuth.getInstance().onPlayerJoin(event);
        counter++;

        Mockito.verify(event.getTargetEntity(), Mockito.times(counter)).sendMessage((Text) Mockito.any());


    }

}
