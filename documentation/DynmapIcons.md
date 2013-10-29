## DynmapIcons
A class of functions to manage the Dynmap icons.

### array dm\_all\_icons():
Returns an array containing all the icon IDs.

### string dm\_create\_icon(newIconID, [label], imageFile):
Registers an icon in Dynmap and returns its ID. The icon ID must be unique among icons and must only contain numbers, letters, periods (.) and underscores (_). If the label is not given, it is equals to the icon ID. The image file must be encoded in PNG.

### void dm\_delete\_icon(iconID):
Deletes an icon (can't be used on builtin icons).

### boolean dm\_icon\_is\_builtin(iconID):
Returns if an icon is builtin.

### string dm\_icon\_label(iconID):
Returns the label of the icon.

### string dm\_icon\_size(iconID):
Returns the size of the icon. Size can be one of MARKER_8x8, MARKER_16x16, or MARKER_32x32.

### void dm\_set\_icon\_image(iconID, file):
Sets the image of the icon (image format must be PNG).

### void dm\_set\_icon\_label(iconID, label):
Sets the label of the icon.