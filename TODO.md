TODOs
=====

Make the text on the first label be dependent on input rather than hardcoded
-----------------------------------------------------------------------------
Currently the text on the label reads 'please choose the csv file'. This should
not be hard-coded, but rather find the file type depending on how input is being
processed.

Input Validation
----------------
- finish validation on InteractionMediator
- Test if user input can take special characters
- Protect against wrong file types being chosen (for input)


Write tests
-----------
- for all methods

Apply chain of responsibility
-----------------------------
- InteractionMediator is doing too much. See if it can be split into classes
  which conform to the Chain Of Responsibility pattern, for example


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
