## Documentation

Documentation for Project Design should be on the shared [google drive](https://drive.google.com/drive/folders/1XKTilrG06X8pr-Nr0WpW3K2VfH7jISyc?usp=sharing). 
<br>After running the BackendApplication.java, you can access API documentation using this [link](http://localhost:8080/swagger-ui/index.html)
<br>Please see below for build instructions

## Setup and Execution Instructions
Go to vscode extensions and install the Spring boot extension pack:
![image](https://user-images.githubusercontent.com/28746967/220386515-c81e6f4b-8f79-47f9-8b66-ac497a608296.png)

To run the spring boot project, click on Run under main method in BackendApplication.java:
![Screenshot 2023-02-21 232648](https://user-images.githubusercontent.com/28746967/220387154-30576be0-18c4-4dd4-927e-57d07fb0e8ee.png)

If you prefer to run build.bat instead (running application via command line), you need to install maven: 
[link](https://www.tutorialspoint.com/maven/maven_environment_setup.htm)

If you are running frontend for the first time:
<br>Install [nodejs](https://nodejs.org/en/) if you do not have npm
```
cd frontend
npm install (if it's your first time running the app)
```

If you are running frontend on windows with node version 18 or higher
```
cd frontend
npm run serve-windows
```

If you are running frontend on windows with node version lower than 17
```
cd frontend
npm run serve-windows-old
```

If you are running frontend on macos
```
cd frontend
npm run serve-mac
```


## DB Setup
If you want to use a local mongo for your own testing, download the mongodb community server from this [link](https://www.mongodb.com/try/download/community)
<br>To configure db connections go to: src/main/resources/application.properties
<br>You can use mongodbcompass to create a db in your local mongo:
1. Click connect
<br>![image](https://user-images.githubusercontent.com/28746967/221094757-759f3215-47c9-438b-a127-9b726199c88d.png)
2. Click on the "+" button
<br>![image](https://user-images.githubusercontent.com/28746967/221094953-5cc9b46e-27a4-43b4-b2c0-bd92156b604b.png)
3. Enter "vms" as database name and the name of the table you want to create under collection name e.g. form, user etc and click create db
<br>![image](https://user-images.githubusercontent.com/28746967/221095193-804750c5-4619-45ab-85bc-37fb4e7e59c3.png)


## Lombok Troubleshooting/Installation for Spring Tool Suite 4 / Eclipse
When starting with a fresh eclipse installation you, in fact, need to "install" Lombok before being able to use it.
<br>
1. Go where you Lombok jar is (e.g. (e.g. you can find in ~/.m2/repository/org/projectlombok/lombok/1.16.10/lombok-1.16.10.jar), run it (Example: java -jar lombok-1.16.10.jar). A window should appear, browse to your eclipse.exe location.<br>
2. Click on install.<br>
3. Launch Eclipse, update project configuration on all projects

