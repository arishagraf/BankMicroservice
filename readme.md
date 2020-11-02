## Business Requirements

• Create a customer with full name, day of birth, address and a rating class which defaults to
"2".

• Query names and addresses of all customers by their last name and allow to sort the result.

• Transfer money from one account to another resulting in a posting.

• Create a new account for a given customer.

• Create a new credit for a given customer.

• List all accounts of one customer with their current balance.

• List all credits of one customer with their original term, remaining term, original credit
amount and the current credit amount.

• List all postings of the financial institution for a given booking date.

• List all postings with the account id and customer name of source and destination of one
customer and make the result sortable and page-able.

• Payoff a part of a credit by transferring money from an account.

• The application should be secured with a login backed with only one hardcoded user and
password combination. If a user is not logged he should not have access to the system.

• Show the balance for one customers.

• Show the balance for the financial institution.

• The API should have an online documentation.

• The first booking should be configurable in the properties of the application.

• Process to the next booking date. A booking date should be every weekday regardless of
holidays.

• List all credits with original credit amount, current credit amount and customer name which
are exceeded their original terms.

• If a customer paid off a credit he will be awarded to a better rating class but at maximum to
"1".

• If a customer didn‘t paid off a credit before the remaining term is below zero his rating class
will be set to "4".

• List all customers with name grouped by their current rating class.

# Techical requirements:

  - Spring Boot 2
  - Maven
  - Relational Database (In-memory H2)
  - API documentation

Installation
=============



Bank Microservice requires [Intellij IDEA](https://www.jetbrains.com/idea/download/#section=windows) to run (Community edition is enough).

Install the following dependencies:

```sh
spring-boot
spring-boot-starter-security
spring-boot-starter-data-jpa
h2 database
```
For project-building use **Maven** plugin:  **maven-compiler-plugin**

## How it works
This project uses in-memory relational databse H2, with "User" - "Admin" roles.

```sh
 @GetMapping("/")
```
It return a text "Welcome home!"


```sh
 @PostMapping("/createUser")
```
create User with fullname, birthdate, address

```sh
  @PostMapping("/createAccount")
```
create a customer id

```sh
  @PostMapping("/createCredit")
```
create customer id,  end date of a credit and  amount

```sh
   @PostMapping("/transferMoney")
```
transfer money from one account to another

```sh
    @GetMapping("/namesAndAdressesByLastName")
```
sort customers by their last name

```sh
	  @GetMapping("/customerAccounts")
```

get customers account with balance
```sh
	  @GetMapping("/customerCredits")
```
get customers credits with remaining day



### Todos

 - Write MORE Tests
 - Write comments

License
----

Open Source


**Made for German Standard**
