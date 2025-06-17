package citybike;

public class Electrica extends Bicicleta implements Recargable {
	private double bateria;

	public Electrica(boolean operativa, double bateria) {
		super(operativa);
		this.bateria = bateria;
	}

	public Electrica() {
		super();
		this.bateria = 0;
	}
	
	public double getBateria() {
		return bateria;
	}

	public void setBateria(double bateria) {
		this.bateria = bateria;
	}

	@Override
	public String toString() {
		return "Electrica [bateria=" + bateria + ", Codigo()=" + getCodigo() + "]";
	}

	@Override
	public String getCodigo() {
		return "E" + getNumero();
	}

	@Override
	public boolean usable() {
		return (bateria > 80 && isOperativa() );
	}

	@Override
	public void recargar() {
		this.setBateria(100.0);
		
	}
	
}
