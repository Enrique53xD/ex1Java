public class main {

    // ─── Nodo ────────────────────────────────────────────────────────────────────
    static class Nodo {
        int valor;
        int altura;
        Nodo izquierdo, derecho;

        Nodo(int valor) {
            this.valor  = valor;
            this.altura = 1;
        }
    }

    // ─── ArbolAVL ─────────────────────────────────────────────────────────────────
    static class ArbolAVL {

        private Nodo raiz;

        // Altura de un nodo (null = 0)
        int obtenerAltura(Nodo nodo) {
            return (nodo == null) ? 0 : nodo.altura;
        }

        // Factor de balance: altura(izq) - altura(der)
        int obtenerBalance(Nodo nodo) {
            return (nodo == null) ? 0
                    : obtenerAltura(nodo.izquierdo) - obtenerAltura(nodo.derecho);
        }

        // Rotación simple a la derecha (caso LL)
        Nodo rotarDerecha(Nodo y) {
            Nodo x  = y.izquierdo;
            Nodo T2 = x.derecho;

            x.derecho  = y;
            y.izquierdo = T2;

            y.altura = 1 + Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho));
            x.altura = 1 + Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho));

            return x;
        }

        // Rotación simple a la izquierda (caso RR)
        Nodo rotarIzquierda(Nodo x) {
            Nodo y  = x.derecho;
            Nodo T2 = y.izquierdo;

            y.izquierdo = x;
            x.derecho   = T2;

            x.altura = 1 + Math.max(obtenerAltura(x.izquierdo), obtenerAltura(x.derecho));
            y.altura = 1 + Math.max(obtenerAltura(y.izquierdo), obtenerAltura(y.derecho));

            return y;
        }

        // Inserción pública
        void insertar(int valor) {
            raiz = insertarRec(raiz, valor);
        }

        // Inserción recursiva con rebalanceo automático
        private Nodo insertarRec(Nodo nodo, int valor) {
            // 1. BST normal
            if (nodo == null) return new Nodo(valor);

            if (valor < nodo.valor) {
                nodo.izquierdo = insertarRec(nodo.izquierdo, valor);
            } else if (valor > nodo.valor) {
                nodo.derecho = insertarRec(nodo.derecho, valor);
            } else {
                // Duplicado: se ignora
                return nodo;
            }

            // 2. Actualizar altura
            nodo.altura = 1 + Math.max(obtenerAltura(nodo.izquierdo),
                                       obtenerAltura(nodo.derecho));

            // 3. Calcular balance
            int balance = obtenerBalance(nodo);

            // ── LL ──
            if (balance > 1 && valor < nodo.izquierdo.valor) {
                System.out.println("  [Rotacion LL en nodo " + nodo.valor + "]");
                return rotarDerecha(nodo);
            }

            // ── RR ──
            if (balance < -1 && valor > nodo.derecho.valor) {
                System.out.println("  [Rotacion RR en nodo " + nodo.valor + "]");
                return rotarIzquierda(nodo);
            }

            // ── LR ──
            if (balance > 1 && valor > nodo.izquierdo.valor) {
                System.out.println("  [Rotacion LR en nodo " + nodo.valor + "]");
                nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
                return rotarDerecha(nodo);
            }

            // ── RL ──
            if (balance < -1 && valor < nodo.derecho.valor) {
                System.out.println("  [Rotacion RL en nodo " + nodo.valor + "]");
                nodo.derecho = rotarDerecha(nodo.derecho);
                return rotarIzquierda(nodo);
            }

            return nodo;
        }

        // ── Recorrido inOrden ──────────────────────────────────────────────────
        void inOrden() {
            inOrdenRec(raiz);
            System.out.println();
        }

        private void inOrdenRec(Nodo nodo) {
            if (nodo == null) return;
            inOrdenRec(nodo.izquierdo);
            System.out.print(nodo.valor + " ");
            inOrdenRec(nodo.derecho);
        }

        // ── Impresión visual del árbol ─────────────────────────────────────────
        void imprimirArbol() {
            imprimirRec(raiz, "", true);
        }

        private void imprimirRec(Nodo nodo, String prefijo, boolean esRaiz) {
            if (nodo == null) return;
            System.out.println(prefijo + (esRaiz ? "Raiz──" : "├──── ") + nodo.valor
                    + " (h=" + nodo.altura + ", b=" + obtenerBalance(nodo) + ")");
            if (nodo.izquierdo != null || nodo.derecho != null) {
                imprimirRec(nodo.izquierdo,  prefijo + (esRaiz ? "  " : "│     "), false);
                imprimirRec(nodo.derecho,    prefijo + (esRaiz ? "  " : "│     "), false);
            }
        }
    }

    // ─── main ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        // Caso 1 — LL (Izquierda-Izquierda): insertar 30, 20, 10
        System.out.println("═══════════════════════════════════════");
        System.out.println(" Caso LL  →  insertar: 30, 20, 10");
        System.out.println("═══════════════════════════════════════");
        ArbolAVL avl1 = new ArbolAVL();
        for (int v : new int[]{30, 20, 10}) avl1.insertar(v);
        System.out.print("InOrden : ");
        avl1.inOrden();
        avl1.imprimirArbol();

        // Caso 2 — RR (Derecha-Derecha): insertar 10, 20, 30
        System.out.println("\n═══════════════════════════════════════");
        System.out.println(" Caso RR  →  insertar: 10, 20, 30");
        System.out.println("═══════════════════════════════════════");
        ArbolAVL avl2 = new ArbolAVL();
        for (int v : new int[]{10, 20, 30}) avl2.insertar(v);
        System.out.print("InOrden : ");
        avl2.inOrden();
        avl2.imprimirArbol();

        // Caso 3 — LR (Izquierda-Derecha): insertar 30, 10, 20
        System.out.println("\n═══════════════════════════════════════");
        System.out.println(" Caso LR  →  insertar: 30, 10, 20");
        System.out.println("═══════════════════════════════════════");
        ArbolAVL avl3 = new ArbolAVL();
        for (int v : new int[]{30, 10, 20}) avl3.insertar(v);
        System.out.print("InOrden : ");
        avl3.inOrden();
        avl3.imprimirArbol();

        // Caso 4 — RL (Derecha-Izquierda): insertar 10, 30, 20
        System.out.println("\n═══════════════════════════════════════");
        System.out.println(" Caso RL  →  insertar: 10, 30, 20");
        System.out.println("═══════════════════════════════════════");
        ArbolAVL avl4 = new ArbolAVL();
        for (int v : new int[]{10, 30, 20}) avl4.insertar(v);
        System.out.print("InOrden : ");
        avl4.inOrden();
        avl4.imprimirArbol();
    }
}
