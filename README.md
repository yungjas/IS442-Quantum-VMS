## DB Setup
If you want to use a local mongo for your own testing, download the mongodb community server from this [link](https://www.mongodb.com/try/download/community)
<br>To configure db connections go to: src/main/resources/application.properties

## Setup and Execution Instructions
Go to vscode extensions and install the Spring boot extension pack:
![image](https://user-images.githubusercontent.com/28746967/220386515-c81e6f4b-8f79-47f9-8b66-ac497a608296.png)

To run the spring boot project, click on Run under main method in BackendApplication.java:
![Screenshot 2023-02-21 232648](https://user-images.githubusercontent.com/28746967/220387154-30576be0-18c4-4dd4-927e-57d07fb0e8ee.png)

If you prefer to run build.bat instead (running application via command line), you need to install maven: 
[link](https://www.tutorialspoint.com/maven/maven_environment_setup.htm)

To run frontend:
<br>Install [nodejs](https://nodejs.org/en/) if you do not have npm
```
cd frontend
npm install (if it's your first time running the app)
npm run serve
```
