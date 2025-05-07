CREATE OR REPLACE FUNCTION fn_asignar_preguntas_aleatorias(
    p_examen_id IN NUMBER,
    p_estudiante_id IN NUMBER,
    p_num_preguntas IN NUMBER

) RETURN NUMBER IS
    v_examen_estudiante_id NUMBER;
    v_count_preguntas NUMBER;
    v_count_preguntas_asignadas NUMBER := 0;

CURSOR c_preguntas_aleatorias IS
SELECT pregunta_id
FROM detalleexamenpregunta
WHERE examen_id = p_examen_id
ORDER BY DBMS_RANDOM.VALUE;

BEGIN

SELECT COUNT(*) INTO v_count_preguntas
FROM examen
WHERE id = p_examen_id;

IF v_count_preguntas = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'El examen especificado no existe');
END IF;

SELECT COUNT(*) INTO v_count_preguntas
FROM detalleexamenpregunta
WHERE examen_id = p_examen_id;

IF v_count_preguntas < p_num_preguntas THEN
        RAISE_APPLICATION_ERROR(-20002, 'No hay suficientes preguntas en el examen general');
END IF;

BEGIN
SELECT id INTO v_examen_estudiante_id
FROM examenestudiante
WHERE examen_id = p_examen_id
  AND estudiante_id = p_estudiante_id;
EXCEPTION
        WHEN NO_DATA_FOUND THEN
            INSERT INTO examenestudiante (
                id,
                fechapresentacion,
                puntaje,
                duracion,
                ipaddress,
                estudiante_id,
                examen_id
            ) VALUES (
                         seq_examen_estudiante.NEXTVAL,
                         NULL,
                         NULL,
                         NULL,
                         NULL,
                         p_estudiante_id,
                         p_examen_id
                     )
            RETURNING id INTO v_examen_estudiante_id;
END;

FOR r_preg IN c_preguntas_aleatorias LOOP
            EXIT WHEN v_count_preguntas_asignadas >= p_num_preguntas;

INSERT INTO preguntaexamen (
    id,
    pregunta_id,
    examenestudiante_id
) VALUES (
             seq_pregunta_examen.NEXTVAL,
             r_preg.pregunta_id,
             v_examen_estudiante_id
         );

v_count_preguntas_asignadas := v_count_preguntas_asignadas + 1;
END LOOP;

RETURN v_count_preguntas_asignadas;  -- Preguntar si es necesario que retorne la cantidad de preguntas

END fn_asignar_preguntas_aleatorias;
/