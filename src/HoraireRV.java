import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;


public class HoraireRV {

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public Date getDateP() {
		return dateP;
	}

	public void setDateP(Date dateP) {
		this.dateP = dateP;
	}

	public Time getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Time heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Time getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Time heureFin) {
		this.heureFin = heureFin;
	}

	private double duree;
	private Date dateP;
	private Time heureDebut;
	private Time heureFin;
	
	public HoraireRV(double d, Date _dateP, Time _heureDebut, Time _heureFin)
	{
		this.duree  = d;
		this.dateP = _dateP;
		this.heureDebut = _heureDebut;
		this.heureFin = _heureFin;
	}
	
}
