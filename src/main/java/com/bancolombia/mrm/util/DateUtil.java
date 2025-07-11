// =================================================================================
// PAQUETE: com.bancolombia.mrm.util
// DESCRIPCIÓN: Clases de utilidad reutilizables.
// =================================================================================
package com.bancolombia.mrm.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static long diasEntre(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }
}
