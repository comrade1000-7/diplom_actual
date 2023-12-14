FROM openjdk:17-oracle
EXPOSE 8081
ADD target/diplom-0.0.1-SNAPSHOT.jar diplom.jar
CMD ["java", "-jar", "diplom.jar"]