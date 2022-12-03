To compile and create an executable run file, from the command line type:

`mvn assembly:assembly -DdescriptorId=jar-with-dependencies`

This will create a directory call 'target' that will contain an executable jar file.
Change directory into that target directory:

`cd target`

Then type the following command to run the program:

`java -cp Team1_FtpClient-1.0-SNAPSHOT-jar-with-dependencies.jar application.Main`
