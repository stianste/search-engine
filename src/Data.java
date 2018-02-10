import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Data {
  private String documentsPath;
  private List<Document> documents;
  private Map<String, Integer> termToId;
  private Map<Integer, String> idToTerm;


  private Map<Integer, Integer> termDocumentFrequency;

  public Data(String documentsPath){
    this.documentsPath = documentsPath;
    this.documents = new ArrayList<>();
    this.termToId = new HashMap<>();
    this.idToTerm = new HashMap<>();
    this.readData();
    this.termDocumentFrequency = getTermDocumentFrequenies();
  }

  public List<Document> getDocuments() {
    return documents;
  }

  public Map<String, Integer> getTermToId() {
    return termToId;
  }

  public Map<Integer, String> getIdToTerm() {
    return idToTerm;
  }

  public int getTermDocumentFrequency(int termId) {
    if (termDocumentFrequency.containsKey(termId))
      return termDocumentFrequency.get(termId);
    else
      return 0;
  }

  public List<Integer> getTermIdsFromString(String s) {
    List<Integer> termIds = new ArrayList<>();

    String[] terms = s.split("\\s+");
    Arrays.stream(terms).map(this::tokenizeTerm).forEach(term -> {
      if (this.termToId.containsKey(term))
        termIds.add(this.termToId.get(term));
    });
    return termIds;
  }

  public String getDocumentAsString(Document document) {
    String s = "";

    for (Integer termId : document.getTermIds()) {
      s += this.idToTerm.get(termId) + " ";
    }
    return s;
  }

  private void addTerm(String term) {
    term = tokenizeTerm(term);
    if (termToId.containsKey(term))
      return;
    else
      termToId.put(term, this.termToId.size());
      idToTerm.put(this.idToTerm.size(), term);
  }

  private String tokenizeTerm(String term){
    return term.toLowerCase().replaceAll("[^A-Za-z0-9]", "");
  }

  private Document readDocument(Path filePath){
    List<Integer> documentTerms = new ArrayList<>();

    try (Stream<String> stream = Files.lines(filePath)) {
      stream.forEach(line -> {
          String[] terms = line.split("\\s+");
          Arrays.stream(terms).map(this::tokenizeTerm).forEach(term -> {
            addTerm(term);
            documentTerms.add(termToId.get(term));
          });
          }
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new Document(documentTerms);
  }

  private void readData() {
    try (Stream<Path> paths = Files.walk(Paths.get(this.documentsPath))) {
    paths
        .filter(Files::isRegularFile)
        .forEach(file -> documents.add(readDocument(file)));
    } catch (IOException e) {
      System.out.println("Couldn't read documents");
    }
  }

  private Map<Integer, Integer> getTermDocumentFrequenies() {
    Map<Integer, Integer> termDocumentFrequencies = new HashMap<>();
    documents.forEach(document -> {
      idToTerm.keySet().forEach(termId -> {
        if (document.hasTerm(termId))
          if (termDocumentFrequencies.containsKey(termId))
            termDocumentFrequencies.put(termId, termDocumentFrequencies.get(termId) + 1);
          else
            termDocumentFrequencies.put(termId, 1);
      });
    });
    return termDocumentFrequencies;
  }

}
