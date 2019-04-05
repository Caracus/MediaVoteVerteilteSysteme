Anwendung MediaVote
=========================

Kurzbeschreibung
----------------

Die Anwendung ermöglicht es einem verschiende Episoden zu Serien anzulegen und diese zu bewerten.
Der Nutzer kann sich mit einem Account registrieren und sich sobald er eingeloggt ist damit auf der Seite bewegen.
Die angelegten Episoden sind für alle Nutzer sichtbar und können auch von allen Nutzern bewertet werden, was dann 
den allgemeinen Durchschnittsscore ergibt. Die gesammelten Daten der verschiedenen Episoden kann auch per
API abgerufen werden.

Verwendete Technologien
-----------------------

Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung

Screenshots
-----------

Copyright
---------

Dieses Projekt ist lizenziert unter
[_Creative Commons Namensnennung 4.0 International_](http://creativecommons.org/licenses/by/4.0/)

© 2018 – 2019 Dennis Schulmeister-Zimolong <br/>
	Erweitert von Johannes Hoppstädter und Sascha Hans

E-Mail: [dhbw@windows3.de](mailto:dhbw@windows3.de) <br/>
Webseite: https://www.wpvs.de
