drop trigger TriggerAjoutAvion ;
drop trigger TriggerDeleteVol ;
drop table LesHotessesVol_Projet ;
drop table LesPilotesVol_Projet;
drop table LesPilotesModel_Projet ;
drop table LesReservation_Projet;
drop table LesVols_Projet ;
drop table LesPlaceAvions_Projet ;
drop table LesAvions_Projet ;
drop table LesModelAvions_Projet ;
drop table LesClients_Projet ;
drop table LesPilotes_Projet ;
drop table LesHotessesLangues_Projet ;
drop table LesHotesses_Projet ;

create table LesModelAvions_Projet (
	numModel varchar2(20),
	distanceMax number(5),
	nbPilote number(3),
	nbPlace number(3),
	nbHotesse number(3),
	CONSTRAINT PK_LesModelAvions PRIMARY KEY (numModel)
);

create table LesAvions_Projet (
	numAvion number(3),
	numModel varchar2(20),
	nbPlacesEco number(3),
	nbPlacesPremiere number(3),
	nbPlacesAffaire number(3),
	CONSTRAINT PK_LesAvions PRIMARY KEY (numAvion),
	CONSTRAINT FK_LesAvions FOREIGN KEY (numModel) REFERENCES LesModelAvions_Projet (numModel)
);

create table LesPlaceAvions_Projet (
    numPlace number(3),
    numAvion number(3),
    placeposition varchar2(10),
    typeClasse varchar2(10),
    prix float,
    constraint LesPlaceAvions_C2 check (placeposition in ('hublot','couloir','centre')),
    constraint LesPlaceAvions_C3 check (typeClasse in ('eco','premiere','affaire')),
    CONSTRAINT PK_LesPlaceAvions PRIMARY KEY (numPlace,numAvion),
    CONSTRAINT FK_LesPlaceAvions FOREIGN KEY (numAvion) REFERENCES LesAvions_Projet (numAvion)
);

create table LesVols_Projet (
	numVol varchar2(5),
	numAvion number(3),
	aeroportOrigine varchar2(20),
	aeroportDestination varchar2(20),
	dateDepart date,
	durree number(3),
	distance number(8),
	nbPlaceDispoMin number(5),	
	nbPlacesEcoMin number(5),
	nbPlacesPremiereMin number(5),
	nbPlacesAffaire number(5),
	CONSTRAINT PK_LesVols PRIMARY KEY (numVol),	
	CONSTRAINT FK_LesVols FOREIGN KEY (numAvion) REFERENCES LesAvions_Projet (numAvion)
);

create table LesClients_Projet (
	numClient number(3),
	nomClient varchar2(20),
	prenomClient varchar2(20),
	numPassport varchar2(20),
	reduction number(3),
	numero number(3),
	rue varchar2(30),
	codePostale number(5), 
	ville varchar2(20), 
	pays varchar2(20),
	CONSTRAINT PK_Clients_Projet PRIMARY KEY (numClient)
);

create table LesReservation_Projet (
    numReservation number(3),
    prix float,
    dateReservation date,
    numClient number(3),    
    numVol varchar2(5),
    numPlace number(3),
    numAvion number(3),
    CONSTRAINT FK_Reservation_Clients FOREIGN KEY (numClient) REFERENCES LesClients_Projet(numClient),
    CONSTRAINT FK_Reservation_Vols FOREIGN KEY (numAvion,numPlace) REFERENCES LesPlaceAvions_Projet(numAvion,numPlace)
);

create table LesPilotes_Projet (
	numPersonnel number(3),
	prenom varchar2(20),
	nom varchar2(20),
	nbHeureTotal number(3),
	numero number(3),
	rue varchar2(30),
	codePostale number(5), 
	ville varchar2(20), 
	pays varchar2(20),
	CONSTRAINT PK_Pilotes PRIMARY KEY (numPersonnel)
);

create table LesPilotesModel_Projet (
	numPersonnel number(3),
	numModel varchar2(20),
	CONSTRAINT PK_PilotesModel_Pilotes PRIMARY KEY (numPersonnel, numModel),
	CONSTRAINT FK_PilotesModel_Pilotes FOREIGN KEY (numPersonnel) REFERENCES LesPilotes_Projet(numPersonnel),
	CONSTRAINT FK_PilotesModel_ModelAvions FOREIGN KEY (numModel) REFERENCES LesModelAvions_Projet(numModel)
);

create table LesPilotesVol_Projet (
	numPersonnel number(3),
	numVol varchar2(20),
	CONSTRAINT PK_LesPilotesVol_Pilotes PRIMARY KEY (numPersonnel, numVol),
	CONSTRAINT FK_LesPilotesVol_Pilotes FOREIGN KEY (numPersonnel) REFERENCES LesPilotes_Projet(numPersonnel),
	CONSTRAINT FK_LesPilotesVol_Vol FOREIGN KEY (numVol) REFERENCES LesVols_Projet(numVol)
);


create table LesHotesses_Projet (
	numPersonnel number(3),
	prenom varchar2(20),
	nom varchar2(20),
	nbHeureTotal number(3),
	numero number(3),
	rue varchar2(30),
	codePostale number(5), 
	ville varchar2(20),
	pays varchar2(20),
	CONSTRAINT PK_Hotesses PRIMARY KEY (numPersonnel)
);

create table LesHotessesLangues_Projet (
	numPersonnel number(3),	
        langue varchar2(10),
	constraint PK_HotessesLangues_C1 check (langue in ('francais','anglais','arabe','pular')),
	CONSTRAINT FK_LesHotessesLangues_Projet PRIMARY KEY  (numPersonnel,langue),
	CONSTRAINT FK_HotessesLangues_Hotesses FOREIGN KEY (numPersonnel) REFERENCES LesHotesses_Projet(numPersonnel)
);

create table LesHotessesVol_Projet (
	numPersonnel number(3),	
        numVol varchar2(10),
	CONSTRAINT PK_LesHotessesVol_Projet PRIMARY KEY  (numPersonnel,numVol),
	CONSTRAINT FK_LesHotessesVol_Hotesses FOREIGN KEY (numPersonnel) REFERENCES LesHotesses_Projet(numPersonnel),
	CONSTRAINT FK_LesHotessesVol_Vol FOREIGN KEY (numVol) REFERENCES LesVols_Projet(numVol)
);

insert into LesModelAvions_Projet values ('Airbus A110',10000,3,250,5);
insert into LesModelAvions_Projet values ('Airbus A320',10000,3,300,6);
insert into LesModelAvions_Projet values ('Airbus A321',20000,3,250,5);
insert into LesModelAvions_Projet values ('Airbus A325',30000,3,350,7);
insert into LesModelAvions_Projet values ('Airbus A330',50000,5,500,10);

Create OR REPLACE TRIGGER TriggerAjoutAvion AFTER INSERT ON LesAvions_Projet
FOR EACH ROW
Declare
	i integer;
BEGIN
	for i in 1..:new.nbPlacesEco loop
		if i mod 6=1 or i mod 6=0  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'hublot','eco',50);
		end if;
		if i mod 6=2 or i mod 6=5  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'centre','eco',50);
		end if;
		if i mod 6=3 or i mod 6=4  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'couloir','eco',50);
		end if;
	end loop;
	for i in 1..:new.nbPlacesPremiere loop
		if i mod 6=1 or i mod 6=0  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+i,:new.numAvion,'hublot','premiere',100);
		end if;
		if i mod 6=2 or i mod 6=5  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+i,:new.numAvion,'centre','premiere',100);
		end if;
		if i mod 6=3 or i mod 6=4  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+i,:new.numAvion,'couloir','premiere',100);
		end if;
	end loop;
	for i in 1..:new.nbPlacesAffaire loop
		if i mod 6=1 or i mod 6=0  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'hublot','affaire',150);
		end if;
		if i mod 6=2 or i mod 6=5  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'centre','affaire',150);
		end if;
		if i mod 6=3 or i mod 6=4  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'couloir','affaire',150);
		end if;
	end loop;
END;
/


Create OR REPLACE TRIGGER TriggerDeleteVol BEFORE DELETE ON LesVols_Projet
FOR EACH ROW
BEGIN	
	DELETE FROM LesReservation_Projet WHERE numVol=:old.numVol;
	DELETE FROM LesPilotesVol_Projet WHERE numVol=:old.numVol;
	DELETE FROM LesHotessesVol_Projet WHERE numVol=:old.numVol;
END;
/

insert into LesAvions_Projet values (001,'Airbus A330',300,100,100);
insert into LesAvions_Projet values (002,'Airbus A330',400,75,25);
insert into LesAvions_Projet values (003,'Airbus A330',300,150,50);
insert into LesAvions_Projet values (004,'Airbus A325',200,100,50);
insert into LesAvions_Projet values (005,'Airbus A325',200,125,25);
insert into LesAvions_Projet values (006,'Airbus A321',150,50,50);
insert into LesAvions_Projet values (007,'Airbus A320',150,100,50);
insert into LesAvions_Projet values (008,'Airbus A110',100,100,50);
insert into LesAvions_Projet values (009,'Airbus A110',150,50,50);
insert into LesAvions_Projet values (010,'Airbus A110',150,75,25);

insert into LesVols_Projet values ('AABB1',001,'zate','paris',TO_DATE('2020-05-01 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),3,10000,500,300,100,100);
insert into LesVols_Projet values ('AABB2',010,'casa','lyon',TO_DATE('2020-05-01 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),2,10000,250,150,75,25);
insert into LesVols_Projet values ('AABB3',008,'paris','lyon',TO_DATE('2020-05-01 13:00:00', 'YYYY-MM-DD HH24:MI:SS'),1,1000,250,100,100,50);
insert into LesVols_Projet values ('AABB4',009,'zate','lyon',TO_DATE('2020-05-01 16:00:00', 'YYYY-MM-DD HH24:MI:SS'),3,12000,250,150,50,50);
insert into LesVols_Projet values ('AABB5',007,'paris','casa',TO_DATE('2020-05-01 18:00:00', 'YYYY-MM-DD HH24:MI:SS'),2,10000,300,150,100,50);
insert into LesVols_Projet values ('AABB6',002,'zate','paris',TO_DATE('2020-05-07 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),3,10000,500,300,100,100);
insert into LesVols_Projet values ('AABB7',003,'zate','paris',TO_DATE('2020-05-14 22:00:00', 'YYYY-MM-DD HH24:MI:SS'),3,10000,500,300,100,100);


insert into LesPilotes_Projet values (001,'jean','Tapis',52,10,'Avenue Victor Hugo',34800,'valence','France');
insert into LesPilotes_Projet values (002,'Mario','Balotteli',40,35,'rue de la chapelle',38000,'grenoble','France');
insert into LesPilotes_Projet values (003,'Pierre','Papin',100,08,'rue de la piscine',34800,'SMH','France');
insert into LesPilotes_Projet values (004,'boukris','lahcen',120,8,'rue de maroc',36000,'ouarzazate','maroc');
insert into LesPilotes_Projet values (005,'allen','marcel',50,19,'rue de la piscine',34800,'SMH','France');
insert into LesPilotes_Projet values (006,'Pierre','kabouri',23,32,'Avenue Victor Hugo',34800,'SMH','France');
insert into LesPilotes_Projet values (007,'mehdi','hassan',76,34,'rue de la piscine',34800,'SMH','France');
insert into LesPilotes_Projet values (008,'ziko','bkser',46,65,'rue de la piscine',34800,'SMH','France');

insert into LesPilotesModel_Projet values (001,'Airbus A110');
insert into LesPilotesModel_Projet values (001,'Airbus A320');
insert into LesPilotesModel_Projet values (001,'Airbus A321');
insert into LesPilotesModel_Projet values (001,'Airbus A325');
insert into LesPilotesModel_Projet values (001,'Airbus A330');
insert into LesPilotesModel_Projet values (002,'Airbus A330');
insert into LesPilotesModel_Projet values (003,'Airbus A110');
insert into LesPilotesModel_Projet values (003,'Airbus A330');
insert into LesPilotesModel_Projet values (004,'Airbus A330');
insert into LesPilotesModel_Projet values (005,'Airbus A330');
insert into LesPilotesModel_Projet values (006,'Airbus A110');
insert into LesPilotesModel_Projet values (007,'Airbus A330');
insert into LesPilotesModel_Projet values (008,'Airbus A330');
insert into LesPilotesModel_Projet values (008,'Airbus A321');

insert into LesPilotesVol_Projet values (001,'AABB1');

insert into LesHotesses_Projet values (001,'jamel','debbouze',51,05,'Avenue Victor Hugo',38000,'Grenoble','France');
insert into LesHotesses_Projet values (002,'Maria','Carey',65,08,'oxford street',41000,'atlanta','US');
insert into LesHotesses_Projet values (003,'mathieu','canne',59,13,'avenue latour',26000,'Valence','France');
insert into LesHotesses_Projet values (004,'mat','canne',60,10,'avenue mars',26000,'Valence','France');
insert into LesHotesses_Projet values (005,'anaelle','michelle',30,19,'avenue d italy',26000,'Valence','France');
insert into LesHotesses_Projet values (006,'cristophe','lemer',12,26,'avenue d europe',38000,'Grenoble','France');
insert into LesHotesses_Projet values (007,'allen','michell',10,76,'avenue des nations',41000,'atlanta','US');

insert into LesHotessesLangues_Projet values (001,'arabe');
insert into LesHotessesLangues_Projet values (001,'francais');
insert into LesHotessesLangues_Projet values (001,'anglais');
insert into LesHotessesLangues_Projet values (002,'anglais');
insert into LesHotessesLangues_Projet values (003,'francais');
insert into LesHotessesLangues_Projet values (004,'anglais');
insert into LesHotessesLangues_Projet values (004,'pular');
insert into LesHotessesLangues_Projet values (005,'francais');
insert into LesHotessesLangues_Projet values (006,'anglais');
insert into LesHotessesLangues_Projet values (007,'francais');
insert into LesHotessesLangues_Projet values (007,'pular');

insert into LesHotessesVol_Projet values (007,'AABB1');
insert into LesHotessesVol_Projet values (004,'AABB1');
insert into LesHotessesVol_Projet values (006,'AABB2');
insert into LesHotessesVol_Projet values (001,'AABB2');

insert into LesClients_Projet values (001,'boukris','walid','AA1111',50,23,'Avenue Mohamed 6',34800,'ouarzazate','Maroc');
insert into LesClients_Projet values (002,'sow','ousmane','AA1112',100,5,'Avenue d afrique',34800,'Diaguisso','Guine');
insert into LesClients_Projet values (003,'diallo','mariam','AA1113',0,32,'Avenue Marcel Cachin',34800,'Grenoble','France');
insert into LesClients_Projet values (004,'bah','korka','AA1114',0,6,'Avenue Victor Hugo',34800,'SMH','France');

insert into LesReservation_Projet values (001,142,TO_DATE('01/09/2020', 'DD/MM/YYYY'),001,'AABB1',001,001);
insert into LesReservation_Projet values (001,142,TO_DATE('01/09/2020', 'DD/MM/YYYY'),001,'AABB1',002,001);
insert into LesReservation_Projet values (001,142,TO_DATE('01/09/2020', 'DD/MM/YYYY'),001,'AABB1',003,001);
insert into LesReservation_Projet values (001,142,TO_DATE('01/09/2020', 'DD/MM/YYYY'),001,'AABB1',302,001);
insert into LesReservation_Projet values (004,200,TO_DATE('01/09/2020', 'DD/MM/YYYY'),002,'AABB1',405,001);
insert into LesReservation_Projet values (003,121,TO_DATE('09/01/2020', 'DD/MM/YYYY'),004,'AABB2',003,010);
insert into LesReservation_Projet values (002,135,TO_DATE('08/05/2020', 'DD/MM/YYYY'),003,'AABB3',002,008);
