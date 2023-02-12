# Meowland plugin

This plugin is being developed for the meowland.ru server but anyone can use it.

mindustry build 141+
### Player commands

* `/wave` - vote for wave skip. 
* `/rtv` - vote for map change.
* `/shiza <text...>` - send a message to yourself. 

### Admin commands

* `/spawn <Unit> <Count> <Team>` - spawn units.
* `/team <Team>` - change your team.
* `/despw` - kill all units.
* `/spawncore <small|medium|large>` - spawn core.
* `/js <code>` - run JavaScript code
* `/setblock <block>` - spawn block 
* `/advertisement <text...>` - send gui menu with message
* `/effect` - add beautiful effect to player
### Permissions

All command permissions can be changed in the config (config/mods/Meowland/config.yml)

```yml
#Permissions.
#If it is true then the players will be able to use the command.
#If it is false then only admins will can able to use the command.
despw: false
spawn: false
team: false
shiza: true
rtv: true
wave: true 
spawncore: false
js: false
setblock: false
advertisement: false
effect: false
```

### Discord integration

The output of messages and events to the discord is configured in the config (config/mods/MeowLand/config.yml)
```yml
#Discord integration settings
#webhook
enable: false
webhook_url: url
channel_id: id
avatar_url: https://github.com/Anuken/Mindustry/blob/master/core/assets-raw/sprites/units/corvus.png?raw=true
server_name: test server
#bot
bot_enable: true
bot_token: token
channel_id: id
bot_prefix: !
```

### Bot commands

!! `bot_enable` must be true !!

* `m!send <text>` - send message on mindustry server
* `m!players` - display all players at the server\
* `m!ban <uuid>` - ban player (need kick). Admin only cmd
* `m!unban <uuid>` - unban player. Admin only cmd
* `m!add_map <file.msav>` - add map on server. Admin only cmd. Need server restart

