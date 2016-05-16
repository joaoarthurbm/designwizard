# Design Wizard

Visit http://www.designwizard.org to get information 
about the usage of Design Wizard.

## Contributing

Want to contribute to this project? Awesome! Just remember to fork and add
an upstream remote pointing to the official repository. Doing this you can keep
track of the official repository and keep your code updated.

1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -m "Add some feature"`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request  :)

English is the universal language nowadays, so please don't create or comment on issues using another language.

## Continuos Integration with [Travis](https://travis-ci.org)

[![Build Status](https://travis-ci.org/joaoarthurbm/designwizard.svg)](https://travis-ci.org/joaoarthurbm/designwizard)

## Instructions to Eclipse users

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

## Instructions to Maven users

The DesignWizard project has the ASM dependency considered optional in the your pom.xml file. So, to use the DesignWizard API is necessary to inform the following dependencies:

        <dependency>
            <groupId>org.designwizard</groupId>
            <artifactId>designwizard</artifactId>
            <version>1.4</version>
            <scope>compile</scope>
        </dependency>
        <dependency>
            <groupId>asm</groupId>
            <artifactId>asm</artifactId>
            <version>3.1</version>
        </dependency>


## History

For detailed changelog, see [Releases](https://github.com/joaoarthurbm/designwizard/releases).

## License

[MIT License](http://opensource.org/licenses/MIT)
