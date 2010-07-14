-- sqlite3 gpi.db // create the DB
DROP Table produkt;
drop table epc;

CREATE TABLE produkt (
	GTIN NUMERIC PRIMARY KEY,
	name VARCHAR(50),
	ausfuehrung VARCHAR(50),
	gewicht FLOAT,
	masse VARCHAR(20),
	anzahl_verfuegbar INTEGER,
	preis FLOAT,
  praesentation VARCHAR(4)
);

CREATE TABLE epc (
	SGTIN NUMERIC PRIMARY KEY,
	GTIN NUMERIC,
	GLN NUMERIC,
  SRN NUMERIC,
	vk_datum VARCHAR(20)
);

-- select * from produkt;

-- select * from epc;

--> ab hier, VERKAUFS_EINHEITEN

INSERT INTO produkt VALUES(2965197100101,'DIN A4 Papier','500 Blatt / Packung',2.5,'300 x 215 x 70',235,2.99, 'VKE');
INSERT INTO produkt VALUES(2965197100200,'Toilettenpapier','8 Stück / Packung',0.4,'700 x 400 x 110',114,2.49,'VKE');
INSERT INTO produkt VALUES(2965197100309,'Servietten','100 Stück / Packung',0.15,'200 x 200 x 65',1890,0.89,'VKE');
INSERT INTO produkt VALUES(2965197100408,'Kuechenrollen','3 Stück / Packung',0.6,'400 x 90 x 300',440,1.29,'VKE');
INSERT INTO produkt VALUES(2965197100507,'Briefumschlaege','10 Stück / Packung',0.1,'230 x 115 x 20',965,1.09,'VKE');

--> ab hier, VERPACKUNGS_EINHEITEN

INSERT INTO produkt VALUES(2965197100118,'DIN A4 Papier (Karton)','12 x VerkaufsEinheit',30,'600 x 430 x 210',35,34,'VPE');
INSERT INTO produkt VALUES(2965197100125,'DIN A4 Papier (Palette)','12 x 12 x VerkaufsEinheit',360,'1200 x 860 x 630',14,396,'VPE');
INSERT INTO produkt VALUES(2965197100217,'Toilettenpapier (Karton)','6 x VerkaufsEinheit',2.4,'1400 x 1200 x 110',44,14,'VPE');
INSERT INTO produkt VALUES(2965197100224,'Toilettenpapier (Palette)','4 x 6 x VerkaufsEinheit',9.6,'2800 x 2400 x 220',12,53.4,'VPE');
INSERT INTO produkt VALUES(2965197100316,'Servietten (Karton)','20 x VerkaufsEinheit',3,'1000 x 400 x 130',369,17.2,'VPE');
INSERT INTO produkt VALUES(2965197100323,'Servietten (Palette)','8 x 20 x VerkaufsEinheit',24,'2000 x 800 x 260',28,133.2,'VPE');
INSERT INTO produkt VALUES(2965197100415,'Kuechenrollen (Karton)','8 x VerkaufsEinheit',4.8,'800 x 180 x 600',33,9.40,'VPE');
INSERT INTO produkt VALUES(2965197100422,'Kuechenrollen (Palette)','8 x 8 x VerkaufsEinheit ',38.4,'1600 x 360 x 1200',12,72.1,'VPE');
INSERT INTO produkt VALUES(2965197100514,'Briefumschlaege (Karton)','40 x VerkaufsEinheit',4,'920 x 230 x 100',90,40.6,'VPE');
INSERT INTO produkt VALUES(2965197100521,'Briefumschlaege (Palette)','20 x 40 x VerkaufsEinheit',80,'1840 x 460 x 500',19,780,'VPE');
