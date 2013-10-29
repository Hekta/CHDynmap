## DynmapEvents
Contains events related to the Dynmap plugin

### dm\_player\_web\_chat
Fires when a player send a message on the Dynmap.
#### Prefilters
**name**: <Macro>  
**processed**: <boolean match>  
**source**: <Macro>
#### Event Data
**message**: the message the player sent  
**name**: the name of the sender  
**processed**: returns if the event has been handled by a plugin  
**source**: from where the message is sent
#### Mutable Fields