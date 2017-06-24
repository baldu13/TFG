package test;

import dao.ExperimentApplicationDAO;

public class SimpleTest {

	private SimpleTest(){}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExperimentApplicationDAO dao = new ExperimentApplicationDAO();
		
		System.out.println(dao.getResultadosExperimento(43).getResultados().size());
	}

}
