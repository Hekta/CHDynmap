## DynmapManagement
A class of functions that allows to manage and use general features of Dynmap.

### void dm\_broadcast\_to\_web(message, [sender]):
Send a generic message to all web users, sender is the label for the sender of the message, could be null, message is the message to be sent.

### boolean dm\_full\_radius\_renders\_paused():
Returns if full and radius renders are paused.

### void dm\_render\_block(locationArray):
Trigger update on tiles at the given block location.

### void dm\_render\_volume(locationArray, locationArray):
Trigger update on tiles in the given volume. The volume is a cuboid defined by the two locations (opposite corners).

### void dm\_set\_chat\_to\_web\_enabled(boolean):
Sets if chat to web processing is enabled or not.

### void dm\_set\_full\_radius\_renders\_paused(boolean):
Sets if full and radius renders are paused.

### void dm\_set\_update\_renders\_paused(boolean):
Sets if update renders are paused.

### boolean dm\_update\_renders\_paused():
Returns if update renders are paused.