# Architecture Description

![Architecture Diagram](https://code.cs.umanitoba.ca/comp3350-winter2024/jockiesonmonkies-a02-4/-/raw/main/docs/Architecture_Iteration_3.drawio.png?ref_type=heads)

## Description

The architecture of our application is a good visual aid and will be explained here:


Our architecture diagram for iteration 3 is very similar to the architecture diagram that was created for iteration 2.

One noticeable difference in this diagram is the creation of new validator classes within the Logic layer.
These validator classes will be used to make sure that data that is inputted into our app is able to be 
properly stored within our data base and that it is of the expected datatype for its corresponding data input.

Another notable difference is the inclusion of removal of classes pertaining to our calendar implementation and
the addition of new classes to make up for the loss of those previous classes. We did this to allow for
less coupling between separate classes.

Another difference in our chart is the inclusion of the Main class and arrows indicating its effect
in creating our database. The main class creates the connection for the database that the HSQLDB persistence
implementations use.
