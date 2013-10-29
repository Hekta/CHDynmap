## DynmapPlayerSets
A class of functions to manage the Dynmap playersets.

### array dm\_all\_playersets():
Returns an array containing all the playerset IDs.

### string dm\_create\_playerset(newSetID, [optionArray]):
Creates a playerset and returns its ID.

The ID must be unique among playersets and must only contain numbers, letters, periods (.) and underscores (_). The option array is associative and not required, and all its keys are optional. <li>KEY - DEFAULT - DESCRIPTION - COMMENT</li> <li>persistent - false - sets if the playerset is persistent - can not be changed later</li> <li>players - empty array - an array of players the playerset will contain</li> <li>symmetric - false - sets if the playerset will be symmetric (players in set can see other the players in set)</li>

### void dm\_delete\_playerset(setID):
Deletes a playerset.

### boolean dm\_pis\_in\_playerset(setID, [playerName]):
Returns if a player is in the playerset. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### array dm\_players\_in\_playerset(setID):
Returns an array containing all the players in the playerset.

### boolean dm\_playerset\_persistent(setID):
Returns if the playerset is persistent.

### boolean dm\_playerset\_symmetric(setID):
Returns if the playerset is symmetric (if true players in set can see other the players in set).

### void dm\_set\_pis\_in\_playerset(setID, [playerName], boolean):
Sets if a player is in the playerset. This will not throw a PlayerOfflineException (exept from console), so the name must be exact.

### void dm\_set\_players\_in\_playerset(setID, array):
Sets the players in the playerset. This will not throw a PlayerOfflineException, so the name must be exact.

### void dm\_set\_playerset\_symmetric(setID, boolean):
Sets if the playerset is symmetric (if true, players in set can see the players in set, if false, privilege is always required).