You can specify what files must exist and which one must not exist.
This is set in the "filelist.*" file. It can be either a TXT file or a XML file.
You can select which one to use in the FileCheckHandler.java FILENAME field.

--How to set the list of files--

---For filename.xml---
It follows the schema of the Maven Verfier plugin: http://maven.apache.org/plugins/maven-verifier-plugin/verifications.html
Each <file> entry must contain a <location> specifying the path (relative to project root) of a file or folder to check.
Setting the value of <exists> to false will check that the file/folder does not exist. If not set, it is understood as true (check if it exists)
Currently the value of <contains>, to check if the file contains that value, is not checked.

---For filename.txt---
Each line must contain the path (relative to project root) of a file or folder to check.
By default it check that it exists. Preceding the path with the character "!" will check that it does not exist.