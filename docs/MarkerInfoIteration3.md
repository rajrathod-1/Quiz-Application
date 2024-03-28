**Application Description:** 
The QuizMonkies app starts the user off on our course page. Within this course page there is the 
functionality to go to our course list implementation, the calendar implementation, or the profile
implementation. Each of these components have been updated with new functionality within this 
iteration. We have also implemented the ability for the app to be used in either landscape or 
portrait mode where you can also switch between those modes in real time.

When you start off the course page implementation you will go to a page where you can see all of 
the information about courses that you are enrolled in. Once you click on a course a series of 
informational text bars and buttons become active. These text bars will tell you the information 
about the course while the buttons will allow for you to go to different course components. The
Quiz button will take you to the quizzes associated with that course in the final build. Similarly 
the notes button will take you to the notes that are associated notes with that course in the final
build. In iteration 2 these buttons will take you to all available courses. The calendar button will
take you to the events associated with that course in the final build but right now only takes you 
to the calendar portion of the app. The favourite button will allow for the course to be favourited
which makes it appear at the top of the list of courses when you sort by favorite courses. The other
sorting buttons sort the list of available courses by metrics that are available within the courses
data. Lastly the two buttons at the top right hand corner are used to create a new course (plus button)
or leave to the home page (compass button) while the delete button is used to delete a course. 
HSQLDB database has been setup with courses so any course added or deleted will be persistance after
app shutdown. A new functionality with the course page is that you can add your GPA and for each specific course
and also see the overall GPA of all of your presently enrolled courses.

Within the notes tab we have a list of all of the notes that are associated with our account. You can 
add a title and content to any note that has been created. To create a new note you would click the 
"Add New Note" button and when you want to delete a note you would click the "Delete Note" button.
There are also sorting buttons which sort your notes based on title and most recent edited note.
HSQLDB database has been setup with notes so any notes created or deleted will be persistent on app 
shutdown. Notes are now saved to the course that was opened when the notes button is clicked on the course
page so that the user can better organize their note taking.

Within the quiz cards tab you will be shown a list of the available quiz cards. You can click on the
play button to go through the quiz cards one by one. For each quiz card there will be a button to 
reveal the answer to the question an buttons to go to the next or previous quiz card. If a quiz card 
was answered incorrectly the used can let the app know so the user knows what needs to be studied more.
There are also buttons to edit delete and create notes on the main note page.
HSQLDB database has been setup with quiz cards so any quiz cards created, edited, or deleted will be 
persistent on app shutdown. Similar to notes the quiz cards are now saved to the course that was opened
when the quiz card button was pressed.

The calendar page will show a calendar of the present month. In a future update it will also highlight
the dates where events happen but that has not been setup for iteration 3. To add an event in the 
calendar you would click the view events button at the top. You would then click the add event button in the lower right hand corner. 
From there you would input text into the reminder bar and set the time and date of your event. When you click done your event will be added 
and you will be taken to a screen where you can see all of your created events.
HSQLDB database has been setup with events so events that are created will be persistent on app
shutdown. A new feature in the app is the ability for notifications to be given to the user when one of 
the register events is coming up. To do this you will have to go into the tablet settings, go to apps, then select QuizMonkies, and then enable notifications from our app. Then when you create an event, and set the time, then (you can leave the app if you want) swipe down to see the notification center, the notification will pop up a bit after the time it was set to (might have to wait like 30 seconds after the time that was set). 

The last part of the app is the profile page. This page is where the user can change their name, username,
email, and phone number. This is done by inputting their updated values into the text bars and then
clicking update.
HSQLDB database has been setup with profiles so any profile change will be persistent on app shutdown.

Integration and Unit tests have also been setup for all portions of the app. Notable cases of where
and why integration/units tests werent setup are shown below:
comp3350.srsys.business.exceptions: Wasnt setup due to them being SQL errors which are hard to create.
comp3350.srsys.hsqldb.PersistenceException: Same as above. Hard to test SQL errors.
comp3350.srsys.persistence.utils.DBHelper: Database helper commands require UI components to be active.
Therefore this wasnt tested.


System tests have also been setup for all portions of the app. We have a system test that will run for 
every feature that we have created apart from the notifications feature. The reason this test was not made
was due to notifications having to be enabled in the device settings which would go outside the bounds of 
the system tests. Note that with the system tests HSQLDB errors occur frequently so you might have to 
run the tests more than once.