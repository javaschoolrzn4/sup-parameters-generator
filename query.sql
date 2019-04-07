CREATE TABLE path (
	id_path number(10) NOT NULL,
	id_subsystem number(10) NOT NULL,
	id_chanel number(10) NOT NULL,
	id_slice number(10) NOT NULL,
	paramValue varchar(250),
	valueType varchar(250),
	description varchar(250),
	CONSTRAINT path_pk PRIMARY KEY (id_path),
	CONSTRAINT fk_param
		FOREIGN KEY (id_path)
		REFERENCES path(id_path)
	)
	
	
CREATE TABLE subsystem (
	id_subsystem number(10) NOT NULL,
	name varchar(250),
	description varchar(250),
	CONSTRAINT subsystem_pk PRIMARY KEY (id_subsystem)
	)
	
CREATE TABLE chanel (
	id_chanel number(10) NOT NULL,
	name varchar(250),
	description varchar(250),
	CONSTRAINT chanel_pk PRIMARY KEY (id_chanel)
	)
	
CREATE TABLE param (
	id_param number(10) NOT NULL,
	id_path number(10) NOT NULL,
	name varchar(250),
	isList number(1),
	description varchar(250),
	CONSTRAINT param_pk PRIMARY KEY (id_param),
	CONSTRAINT fk_param
		FOREIGN KEY (id_path)
		REFERENCES path(id_path)
	)
	
