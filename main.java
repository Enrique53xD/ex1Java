// Lista enlazada simple con metodos de agregar, eliminar e imprimir.
public class main {
	
	static class Nodo {
		int dato;
		Nodo siguiente;

		Nodo(int dato) {
			this.dato = dato;
			this.siguiente = null;
		}
	}

	static class ListaEnlazadaSimple {
		private Nodo cabeza;

		public void agregar(int dato) {
			Nodo nuevo = new Nodo(dato);

			if (cabeza == null) {
				cabeza = nuevo;
				return;
			}

			Nodo actual = cabeza;
			while (actual.siguiente != null) {
				actual = actual.siguiente;
			}
			actual.siguiente = nuevo;
		}

		public boolean eliminar(int dato) {
			if (cabeza == null) {
				return false;
			}

			if (cabeza.dato == dato) {
				cabeza = cabeza.siguiente;
				return true;
			}

			Nodo actual = cabeza;
			while (actual.siguiente != null && actual.siguiente.dato != dato) {
				actual = actual.siguiente;
			}

			if (actual.siguiente == null) {
				return false;
			}

			actual.siguiente = actual.siguiente.siguiente;
			return true;
		}

		public void imprimir() {
			if (cabeza == null) {
				System.out.println("Lista vacia");
				return;
			}

			Nodo actual = cabeza;
			while (actual != null) {
				System.out.print(actual.dato);
				if (actual.siguiente != null) {
					System.out.print(" -> ");
				}
				actual = actual.siguiente;
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		ListaEnlazadaSimple lista = new ListaEnlazadaSimple();
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		int opcion;

		do {
			System.out.println("\n--- Menu ---");
			System.out.println("1. Agregar elemento");
			System.out.println("2. Eliminar elemento");
			System.out.println("3. Imprimir lista");
			System.out.println("4. Salir");
			System.out.print("Elige una opcion: ");
			opcion = scanner.nextInt();

			switch (opcion) {
				case 1:
					System.out.print("Ingresa el numero a agregar: ");
					int datoAgregar = scanner.nextInt();
					lista.agregar(datoAgregar);
					System.out.println("Elemento agregado.");
					break;
				case 2:
					System.out.print("Ingresa el numero a eliminar: ");
					int datoEliminar = scanner.nextInt();
					if (lista.eliminar(datoEliminar)) {
						System.out.println("Elemento eliminado.");
					} else {
						System.out.println("Elemento no encontrado.");
					}
					break;
				case 3:
					System.out.println("Lista actual:");
					lista.imprimir();
					break;
				case 4:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opcion invalida.");
			}
		} while (opcion != 4);

		scanner.close();
	}
}
