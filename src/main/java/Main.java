import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;

public class Main {

    public static void main(String[] args) {
        try {
            String JSON_SOURCE = "[\n" +
                    "  {\n" +
                    "    \"Name\": \"John Doe\",\n" +
                    "    \"Id\": \"12345\",\n" +
                    "    \"Address\": \"123 Elm Street\",\n" +
                    "    \"Company\": \"Tech Corp\",\n" +
                    "    \"employmentContract\": \"Contract123\",\n" +
                    "    \"Total_income\": \"50000\",\n" +
                    "    \"Car_price\": \"20000\",\n" +
                    "    \"Car_type\": \"Sedan\",\n" +
                    "    \"Next\": \"2025\",\n" +
                    "    \"Statement_number\": \"67890\",\n" +
                    "    \"Invoice_number\": \"INV123\",\n" +
                    "    \"Purchase_agreement\": \"Agreement456\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"Name\": \"Jane Smith\",\n" +
                    "    \"Id\": \"67890\",\n" +
                    "    \"Address\": \"456 Oak Avenue\",\n" +
                    "    \"Company\": \"Innovate Inc\",\n" +
                    "    \"employmentContract\": \"Contract789\",\n" +
                    "    \"Total_income\": \"60000\",\n" +
                    "    \"Car_price\": \"25000\",\n" +
                    "    \"Car_type\": \"SUV\",\n" +
                    "    \"Next\": \"2026\",\n" +
                    "    \"Statement_number\": \"12345\",\n" +
                    "    \"Invoice_number\": \"INV456\",\n" +
                    "    \"Purchase_agreement\": \"Agreement789\"\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"Name\": \"Michael Johnson\",\n" +
                    "    \"Id\": \"11223\",\n" +
                    "    \"Address\": \"789 Pine Road\",\n" +
                    "    \"Company\": \"Global Tech\",\n" +
                    "    \"employmentContract\": \"Contract101\",\n" +
                    "    \"Total_income\": \"70000\",\n" +
                    "    \"Car_price\": \"30000\",\n" +
                    "    \"Car_type\": \"Convertible\",\n" +
                    "    \"Next\": \"2027\",\n" +
                    "    \"Statement_number\": \"67891\",\n" +
                    "    \"Invoice_number\": \"INV789\",\n" +
                    "    \"Purchase_agreement\": \"Agreement101\"\n" +
                    "  }\n" +
                    "]";

            // Deserialize the JSON source into a List of Maps
            List<Map<String, Object>> data = new ObjectMapper().readValue(JSON_SOURCE, List.class);

            // Pass the list of maps to Thymeleaf
            String ht = parseThymeleafTemplate("thymeleaf_template_", data);
            generatePdfFromHtml(ht, "t");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static String parseThymeleafTemplate(String filename, List<Map<String, Object>> data) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("data", data); 
        return templateEngine.process(filename, context);
    }

    public static void generatePdfFromHtml(String html, String fname) throws DocumentException, IOException {
        String outputFolder = System.getProperty("user.home") + File.separator + fname + ".pdf";
        OutputStream outputStream = new FileOutputStream(outputFolder);

        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        sharedContext.setPrint(true);
        sharedContext.setInteractive(false);
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        System.out.println("File Generated");
    }
}
