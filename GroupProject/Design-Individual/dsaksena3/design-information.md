## CS6300 - Assignment 5 Design Information

This list goes through and talks about the pertinent parts of the UML diagram for each requirement in the assignment serially.


 1) The groceryList item has operations to addItems to a list, deleteItems to a list. 

 2)The Database is an aggregation of Item objects and each Item object has a name and type. This satisfies requirement #2.

 3)The listSelection object which is a result of the association between item and groceryList has a quantity attirbute, this is where the quantity for an object set by a user will be stored. The database has a operation that returns the items grouped by their type, this operation should enable us to generate the heirarchical list in 1 call, the Database also has a getItemsBy type operation which can be used in case the number of items gets too long and we dont want to unload all items in the database at once but when the user clicks on that type in the heirarchical list. This satisfies requirement #3.

 4)The database has a getItemsByName operation through which the UI can lookup an item by name, any fuzzy string matching can be implemented in this operation if we should match on an approximate string. This can be used when the user has to look up an item(s) by name. If the required item is not found that database has an addItem operation which can be used to add the item and its corresponsing type. This satisfies requirement #4.

 5)This requirement can be satisfied by the design based on the implementation and does not affects UML class diagram via a auto save functionality. This satisfies requirement #5.

 6)The listSelection association item has a checkedOff attribute which can store a value if that particular item is checked in that particular groceryList and there are corresponding operations to check or uncheck said item. This satisifies requirement #6.

 7)The groceryList object has a clearAllChecks operation that set the checkedOff attribute to its default value for all listSelections associated with that groceryList. This satisfies requirement #7.

 8)This requirement can be satisfied by the design based on the implementation and does not affects UML class diagram via a auto save functionality. This satisfies requirement #8.

 9)The groceryList has access to all the items associated with it, hence has access to their types and can just sort or group by it internally and return the grouped result when its operation getListItems is called. This satisfies requirement #9.

 10)The User has operations to create and delete groceryList. The association of user to groceryLists is 1 to many i.e. a user can have multple results according to our UML class diagrm. The groceryList object has operations that allow the name of the grocery list to be changed. This satisfies requirement #10.

 11)This requirement can be satisfied by the design based on the implementation and does not affects UML class diagram via a auto save functionality. This satisfies requirement #11.




