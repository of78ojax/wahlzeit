Project:&nbsp;&nbsp;Environment photos  
Repo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/  
Tag:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;adap-cw04  
Diff:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/compare/adap-cw03...of78ojax:adap-cw04

## Implementierung
Für diese Woche habe ich mir zunächst die benötigten Methoden für das Interface überlegt. 
Neben den Vorgaben, sollten die Methoden für das schreiben und lesen von einem `ResultSet` hilfreich sein und Übersicht schaffen. 
Diese können einfach von den Übergeordneten aus der `Location` bzw. `Photo` Klasse aufgerufen werden.
Zusätzlich gibt es die beiden final Strings, die als eine Art Type-check beim Auslesen eines `Resultsets` dienen sollen. 
Dort werden die vorhanden Felder für die Koordinaten genutzt und nur ein zusätzlich String gibt an, wie diese zu interpretieren sind.
Das führt leider zu einer Abfrage in Locations beim Auslesen, welcher Type erstellt werden soll, die Koordinaten müssen aber nicht doppelt gespeichert werden.  
Für die einzelnen Koordinaten-Klassen habe ich so viel wie möglich versucht wieder zu verwenden. Die kartesischen Methoden entsprechen oft den der alten `Coordiante`-Klasse. Wenn dies nicht ging, hab ich die entsprechende Aufgabe nur in einer der beiden Unterklassen wirklich bearbeitet und in der anderen nach der Konvertierung aufgerufen.
Das macht doppelten Code relative gering und verringert auch den Aufwand meistens, da die Operationen in einer der beiden Klassen einfacher zu handhaben ist.

## Cartesian
Die Klasse übernimmt den großen Teil der alten Coordinate-Klass. Ich habe diese um einen Konvertierungs-Konstruktor erweitert, der eine SphericalCoordinate bekommt und die Konvertierung entsprechend übernimmt. Das ist recht nützlich, da dieser ähnlich einem Cast verwendet werden kann. Die Konvertierung selbst erfolgt nach den üblichen Formalen, die ich auch im Internet zu finden sind.  
- `asCartesian()` gibt einfach sich selbst zurück, ist ja schon das passende Format
- `asSpherical()` nutzt den entsprechenden Konvertierungs-Konstruktor von Spherical(vergleichbar zu dem oben beschriebenen für die kartesischen Koordinaten)
- `getCartesianDistance()` hatte ich mit `distance()` schon implementiert und wird hier nur noch aufgerufen
- `getCentralAngle()` konvertiert sich selbst zu Spherical und lässt dort die Berechnung machen
- `isEqual()` konvertiert other zu kartesischen Koordinaten und macht dann den Vergleich (genauer unten in der Beantwortung der Frage)
- `getType()` wie oben beschrieben: gibt den String type zurück, damit man dieses mit den SQL-Daten vergleichen kann.
- `writeOn()` und `readFrom()` fallen durch die Interpretierung durch den String relativ simple aus. Man schreibt / liest die Daten einfach aus und setzt sie jeweils für die entsprechenden Koordinaten

## Spherical
Genau wie bei den kartesischen Koordinaten habe ich bei den sphärischen ein paar getter eingebaut, einen Konstruktor der die Werte direkt übergeben bekommt und einen der kartesische in sphärische Konvertiert. Dabei werden auch einfach die üblichen Formeln für die Konvertierung genutzt.
- `asCartesian()` nutzt den Konvertierungs-Konstruktor der Cartesian-Klasse
- `asSpherical()` passendes Format, gibt also sich selbst zurück
- `getCartesianDistance()` konvertiert zu kartesischen und nutzt dort die  `distance()` 
- `getCentralAngle()` hier wäre die Benennung zu erwähnen, damit die Übersicht bzw. die Übereinstimmung zu der Wikipedia Seite passt, habe ich die genutzten parameter in long für longitute and lat für latitude umbenannt.
- `isEqual()` konvertiert other zu sphärischen Koordinaten und macht dann den Vergleich (genauer unten in der Beantwortung der Frage)
- `getType()`, `writeOn()` und `readFrom()` vgl. oben

## isEqual()
Hier war ich erst unschlüssig, ob man überhaupt kartesische und sphärische Koordinaten vergleichen will. Ich bin aber zu dem Schluss gekommen, dass man nur wegen unterschiedlicher Darstellungsformen nicht direkt von Ungleichheit ausgehen kann.  
Trotzdem wollte ich eine Unterscheidung der beiden Vergleiche haben, weshalb nun die aufgerufenen Klasse die "Domäne" bestimmt. 
D.h. der Typ der Coordinate `coord` die aufgerufen wird  `coord.isEquals(other)`, bestimmt die Konvertierung von other. Dadurch muss man zwar den Vergleich doppelt schreiben, aber die Darstellungsform zu bestimmen, die genutzt wird fand ich hier überwiegend.
