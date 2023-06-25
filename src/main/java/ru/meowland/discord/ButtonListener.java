package ru.meowland.discord;

import arc.util.Log;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import ru.meowland.config.Bundle;

import java.util.List;
import java.util.Objects;

public class ButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        switch (Objects.requireNonNull(event.getButton().getId())){
            case "accept" -> {
                Message message = event.getMessage();
                List<MessageEmbed> embed = event.getMessage().getEmbeds();
                MessageEmbed messageEmbed = embed.get(0);
                String uuid = null;
                for(MessageEmbed.Field field : messageEmbed.getFields()){
                    if(Objects.equals(field.getName(), "UUID")){
                        uuid = field.getValue();
                        Player player = Groups.player.find(p -> p.uuid().equals(field.getValue()));
                        Log.info(uuid);
                        player.admin(true);
                        message.reply(Bundle.get("discord.successful")).queue();
                        message.delete().queue();
                    }
                }


            }
            case "deny" -> {
                Message message = event.getMessage();
                message.reply(Bundle.get("discord.deny")).queue();
                message.delete().queue();
            }
        }
    }
}
