## DynmapPlayers
A class of functions using the Dynmap features for players.

### void dm\_assert\_pinvisibility([playerName], boolean | playerName, boolean, [pluginID]):
Asserts the player invisibility (transient, if player is configured to be hidden, it is made visibile if one or more plugins assert its visibility), pluginID is the id that will be used to assert, default to 'CommandHelper'. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_assert\_pvisibility([playerName], boolean | playerName, boolean, [pluginID]):
Asserts the player visibility (transient, if player is configured to be visible, it is made hidden if one or more plugins assert its invisibility), pluginID is the id that will be used to assert, default to 'CommandHelper'. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_pcan\_see([playerName], otherPlayerName):
Returns if the player can see the other player. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_post\_pchat([playerName], message | playerName, message, [displayName]):
Post message from player to web, if displayName is an empty string, it is not noticed. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_post\_pjoin([playerName]):
Post a join message for player to web. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_post\_pquit([playerName]):
Post a quit message for player to web. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### boolean dm\_pvisible([playerName]):
Returns if the player is visible on the Dynmap. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_set\_pvisible([playerName], boolean):
Sets if the player is visible on the Dynmap. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.