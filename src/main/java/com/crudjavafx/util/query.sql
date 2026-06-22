Drop table pacientes
create table pacientes(
	cedula Varchar(10) primary key,
	nombres varchar(100) not null,
	apellidos varchar(100) not null,
	fecha_nacimiento date not null,
	genero varchar(50) not null,
	email varchar(150) unique not null,
	tipo_sangre varchar(10) not null,
	alergias text,
	activo boolean default true
);

-- Insertar datos de prueba en la tabla pacientes
INSERT INTO pacientes (cedula, nombres, apellidos, fecha_nacimiento, genero, email, tipo_sangre, alergias, activo) 
VALUES
('1712345678', 'Juan Carlos', 'Pérez Gómez', '1985-04-12', 'Masculino', 'juan.perez@email.com', 'O+', 'Ninguna', true),
('1723456789', 'María Fernanda', 'López Silva', '1992-08-25', 'Femenino', 'maria.lopez@email.com', 'A+', 'Penicilina', true),
('1734567890', 'Luis Miguel', 'Andrade Ríos', '1978-11-05', 'Masculino', 'luis.andrade@email.com', 'B-', 'Polvo y polen', true),
('1745678901', 'Ana Lucía', 'Torres Vega', '2001-02-14', 'Femenino', 'ana.torres@email.com', 'O-', 'Aspirina', true),
('1756789012', 'Carlos Eduardo', 'Mendoza Ruiz', '1995-09-30', 'Masculino','edu@gmail.com' , 'AB+', 'Mariscos', false);