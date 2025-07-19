# NetSim

This is a Java project for my class that implements CRUD operations on a MySQL database.
It's focus was mostly on learning the syntax, deepening my OOP and design pattern knowledge.
It avoids error checking and does not have much business logic.

## App initialization

Before everything, the auth and main app services are created, the DAOs(objects that represent the database tables and provide the CRUD functionality) are created and lastly the database connection is established.

## Login screen

In order to gain access to the main app, the user has to login/register. Passwords are hashed with bcrypt, email and username are checked for uniqueness.

## Main app

The main app provides simple CRUD functionality on the existing tables, with a few of them being more complex due to the usage of joins.

Most interesting operations:

- List data of packets for a given equipment
- List equipment interfaces
- List mac address table for a given equipment

### Implementation and app structure

`app_init/AppInitializer.java` : The initializer of the app(described before)
`db_conn/DBConn.java`: Singleton class that holds a database connection
`models/` : DAOs providing an abstraction over the database objects All classes extend the Model class which implements the template method pattern, providing a very general implementation for the CRUD operations that the children classes will use. The classes don't hold any data, for that they rely on entities(models_data).
`models_data/` : POJOs that hold database entries