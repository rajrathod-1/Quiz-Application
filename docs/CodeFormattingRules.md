## JockiesOnMonkies Code Formatting Rules

# Git Branch Naming 

All branches that are opened by the developer must be named in the following style "<{developerLastName}{first Letter of developer first name}-{Issue Number}-{description of problem}>".

# Package Formatting

Within the srysy directory we will have the different packages for each layer of our app. These layers will be Application, Objects, Business, Persistence, and Presentation. These folder will contain the different code for each part of our app. The application layer will contain the reference to the different databases that will be used for all of our features. Right now this layer connects the business layer to the stub database. The second layer is the Objects layer. This layer contains the objects that will be used to order the data that will be presented on our app. After that we have the business layer which will contain the functions that will act on the data that is given from the databse layer. This layer will make sure that all of our information is properly shown on the presentation layer and will control additions and deletions of our objects. Persistence stores the stub databases and the interfaces that will be used to connect the real database in future iterations. Lastly we have the Presentation layer which controls the items that are demonstrated via the xml files.



