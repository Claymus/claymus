package com.claymus.commons.client;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.IsSerializable;

public class Amount implements IsSerializable {
	
	private long value;
	
	
	@SuppressWarnings("unused")
	private Amount() {}
	
	public Amount( long value ) {
		this.value = value;
	}
	
	public Amount( double decimalValue ) {
		this.value = (long) ( decimalValue * 100 );
	}
	
	
	public long getValue() {
		return value;
	}
	
	public double getDecimalValue() {
		return ( (double) value ) / 100;
	}
	
	
	public Amount add( Amount amount ) {
		return new Amount( this.value + amount.value );
	}

	@Override
	public String toString() {
		return this.getValue() == 0
				? "-"
				: "Rs. " + NumberFormat.getFormat( "#.00" ).format( ( (double) this.getValue() ) / 100 );
	}

}

