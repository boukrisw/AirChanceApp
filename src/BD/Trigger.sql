
Create OR REPLACE TRIGGER TriggerAjoutAvion AFTER INSERT ON LesAvions_Projet
FOR EACH ROW
Declare
	i integer;
BEGIN
	for i in 1..:new.nbPlacesEco loop
		if i mod 6=1 or i mod 6=0  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'hublot','eco',100);
		end if;
		if i mod 6=2 or i mod 6=5  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'centre','eco',100);
		end if;
		if i mod 6=3 or i mod 6=4  then	
			insert into LesPlaceAvions_Projet values (i,:new.numAvion,'couloir','eco',100);
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
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'hublot','affaire',100);
		end if;
		if i mod 6=2 or i mod 6=5  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'centre','affaire',100);
		end if;
		if i mod 6=3 or i mod 6=4  then	
			insert into LesPlaceAvions_Projet values (:new.nbPlacesEco+:new.nbPlacesPremiere+i,:new.numAvion,'couloir','affaire',100);
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

