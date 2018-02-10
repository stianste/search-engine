import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

public class Document {
  private List<Integer> termIds;
  private Set<Integer> vocabulary;
  private Map<Integer, Integer> termFrequencies;

  public Document(List<Integer> terms){
    this.termIds = terms;
    this.vocabulary = createVocabulary(terms);
    this.termFrequencies = countTermFrequencies(terms);
  }

  public List<Integer> getTermIds() {
    return termIds;
  }

  public int getDocumentLength() {
    return termIds.size();
  }

  public boolean hasTerm(int termId){
    return this.vocabulary.contains(termId);
  }

  public int getTermFrequency(int termId){
    if (this.termFrequencies.containsKey(termId))
      return this.termFrequencies.get(termId);
    return 0;
  }

  private Map<Integer, Integer> countTermFrequencies(List<Integer> termIds){
    Map<Integer, Integer> termFrequencies = new HashMap<>();
    termIds.forEach(termId -> {
      if (termFrequencies.containsKey(termId))
        termFrequencies.put(termId, termFrequencies.get(termId) + 1);
      else
        termFrequencies.put(termId, 1);
    });
    return termFrequencies;
  }

  private Set<Integer> createVocabulary(List<Integer> termIds) {
    Set<Integer> vocabulary = new HashSet<>();
    termIds.forEach(vocabulary::add);
    return vocabulary;
  }
}
