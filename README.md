# Meowland plugin

This plugin is being developed for the meowland.ru server but anyone can use it.

mindustry build 145.1
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
#bot
bot_enable: true
server_name: server_name
bot_token: token
channel_id: id
admin_role: id
```

### Bot commands

!! `bot_enable` must be true !!

* `/server_name send <text>` - send message to the mindustry server
* `/server_name players` - list of players on the server [ADMIN ONLY CMD]
* `/server_name ban <uuid> <reaon>` - ban player on the server [ADMIN ONLY CMD]
* `/server_name unban <uuid>` - unban player on the server [ADMIN ONLY CMD]
* `/server_name add_map <attachment>` - add map to the server [ADMIN ONLY CMD]
* `/server_name maps` - list of custom maps on the server
