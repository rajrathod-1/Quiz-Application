# Architecture Description

![Architecture Diagram](https://code.cs.umanitoba.ca/comp3350-winter2024/jockiesonmonkies-a02-4/-/raw/main/docs/Arcitecture_Iteration_2.png?ref_type=heads)

## Description

The architecture of our application is a good visual aid and will be explained here:


The first major segment of our diagram is the presentation layer. This page details all of the
functions that are used to control the user interface. These functions are both connected to logic 
layer components and to other classes within the presentation layer. Functions within this layer 
will be used to show the user information from the app and for the user to input functions into the 
app.

The second component is the logic layer. This layer is where data will be converted into usable 
data and will also be used to extract usable data from the persistence layer. The classes in this 
layer all have connections to the services class and to their respective presentation layer 
components. THe services class will control the connection to the persistence layer.

The third components is the Persistence layer. This layer is important in that it will be used to 
control the connection to either the stub or the HSQLDB databases. It does this by having a 
interface that will be implemented by either the stub or HSQLDB databases. When we are using the 
application for testing the databased used will be the stub one while when we are doing real 
operations we will be using the HSQLDB connection.

The last component is the domain specific objects. These are the objects that all of the other 
components will use to implement our application.
