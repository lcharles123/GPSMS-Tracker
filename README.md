# SMSTracker
An app to track a Android device using SMS.

Atention: this app is ugly, i created it to my own use, not recomended to anyone.

works on Android API level 9 Android 2.3(Gingerbread) to 22 Android 5.1(Lollipop).

There is no functional UI, you need to add the settings direct to code.

This app will run as a service, waiting for an incomming menssage with a specific text. When it arrives, it will get coordinates through GPS satellites (the GPS must be active) and put them into a SMS to send to a number previously added.

Parametes of settings: 
 - In file MyReceiver.java line 23 : the keyword that must have in incoming SMS

 - In file MyReceiver.java line 98 : the destination and format options to outgoing message


TODO: 
 - start at boot time.
 - version support to api level > 22 (need to request premissions at runtime).
 - UI with start/stop buttons and place to input preferences, help page;
 - Remove duplicate call of broadcast receiver.
 - Possibility to install as a System app (including its preferences), (https://stackoverflow.com/q/25446236/4086871), to work as a realiable anti-theft app.
