drop table if exists persona;

create table persona (
	id_persona identity primary key,
	nombre varchar(45),
	apellido varchar(45)
);

create table usuario (
	id_usuario identity primary key,
	id_persona integer,
	username varchar(45) unique,
	password varchar(45)
/* la quitamos : FOREIGN KEY(id_persona) REFERENCES persona(id_persona) y ponemos el alter table de abajo */
);

ALTER TABLE usuario
ADD FOREIGN KEY (id_persona)
REFERENCES persona(id_persona)
ON DELETE CASCADE;




