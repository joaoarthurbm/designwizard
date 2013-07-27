Design Wizard
=============

Visit http://www.designwizard.org to get information 
about the usage of Design Wizard.

Contributing 
------------

Want to contribute to this project? Awesome! Just remember to fork and add
an upstream remote pointing to the official repository. Doing this you can keep
track of the official repository and keep your code updated.

### Instructions to Eclipse users

This project is not deployed as an Eclipse project. In order to import
*DesignWizard* properly, consider the following steps:

1. Clone the project from the official GitHub repository
2. On *Eclipse*, create a Java project **with the same name** as the cloned
folder. *Eclipse* will understand that you are configuring an existing project.
**Click NEXT** instead of Finish.
3. Select `src` and `src_tests` and select *Add to Java build path*
4. In *Default output folder*, replace `bin` by `classes`
5. On the *Libraries* tab, click on *Add JARs...*
6. Select **all** jars in the *lib* folder and press *OK*
7. Finish the *New Project Wizard*
