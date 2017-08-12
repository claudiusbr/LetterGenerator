TODOs
=====

Make a popup which prints stack traces
--------------------------------------


Improve versatility
-------------------
- currently it can handle simple schemas and variables which only span single lines.
  - read through the docx4j documentation and see if there is a better way to
    handle the sharepoint schemas;
  - check if using scala xml would have a better effect on the variables, or if
    there is a better way of doing find-replace of variables than the current one


Test Scenarios
--------------
- Test what would happen if the user provided only one column and a template with no variables
  - e.g., someone decides to use the generator to just create the exact same
    copy of a letter, but each with a different filename. How would the
    DetailsValidator work there if it would remove the only column provided
    from the list, therefore giving it an empty list?


Input Validation
----------------
- Test if user input can take special characters


Implement multi-threading: one for end user, one for system
----------------------------------------------------------
Set one thread to message the user, another to work on 
the logic. Something like a status bar, or three dots.


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
