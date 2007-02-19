/**
 * OntoLearn a seminar project of:
 * - Remy Stibbe
 * - Hesing Kuo
 * - Nico Vaatstra
 * - Jasper Voskuilen
 * 
 */
package nl.eur.eco_ict.seminar.ontolearn.datatypes.impl;

import nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology;

import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * A wrapper class for a jena based ontology
 * @author Jasper
 *
 */
public class JenaOntology implements Ontology {
	/**
	 * the actual ontology
	 */
	private OntModel model = null;
	
	/**
	 * Determines which kind of ontology will be created / modified
	 */
	private OntModelSpec specs = null;
	
	/**
	 * The default namespace
	 */
	private String defaultNS = null;
	
	private DBSettings dbsettings = null;
	
	
	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addIndividual(java.lang.String, java.lang.String)
	 */
	public Individual addIndividual (String classname, String indname) {
		return this.addIndividual (this.getOClass (classname), indname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addIndividual(com.hp.hpl.jena.ontology.OntClass, java.lang.String)
	 */
	public Individual addIndividual (OntClass oclass, String indname) {
		return this.getModel ().createIndividual (indname, oclass);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addOClass(java.lang.String, java.lang.String)
	 */
	public OntClass addOClass (String namespace, String classname) {
		if (namespace != null){
		return this.getModel ().createClass (namespace + classname);
		}
		return this.getModel ().createClass (classname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addOClass(java.lang.String)
	 */
	public OntClass addOClass (String classname) {
		return this.addOClass (this.defaultNS, classname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getOClass(java.lang.String, java.lang.String)
	 */
	public OntClass getOClass (String namespace, String classname) {
		if (namespace != null){
		return this.getModel ().getOntClass (namespace + classname);
		}
		return this.getModel ().getOntClass (classname); 
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getOClass(java.lang.String)
	 */
	public OntClass getOClass (String classname) {
		return this.getOClass (this.defaultNS, classname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#setDefaultNamespace(java.lang.String)
	 */
	public void setDefaultNamespace (String namespace) {
		this.defaultNS = namespace;
	}
	
	public OntModel getModel (){
		if (this.model == null){
			if (this.getDBConnection () == null){
				this.model = ModelFactory.createOntologyModel (this.specs);
			}else{
				ModelMaker maker =  ModelFactory.createModelRDBMaker (this.getDBConnection ());
				Model base = maker.createModel(this.dbsettings.ontologyname, false );
				this.specs = new OntModelSpec (this.getSpecs ());
				this.specs.setImportModelMaker (maker);
				this.model = ModelFactory.createOntologyModel (this.getSpecs (), base);
				this.model.read (this.dbsettings.ontologyname);
			}
		}
		return this.model;
	}
	
	public void setSpecs (OntModelSpec spec){
		this.specs = spec;
	}
	
	protected OntModelSpec getSpecs (){
		if (this.specs == null){
			this.specs =  OntModelSpec.OWL_LITE_MEM;
		}
		return this.specs;
	}
	
	public String toString (){
		return this.getModel ().toString ();
	}
	
	protected IDBConnection getDBConnection (){
		if (this.dbsettings != null){
			//TODO return ModelFactory.createSimpleRDBConnection (arg0, arg1, arg2, arg3);
		}
		return null;
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addDataProperty(java.lang.String, java.lang.String)
	 */
	public OntProperty addDataProperty (String namespace, String propertyname) {
		if (namespace != null){
			return this.getModel ().createDatatypeProperty (namespace + propertyname);
		}
		return this.getModel ().createDatatypeProperty (propertyname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addDataProperty(java.lang.String)
	 */
	public OntProperty addDataProperty (String propertyname) {
		return this.addDataProperty (this.defaultNS, propertyname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addObjectProperty(java.lang.String, java.lang.String)
	 */
	public OntProperty addObjectProperty (String namespace, String propertyname) {
		if (namespace != null){
			return this.getModel ().createObjectProperty (namespace + propertyname);
		}
		return this.getModel ().createObjectProperty (propertyname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addObjectProperty(java.lang.String)
	 */
	public OntProperty addObjectProperty (String propertyname) {
		return this.addDataProperty (this.defaultNS, propertyname);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsIndividual(java.lang.String, java.lang.String)
	 */
	public boolean containsIndividual (String classname, String indname) {
		return this.getModel ().containsResource (this.getIndividual (classname, indname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsIndividual(com.hp.hpl.jena.ontology.OntClass, java.lang.String)
	 */
	public boolean containsIndividual (OntClass oclass, String indname) {
		return this.getModel ().containsResource (this.getIndividual (oclass, indname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsOClass(java.lang.String, java.lang.String)
	 */
	public boolean containsOClass (String namespace, String classname) {
		return this.getModel ().containsResource (this.getOClass (namespace, classname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsOClass(java.lang.String)
	 */
	public boolean containsOClass (String classname) {
		return this.getModel ().containsResource (this.getOClass (classname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsProperty(java.lang.String, java.lang.String)
	 */
	public boolean containsProperty (String namespace, String propertyname) {
		return this.getModel ().containsResource(this.getProperty (namespace, propertyname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsProperty(java.lang.String)
	 */
	public boolean containsProperty (String propertyname) {
		return this.getModel ().containsResource(this.getProperty (propertyname));
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getIndividual(java.lang.String, java.lang.String)
	 */
	public Individual getIndividual (String classname, String indname) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getIndividual(com.hp.hpl.jena.ontology.OntClass, java.lang.String)
	 */
	public Individual getIndividual (OntClass oclass, String indname) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getProperty(java.lang.String, java.lang.String)
	 */
	public OntProperty getProperty (String namespace, String propertyname) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#getProperty(java.lang.String)
	 */
	public OntProperty getProperty (String propertyname) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#removeIndividual(com.hp.hpl.jena.ontology.Individual)
	 */
	public void removeIndividual (Individual ind) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#removeIndividual(java.lang.String, java.lang.String)
	 */
	public void removeIndividual (String classname, String indname) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#removeIndividual(com.hp.hpl.jena.ontology.OntClass, java.lang.String)
	 */
	public void removeIndividual (OntClass oclass, String indname) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#removeTriplet(com.hp.hpl.jena.ontology.OntClass, com.hp.hpl.jena.ontology.OntProperty, com.hp.hpl.jena.ontology.OntClass)
	 */
	public void removeTriplet (OntClass domain, OntProperty property, OntClass range) {
		this.getModel ().remove (domain, property, range);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#containsTriplet(com.hp.hpl.jena.ontology.OntClass, com.hp.hpl.jena.ontology.OntProperty, com.hp.hpl.jena.ontology.OntClass)
	 */
	public boolean containsTriplet (OntClass domain, OntProperty property, OntClass range) {
		return this.getModel ().contains (domain, property, range);
	}

	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addTriplet(com.hp.hpl.jena.ontology.OntClass, com.hp.hpl.jena.ontology.ObjectProperty, com.hp.hpl.jena.ontology.OntClass)
	 */
	public void addTriplet (OntClass domain, ObjectProperty property, OntClass range) {
		if (domain == null || property == null || range == null){
			throw new IllegalArgumentException ();
		}
		property.setDomain (domain);
		property.setRange (range);
	}


	/**
	 * @see nl.eur.eco_ict.seminar.ontolearn.datatypes.Ontology#addTriplet(com.hp.hpl.jena.ontology.OntClass, com.hp.hpl.jena.ontology.DatatypeProperty, com.hp.hpl.jena.rdf.model.Resource)
	 */
	public void addTriplet (OntClass domain, DatatypeProperty property, Resource datum) {
		if (domain == null || property == null || datum == null){
			throw new IllegalArgumentException ();
		}
		property.setDomain (domain);
		property.setRange (datum);
	}


}