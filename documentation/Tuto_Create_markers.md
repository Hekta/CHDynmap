## Add custom markers with CHDynmap

First, we need to choose in which set of marker, our marker will be.
Markersets are used to separates the markers created by different plugins or which have a different utility.
For instance the WorldBorder plugin uses its own set, called `WorldBorder`. The default markerset, which contains for instance the icons on worlds spawn, is `markers`.

To create a new markerset:

    dm_create_markerset('test', array('label': 'Test'))

Normally a new markerset has appeared in the list on the Dynmap. We can add markers in it.

There is 4 types of markers:
- area, a 2D polygonal surface (like the markers created by WorldGuard-Dynmap for the regions by default)
- circle, a flat round region (like the markers created by WorldBorder for the round borders)
- icon, simple icon, like the spawn marker that appears in each worlds
- polyline, rarely used, used to create 3D markers

Each marker is customizable (size, form, color, icon, ...).

To create a marker:

    dm_create_marker('test', array('id': 'city', 'radius': array('x': 100, 'z': 50), 'type': 'CIRCLE'))

This will create an ellipsoid marker in the set `test`, centered on the spawn of the default world.

Now we can customize the marker:

    dm_set_marker_fill_style('test', 'city', array('color': array('r': 0, 'g': 255, 'b': 0)))

This will set the fill color of the marker `city`, in the markerset `test`, to green.