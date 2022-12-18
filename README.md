# Basic Words Detector - CHAT SCANNER USING A JAVA SYNCHRONIZED (Blocking) MULTI-THREAD IMPLEMENTATION WITH BACKPRESSURE  (BACKEND)

Basic Words Detector is a simple command line runner spring app for scanning chat lines, looking up for certain forbidden words, like big words. 
Basically it has several applications in social networks, blogs, educative platforms, among others. The words are loaded from a CSV file, then they are put into 
memory according to the specified memory capacity for the QUEUES (candidates and suspicious), after that, the analyzer threads 
(number of threads depending on the specified values) start filtering each chat line (candidate) to see if it is containing any forbidden words, if so, 
it is saved in the SUSPICIOUS QUEUE to be written by the reporter worker thread to the assigned target file, the final result written on the file will be 
the chat number and the suspicious line. 
This project's purpose is to explore the memory usage consumption as well as to check for a possible performance improvement through several threads.
The requirement environment variables are mentioned down below. This WEB APP's backend was developed by <b>Luis Espinosa Llanos</b> using a synchronized (blocking)
multi-thread approach with manual backpressure, it was used the following technologies and tools: 

<table style="width:100%">
  <tr>
    <td>
  	Core	
    </td>
    <td>
  	Java 11, Spring Boot 2, Lombok.
    </td>
  </tr>
  <tr>
    <td>
  	Data Structures
    </td>
    <td>
  	Queues, Lists.
    </td>
  </tr>
  <tr>
    <td>
  	IDE	
    </td>
    <td>
  	Intellij
    </td>
  </tr>
  <tr>
    <td>
  	Deployment	
    </td>
    <td>
  	Docker-compose
    </td>
  </tr>
  <tr>
    <td>
  	Executable	
    </td>
    <td>
  	Jar
    </td>
  </tr>
</table>

## Video
A video exposing the functionality of the project in a local environment on a Desktop screen.

1. https://youtu.be/WH3kOYnm6bM

## Development Resources
I provide the following resources:

<table style="width:100%">
  <tr>
    <td>
  	Files
    </td>
    <td>
	In the FILES folder, be aware that topical_chat_big.csv is kind of big. You should put this files in your docker mount volume.
    </td>
  </tr>
  <tr>
    <td>
  	Architecture
    </td>
    <td>
	In the architecture folder
    </td>
  </tr>
</table>


## Pictures
Some pictures of the project on a local environment respectively:


<table style="width:100%">
  <tr>
    <td>
  		<img width="450" alt="Image" src="https://user-images.githubusercontent.com/56041525/208266427-bed31ef4-6fe0-45ba-b647-b49342f1e857.png">
	  </td>
    <td>
  	<img width="450" alt="Image" src="https://user-images.githubusercontent.com/56041525/208266441-3d5f189f-2740-403d-b8b2-ce53a7a14e77.PNG">
    </td>
  </tr>
</table>

<table style="width:100%">
  <tr>
    <td>
  		<img width="450" alt="Image" src="https://user-images.githubusercontent.com/56041525/208266450-81009e6e-3631-45e9-b446-f9c10c461606.PNG">
	  </td>
    <td>
  	<img width="450" alt="Image" src="https://user-images.githubusercontent.com/56041525/208266460-e91125b4-bece-4424-bd3c-ecff031512c2.PNG">
    </td>
  </tr>
</table>


<table style="width:100%">
  <tr>
    <td>
  		<img width="450" alt="Image" src="https://user-images.githubusercontent.com/56041525/208266475-1dc86f65-b49d-4d30-8381-d3fe8165f6b0.PNG">
	  </td>
    
  </tr>
</table>


## Installation

This project should be installed using the following command:
```bash
mvn clean install (inside the project)
docker build -t basic-word-detector:1.0 . (inside the target folder or use my image luisllanos8/basic-word-detector:1.0)
docker-compose up (Modify as per your convenience)
```

## Usage
The recommendation by now is to import it in your favority IDE. And run the project the way I did.
With or without docker.

## Contributing
This project is quite simple, and is part of my personal portfolio, so it is not intended to receive contributions.

## License
It is free.
