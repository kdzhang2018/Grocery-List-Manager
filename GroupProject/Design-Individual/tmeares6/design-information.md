
# Design Information

### Requirements (concisely explained)

**1. A grocery list consists of items the users want to buy at a grocery store. The application must allow users to add items to a list, delete items from a list, and change the quantity of items in the list (e.g., change from one to two pounds of apples).**

For this requirement I added classes _GroceryList_ and _GroceryListEntry_. Class _GroceryListEntry_ contains the item name, quantity, and unit, while class _GroceryList_ contains a list of _GroceryListEntry_ objects and the methods _addListEntry_, _deleteListEntry_, and _changeQuantity_ of a _GroceryListEntry_ object.

**2. The application must contain a database (DB) of items and corresponding item types.**

This requirement will be handled by a RDBMS (to be chosen at a later date) and does not need to be modeled in the UML class diagram. The classes in this diagram where, however, modeled with ORM in mind and will use Hibernate for persistence when implemented.

**3. Users must be able to add items to a list by picking them from a hierarchical list, where the first level is the item type (e.g., cereal), and the second level is the name of the actual item (e.g., shredded wheat). After adding an item, users must be able to specify a quantity for that item.**

The way in which users interact with the GUI does not need to be modeled in the UML class diagram. Database queries powered by Hibernate will interact with the GUI to organize as desired and method _addListEntry_ will be called after the user selects the desired options.

**4. Users must also be able to specify an item by typing its name. In this case, the application must look in its DB for items with similar names and ask the users, for each of them, whether that is the item they intended to add. If a match cannot be found, the application must ask the user to select a type for the item and then save the new item, together with its type, in its DB.**

Searching for an item will also be handled between the GUI and Hibernate through the use of DB queries. Basic CRUD operations will be applied to persistent classes as needed, including the option to create a new Item.

**5. Lists must be saved automatically and immediately after they are modified.**

As mentioned previous, Hibernate will provide persistence to the application.

**6. Users must be able to check off items in a list (without deleting them).**

The _GroceryListEntry_ class contains the property _isChecked_, which can be toggled using the _toggleIsChecked_ method.

**7. Users must also be able to clear all the check-off marks in a list at once.**

For this requirement, the _GroceryList_ class can call the method _uncheckAllEntries_, which will in turn loop through all _GroceryListEntry_ objects within that list and call it's setter for _isChecked_, _setIsChecked_, with parameter _isChecked_=false.

**8. Check-off marks for a list are persistent and must also be saved immediately.**

_GroceryListEntry_ is a persistant class that will act as a junction table between _GroceryList_ table and _Item_ table in the database and will therefore be persisted.

**9. The application must present the items in a list grouped by type, so as to allow users to shop for a specific type of products at once (i.e., without having to go back and forth between aisles).**

Presentation will be handled by the GUI and selecting and organizing will be handled by queries as needed, therefore these do not need to be represented in the UML class diagram as I have designed it.

**10. The application must support multiple lists at a time (e.g., “weekly grocery list”, “monthly farmer’s market list”). Therefore, the application must provide the users with the ability to create, (re)name, select, and delete lists.**

To handle and manipulate the multiple _GroceryList_ objects, I added the _GroceryListController_ class which contains the current (selected) GroceryList and a list of all GroceryLists available. This class provides the (CRUD) methods _createList_, _renameList_, _selectList_, and _deleteList_.

**11. The User Interface (UI) must be intuitive and responsive.**

Not considered because it does not affect the design directly.
