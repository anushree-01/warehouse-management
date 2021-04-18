# wareshouse-management


The assignment is to implement a warehouse software. 
This software should hold articles, and the articles should contain an identification number, a name and available stock. 
It should be possible to load articles into the software from a file, see the attached inventory.json.
The warehouse software should also have products, products are made of different articles. Products should have a name, price and a list of articles of which they are made from with a quantity. 
The products should also be loaded from a file, see the attached products.json. 
 
The warehouse should have at least the following functionality;
* Get all products and quantity of each that is an available with the current inventory
* Remove(Sell) a product and update the inventory accordingly


##Update

1. Additional Features : Instead of removing the product entirely when it has been sold. An extra column has been added to maintain the status of the product.
This status is added as an enum within the project which shows about the availability of the project. Hence when user searches for the specific product they can view its availability.
2. For enhanced fucntionality I would have also included when the product will be available but due to lack of time couldnt proceed for the same.
3. Junit has been added for get and save functionality of products and inventory.
4. Product price is mentioned above but was not available in the json. Hence I have updated the JSON for the price and done the code changes accordingly.