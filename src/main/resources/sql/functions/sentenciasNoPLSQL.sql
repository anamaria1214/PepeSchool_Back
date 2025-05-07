-- Consulta para obtener los examenes de un estudiante
SELECT m.nombre, ex.nombre, e.descripcion, ex.limiteTiempo, ex.fechaPresentacion,
       CASE WHEN ee.puntaje NOT NULL THEN 'Presentado',
       ELSE 'No presentado' AS Estado
       FROM Grupo g ON e.idGrupo=g.id
       LEFT JOIN DetalleGrupoMateria dtgm ON dtgm.idGrupo=g.id
       LEFT JOIN Materia m ON dtgm.idMateria=m.id
       LEFT JOIN Examen ex ON ex.idMateria=m.id
       LEFT JOIN ExamenEstudiante ee ON ee.idExamen=ex.id
       WHERE ee.idEstudiante=&id;