# Retrospective - COMP 3350 A02 Group 4

We think that one part of the project that has not been as successful is the notes feature. 
When iteration 2 completed, it was connected to the database which was important. 
However there were a few things that needed a bit more work. Firstly, the notes are all the same even if you select different courses. 
Our vision for the project was to have individualized notes and quizzes for each course. 
Secondly, the notes would save to the database constantly which is nice in that you never have to hit anything to save, and if anything were to happen it would be saved right where you were, though this created a problem. 
When you were typing in a note, there was a lot of delay from when it showed up on the screen. 
Lastly, when you would edit a note, and then try to sort, it wouldn’t do anything. 
order to sort the notes, you would have to leave the notes page, then go back in, and then it would sort. 
Obviously this is not great for user experience.

In order to have the notes attached to a course, we need to have the ability to access the course after leaving the course page and entering its note page. 
Then whenever a new note is being added, it can be attached to the course in the database. 
Then when you call the function to get notes, and send in the course info, you can get the courses associated with that course. 
To fix the issue of lag when editing a note, we think it might be better to add a button to save the note after you edit it. 
Although it may not be as reliable as constantly saving, in most cases, it is fine to save after editing a bit, especially if it eliminates the lag. So we need to add a save button, and then update the note in the database when it’s clicked. I’m honestly not sure what is wrong with the sorting, we have tried to fix it but to no success. We think having to redo the way notes are saved will make us rethink the logic of that process. Hopefully that and the extra time will help us figure it out. We will measure the success of the fixes by the ability to complete all these tasks successfully without the app crashing, and in a user friendly manor. 
(Note from after fixing the sorting of notes. 
One of the problems we didn’t know was a problem was that when the note was edited, it would send the change to the database and when it was sent, it would include  the date/time it was changed in the call. However, there was some miscommunication and it turned out that the SQL to update the database was not including the new date/time so it wasn’t sorting by last updated, only when they were originally made.)

Below is our Velocity Chart
![Velocity Chart](https://code.cs.umanitoba.ca/comp3350-winter2024/jockiesonmonkies-a02-4/-/raw/main/docs/ProjectVelocity.png?ref_type=heads)