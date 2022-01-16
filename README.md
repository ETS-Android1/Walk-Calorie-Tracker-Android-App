# Walk-Calorie-Tracker-Android-App

Live Demo: https://drive.google.com/file/d/1zNGNQSs2f-9jOeYXFyEmpNkIz6m0fNzH/view?usp=sharing

Problem Statement :
Build a mobile application which allows the user to track their walking/jogging
exercise routine.
Technology used : Android
Keywords :
Location API
Google Maps
Live location updates
Live route tracing
Distance and time tracking

Abstract :
The Walk Tracker App is designed to be a fitness aid for walking/ jogging/
running. It enables the user to track their total distance travelled and time
duration of walk. The user is also provided with their average speed and an
estimate of the calories burnt during the walk.
The app uses GoogleMaps and its Location API frameworks to facilitate the
above features. Distance is calculated by periodically sampling the user’s
location. The route taken by the user is also tracked and updated live using the
same.
The walk summary also includes an estimate of the calories burnt, which uses
the following formula :
Calories = time(mins) *3.5 * MET * weight / 200,

Where MET (Metabolic Equivalents) are approximated by the formula
MET = speed(kmph) * 0.71

The user’s weight is required in this formula. We use SharedPreferences to
store this weight across app sessions.

Module-wise Scope :
Our app can be divided into 3 distinct modules :
1. Data-entry mini module : This module is dedicated to collecting the data we
need (person’s weight) to estimate the calories burnt. This data has to be
maintained across app sessions to enhance user convenience.
2. Walk tracking module : This is the main module which uses Google Maps
and the Location API to calculate the distance travelled, the walk route, time
taken etc.
3. Walk summary module : This module generates the walk summary including
total distance travelled, time taken, average speed and calories burnt. It
receives data from the tracking module via an intent.
Technological features :
1. SharedPreferences are used to maintain state across app sessions.
2. Intents are used to switch between activities. An intent is also used to
transfer data between the tracking activity and the summary activity.
3. Location API, including the FusedLocationProviderClient, is used to
access the user’s location to calculate the distance travelled.
4. GoogleMaps to display a live map which tracks the user in real time.
