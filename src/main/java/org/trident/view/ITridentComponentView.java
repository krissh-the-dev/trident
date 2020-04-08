package org.trident.view;

/**
 * @author https://github.com/vincenzopalazzo
 */
public interface ITridentComponentView {

    //This is an interface, the interface make the rules inside the class
    //An example, all class that implement this interface should be have the following methods

    /**
     * Inside this method you called the previus method to initialized all
     * an example inside this method you should be call this sequence
     * initComponent();
     * initLayout();
     * initActions();
     */
    void initView();


    /**
     * Inside this method you create the component
     */
    void initComponent();

    /**
     * Inside this method you should be initialize the layout
     */
    void initLayout();

    /**
     * Inside this method the component init the action
     */
    void initActions();
}
