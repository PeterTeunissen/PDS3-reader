# PDS3-reader
Java project to read and dump PDS3 data files (see https://pds.nasa.gov/)

You need to download the .DAT, .LBL and .FMT files from a NASA dataset.

Build this project with maven.

Run the program:

```
   $ java -cp target\datreader-0.0.1-SNAPSHOT.jar com.pt.DatReader <name_of_LBL_file> <showHeaders>
```

(showHeaders can be true/false)

The program will output to the screen, but will also create a .csv file in the same folder as the .LBL file. If the .csv exists, it will be OVERWRITTEN!!!