package icomp.server.imc;

public class IMCInputs {
	private double weight;
	private double height;
	private String sex;

	public IMCInputs(double weight, double height, String sex) {
		super();
		this.weight = weight;
		this.height = height;
		this.sex = sex;
	}

	public double getWeight() {
		return weight;
	}

	public double getHeight() {
		return height;
	}

	public String getSex() {
		return sex;
	}
}
