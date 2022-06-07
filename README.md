# Meowland plugin

This plugin is being developed for the meowland.ru server but anyone can use it.

### Player commands

* `/wave` - vote for wave skip. 
* `/rtv` - vote for map change.
* `/shiza <text...>` - send a message to yourself. 

### Admin commands

* `/spawn <Unit> <Count> <Team>` - spawn units.
* `/team <Team>` - change your team.
* `/despw` - kill all units.
* `/spawncore <small|medium|large>` - spawn core.


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
```

### Discord integration

The output of messages and events to the discord is configured in the config (config/mods/MeowLand/config.yml)
```yml
#Discord integration settings
enable: true
webhook_url: url
channel_id: id
avatar_url: https://github.com/Anuken/Mindustry/blob/master/core/assets-raw/sprites/units/corvus.png?raw=true
server_name: test server
```
