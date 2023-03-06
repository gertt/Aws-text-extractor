PROJECT AWS TEXT EXTRACTOR

This app extract the text from image using aws service

This project use:
- java 17 version
- spring boot 3.0.3
- maven 
- aws-java-sdk 
- aws-java-sdk-textract
- slf4j-api

We have different environment dev,test prod,
- Dev can be accessible by url http://localhost:9191/aws-text-extractor-dev/upload
- Prod can be accessible by url http://localhost:9292/aws-text-extractor-prod/upload
- Test can be accessible by url http://localhost:9393/aws-text-extractor-test/upload

Note: The api can be accessed by post method, body, form-data, key -> img, value an image.

The logging is made by slf4j-api  and all logs are printed on log/log directory every day
is another file with the date name

After run all the test we can deploy on docker

Dev: 
- docker build -t aws-text-extractor-dev .  
- docker run -p 9191:9191 aws-text-extractor-dev 

Prod:
- docker build -t aws-text-extractor-prod .
- docker run -p 9292:9292 aws-text-extractor-prod

Test:
- docker build -t aws-text-extractor-test .
- docker run -p 9393:9393 aws-text-extractor-test 

Tools:
- IntelliJ
- Docker
- Postman
