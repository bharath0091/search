
##Design details
<ul>
<li>
Implemented search API with <b>reactive programming</b> using spring webflux & reactor. 
With this approch, the application can be scaled vertically with limited resources.
This approch is far better than the Java8 CompletableFuture for this requirement. 
The CompletableFuture blocks a thread where as the current implementation of search API doesnt block the thread instead event-loop mecanism is used under the hood.
This apprach shines when there are many upstream calls and they are slow
</li>

<li>
Front end is developed using Angular, Angular CLI, typescript, NPM. 
The front end application can be run from ng cli or NPM. 
Also for I have configured a maven plugin(eirslett). 
This plugin is handy for java team members who not comfortable with Agular eco system yet. 
However this plugin is helpfull to build both front end and backend application into a single jar
</li>

<li>Used Functional and reactive programming</li>
</ul>

##Steps to run application
<ul>
<li>mvn install (Note : This is to build the code, first time build would be slow as Node and NPM need to be downloaded)</li>
<li>Go to search-web\target folder and open command prompt at this folder</li>
<li>java -jar -Dspring.profiles.active=dev search-web-0.0.1-SNAPSHOT.jar</li>
<li>The Application can be reached at http://localhost:8080/index.html</li>
<li>Enter text and click Search Button </li>
</ul>

## Version info
<ul>
<li>Java 8</li>
<li>Maven 3.5.0</li>
</ul>
