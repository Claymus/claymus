package com.claymus.commons.client.ui;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;

public class StarRating extends Composite implements HasValue<Long> {

    private FlowPanel mainPanel = new FlowPanel();
    private Image[] stars;
    
    private Long rating = 0L;
    private int rating_max = 5;
    private int hover_index = 0;
    private boolean read_only = false;     
    private String star_selected_path = "images/selected_blue.png";
    private String star_unselected_path = "images/unselected.png";
    private String star_hover_path = "images/hover_blue.png";
    
    //Default Constructor
    public StarRating() {
        this(0L, 5, false);
    }
    
    public StarRating(Long rating, boolean read_only){
        this( rating, 5, read_only );
        
    }
    
    public StarRating(Long rating, int rating_max, boolean read_only){
    	stars = new Image[ rating_max ];
    	for( int i=0; i< rating_max; i++)
        	stars[i] = new Image();
        this.setRating(rating);
        this.setRatingMax(rating_max);
        this.setReadOnly(read_only);
        this.buildWidget();
    }
    
    //Build the widget
    private void buildWidget() {        
        Image.prefetch(this.getStarSelectedPath());
        Image.prefetch(this.getStarUnselectedPath());
        Image.prefetch(this.getStarHoverPath());
        
        //Initialize
        initWidget(mainPanel); 
        mainPanel.setStyleName("starrating");
                
        //Stars
        for (int i = 0; i < this.getRatingMax(); i++) {

        	//Settings
            stars[i].setStyleName("star");
            stars[i].setTitle("" + (i + 1));
            mainPanel.add( stars[i] );
        }
        
        //If not readonly
        if (!this.isReadOnly()) {
        	for (int i = 0; i < this.getRatingMax(); i++) 
                stars[i].getElement().getStyle().setCursor(Style.Cursor.POINTER);
        }
        
        //Set the star images
        this.setStarImages();                
    }
    
    //Out Mouse Handler
    private HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
        return addDomHandler(handler, MouseOutEvent.getType());         
    }

    //Resets the star images
    public void setStarImages() {
        for (int i = 0; i < this.getRatingMax(); i++) {
            Image image = (Image)mainPanel.getWidget(i);
            image.setUrl(this.getImagePath(i));        
        }
    }
    
   //Returns images paths
    private String getImagePath( int index ) {
        String path = "";
        
        if (index >= this.getHoverIndex()) {
            if (index >= this.getRating()) {
                path = this.getStarUnselectedPath();
            }
            else {
                path = this.getStarSelectedPath();
            }
        }
        else {
            path = this.getStarHoverPath();
        }   
    
        return path;
    }

    //Sets rating
    public void setRating(Long rating) {
        this.rating = rating;
    }

    //returns rating
    public Long getRating() {
        return rating;
    }

    //sets max rating
    public void setRatingMax(int rating_max) {
        this.rating_max = rating_max;
    }

    //returns max rating
    public int getRatingMax() {
        return rating_max;
    }

    //sets Hover Index
    public void setHoverIndex(int hover_index) {
        this.hover_index = hover_index;
    }
    
    //Returns hover index
    public int getHoverIndex() {
        return hover_index;
    }

    //Sets rating panel read only
    public void setReadOnly( boolean read_only ) {
        this.read_only = read_only;
    }

    public boolean isReadOnly() {
        return read_only;
    }

    public void setStarSelected(String star_selected) {
        this.star_selected_path = star_selected;
    }

    public String getStarSelectedPath() {
        return star_selected_path;
    }

    public void setStarUnselected(String star_unselected) {
        this.star_unselected_path = star_unselected;
    }

    public String getStarUnselectedPath() {
        return star_unselected_path;
    }

    public void setStarHover(String star_hover) {
        this.star_hover_path = star_hover;
    }

    public String getStarHoverPath() {
        return star_hover_path;
    }

    //Mouse Over Handler
    public void addStarsMouseOverHandler( MouseOverHandler mouseOverHandler ){
    	for(int i=0; i< this.getRatingMax(); i++)
    		stars[i].addMouseOverHandler( mouseOverHandler );
    }
    
    /*//Mouse over event
    public void onMouseOver(MouseOverEvent event) {
        if (!this.isReadOnly()) {
            Image image = (Image)event.getSource();    
            this.setHoverIndex(Integer.parseInt(image.getTitle()));                
            this.setStarImages();
        }
    }*/
    
   //Onclick event
    public void addStarsClickHandler(ClickHandler clickHandler){
    	for(int i=0; i< this.getRatingMax(); i++)
    		stars[i].addClickHandler( clickHandler );
    }

    //On mouse out event
    public HandlerRegistration addStarsMouseOutHandler( MouseOutHandler mouseOutHandler ){
    	return this.addMouseOutHandler( mouseOutHandler );
    }    
    /*public void onMouseOut(MouseOutEvent event) {
        this.setHoverIndex(0);
        this.setStarImages();
    }*/

    /**
     * Adds a ValueChangehandler
     */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Long> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    //returns rating value
    public Long getValue() {
        return this.getRating();
    }

    //sets rating value
    public void setValue(Long value) {
        this.setRating(value);
        this.setStarImages();
    }

    //sets rating and fire value change event.
    public void setValue(Long value, boolean fireEvents) {
        this.setValue(value);
        if (fireEvents)
            ValueChangeEvent.fire(this, value);
    }
    
}

