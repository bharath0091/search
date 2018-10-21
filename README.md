
## Design drivers and some implementation details

<ul>
<li>
Implemented search API with <b>reactive programming</b> using spring webflux & reactor. 
With this approach, the application can be scaled vertically with limited resources.
This approach is far better than the Java8 CompletableFuture for this requirement. 
The CompletableFuture blocks a thread whereas the current implementation of search API doesn’t block the thread instead event-loop(like NODE & ngnix) mechanism is used in reactor framework.
Reactive approach shines(compared to traditional blocking approach) when there are many upstream calls and they are slow.
</li>

<li>
Front end(static content) is developed using Angular, Angular CLI, typescript, NPM. 
The front end application can be run from ng cli or NPM. 
However I have configured a maven plugin(eirslett). 
This plugin is <b>handy for java team members who are not comfortable with Agular eco system yet.</b> 
Also this plugin is helpful to build both front end and backend application into a single jar.
</li>

<li>Used <b>Functional Programming</b>. No deep nested IF ELSE pyramid. No try catch. Declarative programming.
Reactor Flux and Mono takes java functional programming to next level. For eg its ability to compose multiple Publishers and its error flow without nesting. 
Also reactor has publish subscribe approch instead of messy callbacks
</li>

<li>
Unstability of an upstream service cant influence results of other upstream service. 
I have included a error resume publisher(onErrorResume(this::logErrorAndGetEmptyFlux)) for each upstream. It ensures that other upstream flows are not impacted because of any error in a upstream flow.
</li>

<li>
Respond in 1 minute. This would happen because I have set the timeout for each upstream service as 10 seconds.
Let us say there are 100 upstream services and all are slow. If it is a blocking java 8 implementation, it keeps 100 threads waiting for the upstream services response. Each thread occupies memory and brings context switching overhead.
The reactive Spring WebClient and the reactor event-loop mecanism doesnt allocate one thread per upstream call.
<b>This way the current implementation is efficient interms of resources and can scale vertically in a JVM.</b>  
</li>

<li><b>Design Principles/patterns</b> for backend like, <br>
1) <b>Program to abstraction/interface</b>: The handler and services classes depend on the abstracted interface instead of handler depending on the concrete service classes.<br>
This can also be refered as <b>dependency inversion</b>. <br>
2) <b>Strategy pattern</b>: Both the service classes share the same interface and each service class has it's own specific & encapsulated implementation.<br>
3) <b>Encapsulate what changes</b>: I see the items(books and albums) are mainly changing parts of the system.<br> Hence the items implementation is separated from normal framweork classes.<br> 
While the preparation of books and albums have their specific classes. It is also possible to add one more type, let us say pets can be included in response by adding one more servise class.<br> 
Hence Pets feature can be added without changing any other classes of the system.<br>
This capability is called as <b>Open for extension and closed for change</b> design principle.<br>
4) <b>Separation of concerns </b>: Every class has its own responsibility.<br>
5) The domain objects are <b>immutable </b>including the List of values in the object.
</li>

<li><b>Design Principles/patterns</b> for UI/frontend like,
More or less same principles are applied. Here are some frontend specific details,
1) I have created two components.<br>
 one for Search input and search button. It is called as search component.<br>
 Second one to display the search results. It is called search-result component.<br>
The two components encapsulate their specific implementation.<br>
2) The search and search-result components share entered search text in publish-subscribe model.
</li>

<li>I didn't create a separate handler for serving static content. Because spring boot has a handler already to serve content from  public folder which is on classpath.</li>

<li>I couldn’t get to the response time metrics requirement dur to time constraint and I had focused on making the core implementation better. 
Metrics can be implemented using spring AOP and spring actuator.
Also Unit testing and automated integration testing is imporatant but i couldnt get to it with in the given time. 
</li>
</ul>

## Steps to run application
<ul>
<li>mvn clean install (Note : This is to build the code, first time build would be slow as Node and NPM need to be downloaded)</li>
<li>Go to search-web\target folder and open command prompt at this folder and run the following command</li>
<li>java -jar -Dspring.profiles.active=dev search-web-0.0.1-SNAPSHOT.jar</li>
<li>The Application can be reached at http://localhost:8080/index.html and health check at http://localhost:8080/healthCheck <br> 
Health check just returns 200 if the server is up and running</li>
<li>Enter text and click Search Button </li>
</ul>

## Minimum required installation to run application

<ul>
<li>Java 8</li>
<li>Maven 3.5.0</li>
</ul>
Note : Angular and its dependencies would be downloaded automatically from maven install. 
It is not must to install Node, NPM, Angular CLI for the begining.


## Logging
Both dev and non-dev profiles log to console as of now. <br>
A separate file appender or preferably <b>graylog</b> appender can be used for non-dev environments.<br>
Also the log level can be configured per environment. As of now it is at INFO level for all environments. 

## Running UI/front-end application as a standalone application during development

Although we can run the entire application using the above mentioned "Steps to run application".
Running the frontend application separately is flexible and faster(Eg: changes reflect on the fly in ng serve while code changes happen in IDE).

#### Installation for UI
<ul>
<li>Node 9.9.0 or above version</li>
<li>NPM 6.4.1 or above version</li>
<li>Angular/ng CLI 6.2.3</li>
</ul>


#### Run `npm install` at root level in search-static folder

#### I have used [Angular CLI](https://github.com/angular/angular-cli) version 6.2.3. for generating scaffold, build and running

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.
It doesn't run end to end as the UI expects `http://localhost:4200/search` API available. 
I have separated UI and backend so that UI and backend implementations can happen separately.
The current implementation can be extended by pointing UI application(when running in standalone) to a running search endpoint.  

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `src/main/resources/public/` directory. Use the `--prod` flag for a production build.

## Running end-to-end tests
Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).
Only scaffold is present at the moment, to be extended.

## Running unit tests(Only scaffold is present at the moment)
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
Only scaffold is present at the moment, to be extended.
