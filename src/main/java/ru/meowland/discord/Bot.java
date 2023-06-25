package ru.meowland.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import ru.meowland.config.Config;
import ru.meowland.discord.commands.Command;

public class Bot {

    public static JDA jda;
    public static final String cmdMindustry = "mindustry";
    public static void init(){
        String token = Config.get("bot_token");
        jda = JDABuilder.createLight(token)
                .addEventListeners(new Command(), new ButtonListener())
                .build();
        ServerLogger.init();
    }

}
