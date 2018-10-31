# Design Document - v2.0
**Author**: Team39

## 1 Design Considerations
### 1.1 Assumptions
Internet connection may not be available. Sufficient screen size to interact with application. User is familiar with grocery shopping and grocery lists.

### 1.2 Constraints
Local storage only for storage of lists and items, due to no internet connection assumption. Development time frame is limited to only 3 weeks.

### 1.3 System Environment
Requires Android software API 19 or greater running on Android phone or tablet device.

## 2 Architectural Design
### 2.1 Component Diagram
![Component Diagram](design-component.png)

There are 4 components in the Grocery List Manager application design. The user interface component interfaces directly with the 2 core processing components, which manage the Grocery Lists and Entries within individual lists. Finally, the Item and Lists component represents the database, which contains all items, item types, and grocery lists information.

### 2.2 Deployment Diagram
This diagram is unnecessary due to the fact that all components will exist on a single Android device.

## 3 Low-Level Design
### 3.1 Class Diagram
![Team Design UML](../Design-Team/design-team.png)
The core class of the design in GroceryList. This class contains all information about each list within the application. It contains multiple GroceryListEntry class instances, which describes each entry within a grocery list. Each GroceryListEntry contains an Item class instance. The Item class contains ItemType instance. Items, ItemTypes, GroceryLists, and GroceryListEntries are stored in a database. The interface to the database is represented by the DatabaseHandler class. 

### 3.2 Other Diagrams
Entity Relationship Diagram
![Entity Relationship Diagram](erd.png)
Diagram showing the database structure and relationships.

## 4 User Interface Design

### Grocery List GUI
#### View
![Grocery List - View](gl_view.jpg) 
#### Search/Add Item
![Grocery List - Search/Add Item](gl_add_item.jpg) 
#### Change Quantity
![Grocery List - Change Quantity](gl_change_quantity.jpg)
#### Delete Items
![Grocery List - Delete Item](gl_delete_item.jpg)

### List Manager GUI
#### View
![List Manager - View](lm_view.jpg)
#### Add List
![List Manager - Add List](lm_add_list.jpg)
#### Rename List
![List Manager - Rename List](lm_rename_list.jpg)
![List Manager - Rename List Dialog](lm_rename_list_dialog.jpg)
#### Delete List
![List Manager - Delete List](lm_delete_list.jpg "Delete List")
