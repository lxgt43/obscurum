The source code can be found in the "src" directory. Simply open each ".java"
file with a text editor to read.

To compile and run the program, run the "run_obscurum" script by typing:
    ./run_obscurum
in the terminal.

To pack the program as a jar, go to the "bin" directory and type:
    jar -cfe Obscurum.jar obscurum.GameMain obscurum
in the terminal.

To run the jar, type:
    java -jar Obscurum.jar
in the terminal. Note that when running the jar, there should be a "resource"
directory on the same level, which contains a "custom_tiles.csv" file (as seen
in the "ObscurumJar" directory).

In the "ObscurumJar" directory, the "Obscurum.jar" file is the original game as
it is intended to be, and the "ObscurumDev.jar" file is the debugging version,
with god mode and sight cheats (which sometimes crashes from these cheats from
unknown reasons).
