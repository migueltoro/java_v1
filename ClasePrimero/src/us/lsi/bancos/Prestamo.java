package us.lsi.bancos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Prestamo(Integer nid, String dniCliente, Double cantidad, LocalDate fechaComienzo, Integer duracion,
		Double interes, String dniEmpleado) {

	public static Prestamo of(Integer nid, String dniCliente, Double cantidad, LocalDate fechaComienzo,
			Integer duracion, Double interes, String dniEmpleado) {
		return new Prestamo(nid, dniCliente, cantidad, fechaComienzo, duracion, interes, dniEmpleado);
	}

//  0,79597814N,26170537X,2023-06-30 20:34:43,51,99353.52,8.27

	public static Prestamo parse(String text) {
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String[] partes = text.split(",");
		LocalDate fechaComienzo = LocalDateTime.parse(partes[3], fm).toLocalDate();
		Integer nid = Integer.parseInt(partes[0]);
		Double cantidad = Double.parseDouble(partes[5]);
		Double interes = Double.parseDouble(partes[6]);
		Integer duracion = Integer.parseInt(partes[4]);
		return Prestamo.of(nid, partes[1], cantidad, fechaComienzo, duracion, interes, partes[2]);
	}

	public LocalDate fechaVencimiento() {
		return this.fechaComienzo.plusMonths(duracion);
	}

	public String toString() {
		DateTimeFormatter fm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return String.format("%s,%s,%.2f", this.dniCliente(), this.fechaComienzo.format(fm), this.cantidad());
	}
}
