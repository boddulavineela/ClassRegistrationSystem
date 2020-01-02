From openjdk:8
copy ./target/ClassRegistrationSystem-0.0.1.jar ClassRegistrationSystem-0.0.1.jar
CMD ["java","-jar","ClassRegistrationSystem-0.0.1.jar"]