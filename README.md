# AMFPokeEgg
An PokeEgg to catch and release mobs

Tested on PaperMC 1.20.2

# Features
- [x] command to give the poke
- [x] catch entities and add the basic lore
- [x] release entities
- [x] configure material type of poke
- [x] configure name and lore of poke
- [x] configure what entities can be catch
- [ ] configure chance per mobs
- [ ] configure if use original entity eggs (this can prevent change spawner type, but, if you want, you can use original eggs)
- [ ] configure permission by entity
- [ ] create a craft for unique or reusable
- [ ] add reusable items
- [x] add placeholders for messages
- [ ] look at the face of block clicked and add +1 block in direction of player, to prevent mob stuck on wall

# Integrations
- [x] prevent catch and release mobs on protected area on RedProtec
- [x] prevent catch and release mobs on protected area on WorldGuard
- [ ] prevent catch and release mobs on protected area on GriefPrevention
- [ ] prevent catch and release mobs on protected area on Towny
- [ ] prevent catch pets from Citizens
- [ ] prevent catch pets from MyPet
- [ ] prevent catch pets from SimplePets
- [ ] configue if can catch or not MythicMob

# Issues/References

- RedProtect block interaction with entities before us, so, some entities can be permited with "interact with animals", some others no. Anyway, player cannot cautch entity, but error message will be trigged by redprotec
- RedProtect can enable capture and release on some entities if "interact with animals" are enabled, so we block
- WordGuard if "interact" flag are enabled, so we block
- WordGuard if "ride" flag are enabled, so we block

# References

https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html
https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/entity/EntityType.html

# Compile

just clone this repository, and run command

```bash
mvn compile && mvn package
```