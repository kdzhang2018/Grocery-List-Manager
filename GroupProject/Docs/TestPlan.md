# Test Plan - v2.0
**Author**: Team39

## 1 Testing Strategy

### 1.1 Overall strategy
Thorough testing is integral to the long term success of a project. The Team39 Grocery List Manager app will be subject to Unit and System testing; no dedicated integration or regression testing are currently planned for. Unit testing will be done on a method by method basis and developers will be responsible for creating and running each unit test for their own methods before merging any development code. Unit tests must be updated and re-run for any and all changes to code before merging as well - this will serve as our regression testing. System testing will be conducted at the end of each iteration on the prototype to be delivered. All testing will be reviewed periodically by the QA Test Design Lead, Travis Meares, to ensure the quality and standards needed for the project. 

### 1.2 Test Selection
All unit testing will be white-box JUnit tests created and run by the developer and reviewed by the QA Test Design Lead. System testing of each deliverable will also be white-box testing done by the entire development team. Four black-box system tests will be arranged for the final product: a concise set of tasks will be written by the QA Test Design Lead and each developer will have a family or friend perform the tasks while being observed. No assistance or additional instruction will be provided to the tester by the developer.

### 1.3 Adequacy Criterion
To ensure adequacy, unit tests will created for all boundary values for each partition of each method based on the domain established by its contract. The developers of each method will be most familiar with the code so they would be best to write all possible cases. The QA Test Design Lead will work to understand and check to ensure that all possible partitions were accounted for during the unit tests.

### 1.4 Bug Tracking
We are planning to use github for tracking issues as we are already using it for version control. Github's Issue Tracker offers a sophisticated bug tracking system and allows issues and their fixes to be linked to commits which allows for even better tracking than a standalone tracking system.

### 1.5 Technology
Currently we only plan on using JUnit for testing, but other technologies are being looked into.

## 2 Test Cases
| Test Case | Purpose | Steps | Expected Result | Actual Result | P/F Info | Notes | 
| :-------: | ------- | ----- | --------------- | ------------- | -------- | ----- | 
| Manage Lists | Verify that a user can create, rename, select, and delete a GroceryList | Open application | Application is opened<br> User data is loaded | as expected | P | none | 
| | | Create list with name *Shopng List* | List is added to GroceryList table in DB with name *Shopng List* | as expected | P | none | 
| | | Rename *Shopng List* to *Shopping List* | *Shopng List*'s name value in DB is changed to *Shopping List* | as expected | P | none | 
| | | Create list with name *Alternate List* | List is added to GroceryList table in DB with name *Alternate List*| as expected | P | none | 
| | | Select *Shopping List* | Go to entry list page of *Shopping List*: empty | as expected | P | none | 
| | | Delete *Shopping List* | *Shopping List* no longer exists in GroceryList table in DB | as expected | P | none | 
| | | Create list with same name *Alternate List* | Display error message | as expected | P | none | 
| | | Delete *Alternate List* | *Alternate List* no longer exists in GroceryList table in DB | as expected | P | none | 
| | | | | | | | 
| SEARCH/ADD ITEM | Verify that a User can search for and add new Items to the database | Open application | Application is opened<br> User data is loaded | as expected | P | none | 
| | | Create list with name *Farmers Market* | List is added to GroceryList table in DB with name *Farmers Market* |  as expected | P  | none | 
| | | Select *Farmers Market* | Go to entry list page of *Farmers Market*: empty |  as expected | P  | none |
| | | Click SEARCH/ADD ITEM | A new page with search bar appears | as expected | P | none | 
| | | Type *carrot* [not in the database] into item search textbox | No similar items should be returned | as expected | P | none | 
| | | Choose item type *produce* from the dropdown menu and click *ADD NEW* button | *carrot* is added to the database and to the list with quantity as 1 and unit as count <br>Return back to the entry list | as expected | P | none | 
| | | Click SEARCH/ADD ITEM | A new page with search bar appears | as expected | P | none | 
| | | Type *carr* into search textbox | *carrot* appears as a suggestion | as expected | P | none | 
| | | Click *carrot* | display error message that *carrot* is already in the list | as expected | P | none | 
| | | Type *carrot* into search textbox and click *ADD NEW* | display error message that *carrot* is already in the database | as expected | P | none | 
| | | Type *bagel* [in the database] into search textbox | *bagels* appears as a suggestion | as expected | P | none | 
| | | Click *bagels* | *bagels* is added to the list with quantity as 1 and unit as count <br>Return back to the entry list| as expected | P | none | 
| | | Click SEARCH/ADD ITEM | A new page with search bar appears | as expected | P | none | 
| | | Click *RETURN TO ENTRY LIST* | Return back to entry list | as expected | P | none | 
| | | | | | | | 
| BROWSE/ADD ITEMs | Verify that a User can browse items in the database under different types and add entries to the list | Open application | Application is opened<br> User data is loaded | as expected | P | none | 
| | | Select *Farmers Market* | Go to entry list page of *Farmers Market* |  as expected | P  | none | 
| | | Click *BROWSE/ADD ITEMS* | A new page with 5 item types appear | as expected | P | none | 
| | | Click *MEAT* from ItemType list | Items with item type *meat* are shown | as expected | P | none | 
| | | Click *chicken* | *chicken* is added to the list with quantity as 1 and unit as count | as expected | P | none | 
| | | Click *DAIRY* from ItemType list | Items with item type *dairy* are shown | as expected | P | none | 
| | | Click *milk* | *milk* is added to the list with quantity as 1 and unit as count | as expected | P | none | 
| | | Click *milk* again | display error message that *milk* is already in the list | as expected | P | none | 
| | | Click *RETURN TO ENTRY LIST* | Return back to entry list | as expected | P | none | 
| | | | | | | | 
| Manage Entries | Verify that a User can check/uncheck, change quantity/unit, and delete a GroceryListEntry | Open Application | Application is opened<br> User data is loaded | as expected | P | none | 
| | | Select *Farmers Market* | Go to entry list page of *Farmers Market*: *milk*, *chicken*, *carrot*, *bagels* | as expected | P | none | 
| | | Click the checkbox next to *milk* entry | *milk* becomes checked|  as expected | P | none | 
| | | Click the checkbox next to *chicken* entry | *chicken* becoms checked | as expected | P | none | 
| | | Click the checkbox next to *carrot* entry | *carrot* becomes checked | as expected | P | none | 
| | | Click the checkbox next to *milk* entry | *milk* becomes unchecked| as expected | P | none | 
| | | Click *CLEAR CHECKS* button| *chicken* and *carrot* become unchecked| as expected | P | none | 
| | | Click edit button next to *1* in *chicken* entry, enter new quantity *2*, click *Change* | *chicken* quantity becomes *2* | as expected | P | none | 
| | | Click edit button next to *2* in *chicken* entry, enter new quantity *3*, click *Cancel* | *chicken* quantity stays as *2* | as expected | P | none | 
| | | Click edit button next to *count* in *milk* entry, enter new unit *gallon*, click *Change* | *milk* unit becomes *gallon* | as expected | P | none | 
| | | Click edit button next to *gallon* in *milk* entry, enter new unit *liter*, click *Cancel* | *milk* unit stays as *gallon* | as expected | P | none | 
| | | Click delete button in *carrot* entry and click *DELETE* in the new dialog | *carrot* is deleted | as expected | P | none | 
| | | Click delete button in *milk* entry and click *CANCEL* in the new dialog | *milk* is not deleted | as expected | P | none | 
| | | Click *RETURN TO GROCERY LIST* | Return back to grocery list page| as expected | P | none | 
| | | Delete list *Farmers Market* | *Farmers Market* is deleted | as expected | P | none | 

## 2.1 Black Box UX and Functionality Tests

For black box tests we are asking people unfamiliar with the app to try to complete a set of predefined tasks using the app and recording their experience. We hope this task will help us not only to figure out unintutive aspects of the app, but the users might do something unexpected which may discover a bug or unintended behaviour.

The following is the introduction for the app we give to the user:

Grocery List Manager, just like the name suggests, is an app that manages multiple grocery lists for you. It gives you the ability to create, rename, and delete multiple grocery lists. The app allows you to search or look through our database of items organized into broad grocery groups similiar to how aisles in a grocery store might be organized. If there is an item that we don't have in our database that you would like, you can also go ahead and add that item to the database.
Once you have added an item to your grocery list, the app allows you to modify what quantity of the item you would like to buy along with the units associated with that quantity. You can, of course, remove an item from a grocery list as well as check it off the list without removing it from the list.

As a part of a user study we would like you to simulate building a grocery list for when you go shopping and buy the items. Here are  the tasks we want you to accomplish:

1. Create a grocery list.

2. Add 3 items to the list by browsing.

3. Add 3 items to the list by searching.

4. Add 2 items to the database and add them to your list.

5. Change the quantity and units of at least 1 item.

6. Check off all items in the list.

7. Rename the list by appending the string "Completed" to it previous name.

8. Delete the list you created.


## 2.2 Observations from Black Box UX and Functionality Tests

###Reporter - Dhruv

####Observations:

The tester was able to carry out most of the tasks pretty easily and thought the UI was fairly straight forward. For Step 3 the user was not entirely sure about how to add an item to their list. The user was also confused by the fact that adding something to the databse added it to the list, but later rationalized that makes sense when you are using the app but maybe less sense when you are following steps from a user testing guide.

No unintended functionality was found in the App by the tester.

The app was deemed straight forward and easily uasable.

###Reporter - Kaidi

####Observations:
My friend performed all the tasks successfully. She said that the app runs quite smoothly and fast.  
She thought that the page of Search / Add Item is a little bit confusing. She was not sure what the dropdown menu was for (to add new item to the item type). Toast message on the searching page was kind of delayed.

###Reporter - Robert

####Observations:
Tester Tim carried out all of the tasks succesfully. There was some confusion on how to load a list after creating it (have to click on it). The browse and search functions worked as expected and without confusion for him. 

No bugs or unexpected results occured during his use of the applicaiton.

Overall thought it was pretty easy to use and worked as a grocery list manager.

###Reporter - Travis

####Observations:
Test was carried out on a physical mobile device, while UI was mostly developed and tested through an emulator, so minor sizing issues occurred during test. Regardless of this, the tester was able to complete all tasks. She commented that the app has a clean, minimalistic look and runs very smoothly.

Complications during test listed below. Impact / importance of the complication is in brackets:

1. Add item to database functionality was ambiguous at first, but becomes clear after use or explanation. [minor]
2. Searching for a specific item was confusing due to small initial database size. [n/a]
3. ItemType dropdown wasn't fully shown in seach/add new item, thus user didn't notice it and all new items were created under ItemType "bread". [major]
4. Tester didn't attribute "count" as the default unit and assumed it was the area to replace with your desired count. Tester recommended changing default to "unit". [minor]
5. Renaming grocery list was confusing because previous list name doesn't disappear from textbox when selected, nor does typing indicator appear. [minor]

Overall, all complications were fairly minor and can easily be fixed via familiarity, providing a user manual, or modifying app slightly.
