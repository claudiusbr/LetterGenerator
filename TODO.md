TODOs
=====

File Names
----------
- give option for user to choose filename from existing columns
  - implement the logic on InteractionMediator

Cleanup
-------
- move the ComboBox generation to ElementMaker


Input Validation
----------------
- Test if user input can take special characters


Implement multi-threading: one for end user, one for system
----------------------------------------------------------
Set one thread to message the user, another to work on 
the logic. Something like a status bar, or three dots.


See if there's a way to make implicit conversion to Java HashMap work
----------------------------------------------------------------------
The conversion takes scala Map and mutable.Map and wraps them in a 
Java Map wrapper. If you try to type cast this to Java HashMap it gives
an error. See if there's a way around it


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


