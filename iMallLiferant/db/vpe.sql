drop table palette;
drop table karton;

CREATE TABLE Karton(
  gtin_karton NUMERIC PRIMARY KEY,
  gtin_vke NUMERIC,
  anzahl INTEGER
);

CREATE TABLE Palette
(
  gtin_palette NUMERIC PRIMARY KEY,
  gtin_karton NUMERIC,
  anzahl INTEGER,
  constraint fk_palette
    foreign key(gtin_karton)
    references Karton(gtin_karton)
);

--INSERT karton
--DIN A4 Papier
INSERT INTO karton values(2965197100118,2965197100101,12);
--Toilettenpapier
INSERT INTO karton values(2965197100217, 2965197100200,6);
--Servietten 
INSERT INTO karton values(2965197100316,2965197100309,20);
--Kuechenrollen
INSERT INTO karton values(2965197100415,2965197100408,8);
--Briefumschlaege
INSERT INTO karton values(2965197100514,2965197100507,40);


--insert palette
--DIN A4 Papier
INSERT INTO palette values(2965197100125,2965197100118,12);
--Toilettenpapier
INSERT INTO palette values(2965197100224,2965197100217,4);
--Servietten 
INSERT INTO palette values(2965197100323,2965197100316,8);
--Kuechenrollen
INSERT INTO palette values(2965197100422,2965197100415,8);
--Briefumschlaege
INSERT INTO palette values(2965197100521,2965197100514,20);
