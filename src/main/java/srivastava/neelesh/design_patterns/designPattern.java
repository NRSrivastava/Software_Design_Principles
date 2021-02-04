/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srivastava.neelesh.design_patterns;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Neelesh
 */
public class designPattern {
    public static void main(String [] args){
        System.out.println("Please enter below:");
        Scanner sc=new Scanner(System.in);
        HashSet<Observer> hs=new HashSet<Observer>();
        Notifier n=new Notifier();
        HashMap<String,Observer> hm= new HashMap<String,Observer>();
        hm.put("banana",new Banana());
        hm.put("ink",new Ink());
        hm.put("salt",new Salt());
        hm.put("frog",new Frog());
        hm.put("blood",new Blood());
        hm.put("sky",new Sky());
        hm.put("apple",new Apple());
        boolean loop=true;
        while(loop){
            String word=sc.next();
            if(word.length()<=0)
                continue;
            if(word.charAt(0)=='+'){
                String temp=(word.substring(1)).toLowerCase();
                if(hm.containsKey(temp))
                   n.subscribe(hm.get(temp));
            }
            else if(word.charAt(0)=='-'){
                String temp=(word.substring(1)).toLowerCase();
                if(hm.containsKey(temp))
                  n.unsubscribe(hm.get(temp));
            }
            else if(word.equals("list")){
                n.printObservers();
            }
            else if(word.equals("exit")){
                return;
            }
            else{
                n.post(word);
            }
        }
        
        
        /*
        Notifier p=new Notifier();
        Observer b=null;
        p.subscribe(b);
        p.subscribe(new Frog());
        p.post("green");
        p.unsubscribe(b);
        p.post("yellow");
        */
    }
}

      // ==============================================================
      // Factory Class
      // ==============================================================
 
    abstract class Object{
        private Color cCheck;
        private Response response;
        public void setCCheck(Color col){
             this.cCheck=col;
         }
        public void setResponse(Response res){
            this.response=res;
        }
        public void process(String s){
            if(cCheck.myColor(s))
                System.out.println(response.respond());
        }
    }
    

      // ==============================================================
      // IMPLEMENTATIONS
      // ==============================================================

      class Notifier implements Observable {
        
        private Set<Observer> observers = new HashSet<>();

        private Deque<String> posts = new ArrayDeque<>();
        
        public void printObservers(){
            System.out.print("[");
            for(Observer o:observers){
                System.out.print(o.getClass().getSimpleName()+", ");
            };
            System.out.println("]");
        }
        
        @Override
        public void subscribe(Observer observer) {
          observers.add(observer);
        }

        @Override
        public void unsubscribe(Observer observer) {
          observers.remove(observer);
        }

        public void post(String msg) {
          posts.add(msg);
          for (Observer observer : observers) {
            observer.onUpdate(this);
          }
        }

        @Override
        public String getUpdate() { //Returns the last posted message.
          return posts.peekLast();
        }
      }


      // ==============================================================
      // OBSERVER
      // ==============================================================

      interface Observer {
        public void onUpdate(Observable observable);
      }
      
      // ==============================================================
      // OBSERVABLE
      // ==============================================================
 
      interface Observable {
        public void subscribe(Observer observer);

        public void unsubscribe(Observer observer);

        public String getUpdate();
      }
      
      // ==============================================================
      // STRATEGY
      // ==============================================================
      
 
      interface Response{
        public String respond();
      }
        
      class NormalObject implements Response{
          private String color;
          private Object object;
          NormalObject(Object ob,String col){
              this.color=col;
              this.object=ob;
          }
          @Override
          public String respond(){
              return "I'm "+object.getClass().getSimpleName()+"! I'm sometimes "+color+".";
          }
      }
      
      class SpecialObject implements Response{
          private String color;
          private Object object;
          SpecialObject(Object ob,String col){
              this.color=col;
              this.object=ob;
          }
          @Override
          public String respond(){
              return "I'm "+object.getClass().getSimpleName()+"! I am "+color+" today.";
          }
      }

      class Color{
          private HashSet<String> hs;
          Color(List<String> col){
              hs=new HashSet<String>(col);
          }
          public boolean myColor(String cl){
              return hs.contains(cl.toLowerCase());
          };
      }
      
      // ==============================================================
      // All Objects Implimentation
      // ==============================================================
      
      class Banana extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("yellow");add("green");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Ink extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("red");add("black");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Salt extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("white");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Blood extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("red");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Sky extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("blue");add("black");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Apple extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("red");add("green");}}));
          setResponse(new NormalObject(this,msg));
          process(msg);
        }
      }
      
      class Frog extends Object implements Observer{
        @Override
        public void onUpdate(Observable observable) {
          String msg = observable.getUpdate();
          setCCheck(new Color(new LinkedList<String>(){{add("yellow");add("blue");}}));
          setResponse(new SpecialObject(this,msg));
          process(msg);
        }
      }