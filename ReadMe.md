
This application is built using Spring boot

For the application the data is loaded inside PhoneNumberService class with @PostConstruct Spring bean annotation

For simplicity no customer objects are created only the PhoneNumber Class has been defined.

The applicaton has Junit Tests Mock MVC for controller and Rest Assured tests as integration tests

For the /activate  end point PUT is used since it updates phone number, but without a body in this case because any information there would create duplicate data.

Also inside test/resource there is postman collection with few examples.

