TODOs
=====

create a web interface
----------------------
- either as part of or as a subproject


give user option of allowing empty cells in columns
---------------------------------------------------
- checkbox allowing details.csv cells to contain empty values.


redirect output of docx4j and dependencies
------------------------------------------
- a lot of logging seems to go on from the docx4j package. redirect that to a
  logger or something else.


DONE: optimise validateDetails' 'f' function
--------------------------------------
- currently creating too many stack frames. Make it tail recursive, somehow.
  - made validateDetails imperative


Test Scenarios
--------------
- Test what would happen if the user provided only one column and a template
  with no variables
  - e.g., someone decides to use the generator to just create the exact same
    copy of a letter, but each with a different filename. How would the
    DetailsValidator work there if it would remove the only column provided
    from the list, therefore giving it an empty list?


Input Validation
----------------
- Test if user input can take special characters


improve on usage of Future in a way that the user is updated
------------------------------------------------------------
Set one thread to message the user, another to work on the logic. Something
like a status bar, or three dots.


Apply chain of responsibility
-----------------------------
- InteractionMediator is doing too much. See if it can be split into classes
  which conform to the Chain Of Responsibility pattern, for example
