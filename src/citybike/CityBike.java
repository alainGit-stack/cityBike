package citybike;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.TreeSet;



public class CityBike {
	private ArrayList<Cliente> clientes;
	private ArrayList<Bicicleta> bicicletas;
	private TreeSet<Estacion> estaciones;
	
	public CityBike(ArrayList<Cliente> clientes, ArrayList<Bicicleta> bicicletas, TreeSet<Estacion> estaciones) {
		super();
		this.clientes = clientes;
		this.bicicletas = bicicletas;
		this.estaciones = estaciones;
	}
	
	public CityBike() {
		super();
		this.clientes = new ArrayList<Cliente>();
		this.bicicletas = new ArrayList<Bicicleta>();
		this.estaciones = new TreeSet<Estacion>();
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
	}

	public ArrayList<Bicicleta> getBicicletas() {
		return bicicletas;
	}

	public void setBicicletas(ArrayList<Bicicleta> bicicletas) {
		this.bicicletas = bicicletas;
	}

	public TreeSet<Estacion> getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(TreeSet<Estacion> estaciones) {
		this.estaciones = estaciones;
	}

	@Override
	public String toString() {
		return "CityBike [clientes=" + clientes + ", bicicletas=" + bicicletas + ", estaciones=" + estaciones + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(estaciones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CityBike other = (CityBike) obj;
		return Objects.equals(estaciones, other.estaciones);
	}
		
	public void cargarClientes(String ruta) {
		
				try {
		    		FileInputStream fis = new FileInputStream(ruta);
		    		ObjectInputStream ois = new ObjectInputStream (fis);
					this.clientes = (ArrayList<Cliente>) ois.readObject();
		    		ois.close();
		    		fis.close();
				} catch (FileNotFoundException e) {
					System.err.println("Fichero no encontrado");
				} catch (IOException e) {
					System.err.println(e);
					System.err.println("Error de entrada/salida");
				} catch (ClassNotFoundException  e) {
					System.err.println("Error al leer objeto");
				}

		    

	}
	
	public Estacion buscarEstacionNombre(String nombre) {
		for (Estacion estacion : estaciones) {
			if(estacion.getNombre().equals(nombre)) {
				return estacion;
			}
		}
		return null;
	}

	public void cargarBicicletasCSV(String ruta) {
	    try {
	        File f = new File(ruta);
	        Scanner sc = new Scanner(f);

	        while(sc.hasNextLine()) {
	            String linea = sc.nextLine();
	            String[] campos = linea.split(";");
	            
	            // Comprobamos que la línea tiene los campos necesarios
	            if (campos.length == 6) {
	                try {
	                    // Datos de la bicicleta
	                    String codigo = campos[0];
	                    String tipo = campos[1];
	                    boolean operativa = Boolean.parseBoolean(campos[2]);
	                    double bateria = 0;
	                    String nombreEstacion = campos[4];
	                    int capacidadEstacion = 0;
	                    
	                    // Intentamos parsear la batería y la capacidad de la estación
	                    try {
	                        bateria = Double.parseDouble(campos[3]);
	                        capacidadEstacion = Integer.parseInt(campos[5]);
	                    } catch (NumberFormatException e) {
	                        System.err.println("Error de formato numérico en la línea: " + linea);
	                        continue; // Pasar a la siguiente línea en caso de error
	                    }

	                    // Crear la bicicleta
	                    Bicicleta bicicleta;
	                    if (tipo.equalsIgnoreCase("Electrica")) {
	                        bicicleta = new Electrica(operativa, bateria);
	                    } else {
	                        bicicleta = new Mecanica(operativa);
	                    }

	                    // Buscar la estación correspondiente
	                    Estacion estacion = buscarEstacionNombre(nombreEstacion);
	                    if (estacion == null) {
	                        // Si la estación no existe, la creamos y la añadimos
	                        estacion = new Estacion(nombreEstacion, capacidadEstacion);
	                        estaciones.add(estacion); 
	                    }

	                    // Añadir la bicicleta a la estación y a la lista de bicicletas
	                    estacion.getBicicletas().add(bicicleta);
	                    bicicletas.add(bicicleta);

	                    // Depuración: Mostrar que la bicicleta se ha añadido correctamente
	                    System.out.println("Bicicleta cargada: " + bicicleta.getCodigo() + " en la estación: " + estacion.getNombre());

	                } catch (ArrayIndexOutOfBoundsException e) {
	                    System.err.println("Error, línea sin suficientes datos: " + linea);
	                } catch (IllegalArgumentException e) {
	                    System.err.println("Error al leer valor de tipo enumerado: " + e.getMessage());
	                }
	            } else {
	                // Si la línea no tiene los campos necesarios
	                System.err.println("Error, línea con formato incorrecto: " + linea);
	            }
	        }

	        sc.close();
	    } catch (IOException e) {
	        System.err.println("Error al leer el archivo: " + e.getMessage());
	    }
	}
	private Estacion estacionConBicicletaAleatoria() {
		ArrayList<Estacion> estacionesConBicicletas = new ArrayList<>();
			for (Estacion estacion : estaciones) {
				if(!estacion.getBicicletas().isEmpty()) {
					estacionesConBicicletas.add(estacion);
				}
			}
			if(estacionesConBicicletas.isEmpty()) {
				return null;
			}
			return estacionesConBicicletas.get((int) (Math.random()*estacionesConBicicletas.size()));
	}
	
	
	
	public void simularReservas() {
		LocalDate fechaInicio = LocalDate.of(2025, 6, 1);
			for (int j = 0; j < 30; j++) {
				 LocalDate fechaReserva = fechaInicio.plusDays(j);
				
				for (int i = 0; i < 100; i++) {
				Cliente cliente = clientes.get((int) (Math.random()* clientes.size()));
				
				Estacion estacionOrigen = estacionConBicicletaAleatoria();
				Bicicleta bicicleta = estacionOrigen.getBicicletas().remove(0);
				Estacion estacionDestino = null;
				
				while(estacionDestino == null || estacionDestino.equals(estacionOrigen)) {
					estacionDestino = estacionConBicicletaAleatoria();
				}
				estacionDestino.getBicicletas().add(bicicleta);
				
				LocalDateTime fechaInicioReserva = fechaReserva.atStartOfDay();
				LocalDateTime fechaFinReserva = fechaReserva.atTime(LocalTime.MAX);
				
				Reserva reserva = new Reserva(cliente, bicicleta, null, null, estacionOrigen, estacionDestino);
				
				cliente.getReservas().add(reserva);
		}
	}}
	
	public HashMap<Cliente, Double> calcularCostesPorCliente() {
		HashMap<Cliente, Double> costesPorCliente = new HashMap<>();
		
		for (Cliente cliente : clientes) {
			double costeTotal =0;
			for(Reserva reserva : cliente.getReservas()) {
				costeTotal += reserva.calcularCoste();
			}
			costesPorCliente.put(cliente, costeTotal);
		}
		return costesPorCliente;
	}
	
	public Cliente clienteMayorCoste(HashMap<Cliente, Double> mapa) {
		Cliente clienteMayorCoste = null;
		double mayorCoste =0;
		
		for (Map.Entry<Cliente, Double> entry : mapa.entrySet()) {
			Cliente cliente = entry.getKey();
			double coste = entry.getValue();
			
			if (coste>mayorCoste) {
				mayorCoste = coste;
				clienteMayorCoste = cliente;
			}
		}
		
		return clienteMayorCoste;
	}
	
}