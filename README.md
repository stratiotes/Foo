# Foo
Anthem Test project

# Version 1 notes
The first version is the original code with the bugs and all.

The second version is just some bare essential changes to achieve the desired
outputs along with a simple junit test to automate checking that output.
One of the main problems with the original was that it used float and double
types for money.  I debated using one of the newer money-oriented utility
packages but decided the most direct conversion would be to just go with 
the BigDecimal approach to keep the changes to a minimum.  Made a few other
minor changes just to make it a little more readable toward the last version
still to be done.

# Version 2 notes
The second version is a refactoring to make it easier to maintain.  Primarily
that meant changing variable names to more meaningful names for the context. 
Also wanted to break up the classes to more easily divide the work between a group of team members as well as separating the presentation and tax calculation models from the data model as much as possible.

Created a new class TaxCalculator to encapsulate the tax calculation and allow different instantiations to calculate domestic or import taxes. 

Created a new OrderMoney class that wraps a BigDecimal. I chose to intentionally not extend BigDecimal so as not to introduce assumptions that they two are effectively interchangeable. This allows different rounding rules and limits the kinds of operations you can perform on OrderMoney to only what we expose to clients. 

OrderMoney and TaxCalculator are set to USD Currency by default. If we wanted to extend the classes we would want to alter them to different names for each currency perhaps. But, in the context of this exercise, it seemed a stretch to do so and live with the assumption that we did not need to worry about converting different monetary values. A better approach for later versions of java would be to utilize the Money class developed more recently. 

In addition, I wanted to seperate the data from the application by moving the hard-coded Order information to a resource XML file and encapsulating the unpacking to a seperate class that could, in theory, be altered to use a database or some other file format to contain the Order attributes. Perhaps it would make sense to make the Order class serializable but that seemed a debate for the design team to determine the approach to take.






