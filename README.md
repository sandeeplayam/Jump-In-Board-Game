@author Danish Butt


This is the third milestone of the project and contains a gui based playable version of the game with additional features such as undo/redo and a solver. The rest of the deliverables include the code (source + executable jar file), documentation (design decisions, java docs, user manual), units tests and UML/sequence diagrams. The authors are Omar Elberougy, David Ou, Sudarsana Sandeep, Danish Butt, and Tharsan Sivathasan.

One known issue is that the hint button only works on the first level (not on the other levels).

There were some changes made from last time. The first thing is that the view and controller were separated into 2 different components. In the last iteration they were both in the view class. The second thing is that undo/redo methods were added in the fox/rabbit/board classes. The last thing is that an ActionStorage/Solver class was created. The actionstorage class is used for doing the undo/redo and the solver class is used to solve the game.

The plan for the next milestone will be to add a solve/load feature and a game level builder.
