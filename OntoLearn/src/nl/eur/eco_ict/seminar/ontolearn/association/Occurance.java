/**
 * OntoLearn a seminar project of:
 * - Remy Stibbe
 * - Hesing Kuo
 * - Nico Vaatstra
 * - Jasper Voskuilen
 * 
 */
package nl.eur.eco_ict.seminar.ontolearn.association;

/**
 * @author remy
 *
 */
public class Occurance {
	public String documentName;
	public String word;
	public int wordCount;
	
	public Occurance() {
		
	}
	/**
	 * @return the documentName
	 */
	public String getDocumentName () {
		return this.documentName;
	}
	/**
	 * @param documentName the documentName to set
	 */
	public void setDocumentName (String documentName) {
		this.documentName = documentName;
	}
	/**
	 * @return the word
	 */
	public String getWord () {
		return this.word;
	}
	/**
	 * @param word the word to set
	 */
	public void setWord (String word) {
		this.word = word;
	}
	/**
	 * @return the wordCount
	 */
	public int getWordCount () {
		return this.wordCount;
	}
	/**
	 * @param wordCount the wordCount to set
	 */
	public void setWordCount (int wordCount) {
		this.wordCount = wordCount;
	}
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode () {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((this.documentName == null) ? 0 : this.documentName.hashCode ());
		result = PRIME * result + ((this.word == null) ? 0 : this.word.hashCode ());
		result = PRIME * result + this.wordCount;
		return result;
	}
}
