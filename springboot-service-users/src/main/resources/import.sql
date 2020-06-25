INSERT INTO users (username, password, email, enabled, name, last_name) VALUES ('cuadrac', '$2a$10$fDkekXKkv.35.o7.FD2x6O0PGU48WNqO0z/wNahy7IU4R2Bvnp1qy', 'correo@correo.com', 1, 'Ricardo', 'Cuadra');
INSERT INTO users (username, password, email, enabled, name, last_name) VALUES ('milic2', '$2a$10$EFFLT/W/EAB8PG0MQT1IHuqjUHoLCSKaOm5.kK2/uMWvaB4UJGN5W', 'juan@correo.com', 1, 'Juan', 'Perez');

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT INTO users_roles(user_id, role_id) VALUES(1,1);
INSERT INTO users_roles(user_id, role_id) VALUES(2,2);
INSERT INTO users_roles(user_id, role_id) VALUES(2,1);