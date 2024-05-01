
# Java CLI Process Wrapper

Application to execute command line processes using java.

## Installation

Install maven

```xml
<dependency>
    <groupId>com.dcat23</groupId>
    <artifactId>cli</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage/Examples

```java
public static void main(String[] args) {
    Cli cat = new Cli("cat");
    cat.addOption("pom.xml");
    CliResult result = cat.execute();
    
    // prints the contents of the pom.xml
    System.out.println(result.out());
    
    // cat pom.xml
    System.out.println(result.command());
}
```
### [Progress Callback](src/main/java/com/dcat23/cli/ProgressCallBackImpl.java)
```java
public interface ProgressCallback {
    /**
     * Recieves each line from the running process
     * 
     */
    void processLine(String line);

    /**
     * Calculate time elapsed
     */
    String getElapsed();

    /**
     * Flag to stop processing
     */
    boolean isReady();
}
```

## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://dcatuns.vercel.app/)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/devin-catuns/)
[![twitter](https://img.shields.io/badge/twitter-1DA1F2?style=for-the-badge&logo=twitter&logoColor=white)](https://twitter.com/)

