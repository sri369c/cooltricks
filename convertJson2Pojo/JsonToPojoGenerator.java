package com.generator;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class JsonToPojoGenerator {

  private static final String FILENAME_NO_EXTENSION = "AllAppObjectsInfo";
  private static final String OUTPUT_PACKAGE_NAME = "com.generated." + FILENAME_NO_EXTENSION;
  
  public static void main (String[] args) {

    
    File inputJson = new File("C:\\MyWork\\eclipse-oxygen-workspace\\JsonToPojoGenerator\\src\\main\\java\\com\\jsonsamples\\" + FILENAME_NO_EXTENSION + ".json");
    File outputPojoDirectory = new File("C:\\MyWork\\eclipse-oxygen-workspace\\JsonToPojoGenerator\\src\\main\\java\\");
    outputPojoDirectory.mkdirs();
    try {
      System.out.println("inputJson.toURI().toURL() --> " + inputJson.toURI().toURL());
      System.out.println("outputPojoDirectory --> " + outputPojoDirectory);
      new JsonToPojoGenerator().convert2JSON(inputJson.toURI().toURL(), outputPojoDirectory,
          OUTPUT_PACKAGE_NAME, inputJson.getName().replace(".json", ""));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      System.out.println("Encountered issue while converting to pojo: " + e.getMessage());
      e.printStackTrace();
    }
  }

  public void convert2JSON (URL inputJson, File outputPojoDirectory, String packageName,
      String className) throws IOException {
    JCodeModel codeModel = new JCodeModel();

    URL source = inputJson;

    GenerationConfig config = new DefaultGenerationConfig() {
      @Override
      public boolean isGenerateBuilders () { // set config option by overriding method
        return true;
      }

      public SourceType getSourceType () {
        return SourceType.JSON;
      }
    };
    SchemaMapper mapper =
        new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()),
            new SchemaGenerator());
    mapper.generate(codeModel, className, packageName, source);

    codeModel.build(outputPojoDirectory);
  }

}
