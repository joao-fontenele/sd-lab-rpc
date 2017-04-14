package icomp.server.imc;

/* Source: Wikipedia
	< 16 		Magreza grave
	16 a < 17 	Magreza moderada
	17 a < 18,5 Magreza leve
	18,5 a < 25 Saudável
	25 a < 30 	Sobrepeso
	30 a < 35 	Obesidade Grau I
	35 a < 40 	Obesidade Grau II (severa)
	> 40 		Obesidade Grau III (mórbida)
*/
public enum IMCResults {
	MAGREZA_GRAVE(00.0, 16.0, "Magreza grave"),
	MAGREZA_MODERADA(16.0, 17.0, "Magreza moderada"), 
	MAGREZA_LEVE(17.0, 18.5, "Magreza leve"),
	SAUDAVEL(18.5, 25.0, "Saudável"),
	SOBREPESO(25.0, 30.0, "Sobrepeso"),
	OBESIDADE_I(30.0, 35.0, "Obesidade Grau I"),
	OBESIDADE_II(35.0, 40.0, "Obesidade Grau II (severa)"),
	OBESIDADE_III(40.0, 99.9, "Obesidade Grau III (mórbida)");

	private final double imcLB;
	private final double imcUB;
	private final String description;

	IMCResults(double imcLB, double imcUB, String description) {
		this.imcLB = imcLB;
		this.imcUB = imcUB;
		this.description = description;
	}

	/*
	 * Uses the SAUDAVEL imc interval, to calculate the ideal weight, given a
	 * height and returns a string representing the calculated interval
	 */
	public static String getIdealWeightIntervalFromHeight(double height) {
		double weightLB = SAUDAVEL.imcLB * height * height;
		double weightUB = SAUDAVEL.imcUB * height * height;

		return "[" + weightLB + " ," + weightUB + "]";
	}

	/*
	 * tests an imc with the intervals of the results to return the description
	 */
	public static String getDescritptionResultFromIMC(double imc) {
		// deals with invalid negative imcs
		if (imc < 0) {
			imc = 0.0;
		}

		// only works because the results are ordered
		for (IMCResults result : IMCResults.values()) {
			if (result.imcLB <= imc && imc < result.imcUB) {
				return result.description;
			}
		}

		// deals with the upper infinity interval
		return OBESIDADE_III.description;
	}

	public static double calculateIMC(double weight, double height, String sex) {
		return weight / (height * height);
	}

	public static String getIMCDiagnostic(double weight, double height, String sex) {
		double imc = IMCResults.calculateIMC(weight, height, sex);
		String imcResult = "Seu IMC ficou em: " + imc;
		String diagnostic = "Você é considerada(o) uma pessoa: " + IMCResults.getDescritptionResultFromIMC(imc);
		String idealWeight = "Para sua altura, seu peso ideal deve ficar entre: "
				+ IMCResults.getIdealWeightIntervalFromHeight(height);

		return imcResult + "\n" + diagnostic + "\n" + idealWeight;
	}
}
