# Architecture Description
The architecture of our application is a good visual aid but will be explained here
There are 8 classes for the presentaion layer currently. The notes, course, and Quiz Cards are using their respective business layer Accessors. All these accessors hold a stub database for each specific Object. These stub databases are created in the service class which is in the application layer. 
Then we have a domain specific object heirarchy. Course is seperate but all classes are a subclass of the Item class. Event, Card, ant Tag are all direct children of the Item class, while Note and Quiz and direct children of the Card class. 
