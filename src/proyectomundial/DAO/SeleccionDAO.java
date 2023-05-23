/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectomundial.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import proyectomundial.model.Seleccion;
import proyectomundial.util.BasedeDatos;
import static proyectomundial.util.BasedeDatos.ejecutarSQL;

/**
 *
 * @author miguelropero
 */
public class SeleccionDAO {

    public SeleccionDAO() {
        BasedeDatos.conectar();
    }
    
    public boolean registrarSeleccion(Seleccion seleccion) {
        
        String sql = "INSERT INTO w_garcia2.seleccion (nombre, continente, dt, nacionalidad) values("
                + "'" + seleccion.getNombre() + "', " 
                + "'" + seleccion.getContinente() + "', " 
                + "'" + seleccion.getDt() + "', " 
                + "'" + seleccion.getNacionalidad() + "')";
        
        //BasedeDatos.conectar();
        boolean registro = BasedeDatos.ejecutarActualizacionSQL(sql);
        //BasedeDatos.desconectar();
        return registro;
    }
    
    public List<Seleccion> getSelecciones() {
        
        String sql = "SELECT nombre, continente, dt, nacionalidad FROM w_garcia2.seleccion";
        List<Seleccion> selecciones = new ArrayList<Seleccion>();
        
        try {
            ResultSet result = BasedeDatos.ejecutarSQL(sql);
            
            if(result != null) {
            
                while (result.next()) { 
                    Seleccion seleccion = new Seleccion(result.getString("nombre"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                    selecciones.add(seleccion);
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error consultando selecciones");
        }
        
        return selecciones;
    }
    
    
    public String[][] getSeleccionesMatriz() {
        
        String[][] matrizSelecciones = null;
        List<Seleccion> selecciones = getSelecciones();
        
        
        if(selecciones != null) {
            
        
            matrizSelecciones = new String[selecciones.size()][4];

            int x = 0;
            for (Seleccion seleccion : selecciones) {

                matrizSelecciones[x][0] = seleccion.getNombre();
                matrizSelecciones[x][1] = seleccion.getContinente();
                matrizSelecciones[x][2] = seleccion.getDt();
                matrizSelecciones[x][3] = seleccion.getNacionalidad();
                x++;
            }
        }
        
        return matrizSelecciones;
    }
public List<Seleccion> buscarSeleccion(String valor) {
    String sql = "SELECT nombre, continente, dt, nacionalidad FROM w_garcia2.seleccion WHERE  nombre = "+"'" + valor + "'";
    List<Seleccion> selecciones = new ArrayList<Seleccion>();

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null) {

            while (result.next()) {
                Seleccion seleccion = new Seleccion(result.getString("nombre"), result.getString("continente"), result.getString("dt"), result.getString("nacionalidad"));
                selecciones.add(seleccion);
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error consultando selecciones");
    }

    return selecciones;
}
public String[][] getSeleccionesMatriz(String valor) {
        
        String[][] matrizSelecciones = null;
        List<Seleccion> selecciones = buscarSeleccion(valor);
        
        
        if(selecciones != null) {
            
        
            matrizSelecciones = new String[selecciones.size()][4];

            int x = 0;
            for (Seleccion seleccion : selecciones) {

                matrizSelecciones[x][0] = seleccion.getNombre();
                matrizSelecciones[x][1] = seleccion.getContinente();
                matrizSelecciones[x][2] = seleccion.getDt();
                matrizSelecciones[x][3] = seleccion.getNacionalidad();
                x++;
            }
        }
        
        return matrizSelecciones;
    }
public int getTotalSeleccionesCargadas() {
    String sql = "SELECT COUNT(*) AS total FROM w_garcia2.seleccion";
    int total = 0;

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            total = result.getInt(1);
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo total de selecciones");
    }

    return total;
}
public Map<String, Integer> getNumeroSeleccionesPorContinente() {
    String sql = "SELECT continente, COUNT(*) AS total FROM w_garcia2.seleccion GROUP BY continente";
    Map<String, Integer> numeroSeleccionesPorContinente = new HashMap<String, Integer>();

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null) {
            while (result.next()) {
                String continente = result.getString("continente");
                int total = result.getInt("total");
                numeroSeleccionesPorContinente.put(continente, total);
                System.out.println("");
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo número de selecciones por continente");
    }

    return numeroSeleccionesPorContinente;
}
public String[][] getNumeroSeleccionesPorContinenteMatriz() {
    Map<String, Integer> numeroSeleccionesPorContinente = getNumeroSeleccionesPorContinente();
    String[][] matrizSelecciones = null;
 
    if(numeroSeleccionesPorContinente != null) {
        matrizSelecciones = new String[numeroSeleccionesPorContinente.size()][2];
        
        int x = 0;
        for (String continente : numeroSeleccionesPorContinente.keySet()) {
            matrizSelecciones[x][0] = continente;
            matrizSelecciones[x][1] = String.valueOf(numeroSeleccionesPorContinente.get(continente));
            x++;
        }
    }
    
    return matrizSelecciones;
}

public int getCantidadNacionalidadesDirectoresTecnicos() {
    String sql = "SELECT COUNT(DISTINCT nacionalidad) AS cantidad FROM w_garcia2.seleccion WHERE dt IS NOT NULL";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            return result.getInt("cantidad");
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo cantidad de nacionalidades de los directores técnicos");
    }

    return 0;
}
public Map<String, Integer> getRankingNacionalidadesDirectoresTecnicos() {
    Map<String, Integer> ranking = new HashMap<>();
    String sql = "SELECT nacionalidad, COUNT(*) as cantidad FROM w_garcia2.seleccion WHERE dt IS NOT NULL GROUP BY nacionalidad ORDER BY cantidad DESC";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        while(result != null && result.next()) {
            ranking.put(result.getString("nacionalidad"), result.getInt("cantidad"));
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo ranking de nacionalidades de directores técnicos");
    }

    return ranking;
}
public int getTotalResultadosCargadas() {
    String sql = "SELECT COUNT(*) AS total FROM w_garcia2.resultado";
    int total = 0;

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            total = result.getInt(1);
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo total de resultados");
    }

    return total;
}
public double getPromedioGolesPorPartido() {
    String sql = "SELECT AVG(goles_local+goles_visitante) FROM w_garcia2.resultado";
    double promedioGoles = 0;

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            promedioGoles = result.getDouble(1);
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo promedio de goles por partido");
    }

    return promedioGoles;
}
 public String getPartidoConMasGoles() {
   String local="local";
     String sql = "SELECT "+local+" , visitante, (goles_local + goles_visitante) as total_goles FROM w_garcia2.resultado ORDER BY total_goles DESC LIMIT 1";
    String partidoConMasGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            String equipoLocal = result.getString("local");
            String equipoVisitante = result.getString("visitante");
            int totalGoles = result.getInt("total_goles");
            partidoConMasGoles = equipoLocal + " vs " + equipoVisitante + " (" + totalGoles + " goles)";
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo partido con más goles");
    }

    return partidoConMasGoles;
}
   public String getPartidoConMenosGoles() {
   String local="local";
     String sql = "SELECT "+local+" , visitante, (goles_local + goles_visitante) as total_goles FROM w_garcia2.resultado ORDER BY total_goles ASC LIMIT 1";
    String partidoConMasGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            String equipoLocal = result.getString("local");
            String equipoVisitante = result.getString("visitante");
            int totalGoles = result.getInt("total_goles");
            partidoConMasGoles = equipoLocal + " vs " + equipoVisitante + " (" + totalGoles + " goles)";
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo partido con más goles");
    }

    return partidoConMasGoles;
}
   public int getNumeroPartidosConGanador() {
    String sql = "SELECT COUNT(*) as numero_partidos FROM w_garcia2.resultado WHERE goles_local != goles_visitante";
    int numeroPartidosConGanador = 0;

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            numeroPartidosConGanador = result.getInt("numero_partidos");
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo número de partidos con ganador");
    }

    return numeroPartidosConGanador;
}
public int getNumeroPartidosConEmpatados() {
    String sql = "SELECT COUNT(*) as numero_partidos FROM w_garcia2.resultado WHERE goles_local = goles_visitante";
    int numeroPartidosConGanador = 0;

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if(result != null && result.next()) {
            numeroPartidosConGanador = result.getInt("numero_partidos");
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo número de partidos con empates");
    }

    return numeroPartidosConGanador;
}

public String getEquipoConMasGoles() {
    String sql = "SELECT local, SUM(goles_local) as goles_local, visitante, SUM(goles_visitante) as goles_visitante FROM w_garcia2.resultado GROUP BY local, visitante";
    String equipoConMasGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null) {
            int maxGoles = 0;
            
            while (result.next()) {
                int golesLocal = result.getInt("goles_local");
                int golesVisitante = result.getInt("goles_visitante");
                String equipoLocal = result.getString("local");
                String equipoVisitante = result.getString("visitante");

                if (golesLocal > maxGoles) {
                    maxGoles = golesLocal;
                    equipoConMasGoles = equipoLocal;
                }

                if (golesVisitante > maxGoles) {
                    maxGoles = golesVisitante;
                    equipoConMasGoles = equipoVisitante;
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo equipo con más goles");
    }

    return equipoConMasGoles;
}
public String getEquipoConMenosGoles() {
    String sql = "SELECT local, SUM(goles_local) as goles_local, visitante, SUM(goles_visitante) as goles_visitante FROM w_garcia2.resultado GROUP BY local, visitante";
    String equipoConMenosGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null) {
            int minGoles = Integer.MAX_VALUE;
            
            while (result.next()) {
                int golesLocal = result.getInt("goles_local");
                int golesVisitante = result.getInt("goles_visitante");
                String equipoLocal = result.getString("local");
                String equipoVisitante = result.getString("visitante");

                if (golesLocal < minGoles) {
                    minGoles = golesLocal;
                    equipoConMenosGoles = equipoLocal;
                }

                if (golesVisitante < minGoles) {
                    minGoles = golesVisitante;
                    equipoConMenosGoles = equipoVisitante;
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo equipo con menos goles");
    }

    return equipoConMenosGoles;
}
public String getEquipoConMasPuntos() {
    String sql = "SELECT local, SUM(CASE WHEN goles_local > goles_visitante THEN 3 WHEN goles_local = goles_visitante THEN 1 ELSE 0 END) as puntos_local, visitante, SUM(CASE WHEN goles_visitante > goles_local THEN 3 WHEN goles_local = goles_visitante THEN 1 ELSE 0 END) as puntos_visitante FROM w_garcia2.resultado GROUP BY local, visitante";
    String equipoConMasPuntos = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null) {
            int maxPuntos = Integer.MIN_VALUE;
            
            while (result.next()) {
                int puntosLocal = result.getInt("puntos_local");
                int puntosVisitante = result.getInt("puntos_visitante");
                String equipoLocal = result.getString("local");
                String equipoVisitante = result.getString("visitante");

                int puntosEquipo = puntosLocal + puntosVisitante;

                if (puntosEquipo > maxPuntos) {
                    maxPuntos = puntosEquipo;
                    equipoConMasPuntos = (puntosLocal > puntosVisitante) ? equipoLocal : equipoVisitante;
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo equipo con más puntos");
    }

    return equipoConMasPuntos;
}
public String getEquipoConMenosPuntos() {
    String sql = "SELECT local, SUM(CASE WHEN goles_local > goles_visitante THEN 3 WHEN goles_local = goles_visitante THEN 1 ELSE 0 END) as puntos_local, visitante, SUM(CASE WHEN goles_visitante > goles_local THEN 3 WHEN goles_local = goles_visitante THEN 1 ELSE 0 END) as puntos_visitante FROM w_garcia2.resultado GROUP BY local, visitante";
    String equipoConMenosPuntos = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null) {
            int minPuntos = Integer.MAX_VALUE;
            
            while (result.next()) {
                int puntosLocal = result.getInt("puntos_local");
                int puntosVisitante = result.getInt("puntos_visitante");
                String equipoLocal = result.getString("local");
                String equipoVisitante = result.getString("visitante");

                int puntosEquipo = puntosLocal + puntosVisitante;

                if (puntosEquipo < minPuntos) {
                    minPuntos = puntosEquipo;
                    equipoConMenosPuntos = (puntosLocal < puntosVisitante) ? equipoLocal : equipoVisitante;
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo equipo con menos puntos");
    }

    return equipoConMenosPuntos;
}

public String getContinenteConMasGoles() {
    String sql = "SELECT continente_local, continente_visitante, SUM(goles_local),  SUM(goles_visitante), SUM(goles_local) + SUM(goles_visitante) as total_goles FROM w_garcia2.resultado GROUP BY continente_local, continente_visitante ORDER BY total_goles DESC LIMIT 1";
    String continenteConMasGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null && result.next()) {
            String continenteLocal = result.getString("continente_local");
            String continenteVisitante = result.getString("continente_visitante");
            if ((continenteLocal != null && !continenteLocal.isEmpty()) && (continenteVisitante != null && !continenteVisitante.isEmpty())) {
                // Si ambos continentes son diferentes, seleccionamos el continente con más goles
                if (!continenteLocal.equals(continenteVisitante)) {
                    // Seleccionamos el continente con más goles de los dos
                    int golesLocal = result.getInt(3);
                    int golesVisitante = result.getInt(4);
                    continenteConMasGoles = (golesLocal > golesVisitante) ? continenteLocal : continenteVisitante;
                } else {
                    // Si ambos continentes son iguales, simplemente seleccionamos uno de ellos
                    continenteConMasGoles = continenteLocal;
                }
            } else if (continenteLocal != null && !continenteLocal.isEmpty()) {
                // Si solo uno de los continentes es válido, seleccionamos ese
                continenteConMasGoles = continenteLocal;
            } else if (continenteVisitante != null && !continenteVisitante.isEmpty()) {
                continenteConMasGoles = continenteVisitante;
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo continente con más goles");
    }

    return continenteConMasGoles;
}
public String getContinenteConMenosGoles() {
    String sql = "SELECT continente_local, continente_visitante, SUM(goles_local),  SUM(goles_visitante), SUM(goles_local) + SUM(goles_visitante) as total_goles FROM w_garcia2.resultado GROUP BY continente_local, continente_visitante ORDER BY total_goles ASC LIMIT 1";
    String continenteConMenosGoles = "";

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null && result.next()) {
            String continenteLocal = result.getString("continente_local");
            String continenteVisitante = result.getString("continente_visitante");
            if ((continenteLocal != null && !continenteLocal.isEmpty()) && (continenteVisitante != null && !continenteVisitante.isEmpty())) {
                // Si ambos continentes son diferentes, seleccionamos el continente con menos goles
                if (!continenteLocal.equals(continenteVisitante)) {
                    // Seleccionamos el continente con menos goles de los dos
                    int golesLocal = result.getInt(3);
                    int golesVisitante = result.getInt(4);
                    continenteConMenosGoles = (golesLocal < golesVisitante) ? continenteLocal : continenteVisitante;
                } else {
                    // Si ambos continentes son iguales, simplemente seleccionamos uno de ellos
                    continenteConMenosGoles = continenteLocal;
                }
            } else if (continenteLocal != null && !continenteLocal.isEmpty()) {
                // Si solo uno de los continentes es válido, seleccionamos ese
                continenteConMenosGoles = continenteLocal;
            } else if (continenteVisitante != null && !continenteVisitante.isEmpty()) {
                continenteConMenosGoles = continenteVisitante;
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo continente con menos goles");
    }

    return continenteConMenosGoles;
}
public List<String> getEquiposClasificadosPorGrupo() {
    String sql = "SELECT grupo, local, visitante, SUM(CASE WHEN goles_local > goles_visitante THEN 3 WHEN goles_local = goles_visitante THEN 1 ELSE 0 END) as puntos FROM w_garcia2.resultado GROUP BY grupo, local, visitante";

    List<String> equiposClasificados = new ArrayList<>();

    try {
        ResultSet result = BasedeDatos.ejecutarSQL(sql);

        if (result != null) {
            Map<String, Integer> puntosPorEquipo = new HashMap<>();
            Map<String, String> grupoPorEquipo = new HashMap<>();

            while (result.next()) {
                String equipoLocal = result.getString("local");
                String equipoVisitante = result.getString("visitante");
                int puntosLocal = result.getInt("puntos");
                String grupo = result.getString("grupo");

                // Actualizamos los puntos del equipo local
                puntosPorEquipo.put(equipoLocal, puntosPorEquipo.getOrDefault(equipoLocal, 0) + puntosLocal);

                // Si el equipo visitante es diferente al local, actualizamos sus puntos también
                if (!equipoVisitante.equals(equipoLocal)) {
                    int puntosVisitante = 3 - puntosLocal; // Puntos del equipo visitante
                    puntosPorEquipo.put(equipoVisitante, puntosPorEquipo.getOrDefault(equipoVisitante, 0) + puntosVisitante);
                }

                // Guardamos el grupo al que pertenece cada equipo
                grupoPorEquipo.put(equipoLocal, grupo);
                grupoPorEquipo.put(equipoVisitante, grupo);
            }

            // Clasificamos los equipos por puntos
            List<Map.Entry<String, Integer>> equiposOrdenados = new ArrayList<>(puntosPorEquipo.entrySet());
            equiposOrdenados.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Obtenemos los equipos clasificados por grupo (los dos primeros)
            Set<String> gruposClasificados = new HashSet<>();
            for (Map.Entry<String, Integer> entry : equiposOrdenados) {
                String equipo = entry.getKey();
                String grupo = grupoPorEquipo.get(equipo);
                if (!gruposClasificados.contains(grupo)) {
                    equiposClasificados.add(equipo);
                    gruposClasificados.add(grupo);
                }
                if (equiposClasificados.size() == (gruposClasificados.size() * 2)) {
                    break; // Ya hemos clasificado los dos primeros de cada grupo
                }
            }
        }
    } catch (Exception e) {
        System.out.println(e.toString());
        System.out.println("Error obteniendo equipos clasificados por grupo");
    }

    return equiposClasificados;
}








}
