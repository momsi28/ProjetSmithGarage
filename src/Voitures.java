
public class Voitures {

	private int id;
	private int clientID;
	private String marque;
	private String modele;
	private String annee;

	
	
	public Voitures(){}
	 public String toString(){return marque;}
	 
		public Voitures(int _id, String _marque, String _modele, String _annee, int _clientID)
		{
			this.id = _id;
			this.marque =_marque;
			this.modele =_modele;
			this.annee =_annee;
			this.clientID =_clientID;
			
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getClientID() {
			return clientID;
		}

		public void setClientID(int clientID) {
			this.clientID = clientID;
		}

		public String getMarque() {
			return marque;
		}

		public void setMarque(String marque) {
			this.marque = marque;
		}

		public String getModele() {
			return modele;
		}

		public void setModele(String modele) {
			this.modele = modele;
		}

		public String getAnnee() {
			return annee;
		}

		public void setAnnee(String annee) {
			this.annee = annee;
		}

	
	}


