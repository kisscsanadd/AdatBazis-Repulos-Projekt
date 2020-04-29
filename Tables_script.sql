DROP TABLE felhasznalo;
DROP TABLE fizetesimod;
DROP TABLE szalloda;
DROP TABLE jaratfigyrel;
DROP TABLE jegy;
DROP TABLE foglalas;
DROP TABLE kategoria;
DROP TABLE repulogep;
DROP TABLE repuloter;
DROP TABLE utazasiosztaly;
DROP TABLE varos;
DROP TABLE orszag;
DROP TABLE jarat;
DROP TABLE figyelmeztetes;

DROP SEQUENCE UtazasiOsztaly_sequence;
DROP SEQUENCE Foglalas_sequence;
DROP SEQUENCE Jegy_sequence;
DROP SEQUENCE Kategoria_sequence;
DROP SEQUENCE FizetesiMod_sequence;
DROP SEQUENCE Felhasznalo_sequence;
DROP SEQUENCE Jarat_sequence;
DROP SEQUENCE JaratFigyRel_sequence;
DROP SEQUENCE Repulogep_sequence;
DROP SEQUENCE Repuloter_sequence;
DROP SEQUENCE Varos_sequence;
DROP SEQUENCE Orszag_sequence;
DROP SEQUENCE Figyelmeztetes_sequence;
DROP SEQUENCE szalloda_sequence;

ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY-MM-DD HH24:MI';

CREATE TABLE UtazasiOsztaly 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30),
  CONSTRAINT UtazasiOsztaly_PRIMARY_KEY PRIMARY KEY (id));

create sequence UtazasiOsztaly_sequence;

create or replace trigger UtazasiOsztaly_trigger
    before insert on UtazasiOsztaly
    for each row
    begin
      select UtazasiOsztaly_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into UtazasiOsztaly(nev) values('Turista');
insert into UtazasiOsztaly(nev) values('Business');
insert into UtazasiOsztaly(nev) values('First');



CREATE TABLE FizetesiMod 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30),
  CONSTRAINT FizetesiMod_PRIMARY_KEY PRIMARY KEY (id));

create sequence FizetesiMod_sequence;

create or replace trigger FizetesiMod_trigger
    before insert on FizetesiMod
    for each row
    begin
      select FizetesiMod_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into FizetesiMod(nev) values('Hitelkártya');
insert into FizetesiMod(nev) values('Banki átutlás');
insert into FizetesiMod(nev) values('Készpénz');



CREATE TABLE Felhasznalo 
    (id              	NUMBER(10) NOT NULL,
     felh_nev           VARCHAR2(30) NOT NULL,
	 jelszo				VARCHAR2(20) NOT NULL,
	 isAdmin			NUMBER(1,0) DEFAULT 0,
	 email				VARCHAR2(50) NOT NULL,
  CONSTRAINT Felhasznalo_PRIMARY_KEY PRIMARY KEY (id));

create sequence Felhasznalo_sequence;

create or replace trigger Felhasznalo_trigger
    before insert on Felhasznalo
    for each row
    begin
      select Felhasznalo_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('admin', 'admin', 1, 'admin@admin.hu');
insert into Felhasznalo(felh_nev,jelszo,isAdmin, email) values('user', 'userr', 0, 'user@user.hu');



CREATE TABLE Kategoria 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30) NOT NULL,
     kedvezmeny         NUMBER(3) NOT NULL,
  CONSTRAINT Kategoria_PRIMARY_KEY PRIMARY KEY (id));

create sequence Kategoria_sequence;

create or replace trigger Kategoria_trigger
    before insert on Kategoria
    for each row
    begin
      select Kategoria_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Kategoria(nev, kedvezmeny) values('Gyerek', 15);
insert into Kategoria(nev, kedvezmeny) values('Diák', 20);
insert into Kategoria(nev, kedvezmeny) values('Nyugdíjas', 20);
insert into Kategoria(nev, kedvezmeny) values('Mozgáskorlátozott', 50);
insert into Kategoria(nev, kedvezmeny) values('Felnőtt', 0);
	
	

CREATE TABLE Figyelmeztetes 
    (id           NUMBER(10) NOT NULL,
     uzenet       VARCHAR2(100) NOT NULL,
  CONSTRAINT Figyelmeztetes_PRIMARY_KEY PRIMARY KEY (id));

create sequence Figyelmeztetes_sequence;

create or replace trigger Figyelmeztetes_trigger
    before insert on Figyelmeztetes
    for each row
    begin
      select Figyelmeztetes_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Figyelmeztetes(uzenet) values('A járat 30 percet késik');



CREATE TABLE Orszag 
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(50) NOT NULL,
  CONSTRAINT Orszag_PRIMARY_KEY PRIMARY KEY (id));

create sequence Orszag_sequence;

create or replace trigger Orszag_trigger
    before insert on Orszag
    for each row
    begin
      select Orszag_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Orszag(nev) values('Magyarország');
insert into Orszag(nev) values('Németország');
insert into Orszag(nev) values('Nagy-Britannia');
insert into Orszag(nev) values('Olaszország');
insert into Orszag(nev) values('Hollandia');
insert into Orszag(nev) values('Belgium');
insert into Orszag(nev) values('Spanyolország');
insert into Orszag(nev) values('Portugália');
insert into Orszag(nev) values('Svédország');
insert into Orszag(nev) values('Franciaország');
insert into Orszag(nev) values('Finnország');



CREATE TABLE Varos 
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(20) NOT NULL,
     orszag_id NUMBER(10) NOT NULL,
  CONSTRAINT Varos_FOREIGN_KEY FOREIGN KEY (orszag_id) REFERENCES Orszag (id),
  CONSTRAINT Varos_PRIMARY_KEY PRIMARY KEY (id));

create sequence Varos_sequence;

create or replace trigger Varos_trigger
    before insert on Varos
    for each row
    begin
      select Varos_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Varos(nev, orszag_id) values('Budapest', 1);
insert into Varos(nev, orszag_id) values('Berlin', 2);
insert into Varos(nev, orszag_id) values('London', 3);
insert into Varos(nev, orszag_id) values('Róma', 4);
insert into Varos(nev, orszag_id) values('Amdszterdam', 5);
insert into Varos(nev, orszag_id) values('Brüsszel', 6);
insert into Varos(nev, orszag_id) values('Madrid', 7);
insert into Varos(nev, orszag_id) values('Lisszabon', 8);
insert into Varos(nev, orszag_id) values('Stockholm', 9);
insert into Varos(nev, orszag_id) values('Párizs', 10);
insert into Varos(nev, orszag_id) values('Helsinki', 11);
insert into Varos(nev, orszag_id) values('Frankfurt', 2);
insert into Varos(nev, orszag_id) values('Köln', 2);
insert into Varos(nev, orszag_id) values('Milánó', 4);
insert into Varos(nev, orszag_id) values('Velence', 4);
insert into Varos(nev, orszag_id) values('Bilbaó', 7);
insert into Varos(nev, orszag_id) values('Nizza', 10);



CREATE TABLE Repuloter
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(50) NOT NULL,
     szelesseg      FLOAT NOT NULL,
     hosszusag      FLOAT NOT NULL,
     varos_id  NUMBER(10) NOT NULL,
  CONSTRAINT Repuloter_FOREIGN_KEY FOREIGN KEY (varos_id) REFERENCES Varos (id),
  CONSTRAINT Repuloter_PRIMARY_KEY PRIMARY KEY (id));

create sequence Repuloter_sequence;

create or replace trigger Repuloter_trigger
    before insert on Repuloter
    for each row
    begin
      select Repuloter_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Budapest Liszt Ferenc nemzetközi', 47.44, 19.26, 1);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Berlin-Schönefeld', 52.37, 13.52, 2);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('London-Gatwick', 51.14, -0.16, 3);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Róma-Fiumicino', 41.8, 12.24, 4);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Amszterdam-Schiphol', 52.31, 4.67, 5);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Brüsszeli', 50.9, 4.48, 6);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Madrid-Barajasi', 40.49, -3.57, 7);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Lisszaboni', 38.77, -9.13, 8);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Stockholm-Arlanda', 59.65, 17.92, 9);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Párizs–Orly', 48.73, 2.37, 10);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Helsinki–Vantaai', 60.32, 24.96, 11);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Frankfurti', 50.04, 8.56, 12);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Köln–Bonn', 50.86, 7.14, 13);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Milánó-Linatei', 45.44, 9.27, 14);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Velence Marco Polo', 45.5, 12.35, 15);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Bilbaói', 43.3, -2.91, 16);
insert into Repuloter(nev, szelesseg, hosszusag, varos_id) values('Nizza Côte d’Azur', 43.66, 7.21, 17);



CREATE TABLE Repulogep
    (id        NUMBER(10) NOT NULL,
     nev       VARCHAR2(20) NOT NULL,
     sebesseg  NUMBER(4) NOT NULL,
     ferohely  NUMBER(3) NOT NULL,
  CONSTRAINT Repulogep_PRIMARY_KEY PRIMARY KEY (id));

create sequence Repulogep_sequence;

create or replace trigger Repulogep_trigger
    before insert on Repulogep
    for each row
    begin
      select Repulogep_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into repulogep(nev, sebesseg, ferohely) values ('Airbus A300', 913, 320);
insert into repulogep(nev, sebesseg, ferohely) values ('Air Force One', 1015, 420);
insert into repulogep(nev, sebesseg, ferohely) values ('Airbus A350 XWB', 975, 150);
insert into repulogep(nev, sebesseg, ferohely) values ('ANT–20', 220, 40);
insert into repulogep(nev, sebesseg, ferohely) values ('Boeing 720', 1009, 425);



CREATE TABLE Jarat
    (id        				NUMBER(10) NOT NULL,
     felszallas_datum       DATE NOT NULL,
     repuloter_id_fel		NUMBER(10) NOT NULL,
     repuloter_id_le		NUMBER(10) NOT NULL,
     repulogep_id			NUMBER(10) NOT NULL,
     nepszeruseg            NUMBER(5) DEFAULT 0,
     szabad_helyek			NUMBER(3) NOT NULL,
  CONSTRAINT Jarat_PRIMARY_KEY PRIMARY KEY (id));

create sequence Jarat_sequence;

create or replace trigger Jarat_trigger
    before insert on Jarat
    for each row
    begin
      select Jarat_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('1998-12-25 17:30' ,1 ,2 ,1 ,320);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2014-08-18 14:30' ,3 ,1 ,4 ,40);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-06-11 11:00' ,1 ,2 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-14 08:00' ,1 ,2 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-19 10:00' ,1 ,2 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-24 19:00' ,1 ,2 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-10 18:00' ,1 ,2 ,1 ,320);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-12 20:00' ,1 ,3 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-30 12:30' ,1 ,4 ,3 ,150);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-25 20:30' ,1 ,5 ,4 ,40);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-21 09:00' ,1 ,9 ,3 ,150);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-17 10:30' ,1 ,3 ,5 ,425);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-29 11:15' ,1 ,5 ,5 ,425);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-01 22:45' ,1 ,6 ,2 ,420);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-06 06:15' ,1 ,7 ,1 ,320);
insert into Jarat(felszallas_datum, repuloter_id_fel, repuloter_id_le, repulogep_id, szabad_helyek) values('2020-04-09 14:30' ,1 ,8 ,3 ,150);


CREATE TABLE Foglalas
    (id        				NUMBER(10) NOT NULL,
     felh_id		        NUMBER(10) NOT NULL,
     jarat_id				NUMBER(10) NOT NULL,
     fizetesi_mod_id		NUMBER(10) NOT NULL,
  CONSTRAINT Foglalas_PRIMARY_KEY PRIMARY KEY (id));

create sequence Foglalas_sequence;

create or replace trigger Foglalas_trigger
    before insert on Foglalas
    for each row
    begin
      select Foglalas_sequence.nextval
         into:new.id
         from dual;
    end;
	/



CREATE TABLE Jegy 
    (id              	NUMBER(10) NOT NULL,
     kategoria_id       NUMBER(5),
	 utazasi_osztaly_id	NUMBER(10),
	 foglalasi_id		NUMBER(10),
  CONSTRAINT Jegy_FOREIGN_KEY FOREIGN KEY (utazasi_osztaly_id) REFERENCES UtazasiOsztaly (id),
  CONSTRAINT Jegy_FOREIGN_KEY_2 FOREIGN KEY (kategoria_id) REFERENCES Kategoria (id),
  CONSTRAINT Jegy_FOREIGN_KEY_3 FOREIGN KEY (foglalasi_id) REFERENCES Foglalas (id),
  CONSTRAINT Jegy_PRIMARY_KEY PRIMARY KEY (id) );

create sequence Jegy_sequence;

create or replace trigger Jegy_trigger
    before insert on Jegy
    for each row
    begin
      select Jegy_sequence.nextval
         into:new.id
         from dual;
    end;
	/
    


CREATE TABLE JaratFigyRel
    (id              	NUMBER(10) NOT NULL,
     jarat_id       	NUMBER(10) NOT NULL,
	 figyelmeztetes_id	NUMBER(10) NOT NULL,
  CONSTRAINT JaratFigyRel_FOREIGN_KEY FOREIGN KEY (jarat_id) REFERENCES Jarat (id),
  CONSTRAINT JaratFigyRel_FOREIGN_KEY_2 FOREIGN KEY (figyelmeztetes_id) REFERENCES Figyelmeztetes (id),
  CONSTRAINT JaratFigyRel_PRIMARY_KEY PRIMARY KEY (id) );

create sequence JaratFigyRel_sequence;

create or replace trigger JaratFigyRel_trigger
    before insert on JaratFigyRel
    for each row
    begin
      select JaratFigyRel_sequence.nextval
         into:new.id
         from dual;
    end;
	/
	

CREATE TABLE Szalloda 
    (id              	NUMBER(10) NOT NULL,
     nev                VARCHAR2(30) NOT NULL,
     csillagok_szama    NUMBER(1) NOT NULL,
     varos_id           NUMBER(10) NOT NULL,
  CONSTRAINT Szalloda_FOREIGN_KEY FOREIGN KEY (varos_id) REFERENCES Varos (id),
  CONSTRAINT Szalloda_PRIMARY_KEY PRIMARY KEY (id));

create sequence Szalloda_sequence;

create or replace trigger Szalloda_trigger
    before insert on Szalloda
    for each row
    begin
      select Szalloda_sequence.nextval
         into:new.id
         from dual;
    end;
	/
insert into Szalloda(nev, csillagok_szama, varos_id) values('Danubius Hotel Helia', 4, 4);
insert into Szalloda(nev, csillagok_szama, varos_id) values('Hotel Canada', 3, 2);
insert into Szalloda(nev, csillagok_szama, varos_id) values('The Savoy', 5, 3);



CREATE OR REPLACE TRIGGER jarat_szabadhely_csokkentes
AFTER INSERT
ON jegy
FOR EACH ROW
BEGIN
    UPDATE jarat SET szabad_helyek = szabad_helyek - 1 WHERE id = (SELECT jarat_id FROM FOGLALAS WHERE id = :NEW.foglalasi_id);
END;
/

CREATE OR REPLACE TRIGGER szabad_helyek_valtozasa
AFTER UPDATE OF ferohely
ON repulogep
FOR EACH ROW
BEGIN
    UPDATE jarat akt SET szabad_helyek = :NEW.ferohely WHERE repulogep_id = :NEW.id AND (SELECT COUNT(*) FROM FOGLALAS WHERE akt.id = foglalas.jarat_id) = 0;
END;
/

CREATE OR REPLACE TRIGGER foglalas_torles
BEFORE DELETE
ON FOGLALAS
FOR EACH ROW
BEGIN

    DELETE FROM JEGY WHERE foglalasi_id = :OLD.id;
END;
/