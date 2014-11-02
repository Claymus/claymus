package com.claymus.commons.client.ui;

import java.util.Date;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimePicker extends Composite implements HasValue<Date> {
	
	private DatePicker datePicker = new DatePicker();
	private Panel timePicker = new FlowPanel();

	private ListBox hourList = new ListBox();
	private ListBox minuteList = new ListBox();
	private ListBox amPmList = new ListBox();
	
	private Label separator_1 = new Label( ":" );
	private Label separator_2 = new Label( " " );
	
	
	public DateTimePicker() {
		FlowPanel panel = new FlowPanel();
		
		timePicker.add( hourList );
		timePicker.add( separator_1 );
		timePicker.add( minuteList );
		timePicker.add( separator_2 );
		timePicker.add( amPmList );

		panel.add( datePicker );
		panel.add( timePicker );

		for( int i = 0; i <= 11; i++ )
			hourList.addItem( Integer.toString( i ), Integer.toString( i ) );
		
		for( int i = 0; i <= 59; i = i + 5 )
			minuteList.addItem( Integer.toString( i ), Integer.toString( i ) );
		
		amPmList.addItem( "AM", "AM" );
		amPmList.addItem( "PM", "PM" );

		
		initWidget( panel );
		
		
		getElement().setAttribute( "style", "display: table" );

		datePicker.getElement().setAttribute( "style", "border-bottom: 0px;" );
		timePicker.getElement().setAttribute( "style", "text-align: center;"
			+ "padding-top: thin-padding;"
			+ "padding-bottom: thin-padding;"
			+ "border: 1px solid #ccc;"
			+ "border-top: 0px;" );
		
		separator_1.getElement().setAttribute( "style", "display: inline;" );
		separator_2.getElement().setAttribute( "style", "display: inline;" );
		
		
		datePicker.addValueChangeHandler( new ValueChangeHandler<Date>() {
			
			@Override
			public void onValueChange( ValueChangeEvent<Date> event ) {
				DateTimePicker.this.fireEvent( new ValueChangeEvent<Date>( DateTimePicker.this.getValue() ) {} );
			}
			
		} );
		
		ChangeHandler changeHandler = new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				DateTimePicker.this.fireEvent( new ValueChangeEvent<Date>( DateTimePicker.this.getValue() ) {} );
			}
			
		};
		
		hourList.addChangeHandler( changeHandler );
		minuteList.addChangeHandler( changeHandler );
		amPmList.addChangeHandler( changeHandler );
		
		setValue( new Date() );
	}

	
	@Override
	public Date getValue() {
		int hour = Integer.parseInt( hourList.getValue( hourList.getSelectedIndex() ) );
		if( amPmList.getValue( amPmList.getSelectedIndex() ).equalsIgnoreCase( "pm" ) )
			hour = hour + 12;
		int minute = Integer.parseInt( minuteList.getValue( minuteList.getSelectedIndex() ) );
		
		return new Date( this.datePicker.getValue().getTime() + ( ( hour * 60 ) + minute ) * 60 * 1000 );
	}
	
	@Override
	public void setValue( Date value ) {
		setValue( value, false );
	}
	
	@Override
	public void setValue( Date value, boolean fireEvents ) {
		datePicker.setValue( value );

		int hour = Integer.parseInt( DateTimeFormat.getFormat( "HH" ).format( value ) );
		int minute = Integer.parseInt( DateTimeFormat.getFormat( "mm" ).format( value ) );

		hourList.setSelectedIndex( hour % 12 );
		minuteList.setSelectedIndex( minute / 5 );
		amPmList.setSelectedIndex( hour < 12 ? 0 : 1 );
		
		DateTimePicker.this.fireEvent( new ValueChangeEvent<Date>( value ) {} );
	}

	@Override
	public HandlerRegistration addValueChangeHandler( ValueChangeHandler<Date> handler ) {
		return addHandler( handler, ValueChangeEvent.getType() );
	}

}