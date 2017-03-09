
When porting this tool to Eclipse Neon, consider the following bug:

API Incompatibility between maven Checkstyle-plugin and slf4j
https://bugs.eclipse.org/bugs/show_bug.cgi?id=504041

What needs to be done:
Currently, the pom file of the project to test for conformance is used directly and unchanged, which results in the mentioned exception. The pom file needs to be changed to include the following lines, then it works:

<plugin>
    <artifactId>maven-checkstyle-plugin</artifactId>
        <version>2.15</version>
        <dependencies>
            <dependency>
                <groupId>org.slf4j</groupId>
                <artifactId>jcl-over-slf4j</artifactId>
                <version>1.7.5</version>
            </dependency>
        </dependencies>
</plugin>