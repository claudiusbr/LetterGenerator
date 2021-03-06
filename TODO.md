TODOs
=====

Refactor
--------
- Make a 'parent folder' path which will be shared by the details, template and
  destination 'Open' buttons so that when you choose one the other will open in
  the same folder, rather than in the root folder.


Write a user guide
-------------------------------
- create a documentation folder
- write step-by-step user guide


Test Scenarios
--------------
- Write a test for the Wizard class
  - maybe also for entities in renderer package
- Test what would happen if the user provided only one column and a template
  with no variables
  - e.g., someone decides to use the generator to just create the exact same
    copy of a letter, but each with a different filename. How would the
    DetailsValidator work there if it would remove the only column provided
    from the list, therefore giving it an empty list?
- Test if user input can take special characters
- Test ValidationMediator's duplicate file checker method;


Check if the effort of saving the JaxbElement within draftSaveReset is really necessary
---------------------------------------------------------------------------------------
- actually, see
  [this](https://www.docx4java.org/forums/docx-java-f6/which-methods-to-use-for-the-deprecated-ones-t2373.html):
  it seems `getJaxbElement` is deprecated. Replace it by `getContents()`
  whenever you can.


Crash when "empty File name column" + "'File name also in template' box ticked" on submit
---------------------------------------------------------------------------------------
- this should not be allowed to happen


Find a way to extract only the first line of the details file without loading the whole thing
---------------------------------------------------------------------------------------------
- a large details file as it currently stands could affect performance, as
  after someone clicks on 'Open' it will be loaded to get the headers. Find a
  way to only get the first line, without reading the whole thing.


create a web interface
----------------------
- either as part of or as a subproject


redirect output of docx4j and dependencies
------------------------------------------
- a lot of logging seems to go on from the docx4j package. redirect that to a
  logger or something else.


improve on usage of Future in a way that the user is updated
------------------------------------------------------------
Set one thread to message the user, another to work on the logic. Something
like a status bar, or three dots.


Rewrite the whole thing from scratch
-------------------------------------
- as per comment on 10/Sep/2017.
