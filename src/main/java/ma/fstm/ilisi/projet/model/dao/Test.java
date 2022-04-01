package ma.fstm.ilisi.projet.model.dao;

import java.sql.Date;
import java.util.List;

import ma.fstm.ilisi.projet.model.bo.CronicDisease;
import ma.fstm.ilisi.projet.model.bo.Diagnostic;
import ma.fstm.ilisi.projet.model.bo.Patient;
import ma.fstm.ilisi.projet.model.bo.Symptom;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*List<Patient> villesofR=(List<Patient>) new DAOPatient().retreive();
		for(Patient v : villesofR)
			System.out.println(v+"\n");*/
		/*String[] values = new String[] { "fievre", "fatigue", "toux seche", "diarrhe", "essouflement", "chute",
				"frisson", "congestion nasal", "gorge seche", "ecoulement nasal", "dyspnee", "douleurs musculaires",
				"maux de tete", "perte de gout", "perte de l'odorat", "confusion", "nause", "vomissement",
				"conjonctivite" };
		String[] values = new String[] {"Diabete","cardiaque","Hypertendu"};*/
		/*for(Symptom s :new DAOSymptom().retreive())
			System.out.println(s);*/
		//Diagnostic dia =new Diagnostic();
		Patient patient=new DAOPatient().findPatientByIdentifier("bj444970");
		/*dia.setPatient(patient);
		dia.setTemperature(39);
		dia.getPatient().setAge(75);
		dia.setDate_diagnostic(new Date(0));
		dia.addSymptom(new DAOSymptom().findSymptomByName("fievre"));
		dia.addSymptom(new DAOSymptom().findSymptomByName("fatigue"));
		dia.addSymptom(new DAOSymptom().findSymptomByName("toux seche"));
		dia.addSymptom(new DAOSymptom().findSymptomByName("dyspnee"));
		dia.fireAll();
		System.out.println(dia.getPossi_presence());*/
		//new DAODiagnostic().create(dia);
		for(Diagnostic s :new DAODiagnostic().retreive(patient.get_id())) {
			s.fireAll();
			System.out.println(s.getPossi_presence());
		}
	}

}
