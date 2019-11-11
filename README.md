# dronizone [![Build Status](https://travis-ci.com/pns-si5-soa/dronizone-1920-scp-1920.svg?token=HLwfpNcZxzix3skLQMXL&branch=dev)](https://travis-ci.com/pns-si5-soa/dronizone-1920-scp-1920)  
The goal of this project "is to define and implement the *Minimal and Viable Product* (MVP) associated to the Dronazon platform". The Dronazon platform is a platform of delivery by drone; our roadmap can be summarized as follows:  
*Service Oriented Architecture (SOA) -> Microservice Oriented Architecture (MOA) -> Event Oriented Architecture (EOA)*  

**From week 39 to week 42**: *To define and implement a SOA* ![](https://placehold.it/15/ffff00/000000?text=+)  
So far, we have distinguished four services (**Order Service**, **Warehouse Service**, **Fleet Service** and **Notification Service**) we have tried to define and implement in a SOA.  

**From week 42 to week 43**: *To define and implement a MOA*  
During the week 42, a study of MOA (for the Notification Service) was started; currently, we have:  
- a first definition of microservice for the Notification Service (definition partially written in a text entitled "[notification_service_architecture](https://github.com/pns-si5-soa/dronizone-1920-scp-1920/blob/dev/dronizone-notification/doc/notification_service_architecture.pdf)"),
- a first implementation of microservice for the Notification Service,
- a link of this service (as a microservice) with a MongoDB...  

We have not been able to test the persistence and, in particular, the *repositories* in the sense of the interfaces **Repository** of Spring (is returned a NullPointerException on the repositories)...  

**From week 43 to week 44**: *To define and implement a EOA*  
We have already make some research about (the bus) Kafka, and, shortly, we will complete the document entitled "notification_service_architecture"...  

**From week 44 to week 45**: *To finalize our architecture and prepare our defense*...  


## The main technologies used
[Apache Maven](https://maven.apache.org/) - Dependency Manager  
[Spring](https://spring.io/) - Application Framework  
[Cucumber](https://cucumber.io/) - TDD (BDD) Tool  
[Travis CI](https://travis-ci.org/) - CI Tool  
[Docker](https://www.docker.com/) - Deployment and Execution Tool  


## Authors
The **SCP-1920** team:
- [Valentin AH-KANE](https://github.com/ValentinAh-Kane)
- [Romain GARNIER](https://github.com/Romain-GARNIER)
- [Filipe COSTA DE SOUSA](https://github.com/FilipeCosta06)
- [Cyril MARILIER](https://github.com/mariliercyril)
