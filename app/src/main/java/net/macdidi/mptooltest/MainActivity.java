 package net.macdidi.mptooltest;

 import android.os.Bundle;
 import android.support.v7.app.AppCompatActivity;
 import android.view.View;
 import android.widget.ListView;
 import android.widget.TextView;

 import java.util.ArrayList;
 import java.util.List;

 public class   MainActivity extends AppCompatActivity {


     private ListView TestList;
     private TextView Result;
     private List<Item> items;
     private boolean resultpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestList = (ListView) findViewById(R.id.listView);
        Result = (TextView) findViewById(R.id.result);

        items = new ArrayList<Item>();
        items.add(new Item(1, "method1", "-"));
        items.add(new Item(2, "method2", "-"));
        items.add(new Item(3, "method3", "-"));
        items.add(new Item(4, "method4", "-"));
        items.add(new Item(5, "method5", "-"));
        items.add(new Item(6, "method6", "-"));
        items.add(new Item(7, "method7", "-"));
        items.add(new Item(8, "method8", "-"));
        items.add(new Item(9, "method9", "-"));
        items.add(new Item(10, "method10", "-"));
        items.add(new Item(11, "method11", "-"));
        items.add(new Item(12, "method12", "-"));
        items.add(new Item(13, "method13", "-"));


        TestList.setAdapter(new ItemAdapter(this, R.layout.item_list, items));
    }

     public void startTest(View view){

         method1(items.get(1));
         method2(items.get(2));
         method3(items.get(3));
         method4(items.get(4));
         method5(items.get(5));
         method6(items.get(6));
         method7(items.get(7));
         method8(items.get(8));
         method9(items.get(9));
         method10(items.get(10));
         method11(items.get(11));
         method12(items.get(12));
         method13(items.get(13));

//         for(int i=1;i < 13; i++)
//         {
//             if(items.get(i).getPass()=="pass")
//                resultpass = true;
//             else
//                 resultpass = false;
//         }
//
//         if(resultpass==true)
//             Result.setText("Pass");



     }

     public void method1(Item item)
     {
         item.setPass("Pass");

     }
     public void method2(Item item)
     {
         item.setPass("Pass");

     }
     public void method3(Item item)
     {
         item.setPass("Pass");

     }
     public void method4(Item item)
     {
         item.setPass("Pass");

     }
     public void method5(Item item)
     {
         item.setPass("Pass");

     }
     public void method6(Item item)
     {
         item.setPass("Pass");

     }
     public void method7(Item item)
     {
         item.setPass("Pass");

     }
     public void method8(Item item)
     {
         item.setPass("Pass");

     }
     public void method9(Item item)
     {
         item.setPass("Pass");

     }
     public void method10(Item item)
     {
         item.setPass("Pass");

     }
     public void method11(Item item)
     {
         item.setPass("Pass");

     }
     public void method12(Item item)
     {
         item.setPass("Pass");

     }
     public void method13(Item item)
     {
         item.setPass("Pass");

     }



}
