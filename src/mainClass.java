import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class mainClass {

	public static void main(String[] args) throws Exception {
			
			System.out.println("STARTNEWSPROC");
			String messages[]=new String[10000];
			
			int c = 0;
			do {
				// Connecting Server
				try {
					Document doc= Jsoup.connect("https://sslecal2.forexprostools.com/?columns=exc_currency,exc_actual&category=_employment,_economicActivity,_inflation,_credit,_centralBanks,_confidenceIndex,_balance,_Bonds&importance=1,2,3&countries=29,25,54,145,47,34,174,163,32,70,6,27,37,122,15,78,113,107,55,24,121,59,89,72,71,22,17,51,39,93,106,14,48,66,33,23,10,119,35,92,102,57,94,97,68,96,103,111,42,109,188,7,139,247,105,172,21,43,20,60,87,44,193,125,45,53,38,170,100,56,80,52,238,36,90,112,110,11,26,162,9,12,46,85,41,202,63,123,61,143,4,5,138,178,84,75&calType=day&timeZone=65&lang=1").timeout(6000).get();
					Elements ele=doc.select("table#ecEventsTable");
					//System.out.println("Connected");
					
					// Get page elements
					for (Element element : ele.select("tr")) {
						
						// Actual
						String Actual=element.select("td.act").text().trim();
						Actual = Actual.replaceAll("\u00A0", "").trim();
	
						//System.out.println(Actual);
						
						if(!Actual.isEmpty()) {
							// Currency
							String Currency=element.select("td.flagCur").text();
							Currency= Currency.replaceAll("\u00A0", "").trim();
							
							// Event
							String Event=element.select("td.event").text();
							Event= Event.replaceAll("\u00A0", "").trim();
		
							// Format message
							String message= " " + Currency + " " + Event + " ACT " + Actual;
							//System.out.println(message);

							// Check if message is announced
							boolean found=false;
							for(int i=0; i<c; i++) {
								if(messages[i].equals(message)) {
									found=true;
								}							
							}
							
							// if not announced before then send message
							if(found==false) {
								messages[c]=message;
								String time = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
								System.out.println("H:INV:" + time + message);
								c++;
							}
						}
					}
	
				}catch(Exception e){};
	
				Thread.sleep(10);
				
			}while (true);
			
			//System.out.println("Successfully Done");
	}	
}