package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;

import uk.ac.ed.bikerental.Utils.EBikeType;
import uk.ac.ed.bikerental.Utils.ECondition;

public class TestBookingAQuote {

	BikeRentalSystem brs;
	Customer c;
	
	/*
	 * Using this file, we shall test the following use case:
	 * A customer wants to book a quote. Price/Deposit of the quote have
	 * already been computed, i.e. the quote has been generated. 
	 * Our tests will pass if the payment happens as it should,
	 * if the invoice for the quote is passed to the customer accordingly
	 * and if the requested bikes are available iff we would expect them to. 
	 */
	
	
	@BeforeAll
	void setUp() {
		DeliveryServiceFactory dsf = new DeliveryServiceFactory();
		dsf.setupMockDeliveryService();
		DeliveryService ds = dsf.getDeliveryService();
		brs = new BikeRentalSystem(ds, LocalDate.now());
		
		Location cLoc = new Location("EH12FJ", "79 Street Street");
		c = new Customer(brs, "Bob", "Bobertson" , "911" , cLoc);
		
		Location loc = new Location("EH89QX", "5 Main Street");
		StandardPricingPolicy spp = new StandardPricingPolicy();
		StandardValuationPolicy svp = new StandardValuationPolicy();
		brs.registerProvider(loc, svp, spp);
		
		Location loc2 = new Location("EH89BL", "12 Side Street");
		brs.registerProvider(loc2, svp, spp);
		
		brs.registerBikeType(EBikeType.MOUNTAIN, new BigDecimal(500));
		brs.registerBikeType(EBikeType.HYBRID, new BigDecimal(700));
		brs.registerBikeType(EBikeType.ROAD, new BigDecimal(300));
		
		brs.registerBike(brs.getType(EBikeType.MOUNTAIN),ECondition.BAD,  LocalDate.MIN, 1);
		brs.registerBike(brs.getType(EBikeType.MOUNTAIN), ECondition.AVERAGE, LocalDate.EPOCH, 1);
		brs.registerBike(brs.getType(EBikeType.HYBRID), ECondition.GOOD, LocalDate.EPOCH, 1);
		brs.registerBike(brs.getType(EBikeType.HYBRID), ECondition.NEW, LocalDate.now(), 2);
		brs.registerBike(brs.getType(EBikeType.ROAD), ECondition.NEW, LocalDate.MAX, 2);
	}
	
	@Test
	void testSimpleOrder() {
		LinkedList<Bike> oneBike = new LinkedList<Bike>();
		oneBike.add(brs.getProviderWithID(1).getBikeWithCode(0));
		assert(oneBike.get(0).getManufactureDate().equals(LocalDate.MIN));
		
		BigDecimal price = new BigDecimal(30);
		BigDecimal deposit = new BigDecimal(100);
		
		LocalDate today = LocalDate.now();
		LocalDate soon = LocalDate.now().plusDays(3);
		DateRange dr = new DateRange(today, soon);
		
		Quote q = new Quote(brs.getProviderWithID(1) , price, deposit, oneBike, dr);
	}
	
}