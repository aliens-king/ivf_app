# README #

MVID-CODE Scanner
MVID-CODE is an electronic identification system to be developed specifically for In-Vitro Fertilization (IVF) clinics. The target users of this application will be the staff members or users of these IVF centers. The users should be able to insert, view, update and delete the patients’ data visiting the IVF centers. This application will be able to book appointments of patients, find/identify patients file using unique design assigned to each patient, insert full treatment or cycle of the patients starting from day patient visit the center till the day embryo or egg transfer is done.






## Setting up the project ##
Two types of applications are going to be developed one for Server of the IVF center and other for the client systems or computers of the staff members. The Desktop application on client system will be communicating to Web Service application deployed on the IVF center server through restful web services via Intranet. The IVF center server will be connected to internet.

### Server Application Specification ###
Server application will be spring based restful web service application which will be connected to a mysql database. Installation on server will include:-
* Java or JDK 8
* Apache Tomcat 7.0.57 or above
* MySql 5.x and any MySql client among MySql Workbench or PhpMyAdmin
* Spring-Rest based WebService Application

### Client Desktop application ###
For the installation of desktop application on client machine or staff member computer on ly Java 8 is required.

### Developemnt Environment SetUp ###
* Download and install [Java FX Eclispe](http://efxclipse.bestsolution.at/install.html#all-in-one)
* Download [Scene Builder](http://efxclipse.bestsolution.at/install.html#all-in-one)
* Install JAVA EE Tools from Eclisppe Uppdae as Java FX is ot able to run web applications.
* See [this tutorial](http://code.makery.ch/library/javafx-8-tutorial/part1/) for more info on Java FX and Scene Buider
* Now Check this project Using Tortoise Git
* Import this project as Maven project in Java FX Eclispe
* Run **mvn clean install** on parent project
* Run mvd-api on tomcat
* Run mvid-javfx-app as java application

