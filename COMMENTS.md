Comments
========

Questions:
----------
- Is there a way to make multithreading work for us when generating the docx?
  As in, is it possible to create several DocxMakers, one per thread, each with
  its own copy of the template and a chunk of the details file to save
  documents to disk concurrently? Is it even possible to save more than one
  document to disk at once? you can have multiple processors, but in a local
  machine they are still written to disk one at a time... Or maybe it varies
  depending on the block size, as in there could be more than one file in the
  same block, depending on how big the block is...? In any case, these are
  "ideas" for now, so still need to verify if they are possible.


Log:
----

### 25/Sep/2017
- make a branch where all the packages are organised in a more modular rather
  than layered fashion, e.g. all Template Docx entities together, then all the
  Details/Csv, then one for all the hybrid stuff.
  - actually I will leave this for now.



### 23/Sep/2017
#### Something wrong with changing the details file after one is entered and a filename column is chosen
- An exception is being thrown. Analyse what it is.
  - turns out the exception was happening because whenever a new details file
    is chosen, before the text in the textfield changed to the path of the new
    file, it would change to empty. This caused the `detailsFileHeaders` method
    of `InteractionMediator` to be called with an empty file path, and caused
    it to crash before the new one could be loaded. I have added a guard
    condition for this now, so the method should be working.

#### Write a test for inputs with commas for DetailsFmtTester
- oh yea, I've also done this one.

#### Try to maker DocxGenerator smaller -- as shown by the test, it's still doing too much.
- I think I'm ok with it for now.

#### give user option of allowing empty cells in columns
- checkbox allowing details.csv cells to contain empty values.
  - did this earlier, forgot to remove the TODO on previous commit





### 22/Sep/2017
- write a nicer readme 
  - done


### 15/Sep/2017
- Test DocxMakerFormatter;
  - this actually already happened and I forgot to remove.


### 11/Sep/2017
- fix red test for DocxMedTester.
  - I feel silly...


### 10/Sep/2017
#### Package Organisation
Was talking to someone today about the package organisation, and how I think
the way I have organised them is generating a lot of code smell -- the mediator
package seems to be importing from a lot of them.  I think I've added a todo to
add a branch and refactor it all as such.

#### Is it time for a rewrite?
Been thinking whether I should apply 'courage' and ditch the whole thing, and
start it again from scratch. But this time, try to use diagrams to model the
problem and solution domain before I start coding. Added a TODO for that, but
may never get to it.


### 09/Sep/2017
#### Complex Objects
Other refactoring points are done, or I changed my mind about them. One that
seems to still be baffling me though is the DocxMediator, which I am unable to
test due to it using too complex object, which call on final methods and cannot
be stubbed, for example. Test the base entities instead, and think of
DocxMediator as a main method, rather than a component. Something to think
about, but for now the TODO is to test all the base units.
