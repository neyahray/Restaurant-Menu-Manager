# Restaurant Menu Manager

**Student Name:** Tashbaeva Ayana, **Group:** COMSE-25

## Description
This is a simple and handy JavaFX based application, for managing a restaurant menu. The application provides an interactive interface for viewing and managing menu items categorized into Pastries and Drink. It helps staff organize food and drinks without using paper.

**What it can do:**
* **Role-Based Access Control:** Admins can change everything, Chefs can update info, and Guests can only look at the menu.
* **CRUD-operations:** You can add new dishes, change their prices or descriptions, and delete them if they are no longer available.
* **Modern Design:** The app has a clean look with a dimmed background when editing and easy step-by-step forms.
* **Database Integration:** Persistent storage of all menu data through a robust SQL backend handled by a dedicated DataBaseHandler.

## Objectives
The main goals of this project are:
* **Make work faster:** To help restaurant workers update the menu in seconds.
* **Keep it safe:** To make sure only the right people (like Admins) can delete items or change prices.
* **Keep it simple:** To create an interface that is easy to understand, even for someone who isn't good with computers.
* **Stay organized:** To keep all pastries and drinks in one clear, digital list that is easy to search and manage.



## Project Requirement List
###    Requirement | Implementation in My Project
* 1 | Implement CRUD Operations | Supported for all menu items (Add, View, Update, Delete) via DataBaseHandler and GUI
* 2 | Command Line Interface | Instead of this, there is a GUI interface
* 3 | Input Validation | Methods: passwordCheck(), enteredInputCheck(), enteredInputCheckToPrint(), enteredInputCheckToPrintForAdd()
* 4 | Data Persistence | Implemented using a relational SQLite database via JDBC
* 5 | Modular Design | Project is split into layers:
                        View layer (FXML): login-page.fxml, menu-page.fxml

                        Data Classes:  Classes Item, Pastry, Drink

                        Controllers: Classes LoginPageController, MenuController

                        Database Handler: Class DataBaseHandler

                        Common Utilities: CommonMethods, CommonStyledItem
* 6 | Documentation | There is Readme file in my gitHub
* 7 | Error Handling | Critical database and FXML loading operations are wrapped in try-catch blocks with user notifications.
* 8 | Encapsulation | All fields in Item are  private. Access is provided only through public getters and setters.
* 9 | Inheritance | Classes  Pastry and  Drink inherit common properties from the base class Item
* 10| Polymorphism | Overridden getTableName() method in subclasses allows dynamic selection of SQL tables.

## DOCUMENTATION
### 1. Algorithms and Logic
* **Authentication & Role Logic:** The system uses a `switch-case` algorithm based on `UserRole` (ADMIN, CHEF, GUEST). This determines the visibility of UI elements (e.g., hiding the Delete button for Chefs).
* **Validation Algorithms:** I implemented custom validation using **Regular Expressions (Regex)**.
    * `[a-zA-Z0-9!' -]+` for names and descriptions.
    * `\\d+(\\.\\d+)?` for prices.
    * This ensures data integrity before it reaches the database.
* **Dynamic UI Rendering:** The `loadDataFromDB` method uses a loop to iterate through database results, creating new `CommonStyledItem` objects and injecting them into JavaFX containers (VBox) dynamically.

### 2. Data Structures
* **Lists (ArrayList):** Used to store and manage collections of items retrieved from the database and to keep track of UI nodes during navigation.
* **HashMaps:** Used in the `LoginPageController` for quick, constant-time `O(1)` lookups of hardcoded employee credentials.
* **Objects & Inheritance Hierarchy:** The project uses a hierarchical structure where `Pastry` and `Drink` inherit from an abstract `Item` class, utilizing a common data model across the app.

### 3. Functions and Modules
The project follows a modular architecture:
* **`DataBaseHandler`**: A dedicated module for JDBC operations, handling table creation, CRUD queries, and connection management.
* **`CommonMethods`**: A utility module containing shared UI logic like `playScaleAnimation` and `notification` systems, preventing code duplication (DRY principle).
* **`CommonStyledItem`**: A helper class that acts as a bridge between the Data Model and the View, styling raw data into interactive JavaFX components.

### 4. Challenges Faced
The hardest part was figuring out how JDBC works and how to get the deleteButton and updatePencil from another class. It was a challenge to make them work so that when you click them, they actually react and "know" which item they belong to. I had to find a way for these buttons to send information back to the main controller and interact with other UI elements correctly. Managing this connection between the database and buttons from different classes was the most difficult task for me.

## Test Cases and Outputs
Here is the Video Test Case : https://drive.google.com/file/d/147bww9weknMOM22QVCQwpS7wu9jjmbLD/view?usp=sharing

