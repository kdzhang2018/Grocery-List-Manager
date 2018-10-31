*1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).*  

I added to the design three classes: **User**, **List** (with an attribute name) and **Item** (with an attribute name) and several operations **addItem**, **deleteItem** and **changeQuantity** within the class List.  
The relationship between List and Item is **aggregation**, because List consists of multiple Items. I named the aggregation relationship as **Include** and added a float attribute **quantity**.  
The relationship between User and List is **association**, because User has List. I named this relationship as **Own**.
*2. The application must contain a database (DB) of items and corresponding item types.*  

I added a class **Type** with an attribute name.  
*3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.*  

I added a method **viewAllTypes** in the class User to display all Types. I also added a method **viewAllItems** in the class Type to display all the Items in one Type.  
The relationship between User and Type is **dependence**.

*4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type.*  

I added one method **searchItem** within the class User. This method will return Items with similar names.  
The relationship between User and Item is **dependence**, because User uses Item to search for Item.  
To realize the requirement of adding a new Item to the database, I added an operation **addNewItem** within the class Type.  
The relationship between Type and Item is **aggregation** with 1 : * cardinality: one Type consists of multiple Items. 
*5. Lists must be saved automatically and immediately after they are modified.*  

Not considered. Any operation that User performs will directly update the database.
*6. Users must be able to check off items in a list (without deleting them).*  

I added an attribute **checkoff** with the aggregation class Include. It is a boolean and the initial value is false. I also added an operation **changeCheckoff** within the class List.
*7. Users must also be able to clear all the check­off marks in a list at once.*  

I added a method **clearAllCheckoff** in the class List.
*8. Check­off marks for a list are persistent and must also be saved immediately.*  

Not considered.
*9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).*  

I added a method **sortItemByType** within the class List.  
I didn't specify the relationship between List and Type, because it is easy to figure out all Types in one list by tracing the Type of all Items in one List. And it also requires additional operations to keep track of the relationship between List and Type every time Items are added or deleted.
*10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.*  

I added the operations **createList**, **renameList**, and **deleteList** within the class User.  
The cardinality of the association class Own between User and List is 1 : *.  
The cardinality of the aggregation class Include between List and Item is * : *, as one List can have many Items and one Item can be on several Lists.
*11. The User Interface (UI) must be intuitive and responsive.*  

Not considered.