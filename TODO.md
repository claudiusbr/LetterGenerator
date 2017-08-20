TODOs
=====


Refactor
--------
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


Test Scenarios
--------------
- Test what would happen if the user provided only one column and a template
  with no variables
  - e.g., someone decides to use the generator to just create the exact same
    copy of a letter, but each with a different filename. How would the
    DetailsValidator work there if it would remove the only column provided
    from the list, therefore giving it an empty list?
- Test if user input can take special characters


improve on usage of Future in a way that the user is updated
------------------------------------------------------------
Set one thread to message the user, another to work on the logic. Something
like a status bar, or three dots.


Apply chain of responsibility
-----------------------------
- InteractionMediator is doing too much. See if it can be split into classes
  which conform to the Chain Of Responsibility pattern, for example
