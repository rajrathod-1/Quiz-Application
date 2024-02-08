# Architecture

## Diagram
![Architecture Diagram](https://code.cs.umanitoba.ca/comp3350-winter2024/jockiesonmonkies-a02-4/-/raw/main/Arcitecture_Iteration_1.drawio.png?ref_type=heads)

## Description

The architecture of our application is a good visual aid and will be explained here:

There are 8 classes for the presentaion layer currently. The notes, course, and Quiz Cards are using their respective business layer Accessors. All these accessors hold a stub database for each specific Object. These stub databases are created in the service class which is in the application layer.
Then we have a domain specific object hierarchy. Course is separate but all other classes are a subclass of the Item class. Event, Card, ant Tag are all direct children of the Item class, while Note and Quiz and direct children of the Card class.

In the folder in app.java.src.main.java is the comp3350.srsys folder which holds all of our java code. The code is separated into:
Presentation: UI functionality 
Persistence: Stub databases and their interfaces
Objects: all DSOs are held in this folder
Business: Validators and data accessors
Application: has service class that creates database stubs

Then in the folder app is the res folder that holds all UI elements, including the layout folder which has all our .xml files that make up the UI skeleton.

