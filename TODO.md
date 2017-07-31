TODOs
=====

Make the text on the first label be dependent on input rather than hardcoded
-----------------------------------------------------------------------------
Currently the text on the label reads 'please choose the csv file'. This should
not be hardcoded, but rather find the filetype depending on how input is being
processed.

Implement multithreading: one for end user, one for system
----------------------------------------------------------
Set one thread to message the user, another to work on 
the logic. Something like a status bar, or three dots.