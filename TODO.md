TODOs
=====

Make the text on the first label be dependent on input rather than hardcoded
-----------------------------------------------------------------------------
Currently the text on the label reads 'please choose the csv file'. This should
not be hardcoded, but rather find the filetype depending on how input is being
processed.

Test if user input can take special characters
----------------------------------------------
Add some kind of validation to it to make sure it does.


Fault tolerance?
----------------
What if the user input is incomplete for any of the variables? Should the 
whole thing just fail, or should we prompt user for input?
- if we need to deliver soon, just let it fail
- if fault tolerance is more important, do it.


Implement multithreading: one for end user, one for system
----------------------------------------------------------
Set one thread to message the user, another to work on 
the logic. Something like a status bar, or three dots.