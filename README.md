@author Danish Butt


This is the fourth milestone of the project and contains a gui based playable version of the game with additional features such as save/load features and a level game builder. The rest of the deliverables include the code (source + executable jar file), documentation (design decisions, java docs, user manual), units tests and UML/sequence diagrams. The authors are Omar Elberougy, David Ou, Sudarsana Sandeep, Danish Butt, and Tharsan Sivathasan.

There were no known issues this time.

There were some changes made from last time.The first thing is that a Loader, SaveFile, Saver classes were created to save/load the game using xml. The second change is a Level Builder class was created to build new levels. The third change was that a Moving Piece interface was created. This interface is only used by the fox and rabbit. The last change is that a lot of the previous code was refactored (especially the solver class).
