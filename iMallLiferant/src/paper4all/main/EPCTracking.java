package paper4all.main;

import java.util.List;
import java.util.Scanner;

import paper4all.WHinterface.GetSGTINWhereaboutsFromStorage;
import paper4all.WHinterface.SgtinContainer;
import paper4all.WHinterface.SgtinWhereAbouts;
import paper4all.WHinterface.Storage;
import paper4all.WHinterface.StorageService;

public class EPCTracking {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		int anzahlSgtin;
		Scanner scan = new Scanner(System.in);
		System.out.println("anzahl sgtins: ");
		anzahlSgtin = scan.nextInt();
		//List
		
		for(int i=0; i<anzahlSgtin; i++)
		{
			
		}
		
		Storage storage = new StorageService().getStoragePort();
		SgtinWhereAbouts res = storage.getSGTINWhereaboutsFromStorage(null);
		List<SgtinContainer> sgtinRes = res.getSgtin();
		

	}

}
