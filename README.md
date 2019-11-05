@author Danish Butt


This is the second milestone of the project and contains a gui based playable version of the game. The rest of the deliverables include the code (source + executable jar file), documentation (design decisions, java docs, user manual), units tests and UML/sequence diagrams. The authors are Omar Elberougy, David Ou, Sudarsana Sandeep, Danish Butt, and Tharsan Sivathasan.

//KNOWN ISSUES

There were some changes made from last time. The first thing is that the game/command words class was deleted as it was no longer needed as the view takes care of it. The parser class was also deleted as it is being replaced by the mouse. Lastly, the move method was moved into each of the rabbit/foxes class to allow for high cohesion. This was done to fix the previous issue of low cohesion with the board class. 

The plan for the next milestone will be to add a solver and an unlimited redo button. 
