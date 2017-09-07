TODOs
=====

Refactor
--------
- make methods smaller and stop this thing of 'one method calling the next'.
  It's making for really large method signatures. Instead, have sets of small
  methods and get them all called by the main method (this applies for both
  InteractionMediator and DocxMaker)
  - done.
- this may have made DocxMaker a bit too big. Examine whether it's worth
  creating a new entity.
- make each method in InteractionMediator a standalone one and make one method
  responsible for calling them and checking if they are ok, or if they returned
  an exception, and communicate any relevant details to the user. E.g.:
  ```
  // have all methods return a boolean
  coordinate() {
    if (validatePaths) {
      messageUser("Files exist")
      if(loadDetails) {
        messageUser("details loaded")
        if (validateDetails) {
          
        } else {
          messageUser("could not validate details")
        }
      } else {messageUser("could not load details. make sure they are correct")}
    } else {messageUser("check the ??? file")}
  }

  // have all methods return a string, and allow them to throw 
  // exceptions which can then be sent to the user
  try for (method <- InteractionMediator.methods) {// or something like that (reflection, a method collection, etc)
      messageUser(method)
  } catch {
    case e: Throwable => alertUser({
      import java.io._
      val s = new StringWriter
      e.printStackTrace(new PrintWriter(s))
      alertUser(s.toString)
    })
  }
  ```

Test Scenarios
--------------
- Test what would happen if the user provided only one column and a template
  with no variables
  - e.g., someone decides to use the generator to just create the exact same
    copy of a letter, but each with a different filename. How would the
    DetailsValidator work there if it would remove the only column provided
    from the list, therefore giving it an empty list?
- Test if user input can take special characters
- Test ValidationMediator's duplicate file checker method;
- Test DocxMakerFormatter;


Check if the effort of saving the JaxbElement within draftSaveReset is really necessary
---------------------------------------------------------------------------------------
- actually, see
  [this](https://www.docx4java.org/forums/docx-java-f6/which-methods-to-use-for-the-deprecated-ones-t2373.html):
  it seems `getJaxbElement` is deprecated. Replace it by `getContents()`
  whenever you can.



Something wrong with changing the details file after one is entered and a filename column is chosen
---------------------------------------------------------------------------------------------------
- An exception is being thrown. Analyse what it is.


Validate "empty File name column" + "'File name also in template' box ticked" on submit
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


give user option of allowing empty cells in columns
---------------------------------------------------
- checkbox allowing details.csv cells to contain empty values.


redirect output of docx4j and dependencies
------------------------------------------
- a lot of logging seems to go on from the docx4j package. redirect that to a
  logger or something else.


improve on usage of Future in a way that the user is updated
------------------------------------------------------------
Set one thread to message the user, another to work on the logic. Something
like a status bar, or three dots.


Apply chain of responsibility
-----------------------------
- InteractionMediator is doing too much. See if it can be split into classes
  which conform to the Chain Of Responsibility pattern, for example
