# Thymeleaf PDF Generator

## Description

This Java application generates a PDF document from a Thymeleaf HTML template populated with data parsed from a JSON string. The data is passed to the Thymeleaf engine, which renders the HTML template. The rendered HTML is then converted into a PDF using `ITextRenderer`.

### Key Components

- **Thymeleaf**: Used for processing and rendering the HTML template with data.
- **Jackson ObjectMapper**: Used to deserialize JSON data into Java objects (List of Maps).
- **ITextRenderer**: Used to convert the rendered HTML into a PDF document.

## Prerequisites

Ensure you have the following installed:

- Java 8 or higher
- Maven (for dependency management)

## Dependencies

Add the following dependencies to your `pom.xml` if using Maven:

```xml
<dependencies>
    <!-- Thymeleaf -->
    <dependency>
        <groupId>org.thymeleaf</groupId>
        <artifactId>thymeleaf</artifactId>
        <version>3.0.15.RELEASE</version>
    </dependency>

    <!-- Jackson for JSON parsing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.13.0</version>
    </dependency>

    <!-- Flying Saucer PDF generation -->
    <dependency>
        <groupId>org.xhtmlrenderer</groupId>
        <artifactId>flying-saucer-pdf-itext2</artifactId>
        <version>9.1.20</version>
    </dependency>

    <!-- IText PDF library -->
    <dependency>
        <groupId>com.lowagie</groupId>
        <artifactId>itext</artifactId>
        <version>2.1.7</version>
    </dependency>
</dependencies>
```

## Project Structure

```plaintext
|-- src
|   |-- main
|       |-- java
|       |   |-- Main.java
|       |-- resources
|               |-- thymeleaf_template.html
|-- pom.xml
```

## How to Run

1. **Clone or Download the Project**  
   Clone this repository or download the code files to your local machine.

2. **Set Up the Template**  
   Place your Thymeleaf HTML template in the `resources/templates` directory. Ensure the template filename matches the name expected in the code (`thymeleaf_template_` in this case).

3. **Compile and Run the Project**  
   Navigate to the root directory of the project in your terminal and run:

   ```bash
   mvn clean install
   java -cp target/your-artifact-name.jar Main
   ```

4. **Output**  
   After running, a PDF file named `t.pdf` will be generated in your home directory (`System.getProperty("user.home")`).

## Code Explanation

### Main.java

- **`main` Method**: 
  - Reads the JSON data source.
  - Converts JSON into a list of maps using Jackson's `ObjectMapper`.
  - Calls the `parseThymeleafTemplate` method to render HTML using Thymeleaf.
  - Calls the `generatePdfFromHtml` method to generate the PDF from the rendered HTML.

- **`parseThymeleafTemplate` Method**:
  - Configures a `ClassLoaderTemplateResolver` to locate Thymeleaf templates with a `.html` suffix.
  - Creates a `Context` object and sets the `data` variable with the list of maps.
  - Processes the template with the context to generate the HTML string.

- **`generatePdfFromHtml` Method**:
  - Sets up `ITextRenderer` and `SharedContext` for rendering.
  - Converts the HTML string into a PDF and saves it in the specified output location.

## Troubleshooting

- **Missing Template**: Ensure the template name matches the string passed in `parseThymeleafTemplate`.
- **File Permissions**: Ensure the application has permissions to write files in the target directory.
- **PDF Rendering Issues**: Make sure the HTML structure is compatible with the PDF rendering engine.

## License

This project is licensed under the MIT License.

