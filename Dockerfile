FROM adoptopenjdk/openjdk11:alpine-jre

ENV SOURCE_FILE=sourceFile \
     TARGET_FILE=targetFile \
     FORBBIDEN_WORDS_FILE=forbbiden \
     CANDIDATES_CAPACITY=candidatesCapacity \
     SUSPICIOUS_CAPACITY=suspiciousCapacity \
     ANALYZER_THREADS_NUMBER=analyzerThreads

ARG JAR_FILE=target/basic-word-detector.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]