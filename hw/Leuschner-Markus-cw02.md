Project:&nbsp;&nbsp;Environment photos  
Repo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/  
Tag:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;adap-cw02  
Diff:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/compare/adap-cw01...of78ojax:adap-cw02


## Programmierung
### Location
Neben den Koordinaten habe ich der Klasse vorerst noch einen Namen gegeben, da der Ort an dem das Foto gemacht wurde bei aktuelleren Kameras meist getaggt wird. Kann ggf. noch um andere Kategorien erweitert werden: Region, Land, Indoor/Outdoor, o.ä.  
Neben den üblichen Set / Get Methoden, über die auch die Koordinaten verändert werden können, zeigt die `equals()` Methode die Gleichheit zweier `Locations`.  
Da bei örtlichen Angaben evtl. nicht die exakt Gleichheit gesucht ist, sondern eine angemessen nahe Entfernung der beiden Orte ein ausreichendes Kriterium liefert, habe ich zusätzlich die Methode `isClose()` und entsprechende in der Klasse `Coordinate` implementiert.  
### Coordinate
Da die einzelnen `x,y,z`-Werte vom Typ `double` sind, nutze ich in der `isEqual()` Methode die built-in Funktion aus `Double` um die Koordinaten auf Gleichheit zu prüfen (aus den üblichen Gründen für float/double Ungenauigkeit).  
Wie oben bereits beschrieben möchte man, aber gar nicht die genauen Koordinaten vergleichen. Deshalb gibt es auch hier die Methode `isClose()`, die `true` zurück gibt, solange die beiden Koordinaten nicht zu weit entfernt sind. Dazu habe ich zwei Hilfsmethoden erstellt:
- `length()` betrachtet die Koordinate als Vektor und gibt dessen euklidische Norm zurück
- `distance()` nutzt diese Norm, um Entfernung zweier Koordinaten zu messen
  
Die maximale Entfernung, die die Koordinaten dabei haben dürfen um noch als 'nahe' zu gelten, ist derzeit als fester Wert in der Klasse Coordinate gespeichert, hier will ich bei den nächsten Aufgaben vorerst schauen, in welcher Maßstäben meine Bilder auseinander liegen und mich dann entweder für eine andere feste Distanz entscheiden oder evtl. die Möglichkeit anbieten den maximalen Abstand beim Vergleich selbst zu wählen.

### Photo
Um den Modell gerecht zu werden, habe ich in der `Photo` Klasse, die Location Referenz nicht instanziiert und stattdessen die betroffenen Methoden mit checks versehen.  
Weitere Funktionen wie `hasSameLocation()` oder `takenCloseTogehter()` sind von der übrigen Klasse und den oben beschrieben Aspekten inspiriert.  
Weiter habe ich die update und read Methode so erweitert, dass die neuen Daten mit gespeichert bzw. gelesen werden können. (Die Tabellen sind ebenso um die entsprechenden Spalten erweitert worden)
