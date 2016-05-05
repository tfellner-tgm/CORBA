package calculator;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.CORBA.Object;

/**
 * @author Thomas Fellner
 * @version 05.05.2016
 *
 * Ruft die Echo Methode des C++ Servers auf und gibt einen String auf der Konsole aus.
 * Sollte ein Fehler aufgetreten sein, so wird eine Exception geworfen und eine Fehlermeldung zusammen mit dem Stracktrace auf der Konsole ausgegeben.
 */
public class Client {
	
	public static void main(String[] args)  {

		//create Calculator Object
		Calculation calculator = connectToRemote(args);
		System.out.println("5 + 2 = " + calculator.add(5, 2));
		System.out.println("2 - 3 = " + calculator.subtract(2, 3));
		System.out.println("2 * 4.35 = " + calculator.multiply(2, 4.35));
		
		try {
			System.out.println("5.2 / 2.5 = " + calculator.divide(5.2, 2.5));
			
			//calculator.divide(16,0);
		}catch(DivisionThroughZero e) {
			System.err.println(e.message);
		}    
	}

	public static Calc connectToRemote(String[] args) {
		try {

			/* Erstellen und intialisieren des ORB */
			ORB orb = ORB.init(args, null);

			/* Erhalten des RootContext des angegebenen Namingservices */
			Object o = orb.resolve_initial_references("NameService");

			/* Verwenden von NamingContextExt */
			NamingContextExt rootContext = NamingContextExtHelper.narrow(o);

			/* Angeben des Pfades zum Calculate Objekt */
			NameComponent[] name = new NameComponent[2];
			name[0] = new NameComponent("test","my_context");
			name[1] = new NameComponent("Calculation", "Object");

			/* Aufloesen der Objektreferenzen  */
			return CalculationHelper.narrow(rootContext.resolve(name));

		}	catch (Exception e)	{
			System.err.println("Es ist ein Fehler aufgetreten: " + e.getMessage());
			e.printStackTrace();
			
			return null;
		}

	}

}
