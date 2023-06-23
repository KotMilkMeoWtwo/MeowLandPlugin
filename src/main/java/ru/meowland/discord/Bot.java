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
                .addEventListeners(new Command())
                .build();
    }


    /*
    public void bot() throws LoginException {
        if(Config.get("bot_enable").equals("true")){
            Log.info("Meowland: bot started");
            JDA jda = JDABuilder.createLight(Config.get("bot_token")).addEventListeners(this).build();

            Events.on(EventType.PlayerJoin.class, event ->{
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.join"),  false);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
                eb.setColor(3211008);
                ch.sendMessageEmbeds(eb.build()).queue();
            });
            Events.on(EventType.PlayerLeave.class, event -> {
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.leave"), false);
                eb.setColor(9109504);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()-1), false);
                ch.sendMessageEmbeds(eb.build()).queue();
            });
            Events.on(EventType.PlayerChatEvent.class, event ->{
                Player player = event.player;
                GuildMessageChannel ch = jda.getChannelById(GuildMessageChannel.class, Config.get("channel_id"));
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(player.name, Bundle.get("discord.send") + event.message, false);
                eb.setColor(15258703);
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
                ch.sendMessageEmbeds(eb.build()).queue();
            });

        }
    }


    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        Message msg = event.getMessage();
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "send") && !msg.getAuthor().isBot()){
            MessageChannel channel = event.getChannel();
            channel.sendMessage(Bundle.get("discord.sends")).queue();
            Call.sendMessage("[blue]Discord[] " + msg.getAuthor().toString().replace("U:", "").replaceAll("[\\(1234567890\\)]", "") + ": " + msg.getContentRaw().replace(Config.get("bot_prefix") + "send", ""));
        }

        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "players") && !msg.getAuthor().isBot()){
            if(Objects.requireNonNull(msg.getGuild().getMemberById(msg.getAuthor().getId())).hasPermission(Permission.ADMINISTRATOR)){
                MessageChannel channel = event.getChannel();
                StringBuilder builder = new StringBuilder();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(Config.get("server_name"));
                eb.addField(Bundle.get("discord.count"), String.valueOf(Groups.player.size()), false);
                Groups.player.each(p ->{
                    builder.append(p.name).append(" uuid: ").append(p.uuid()).append(" admin: "+ p.admin()).append("\n");
                });
                eb.addField(Bundle.get("discord.players"), builder.toString(), false);
                channel.sendMessageEmbeds(eb.build()).queue();
            }else {
                msg.reply(Bundle.get("commands.permission-denied")).queue();
            }

        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "ban") && !msg.getAuthor().isBot()){
            Member author =  msg.getMember();
            if(author.getRoles().stream().flatMap(p -> p.getPermissions().stream()).anyMatch(p -> p == Permission.ADMINISTRATOR)) {
                NetServer server = new NetServer();
                MessageChannel channel = event.getChannel();
                server.admins.banPlayer(msg.getContentRaw().replace(Config.get("bot_prefix") + "ban ", ""));
                channel.sendMessage(Bundle.get("discord.successful")).queue();
            }
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "unban") && !msg.getAuthor().isBot()){
            Member author =  msg.getMember();
            if(author.getRoles().stream().flatMap(p -> p.getPermissions().stream()).anyMatch(
                    p -> p == Permission.ADMINISTRATOR)){
                NetServer server = new NetServer();
                MessageChannel channel = event.getChannel();
                server.admins.unbanPlayerID(msg.getContentRaw().replace(Config.get("bot_prefix") + "unban ", ""));
                channel.sendMessage(Bundle.get("discord.successful")).queue();
            }
        }

        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "help") && !msg.getAuthor().isBot()){
            EmbedBuilder eb = new EmbedBuilder();
            MessageChannel channel = event.getChannel();
            eb.setTitle(Bundle.get("discord.help"));
            eb.addField("1.",  Config.get("bot_prefix") + "send " + Bundle.get("discord.help.send"), false);
            eb.addField("2.",  Config.get("bot_prefix") + "players " + Bundle.get("discord.help.players"), false);
            eb.addField("3.",  Config.get("bot_prefix") + "ban " + Bundle.get("discord.help.ban"), false);
            eb.addField("4.",  Config.get("bot_prefix") + "unban " + Bundle.get("discord.help.unban"), false);
            eb.addField("5.",  Config.get("bot_prefix") + "add_map"  + Bundle.get("discord.help.add_map"), false);
            channel.sendMessageEmbeds(eb.build()).queue();
        }
        if(msg.getContentRaw().startsWith(Config.get("bot_prefix") + "add_map") && !msg.getAuthor().isBot()){
            Member author = msg.getMember();
            if(author.getRoles().stream().flatMap(p-> p.getPermissions().stream()).anyMatch(
                    p-> p == Permission.ADMINISTRATOR
            )){
                MessageChannel channel = event.getChannel();
                var attachments = msg.getAttachments();
                if(attachments.size() != 1){
                    channel.sendMessage(Bundle.get("discord.no_file")).queue();
                    return;
                }
                InputStream file = attachments.get(0).retrieveInputStream().join();
                Core.settings.getDataDirectory().child("/maps/" + /*Math.random()*999999999  UUID.randomUUID().toString() + ".msav").write(file, false);
                Vars.maps.reload();
                channel.sendMessage(Bundle.get("discord.successful")).queue();
            }
        }

    }
    */
}
