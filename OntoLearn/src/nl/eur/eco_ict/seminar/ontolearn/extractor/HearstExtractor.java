/**
 * 
 */
package nl.eur.eco_ict.seminar.ontolearn.extractor;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.io.*;

import nl.eur.eco_ict.seminar.ontolearn.Extractor;
import nl.eur.eco_ict.seminar.ontolearn.datatypes.Document;
import nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology;
import nl.eur.eco_ict.seminar.ontolearn.util.PartOfSpeechTagger;
import nl.eur.eco_ict.seminar.ontolearn.util.Tokenizer;

/**
 * @author Nico
 *
 */
public class HearstExtractor implements Extractor {

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.Extractor#parse(nl.eur.eco_ict.seminar.ontolearn.datatypes.Document, nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology)
	 */
	public void parse (Document doc, Ontology ontology) {
		System.out.println("HearstExtractor is parsing "+doc.getName ()+".");
		//PartOfSpeechTagger posTagger = PartOfSpeechTagger.Factory.getInstance ();
		
		try {
			List<String> l = Tokenizer.Factory.getInstance().toSentences(doc.readAbstracts());
				
			for (int j = 0, sz = l.size(); j < sz; j++) {
				  String s =  l.get(j);
				  // Each sentence in each line in the abstract can now be parsed individually
				  
				  // Run the pattern finder on the sentence
				  // foundPatterns contains the String NP0 as key, and the String[] NPx as value(s) <-- NPx is an array!
				  Patternator myPatternator = new Patternator();
				  HashMap<String, String[]> foundPatterns = myPatternator.parseString(s);
				  
				  // Test to see if the HashMap works
				  
				  Collection<Entry<String, String[]>> collection = foundPatterns.entrySet();
				  Iterator<Entry<String, String[]>> iterator = collection.iterator();
				  
				  Entry<String, String[]> entry = null;
				  while(iterator.hasNext())
				  { 
				      entry = iterator.next();
				      String key = entry.getKey();
				      String[] value = entry.getValue();
				      
				      // Display NP0 and NPx from foundPatterns (testing)
					  System.out.println("NP0: "+key);
					  System.out.println("NPx: "+Arrays.asList(value));
				  }
				  
				  
				  // Run the POS tagger on the sentence
				  // COMMENTED OUT TO TEST PATTERN FUNCTIONALITY!!!!
				  // System.out.println(posTagger.tagInternal(s) + " \r\n");
				  // String x = posTagger.tagInternal(s); <-- x = pos tagged version of sentence s
		    }
		}
		catch (IOException e) {
			System.out.println("Error: "+e);
		}
	}

}
