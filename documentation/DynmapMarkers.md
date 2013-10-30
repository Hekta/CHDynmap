## DynmapMarkers
A class of functions to manage the Dynmap markers.

### array dm\_all\_markers(setID, [type]):
Returns an associative array containing the ID of all markers in the markerset. If the type is given, only the markers of this type are returne. Type can be one of AREA, CIRCLE, ICON, or POLYLINE.

### string dm\_create\_marker(setID, [optionArray]):
Creates a marker and returns its ID. The option array is associative and not required, and all its keys are optional.
- KEY - DEFAULT - DESCRIPTION - COMMENT
- center - world spawn - the center of the marker - only for circle markers, world is ignored
- corners - world spawn - the corners of the marker - only for area or polyline markers, world is ignored (and also y for area markers)
- icon - null - the icon ID of the marker, null for the markerset default icon - only for icon markers
- id - random - ID of the marker, must be unique within the set, if null or not given, an unique ID is generated
- label - markerID - the label of the marker
- label\_is\_html - false - sets if the label is processing as HTML
- location - world spawn - the location of the marker - only for icon markers, world is ignored
- persistent - false - sets if the label is persistent (saved and reloaded on restart), the markerset must be persistent - can not be changed later
- radius - 0 0 - the radius of the marker - only for circle markers
- type - ICON - the type of the marker, can be one of AREA, CIRCLE, ICON, or POLYLINE - can not be changed later
- world - first world - the world of the marker

### void dm\_delete\_marker(setID, markerID):
Deletes a marker in the set.

### array dm\_marker\_boosted(setID, markerID):
Returns if the marker resolution is boosted. Only for area and circle markers.

### array dm\_marker\_center(setID, markerID):
Returns the location of the marker center. Only for circle markers.

### array dm\_marker\_corners(setID, markerID):
Returns the corners location of the marker. Only for area and polyline markers.

### string dm\_marker\_label(setID, markerID):
Returns the description of the marker.

### array dm\_marker\_fill\_style(setID, markerID):
Returns the fill style array of the marker. Only for area and circle markers.

### string dm\_marker\_icon(setID, markerID):
Returns the icon ID of the marker. Only for icon markers.

### string dm\_marker\_label(setID, markerID):
Returns the label of the marker.

### boolean dm\_marker\_label\_is\_html(setID, markerID):
Returns if the label of the marker is processed as HTML.

### array dm\_marker\_line\_style(setID, markerID):
Returns the line style array of the marker. Only for area, circle and polyline markers.

### string dm\_marker\_loc(setID, markerID):
Returns the location of the marker. Only for icon markers.

### string dm\_marker\_normalized\_world(setID, markerID):
Returns the normalized world of the marker (used for directory and URL names in Dynmap).

### boolean dm\_marker\_persistent(setID, markerID):
Returns if the marker is persistent.

### array dm\_marker\_radius(setID, markerID):
Returns the radius of the marker. Only for circle markers.

### array dm\_marker\_range\_height(setID, markerID):
Returns the range height of the marker. Only for area markers.

### string dm\_marker\_type(setID, markerID):
Returns the type of the marker. Can be one of AREA, CIRCLE, ICON, POLYLINE, or UNKNOWN.

### string dm\_marker\_world(setID, markerID):
Returns the world of the marker.

### void dm\_set\_marker\_boosted(setID, markerID, boolean):
Sets if the marker resolution is boosted. Only for area and circle markers.

### void dm\_set\_marker\_center(setID, markerID, locationArray):
Sets the center of a marker. Only for circle markers.

### void dm\_set\_marker\_corners(setID, markerID, array):
Sets the location of the marker corners (array of location arrays, world is ignored, and for area markers y is ignored). Only for area and polyline markers.

### void dm\_set\_marker\_description(setID, markerID, htmlDescription):
Sets the description of the marker (in HTML).

### void dm\_set\_marker\_fill\_style(setID, markerID, array):
Sets the marker fill style (array with "color" and "opacity" optional keys, color is a color r g b array, and opacity a number between 0 and 1 inclusive). Only for area and circle markers

### void dm\_set\_marker\_icon(setID, markerID, iconID):
Sets the icon of a marker. Only for icon markers.

### void dm\_set\_marker\_label(setID, markerID, label, [isHTML]):
Sets the label of the marker, isHTML is a boolean, if true, label will be processed as HTML.

### void dm\_set\_marker\_line\_style(setID, markerID, array):
Sets the marker line style (array with "color", "opacity" and "weight" optional keys, color is a color r g b array, opacity a number between 0 and 1 inclusive and weight is an integer). Only for area, circle and polyline markers.

### void dm\_set\_marker\_loc(setID, markerID, locationArray):
Sets the icon location of a marker. Only for icon markers.

### void dm\_set\_marker\_markerset(setID, markerID, newSetID):
Changes the markerset of the marker.

### void dm\_set\_marker\_radius(setID, markerID, array):
Sets the radius of the marker (array with "x" and "z" keys). Only for circle markers.

### void dm\_set\_marker\_range\_height(setID, markerID, array):
Sets the range height of a marker (array with "top" and "bottom" keys). Only for area markers.