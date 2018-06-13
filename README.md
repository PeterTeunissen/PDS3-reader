# PDS3-reader
Java project to read PDS3 data files.

You need to download the .DAT, .LBL and .FMT files from a NASA dataset.

Then build this project with maven.

Run the program:

```
   $ java -cp target\datreader-0.0.1-SNAPSHOT.jar com.pt.DatReader <name_of_LBL_file> <showHeaders>
```

(showHeaders can be true/false)
