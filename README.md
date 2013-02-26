PlayITS
=======

ITS Student Project at FU-Berlin 

Winter Semester 2012-2013 (18.02.2013 until 29.03.2013)

https://www.mi.fu-berlin.de/kvv/course.htm?cid=10887&sid=26&iid=1


based on

TTworkbench Play
================

TTworkbench Play designs a new approach for testing.


Details to our TTworkbench Play Program can be found here

http://www.testingtech.com/services/ttworkbench_play.php

If you would like to join this Program please contact play[at]testingtech.com.


Projektbeschreibung
===================

Autosimulation

Innerhalb dieses Kurses sollen Autofahrten aus Sicht der Fahrer simuliert werden. Dafür wird eine grafische Oberfläche entwickelt, die einem Armaturenbrett gleichen soll. 
Das Auto hat mehrere Funktionen:

- Motor anlassen und ausschalten
- Geschwindigkeit einstellen
- Gefahren erkennen und Warnungen ausgeben
- Abstandsmessung zu anderen Autos.

Die zu fahrende Strecke kann ausgewählt werden. Die Funktionen des Autos sollen getestet werden.
Dafür verwenden wir eine TTCN3 Schicht, die mit einem in Java implementierten "Auto" - dem System Under Test - und dem Widget kommuniziert und die Tests ausführt. Weiterführend soll es möglich sein, auch mehrere SUTs mittels der TTCN3 Schicht untereinander zu verknüpfen. 
So können Warnungen von einem Auto an alle anderen gesendet und entsprechend darauf reagiert werden.
