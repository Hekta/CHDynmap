## DynmapMarkerSets
A class of functions to manage the Dynmap markersets.

### array dm\_all\_markersets():
Returns an array of all markersets ID.

### string dm\_create\_markerset(newSetID, [optionArray]):
Creates a markerset and returns its ID. The ID must be unique among markersets and must only contain numbers, letters, periods (.) and underscores (_). The option array is associative and not required, and all its keys are optional.
- KEY - DEFAULT - DESCRIPTION - COMMENT
- allowed\_icons - null - an array of icons allowed in the markerset, null to unrestrict - restriction status can not be changed later, only the list of icons allowed could be modified
- label - setID - the markerset label
- persistent - false - sets if the markerset is persistent and can contain persistent markers - can not be changed later

### string dm\_default\_markerset\_id():
Returns the ID of the default markerset.

### void dm\_delete\_markerset(setID):
Deletes a marker set.

### array dm\_markerset\_allowed\_icons(setID):
Returns an array of icons ID allowed for the set (if restricted, else returns null and any icon can be used in set).

### string dm\_markerset\_default\_icon(setID):
Returns the default icon ID for the markers added to this set.

### boolean dm\_markerset\_hide\_by\_default(setID):
Returns if the markerset is hidden by default.

### array dm\_markerset\_icons\_in\_use(setID):
Sets the default icon of the markerset.

### string dm\_markerset\_label(setID):
Returns the markerset label.

### integer dm\_markerset\_layer\_priority(setID):
Returns the markerset layer ordering priority (0=default, low before high in layer order).

### integer dm\_markerset\_max\_zoom(setID):
Returns the maximum zoom level of the markerset (the markers in the set will be hidden when the zoom level is above this setting). -1 means no maximum. This setting may be ignored on certain markers with the dm\_set\_marker\_max\_zoom() function.

### integer dm\_markerset\_min\_zoom(setID):
Returns the minimum zoom level of the markerset (the markers in the set will be hidden when the zoom level is below this setting). -1 means no minimum. This setting may be ignored on certain markers with the dm\_set\_marker\_min\_zoom() function.

### boolean dm\_markerset\_persistent(setID):
Returns if the markerset is persistent and can contain persistent markers.

### boolean dm\_markerset\_show\_labels(setID):
Returns if labels are shown (if false, hide, show on hover, if null, use global default).

### void dm\_set\_icon\_allowed\_for\_marketset(setID, iconID, boolean):
Sets if an icon is allowed for the markerset (the marketset must have been created restricted).

### void dm\_set\_markerset\_default\_icon(setID, iconID):
Sets the default icon of the markerset.

### void dm\_set\_markerset\_hide\_by\_default(setID, boolean):
Sets if the markerset is hide by default.

### void dm\_set\_markerset\_label(setID, label):
Sets the label of the markerset.

### void dm\_set\_markerset\_layer\_priority(setID, integer):
Sets the layer priority of the markerset (0=default, low before high in layer order).

### void dm\_set\_markerset\_max\_zoom(setID, integer):
Sets the maximum zoom level of the markerset (the markers in the set will be hidden when the zoom level is above this setting). -1 means no maximum. This setting may be ignored on certain markers with the dm\_set\_marker\_max\_zoom() function.

### void dm\_set\_markerset\_min\_zoom(setID, integer):
Sets the minimum zoom level of the markerset (the markers in the set will be hidden when the zoom level is below this setting). -1 means no minimum. This setting may be ignored on certain markers with the dm\_set\_marker\_min\_zoom() function.

### void dm\_set\_markerset\_show\_labels(setID, mixed):
Sets if labels are shown (if false, hide, show on hover, if null, use global default).