import static org.junit.Assert.*;

import org.junit.Test;

public class DataTest {

  private Data data = new Data(SearchEngine.documentPath);

  @Test
  public void testCorrectTermIds(){
    // Make sure the two hash maps termToId and idToTerm match
    data.getIdToTerm().keySet().forEach(id -> {
      assertEquals(data.getTermToId().get(data.getIdToTerm().get(id)), id);
    });

  }

}