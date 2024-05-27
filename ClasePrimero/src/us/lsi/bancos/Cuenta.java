package us.lsi.bancos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cuenta {

	private String iban;
	private String dni;
	private LocalDate fechaDeCreacion;
	private Double saldo;

	private Cuenta(String iban, String dni, LocalDate fechaDeCreacion, Double saldo) {
		super();
		this.iban = iban;
		this.dni = dni;
		this.fechaDeCreacion = fechaDeCreacion;
		this.saldo = saldo;
	}

	public static Cuenta of(String iban, String dni, LocalDate fechaDeCreacion, Double saldo) {
		return new Cuenta(iban, dni, fechaDeCreacion, saldo);
	}

	public static Cuenta parse(String text) {
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String[] partes = text.split(",");
		LocalDate fechaDeCreacion = LocalDateTime.parse(partes[2], fm).toLocalDate();
		Double saldo = Double.parseDouble(partes[3]);
		return Cuenta.of(partes[0], partes[1], fechaDeCreacion, saldo);
	}

	public String iban() {
		return this.iban;
	}

	public String dni() {
		return this.dni;
	}

	public LocalDate fechaDeCreacion() {
		return this.fechaDeCreacion;
	}

	public Double saldo() {
		return this.saldo;
	}

	public void ingresar(Double c) {
		this.saldo += c;
	}

	public void retirar(Double c) {
		this.saldo -= c;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni, fechaDeCreacion, iban, saldo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Cuenta))
			return false;
		Cuenta other = (Cuenta) obj;
		return Objects.equals(dni, other.dni) && Objects.equals(fechaDeCreacion, other.fechaDeCreacion)
				&& Objects.equals(iban, other.iban) && Objects.equals(saldo, other.saldo);
	}

	@Override
	public String toString() {
		return String.format("%s,%s", this.iban(), this.saldo());
	}
	
	public static void main(String[] args) {	
		Cuenta cuenta = Cuenta.parse("ES1161954191929372160031,99212318R,2017-04-09 00:17:51,549928.47");
	    System.out.println(cuenta);
    }
}
