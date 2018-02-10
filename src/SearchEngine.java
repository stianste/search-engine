public class SearchEngine {
  public final static String documentPath = "./small_sample_of_documents/";

  public static void main(String args[]){
    System.out.println("Search engine started\n");
    Data corpus = new Data(documentPath);

    Ranker ranker = new Ranker(corpus);
    Document topResult = ranker.getTopNRankings("What is norway?", 1).get(0);
    System.out.println(corpus.getDocumentAsString(topResult));
    System.out.println(corpus.getIdToTerm().size());
  }
}
