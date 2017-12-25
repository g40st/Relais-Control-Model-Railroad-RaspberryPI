# Relais Control Model Railroad - Raspberry PI

This is a model railroad relais control. It's using a tomcat webserver to provide a userinterface. 

![model](https://user-images.githubusercontent.com/7523395/34341871-f6e901a0-e9a0-11e7-9c4c-0f1dbf20d6c1.gif)


## Dependencies
* Apache Tomcat
* [jQuery](https://jquery.com/)
* [Bootstrap](http://getbootstrap.com/)
* Apache ant
* Java JDK

```shell
apt-get install ant
apt-get install openjdk-8-jdk
```

## Installing / Getting started (Raspberry PI)
  1) Download Apache Tomcat (tested under Apache Tomcat 8.5.24)
  2) Copy the files to /opt/tomcat
  3) Adopt the tomcat users:
  
      File: opt/tomcat/conf/tomcat-users.xml
      ```xml
      <tomcat-users>
        <role rolename="manager-gui"/>
        <user username="admin" password="admin" roles="manager-gui,admin-gui,manager-script,admin-script"/>
      </tomcat-users>
      ```
   4) starting tomcat using: 
   ```shell 
   /opt/tomcat/bin/startup.sh 
   ```
   5) Go to the repository directory and run ant
   6) At the first deployment you have to deploy the .war file by hand. Use the build-in manager. This manager is avaliable under <IP_ADDRESS>:8080/manager/html. Then go to "WAR file to deploy" and select the .war file to deploy it.

## Using
  Use your Browser and go to "http://<IP_ADDRESS>:8080/train/"

## Nice To Know

### Fritzing
![fritzing](https://user-images.githubusercontent.com/7523395/34341877-484a6d9a-e9a1-11e7-84e6-44284fc9b4cf.png)

### UML diagram
![uml](https://user-images.githubusercontent.com/7523395/34341879-5ac64fe8-e9a1-11e7-95a2-dec8c9ad122f.png)

### names of the elements
The UI is build in [SVG](https://www.w3schools.com/graphics/svg_intro.asp). 
![webseitenamen](https://user-images.githubusercontent.com/7523395/34341882-6a95cfe8-e9a1-11e7-9a1f-cb101c117965.png)

### documentation
If you want to have a documentation please do not hesitate to contact me. The documentation is only available in German.

## Author
Christian Högerle

## Licensing
The code in this project is licensed under MIT license.
