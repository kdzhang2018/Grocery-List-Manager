**GroceryListManager**

 1. **A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).**
Created class GroceryList with attributes name, itemsList and operations addItem, deleteItem, quantityItem
Created class listEntry with attributes item, quantity. 
Also see class Item, further defined in #2.

 1. **The application must contain a database (DB) of items and corresponding item types.**
Created class Item with attributes name, ItemType.
Created class ItemsDb which represents an aggregation of Items and ItemTypes stored in a database.
  
 1. **Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.**
Add operations to ItemsDb: getItemTypes, getItemsByType.
The operations to addItem and adjust quantity for Item have already been handled in #1.
Otherwise, this is more of an implementation detail describing how the UI should work rather than a design detail. 

 1. **Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type.**
Add operations to ItemsDb: searchItemName, newItem.
Class Item attribute name defined in #2.
Other details are implementation details describing how UI works (typing name, if match not found then add new item) and are not part of design

 1. **Lists must be saved automatically and immediately after they are modified.**
Add saveList operation to groceryListsHandler class. 
Save timing details implementation (how), not design (what). 

 1. **Users must be able to check off items in a list (without deleting them).**
Add attribute checked to class listEntry and checkOffItem operation to groceryList class.

 1. **Users must also be able to clear all the check­off marks in a list at once.**
Add clearAllCheckOffMarks operation to groceryList class.

 1. **Check­off marks for a list are persistent and must also be saved immediately.**
Handled via saveList operation in groceryList class, specified in #5.
Save timing details implementation (how), not design (what). 

 1. **The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).**
Add getListByType operation to groceryList class.

 1. **The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.**
Create class groceryListsHandler with operations saveList, renameList, selectList, deleteList.
selectList in GroceryListHandler loads a selected Grocery list

 1. **The User Interface (UI) must be intuitive and responsive.**
Not related to the design structure.

**Additional Information**

 - GroceryLists are saved/loaded to local file structure, not the database.
 - GroceryLists can be saved at any time in code by calling saveList from the GroceryListsHandler class