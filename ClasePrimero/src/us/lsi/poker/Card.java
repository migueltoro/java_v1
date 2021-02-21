package us.lsi.poker;

import java.util.Arrays;
import java.util.List;

import us.lsi.tools.Preconditions;

public record Card(Integer id, Integer palo, Integer valor) implements Comparable<Card> {
	
	public static Card of(String text) {
		Character pc = text.charAt(text.length()-1);
		String ind = text.substring(0,text.length()-1);
		Integer palo = Card.symbolsPalos.indexOf(pc);
		Integer valor = Card.nombreValores.indexOf(ind);
		return Card.of(valor, palo);
	}
	
	public static Card of(Integer id) {
		Preconditions.checkArgument(id >= 0 && id < 52,"No es posible");
		Integer palo = id % 4;
		Integer valor = id % 13;
		return new Card(id,palo,valor);
	}
	
	public static Card of(Integer valor, Integer palo) {
		return new Card(palo*4+valor,valor, palo);
	}

	public static List<String> nombreValores = Arrays.asList("2","3","4","5","6","7","8","9","10","J","Q","K","A");
	public static List<Character> symbolsPalos = Arrays.asList('C', 'H', 'S', 'D');
	public static List<String> nombrePalos = Arrays.asList("clubs","hearts","spades","diamonds");
	

//	Integer palo; // [0,4)
//	Integer valor; // [0,12)
//	Integer id; // [0,52)


	@Override
	public String toString() {
		return Card.nombreValores.get(valor)+Card.symbolsPalos.get(palo);
	}
	
	public String nameFile() {
		String r = null;
		
		if(valor<9)
			r = String.format("resources/images/%s_of_%s.svg",nombreValores.get(valor),nombrePalos.get(palo));
		else {
			switch(valor) {
			case 9: r = String.format("resources/images/jack_of_%s.svg",Card.nombrePalos.get(palo)); break;
			case 10: r = String.format("resources/images/queen_of_%s.svg",Card.nombrePalos.get(palo)); break;
			case 11: r = String.format("resources/images/king_of_%s.svg",Card.nombrePalos.get(palo)); break;
			case 12: r = String.format("resources/images/ace_of_%s.svg",Card.nombrePalos.get(palo)); break;
//			case 13: r = "resources/images/red_joker.svg"; break;
//			case 14: r = "resources/images/black_joker.svg"; break;
			default:  Preconditions.checkArgument(false,"Indentificador nop posible");
			}
		}
		return r;
	}

	@Override
	public int compareTo(Card card) {
		return this.valor.compareTo(card.valor);
	}

}
