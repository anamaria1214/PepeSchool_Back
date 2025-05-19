create or replace FUNCTION fn_login(
p_username IN VARCHAR2,
p_password IN VARCHAR2,
tipo IN NUMBER
)RETURN INTEGER IS
flag INTEGER := 0;
v_id estudiante.id%type;

BEGIN

IF tipo=1 THEN

SELECT id
INTO v_id
FROM estudiante
WHERE nombreusuario = p_username AND contrasena = p_password;

flag := v_id;

ELSE
SELECT id
INTO v_id
FROM docente
WHERE nombreusuario = p_username AND contrasena = p_password;

flag := v_id;

END IF;

RETURN flag;

EXCEPTION
WHEN NO_DATA_FOUND THEN
flag := 0;
RETURN flag;

WHEN TOO_MANY_ROWS THEN
flag := 0;
RETURN flag;

END fn_login;