# Contract testing with Pact and Pact Broker (using Pact JVM)
This repository is an example of using Pact and it's broker for contract testing. The scenario we have here is of a consumer of a (very) simple order API that needs to have its contracts tested. At the same time the Order API needs to make sure none of it's contract changes will eventually break it's consumers (in our example, just one project).
  
## Folder Structure
The projects folder structure goes as follows:

* broker: A folder containing a single docker-compose.yml (default from pact's repository)
* orderconsumer: The Java 8 application for the consumer of the Order API. This application is responsible for: 
    * Generating the contract tests (pacts) 
    * Uploading them to the broker.
* orderprovider: The Java 8 application for the Order API itself. This application is responsible for:
    * Providing end-points to which the contract tests can be run
    * Gathering all pacts for it's listed as a provider
    * Running the contract tests and publishing the results to the broker
    
## 1. Starting the broker
Just navigate to the broker's folder and run ```docker-compose up```, simple as that. After a while you should have the broker running on your ```http://localhost:9292```  


## 2. Creating the pact file and uploading it to the broker
Go to the orderconsumer application and run the test class called ```OrderConsumerPactTest```. After a successful run, a pact file called ```orderConsumer-OrderAPI.json``` will be created on ```src/main/resources/pacts```.

To publish your pact file into the broker, just run ```./gradlew pactPublish```. After that, you should already see your pact on the broker

__To understand more about how the configuration was done a simple read of the following files might prove very useful:__
* ```OrderConsumerPactTest```
* ```build.gradle```

## 3. Fetching and running the pact from the broker
First, let's begin by running the orderprovider API by running ```./gradlew bootRun```. After that, the Order API should be running on your ```http://localhost:8080```

Now, to fetch and run the contract tests, run ```./gradlew pactVerify -Ppact.verifier.publishResults=true``` (everything after ```-P``` is just to make sure the run results are published back to the broker).

__To understand more about how the configuration was done a simple read of the ```build.gradle``` file might prove very useful.__
