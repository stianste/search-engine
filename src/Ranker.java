import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.lang.Math;
import java.util.Map;
import java.util.Set;

public class Ranker {
  private Data data;

  public Ranker(Data data){
    this.data = data;
  }

  public List<Document> getTopNRankings(String query, int topN) {
    HashMap<Document, Double> documentScores = new HashMap<>();

    data.getDocuments().forEach(document -> {
      documentScores.put(document, score(query, document));
    });


    ArrayList<Document> sortedDocuments = sortHashMapByValues(documentScores);
    return sortedDocuments.subList(0, topN);
  }

  private double score(String query, Document document){
    return tfIdf(query, document);
  }

  private double tfIdf(String query, Document document){
    // TODO: Make scorer class
    double score = 0.0;
    List<Integer> queryTermIds = data.getTermIdsFromString(query);
    int numberOfDocuments = data.getDocuments().size();

    for (Integer queryTermId : queryTermIds) {
      score += Math.log(1 + document.getTermFrequency(queryTermId)) * Math.log(numberOfDocuments / data.getTermDocumentFrequency(queryTermId));
    }

    return score;
  }

  private static ArrayList<Document> sortHashMapByValues(final HashMap<Document, Double> map) {
      Set<Document> set = map.keySet();
      ArrayList<Document> keys = new ArrayList<Document>(set);

      Collections.sort(keys, new Comparator<Document>() {
          @Override
          public int compare(Document document1, Document document2) {
               return Double.compare(map.get(document2), map.get(document1));
           }
      });

      return keys;
  }
}
