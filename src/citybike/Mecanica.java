package citybike;

public class Mecanica extends Bicicleta {

	public Mecanica(boolean operativa) {
		super(operativa);
	}
	
	public Mecanica() {
		super();
	}
	
	@Override
	public String toString() {
	    return "Mecanica [Codigo=" + getCodigo() + "]";
	}


	@Override
	public String getCodigo() {
	    return "M" + getNumero();  
	}


	@Override
	public boolean usable() {
		return isOperativa();
	}
	
}
