
public class Clients {

	private int id;
	private String nom;
	private String prenom;
	private String phone;
	private String information;
	
	public Clients(){}
		
		public Clients(int _id, String _nom, String _prenom, String _phone, String _information)
		{
			this.id= _id;
			this.nom=_nom;
			this.prenom=_prenom;
			this.phone=_phone;
			this.information=_information;
			
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) {
			this.nom = nom;
		}

		public String getPrenom() {
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getInformation() {
			return information;
		}

		public void setInformation(String information) {
			this.information = information;
		}
	}


