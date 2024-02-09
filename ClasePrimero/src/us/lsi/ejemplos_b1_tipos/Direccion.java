package us.lsi.ejemplos_b1_tipos;

public record Direccion(String calle, String ciudad, Integer zipCode) implements Comparable<Direccion> {

	public static Direccion of(String calle, String ciudad, Integer zipCode) {
		return new Direccion(calle, ciudad, zipCode);
	}

	public static Direccion parse(String text) {
		String[] partes = text.split(";");
		return Direccion.of(partes[0], partes[1], Integer.parseInt(partes[2]));
	}

	public String toString() {
		return String.format("%s,%s,%d", this.calle(), this.ciudad(), this.zipCode());
	}

	@Override
	public int compareTo(Direccion other) {
		Integer r = this.zipCode().compareTo(other.zipCode());
		if (r == 0) r = this.ciudad().compareTo(other.ciudad());
		if (r == 0) r = this.calle().compareTo(other.calle());
		return r;

	}

	public static void main(String[] args) {
		Direccion d1 = Direccion.parse("Callejón Virginia Collado 21;Lugo;37687");
		System.out.println(d1);
		Direccion d2 = Direccion.of("Victoria", "Córdoba", 42000);
		System.out.println(d2);
		System.out.println(d1.compareTo(d2));
	}

}
