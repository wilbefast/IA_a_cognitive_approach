package ac.analysis.structure;

/**
 * Mod�lise une requ�te conjonctive : une liste d'atomes. 
 * La classe BaseFaits poss�dant toutes les fonctionnalit�es attendues par une requ�te,
 * cette classe en est une sp�cialisation : notamment, elle ne modifie que la m�thode toString
 *
 */
public class Query extends FactBase {
//Les constructeurs 
	public Query() {
	}

	public Query(Atom a) {
	  super.addNewFact(a);
	}
	public Query(FactBase BF) {
		super(BF);
	}

	public Query(String baseFaits) {
		super(baseFaits);
	}
// La m�thode toString de la classe	
	public String toString()
	{
		String s = "Nombre d'atomes : "+atomList.size()+ "\n";
		s+="Liste des atomes : \n";
		for(int i=0;i<atomList.size();i++)
		{
			s += "\tAtome " + (i+1) + " : " + atomList.get(i) + "\n";			
		}
	 return s;
	}

}
