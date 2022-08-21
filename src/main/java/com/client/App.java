package com.client;


import javax.swing.*;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        simplyService service = new Proxy();

        callService(service);
    }


    static void callService(simplyService service){
        System.out.println(service.getInt(5));
    }


}
