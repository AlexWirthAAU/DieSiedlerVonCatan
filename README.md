# DieSiedlerVonCatan

[![Build Status](https://travis-ci.com/AlexWirthAAU/DieSiedlerVonCatan.svg?branch=master)](https://travis-ci.com/AlexWirthAAU/DieSiedlerVonCatan)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DieSiedlerVonCatan&metric=alert_status)](https://sonarcloud.io/dashboard?id=DieSiedlerVonCatan)

# Allgemein: 
## Spielablauf:
Zu Beginn loggt sich der User mit seinem Namen ein. Dann wird er in eine Lobby weitergeleitet, wo er nach Mitspielern suchen kann. Er kann zwischen zwei und drei Mitspielern auswählen und das Spiel starten. Ist dies passiert, kommen alle gewählten Spieler zu einer Farbauswahl. Haben alle ihre Spielfarbe gewählt, kann das Hauptspiel gestartet werden. Es beginnt jener Spieler, der nach der Farbauswahl das Spiel startet. Zu Beginn erhält jeder einen Rohstoff jeder Art. Reihum kann jeder Spieler „kostenlos“ zwei Siedlungen und Straßen bauen. In Rot werden die Siedlungen bzw. Straßen angezeigt, auf denen der Spieler bauen kann. Dann beginnt der normale Spielablauf: Zu Beginn muss der Spieler würfeln, indem er das Handy schüttelt. Je nach Augenzahl werden die Rohstoffe verteilt und Spieler kommt zu einer Auswahl bei der aus folgenden Optionen wählen kann: 

  * Siedlung bauen 
  * Straße bauen 
  * Stadt bauen 
  * Handeln 
  * Banktausch 
  * Hafentausch 
  * Karte kaufen 
  * Karte ausspielen 
  * Baukosten ansehen 
  * Inventar ansehen 
  * Weiter 

\
Es darf jeweils nur eine Aktion pro Spielzug gewählt werden. Wird eine 7 gewürfelt, werden keine Rohstoffe verteilt und der Spieler darf den Räuber versetzten. Befindet sich der Räuber auf der Landschaft mit der gewürfelten Zahl gibt es ebenso keine Rohstoffe. Für jede Siedlung gibt es 2, für jeder Stadt 3 Siegpunkte. Eine Siegpunktkarte bringt einen Siegpunkt, ebenso wie die längste Handelsstraße und die größte Rittermacht. Hat ein Spieler 10 Siegpunkte erreicht, ist das Spiel beendet. \
TODO: ergänzung Spielende

## Regelanpassungen:
* nur eine Aktion pro Spielzug erlaubt
* zu Beginn gibt es einen Rohstoff jeder Art 
* zu Beginn werden die zwei Straßen und Siedlungen reihum gebaut
* es gibt nur Spezialhäfen
* der Räuber stielt nur einen Rohstoff pro Spieler
* beim Ausspielen der Ritterkarte, erhält der Spieler nur zwei Rohstoffe von den Mitspielern

**@author Alex Wirth**
* Gameboard
* Bauen
* Längste Handelsstraße
* Würfeln & Ressourcenverteilung

**@author Fabien Schaffenrath**
* Netzwerk
* Spielstart (edit)
* Räuber
* Schummeln

**@author Christina Senger**
* Netzwerk (edit)
* Spielstart
* Handeln mit Mitspielern, mit der Bank und über Häfen
* Entwicklungskarten
* Größte Rittermacht

### Gameboard
Das Spielbrett besteht aus 19 Feldern, die einer bestimmten Ressource zugeteilt sind und einen Würfelwert zwischen 2-12 haben. An jedem Knotenpunkt der Felder können Siedlungen gebaut werden, die mit Straßen verbunden sind. Einige Knotenpunkte sind Hafensiedlungen. An ihnen ist ein weißes Schiff zu erkennen. 
### Bauen
Entscheidet sich der Spieler eine Siedlung zu bauen, so werden ihm alle möglichen Knotenpunkte in Rot markiert. Klickt er auf einen dieser markierten Punkte, wird der Punkt besiedelt und in seiner Spielerfarbe eingefärbt. Eine Siedlung kann nur an einer Straße des Spielers gebaut werden sofern die benachbarten Kreuzungen noch nicht besiedelt sind.
Straßen können entweder an andere bereits existierende Straßen des Spielers oder an eine seiner Siedlungen gebaut werden. Auch hier werden die möglichen Kanten zunächst rot eingefärbt. Baut der Spieler eine Straße, wird sie in seiner Spielfarbe eingefärbt.
Siedlungen können im Verlauf des Spieles zu Städten umgebaut werden. Städte werden als schwarze Quadrate mit Punkten der Spielerfarbe dargestellt.
### Längste Handelsstraße

### Würfeln
Wenn ein Spieler an der Reihe ist, wird er aufgefordert zu würfeln. Dies passiert durch Schütteln seines Smartphones. Eine zufallsgenerierte Zahl wird dem Spieler angezeigt.
Je nach Zahl werden Rohstoffe an jene Spieler verteilt, die Siedlungen bzw. Städte an den betroffenen Feldern haben. Siedlungen bringen eine Einheit des Rohstoffs, Städte zwei.
### Räuber 

### Schummeln

### Netzwerk

### Handeln
Ein Spieler kann mit der Bank 4:1 handeln, sofern er von mindestens einem Rohstoff 4 oder mehr hat. Dazu wählt er den gewünschten und gebotenen Rohstoff aus und schließt den Handel ab. 
Besitzt er eine Siedlung er Stadt an einem Hafen, kann er auch den Hafentausch als Aktion wählen. Dieser funktioniert äquivalent zum Banktausch, allerdings wird im Verhältnis 3:1 getauscht. Im Gegensatz zum Originalspiel gibt es keine allgemeinen, sondern nur Spezialhäfen (Rohstoff des Hafens ist jener der angrenzenden Landschaft).  Der gewünschte Rohstoff kann beliebig gewählt werden, der gebotene muss einem Hafen im eigenen Besitz entsprechen.
Besitzt ein Spieler mindestens einen Rohstoff, kann er mit seinen Mitspielern handeln. Er kann die Rohstoffanzahl variieren und auch mehrere Rohstoffe auswählen. Das Handelsangebot wird an alle Mitspieler gesendet, die genug Rohstoffe haben. Diese können das Angebot annehmen oder ablehnen. Hat kein Mitspieler genug Rohstoffe oder alle lehnen ab, kann kein Handel durchgeführt werden. Andernfalls „gewinnt“ jener Spieler, der zuerst angenommen hat. Die Handelspartner bekommen eine Bestätigung.

### Entwicklungskarten
Insgesamt gibt es 26 Entwicklungskarten im Spiel. Als erstes werden Ritterkarte, Straßenbaukarte und Siegpunktkarte verkauft. Die restlichen Karten werden zufällig gekauft. Nach dem Ausspielen sind die Karten aus dem Spiel. Insgesamt gibt es fünf verschiedene Karten:
* Ritterkarte: der Spieler darf den Räuber versetzten und von den Mitspielern, auf deren Landschaft der Räuber nun ist, zwei von deren Rohstoffen stehlen
* Straßenbaukarte: der Spieler darf zwei Straßen bauen 
* Erfindungskarte: der Spieler wählt einen Rohstoff, von dem er zwei erhält
* Monopolkarte: der Spieler wählt einen Rohstoff, von dem er die Vorräte aller anderen Spieler erhält
* Siegpunktkarte: der Spieler erhält bei Kauf einen Siegpunkt

### Größte Rittermacht
Die größte Rittermacht besitzt jener Spieler mit den meisten aktiven Ritterkarten. Wurde eine Ritterkarte gekauft, wird überprüft, ob der Spieler nun die größte Rittermacht besitzt. Ist dies der Fall, erhält er einen Siegpunkt. Wird die Ritterkarte ausgespielt, wird wieder geprüft und gegebenenfalls erhält ein anderer Spieler den Siegpunkt.
