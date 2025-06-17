package citybike;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        CityBike cityBike = new CityBike();

        // Cargar los datos desde los archivos
        cityBike.cargarClientes("clientes.dat");
        cityBike.cargarBicicletasCSV("bicicletas-errores.csv");

        // Mostrar la cantidad de clientes, bicicletas y estaciones cargadas
        System.out.println("Clientes cargados: " + cityBike.getClientes().size());
        System.out.println("Bicicletas cargadas: " + cityBike.getBicicletas().size());
        System.out.println("Estaciones cargadas: " + cityBike.getEstaciones().size());

        // Simular las reservas para los 30 días de junio
        cityBike.simularReservas();

        // Calcular los costes por cliente y mostrarlos
        System.out.println("\nCostes por cliente:");
        cityBike.calcularCostesPorCliente().forEach((cliente, coste) -> {
            System.out.println(cliente.getTelefono() + " | Coste total: " + coste);
        });

        // Mostrar el cliente con el mayor coste
        Cliente clienteMayorCoste = cityBike.clienteMayorCoste(cityBike.calcularCostesPorCliente());
        if (clienteMayorCoste != null) {
            System.out.println("\nTelefono del cliente con el mayor coste: " + clienteMayorCoste.getTelefono());
        } else {
            System.out.println("\nNo hay clientes registrados.");
        }
    }
}


