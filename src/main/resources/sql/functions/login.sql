CREATE OR REPLACE FUNCTION fn_login(
    p_username IN VARCHAR2,
    p_password IN VARCHAR2,
    tipo IN CHAR

) RETURN BOOLEAN IS
    flag BOOLEAN;

DECLARE
flag BOOLEAN:=FALSE;
    v_result estudiante%ROWTYPE;
    v_result docente%ROWTYPE;
BEGIN

IF tipo=1 THEN
SELECT
    'ESTUDIANTE' AS tipo_usuario,
    id,
    nombre,
    nombreUsuario AS username
FROM
    estudiante
WHERE
        nombreUsuario = p_username
  AND contrasena = p_password;

flag:= TRUE;

ELSE

SELECT
    'DOCENTE' AS tipo_usuario,
    id,
    nombre,
    nombreUsuario AS username
FROM
    docente
WHERE
        nombreUsuario = p_username
  AND contrasena = p_password;

flag:= TRUE;
END IF;

RETURN flag;

EXCEPTION
 WHEN NO_DATA_FOUND THEN
    flag := FALSE;
 WHEN TOO_MANY_ROWS THEN
    flag := FALSE;
END;

END fn_login;
/