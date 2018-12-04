In order to compile you have to download the latest IntelliJ community distro from the [releases site](https://www.jetbrains.com/intellij-repository/releases) into the lib folder.


* On Windows:
  ```
  cd client
  if not exist lib mkdir lib
  cd lib
  certutil.exe -urlcache -split -f "https://www.jetbrains.com/intellij-repository/releases/com/jetbrains/intellij/idea/ideaIC/VERSION/ideaIC-VERSION.zip" ideaIC-VERSION.zip
  jar xf ideaIC-VERSION.zip
  ```
* On Linux:
  ```
  cd client
  ```